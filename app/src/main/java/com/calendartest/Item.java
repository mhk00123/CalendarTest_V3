package com.calendartest;

import java.io.Serializable;

/**
 * Created by user on 2017/5/26.
 */

public class Item implements Serializable {
    private long id;
    private String Name;
    private String Location;
    private String Date_from, Date_to;
    private String Time_from, Time_to;
    private String RepeatFrequency;
    private String Privacy;
    private String Description;
    private String Remind;
    private int Color;

    // 預設建構子
    public Item() {

    }

    public Item(long id, String name, String location, String date_from, String date_to,
                String time_from, String time_to, String repeatFrequency,
                String privacy, String description, String remind, int color) {
        this.id = id;
        this.Name = name;
        this.Location = location;
        this.Date_from = date_from;
        this.Date_to = date_to;
        this.Time_from = time_from;
        this.Time_to = time_to;
        this.RepeatFrequency = repeatFrequency;
        this.Privacy = privacy;
        this.Description = description;
        this.Remind = remind;
        this.Color = color;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return Name == null ? "" : Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location == null ? "" : Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDate_from() {
        return Date_from == null ? "" : Date_from;
    }

    public void setDate_from(String date_from) {
        Date_from = date_from;
    }

    public String getDate_to() {
        return Date_to == null ? "" : Date_from;
    }

    public void setDate_to(String date_to) {
        Date_to = date_to;
    }

    public String getTime_from() {
        return Time_from == null ? "" : Time_from;
    }

    public void setTime_from(String time_from) {
        Time_from = time_from;
    }

    public String getTime_to() {
        return Time_to == null ? "" : Time_to;
    }

    public void setTime_to(String time_to) {
        Time_to = time_to;
    }

    public String getRepeatFrequency() {
        return RepeatFrequency == null ? "" : RepeatFrequency;
    }

    public void setRepeatFrequency(String repeatFrequency) {
        RepeatFrequency = repeatFrequency;
    }

    public String getPrivacy() {
        return Privacy == null ? "" : Privacy;
    }

    public void setPrivacy(String privacy) {
        Privacy = privacy;
    }

    public String getRemind() {
        return Remind == null ? "" : Remind;
    }

    public void setRemind(String remind) {
        Remind = remind;
    }

    public String getDescription() {
        return Description == null ? "" : Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getColor() {
        return Color;
    }

    public void setColor(int color) {
        Color = color;
    }
}
