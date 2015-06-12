package com.rdupuis.amikcal.useractivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.components.Component_Food;

public class UserActivity_Commons extends UserActivity {

    private long _id;
    public Calendar day;
    private String title;
    public Date last_update;
    

    public UserActivity_Commons() {
	this.day = Calendar.getInstance();
	this.title = "";
	this._id = AppConsts.NO_ID;

	
    }

    public UserActivity_Commons(Calendar day) {
	this.day = Calendar.getInstance();
	this.title = "";
	this._id = AppConsts.NO_ID;
    }

    

public long getId() {
	return _id;
}

public void setId(long _id) {
	this._id = _id;
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
public UA_CLASS_CD getType() {
    // TODO Auto-generated method stub
    return null;
}

@Override
public ArrayList<? extends Component> getComponentsList() {
    // TODO Auto-generated method stub
    return null;
}

@Override
public void setComponentsList(ArrayList<? extends Component> arrayList) {
    // TODO Auto-generated method stub
    
}




}
