package com.wikav.schoolian.Adeptor;

public class DataOFComment {

    String Image;
    String title;
    String dis;
    String pese;
    String id;
    String time;
    String mobile;

    public DataOFComment(String image, String title, String dis, String pese, String id, String time, String mobile) {
        Image = image;
        this.title = title;
        this.dis = dis;
        this.pese = pese;
        this.id = id;
        this.time = time;
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getImage() {
        return Image;
    }


    public void setImage(String image) {
        Image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getPese() {
        return pese;
    }

    public void setPese(String pese) {
        this.pese = pese;
    }

}
