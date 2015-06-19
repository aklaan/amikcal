package com.rdupuis.amikcal.useractivity.lunch;

import java.util.ArrayList;
import java.util.Calendar;
import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.components.Component_Food;
import com.rdupuis.amikcal.useractivity.UA_CLASS_CD;
import com.rdupuis.amikcal.useractivity.UserActivity_Commons;

public class UserActivity_Lunch extends UserActivity_Commons {

private LUNCH_TYPE mTypeOfLunch;    
        

    public UserActivity_Lunch() {
	super();	
	this.setTypeOfLunch(LUNCH_TYPE.UNDEFINED);
	
    }

    public UserActivity_Lunch(Calendar day) {
	super(day);
	
    }

    
@Override
public UA_CLASS_CD getActivityType() {
	return UA_CLASS_CD.LUNCH;
}

public LUNCH_TYPE getTypeOfLunch() {
    return mTypeOfLunch;
}

public void setTypeOfLunch(LUNCH_TYPE mTypeOfLunch) {
    this.mTypeOfLunch = mTypeOfLunch;
}


}
