package com.rdupuis.amikcal.useractivity;

import java.util.Calendar;

import com.rdupuis.amikcal.data.ContentDescriptorObj;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;

public class UserActivityItem extends AbstractUserActivityItem{

	//public Activity mActivity;
	//public UserActivity mUserActivity;

	public UserActivityItem(Activity activity) {
		this.mActivity=activity;
		}

	@Override
	public void delete() {

		Uri uriDelete = ContentUris.withAppendedId(
				ContentDescriptorObj.UserActivities.URI_DELETE_USER_ACTIVITIES,
				this.mUserActivity.get_id());
		mActivity.getContentResolver().delete(uriDelete, null, null);
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		this.mActivity.setResult(0, intent);
	}


	@Override
	public void edit() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void create(Calendar day) {
		// TODO Auto-generated method stub
		
	}
}
