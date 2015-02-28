package com.rdupuis.amikcal.useractivity;

import java.util.Calendar;
import java.util.Date;

import com.rdupuis.amikcal.commons.AppConsts;


import android.graphics.Picture;

public class UserActivity {
	public enum UA_CLASS_CD {
		LUNCH, WEIGHT, MOVE
	}
	
	public Long _id;
	public Calendar day;
	private String title;
	public UA_CLASS_CD type; // moving / eating / cooking
	public int score;
	public Picture picture;
	public Date last_update;

	
	public UserActivity() {
	//Par defaut
		this.day = Calendar.getInstance();
		this.title = "";
		this._id = AppConsts.NO_ID;
	}

	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
	}

	public UA_CLASS_CD getType() {
		return type;
	}

	public void setType(UA_CLASS_CD type) {
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

	public void setDay(int year, int month, int day, int hourOfDay, int minute) {
		this.day.set(year, month, day, hourOfDay, minute);
	}

}
