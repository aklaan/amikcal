package com.rdupuis.amikcal.equivalence;

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

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AmiKcalFactory;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.Qty;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.useractivitycomponent.UserActivityComponent;

public class Act_EquivalenceList extends Activity {

    private ListView maListViewPerso;
    // pour filter la liste des équivalences sur une énergie donnée.
    public final static String INTPUT____NRJ_ID = "nrj_id";
    private EnergySource nrj;
    public ArrayList<Equivalence> equivalences;
    private AmiKcalFactory factory;
    public Equivalence selected_equiv;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	// Etape 0 : initialitations
	super.onCreate(savedInstanceState);
	factory = new AmiKcalFactory(this);
	equivalences = new ArrayList<Equivalence>();
	this.nrj = new EnergySource();
	// Etape 1 : Récupérer les éventuelles informations de l'intent
	String _id = this.getIntent().getStringExtra(this.INTPUT____NRJ_ID);
	long nrj_id = (_id != null) ? Long.parseLong(_id) : AppConsts.NO_ID;

	if (nrj_id != AppConsts.NO_ID) {
	    nrj = factory.load_Energy(nrj_id);
	    this.equivalences = nrj.getEquivalences();
	}

	// etape 2 charger la vue
	setContentView(R.layout.view_equivalences_list);

	// etape 3 alimenter l'affichage

	generateList();
	getActionBar().setTitle("Equivalences");
	// Button bt = (Button) findViewById(R.id.btnAdd);
	// bt.setText("Ajouter une unitée");
    }// fin du onCreate



    public void onClickAdd(View v) {

	this.editEquivalence(this.nrj.getId(),AppConsts.NO_INDEX);

    }

    /**
     * Gérer les retours d'appels aux autres activitées
     * 
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

	switch (requestCode) {

	case R.integer.ACTY_UNIT:

	    if (resultCode == RESULT_OK) {

		cleanList();
		generateList();

	    }
	    break;
	default:
	    break;

	}
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

    protected void generateList() {

	// recharger les modification qui ont pu être effectuées
	if (this.nrj.getId() != AppConsts.NO_ID) {
	    this.nrj = factory.load_Energy(nrj.getId());
	    equivalences = nrj.getEquivalences();
	}

	// Récupération de la listview créée dans le fichier customizedlist.xml
	maListViewPerso = (ListView) findViewById(R.id.listviewperso);

	// effacer la liste actuelle
	maListViewPerso.removeAllViewsInLayout();

	// Création de la ArrayList qui nous permettra de remplir la listView
	ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

	// On déclare la HashMap qui contiendra les informations pour un item
	HashMap<String, String> map;
	map = new HashMap<String, String>();

	// Pour chaque UAC de L'UA
	for (Equivalence equiv : this.equivalences) {

	    map = new HashMap<String, String>();
	    map.put("NRJ_ID", String.valueOf(this.nrj.getId()));
	    map.put("EQUIV_INDEX", String.valueOf(this.equivalences.indexOf(equiv)));

	    map.put("quantity", String.valueOf(equiv.getQuantityOut().getAmount()));
	    map.put("unity", equiv.getQuantityOut().getUnity().getLongName());

	    listItem.add(map);
	}

	// Création d'un SimpleAdapter qui se chargera de mettre les items
	// présent dans notre list (listItem)
	// dans la vue affichageitem
	SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.list_item_equivalence,
		new String[] { "energy_name", "unit_in_name", "unit_out_name" }, new int[] {
			R.id.equivalence_item_tv_EnergyName, R.id.equivalence_item_tv_UnitIn,
			R.id.equivalence_item_tv_UnitOut });

	// On attribut à notre listView l'adapter que l'on vient de créer
	maListViewPerso.setAdapter(mSchedule);

	// ********************************************************
	// on met un écouteur d'évènement sur notre listView
	// ********************************************************
	maListViewPerso.setOnItemClickListener(new OnItemClickListener() {
	    // @Override
	    @SuppressWarnings("unchecked")
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// on récupère la HashMap contenant les infos de notre item
		// (titre, description, img)
		HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
		// OnClick(view, map.get("id"));
	    }
	});

	// ********************************************************
	// on met un écouteur d'évènement sur notre listView
	// ********************************************************
	maListViewPerso.setOnItemLongClickListener(new OnItemLongClickListener() {
	    // @Override
	    @SuppressWarnings({ "rawtypes", "unchecked" })
	    public boolean onItemLongClick(AdapterView parent, View v, int position, long id) {
		HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
		int indx = Integer.parseInt(map.get("EQUIV_INDEX"));
		
		Act_EquivalenceList.this.selected_equiv = Act_EquivalenceList.this.equivalences.get(indx);
		// modifier
		// Act_EquivalenceList.currentId =
		// Long.parseLong(map.get("id"));
		// création d'un boite de dialogue pour confirmer le choix
		new AlertDialog.Builder(Act_EquivalenceList.this).setTitle("Confirmation")
			.setMessage("Que souhaitez-vous faire ?")
			.setPositiveButton("Editer", new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int whichButton) {
				/* User clicked edit so do some stuff */
				onClick_edit(Act_EquivalenceList.this.nrj.getId(),
					Act_EquivalenceList.this.nrj.getEquivalences().indexOf(Act_EquivalenceList.this.selected_equiv));

			    }
			}).setNegativeButton("Supprimer", new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int whichButton) {
				/* User delete Cancel so do some stuff */
			    }
			}).setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int whichButton) {
				/* User clicked Cancel so do some stuff */
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

    public void OnClick(View v, String id) {

	// on alimente le résultat dans l'Intent pour que l'Activity mère puisse
	// récupérer la valeur.
	// this.getIntent().putExtra(this.OUTPUT____EQUIV_LIST____EQUIV_ID, id);

	// on appelle setResult pour déclancher le onActivityResult de
	// l'activity mère.
	// this.setResult(RESULT_OK, this.getIntent());

	// On termine l'Acitvity
	// this.finish();
    }

    /*********************************************************************************
     * 
     * 
     **********************************************************************************/
    public void OnDelete(Long id) {

	// afficher une demande de confirmation

    }

    public void onClick_edit(long nrj_id, int indx_equiv) {
	editEquivalence(nrj_id,indx_equiv);
    }

    public void editEquivalence(long nrj_id, int indx_equiv) {
	Intent intent = new Intent(this, Act_EquivalenceEditor.class);
	intent.putExtra(Act_EquivalenceEditor.INPUT____INDX_EQUIV, String.valueOf(indx_equiv));
	intent.putExtra(Act_EquivalenceEditor.INPUT____ID_NRJ, String.valueOf(nrj_id));
	startActivityForResult(intent, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.actionbar_equivalence_list, menu);
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
	case R.id.actionbar_equivalencelist_item_add:
	    onClickAdd(null);
	    break;
	case R.id.actionbar_equivalencelist_item_back:
	    finish();
	    break;

	default:
	    return super.onOptionsItemSelected(item);
	}

	// invalidateOptionsMenu va appeller la méthode onPrepareOptionsMenu();
	// qui va elle même rafraichir la barre de menu
	invalidateOptionsMenu();
	return true;
    }

    // *************************************
} // end class