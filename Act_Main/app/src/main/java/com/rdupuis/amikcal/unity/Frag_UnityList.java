package com.rdupuis.amikcal.unity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.energy.EnergySource;

//2013-08-25 - R.Dupuis
//Fragment permetant d'afficher une liste d'unité de mesure
public class Frag_UnityList extends Fragment {

    private ListView maListViewPerso;
    static Long currentId;
    static Long energyId;
    private static Long WITH_NO_FILTER = 0l;
    public static final String INPUT____ENERGY_FILTER = "ENERGY_FILTER";

    private View mainView;

    //Création de la vue
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.view_unit_list,
                container, false);

        // si on demande une liste des unités avec un ID d'énergy en entrée
        // on n'affiche que les unités qui on été utilisé pour cet ID
        // dans le cas contraire, on affiche la liste de toutes les unitées
        // connues.

        EnergySource energySource = getActivity().getIntent().getExtras().getParcelable(Frag_UnityList.INPUT____ENERGY_FILTER);

        generateList(energySource);
        return mainView;
    }

    // Action de cliquer sur une unitée
    // on lance l'éditeur d'unité
    public void onClickUnit(View v, Unity unity) {
        Intent intent = new Intent(getActivity(), Act_UnitOfMeasureEditor.class);
        intent.putExtra(Act_UnitOfMeasureEditor.INPUT____UNITY, unity);
        startActivityForResult(intent, R.integer.ACTY_UNIT);

    }

    //Ajouter une unité
    public void onClickAdd(View v) {

        onClickUnit(v, null);

    }


    /*********************************************************************************************
     *
     *
     ********************************************************************************************/
    protected void cleanList() {
        maListViewPerso.removeAllViewsInLayout();
    }

    /*********************************************************************************************
     *
     *
     ********************************************************************************************/

    protected void generateList(EnergySource energySource
    ) {

        Uri mUri;

        if (energySource != null) {
            mUri = ContentDescriptorObj.TB_Units.URI_SELECT_UNIT;

        } else {

            mUri = ContentUris.withAppendedId(
                    ContentDescriptorObj.CustomQuery.URI_USED_UNITS_FOR_ENERGY,
                    energySource.getDatabaseId());
        }

        // Récupération de la listview créée dans le fichier customizedlist.xml
        maListViewPerso = (ListView) mainView.findViewById(R.id.listviewperso);

        // Création de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

        // On déclare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;
        map = new HashMap<String, String>();

        // On créé un curseur pour lire la table des unitées
        Cursor cur = getActivity().getContentResolver().query(mUri, null, null, null,
                null);

        final int INDX_COL_ID = cur
                .getColumnIndex(ContentDescriptorObj.TB_Units.Columns.ID);
        final int INDX_COL_NAME = cur
                .getColumnIndex(ContentDescriptorObj.TB_Units.Columns.LONG_NAME);
        final int INDX_COL_SYMBOL = cur
                .getColumnIndex(ContentDescriptorObj.TB_Units.Columns.SHORT_NAME);

        // faire un move First pour positionner le pointeur

        if (cur.moveToFirst()) {

            do {
                map = new HashMap<String, String>();

                map.put("id", cur.getString(INDX_COL_ID));
                map.put("name", cur.getString(INDX_COL_NAME));
                map.put("symbol", cur.getString(INDX_COL_SYMBOL));
                listItem.add(map);
            } while (cur.moveToNext());

        }
        cur.close();

        // Création d'un SimpleAdapter qui se chargera de mettre les items
        // présent dans notre list (listItem)
        // dans la vue affichageitem
        SimpleAdapter mSchedule = new SimpleAdapter(getActivity().getBaseContext(),
                listItem, R.layout.list_item_unit, new String[]{"name",
                "symbol"}, new int[]{R.id.item_unit_name,
                R.id.item_unit_symbol});

        // On attribut à notre listView l'adapter que l'on vient de créer
        maListViewPerso.setAdapter(mSchedule);

        // ********************************************************
        // on met un écouteur d'événement sur notre listView
        // ********************************************************
        maListViewPerso.setOnItemClickListener(new OnItemClickListener() {
            // @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // on récupère la HashMap contenant les infos de notre item
                // (titre, description, img)
                HashMap<String, String> map = (HashMap<String, String>) maListViewPerso
                        .getItemAtPosition(position);
                OnChooseUnit(view, map.get("id"));
            }
        });

        // ********************************************************
        // on met un écouteur d'événement sur notre listView
        // ********************************************************
        maListViewPerso
                .setOnItemLongClickListener(new OnItemLongClickListener() {
                    // @Override
                    @SuppressWarnings({"rawtypes", "unchecked"})
                    public boolean onItemLongClick(AdapterView parent, View v,
                                                   int position, long id) {
                        HashMap<String, String> map = (HashMap<String, String>) maListViewPerso
                                .getItemAtPosition(position);
                        Frag_UnityList.currentId = Long.parseLong(map
                                .get("id"));
                        // création d'un boite de dialogue pour confirmer le
                        // choix
                        new AlertDialog.Builder(Frag_UnityList.this.getActivity())
                                .setTitle("Confirmation")
                                .setMessage("Que souhaitez-vous faire ?")
                                .setPositiveButton("Editer",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int whichButton) {
                                                /*
												 * User clicked edit so do some
												 * stuff
												 */
                                                onClick_edit();

                                            }
                                        })
                                .setNegativeButton("Supprimer",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int whichButton) {
												/*
												 * User delete Cancel so do some
												 * stuff
												 */
                                            }
                                        })
                                .setNeutralButton("Annuler",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int whichButton) {
												/*
												 * User clicked Cancel so do
												 * some stuff
												 */
                                            }
                                        }).show();

                        return true;
                    }// public boolean onItemLongClick
                }); // fin du OnItemLongClickListener

    }

    /*********************************************************************************
     *
     *
     **********************************************************************************/

    public void OnChooseUnit(View v, String id) {


    }

    /*********************************************************************************
     *
     *
     **********************************************************************************/
    public void OnDelete(Long id) {

        // afficher une demande de confirmation

    }

    public void onClick_edit() {
        editUnit(Frag_UnityList.currentId.toString());
    }

    public void editUnit(String id) {
        Intent intent = new Intent(getActivity(), Act_UnitOfMeasureEditor.class);
        intent.putExtra("UnitId", id);
        startActivityForResult(intent, R.integer.ACTY_UNIT);
    }


} // end class