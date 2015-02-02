package com.rdupuis.amikcal.useractivity;

public class UserActivityLunch extends UserActivity{

	
	public enum LunchType {
		BREAKFAST,LUNCH,DINER,SNACK,UNKNOWN
		}

	
	public UserActivityLunch() {
		super();
		
		this.type = UserActivity.UAType.LUNCH;
		this.set_id(this.NO_ID);
		

	}

	
}
