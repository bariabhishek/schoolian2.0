package startup.abhishek.spleshscreen.Adeptor;

public class DataForNotification {
    private String notiImage;
    private String notification;
    private String time;

    public DataForNotification(String  notiImage, String notification, String time) {
        this.notiImage = notiImage;
        this.notification = notification;
        this.time = time;
    }

    public String getNotiImage() {
        return notiImage;
    }

    public void setNotiImage(String  notiImage) {
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
