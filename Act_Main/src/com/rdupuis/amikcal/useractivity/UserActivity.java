package com.rdupuis.amikcal.useractivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.useractivitycomponent.UserActivityComponent;
import components.Component;
import components.ComponentFood;

public  class UserActivity implements EditableUA{
	public enum UA_CLASS_CD {
		LUNCH, WEIGHT, MOVE
	}

	private long _id;
	public Calendar day;
	private String title;
	public UA_CLASS_CD type; // moving / eating / cooking
	
	
	public Date last_update;
    //la liste des composants est une liste d'objets qui héritent de Component
	private ArrayList<? extends Component> mComponentsList;

	public UserActivity() {
		// Par defaut
		this.day = Calendar.getInstance();
		this.title = "";
		this._id = AppConsts.NO_ID;
		this.setComponentsList(new ArrayList<Component>());
	}

	
	public UserActivity(Calendar day) {
		// Par defaut
		this.day = day;
		this.title = "";
		this._id = AppConsts.NO_ID;
		this.setComponentsList(new ArrayList<Component>());
	}

	
	public long getId() {
		return _id;
	}

	public void setId(long _id) {
		this._id = _id;
	}

	public UA_CLASS_CD getType() {
		return type;
	}

	public void setType(UA_CLASS_CD type) {
		this.type = type;
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

	public ArrayList<Component> getComponentsList() {
		return (ArrayList<Component>) this.mComponentsList;
	}

	public void setComponentsList(ArrayList<? extends Component> arrayList) {
		this.mComponentsList = arrayList;
	}

}
