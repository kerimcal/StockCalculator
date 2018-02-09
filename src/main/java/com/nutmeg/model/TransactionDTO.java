package com.nutmeg.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

public class TransactionDTO {

    private String account;
    private LocalDate date;
    private TxnTypeEnum txnType;
    private BigDecimal units;
    private BigDecimal price;
    private String asset;

    public TransactionDTO(String account, LocalDate date, TxnTypeEnum txnType, BigDecimal units, BigDecimal price, String asset) {
        this.account = account;
        this.date = date;
        this.txnType = txnType;
        this.units = units;
        this.price = price;
        this.asset = asset;
    }

    public String getAccount() {
        return account;
    }

    public LocalDate getDate() {
        return date;
    }

    public TxnTypeEnum getTxnType() {
        return txnType;
    }

    public BigDecimal getUnits() {
        return units;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getAsset() {
        return asset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDTO that = (TransactionDTO) o;
        return Objects.equals(account, that.account) &&
                Objects.equals(date, that.date) &&
                txnType == that.txnType &&
                Objects.equals(units, that.units) &&
                Objects.equals(price, that.price) &&
                Objects.equals(asset, that.asset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, date, txnType, units, price, asset);
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "account='" + account + '\'' +
                ", date=" + date +
                ", txnType=" + txnType +
                ", units=" + units +
                ", price=" + price +
                ", asset='" + asset + '\'' +
                '}';
    }

    public BigDecimal calculateCashEffect(){
        return  getUnits().multiply(getPrice()).multiply(new BigDecimal(getTxnType().getCashFactor()))
                .setScale( 4, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateAssetEffect(){
        return  getUnits().multiply(new BigDecimal(getTxnType().getAssetFactor()))
                .setScale( 4, RoundingMode.HALF_UP);
    }
}
