package com.example.heremiStartup;

public class modelTokens {
    private String access;
    private String refresh;

    public modelTokens() {
    }

    public modelTokens(String access, String refresh) {
        this.access = access;
        this.refresh = refresh;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    @Override
    public String toString() {
        return "modelTokens{" +
                "access='" + access + '\'' +
                ", refresh='" + refresh + '\'' +
                '}';
    }
}
