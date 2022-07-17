package com.uc.appsinovatifguru.Model;

public class Eval {
    private String pertanyaan;
    private String jawaban;

    public Eval(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }
}
