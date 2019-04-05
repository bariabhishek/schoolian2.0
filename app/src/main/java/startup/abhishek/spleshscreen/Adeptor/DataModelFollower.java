package startup.abhishek.spleshscreen.Adeptor;

public class DataModelFollower {
    private int photofollower;
    private String namefollower;
    private String followerstatus;
    private String folloer;

    public DataModelFollower(int photofollower, String namefollower, String followerstatus, String folloer) {
        this.photofollower = photofollower;
        this.namefollower = namefollower;
        this.followerstatus = followerstatus;
        this.folloer = folloer;
    }

    public int getPhotofollower() {
        return photofollower;
    }

    public void setPhotofollower(int photofollower) {
        this.photofollower = photofollower;
    }

    public String getNamefollower() {
        return namefollower;
    }

    public void setNamefollower(String namefollower) {
        this.namefollower = namefollower;
    }

    public String getFollowerstatus() {
        return followerstatus;
    }

    public void setFollowerstatus(String followerstatus) {
        this.followerstatus = followerstatus;
    }

    public String getFolloer() {
        return folloer;
    }

    public void setFolloer(String folloer) {
        this.folloer = folloer;
    }
}
