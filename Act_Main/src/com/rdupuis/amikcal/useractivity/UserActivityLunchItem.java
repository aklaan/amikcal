package com.rdupuis.amikcal.useractivity;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ToolBox;

public class UserActivityLunchItem extends UserActivityItem {

	public UserActivityLunchItem(Activity activity) {
		super(activity);
		this.mUserActivity = new UserActivityLunch();
	}

	// Dans le cas d'une mise à jour on appelle l'éditeur avec l'ID de 
	// l'activité à modifier
	public void edit() {
				
		Intent intent = new Intent(this.mActivity,
				Act_UserActivity_EditLunchActivity.class);
		intent.putExtra(AppConsts.INPUT____UA_EDITOR____USER_ACTIVITY_ID,
				this.mUserActivity._id);
		
		this.mActivity
				.startActivityForResult(intent, 0);
	}

	
	// Dans le cas d'une création on appelle l'éditeur avec la date où
	// on veut créer l'activité
	public void create(Calendar day){
		Intent intent = new Intent(this.mActivity,
				Act_UserActivity_EditLunchActivity.class);
				  intent.putExtra( AppConsts.INPUT____UA_EDITOR____DAY , ToolBox.getSqlDate(day));
			
		this.mActivity
				.startActivityForResult(intent, 0);

		
	}
	

		
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent){
		Toast.makeText(this.mActivity, "on result lunchitem",
                Toast.LENGTH_LONG).show();
	}

	
}
