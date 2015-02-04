package com.rdupuis.amikcal.useractivity;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ToolBox;

public class UserActivityMoveItem extends UserActivityItem {

	public UserActivityMoveItem(Activity activity) {
		super(activity);
		this.mUserActivity = new UserActivityMove();
	}

	/*****************************************************************************
	 * 
	 * edit : editer l'activitee de type move
	 * 
	 *****************************************************************************/
	public void edit() {
		Intent intent = new Intent(this.mActivity,
				Act_UserActivity_EditMoveActivity.class);
		intent.putExtra(AppConsts.INPUT____UA_EDITOR____USER_ACTIVITY_ID,
				this.mUserActivity._id);
		/*
		 * intent.putExtra( Act_UserActivity_EditorCommons.
		 * INTENT_IN____UA_EDITOR_COMMONS____DAY_OF_THE_USER_ACTIVITY,
		 * ToolBox.getSqlDate(getCurrentDay()));
		 */
		// intent.putExtra("page", getCurrentPage());

		this.mActivity
				.startActivityForResult(intent, this.mActivity.getResources()
						.getInteger(R.integer.ACTY_USER_ACTIVITY_EDITOR));
	}

	@Override
	public
	void create(Calendar day) {
		Intent intent = new Intent(this.mActivity,
				Act_UserActivity_EditMoveActivity.class);
				  intent.putExtra( AppConsts.INPUT____UA_EDITOR____DAY , ToolBox.getSqlDate(day));
			
		this.mActivity
				.startActivityForResult(intent, 0);

		
	}

}
