package com.wikav.voulu.DataClassSchoolian;

public class AttendanceSetGet {

    String subject,marks,grads;

    public AttendanceSetGet(String subject, String marks, String grads) {
        this.subject = subject;
        this.marks = marks;
        this.grads = grads;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getGrads() {
        return grads;
    }

    public void setGrads(String grads) {
        this.grads = grads;
    }
}
