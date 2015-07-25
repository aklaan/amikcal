package com.rdupuis.amikcal.useractivity.move;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.useractivity.UserActivity;
import com.rdupuis.amikcal.useractivity.UserActivity_Action;

public class UserActivity_Move_Action extends UserActivity_Action {

    public UserActivity_Move_Action(Activity activity, UserActivity uaMove) {
	this.mActivity = activity;
	this.mUserActivity = uaMove;
    }

    // Dans le cas d'une mise à jour on appelle l'éditeur avec l'ID de
    // l'activité à modifier
    public void edit() {

	Intent intent = new Intent(this.mActivity, Act_UserActivity_Move_Editor.class);
	intent.putExtra(Act_UserActivity_Move_Editor.INPUT____ID, this.mUserActivity.getId());
	intent.putExtra(Act_UserActivity_Move_Editor.INPUT____DAY, ToolBox.getSqlDate((this.mUserActivity.getDay())));

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
