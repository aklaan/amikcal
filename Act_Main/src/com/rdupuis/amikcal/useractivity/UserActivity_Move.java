package com.rdupuis.amikcal.useractivity;

import java.util.Calendar;

public class UserActivity_Move extends UserActivity {

    public UserActivity_Move() {

	super();
	this.type = UserActivity.UA_CLASS_CD.MOVE;
    }

    public UserActivity_Move(Calendar day) {
	super(day);
	this.type = UserActivity.UA_CLASS_CD.MOVE;

    }

}
