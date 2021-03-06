package com.rdupuis.amikcal.useractivity;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AmiKcalFactory;
import com.rdupuis.amikcal.commons.MultipleItemsActivityList;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.data.ContentDescriptorObj;

/**
 * <b>Liste des activit�es de l'utilisateur.</b>
 * <p>
 * les activit�es sont :
 * <ul>
 * <li>les repas</li>
 * <li>les activit�es physiques</li>
 * <li>les pes�es</li>
 * <li>les recettes</li>
 * </ul>
 * </p>
 * 
 * @author Rodolphe Dupuis
 * @version 0.1
 */

public class Act_UserActivityList extends Activity {

    private ListView mCustomListView;
    private Calendar currentDay;
    private int currentTypeOfList;
    private long currentId;
    private AmiKcalFactory mFactory;
    public static final String INPUT____LIST_TYP = "list_typ";
    public static final String INPUT____DAY = "_day";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.view_useractivities_list);
	this.mFactory = new AmiKcalFactory(this);
	mCustomListView = (ListView) findViewById(R.id.listviewperso);

	// * on tente de r�cup�rer une date si l'intent nous en a envoy� une
	try {
	    currentDay = ToolBox.parseCalendar(this.getIntent().getStringExtra(this.INPUT____DAY));
	} catch (Exception e) {
	    currentDay = Calendar.getInstance();
	}
	;

	// * on tente de r�cup�rer le type de liste � afficher si l'intent nous
	// en a envoy� un
	try {
	    currentTypeOfList = Integer.parseInt(this.getIntent().getStringExtra(this.INPUT____LIST_TYP));
	} catch (Exception e) {
	    currentTypeOfList = this.getResources().getInteger(R.integer.USER_ACTIVITY_LIST_ACTIVITY);

	}
	;
	// * rafraichissement de l'�cran.
	refreshScreen();

    }

    /**
     * lorsque l'activit� s'arr�te, on doit appeller la fonction setResult pour
     * �ventuellement renvoyer les r�sultats aux activit�es appelantes
     */
    public void onStop() {
	super.onStop();
	// Toast.makeText(this, "Activit� UserActivity stop�e",
	// Toast.LENGTH_SHORT).show();
	this.setResult(RESULT_OK, this.getIntent());
    }

    /**
     * Rafraichissement de la liste des activit�s � l'�cran
     */
    private void refreshScreen() {
	cleanList();
	generateList();

	TextView tvComment = (TextView) findViewById(R.id.user_activities_view_tv_comment);

	if (mCustomListView.getCount() == 0) {
	    tvComment.setText("vous n'avez aucune saisies ce jour");
	} else {
	    tvComment.setText(ToolBox.getSqlDate(currentDay));
	    ;
	}
    }

    /**
     * <p>
     * On affiche la liste des activit�es.
     * </p>
     */

    protected void generateList() {

	Uri request;

	if (currentTypeOfList == this.getResources().getInteger(R.integer.USER_ACTIVITY_LIST_RECIPE)) {
	    request = ContentUris.withAppendedId(
		    ContentDescriptorObj.TB_UserActivities.SELECT_USER_ACTIVITIES_BY_TYPE_URI, this.getResources()
			    .getInteger(R.integer.ACTIVITY_RECIPE));

	} else {
	    request = ContentDescriptorObj.TB_UserActivities.SELECT_USER_ACTIVITIES_BY_DATE_URI.buildUpon()
		    .appendPath(ToolBox.getSqlDate(currentDay)).build();
	}
	;

	MultipleItemsActivityList mMultipleItemsList = new MultipleItemsActivityList(this);

	// On d�clare la HashMap qui contiendra les informations pour un item
	HashMap<String, String> map;
	map = new HashMap<String, String>();

	Cursor cur = this.getContentResolver().query(request, null, null, null, null);

	final int INDX_COL_ID = cur.getColumnIndex(ContentDescriptorObj.TB_UserActivities.Columns.ID);
	final int INDX_COL_TITLE = cur.getColumnIndex(ContentDescriptorObj.TB_UserActivities.Columns.TITLE);
	final int INDX_COL_DATE = cur.getColumnIndex(ContentDescriptorObj.TB_UserActivities.Columns.DATE);
	final int INDX_COL_CLASS = cur.getColumnIndex(ContentDescriptorObj.TB_UserActivities.Columns.CLASS);

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

		// dans la MAP on r�cup�re le code de la class
		// il faut passer par le mapping plus tard pour savoir � quoi
		// correspond ce code
		map.put("class", cur.getString(INDX_COL_CLASS));

		map.putAll(computeEnergy(Long.parseLong(cur.getString(INDX_COL_ID))));

		mMultipleItemsList.addItem(map);

	    } while (cur.moveToNext());

	}
	cur.close();

	// On attribut � notre listView l'adapter que l'on vient de cr�er
	this.mCustomListView.setAdapter(mMultipleItemsList);

	// Enfin on met un �couteur d'�v�nement sur notre listView
	this.mCustomListView.setOnItemClickListener(new OnItemClickListener() {
	    // @Override
	    @SuppressWarnings("unchecked")
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// on r�cup�re la HashMap contenant les infos de notre item
		// (titre, description, img)
		HashMap<String, String> map = (HashMap<String, String>) mCustomListView.getItemAtPosition(position);

		onClickActivity(map.get("id"));

	    }
	});

	// Enfin on met un �couteur d'�v�nement long sur notre listView
	mCustomListView.setOnItemLongClickListener(new OnItemLongClickListener() {
	    // @Override
	    @SuppressWarnings("unchecked")
	    public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {

		HashMap<String, String> map = (HashMap<String, String>) mCustomListView.getItemAtPosition(position);
		Act_UserActivityList.this.currentId = Long.parseLong(map.get("id"));

		// int ilaposition=position;
		// cr�ation d'une boite de dialogue pour confirmer le choix

		// AlertDialog.Builder adb = new
		// AlertDialog.Builder(Act_UserActivityList.this);
		AlertDialog.Builder adb = new AlertDialog.Builder(parent.getContext());
		adb.setTitle("Confirmation");
		adb.setMessage("Que voulez-vous faire ?");

		// *********************************************************************************************************
		adb.setPositiveButton("Editer", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int whichButton) {

			long item_id = Act_UserActivityList.this.currentId;

			AmiKcalFactory factory = new AmiKcalFactory(Act_UserActivityList.this);

			UserActivity ua = factory.load_UserActivity(item_id);

			Act_UserActivityList.this.onClickEdit(ua);
		    }
		});

		// *********************************************************************************************************
		adb.setNegativeButton("Supprimer", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int whichButton) {
			Act_UserActivityList.this.onClickDelete(String.valueOf(Act_UserActivityList.this.currentId));
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
     * G�rer les retours d'appels aux autres activit�es
     * 
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	switch (requestCode) {

	case R.integer.ACTY_USER_ACTIVITY:

	    if (resultCode == RESULT_OK) {

	    }
	    break;

	case R.integer.ACTY_COMPONENT_LIST:

	    if (resultCode == RESULT_OK) {

	    }
	    break;

	default:
	    break;
	}
	refreshScreen();
    }

    /**
     * Efface tous les items de la liste affich� � l'�cran
     */
    protected void cleanList() {
	mCustomListView.removeAllViewsInLayout();
    }

    /**
     * 
     * @param id
     *            Identifiant de l'activit�e utilisateur s�l�ction�e
     */
    public void onClickActivity(String id) {
	Intent intent = new Intent(this, Act_UserActivity_Component_List.class);
	intent.putExtra(Act_UserActivity_Component_List.INPUT____UA_ID, id);
	startActivityForResult(intent, R.integer.ACTY_COMPONENT_LIST);
    }

    /**
     * 
     * @param id
     *            Identifiant de l'activit�e utilisateur � �diter
     */
    public void onClickEdit(UserActivity userActivity) {

	UserActivity_Action action = mFactory.create_UserActivity_Action(this, userActivity);
	action.edit();
    }

    /**
     * 
     * @param id
     *            Identifiant de l'activit�e utilisateur � supprimer
     */
    public void onClickDelete(String id) {

	// v�rifier s'il y a des enfants
	// s'il y a des enfant alerter et demander une confirmation

	Uri uriDelete = ContentUris.withAppendedId(ContentDescriptorObj.TB_UserActivities.DELETE_USER_ACTIVITY_URI,
		Long.parseLong(id));
	this.getContentResolver().delete(uriDelete, null, null);
	refreshScreen();
    }

    public void onClickAdd(View v) {

	onClickEdit(new UserActivity_Commons(this.currentDay));
    }

    private HashMap<String, String> computeEnergy(long UserActivityId) {

	HashMap<String, String> map;
	map = new HashMap<String, String>();

	/*
	 * Uri selectUri =
	 * ContentUris.withAppendedId(ContentDescriptorObj.ViewUserActivities
	 * .URI_VIEW_USER_ACTIVITIES,UserActivityId);
	 * 
	 * Cursor cur = this.getContentResolver().query(selectUri, null, null,
	 * null, null);
	 * 
	 * final int INDX_COL_SUM_ENERGY =
	 * cur.getColumnIndex(ContentDescriptorObj
	 * .ViewUserActivities.Columns.SUM_ENERGY); final int
	 * INDX_COL_SUM_LIPIDS =
	 * cur.getColumnIndex(ContentDescriptorObj.ViewUserActivities
	 * .Columns.SUM_LIPIDS); final int INDX_COL_SUM_GLUCIDS =
	 * cur.getColumnIndex
	 * (ContentDescriptorObj.ViewUserActivities.Columns.SUM_GLUCIDS); final
	 * int INDX_COL_SUM_PROTEINS =
	 * cur.getColumnIndex(ContentDescriptorObj.ViewUserActivities
	 * .Columns.SUM_PROTEINS);
	 * 
	 * 
	 * if (cur.moveToFirst()) {
	 * 
	 * 
	 * DecimalFormat decimalFormat =
	 * (DecimalFormat)DecimalFormat.getInstance();
	 * decimalFormat.applyPattern("000.0"); DecimalFormatSymbols dfs = new
	 * DecimalFormatSymbols(); dfs.setDecimalSeparator('.');
	 * decimalFormat.setDecimalFormatSymbols( dfs );
	 * 
	 * do { map = new HashMap<String, String>();
	 * 
	 * decimalFormat.applyPattern("###0.0"); map.put("sumEnergy",
	 * decimalFormat.format(cur.getFloat(INDX_COL_SUM_ENERGY)));
	 * 
	 * decimalFormat.applyPattern("##0.0");
	 * 
	 * map.put("sumGlucids",
	 * "g:"+decimalFormat.format(cur.getFloat(INDX_COL_SUM_GLUCIDS)));
	 * map.put("sumLipids",
	 * "l:"+decimalFormat.format(cur.getFloat(INDX_COL_SUM_LIPIDS)));
	 * map.put("sumProteins",
	 * "p:"+decimalFormat.format(cur.getFloat(INDX_COL_SUM_PROTEINS))); }
	 * while (cur.moveToNext());
	 * 
	 * } cur.close();
	 */
	return map;

    }

    public void onClickButtonPrev(View v) {
	this.currentDay.add(Calendar.DATE, -1);
	refreshScreen();

    }

    public void onClickButtonNext(View v) {
	this.currentDay.add(Calendar.DATE, +1);
	refreshScreen();

    }

}
