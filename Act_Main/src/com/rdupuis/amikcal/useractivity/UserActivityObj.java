package com.rdupuis.amikcal.useractivity;

import java.util.Calendar;
import java.util.Date;

import com.rdupuis.amikcal.commons.WeightObj;



import android.graphics.Picture;

public class UserActivityObj {

private  Long     _id; 
private  Calendar day;
private  String   title;
private  int      type; // moving / eating / cooking
private  int      score;
private  Picture  picture;
private  Date     last_update;
private  WeightObj   weight;         


public UserActivityObj(){
	this.weight=new WeightObj();
	this.day=Calendar.getInstance();
	this.title="";
	this.type=0;
	
			
}

public Long get_id() {
	return _id;
}
public void set_id(Long _id) {
	this._id = _id;
}



public int getType() {
	return type;
}
public void setType(int type) {
	this.type = type;
}
public int getScore() {
	return score;
}
public void setScore(int score) {
	this.score = score;
}


public Picture getPicture() {
	return picture;
}
public void setPicture(Picture picture) {
	this.picture = picture;
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
	
public void setDay(int year,int month,int day,int hourOfDay,int minute) {
	this.day.set(year, month, day, hourOfDay, minute);
}
public WeightObj getWeight() {
	return weight;
}
public void setWeight(WeightObj weight) {
	this.weight = weight;
}



}
