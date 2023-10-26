package com.example.banggaevcs;

public class DatabaseData {
    private String csid;
    private String time;
    private String token;
    public DatabaseData(String csid, String time, String token) {
        this.csid = csid;
        this.time = time;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
