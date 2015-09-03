package com.rdupuis.amikcal.commons;

import android.app.Activity;

import com.rdupuis.amikcal.Qty.Qty;
import com.rdupuis.amikcal.Qty.Qty_Manager;
import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.components.food.Component_Food;
import com.rdupuis.amikcal.components.food.Component_Food_Manager;
import com.rdupuis.amikcal.components.move.Component_Move;
import com.rdupuis.amikcal.components.move.Component_Move_Manager;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.relations.Relation;
import com.rdupuis.amikcal.useractivity.UserActivity;

/**
 * Created by rodol on 01/09/2015.
 */
public final class ManagerBuilder {

    public static Manager build(Activity activity, Component component) {

        switch (component.getRelationClass()) {

            case CFOOD:
                return new Component_Food_Manager(activity, (Component_Food) component);

            case CMOVE:
                return new Component_Move_Manager(activity, (Component_Move) component);
            case CWEIGHT:
                break;
            case NRJ_REF_INTRNL:
                break;


            default:
        }
        return null;

    }


    public static Manager build(Activity activity, UserActivity userActivity) {

        switch (userActivity.getActivityType()) {

            case LUNCH:
                break;
            case MOVE:
                return null;

            case WEIGHT:
                return null;
            default:
        }
        return null;

    }


    public static Manager build(Activity activity, EnergySource energySource) {
        return null;
    }


    public static Manager build(Activity activity, Qty qty) {
        return new Qty_Manager(activity, qty);
    }


    public static Manager build(Activity activity, Relation relation) {
        switch (relation.getRelationClass()) {
            case CFOOD:
        }
        return null;
    }


}
