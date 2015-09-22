package com.rdupuis.amikcal.useractivity;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AmiKcalFactory;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.Manager;
import com.rdupuis.amikcal.commons.ManagerBuilder;
import com.rdupuis.amikcal.commons.MultipleItemsActivityList;
import com.rdupuis.amikcal.commons.TimeSlidableFragment;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.data.ContentDescriptorObj;

/**
 * <b>Liste des activit�es de l'utilisateur pour un jour donn�.</b> il est aussi
 * pr�vu d'affichier des liste de recettes mais je pense que le d�coupage est �
 * revoir
 *
 * @author Rodolphe Dupuis
 * @version 0.1
 */

public class Frag_UserActivityList extends TimeSlidableFragment {

    private ListView mCustomListView;
    private long selectedItemId;

    /**
     * Called when the activity is first created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // on initialise la date a traiter
        this.initCurrentDay();

        // On affiche le layer souhait�
        View mainView = inflater.inflate(R.layout.view_useractivities_list, container, false);

        // on r�cup�re la listView du layer
        mCustomListView = (ListView) mainView.findViewById(R.id.listviewperso);

        // Afficher la date du jour.
        TextView tv = (TextView) mainView.findViewById(R.id.fragtextView);

        // j'affiche la page seulement pour le debug.
        tv.setText(ToolBox.getSqlDate(currentDay) + " / n�Fragment: " + getArguments().getString("page"));

        // On tente de r�cup�rer le type de liste � afficher si l'intent nous
        // en a envoy� un

        refreshScreen();
        return mainView;
    }

    ;

    /**
     * lorsque l'activit� s'arr�te, on doit appeller la fonction setResult pour
     * �ventuellement renvoyer les r�sultats aux activit�es appelantes
     */
    public void onStop() {
        super.onStop();
        // Toast.makeText(this, "Activit� UserActivity stop�e",
        // Toast.LENGTH_SHORT).show();
        // this.setResult(Activity.RESULT_OK, mIntent);
    }

    /**
     * ======================================================================
     * <p>
     * On affiche la liste des activit�es.
     * </p>
     * ======================================================================
     */

    protected void generateList() {

        // On pr�pare la requ�te pour rappatrier les activit�es du jour
        Uri request;
        request = ContentDescriptorObj.TB_UserActivities.SELECT_USER_ACTIVITIES_BY_DATE_URI.buildUpon()
                .appendPath(ToolBox.getSqlDate(currentDay)).build();

        // On cr�e une liste d'item diff�rents selon le type d'activit� �
        // afficher (lunch / move / weight).
        MultipleItemsActivityList mMultipleItemsList = new MultipleItemsActivityList(this);

        // On d�clare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;
        map = new HashMap<String, String>();

        // Cr�ation du curseur
        Cursor cur = getActivity().getContentResolver().query(request, null, null, null, null);

        // Initialisation des index o� r�cup�rer les infos
        final int INDX_COL_ID = cur.getColumnIndex(ContentDescriptorObj.TB_UserActivities.Columns.ID);
        final int INDX_COL_TITLE = cur.getColumnIndex(ContentDescriptorObj.TB_UserActivities.Columns.TITLE);
        final int INDX_COL_DATE = cur.getColumnIndex(ContentDescriptorObj.TB_UserActivities.Columns.DATE);
        final int INDX_COL_TYPE = cur.getColumnIndex(ContentDescriptorObj.TB_UserActivities.Columns.CLASS);
        AppConsts.UA_CLASS_CD_MAP mapping = new AppConsts.UA_CLASS_CD_MAP();
        // Lecture du curseur
        if (cur.moveToFirst()) {

            do {

                map = new HashMap<String, String>();
                map.put("titre", cur.getString(INDX_COL_TITLE));

                Calendar mCalendar = ToolBox.parseSQLDatetime(cur.getString(INDX_COL_DATE));

                DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance();
                decimalFormat.applyPattern("00");

                map.put("hour", String.valueOf(decimalFormat.format(mCalendar.get(Calendar.HOUR_OF_DAY))));
                map.put("minute", String.valueOf(decimalFormat.format(mCalendar.get(Calendar.MINUTE))));
                map.put("id", cur.getString(INDX_COL_ID));
                map.put("class", cur.getString(INDX_COL_TYPE));


                map.putAll(computeEnergy(Long.parseLong(cur.getString(INDX_COL_ID))));

                mMultipleItemsList.addItem(map);

            } while (cur.moveToNext());

        }
        cur.close();

        // On attribut � notre listView l'adapter que l'on vient de cr�er
        this.mCustomListView.setAdapter(mMultipleItemsList);

        // Enfin on met un �couteur d'�v�nement sur notre listView
        // ceci va nous permetre d'interagir lorsque l'utilisateur va clicker
        // sur
        // un item.
        this.mCustomListView.setOnItemClickListener(new OnItemClickListener() {
            // @Override
            @SuppressWarnings("unchecked")
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // on r�cup�re la HashMap contenant les infos de notre item
                // (titre, description, img)
                HashMap<String, String> map = (HashMap<String, String>) mCustomListView.getItemAtPosition(position);

                UserActivities_FragmentsSlider uafs = (UserActivities_FragmentsSlider) Frag_UserActivityList.this
                        .getActivity();

                // ----------------------------------------------------------------
                // A voir si c'est utile de garder la page
                uafs.setCurrentPage(Integer.parseInt(getArguments().getString("page")));

                Activity activity = Frag_UserActivityList.this.getActivity();

                long _id = Long.parseLong(map.get("id"));
                //on ne sait pas quel type d'activité on a en face.
                //on commence à lire l'activité pour savoir ce que c'est
                UserActivity_Manager manager = new UserActivity_Manager(activity);
                UserActivity ua =  manager.load(_id);
                manager.showComponents(ua);

            }
        });

        // Enfin on met un �couteur d'�v�nement long sur notre listView
        // on cr�e un OnItemLongClickListener sur lequel on va red�finir la
        // m�thode onItemLongClick
        mCustomListView.setOnItemLongClickListener(new OnItemLongClickListener() {
            // @Override
            @SuppressWarnings("unchecked")
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {

                // on r�cup�re le contenu de l'item s�l�ctionn� dans une
                // HasMap
                HashMap<String, String> map = (HashMap<String, String>) mCustomListView.getItemAtPosition(position);

                // on alimentente l'Id de l'item selectionn�
                Frag_UserActivityList.this.selectedItemId = Long.parseLong(map.get("id"));

                // int ilaposition=position;

                // cr�ation d'une boite de dialogue pour proposer les
                // actions
                // a effectuer.

                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());

                // Titre de la boite de dialoge
                adb.setTitle("Confirmation");

                // Message
                adb.setMessage("Que voulez-vous faire ?");

                // Bouton : Editer l'activit� selectionn�e
                // on d�clare un bouton avec un listener sur lequel on
                // vas red�finir
                // la m�thode onClick pour :
                // recontruite un objet activit� avec le bon type puis
                // lancer sa m�thode Edit
                adb.setPositiveButton("Editer", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        // j'ai un id, mais je ne sais pas de quel type est ma UserActivity
                        long _id = Frag_UserActivityList.this.selectedItemId;
                        UserActivity_Manager manager = new UserActivity_Manager(Frag_UserActivityList.this.getActivity());
                        UserActivity ua =  manager.load(_id);
                        manager.edit(ua);

                    }
                });

                // *********************************************************************************************************

                adb.setNegativeButton("Supprimer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        long item_id = Frag_UserActivityList.this.selectedItemId;
                        Activity currentActivity = Frag_UserActivityList.this.getActivity();
                        //UserActivity_Manager uam = new UserActivity_Manager(currentActivity);
                        //UserActivity ua = uam.load(item_id);
                        //   uam.delete(ua);
                        Frag_UserActivityList.this.refreshScreen();
                    }
                });

                adb.setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
            /* User clicked Cancel so do some stuff */
                    }
                });

                adb.show();

                return true;
            }// public boolean onItemLongClick
        }); // fin du OnItemLongClickListener

    }

    /**
     * =============================================================== cleanList
     * : Efface tous les items de la liste affich� � l'�cran
     * ===============================================================
     */
    protected void cleanList() {
        mCustomListView.removeAllViewsInLayout();
    }

    /**
     * ======================================================= computeEnergy :
     * Calcule l'�nergie pour une activit�
     * ======================================================
     */

    private HashMap<String, String> computeEnergy(long UserActivityId) {

        HashMap<String, String> map;
        map = new HashMap<String, String>();
    /*
     * Uri selectUri = ContentUris .withAppendedId(
	 * ContentDescriptorObj.ViewUserActivities.URI_VIEW_USER_ACTIVITIES,
	 * UserActivityId);
	 * 
	 * Cursor cur = getActivity().getContentResolver().query(selectUri,
	 * null, null, null, null);
	 * 
	 * final int INDX_COL_SUM_ENERGY = cur
	 * .getColumnIndex(ContentDescriptorObj
	 * .ViewUserActivities.Columns.SUM_ENERGY); final int
	 * INDX_COL_SUM_LIPIDS = cur
	 * .getColumnIndex(ContentDescriptorObj.ViewUserActivities
	 * .Columns.SUM_LIPIDS); final int INDX_COL_SUM_GLUCIDS = cur
	 * .getColumnIndex
	 * (ContentDescriptorObj.ViewUserActivities.Columns.SUM_GLUCIDS); final
	 * int INDX_COL_SUM_PROTEINS = cur
	 * .getColumnIndex(ContentDescriptorObj.ViewUserActivities
	 * .Columns.SUM_PROTEINS);
	 * 
	 * if (cur.moveToFirst()) {
	 * 
	 * DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat
	 * .getInstance(); decimalFormat.applyPattern("000.0");
	 * DecimalFormatSymbols dfs = new DecimalFormatSymbols();
	 * dfs.setDecimalSeparator('.');
	 * decimalFormat.setDecimalFormatSymbols(dfs);
	 * 
	 * do { map = new HashMap<String, String>();
	 * 
	 * decimalFormat.applyPattern("###0.0"); map.put("sumEnergy",
	 * decimalFormat.format(cur.getFloat(INDX_COL_SUM_ENERGY)));
	 * 
	 * decimalFormat.applyPattern("##0.0");
	 * 
	 * map.put("sumGlucids", "g:" + decimalFormat.format(cur
	 * .getFloat(INDX_COL_SUM_GLUCIDS))); map.put("sumLipids", "l:" +
	 * decimalFormat.format(cur .getFloat(INDX_COL_SUM_LIPIDS)));
	 * map.put("sumProteins", "p:" + decimalFormat.format(cur
	 * .getFloat(INDX_COL_SUM_PROTEINS))); } while (cur.moveToNext());
	 * 
	 * } cur.close();
	 */
        return map;

    }

    /**
     * ======================================================= refreshScreen :
     * rafraichir l'�cran ======================================================
     */
    public void refreshScreen() {

        cleanList();
        generateList();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        refreshScreen();
    }

}
