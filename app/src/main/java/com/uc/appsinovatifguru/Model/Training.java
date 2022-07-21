package com.uc.appsinovatifguru.Model;

public class Training {
    private String judul;
    private String downloadPdf;
    private String video;
    private String uploadPdf;
    private String templateWord;
    private String profilPeneliti;
    private boolean preTest;
    private boolean postTest;
    private boolean eval;

    public Training() {
        this.judul = null;
        this.downloadPdf = null;
        this.video = null;
        this.uploadPdf = null;
        this.templateWord = null;
        this.profilPeneliti = null;
        this.preTest = false;
        this.postTest = false;
        this.eval = false;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDownloadPdf() {
        return downloadPdf;
    }

    public void setDownloadPdf(String downloadPdf) {
        this.downloadPdf = downloadPdf;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getUploadPdf() {
        return uploadPdf;
    }

    public void setUploadPdf(String uploadPdf) {
        this.uploadPdf = uploadPdf;
    }

    public String getTemplateWord() {
        return templateWord;
    }

    public void setTemplateWord(String templateWord) {
        this.templateWord = templateWord;
    }

    public String getProfilPeneliti() {
        return profilPeneliti;
    }

    public void setProfilPeneliti(String profilPeneliti) {
        this.profilPeneliti = profilPeneliti;
    }

    public boolean isPreTest() {
        return preTest;
    }

    public void setPreTest(boolean preTest) {
        this.preTest = preTest;
    }

    public boolean isPostTest() {
        return postTest;
    }

    public void setPostTest(boolean postTest) {
        this.postTest = postTest;
    }

    public boolean isEval() {
        return eval;
    }

    public void setEval(boolean eval) {
        this.eval = eval;
    }
}
