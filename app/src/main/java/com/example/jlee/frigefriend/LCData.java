package com.example.jlee.frigefriend;

public class LCData {
    private int LCID;
    private String name;

    public LCData(int LCID, String name) {
        this.LCID = LCID;
        this.name = name;
    }

    public int getLCID() {
        return LCID;
    }

    public void setLCID(int LCID) {
        this.LCID = LCID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "{\"LCID\" : " + LCID + ", \"name\" : " + name+ "}";
    }
}
