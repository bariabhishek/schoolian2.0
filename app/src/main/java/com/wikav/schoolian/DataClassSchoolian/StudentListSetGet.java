package com.wikav.schoolian.DataClassSchoolian;

public class StudentListSetGet {

    String name , roll,mobile;
    String  image;
    public StudentListSetGet(String name, String  image, String roll, String mobile) {
        this.name = name;
        this.image = image;
        this.roll = roll;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String  getImage() {
        return image;
    }

    public void setImage(String  image) {
        this.image = image;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getMobile() {
        return mobile;
    }
}
