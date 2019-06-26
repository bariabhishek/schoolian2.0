package com.wikav.schoolian.DataClassSchoolian;

public class Evants_holidays_SetGet {
String color,holidayname,holidaysdate,fromDate,toDate;

    public Evants_holidays_SetGet(String color, String holidayname, String fromDate,String toDate) {
        this.color = color;
        this.holidayname = holidayname;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHolidayname() {
        return holidayname;
    }

    public void setHolidayname(String holidayname) {
        this.holidayname = holidayname;
    }

    public String getHolidaysdate() {
        return holidaysdate;
    }

    public void setHolidaysdate(String holidaysdate) {
        this.holidaysdate = holidaysdate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
