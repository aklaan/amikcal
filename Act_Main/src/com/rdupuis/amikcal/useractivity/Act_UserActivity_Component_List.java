package com.rdupuis.amikcal.useractivity;

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
import com.rdupuis.amikcal.components.Act_Component_Editor;
import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.components.Component_Action;
import com.rdupuis.amikcal.components.Component_Food;
import com.rdupuis.amikcal.components.Component_Move;

public class Act_UserActivity_Component_List extends Activity {

    public UserActivity mUA; // la liste se réfère obligatoirement à une UA
    private ListView maListViewPerso;
    private AmiKcalFactory factory;
    // Noms des zones d'échanges prévues dans l'Intent
    public static final String INPUT____UA_ID = "ua_id";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	this.factory = new AmiKcalFactory(this);

	/* *********************************************
	 * Etape 1 : Chargement des données
	 * *********************************************
	 */

	// - récupérer l'UA pour laquelle on souhaites afficher des composants
	long ua_id = Long.parseLong(this.getIntent().getStringExtra(this.INPUT____UA_ID));
	this.mUA = factory.load_UserActivity(ua_id);

	/* *********************************************
	 * Etape 2 : Chargement du Layer d'affichage
	 * *********************************************
	 */

	setContentView(R.layout.view_components_list);
	// Récupération de la listview créée dans le fichier customizedlist.xml

	/* *********************************************
	 * Etape 3 : Alimentation des zones d'affichage
	 * *********************************************
	 */
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

    public void onClickComponent(Component component) {
	Component_Action action = this.factory.createComponentAction(this, component);
	action.edit();
    }

    // créer un nouveau composant.
    public void onClickAdd(View v) {

	switch (this.mUA.getActivityType()) {
	case LUNCH:
	    onClickComponent(new Component_Food());
	    break;
	case MOVE:
	    onClickComponent(new Component_Move());
	    break;
	case WEIGHT:
	    // onClickComponent(new Component_Weight());
	    break;
	default:
	    break;
	}

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

	// on effectue un retour de l'éditeur de composants
	// il faut ajouter le composant à l'UA s'il n'existe pas
	case R.integer.COMPONENT_EDITOR:

	    if (resultCode == RESULT_OK) {
		long component_id = intent.getLongExtra(Act_Component_Editor.OUTPUT____COMP_ID, AppConsts.NO_ID);
		// si au retour de léditeur, le composant possède un ID
		// c'est qu'il a été enregistré dans la database
		if (component_id != AppConsts.NO_ID) {
		    // on recharge le composant
		    Component edited_component = factory.load_Component(component_id);

		    // on recherche si le composant est déja lié à l'UA
		    boolean Linked_with_UA = false;

		    for (Component component : this.mUA.getComponentsList()) {

			if (component.getId() == component_id) {
			    Linked_with_UA = true;
			}
		    }
		    // si le composant n'est pas lié à l'UA, on le lie.
		    if (!Linked_with_UA) {
			this.mUA.getComponentsList().add(edited_component);
			this.factory.save(this.mUA);
		    }
		}

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
	// recharger les modification qui ont pu être effectué sur l'UA
	//this.mUA = factory.load_UserActivity(this.mUA.getId());

	// effacer la liste actuelle
	maListViewPerso.removeAllViewsInLayout();

	// Création de la ArrayList qui nous permettra de remplir la listView
	ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

	// On déclare la HashMap qui contiendra les informations pour un item
	HashMap<String, String> map;
	map = new HashMap<String, String>();

	// Pour chaque Composant de L'UA
	for (Component comp : this.mUA.getComponentsList()) {

	    map = new HashMap<String, String>();
	    map.put("COMPONENT_ID", String.valueOf(comp.getId()));
	    map.put("name", comp.getEnergy().getName());
	    map.put("quantity", String.valueOf(comp.getQty().getAmount()));
	    map.put("unity", comp.getQty().getUnity().getLongName());

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

		long component_id = Long.parseLong(map.get("COMPONENT_ID"));

		onClickComponent(Act_UserActivity_Component_List.this.factory.load_Component(component_id));
		// onClickComponent(Integer.parseInt(map.get("UAC_INDEX")));

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