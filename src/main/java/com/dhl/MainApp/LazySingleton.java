package com.dhl.MainApp;

import org.apache.poi.hslf.record.CString;

import java.util.ArrayList;
import java.util.List;

public class LazySingleton {
    private static LazySingleton lazySingleton;

    private LazySingleton() {

    }

    public static LazySingleton getInstance() {
        if (lazySingleton == null) {
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }

    public List<String> getM_NumberList() {
        return m_NumberList;
    }

    public void setM_NumberList(List<String> m_NumberList) {
        this.m_NumberList = m_NumberList;
    }

    private List<String> m_NumberList = new ArrayList<>();

    private int okcount = 0;
    private int ngcount = 0;


    public int getOkcount() {
        return okcount;
    }

    public void AddOkcount() {
        this.okcount = okcount + 1;
    }

    public int getNgcount() {
        return ngcount;
    }

    public void AddNgcount() {
        this.ngcount = ngcount + 1;
    }

    public String filePathWrongNum = "";
    public String filePathblockNum = "";
}