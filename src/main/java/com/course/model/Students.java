package com.course.model;

public class Students {
    private String studentid;

    private String studentpassword;

    private String studentname;

    private String studentnumber;

    private String studentschool;

    private String role;

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid == null ? null : studentid.trim();
    }

    public String getStudentpassword() {
        return studentpassword;
    }

    public void setStudentpassword(String studentpassword) {
        this.studentpassword = studentpassword == null ? null : studentpassword.trim();
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname == null ? null : studentname.trim();
    }

    public String getStudentnumber() {
        return studentnumber;
    }

    public void setStudentnumber(String studentnumber) {
        this.studentnumber = studentnumber == null ? null : studentnumber.trim();
    }

    public String getStudentschool() {
        return studentschool;
    }

    public void setStudentschool(String studentschool) {
        this.studentschool = studentschool == null ? null : studentschool.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    @Override
    public String toString() {
        return "Students{" +
                "studentid='" + studentid + '\'' +
                ", studentpassword='" + studentpassword + '\'' +
                ", studentname='" + studentname + '\'' +
                ", studentnumber='" + studentnumber + '\'' +
                ", studentschool='" + studentschool + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}