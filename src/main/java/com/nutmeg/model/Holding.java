package com.nutmeg.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Holding implements java.io.Serializable{
    private String asset;
    private double holding;

    public Holding(String asset, double holding) {
        this.asset = asset;
        this.holding = holding;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public double getHolding() {
        return holding;
    }

    public void setHoldings(double holding) {
        this.holding = holding;
    }

    public void addTransaction(BigDecimal trxAmount){
        holding = trxAmount.add(new BigDecimal(holding)).setScale( 4, RoundingMode.HALF_UP).doubleValue();
    }

    public String toString() {
        return getAsset() + ":\t" + getHolding();
    }
}