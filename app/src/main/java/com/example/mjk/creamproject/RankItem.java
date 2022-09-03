package com.example.mjk.creamproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RankItem {

    String rank;
    String dept;
    String name;
    int cream;

    public RankItem(String rank, String dept, String name, int cream) {
        this.rank=rank;
        this.dept=dept;
        this.name=name;
        this.cream=cream;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String Rank) {this.rank = rank;}

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
