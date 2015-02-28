package com.rdupuis.amikcal.useractivity;

import com.rdupuis.amikcal.commons.AppConsts;

public class UserActivityLunch extends UserActivity{

	
	public enum LunchType {
		BREAKFAST,LUNCH,DINER,SNACK,UNKNOWN
		}

	
	public UserActivityLunch() {
		super();
		
		this.type = UserActivity.UA_CLASS_CD.LUNCH;
		this.set_id(AppConsts.NO_ID);
		

	}

	
}
