package com.rdupuis.amikcal.useractivity.lunch;

import java.util.Calendar;

import com.rdupuis.amikcal.useractivity.UserActivity;
import com.rdupuis.amikcal.useractivity.UserActivity.UA_CLASS_CD;

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
