package com.example.findyourlecturer.Model;


public class ShowDosen {

    private String ssid;
    private String key;
    private String tgljam;


    public ShowDosen() {
    }

    public ShowDosen(String longitude, String latidude, String status, String ssid) {
        this.ssid = ssid;
        this.ssid = key;
        this.ssid = tgljam;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTgljam() {
        return tgljam;
    }

    public void setTgljam(String tgljam) {
        this.tgljam = tgljam;
    }
}
