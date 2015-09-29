package com.rdupuis.amikcal.energy;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.Manager;
import com.rdupuis.amikcal.data.ContentDescriptorObj;

/**
 * <b>Act_EnergyList est la classe qui affiche la vue liste d'aliments.</b>
 * <p>
 * Cette vue comporte :
 * <ul>
 * <li>Une zone de recherche pour filtrer les aliments par nom.</li>
 * <li>Une liste d'aliment.</li>
 * </ul>
 * </p>
 *
 * @author Rodolphe Dupuis
 * @version 0.1
 */

public class Act_Energy_Food_List extends Act_Energy_List {

    /**
     * This function is Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(this, this.getClass().getCanonicalName(), Toast.LENGTH_SHORT).show();

        setContentView(R.layout.view_energy_list);
        getActionBar().setTitle("Aliments");
        generateList(currentFilter);

        ((TextView) findViewById(R.id.energy_list_view_tv_filter)).addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                cleanList();
                currentFilter = s.toString();
                generateList(currentFilter);

            }

        });

    }// fin du onCreate


    /**
     * onClickAdd est g�n�rique dans les �crans du m�me type on doit donc
     * red�finir la fonction
     */
    public void onClickAdd(View v) {


    }
public void editNewFood(){
    Food energySource = new Food();
    Energy_Food_Manager energy_food_manager = new Energy_Food_Manager(this);
    energy_food_manager.edit(energySource);

}
    /**
     * G�re les actions � effectuer en cas de retours des Intent appel�s
     * <p/>
     * <p/>
     * <p/>
     * <p>
     * Liste des actions actuellement g�r�es
     * <ul>
     * <li>Retour depuis l'�cran d'�dition d'un aliment:</li>
     * <ul>
     * <li>Effacer la liste actuelle.</li>
     * <li>Reg�n�rer la liste avec les donn�es actualis�es.</li>
     * </ul>
     * <p/>
     * </ul>
     * </p>
     *
     * @param requestCode le code le l'action demand�e
     * @param resultCode  le code retour envoy� par l'activit�e appell�e
     * @param intent      l'intent utilis�e
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        switch (requestCode) {

            case R.integer.ACTY_ENERGY_FOOD_EDITOR:

                if (resultCode == RESULT_OK) {

                    cleanList();
                    generateList(currentFilter);

                }
                break;
            default:
                break;

        }
    }

    /**
     * ____________________________________________________________________________
     * effacer la liste
     */
    protected void cleanList() {
        maListViewPerso.removeAllViewsInLayout();
    }

    /**
     * ________________________________________________________________________
     * g�n�rer la liste des �nergie � partir des informations stock�es dans la
     * base de donn�e.
     */
    protected void generateList(String filter) {

        Uri selectUri;

        if (filter == null || filter.length() == 0) {
            selectUri = ContentDescriptorObj.TB_Energies.SELECT_ALL_ENERGIES_URI;
        } else {
            selectUri = ContentDescriptorObj.TB_Energies.SELECT_ENERGIES_LIKE_URI.buildUpon().appendPath(filter)
                    .build();
        }

        // Récupération de la listview créée dans le fichier customizedlist.xml
        maListViewPerso = (ListView) findViewById(R.id.listviewperso);

        // Création de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

        // On d�clare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;
        map = new HashMap<String, String>();

        // On cr� un curseur pour lire la table des aliments consomm�s
        Cursor cur = this.getContentResolver().query(selectUri, null, null, null, null);

        final int INDX_COL_ID = cur.getColumnIndex(ContentDescriptorObj.TB_Energies.Columns.ID);
        final int INDX_COL_NAME = cur.getColumnIndex(ContentDescriptorObj.TB_Energies.Columns.NAME);
        // final int INDX_COL_NBKCAL = cur
        // .getColumnIndex(ContentDescriptorObj.Energies.Columns.MNT_ENERGY);
        // final int INDX_COL_NBGLU =
        // cur.getColumnIndex(ContentDescriptorObj.Energies.Columns.MNT_GLUCIDS);
        // final int INDX_COL_NBLIP =
        // cur.getColumnIndex(ContentDescriptorObj.Energies.Columns.MNT_LIPIDS);
        // final int INDX_COL_NBPRO =
        // cur.getColumnIndex(ContentDescriptorObj.Energies.Columns.MNT_PROTEINS);
        // final int INDX_COL_ISLIQUID =
        // cur.getColumnIndex(ContentDescriptorObj.Energies.Columns.ISLIQUID);
        // faire un move First pour positionner le pointeur
        // cur.moveToFirst();

        // System.out.println(cur.getString(INDX_COL_NAME));

        if (cur.moveToFirst()) {

            do {
                map = new HashMap<String, String>();
                map.put("id", cur.getString(INDX_COL_ID));
                map.put("name", cur.getString(INDX_COL_NAME));
                // map.put("energy", cur.getString(INDX_COL_NBKCAL) + " Kcal");
                // map.put("lipids", cur.getString(INDX_COL_NBLIP)+"g");
                // map.put("proteins", cur.getString(INDX_COL_NBPRO)+"g");
                // map.put("glucids", cur.getString(INDX_COL_NBGLU) +"g");
                // map.put("isliquid", cur.getString(INDX_COL_ISLIQUID) +"g");

                listItem.add(map);
            } while (cur.moveToNext());

        }
        cur.close();

        // Création d'un SimpleAdapter qui se chargera de mettre les items
        // présent dans notre list (listItem) dans la vue affichageitem
        SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.list_item_energy_food,

                new String[]{"name", "energy"}, new int[]{R.id.item_energy_food_name, R.id.item_energy_food_nbkcal

	/*
     * new String[] {"name", "energy", "lipids","proteins","glucids"}, new
	 * int[] {R.id.item_energy_food_name, R.id.item_energy_food_nbkcal,
	 * R.id.item_energy_food_lip, R.id.item_energy_food_pro,
	 * R.id.item_energy_food_glu
	 */

        });

        // On attribut � notre listView l'adapter que l'on vient de cr�er
        maListViewPerso.setAdapter(mSchedule);

        // Enfin on met un �couteur d'�v�nement sur notre listView
        maListViewPerso.setOnItemClickListener(new OnItemClickListener() {
            // @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // on r�cup�re la HashMap contenant les infos de notre item
                // (titre, description, img)
                HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
                Energy_Manager manager = new Energy_Manager(Act_Energy_Food_List.this);
                EnergySource energySource = manager.load(Long.parseLong(map.get("id")));
                // On retourne l'énergie choisie par l'utilisateur
                OnChooseEnergy(energySource);

            }
        });

        // Enfin on met un écouteur d'évènement long sur notre listView
        maListViewPerso.setOnItemLongClickListener(new OnItemLongClickListener() {
            // @Override
            @SuppressWarnings("unchecked")
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {

                HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
                Act_Energy_Food_List.this.chosedEnergyId = Long.parseLong(map.get("id"));


                // int ilaposition=position;
                // cr�ation d'un boite de dialogue pour confirmer le
                // choix
                new AlertDialog.Builder(Act_Energy_Food_List.this).setTitle("Confirmation")
                        .setMessage("Que voulez-vous faire ?")
                        .setPositiveButton("Editer", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //on va éditer l'énergie
                                Energy_Food_Manager manager = new Energy_Food_Manager(Act_Energy_Food_List.this);
                                Food food = manager.load(Act_Energy_Food_List.this.chosedEnergyId);
                                manager.edit(food);
                            }
                        }).setNegativeButton("Supprimer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                /*
                 * User clicked Cancel so do some stuff
				 */
                    }
                })

                        .setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
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

    /**
     * ________________________________________________________ Action a
     * effectuer lorsque l'on séléctionne une énergie
     */

    public void OnChooseEnergy(EnergySource energySource) {

        // on alimente le résultat dans l'Intent pour que l'Activity mère puisse récupérer la valeur.
        this.getIntent().putExtra(this.OUTPUT____CHOOSED_ENERGY, energySource);

        // on appelle setResult pour déclencher le onActivityResult de l'activity mère.
        this.setResult(RESULT_OK, this.getIntent());

        // On termine l'Activity
        this.finish();
    }

    /**
     * _________________________________________________________________________
     * Action a effectuer lorsque l'on clique sur �diter l'�nergie s�l�ctionn�e
     */
    public void onClick_edit() {

        //TODO
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_energy_list, menu);
        return true;

    }

    // rafraichir la barre de menu
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);

    }

    /**
     *
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionbar_energylist_item_add:
                editNewFood();
                break;
            case R.id.actionbar_energylist_item_back:
                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        // invalidateOptionsMenu va appeller la m�thode onPrepareOptionsMenu();
        // qui va elle m�me rafraichir la barre de menu
        invalidateOptionsMenu();
        return true;
    }

    // *************************************
} // end class