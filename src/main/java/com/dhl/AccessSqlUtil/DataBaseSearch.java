package com.dhl.AccessSqlUtil;


import com.dhl.Data.CDMSData;

import java.sql.*;

public class DataBaseSearch {

//    Logger LOG = LoggerFactory.getLogger(DataBaseSearch.class);
    private static final String DBDRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DBURL = "jdbc:sqlserver://23.156.5.120:1433;DatabaseName=nao_cdms_import";
    private static final String USER = "naouser";//数据库用户名
    private static final String PASSWORD = "naouser123";//数据库密码

    public CDMSData GetCDMSData(String m_hWab) {
//        LOG.info("数据库查询方法执行开始");
        try {
            System.out.println("驱动程序开始加载");
            Class.forName(DBDRIVER);
            System.out.println("驱动程序已加载");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("驱动程序加载异常发生:" + e.getMessage());
        }
        CDMSData cdmsData = new CDMSData();
//        String user = "naouser";
//        String password = "naouser123";
        Connection conn;
        Statement stmt;
        ResultSet rs;
//        String url = "jdbc:microsoft:sqlserver://23.156.5.120:1433;DatabaseName=nao_cdms_import;";
        String sql = "select " +
                "TotalPiece,TotalKgWeight,FreightFeeDcc,CustomsValue,Currency " +
                "from " +
                "nao_cdms_import.dbo.EDIDeclaration " +
                "where Hawb = '" + m_hWab + "'";
        try {
//            LOG.info("User : " + user);
//            LOG.info("password : " + password);
//            LOG.info("连接数据库");
            System.out.println("连接数据库");
            // 连接数据库
//            conn = DriverManager.getConnection(url, user, password);
            conn = DriverManager.getConnection(DBURL,USER,PASSWORD);
            System.out.println(conn);//输出数据库连接
            System.out.println("建立Statement对象");
//            LOG.info("建立Statement对象");
            // 建立Statement对象
            stmt = conn.createStatement();
//            LOG.info("执行数据库查询语句 : " + sql);
            // 执行数据库查询语句
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                // 件数
                String m_totalPiece = rs.getString("TotalPiece");
//                LOG.info("件数 : " + m_totalPiece);
                // 毛重
                String m_totalKgWeight = rs.getString("TotalKgWeight");
//                LOG.info("毛重 : " + m_totalKgWeight);
                // 运费
                String m_freightFeeDcc = rs.getString("FreightFeeDcc");
//                LOG.info("运费 : " + m_freightFeeDcc);
                // 原始价值
                String m_customsValue = rs.getString("CustomsValue");
//                LOG.info("原始价值 : " + m_customsValue);
                // 币别
                String m_currency = rs.getString("Currency");
//                LOG.info("币别 : " + m_currency);

                cdmsData.setCountCDMS(m_totalPiece);

                cdmsData.setGrossWeightCDMS(Float.parseFloat(m_totalKgWeight));

//                cdmsData.setFreightCDMS(Float.parseFloat(String.format("%.2f", Double.parseDouble(m_freightFeeDcc.toString()))));

                cdmsData.setOriginalValueCDMS(Float.parseFloat(m_customsValue));

                cdmsData.setCurrencyTypeCDMS(m_currency);

            }
            if (rs != null) {
//                LOG.info("rs.close()");
                rs.close();
                rs = null;
            }
            if (stmt != null) {
//                LOG.info("stmt.close()");
                stmt.close();
                stmt = null;
            }
            if (conn != null) {
//                LOG.info("conn.close()");
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
//            LOG.info("数据库连接失败" + e.getMessage());
        }catch (Exception e){
//            LOG.info("数据库连接异常发生" + e.getMessage());
        }

        return cdmsData;
    }
}
