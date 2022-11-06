package com.example.ezapp3;

public class PlaceItem {
    String statNm;
    String chgerType;
    String addr;
    String useTime;
    String stat;
    String statUpdDt;

    public PlaceItem(String statNm, String chgerType, String addr, String useTime, String stat, String statUpdDt) {
        this.statNm = statNm;
        this.chgerType = chgerType;
        this.addr = addr;
        this.useTime = useTime;
        this.stat = stat;
        this.statUpdDt = statUpdDt;
    }

    public void setStatNm(String statNm) {
        this.statNm = statNm;
    }

    public void setChgerType(String chgerType) {
        this.chgerType = chgerType;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public void setStatUpdDt(String statUpdDt) {
        this.statUpdDt = statUpdDt;
    }

    public String getStatNm() {
        return statNm;
    }

    public String getChgerType() {
        return chgerType;
    }

    public String getAddr() {
        return addr;
    }

    public String getUseTime() {
        return useTime;
    }

    public String getStat() {
        return stat;
    }

    public String getStatUpdDt() {
        return statUpdDt;
    }
}



