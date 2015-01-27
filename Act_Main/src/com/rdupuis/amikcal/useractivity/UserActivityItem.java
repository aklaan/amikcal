package com.rdupuis.amikcal.useractivity;

import android.app.Activity;
import android.content.Intent;

public abstract class UserActivityItem {

	public Activity mActivity;
	public UserActivity mUserActivity;

	public UserActivityItem(Activity activity) {
		mActivity = activity;
	}

	public void edit() {

	}

	
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
    
	this.mActivity.setResult(0, intent);
	}
}
