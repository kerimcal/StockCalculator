package com.nutmeg.service;

import com.nutmeg.model.Holding;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HoldingCalculatorImpl.class})
public class HoldingCalculatorImplTest {

    private static final String ACC_NEAA0000 = "NEAA0000";
    private static final String ACC_NEAB0001 = "NEAB0001";
    private static final Double CASH_AMOUNT_10000 = 10000D;

    @Autowired
    private HoldingCalculator holdingCalculator;


    @Test
    public void testCalculateHoldingsSuccess() throws Exception {

        File f = new File("src/test/resources/transactions.txt");
        Map<String, List<Holding>> result = holdingCalculator.calculateHoldings(f, LocalDate.now());
        assertTrue(result.size() == 2);
        assertTrue( result.get(ACC_NEAA0000).size() == 4);
        assertTrue( result.get(ACC_NEAB0001).size() == 1);

    }

    @Test
    public void testCalculateHoldingsWithAnInvalidLine() throws Exception {

        File f = new File("src/test/resources/transactionsWithInvalidLine.txt");
        Map<String, List<Holding>> result = holdingCalculator.calculateHoldings(f, LocalDate.now());
        assertTrue(result.size() == 2);
        assertTrue( result.get(ACC_NEAA0000).size() == 3);
        assertTrue( result.get(ACC_NEAB0001).size() == 1);

    }

    @Test
    public void testCalculateHoldingsBeforeAWithdrawal() throws Exception {
        File f = new File("src/test/resources/transactions.txt");
        Map<String, List<Holding>> result = holdingCalculator.calculateHoldings(f, LocalDate.parse("2017-02-01"));
        assertTrue(result.size() == 2);
        assertTrue( result.get(ACC_NEAA0000).size() == 4);
        assertTrue( result.get(ACC_NEAB0001).size() == 1);
        assertTrue( result.get(ACC_NEAB0001).stream().filter( h -> h.getAsset().equalsIgnoreCase(HoldingCalculatorImpl.ASSET_NAME_CASH)).findFirst().isPresent());
        result.get(ACC_NEAB0001).stream().filter( h -> h.getAsset().equalsIgnoreCase(HoldingCalculatorImpl.ASSET_NAME_CASH)).findFirst().ifPresent( h-> {
            assertTrue( h.getHolding() == CASH_AMOUNT_10000);
        });

    }

    @Test
    public void testCalculateHoldingsExcludeZeroStocks() throws Exception {

        File f = new File("src/test/resources/transactionsZeroStockHolding.txt");
        Map<String, List<Holding>> result = holdingCalculator.calculateHoldings(f, LocalDate.now());
        assertTrue(result.size() == 2);
        assertTrue( result.get(ACC_NEAA0000).size() == 3);
        assertTrue( result.get(ACC_NEAB0001).size() == 1);
    }

    @Test
    public void testCalculateHoldingsExcludeZeroCash() throws Exception {

        File f = new File("src/test/resources/transactionsZeroCashHolding.txt");
        Map<String, List<Holding>> result = holdingCalculator.calculateHoldings(f, LocalDate.now());
        assertTrue(result.size() == 2);
        assertTrue( result.get(ACC_NEAA0000).size() == 3);
        assertTrue( result.get(ACC_NEAB0001).size() == 1);
        assertTrue( result.get(ACC_NEAB0001).stream().filter( h -> h.getAsset().equalsIgnoreCase(HoldingCalculatorImpl.ASSET_NAME_CASH)).findFirst().isPresent());
        result.get(ACC_NEAB0001).stream().filter( h -> h.getAsset().equalsIgnoreCase(HoldingCalculatorImpl.ASSET_NAME_CASH)).findFirst().ifPresent( h-> {
            assertTrue( h.getHolding() == 0);
        });
    }

    @Test
    public void testCalculateHoldingsWithNegativeBalance() throws Exception {

        File f = new File("src/test/resources/transactionsWithNegativeBalance.txt");
        Map<String, List<Holding>> result = holdingCalculator.calculateHoldings(f, LocalDate.now());
        assertTrue(result.size() == 2);
        assertTrue( result.get(ACC_NEAA0000).size() == 4);
        assertTrue( result.get(ACC_NEAB0001).size() == 1);
        assertTrue( result.get(ACC_NEAA0000).stream().filter( h -> h.getAsset().equalsIgnoreCase(HoldingCalculatorImpl.ASSET_NAME_CASH)).findFirst().isPresent());
        result.get(ACC_NEAA0000).stream().filter( h -> h.getAsset().equalsIgnoreCase(HoldingCalculatorImpl.ASSET_NAME_CASH)).findFirst().ifPresent( h-> {
            assertTrue( h.getHolding() < 0);
        });

    }

    @Test
    public void testCalculateHoldingsWithInvalidDate() throws Exception {

        File f = new File("src/test/resources/transactionsWithInvalidDate.txt");
        Map<String, List<Holding>> result = holdingCalculator.calculateHoldings(f, LocalDate.now());
        assertTrue(result.size() == 2);
        assertTrue( result.get(ACC_NEAA0000).size() == 4);
        assertTrue( result.get(ACC_NEAB0001).size() == 1);

    }
}