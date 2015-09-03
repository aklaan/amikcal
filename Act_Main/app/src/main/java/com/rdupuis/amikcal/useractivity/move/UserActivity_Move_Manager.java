package com.rdupuis.amikcal.useractivity.move;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.rdupuis.amikcal.commons.ToolBox;

import com.rdupuis.amikcal.useractivity.UserActivity_Manager;


public class UserActivity_Move_Manager extends UserActivity_Manager {

    public UserActivity_Move_Manager(Activity activity) {
        super(activity);
    }

    /**
     * Appel de l'éditeur des Activité Physiques
     * @param userActivity_move
     */
    public void edit(UserActivity_Move userActivity_move) {

        Intent intent = new Intent(getActivity(), Act_UserActivity_Move_Editor.class);
        intent.putExtra(Act_UserActivity_Move_Editor.INPUT____ID, userActivity_move.getDatabaseId());
        intent.putExtra(Act_UserActivity_Move_Editor.INPUT____DAY, ToolBox.getSqlDate((userActivity_move.getDay())));
        getActivity().startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Toast.makeText(getActivity(), "on result lunchitem", Toast.LENGTH_LONG).show();
    }


    public void delete() {
        // TODO Auto-generated method stub

    }

}
