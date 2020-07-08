package com.example.findyourlecturer;

public class datamhs {
    private String nama;
    private String nim;

    public datamhs(String nama, String nim) {
        this.nama = nama;
        this.nim = nim;
    }

    public datamhs() {
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }
}
