package com.nutmeg.service;

import com.nutmeg.model.TransactionDTO;
import com.nutmeg.model.TxnTypeEnum;
import com.nutmeg.model.Holding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HoldingCalculatorImpl implements HoldingCalculator {
    private static final Logger LOGGER = LoggerFactory.getLogger(HoldingCalculatorImpl.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final String ASSET_NAME_CASH = "CASH";

    @Override
    public Map<String, List<Holding>> calculateHoldings(File transactionFile, LocalDate date) {
        String d = date.format(formatter);
        final List<TransactionDTO> transactions = readCSVFile(transactionFile);
        final Map<String, List<Holding>> holdings = processTransactions(transactions, date);
        return holdings;
    }

    private List<TransactionDTO> readCSVFile(File transactionFile) {
        String csvSplitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader( transactionFile ));
            return br.lines().map(fl -> {
                        String[] trxLine = fl.toUpperCase().split(csvSplitBy);
                        try {
                            return new TransactionDTO(trxLine[0].trim(), LocalDate.parse(trxLine[1], formatter), TxnTypeEnum.valueOf(trxLine[2]),
                                    new BigDecimal(trxLine[3]), new BigDecimal(trxLine[4]), trxLine[5].trim());
                        } catch (Exception e) {
                            LOGGER.error("CSV FILE ROW READ FAILURE FOR ROW:{}, SKIPPING THE ROW!! Error:", fl, e);
                        }
                        return null;
                    })
                    .filter(t -> Optional.ofNullable(t).isPresent())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("CAN'T READ THE FILE. Error:", e);
        }
        return new ArrayList<>();
    }

    private Map<String,List<Holding>> processTransactions(final List<TransactionDTO> transactions, final LocalDate date) {
        Map<String, List<Holding>> accHoldings = transactions.stream()
                .filter(t -> !t.getDate().isAfter(date))
                .sorted(Comparator.comparing(TransactionDTO::getDate))
                .collect(Collectors.groupingBy(TransactionDTO::getAccount, Collectors.toList()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> this.transformIntoHoldings(e.getValue())));

        return accHoldings;
    }

    private List<Holding> transformIntoHoldings(final List<TransactionDTO> transactions){
        final List<Holding> holdings = new ArrayList<>(Arrays.asList(new Holding(ASSET_NAME_CASH, 0)));
        final Holding cashHolding = holdings.get(0);
        cashHolding.addTransaction(transactions.stream().map(t -> t.calculateCashEffect()).reduce(BigDecimal.ZERO, BigDecimal::add));

        final List<Holding> stockHoldings = transactions.stream()
            .filter( t -> t.getTxnType() != TxnTypeEnum.DIV)
            .filter( t -> !t.getAsset().equalsIgnoreCase(ASSET_NAME_CASH))
            .collect(Collectors.groupingBy( TransactionDTO::getAsset, Collectors.summingDouble(t -> t.calculateAssetEffect().doubleValue())))
            .entrySet().stream()
                .filter( e -> e.getValue() > 0)
                .map(e -> new Holding(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
        holdings.addAll(stockHoldings);
        return holdings;
    }
}
