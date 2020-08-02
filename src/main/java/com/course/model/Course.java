package com.course.model;

public class Course {
    private String courseid;

    private String coursename;

    private String courseclass;

    private String courseyear;

    private String courseterm;

    private Integer membercount;

    private Boolean operatemodel;

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid == null ? null : courseid.trim();
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename == null ? null : coursename.trim();
    }

    public String getCourseclass() {
        return courseclass;
    }

    public void setCourseclass(String courseclass) {
        this.courseclass = courseclass == null ? null : courseclass.trim();
    }

    public String getCourseyear() {
        return courseyear;
    }

    public void setCourseyear(String courseyear) {
        this.courseyear = courseyear == null ? null : courseyear.trim();
    }

    public String getCourseterm() {
        return courseterm;
    }

    public void setCourseterm(String courseterm) {
        this.courseterm = courseterm == null ? null : courseterm.trim();
    }

    public Integer getMembercount() {
        return membercount;
    }

    public void setMembercount(Integer membercount) {
        this.membercount = membercount;
    }

    public Boolean getOperatemodel() {
        return operatemodel;
    }

    public void setOperatemodel(Boolean operatemodel) {
        this.operatemodel = operatemodel;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseid='" + courseid + '\'' +
                ", coursename='" + coursename + '\'' +
                ", courseclass='" + courseclass + '\'' +
                ", courseyear='" + courseyear + '\'' +
                ", courseterm='" + courseterm + '\'' +
                ", membercount=" + membercount +
                ", operatemodel=" + operatemodel +
                '}';
    }
}