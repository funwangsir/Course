package com.course.model;

public class Stucourse extends StucourseKey {
    private String score;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
    }
}