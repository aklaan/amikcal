package com.rdupuis.amikcal.useractivity;

import java.util.Calendar;
import java.util.Date;

import com.rdupuis.amikcal.commons.ActivityType;
import com.rdupuis.amikcal.commons.WeightObj;

import android.graphics.Picture;

public abstract class UserActivity {

	public Long _id;
	public Calendar day;
	private String title;
	public ActivityType type; // moving / eating / cooking
	public int score;
	public Picture picture;
	public Date last_update;
	final long NO_ID = -1l;

	public UserActivity() {
		this.day = Calendar.getInstance();
		this.title = "";

	}

	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
	}

	public ActivityType getType() {
		return type;
	}

	public void setType(ActivityType type) {
		this.type = type;
	}

	public void setTypeByName(String typeName) {

		this.type = ActivityType.valueOf(typeName);
		

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

	public void setDay(int year, int month, int day, int hourOfDay, int minute) {
		this.day.set(year, month, day, hourOfDay, minute);
	}

	

}
