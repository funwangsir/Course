package com.course.model;

public class Teacourse extends TeacourseKey {
    private String coursestate;

    public String getCoursestate() {
        return coursestate;
    }

    public void setCoursestate(String coursestate) {
        this.coursestate = coursestate == null ? null : coursestate.trim();
    }
}