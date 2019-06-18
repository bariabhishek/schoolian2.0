package com.wikav.schoolian.DataClassSchoolian;

public class Evants_holidays_SetGet {
String color,holidayname,holidaysdate;

    public Evants_holidays_SetGet(String color, String holidayname, String holidaysdate) {
        this.color = color;
        this.holidayname = holidayname;
        this.holidaysdate = holidaysdate;
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
}
