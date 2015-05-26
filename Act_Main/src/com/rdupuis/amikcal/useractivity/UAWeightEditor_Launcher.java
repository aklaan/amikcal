package com.rdupuis.amikcal.useractivity;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ToolBox;

public class UAWeightEditor_Launcher extends UAEditorLauncher {

    public UAWeightEditor_Launcher(Activity activity) {
	this.mActivity = activity;
	this.mEditable = new UserActivityLunch();
    }

    // Dans le cas d'une mise à jour on appelle l'éditeur avec l'ID de
    // l'activité à modifier
    public void start() {

	Intent intent = new Intent(this.mActivity, Act_UserActivity_EditLunchActivity.class);
	intent.putExtra(AppConsts.INPUT____UA_EDITOR____USER_ACTIVITY_ID, this.mEditable.getId());

	this.mActivity.startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	Toast.makeText(this.mActivity, "on result lunchitem", Toast.LENGTH_LONG).show();
    }

    @Override
    public void delete() {
	// TODO Auto-generated method stub

    }

    @Override
    public void editNewUA(Calendar day_Of_the_UA) {
	Intent intent = new Intent(this.mActivity, Act_UserActivity_EditLunchActivity.class);
	intent.putExtra(AppConsts.INPUT____UA_EDITOR____DAY, ToolBox.getSqlDate(day_Of_the_UA));

	this.mActivity.startActivityForResult(intent, 0);

    }

}
