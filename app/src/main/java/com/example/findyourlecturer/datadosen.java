package com.example.findyourlecturer;

public class datadosen {
    private String nama;
    private String nip;

    public datadosen(String nama, String nip) {
        this.nama = nama;
        this.nip = nip;
    }

    public datadosen() {
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }
}
