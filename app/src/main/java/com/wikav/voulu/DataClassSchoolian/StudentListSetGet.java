package com.wikav.voulu.DataClassSchoolian;

public class StudentListSetGet {

    String name , subject;
    int image;
    public StudentListSetGet(String name, int image, String subject) {
        this.name = name;
        this.image = image;
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
