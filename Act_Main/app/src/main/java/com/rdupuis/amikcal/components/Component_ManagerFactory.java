package com.rdupuis.amikcal.components;

import android.app.Activity;
import android.widget.Toast;

import com.rdupuis.amikcal.components.food.Component_Food;
import com.rdupuis.amikcal.components.food.Component_Food_Manager;
import com.rdupuis.amikcal.components.move.Component_Move;
import com.rdupuis.amikcal.components.move.Component_Move_Manager;
import com.rdupuis.amikcal.components.weight.Component_Weight;
import com.rdupuis.amikcal.components.weight.Component_Weight_Manager;

/**
 * Created by rodol on 31/08/2015.
 */
public class Component_ManagerFactory {

    public Component_Manager createComponentAction(Activity activity, Component component) {

        // en fonction du type d'activit�e, on va retourner l'objet adequat
        switch (component.getRelationClass()) {
            case CFOOD:
                return new Component_Food_Manager(activity, (Component_Food) component);
            case CMOVE:
                return new Component_Move_Manager(activity, (Component_Move) component);
            case CWEIGHT:
                return new Component_Weight_Manager(activity, (Component_Weight) component);
            default:
                Toast.makeText(activity, "ERR Cr�a Component Action editor", Toast.LENGTH_LONG).show();

                break;
        }

        return null;
    }


}
