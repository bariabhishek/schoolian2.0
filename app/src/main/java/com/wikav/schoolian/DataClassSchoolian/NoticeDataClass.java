package com.wikav.schoolian.DataClassSchoolian;

public class NoticeDataClass {
    private String date;
    private String notice;
    private String noticeTitle;

    public NoticeDataClass(String date, String notice, String noticeTitle) {
        this.date = date;
        this.noticeTitle = noticeTitle;
        this.notice = notice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }
}
