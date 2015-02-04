package com.rdupuis.amikcal.useractivity;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ToolBox;

public class UserActivityWeightItem extends UserActivityItem {

	public UserActivityWeightItem(Activity activity) {
		super(activity);
		this.mUserActivity = new UserActivityWeight();
	}

	/*****************************************************************************
	 * 
	 * edit : editer l'activitee de type move
	 * 
	 *****************************************************************************/
	public void edit() {
		Intent intent = new Intent(this.mActivity,
				Act_UserActivity_EditWeightActivity.class);
		intent.putExtra(AppConsts.INPUT____UA_EDITOR____USER_ACTIVITY_ID,
				this.mUserActivity._id);
		
		this.mActivity
				.startActivityForResult(intent, 0);
	}

	
	// Dans le cas d'une création on appelle l'éditeur avec la date où
	// on veut créer l'activité
	public void create(Calendar day){
		Intent intent = new Intent(this.mActivity,
				Act_UserActivity_EditWeightActivity.class);
				  intent.putExtra( AppConsts.INPUT____UA_EDITOR____DAY , ToolBox.getSqlDate(day));
			
		this.mActivity
				.startActivityForResult(intent, 0);

		
	}

	
}
