package com.rdupuis.amikcal.useractivity;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.rdupuis.amikcal.commons.ToolBox;

public class UserActivity_Lunch_Action extends UserActivity_Action {
    
    
    public UserActivity_Lunch_Action(Activity activity,UserActivity uaLunch) {
	this.mActivity = activity;
	this.mUserActivity = uaLunch;
	
    }

    // Dans le cas d'une mise à jour on appelle l'éditeur avec l'ID de
    // l'activité à modifier
    public void edit() {

	Intent intent = new Intent(this.mActivity, Act_UserActivity_EditLunchActivity.class);
	intent.putExtra(Act_UserActivity_EditLunchActivity.INPUT____UA_ID, this.mUserActivity.getId());
	intent.putExtra(Act_UserActivity_EditLunchActivity.INPUT____DAY, ToolBox.getSqlDate(this.mUserActivity.getDay()));

	this.mActivity.startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	Toast.makeText(this.mActivity, "on result lunchitem", Toast.LENGTH_LONG).show();
    }

    @Override
    public void delete() {
	// TODO Auto-generated method stub

    }


}
