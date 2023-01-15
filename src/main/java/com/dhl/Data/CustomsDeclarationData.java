package com.dhl.Data;

import java.math.BigDecimal;

/**
 * 报关单内容
 */
public class CustomsDeclarationData {

    // 报关单号
    private String customsDeclarationNumber = "";

    // 运单号
    private String waybillNumber = "";

    // 件数
    private String count = "";

    // 毛重
    private BigDecimal grossWeight = new BigDecimal(0);

    // pdf获取的净重
    private BigDecimal netWeight = new BigDecimal(0);

    // 计算来的净重
    private BigDecimal tempWeight = new BigDecimal(0);

    public BigDecimal getTempWeight() {
        return tempWeight;
    }

    public void setTempWeight(BigDecimal tempWeight) {
        this.tempWeight = tempWeight;
    }

    // 成交方式
    private String transactionMethod = "";

    // 运费
    private BigDecimal freight = new BigDecimal(0);

    // 总价
    private BigDecimal totalPrice = new BigDecimal(0);

    // 币别
    private String currencyType = "";

    // 报关单号
    public String getCustomsDeclarationNumber() {
        return customsDeclarationNumber;
    }

    // 报关单号
    public void setCustomsDeclarationNumber(String customsDeclarationNumber) {
        this.customsDeclarationNumber = customsDeclarationNumber;
    }

    // 运单号
    public String getWaybillNumber() {
        return waybillNumber;
    }

    // 运单号
    public void setWaybillNumber(String waybillNumber) {
        this.waybillNumber = waybillNumber;
    }

    // 件数
    public String getCount() {
        return count;
    }

    // 件数
    public void setCount(String count) {
        this.count = count;
    }

    // 毛重
    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    // 毛重
    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    // 净重
    public BigDecimal getNetWeight() {
        return netWeight;
    }

    // 净重
    public void setNetWeight(BigDecimal netWeight) {
        this.netWeight = netWeight;
    }

    // 成交方式
    public String getTransactionMethod() {
        return transactionMethod;
    }

    // 成交方式
    public void setTransactionMethod(String transactionMethod) {
        this.transactionMethod = transactionMethod;
    }

    // 运费
    public BigDecimal getFreight() {
        return freight;
    }

    // 运费
    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    // 总价
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    // 总价
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    // 币别
    public String getCurrencyType() {
        return currencyType;
    }

    // 币别
    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    @Override
    public String toString() {
        return "CustomsDeclarationData{" +
                "customsDeclarationNumber='" + customsDeclarationNumber + '\'' +
                ", waybillNumber='" + waybillNumber + '\'' +
                ", count='" + count + '\'' +
                ", grossWeight='" + grossWeight + '\'' +
                ", netWeight='" + netWeight + '\'' +
                ", transactionMethod='" + transactionMethod + '\'' +
                ", freight='" + freight + '\'' +
                ", totalPrice=" + totalPrice +
                ", currencyType='" + currencyType + '\'' +
                '}';
    }
}
