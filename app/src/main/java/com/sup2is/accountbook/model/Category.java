package com.sup2is.accountbook.model;

public class Category {

    private final int idx;
    private final int seq;
    private final int type;
    private final String name;

    public Category(int idx, int seq, int type, String name) {
        this.idx = idx;
        this.seq = seq;
        this.type = type;
        this.name = name;
    }


    public int getIdx() {
        return idx;
    }

    public int getSeq() {
        return seq;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
