package com.wikav.schoolian.DataClassSchoolian;

public class TeacherSetGet {
    String image;
    String TeacherName, subject,tId;

    public TeacherSetGet(String tId,String image, String teacherName, String subject) {
        this.image = image;
        this.tId = tId;
        TeacherName = teacherName;
        this.subject = subject;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }
}