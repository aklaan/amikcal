package com.rdupuis.amikcal.useractivity.lunch;

import android.app.Activity;

import android.content.Intent;
import android.widget.Toast;

import com.rdupuis.amikcal.commons.ManagedElement;
import com.rdupuis.amikcal.commons.ToolBox;

import com.rdupuis.amikcal.useractivity.UserActivity;
import com.rdupuis.amikcal.useractivity.UserActivity_Manager;


public class UserActivity_Lunch_Manager extends UserActivity_Manager {


    /**
     * on peu appeler l'éditeur UA lunch de n'importe quel activité.
     *
     * @param activity
     */
    public UserActivity_Lunch_Manager(Activity activity, UserActivity_Lunch element) {
        super(activity, element);


    }

    /**
     * Fonction edit :
     * Appelle l'activité qui permet d'éditer un  userActivity_Food
     */

    @Override
    public void edit() {
        UserActivity userActivity = (UserActivity) getElement();
        Intent intent = new Intent(getActivity(), Act_UserActivity_Lunch_Editor.class);
        intent.putExtra(Act_UserActivity_Lunch_Editor.INPUT____ID, userActivity.getDatabaseId());
        intent.putExtra(Act_UserActivity_Lunch_Editor.INPUT____DAY, ToolBox.getSqlDate(userActivity.getDay()));
        getActivity().startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Toast.makeText(getActivity(), "on result lunchitem", Toast.LENGTH_LONG).show();
    }


}
