package com.wikav.schoolian.DataClassSchoolian;

public class ClassTimeTableSetGet {
    String  image;
    String startTime,endTime,subject;

    public ClassTimeTableSetGet(String image, String startTime, String endTime, String subject) {
        this.image = image;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subject = subject;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
