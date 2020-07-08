package com.example.findyourlecturer;

public class user {
    private String namalengkap;
    private String email;
    private String password;
    private String noidentitas;
    private String user;
    private String key;
    private String tgljam;
    private String token;
    private String nilai;
    private String pencari;
    private String tipeuser;
    private String tahunmasuk;
    private String tahunkeluar;


    public user() {
    }

    public user(String tahunmasuk, String tahunkeluar) {
        this.tahunmasuk = tahunmasuk;
        this.tahunkeluar = tahunkeluar;
    }

    public user(String tipeuser) {
        this.tipeuser = tipeuser;
    }

    public user(String token, String nilai, String pencari) {
        this.token = token;
        this.nilai = nilai;
        this.pencari = pencari;
    }

    public user(String namalengkap, String email, String password, String noidentitas, String user, String key, String tgljam) {
        this.namalengkap = namalengkap;
        this.email = email;
        this.password = password;
        this.noidentitas = noidentitas;
        this.user = user;
        this.key = key;
        this.tgljam = tgljam;

    }

    public String getTahunmasuk() {
        return tahunmasuk;
    }

    public void setTahunmasuk(String tahunmasuk) {
        this.tahunmasuk = tahunmasuk;
    }

    public String getTahunkeluar() {
        return tahunkeluar;
    }

    public void setTahunkeluar(String tahunkeluar) {
        this.tahunkeluar = tahunkeluar;
    }

    public String getTipeuser() {
        return tipeuser;
    }

    public void setTipeuser(String tipeuser) {
        this.tipeuser = tipeuser;
    }

    public String getPencari() {
        return pencari;
    }

    public void setPencari(String pencari) {
        this.pencari = pencari;
    }

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setNamalengkap(String namalengkap) {
        this.namalengkap = namalengkap;
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

    public String getNoidentitas() {
        return noidentitas;
    }

    public String getNamalengkap() {

        return namalengkap;
    }

    public void setNoidentitas(String noidentitas) {
        this.noidentitas = noidentitas;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTgljam() {
        return tgljam;
    }

    public void setTgljam(String tgljam) {
        this.tgljam = tgljam;
    }
}
