package com.rdupuis.amikcal.useractivity;

import java.util.Calendar;

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

	public void create(Calendar day) {

	}

	
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
    
	this.mActivity.setResult(0, intent);
	}
}
