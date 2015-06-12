package com.rdupuis.amikcal.useractivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.components.Component_Food;

public abstract class UserActivity {

    
    // la liste des composants est une liste d'objets qui héritent de Component
    //private ArrayList<? extends Component> mComponentsList;

    
    public abstract long getId();

    public abstract void setId(long _id);

    public abstract UA_CLASS_CD getType();

    public abstract String getTitle();

    public abstract void setTitle(String title);

    public abstract Calendar getDay();

    public abstract void setDay(Calendar day);

    public abstract void setDay(int year, int month, int day, int hourOfDay, int minute);

    public abstract ArrayList<? extends Component> getComponentsList();

    public abstract void setComponentsList(ArrayList<? extends Component> arrayList);

}
