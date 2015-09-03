package com.rdupuis.amikcal.useractivity;

import android.app.Activity;
import android.widget.Toast;

import com.rdupuis.amikcal.useractivity.lunch.UserActivity_Lunch_Manager;
import com.rdupuis.amikcal.useractivity.move.UserActivity_Move_Manager;
import com.rdupuis.amikcal.useractivity.weight.UserActivity_Weight_Manager;

/**
 * @author Rodolphe
 * @created 28/08/2015.
 */
public class UserActivity_ManagerFactory {

    /*********************************************************************************
     * @param activity
     * @return
     ********************************************************************************/
    public static UserActivity_Manager getManager(Activity activity, UserActivity userActivity) {

        // en fonction du type d'activit�e, on va retourner l'objet adequat
        switch (userActivity.getActivityType()) {
            case LUNCH:
                return new UserActivity_Lunch_Manager(activity );
            case MOVE:
                return new UserActivity_Move_Manager(activity);
            case WEIGHT:
                return new UserActivity_Weight_Manager(activity);
        }

       // Toast.makeText(this.mActivity, "Action non pr�vu", Toast.LENGTH_LONG).show();

        return null;
    }



}
