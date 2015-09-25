package com.rdupuis.amikcal.useractivity.lunch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.SimpleAdapter;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.components.Component_List_Builder;
import com.rdupuis.amikcal.components.food.Component_Food;
import com.rdupuis.amikcal.components.food.Component_Food_Manager;
import com.rdupuis.amikcal.useractivity.Act_UserActivity_Component_List;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Activitée permettant d'afficher les composants d'un repas (lUNNCH)
 */
public class Act_UserActivity_Lunch_Component_List extends Act_UserActivity_Component_List {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClickComponent(Component component) {
        Component_Food_Manager manager = new Component_Food_Manager(this);
        manager.edit(component);
    }

    // Créer un nouveau composant Food.
    @Override
    public void onClickAdd(View v) {
        onClickComponent(new Component_Food());
    }


    /**
     * On souhaite afficher la liste des composant FOOD d'une UA FOOD
     */

    @Override
    protected void refreshScreen() {

        // effacer la liste actuelle de composants
        maListViewPerso.removeAllViewsInLayout();

        // Création de la ArrayList qui nous permettra de remplir la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

        // On déclare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;
        map = new HashMap<String, String>();


        /**
         * Il faut passer par un Builder qui va créer un conteneur de composants liées à l'UA
         *
         *
         * this.mUA est à null !!!!
         *
         */

        Component_List_Builder clb = new Component_List_Builder(this);
        ArrayList<Component_Food> list = (ArrayList<Component_Food>) clb.getComponentList(this.mUA);

        // Pour chaque Composant de L'UA
        for (Component comp : list) {

            map = new HashMap<String, String>();
            map.put("COMPONENT_ID", String.valueOf(comp.getDatabaseId()));
            map.put("name", comp.getEnergy().getName());
            map.put("quantity", String.valueOf(comp.getQty().getAmount()));
            map.put("unity", comp.getQty().getUnity().getLongName());

            listItem.add(map);
        }

        // Création d'un SimpleAdapter qui se chargera de mettre les items
        // présent dans notre list (listItem)
        // dans la vue affichageitem
        SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), listItem,
                R.layout.list_item_activity_lunch_component, new String[]{"name", "quantity", "energy", "equiv",
                "nbglu", "nbpro", "nblip"}, new int[]{R.id.itemfood_name, R.id.itemfood_quantity,
                R.id.itemfood_nbkcal, R.id.itemfood_equiv, R.id.itemfood_glu, R.id.itemfood_pro,
                R.id.itemfood_lip});

        // On attribut à notre listView l'adapter que l'on vient de créer
        maListViewPerso.setAdapter(mSchedule);

        // Enfin on met un écouteur d'évènement sur notre listView
        maListViewPerso.setOnItemClickListener(new OnItemClickListener() {
            // @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // on récupère la HashMap contenant les infos de notre item
                // (titre, description, img)
                HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);


                long component_id = Long.parseLong(map.get("COMPONENT_ID"));
                Component_Food_Manager manager = new Component_Food_Manager(Act_UserActivity_Lunch_Component_List.this);
                Component_Food component = manager.load(component_id);
                //un simple click permet d'éditer le composant
                manager.edit(component);


            }
        });

        // Enfin on met un écouteur d'évènement long sur notre listView
        maListViewPerso.setOnItemLongClickListener(new OnItemLongClickListener() {
            // @Override
            @SuppressWarnings("rawtypes")
            public boolean onItemLongClick(AdapterView parent, View v, int position, long id) {
                // int ilaposition=position;
                // création d'un boite de dialogue pour confirmer le
                // choix

                new AlertDialog.Builder(parent.getContext())

                        // new
                        // AlertDialog.Builder(Act_UserActivityComponentList.this)
                        .setTitle("Confirmation").setMessage("Voulez vous supprimer cet component de la liste")
                        .setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // aTableauComponentaff.remove(ilaposition);
                                // aTableauComponent.remove(ilaposition);
                                // maListViewPerso = (ListView)
                                // findViewById(android.R.id.list);
                                // final ArrayAdapter adapter2 =
                                // new
                                // ArrayAdapter(getBaseContext(),
                                // android.R.layout.simple_list_item_1,aTableauComponentaff);
                                // maListViewPerso.setAdapter(adapter2);
                                //
                            }
                        }).setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                /*
                 * User clicked Cancel so do some stuff
				 */
                    }
                })

                        .show();

                return true;
            }// public boolean onItemLongClick
        }); // fin du OnItemLongClickListener

    }

} // end class