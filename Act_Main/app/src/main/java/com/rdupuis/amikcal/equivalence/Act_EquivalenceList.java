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
import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.energy.EnergySource;

public class Act_EquivalenceList extends Activity {

    private ListView maListViewPerso;
    // pour filter la liste des �quivalences sur une �nergie donn�e.
    public final static String INTPUT____NRJ_ID = "nrj_id";
    private EnergySource nrj;
    public ArrayList<Component> equivalences;
    private AmiKcalFactory factory;
    public Component selected_equiv;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	// Etape 0 : initialitations
	super.onCreate(savedInstanceState);
	factory = new AmiKcalFactory(this);
	equivalences = new ArrayList<Component>();
	this.nrj = new EnergySource();
	// Etape 1 : R�cup�rer les �ventuelles informations de l'intent
	long nrj_id = this.getIntent().getLongExtra(this.INTPUT____NRJ_ID,AppConsts.NO_ID);
	
	if (nrj_id != AppConsts.NO_ID) {
	    nrj = factory.load_Energy(nrj_id);
	    this.equivalences = nrj.getReferenceComponent().getEquivalences();
	}

	// etape 2 charger la vue
	setContentView(R.layout.view_equivalences_list);

	// etape 3 alimenter l'affichage

	generateList();
	getActionBar().setTitle("Equivalences");
	// Button bt = (Button) findViewById(R.id.btnAdd);
	// bt.setText("Ajouter une unit�e");
    }// fin du onCreate



    public void onClickAdd(View v) {

	this.editEquivalence(this.nrj.getId(),AppConsts.NO_INDEX);

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

	// recharger les modification qui ont pu �tre effectu�es
	if (this.nrj.getId() != AppConsts.NO_ID) {
	    this.nrj = factory.load_Energy(nrj.getId());
	    equivalences = nrj.getReferenceComponent().getEquivalences();
	}

	// R�cup�ration de la listview cr��e dans le fichier customizedlist.xml
	maListViewPerso = (ListView) findViewById(R.id.listviewperso);

	// effacer la liste actuelle
	maListViewPerso.removeAllViewsInLayout();

	// Cr�ation de la ArrayList qui nous permettra de remplir la listView
	ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

	// On d�clare la HashMap qui contiendra les informations pour un item
	HashMap<String, String> map;
	map = new HashMap<String, String>();

	// Pour chaque UAC de L'UA
	for (Component equiv : this.equivalences) {

	    map = new HashMap<String, String>();
	    map.put("NRJ_ID", String.valueOf(this.nrj.getId()));
	    map.put("EQUIV_INDEX", String.valueOf(this.equivalences.indexOf(equiv)));

	    map.put("quantity", String.valueOf(equiv.getQty().getAmount()));
	    map.put("unity", equiv.getQty().getUnity().getLongName());

	    listItem.add(map);
	}

	// Cr�ation d'un SimpleAdapter qui se chargera de mettre les items
	// pr�sent dans notre list (listItem)
	// dans la vue affichageitem
	SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.list_item_equivalence,
		new String[] { "energy_name", "unit_in_name", "unit_out_name" }, new int[] {
			R.id.equivalence_item_tv_EnergyName, R.id.equivalence_item_tv_UnitIn,
			R.id.equivalence_item_tv_UnitOut });

	// On attribut � notre listView l'adapter que l'on vient de cr�er
	maListViewPerso.setAdapter(mSchedule);

	// ********************************************************
	// on met un �couteur d'�v�nement sur notre listView
	// ********************************************************
	maListViewPerso.setOnItemClickListener(new OnItemClickListener() {
	    // @Override
	    @SuppressWarnings("unchecked")
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// on r�cup�re la HashMap contenant les infos de notre item
		// (titre, description, img)
		HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
		// OnClick(view, map.get("id"));
	    }
	});

	// ********************************************************
	// on met un �couteur d'�v�nement sur notre listView
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
		// cr�ation d'un boite de dialogue pour confirmer le choix
		new AlertDialog.Builder(Act_EquivalenceList.this).setTitle("Confirmation")
			.setMessage("Que souhaitez-vous faire ?")
			.setPositiveButton("Editer", new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int whichButton) {
				/* User clicked edit so do some stuff */
				onClick_edit(Act_EquivalenceList.this.nrj.getId(),
					Act_EquivalenceList.this.nrj.getReferenceComponent().getEquivalences().indexOf(Act_EquivalenceList.this.selected_equiv));

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

	// on alimente le r�sultat dans l'Intent pour que l'Activity m�re puisse
	// r�cup�rer la valeur.
	// this.getIntent().putExtra(this.OUTPUT____EQUIV_LIST____EQUIV_ID, id);

	// on appelle setResult pour d�clancher le onActivityResult de
	// l'activity m�re.
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

	// invalidateOptionsMenu va appeller la m�thode onPrepareOptionsMenu();
	// qui va elle m�me rafraichir la barre de menu
	invalidateOptionsMenu();
	return true;
    }

    // *************************************
} // end class