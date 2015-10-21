package com.rdupuis.amikcal.components;

import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.components.food.Component_Food;
import com.rdupuis.amikcal.components.food.Component_Food_Manager;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.energy.Food;
import com.rdupuis.amikcal.useractivity.UserActivity;
import com.rdupuis.amikcal.useractivity.lunch.UserActivity_Lunch;

import java.util.ArrayList;

/**
 * Created by rodol on 23/09/2015.
 */
public class Component_List_Builder {
    Activity activity;

    public Component_List_Builder(Activity activity) {
        this.activity = activity;
    }


    public ArrayList<? extends Component> getComponentList(UserActivity userActivity) {

        //Pour un repas, on préprare une liste d'aliments
        if (UserActivity_Lunch.class.isInstance(userActivity)) {
            return getComponent_Food_List((UserActivity_Lunch) userActivity);
        }

        return null;
    }

    private Activity getActivity() {
        return this.activity;
    }




    private ArrayList<Component_Food> getComponent_Food_List(UserActivity_Lunch userActivity_lunch) {
        ArrayList<Component_Food> list = new ArrayList<Component_Food>();

        if (userActivity_lunch.getDatabaseId() != AppConsts.NO_ID) {
            long _id = userActivity_lunch.getDatabaseId();
            Uri selectUri = ContentUris.withAppendedId(
                    ContentDescriptorObj.TB_Party_rel.SELECT_CHILDREN_URI, _id);

            // On crée un curseur pour lire la vue
            Cursor cursor = this.getActivity().getContentResolver().query(selectUri, null, String.valueOf(_id), null, null);

            // On récupère les index des colonnes de la vue.
            final int INDX_COMPONENT_ID = cursor.getColumnIndex(ContentDescriptorObj.TB_Party_rel.Columns.ROW_ID);

            Component_Food_Manager manager = new Component_Food_Manager(getActivity());

            // faire un move First pour positionner le curseur, sinon on pointe sur null

            if (cursor.moveToFirst()) {

                do {
                    Component_Food component = manager.load(cursor.getLong(INDX_COMPONENT_ID));
                    list.add(component);

                } while (cursor.moveToNext());
            } else {
                String message = "Composant " + String.valueOf(_id) + " non trouvé";
                Log.e("Component_Lisy_Builder", message);

            }
            cursor.close();

        }
        return list;
    }


    public ArrayList<Component_Food> getComponent_Food_List(Food food) {
        ArrayList<Component_Food> list = new ArrayList<Component_Food>();

        if (food.getDatabaseId() != AppConsts.NO_ID) {
            long _id = food.getDatabaseId();
            Uri selectUri = ContentUris.withAppendedId(
                    ContentDescriptorObj.TB_Party_rel.SELECT_CHILDREN_URI, _id);

            // On crée un curseur pour lire la vue
            Cursor cursor = this.getActivity().getContentResolver().query(selectUri, null, String.valueOf(_id), null, null);

            // On récupère les index des colonnes de la vue.
            final int INDX_COMPONENT_ID = cursor.getColumnIndex(ContentDescriptorObj.TB_Party_rel.Columns.ROW_ID);

            Component_Food_Manager manager = new Component_Food_Manager(getActivity());

            // faire un move First pour positionner le curseur, sinon on pointe sur null

            if (cursor.moveToFirst()) {

                do {
                    Component_Food component = manager.load(cursor.getLong(INDX_COMPONENT_ID));
                    list.add(component);

                } while (cursor.moveToNext());
            } else {
                String message = "Composant " + String.valueOf(_id) + " non trouvé";
                Log.e("Component_Lisy_Builder", message);

            }
            cursor.close();

        }
        return list;
    }


}