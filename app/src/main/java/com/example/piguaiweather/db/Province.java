package com.example.piguaiweather.db;

import org.litepal.crud.DataSupport;

/**
 * @author Richard_Y_Wang
 * @version $Rev$
 * @des 2018/3/30
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class Province extends DataSupport {
    private int id;
    private String provinceName;
    private int provinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
