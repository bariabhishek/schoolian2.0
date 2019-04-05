package startup.abhishek.spleshscreen.Adeptor;

public class DataForNotification {
    private int notiImage;
    private String notification;
    private String time;

    public DataForNotification(int notiImage, String notification, String time) {
        this.notiImage = notiImage;
        this.notification = notification;
        this.time = time;
    }

    public int getNotiImage() {
        return notiImage;
    }

    public void setNotiImage(int notiImage) {
        this.notiImage = notiImage;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
