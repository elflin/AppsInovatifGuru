package com.uc.appsinovatifguru.Model;

public class User {

    private String id, name, email, password,
    jenis_kelamin, status_pernikahan, asal_sekolah, jenjang_mengajar,
    mata_pelajaran, pendidikan;

    private int usia, jumlah_anak, lama_mengajar;

    private boolean isIlmuPendidikan;

    public User() {
    }

    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getStatus_pernikahan() {
        return status_pernikahan;
    }

    public void setStatus_pernikahan(String status_pernikahan) {
        this.status_pernikahan = status_pernikahan;
    }

    public String getAsal_sekolah() {
        return asal_sekolah;
    }

    public void setAsal_sekolah(String asal_sekolah) {
        this.asal_sekolah = asal_sekolah;
    }

    public String getJenjang_mengajar() {
        return jenjang_mengajar;
    }

    public void setJenjang_mengajar(String jenjang_mengajar) {
        this.jenjang_mengajar = jenjang_mengajar;
    }

    public String getMata_pelajaran() {
        return mata_pelajaran;
    }

    public void setMata_pelajaran(String mata_pelajaran) {
        this.mata_pelajaran = mata_pelajaran;
    }

    public String getPendidikan() {
        return pendidikan;
    }

    public void setPendidikan(String pendidikan) {
        this.pendidikan = pendidikan;
    }

    public int getUsia() {
        return usia;
    }

    public void setUsia(int usia) {
        this.usia = usia;
    }

    public int getJumlah_anak() {
        return jumlah_anak;
    }

    public void setJumlah_anak(int jumlah_anak) {
        this.jumlah_anak = jumlah_anak;
    }

    public int getLama_mengajar() {
        return lama_mengajar;
    }

    public void setLama_mengajar(int lama_mengajar) {
        this.lama_mengajar = lama_mengajar;
    }

    public boolean isIlmuPendidikan() {
        return isIlmuPendidikan;
    }

    public void setIlmuPendidikan(boolean ilmuPendidikan) {
        isIlmuPendidikan = ilmuPendidikan;
    }
}
