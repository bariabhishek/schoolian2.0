package com.wikav.voulu.Adeptor;

public class ModelList {

    String Image;
    String title;
    String dis;
    String pese;
    String id;
    String time;
    String mobile;
    String like;
    String profilePic;
    String username;
    String status;
    String img2,img3;

    public ModelList(String image, String title, String dis, String pese, String id, String time, String mobile, String like,
                     String profilePic, String username, String status, String img2, String img3)
    {
        Image = image;
        this.title = title;
        this.dis = dis;
        this.pese = pese;
        this.id = id;
        this.time = time;
        this.mobile = mobile;
        this.like = like;
        this.profilePic = profilePic;
        this.username = username;
        this.status = status;
        this.img2 = img2;
        this.img3 = img3;
    }

    public String getImg2() {
        return img2;
    }

    public String getImg3() {
        return img3;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
