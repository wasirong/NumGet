package com.dhl.Data;

public class CDMSData {

    // 件数
    private String CountCDMS = "";

    // 毛重
    private float GrossWeightCDMS;

    // 原始价值
    private float originalValueCDMS;

    // 币别
    private String currencyTypeCDMS = "";

    public String getCountCDMS() {
        return CountCDMS;
    }

    public void setCountCDMS(String countCDMS) {
        CountCDMS = countCDMS;
    }

    public float getGrossWeightCDMS() {
        return GrossWeightCDMS;
    }

    public void setGrossWeightCDMS(float grossWeightCDMS) {
        GrossWeightCDMS = grossWeightCDMS;
    }


    public float getOriginalValueCDMS() {
        return originalValueCDMS;
    }

    public void setOriginalValueCDMS(float originalValueCDMS) {
        this.originalValueCDMS = originalValueCDMS;
    }

    public String getCurrencyTypeCDMS() {
        return currencyTypeCDMS;
    }

    public void setCurrencyTypeCDMS(String currencyTypeCDMS) {
        this.currencyTypeCDMS = currencyTypeCDMS;
    }

    @Override
    public String toString() {
        return "CDMSData{" +
                "CountCDMS='" + CountCDMS + '\'' +
                ", GrossWeightCDMS='" + GrossWeightCDMS + '\'' +
                ", originalValueCDMS='" + originalValueCDMS + '\'' +
                ", currencyTypeCDMS='" + currencyTypeCDMS + '\'' +
                '}';
    }
}
