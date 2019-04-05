package startup.abhishek.spleshscreen.Adeptor;

public class ModelList {

    int Image;
    String title;

    public int getImage() {
        return Image;
    }

    public ModelList(int image, String title, String dis, String pese) {
        Image = image;
        this.title = title;
        this.dis = dis;
        this.pese = pese;
    }

    public void setImage(int image) {
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

    String dis;
    String pese;
}
