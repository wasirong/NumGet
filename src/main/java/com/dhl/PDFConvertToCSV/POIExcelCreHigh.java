package com.dhl.PDFConvertToCSV;

import com.dhl.Data.SHERLOCData;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

public class POIExcelCreHigh {

    public SHERLOCData getHWBContent(String m_waybillNumber, String m_path) {
        //创建Excel，读取文件内容
        File file = new File(m_path);
        XSSFWorkbook workbook;
        SHERLOCData sherlocData = new SHERLOCData();

        try {
            workbook = new XSSFWorkbook(FileUtils.openInputStream(file));
            //两种方式读取工作表
            Sheet m_shipment = workbook.getSheet("Shipment");
//            Sheet sheet = workbook.getSheetAt(0);
            //获取sheet中最后一行行号
            int lastRowNum = m_shipment.getLastRowNum();

            for (int i = 0; i <= lastRowNum; i++) {
                Row row = m_shipment.getRow(i);
//                System.out.println("行号:" + i + "类型:" +row.getCell(2).getCellType().toString());
                String m_sdCount = "";
                if (row.getCell(2).getCellType().toString().equals("NUMERIC")){
                    m_sdCount = String.valueOf(row.getCell(2).getNumericCellValue());
                }else {
                    m_sdCount = row.getCell(2).getStringCellValue();
                }
                if (row.getCell(0).getStringCellValue().equals(m_waybillNumber) && m_sdCount.contains("1")) {
//                    System.out.println("运单号:" + row.getCell(0).getStringCellValue() + "实际:" + m_waybillNumber);

                    // 件数_SHERLOC
                    sherlocData.setCountSherloc(String.valueOf(row.getCell(11).getNumericCellValue()));

                    // 总价_SHERLOC
                    sherlocData.setTotalValue(new BigDecimal(String.valueOf(row.getCell(40).getNumericCellValue())));

                    // 币别
                    sherlocData.setCurrencyTypeSherloc(row.getCell(41).getStringCellValue());
                }
//                Row row = sheet.getRow(i);
//                //获取当前行最后单元格列号
//                int lastCellNum = row.getLastCellNum();
//                for (int j = 0; j < lastCellNum; j++) {
//                    Cell cell = row.getCell(j);
//                    String value = cell.getStringCellValue();
//                    System.out.print(value + " ");
//                }
//                System.out.println();
            }

            Sheet m_event = workbook.getSheet("Event");

            //获取sheet中最后一行行号
            int lastRowNumEvent = m_event.getLastRowNum();

            BigDecimal m_weight = new BigDecimal(0);

            for (int i = 0; i <= lastRowNumEvent; i++) {
                Row row = m_event.getRow(i);
//                System.out.println("运单号:" + row.getCell(0).getStringCellValue()
//                        + "6:" + row.getCell(5).getStringCellValue()
//                        + "7:" + row.getCell(6).getStringCellValue()
//                        + "8:" + row.getCell(7).getStringCellValue());

                if (row.getCell(0).getStringCellValue().equals(m_waybillNumber)
                        && row.getCell(5).getStringCellValue().equals("DLC")
                        && row.getCell(6).getStringCellValue().equals("DLW")
                        && row.getCell(7).getStringCellValue().equals("RW")) {

                    String tempWeight = String.valueOf(row.getCell(11).getStringCellValue());
                    if (tempWeight.contains("> <")){
                        tempWeight = tempWeight.substring(1, tempWeight.indexOf("> <"));
                    }else {
                        tempWeight = tempWeight.substring(1, tempWeight.indexOf(">"));
                    }
                    m_weight = m_weight.add(new BigDecimal(tempWeight));
                }
            }
            sherlocData.setWeightSherloc(String.valueOf(m_weight));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sherlocData;
    }
}
