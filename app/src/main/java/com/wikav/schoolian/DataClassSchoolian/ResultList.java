package com.wikav.schoolian.DataClassSchoolian;

public class ResultList {

    String subject,marks,grads;
    int length;

    public ResultList(String subject, String marks, String grads, int i) {
        this.subject = subject;
        this.marks = marks;
        this.grads = grads;
        length=i;
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

    public int getLength() {
        return length;
    }
}
