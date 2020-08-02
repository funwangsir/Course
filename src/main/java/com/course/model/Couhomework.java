package com.course.model;

public class Couhomework extends CouhomeworkKey {
    private String submitstate;

    private Integer piyuecount;

    private Double score;

    public String getSubmitstate() {
        return submitstate;
    }

    public void setSubmitstate(String submitstate) {
        this.submitstate = submitstate == null ? null : submitstate.trim();
    }

    public Integer getPiyuecount() {
        return piyuecount;
    }

    public void setPiyuecount(Integer piyuecount) {
        this.piyuecount = piyuecount;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}