package com.rdupuis.amikcal.useractivity;

import java.util.ArrayList;

import com.rdupuis.amikcal.commons.AppConsts;
import components.Component;
import components.ComponentFood;

public class UserActivityLunch extends UserActivity{

	
	public enum LunchType {
		BREAKFAST,LUNCH,DINER,SNACK,UNDEFINED
		}

	
	public UserActivityLunch() {
		super();
		
		this.type = UserActivity.UA_CLASS_CD.LUNCH;
		this.setId(AppConsts.NO_ID);
		this.setComponentsList(new ArrayList<Component>());
		

	}

		
	
}
