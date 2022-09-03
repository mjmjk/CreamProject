package com.example.mjk.creamproject;

/**
 * Created by mjk on 2017-10-29.
 */

public class DealItem {

    String date;
    String dept;
    String name;
    int cream;
    boolean opt;

    public DealItem(String date, String dept, String name, int cream, boolean opt) {
        this.date=date;
        this.dept=dept;
        this.name=name;
        this.cream=cream;
        this.opt=opt;
    }

    public boolean isOpt() {
        return opt;
    }

    public void setOpt(boolean opt) {
        this.opt = opt;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCream() {
        return cream;
    }

    public void setCream(int cream) {
        this.cream = cream;
    }


}
