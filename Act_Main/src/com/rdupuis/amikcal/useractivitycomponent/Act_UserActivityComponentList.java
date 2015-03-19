package com.rdupuis.amikcal.useractivitycomponent;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AmiKcalFactory;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.useractivity.UserActivity;

public class Act_UserActivityComponentList extends Activity {

    public UserActivity mUA; // la liste se réfère obligatoirement à une UA
    private ListView maListViewPerso;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	// Etape 1 : on charge les données

	mUA = new UserActivity();

	try {

	    AmiKcalFactory factory = new AmiKcalFactory(this);
	    long ua_id = Long.parseLong(this.getIntent().getStringExtra(
		    AppConsts.INPUT____USER_ACTIVITY_COMPONENT_LIST____ID_OF_PARENT_USER_ACTIVITY));

	    this.mUA = factory.load_UserActivity(ua_id);

	} catch (Exception e) {
	    // TODO
	}

	// Etape 2 : on charge l'affichage
	setContentView(R.layout.view_components_list);
	// Récupération de la listview créée dans le fichier customizedlist.xml
	maListViewPerso = (ListView) findViewById(R.id.listviewperso);

	this.refreshScreen();

    }// fin du onCreate

    /**
     * lorsque l'activité s'arrête, on active le setResult pour que les Ecran
     * précédant puissent éventuellement se mettre à jour.
     */

    public void onStop() {
	super.onStop();
	Toast.makeText(this, "Activité Activity Component stopée", Toast.LENGTH_SHORT).show();
	this.setResult(RESULT_OK, this.getIntent());
    }

    /*
     * public void onPause(){ super.onPause(); Toast.makeText(this,
     * "Activité pause", Toast.LENGTH_SHORT).show();
     * 
     * //on appelle setResult pour déclancher le onActivityResult de l'activity
     * mère. this.setResult(RESULT_OK, mIntent);
     * 
     * //On termine l'Acitvity this.finish(); }
     */

    public void onClickComponent(long componentId, long activityId) {
	Intent intent = new Intent(this, Act_UserActivityComponentEditor.class);

	// Intent intent = new Intent(this,
	// Act_UserActivityComponentSlider.class);

	intent.putExtra(AppConsts.INPUT____USER_ACTIVITY_COMPONENT_EDITOR____COMPONENT_ID, componentId);
	intent.putExtra(AppConsts.INPUT____USER_ACTIVITY_COMPONENT_EDITOR____ID_OF_PARENT_USER_ACTIVITY, activityId);
	startActivityForResult(intent, R.integer.ACTY_COMPONENT);
    }

    // Si on veut créer une nouvelle UAC, il faut que l'éditeur puisse savoir à
    // quel UA
    // l'associer
    public void onClickAdd(View v) {
	onClickComponent(AppConsts.NO_ID, mUA.get_id());
    }

    /**
     * Gère le retour de l'appel à une autre activitée
     * 
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

	switch (requestCode) {

	case R.integer.ACTY_COMPONENT:

	    if (resultCode == RESULT_OK) {

		this.refreshScreen();

	    }
	    break;
	default:
	    break;

	}
    }

    /**
     * On souhaite afficher la liste des UAC d'une UA
     * 
     * @param parentId
     */

    protected void refreshScreen() {

	// on efface la liste actuelle
	maListViewPerso.removeAllViewsInLayout();

	// Création de la ArrayList qui nous permettra de remplire la listView
	ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

	// On déclare la HashMap qui contiendra les informations pour un item
	HashMap<String, String> map;
	map = new HashMap<String, String>();
	/*
	 * Uri selectionUri = ContentUris .withAppendedId(
	 * ContentDescriptorObj.ViewActivityComponent
	 * .URI_VIEW_ACTIVITY_COMPONENTS_BY_ACTIVITY, parentId);
	 * 
	 * // On cré un curseur pour lire la table des aliments consommés Cursor
	 * cur = this.getContentResolver().query(selectionUri, null, null, null,
	 * ContentDescriptorObj.ViewActivityComponent.Columns.LAST_UPDATE);
	 * 
	 * final int INDX_COL_ID = cur
	 * .getColumnIndex(ContentDescriptorObj.ViewActivityComponent
	 * .Columns.ID); final int INDX_COL_NAME = cur
	 * .getColumnIndex(ContentDescriptorObj
	 * .ViewActivityComponent.Columns.ENERGY_NAME); final int INDX_COL_QTY =
	 * cur
	 * .getColumnIndex(ContentDescriptorObj.ViewActivityComponent.Columns
	 * .QUANTITY); final int INDX_COL_UNIT = cur
	 * .getColumnIndex(ContentDescriptorObj
	 * .ViewActivityComponent.Columns.UNIT_NAME); final int
	 * INDX_COL_ACTIVITY = cur
	 * .getColumnIndex(ContentDescriptorObj.ViewActivityComponent
	 * .Columns.PARENT_ID);
	 * 
	 * final int INDX_COL_NBKCAL = cur
	 * .getColumnIndex(ContentDescriptorObj.ViewActivityComponent
	 * .Columns.MNT_ENERGY); final int INDX_COL_NBGLU = cur
	 * .getColumnIndex(ContentDescriptorObj
	 * .ViewActivityComponent.Columns.MNT_GLUCIDS); final int INDX_COL_NBLIP
	 * = cur
	 * .getColumnIndex(ContentDescriptorObj.ViewActivityComponent.Columns
	 * .MNT_LIPIDS); final int INDX_COL_NBPRO = cur
	 * .getColumnIndex(ContentDescriptorObj
	 * .ViewActivityComponent.Columns.MNT_PROTEINS);
	 * 
	 * // faire un move First pour positionner le pointeur //
	 * cur.moveToFirst();
	 * 
	 * if (cur.moveToFirst()) {
	 * 
	 * do {
	 */

	// Pour chaque UAC de L'UA
	for (UserActivityComponent UAC : this.mUA.getUAC_List()) {

	    map = new HashMap<String, String>();
	    map.put("componentId", String.valueOf(UAC.getId()));
	    map.put("name", UAC.getEnergySource().getName());
	    map.put("quantity", String.valueOf(UAC.getQty().getAmount()));
	    map.put("unity", UAC.getQty().getUnity().getLongName());

	    listItem.add(map);
	}

	// Création d'un SimpleAdapter qui se chargera de mettre les items
	// présent dans notre list (listItem)
	// dans la vue affichageitem
	SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), listItem,
		R.layout.list_item_activity_lunch_component, new String[] { "name", "quantity", "energy", "equiv",
			"nbglu", "nbpro", "nblip" }, new int[] { R.id.itemfood_name, R.id.itemfood_quantity,
			R.id.itemfood_nbkcal, R.id.itemfood_equiv, R.id.itemfood_glu, R.id.itemfood_pro,
			R.id.itemfood_lip });

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

		/*
		 * //on créer une boite de dialogue AlertDialog.Builder adb =
		 * new AlertDialog.Builder(Acty_ActivityComponentList.this);
		 * //on attribut un titre à notre boite de dialogue
		 * adb.setTitle("Sélection Item"); //on insère un message à
		 * notre boite de dialogue, et ici on affiche le titre de l'item
		 * cliqué adb.setMessage("Votre choix : "+map.get("titre"));
		 * //on indique que l'on veut le bouton ok à notre boite de
		 * dialogue adb.setPositiveButton("Ok", null); //on affiche la
		 * boite de dialogue adb.show();
		 */

		onClickComponent(Long.getLong(map.get("componentId")), Long.getLong(map.get("activityId")));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.actionbar_component_list, menu);
	return true;

    }

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
	case R.id.actionbar_componentlist_item_add:
	    onClickAdd(null);
	    break;
	case R.id.actionbar_componentlist_item_back:
	    finish();
	    break;

	default:
	    return super.onOptionsItemSelected(item);
	}

	// invalidateOptionsMenu va appeller la méthode onPrepareOptionsMenu();
	invalidateOptionsMenu();
	return true;
    }

    // *************************************
} // end class