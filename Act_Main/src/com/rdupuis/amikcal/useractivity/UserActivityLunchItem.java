package com.rdupuis.amikcal.useractivity;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.ActivityType;
import com.rdupuis.amikcal.commons.ToolBox;

public class UserActivityLunchItem extends UserActivityItem {

	public UserActivityLunchItem(Activity activity) {
		super(activity);
		this.mUserActivity = new UserActivityLunch();
	}

	// Dans le cas d'une mise � jour on appelle l'�diteur avec l'ID de 
	// l'activit� � modifier
	public void edit() {
		Intent intent = new Intent(this.mActivity,
				Act_UserActivity_EditLunchActivity.class);
		intent.putExtra(
				Act_UserActivity_EditorCommons.INTENT_IN____UA_EDITOR_COMMONS____ID_OF_THE_USER_ACTIVITY,
				this.mUserActivity._id);
		
		this.mActivity
				.startActivityForResult(intent, this.mActivity.getResources()
						.getInteger(R.integer.ACTY_USER_ACTIVITY_EDITOR));
	}

	
	// Dans le cas d'une cr�ation on appelle l'�diteur avec la date o�
	// on veut cr�er l'activit�
	public void create(Calendar day){
		Intent intent = new Intent(this.mActivity,
				Act_UserActivity_EditLunchActivity.class);
		
		  intent.putExtra( Act_UserActivity_EditorCommons.INTENT_IN____UA_EDITOR_COMMONS____DAY
		  , ToolBox.getSqlDate(day));
		
		
		this.mActivity
				.startActivityForResult(intent, this.mActivity.getResources()
						.getInteger(R.integer.ACTY_USER_ACTIVITY_EDITOR));

		
	}
	
}
