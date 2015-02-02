package com.rdupuis.amikcal.useractivity;

import android.app.Activity;
import android.content.Intent;
import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AmikcalVar;

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
		intent.putExtra(AmikcalVar.INPUT____UA_EDITOR____USER_ACTIVITY_ID,
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
