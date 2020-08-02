package com.course.model;

import java.util.Date;

public class Stuhomework extends StuhomeworkKey {

    private Date submitdate;

    private String message;

    private String submitstate;

    private Integer submitcount;

    private String score;

    public Date getSubmitdate() {
        return submitdate;
    }

    public void setSubmitdate(Date submitdate) {
        this.submitdate = submitdate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getSubmitstate() {
        return submitstate;
    }

    public void setSubmitstate(String submitstate) {
        this.submitstate = submitstate == null ? null : submitstate.trim();
    }

    public Integer getSubmitcount() {
        return submitcount;
    }

    public void setSubmitcount(Integer submitcount) {
        this.submitcount = submitcount;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
    }

    @Override
    public String toString() {
        return "Stuhomework{" +
                "submitdate=" + submitdate +
                ", message='" + message + '\'' +
                ", submitstate='" + submitstate + '\'' +
                ", submitcount=" + submitcount +
                ", score='" + score + '\'' +
                '}';
    }
}