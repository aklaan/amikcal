package com.rdupuis.amikcal.useractivity.weight;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.rdupuis.amikcal.useractivity.UserActivity_Manager;


public class UserActivity_Weight_Manager extends UserActivity_Manager {

    public UserActivity_Weight_Manager(Activity activity, UserActivity_Weight userActivity_weight) {
	super (activity, userActivity_weight);
    }




    public void edit(UserActivity_Weight userActivity_weight) {

	Intent intent = new Intent(getActivity(), Act_UserActivity_Weight_Editor.class);
	//intent.putExtra(Act_UserActivity_Weight_Editor.INPUT____UA_ID, this.mUserActivity.getId());

	//intent.putExtra(Act_UserActivity_Weight_Editor.INPUT____DAY, ToolBox.getSqlDate(( this.mUserActivity).getDay()));


	getActivity().startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	Toast.makeText(getActivity(), "on result lunchitem", Toast.LENGTH_LONG).show();
    }


    public void delete() {
	// TODO Auto-generated method stub

    }


}
