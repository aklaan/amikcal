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

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AppConsts;
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
 * 
 * 
 * @author Rodolphe Dupuis
 * @version 0.1
 */

public class Act_EnergyList extends Activity {
    long currentId;
    String currentFilter = "";
    private ListView maListViewPerso;
    public final static String OUTPUT____ID_OF_ENERGY = "_id";

    /**
     * This function is Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.view_energy_list);
	getActionBar().setTitle("Aliments");
	generateList();

	((TextView) findViewById(R.id.energy_list_view_tv_filter)).addTextChangedListener(new TextWatcher() {

	    public void afterTextChanged(Editable s) {
		// ((TextView)findViewById(R.id.numcaratteri)).setText(String.format(getString(R.string.caratteri),
		// s.length()));

	    }

	    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub

	    }

	    public void onTextChanged(CharSequence s, int start, int before, int count) {

		cleanList();
		currentFilter = s.toString();
		generateList();

	    }

	});

    }// fin du onCreate

    /**
     * Appel la vue d'�dition d'un aliment (energy)
     */
    public void editEnergy(long id) {
	Intent intent = new Intent(this, Act_Food_Editor.class);
	intent.putExtra(Act_Food_Editor.INPUT____ID_OF_FOOD, id);
	startActivityForResult(intent, R.integer.ACTY_ENERGY);
    }

    /**
     * onClickAdd est g�n�rique dans les �crans du m�me type on doit donc
     * red�finir la fonction
     * 
     */
    public void onClickAdd(View v) {

	editEnergy(AppConsts.NO_ID);

    }

    /**
     * G�re les actions � effectuer en cas de retours des Intent appel�s
     * 
     * 
     * 
     * <p>
     * Liste des actions actuellement g�r�es
     * <ul>
     * <li>Retour depuis l'�cran d'�dition d'un aliment:</li>
     * <ul>
     * <li>Effacer la liste actuelle.</li>
     * <li>Reg�n�rer la liste avec les donn�es actualis�es.</li>
     * </ul>
     * 
     * </ul>
     * </p>
     * 
     * @param requestCode
     *            le code le l'action demand�e
     * 
     * @param resultCode
     *            le code retour envoy� par l'activit�e appell�e
     * 
     * @param intent
     *            l'intent utilis�e
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

	switch (requestCode) {

	case R.integer.ACTY_ENERGY:

	    if (resultCode == RESULT_OK) {

		cleanList();
		generateList();

	    }
	    break;
	default:
	    break;

	}
    }

    /**
     * ____________________________________________________________________________
     * effacer la liste
     * 
     */
    protected void cleanList() {
	maListViewPerso.removeAllViewsInLayout();
    }

    /**
     * ________________________________________________________________________
     * g�n�rer la liste des �nergie � partir des informations stock�es dans la
     * base de donn�e.
     */
    protected void generateList() {

	Uri selectUri;

	if (currentFilter == null || currentFilter.length() == 0) {
	    selectUri = ContentDescriptorObj.TB_Energies.SELECT_ALL_ENERGIES_URI;
	} else {
	    selectUri = ContentDescriptorObj.TB_Energies.SELECT_ENERGIES_LIKE_URI.buildUpon().appendPath(currentFilter)
		    .build();
	}

	// R�cup�ration de la listview cr��e dans le fichier customizedlist.xml
	maListViewPerso = (ListView) findViewById(R.id.listviewperso);

	// Cr�ation de la ArrayList qui nous permettra de remplire la listView
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

	// Cr�ation d'un SimpleAdapter qui se chargera de mettre les items
	// pr�sent dans notre list (listItem)
	// dans la vue affichageitem
	SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.list_item_energy_food,

	new String[] { "name", "energy" }, new int[] { R.id.item_energy_food_name, R.id.item_energy_food_nbkcal

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

		OnChooseEnergy(view, Long.parseLong(map.get("id")));

	    }
	});

	// Enfin on met un �couteur d'�v�nement long sur notre listView
	maListViewPerso.setOnItemLongClickListener(new OnItemLongClickListener() {
	    // @Override
	    @SuppressWarnings("unchecked")
	    public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {

		HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
		Act_EnergyList.this.currentId = Long.parseLong(map.get("id"));

		// int ilaposition=position;
		// cr�ation d'un boite de dialogue pour confirmer le
		// choix
		new AlertDialog.Builder(Act_EnergyList.this).setTitle("Confirmation")
			.setMessage("Que voulez-vous faire ?")
			.setPositiveButton("Editer", new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int whichButton) {

				onClick_edit();
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

    public void OnChooseEnergy(View v, Long id) {

	// on alimente le résultat dans l'Intent pour que l'Activity m�re puisse
	// récupérer la valeur.
	this.getIntent().putExtra(this.OUTPUT____ID_OF_ENERGY, id);

	// on appelle setResult pour d�clencher le onActivityResult de
	// l'activity m�re.
	this.setResult(RESULT_OK, this.getIntent());

	// On termine l'Activity
	this.finish();
    }

    /**
     * _________________________________________________________________________
     * Action a effectuer lorsque l'on clique sur �diter l'�nergie s�l�ctionn�e
     */
    public void onClick_edit() {
	editEnergy(this.currentId);
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
	    onClickAdd(null);
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