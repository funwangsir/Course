package com.course.model;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

public class Homework {
    private String homeworkid;

    private Date publishdate;

    private String homeworkname;

    private String homeworkintroduction;

    private Date deadline;

    private Integer maxscore;

    private Boolean operatemodel;

    public String getHomeworkid() {
        return homeworkid;
    }

    public void setHomeworkid(String homeworkid) {
        this.homeworkid = homeworkid == null ? null : homeworkid.trim();
    }

    public Date getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(Date publishdate) {
        this.publishdate = publishdate;
    }

    public String getHomeworkname() {
        return homeworkname;
    }

    public void setHomeworkname(String homeworkname) {
        this.homeworkname = homeworkname == null ? null : homeworkname.trim();
    }

    public String getHomeworkintroduction() {
        return homeworkintroduction;
    }

    public void setHomeworkintroduction(String homeworkintroduction) {
        this.homeworkintroduction = homeworkintroduction == null ? null : homeworkintroduction.trim();
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getMaxscore() {
        return maxscore;
    }

    public void setMaxscore(Integer maxscore) {
        this.maxscore = maxscore;
    }

    public Boolean getOperatemodel() {
        return operatemodel;
    }

    public void setOperatemodel(Boolean operatemodel) {
        this.operatemodel = operatemodel;
    }

    @Override
    public String toString() {
        return "Homework{" +
                "homeworkid='" + homeworkid + '\'' +
                ", publishdate=" + publishdate +
                ", homeworkname='" + homeworkname + '\'' +
                ", homeworkintroduction='" + homeworkintroduction + '\'' +
                ", deadline=" + deadline +
                ", maxscore=" + maxscore +
                ", operatemodel=" + operatemodel +
                '}';
    }
}