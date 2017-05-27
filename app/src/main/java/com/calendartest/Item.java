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
    private String Color;

    // 預設建構子
    public Item() {

    }

    public Item(long id, String name, String location, String date_from, String date_to,
                String time_from, String time_to, String repeatFrequency,
                String privacy, String description, String remind, String color) {
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
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getLocation() {
        return this.Location;
    }

    public void setLocation(String location) {
        this.Location = location;
    }

    public String getDate_from() {
        return this.Date_from;
    }

    public void setDate_from(String date_from) {
        this.Date_from = date_from;
    }

    public String getDate_to() {
        return this.Date_to;
    }

    public void setDate_to(String date_to) {
        this.Date_to = date_to;
    }

    public String getTime_from() {
        return this.Time_from;
    }

    public void setTime_from(String time_from) {
        this.Time_from = time_from;
    }

    public String getTime_to() {
        return this.Time_to;
    }

    public void setTime_to(String time_to) {
        this.Time_to = time_to;
    }

    public String getRepeatFrequency() {
        return this.RepeatFrequency;
    }

    public void setRepeatFrequency(String repeatFrequency) {
        this.RepeatFrequency = repeatFrequency;
    }

    public String getPrivacy() {
        return this.Privacy;
    }

    public void setPrivacy(String privacy) {
        this.Privacy = privacy;
    }

    public String getRemind() {
        return this.Remind;
    }

    public void setRemind(String remind) {
        this.Remind = remind;
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getColor() {
        return this.Color;
    }

    public void setColor(String color) {
        this.Color = color;
    }
}
