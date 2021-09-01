package com.uc.appsinovatifguru.Model;

public class Soal {

    private int id;
    private String no_item, variabel, soal, dimensi, ukuran;
    private int nilai;

    public Soal() {
    }

    public Soal(int id, String no_item, String variabel, String soal, String dimensi, String ukuran, int nilai) {
        this.id = id;
        this.no_item = no_item;
        this.variabel = variabel;
        this.soal = soal;
        this.dimensi = dimensi;
        this.ukuran = ukuran;
        this.nilai = nilai;
    }

    public int getNilai() {
        return nilai;
    }

    public void setNilai(int nilai) {
        this.nilai = nilai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNo_item() {
        return no_item;
    }

    public void setNo_item(String no_item) {
        this.no_item = no_item;
    }

    public String getVariabel() {
        return variabel;
    }

    public void setVariabel(String variabel) {
        this.variabel = variabel;
    }

    public String getSoal() {
        return soal;
    }

    public void setSoal(String soal) {
        this.soal = soal;
    }

    public String getDimensi() {
        return dimensi;
    }

    public void setDimensi(String dimensi) {
        this.dimensi = dimensi;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }
}
