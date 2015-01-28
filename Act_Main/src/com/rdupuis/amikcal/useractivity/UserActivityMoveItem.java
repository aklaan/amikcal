package com.rdupuis.amikcal.useractivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.ActivityType;

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
		intent.putExtra(
				Act_UserActivity_EditorCommons.INTENT_IN____UA_EDITOR_COMMONS____ID_OF_THE_USER_ACTIVITY,
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

}
