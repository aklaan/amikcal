package com.rdupuis.amikcal.useractivity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import com.rdupuis.amikcal.commons.ManagedElement;
import com.rdupuis.amikcal.components.Component;

public abstract class UserActivity implements ManagedElement,Parcelable{



    public abstract long getDatabaseId();

    public abstract void setDatabaseId(long _id);

    public abstract UA_CLASS_CD getActivityType();

    public abstract String getTitle();

    public abstract void setTitle(String title);

    public abstract Calendar getDay();

    public abstract void setDay(Calendar day);

    public abstract void setDay(int year, int month, int day, int hourOfDay, int minute);

    public abstract ArrayList<Component> getComponentsList();

    public abstract void setComponentsList(ArrayList<Component> arrayList);




}
