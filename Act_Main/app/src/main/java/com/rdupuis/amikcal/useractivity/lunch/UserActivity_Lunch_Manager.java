package com.rdupuis.amikcal.useractivity.lunch;

import android.app.Activity;

import android.content.Intent;
import android.widget.Toast;

import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.useractivity.UserActivity;

import com.rdupuis.amikcal.useractivity.UserActivity_Manager_Commons;

public class UserActivity_Lunch_Manager extends UserActivity_Manager_Commons {

    /**
     * on peu appeler l'éditeur UA lunch de n'importe quel activité.
     *
     * @param currentActivity
     * @param uaLunch
     */
    public UserActivity_Lunch_Manager(Activity currentActivity, UserActivity uaLunch) {
        super(currentActivity, uaLunch);

    }

    /**
     * Fonction edit :
     * Appelle l'activité qui permet d'éditer un  userActivity_Food
     */

    public void edit() {

        Intent intent = new Intent(this.mActivity, Act_UserActivity_Lunch_Editor.class);
        intent.putExtra(Act_UserActivity_Lunch_Editor.INPUT____ID, this.mUserActivity.getDatabaseId());
        intent.putExtra(Act_UserActivity_Lunch_Editor.INPUT____DAY, ToolBox.getSqlDate(this.mUserActivity.getDay()));
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
