package com.rdupuis.amikcal.useractivity;

import java.util.ArrayList;
import java.util.Calendar;

import com.rdupuis.amikcal.commons.ManagedElement;
import com.rdupuis.amikcal.components.Component;

public abstract class UserActivity implements ManagedElement{


    // la liste des composants est une liste d'objets qui h�ritent de Component
    //private ArrayList<? extends Component> mComponentsList;


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
