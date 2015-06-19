package com.rdupuis.amikcal.useractivity.move;

import java.util.Calendar;

import com.rdupuis.amikcal.useractivity.UA_CLASS_CD;
import com.rdupuis.amikcal.useractivity.UserActivity_Commons;

public class UserActivity_Move extends UserActivity_Commons {

  

    public UserActivity_Move() {
	super();
	
    }

    public UserActivity_Move(Calendar day) {
	super(day);
	
    }

    @Override
    public UA_CLASS_CD getActivityType() {
	return UA_CLASS_CD.MOVE;
    }

    
}
