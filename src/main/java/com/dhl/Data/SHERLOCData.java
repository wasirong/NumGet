package com.dhl.Data;

import java.math.BigDecimal;

public class SHERLOCData {

    // 件数_SHERLOC
    private String countSherloc = "";

    // RW重量_SHERLOC
    private String weightSherloc = "";

    // 总价_SHERLOC
    private BigDecimal totalValue = new BigDecimal(0);

    // 币别
    private String currencyTypeSherloc = "";

    public String getCountSherloc() {
        return countSherloc;
    }

    public void setCountSherloc(String countSherloc) {
        this.countSherloc = countSherloc;
    }

    public String getWeightSherloc() {
        return weightSherloc;
    }

    public void setWeightSherloc(String weightSherloc) {
        this.weightSherloc = weightSherloc;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public String getCurrencyTypeSherloc() {
        return currencyTypeSherloc;
    }

    public void setCurrencyTypeSherloc(String currencyTypeSherloc) {
        this.currencyTypeSherloc = currencyTypeSherloc;
    }
}
