package startup.abhishek.spleshscreen.Adeptor;

public class CommentModel {
    public String comment_id;
    public String comment;
    public String username;
    public String userpic;
    public String time;

    public CommentModel() {
    }

    public CommentModel(String comment_id, String comment, String username, String userpic, String time) {
        this.comment_id = comment_id;
        this.comment = comment;
        this.username = username;
        this.userpic = userpic;
        this.time = time;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
