package com.rdupuis.amikcal.useractivity;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;

public abstract class AbstractUserActivityItem {

	public Activity mActivity;
	public UserActivity mUserActivity;
	
	public abstract void edit();
	public abstract void create(Calendar day) ;
	public abstract void delete();
	public abstract void onActivityResult(int requestCode, int resultCode, Intent intent);
}
