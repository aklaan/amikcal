package com.rdupuis.amikcal.useractivity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ToolBox;
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


    public UserActivity_Commons(Parcel parcel) {
        this.databaseId = parcel.readLong();
        this.title = parcel.readString();
        this.day = ToolBox.parseCalendar(parcel.readString());
        //faire le reste des data
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.getDatabaseId());
        dest.writeString(this.getTitle());
        dest.writeString(ToolBox.getSqlDate(this.getDay()));

        //ajouter le reste
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<UserActivity_Commons> CREATOR = new Parcelable.Creator<UserActivity_Commons>() {
        @Override
        public UserActivity_Commons createFromParcel(Parcel in) {
            return new UserActivity_Commons(in);
        }

        @Override
        public UserActivity_Commons[] newArray(int size) {
            return new UserActivity_Commons[size];

        }

    };


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
