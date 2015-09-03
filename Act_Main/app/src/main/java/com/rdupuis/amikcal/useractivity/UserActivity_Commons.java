package com.rdupuis.amikcal.useractivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.components.Component;

public class UserActivity_Commons extends UserActivity {

    private long databaseId;
    public Calendar day;
    private String title;
    public Date last_update;
    private ArrayList<Component> mArrayListComponent;

    public UserActivity_Commons() {
        this.day = Calendar.getInstance();
        this.title = "";
        this.databaseId = AppConsts.NO_ID;
        this.mArrayListComponent = new ArrayList<Component>();

    }

    public UserActivity_Commons(Calendar day) {
        this.day = Calendar.getInstance();
        this.title = "";
        this.databaseId = AppConsts.NO_ID;
    }

    public long getDatabaseId() {
        return this.databaseId;
    }

    public void setDatabaseId(long _id) {
        this.databaseId = _id;
    }

    public Date getLast_update() {
        return last_update;
    }

    public void setLast_update(Date last_update) {
        this.last_update = last_update;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getDay() {
        return day;
    }

    public void setDay(Calendar day) {
        this.day = day;
    }

    public void setDay(int year, int month, int day, int hourOfDay, int minute) {
        this.day.set(year, month, day, hourOfDay, minute);
    }

    @Override
    public UA_CLASS_CD getActivityType() {
        return UA_CLASS_CD.USER_ACTIVITY;
    }

    @Override
    public ArrayList<Component> getComponentsList() {
        return mArrayListComponent;
    }

    @Override
    public void setComponentsList(ArrayList<Component> arrayList) {
        mArrayListComponent = arrayList;

    }

}
