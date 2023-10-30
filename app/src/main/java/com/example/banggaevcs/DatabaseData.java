package com.example.banggaevcs;

public class DatabaseData {
    private String csid;
    private String time;

    public DatabaseData(String csid, String time) {
        this.csid = csid;
        this.time = time;
    }

    public String getCsid() {
        return csid;
    }

    public void setCsid(String csid) {
        this.csid = csid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
