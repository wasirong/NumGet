package com.dhl.Data;

public class InputCSVData {

    private CustomsDeclarationData customsDeclarationData = new CustomsDeclarationData();

    private SHERLOCData sherlocData = new SHERLOCData();

    // 报关单逻辑有误
    // 计算的净重不等于取得的净重
    // 净重不小于等于毛重
    // 币别出现两种或两种以上(币别有误)
    private String customsLogicWrong = "";

    // 报关单与SHERLOC匹配不一致
    // 件数不等
    // 总价不等
    // 币别
    private String misMatch = "";

    public CustomsDeclarationData getCustomsDeclarationData() {
        return customsDeclarationData;
    }

    public void setCustomsDeclarationData(CustomsDeclarationData customsDeclarationData) {
        this.customsDeclarationData = customsDeclarationData;
    }

    public String getCustomsLogicWrong() {
        return customsLogicWrong;
    }

    public void setCustomsLogicWrong(String customsLogicWrong) {
        this.customsLogicWrong = customsLogicWrong;
    }

    public String getMisMatch() {
        return misMatch;
    }

    public void setMisMatch(String misMatch) {
        this.misMatch = misMatch;
    }

    public SHERLOCData getSherlocData() {
        return sherlocData;
    }

    public void setSherlocData(SHERLOCData sherlocData) {
        this.sherlocData = sherlocData;
    }
}
