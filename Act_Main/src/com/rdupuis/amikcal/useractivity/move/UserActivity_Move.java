package com.rdupuis.amikcal.useractivity.move;

import java.util.Calendar;

import com.rdupuis.amikcal.useractivity.UserActivity;
import com.rdupuis.amikcal.useractivity.UserActivity.UA_CLASS_CD;

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
