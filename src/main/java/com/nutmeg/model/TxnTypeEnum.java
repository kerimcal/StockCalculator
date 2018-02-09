package com.nutmeg.model;

public enum TxnTypeEnum {
    BOT(-1, 1),
    SLD(1, -1),
    DIV(1, 0),
    DEP(1, 0),
    WDR(-1, 0);

    int cashFactor = 1;
    int assetFactor = 0;

    TxnTypeEnum(int cashFactor, int assetFactor) {
        this.cashFactor = cashFactor;
        this.assetFactor = assetFactor;
    }

    public int getCashFactor() {
        return cashFactor;
    }

    public int getAssetFactor() {
        return assetFactor;
    }
}
