package com.rdupuis.amikcal.useractivity;

import android.app.Activity;
import android.content.Intent;

public abstract class UserActivity_Action {

	public Activity mActivity;
	public UserActivity mUserActivity;
	
	public abstract void edit();
	public abstract void delete();
	
}
