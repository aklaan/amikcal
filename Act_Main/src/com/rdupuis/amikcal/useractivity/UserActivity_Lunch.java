package com.rdupuis.amikcal.useractivity;

import java.util.Calendar;

public class UserActivity_Lunch extends UserActivity {

    public enum LunchType {
	BREAKFAST, LUNCH, DINER, SNACK, UNDEFINED
    }

    public UserActivity_Lunch() {
	super();
	this.type = UserActivity.UA_CLASS_CD.LUNCH;

    }

    public UserActivity_Lunch(Calendar day) {
	super(day);
	this.type = UserActivity.UA_CLASS_CD.LUNCH;

    }

}
