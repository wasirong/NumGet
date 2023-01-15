package com.dhl.PDFConvertToCSV;


import com.dhl.AccessSqlUtil.DataBaseSearch;
import com.dhl.Data.CDMSData;
import com.dhl.Data.InputCSVData;
import com.dhl.Data.SHERLOCData;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TurnitInPDFReportProcessor {
    private CSVBuilder csvBuilder;
    private List<String[]> allSumbissionData;
    private List<InputCSVData> allInputCSVData;
    private String inputDir;
    private String hwbDirPath;
    private String outputDir;

    //    Logger LOG = LoggerFactory.getLogger(TurnitInPDFReportProcessor.class);
    public TurnitInPDFReportProcessor(String inputDir, String m_hwbDirPath, String outputDir) {
        csvBuilder = new CSVBuilder(outputDir + "/TurnitInFeedback.csv");
        allSumbissionData = new ArrayList<String[]>();
        allInputCSVData = new ArrayList<InputCSVData>();
        this.inputDir = inputDir;
        this.hwbDirPath = m_hwbDirPath;
        this.outputDir = outputDir;
    }

    public void startProcess() {
        File dir = new File(inputDir);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                int extentionIndex = child.getName().lastIndexOf(".");
                if (extentionIndex == -1 || !child.getName().substring(extentionIndex).toLowerCase().equals(".pdf")) {
//                    LOG.info("当前文件不是pdf");
                    continue;
                }
                InputCSVData paperData = processFile(dir.getPath() + "/" + child.getName(), child.getName());
                allInputCSVData.add(paperData);
            }
            csvBuilder.writeDataToCSV(allInputCSVData);
        } else {
            if (!dir.isDirectory()) {
//                LOG.info("Not a directory");
            }
        }
    }

    public InputCSVData processFile(String path, String name) {
//        LOG.info("path:" + path);
        File file = new File(path);
        FileObject fo = new FileObject(file);
//        LOG.info("PDF文件读取:" + path);
        fo.init();
        List<String> lines = fo.getLines();
//        LOG.info("PDF读取成功");
        InputCSVData inputCSVData = new InputCSVData();
        inputCSVData.getCustomsDeclarationData().setCustomsDeclarationNumber(name.replace(".pdf", ""));
//        LOG.info("PDF报关单号 : " + inputCSVData.getCustomsDeclarationData().getCustomsDeclarationNumber());
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains("提运单号")) {
                inputCSVData.getCustomsDeclarationData().setWaybillNumber(lines.get(i - 1)
                        .substring(lines.get(i - 1).length() - 10, lines.get(i - 1).length()));
//                LOG.info("PDF提运单号 : " + inputCSVData.getCustomsDeclarationData().getWaybillNumber());

            } else if (lines.get(i).equals("件数")) {
                inputCSVData.getCustomsDeclarationData().setCount(lines.get(i - 1));
//                LOG.info("PDF件数 : " + inputCSVData.getCustomsDeclarationData().getCount());

            } else if (lines.get(i).equals("毛重(千克)")) {
                inputCSVData.getCustomsDeclarationData().setGrossWeight(new BigDecimal(lines.get(i - 1)));
//                LOG.info("PDF毛重(千克) : " + inputCSVData.getCustomsDeclarationData().getGrossWeight());

            } else if (lines.get(i).contains("千克") && !lines.get(i).equals("净重(千克)") && !lines.get(i).equals("毛重(千克)") && !lines.get(i).contains("/")) {
                BigDecimal totalWeight = inputCSVData.getCustomsDeclarationData().getTempWeight().add(new BigDecimal(lines.get(i).replace("千克", "")));

                inputCSVData.getCustomsDeclarationData().setTempWeight(totalWeight);

            } else if (lines.get(i).equals("净重(千克)")) {
                inputCSVData.getCustomsDeclarationData().setNetWeight(new BigDecimal(lines.get(i - 1)));
//                LOG.info("PDF净重(千克) : " + inputCSVData.getCustomsDeclarationData().getNetWeight());

//            } else if (lines.get(i).contains("成交方式")) {
//                inputCSVData.getCustomsDeclarationData().setTransactionMethod(lines.get(i - 1));
////                LOG.info("PDF成交方式 : " + inputCSVData.getCustomsDeclarationData().getTransactionMethod());
//
//            } else if (lines.get(i).equals("运费")) {
//                String temStr = lines.get(i - 1).replace("CNY/", "");
//                inputCSVData.getCustomsDeclarationData().setFreight(Float.parseFloat(temStr.substring(0, temStr.indexOf("/"))));
////                LOG.info("PDF运费 : " + inputCSVData.getCustomsDeclarationData().getFreight());

            } else if (lines.get(i).equals("日本元")
                    || lines.get(i).equals("美元")
                    || lines.get(i).equals("欧元")
                    || lines.get(i).equals("人民币")
                    || lines.get(i).equals("英镑")
                    || lines.get(i).equals("韩元")) {
                BigDecimal temPrice = inputCSVData.getCustomsDeclarationData().getTotalPrice().add(new BigDecimal(lines.get(i - 1)));

                inputCSVData.getCustomsDeclarationData().setTotalPrice(temPrice);
                if (!inputCSVData.getCustomsDeclarationData().getCurrencyType().isEmpty()
                        && !inputCSVData.getCustomsDeclarationData().getCurrencyType().equals(lines.get(i))) {
                    inputCSVData.setCustomsLogicWrong(inputCSVData.getCustomsLogicWrong() + "币别有误(" + lines.get(i) + ")/");
                }
                inputCSVData.getCustomsDeclarationData().setCurrencyType(lines.get(i));
            }
        }

//        CDMSData cdmsData = new DataBaseSearch().GetCDMSData(inputCSVData.getCustomsDeclarationData().getWaybillNumber());

//        LOG.info("PDF计算来的净重(千克) : " + inputCSVData.getCustomsDeclarationData().getTempWeight());

//        LOG.info("PDF总价 : " + inputCSVData.getCustomsDeclarationData().getTotalPrice());

//        inputCSVData.setCdmsData(cdmsData);

        SHERLOCData sherlocData = new POIExcelCreHigh().getHWBContent(inputCSVData.getCustomsDeclarationData().getWaybillNumber(), hwbDirPath);

        inputCSVData.setSherlocData(sherlocData);

        // 净重不小于等于毛重
        if (inputCSVData.getCustomsDeclarationData().getNetWeight().compareTo(inputCSVData.getCustomsDeclarationData().getGrossWeight()) == 1) {
            inputCSVData.setCustomsLogicWrong(inputCSVData.getCustomsLogicWrong() + "净重不小于等于毛重/");
        }
        // 计算的净重不等于取得的净重
        BigDecimal tempWeight = inputCSVData.getCustomsDeclarationData().getTempWeight();
        if (((tempWeight.compareTo(new BigDecimal(1)) == -1) ? new BigDecimal(1) : tempWeight).compareTo(inputCSVData.getCustomsDeclarationData().getNetWeight()) != 0) {
            inputCSVData.setCustomsLogicWrong(inputCSVData.getCustomsLogicWrong() + "计算的净重[" + inputCSVData.getCustomsDeclarationData().getTempWeight() + "]不等于取得的净重[" + inputCSVData.getCustomsDeclarationData().getNetWeight() + "]/");
        }
        // 件数不等
        if (Double.parseDouble(sherlocData.getCountSherloc()) != Integer.parseInt(inputCSVData.getCustomsDeclarationData().getCount())) {
            inputCSVData.setMisMatch(inputCSVData.getMisMatch() + "件数不等/");
        }
        // 毛重不等
//        float tempGrossWeightCDMD = cdmsData.getGrossWeightCDMS();
//        if ((tempGrossWeightCDMD < 1 ? 1 : tempGrossWeightCDMD) != inputCSVData.getCustomsDeclarationData().getGrossWeight()) {
//            inputCSVData.setMisMatch(inputCSVData.getMisMatch() + "毛重不等/");
//        }
        // 运费不等
//        if (cdmsData.getFreightCDMS() != inputCSVData.getCustomsDeclarationData().getFreight()) {
//            inputCSVData.setMisMatch(inputCSVData.getMisMatch() + "运费不等/");
//        }
        // 总价不等
        if (sherlocData.getTotalValue().compareTo(inputCSVData.getCustomsDeclarationData().getTotalPrice()) != 0) {
            inputCSVData.setMisMatch(inputCSVData.getMisMatch() + "总价不等/");
        }
//
        boolean currencyCanRead = false;

        // 币别不一致
        switch (sherlocData.getCurrencyTypeSherloc()) {
            case "CNY":
                currencyCanRead = true;
                if (!inputCSVData.getCustomsDeclarationData().getCurrencyType().equals("人民币")) {
                    inputCSVData.setMisMatch(inputCSVData.getMisMatch() + "币别不一致/");
                }
                break;

            case "EUR":
                currencyCanRead = true;
                if (!inputCSVData.getCustomsDeclarationData().getCurrencyType().equals("欧元")) {
                    inputCSVData.setMisMatch(inputCSVData.getMisMatch() + "币别不一致/");
                }
                break;

            case "GBP":
                currencyCanRead = true;
                if (!inputCSVData.getCustomsDeclarationData().getCurrencyType().equals("英镑")) {
                    inputCSVData.setMisMatch(inputCSVData.getMisMatch() + "币别不一致/");
                }
                break;

            case "USD":
                currencyCanRead = true;
                if (!inputCSVData.getCustomsDeclarationData().getCurrencyType().equals("美元")) {
                    inputCSVData.setMisMatch(inputCSVData.getMisMatch() + "币别不一致/");
                }
                break;

            case "JPY":
                currencyCanRead = true;
                if (!inputCSVData.getCustomsDeclarationData().getCurrencyType().equals("日本元")) {
                    inputCSVData.setMisMatch(inputCSVData.getMisMatch() + "币别不一致/");
                }
                break;

            case "KRW":
                currencyCanRead = true;
                if (!inputCSVData.getCustomsDeclarationData().getCurrencyType().equals("韩元")) {
                    inputCSVData.setMisMatch(inputCSVData.getMisMatch() + "币别不一致/");
                }
                break;

            case "AUD":
                currencyCanRead = true;
                if (!inputCSVData.getCustomsDeclarationData().getCurrencyType().equals("澳大利亚元")) {
                    inputCSVData.setMisMatch(inputCSVData.getMisMatch() + "币别不一致/");
                }
                break;

            case "SGD":
                currencyCanRead = true;
                if (!inputCSVData.getCustomsDeclarationData().getCurrencyType().equals("新加坡元")) {
                    inputCSVData.setMisMatch(inputCSVData.getMisMatch() + "币别不一致/");
                }
                break;

            case "CAD":
                currencyCanRead = true;
                if (!inputCSVData.getCustomsDeclarationData().getCurrencyType().equals("加拿大元")) {
                    inputCSVData.setMisMatch(inputCSVData.getMisMatch() + "币别不一致/");
                }
                break;

            case "CHF":
                currencyCanRead = true;
                if (!inputCSVData.getCustomsDeclarationData().getCurrencyType().equals("瑞士法郎")) {
                    inputCSVData.setMisMatch(inputCSVData.getMisMatch() + "币别不一致/");
                }
                break;

            case "SEK":
                currencyCanRead = true;
                if (!inputCSVData.getCustomsDeclarationData().getCurrencyType().equals("瑞典克朗")) {
                    inputCSVData.setMisMatch(inputCSVData.getMisMatch() + "币别不一致/");
                }
                break;

            case "NZD":
                currencyCanRead = true;
                if (!inputCSVData.getCustomsDeclarationData().getCurrencyType().equals("新西兰元")) {
                    inputCSVData.setMisMatch(inputCSVData.getMisMatch() + "币别不一致/");
                }
                break;

            default:
        }
        if (!currencyCanRead) {
            inputCSVData.setMisMatch(inputCSVData.getMisMatch() + "币别不一致/");
        }
//        if (inputCSVData.getCustomsDeclarationData().getTransactionMethod().equals("CIF") && inputCSVData.getCustomsDeclarationData().getFreight() != 0) {
//            inputCSVData.setCustomsLogicWrong(inputCSVData.getCustomsLogicWrong() + "成交方式与运费不匹配/");
//        }
//        if (!inputCSVData.getCustomsDeclarationData().getTransactionMethod().equals("CIF") && inputCSVData.getCustomsDeclarationData().getFreight() == 0) {
//            inputCSVData.setCustomsLogicWrong(inputCSVData.getCustomsLogicWrong() + "成交方式与运费不匹配/");
//        }
        return inputCSVData;
    }

    public String getSubmissionId(String submissionIdLine) {
        return submissionIdLine.substring(14);
    }

    public String getName(String nameLine) {
        return nameLine.substring(3);
    }

    public String getFinalGrade(String gradeLine) {
        String[] grade = gradeLine.split("/");
        return grade[0];
    }

    public CSVBuilder getCsvBuilder() {
        return this.csvBuilder;
    }

    public List<String[]> getAllSumbissionData() {
        return this.allSumbissionData;
    }

    public List<InputCSVData> getAllInputCSVData() {
        return this.allInputCSVData;
    }
}