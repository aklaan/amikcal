package com.rdupuis.amikcal.useractivity.move;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.useractivity.UserActivity;
import com.rdupuis.amikcal.useractivity.UserActivity_Manager;
import com.rdupuis.amikcal.useractivity.UserActivity_Manager_Commons;

public class UserActivity_Move_Manager extends UserActivity_Manager_Commons {

    public UserActivity_Move_Manager(Activity activity, UserActivity uaMove) {
        super(activity, uaMove);
    }

    // Dans le cas d'une mise � jour on appelle l'�diteur avec l'ID de
    // l'activit� � modifier
    public void edit() {

        Intent intent = new Intent(this.mActivity, Act_UserActivity_Move_Editor.class);
        intent.putExtra(Act_UserActivity_Move_Editor.INPUT____ID, this.mUserActivity.getDatabaseId());
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
