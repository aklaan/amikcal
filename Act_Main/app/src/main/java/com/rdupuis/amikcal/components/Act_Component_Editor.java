package com.rdupuis.amikcal.components;

/**
 * commentaires
 * 2013-08-23 : ajout de la barre de menu pour remplacer le bouton valider
 */

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AmiKcalFactory;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.AppConsts.REL_TYP_CD_MAP;
import com.rdupuis.amikcal.commons.Qty;
import com.rdupuis.amikcal.commons.numericpad.Act_NumericPad;
import com.rdupuis.amikcal.energy.Act_EnergyList;
import com.rdupuis.amikcal.energy.Food;
import com.rdupuis.amikcal.unity.Act_UnitOfMeasureList;
import com.rdupuis.amikcal.useractivity.UserActivity;

public class Act_Component_Editor extends Activity {

    UserActivity mUA;
    Component edited_Component;
    ContentResolver contentResolver;
    AmiKcalFactory factory;
    public static final String INPUT____COMP_ID = "in_comp_id";
    public static final String INPUT____CLASS = "in_class";
    public static final String OUTPUT____COMP_ID = "out_comp_id";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	factory = new AmiKcalFactory(this);

	/***************************************************************************
	 * ETAPE I : on r�cup�re les infos de l'intent
	 ***************************************************************************/

	// R�cup�rer l'id du composant � �diter
	long edited_comp_id = getIntent().getLongExtra(Act_Component_Editor.INPUT____COMP_ID, AppConsts.NO_ID);
	// en cas de cr�ation d'un nouveau composant, r�cup�rer le type de
	// composant souhait�
	String input_comp_class = getIntent().getStringExtra(Act_Component_Editor.INPUT____CLASS);

	// si l'ID n'est pas nul on charge le composant � �diter
	if (edited_comp_id != AppConsts.NO_ID) {
	    // chargement du composant stock�
	    this.edited_Component = factory.load_Component(edited_comp_id);

	} else {
	    // Initialisation d'un nouveau composant en fonction de la class
	    // souhait�e
	    REL_TYP_CD_MAP rel_typ_cd_map = new REL_TYP_CD_MAP();

	    switch (rel_typ_cd_map._in.get(input_comp_class)) {
	    case CFOOD:
		edited_Component = new Component_Food();
		break;
	    // default prend en charge les UNDEFINED
	    default:
		Toast.makeText(this, "type Component inconnu", Toast.LENGTH_LONG).show();
		finish();
	    }

	}

    }// fin du onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.actionbar_component_editor, menu);
	return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

	return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

	// Handle action buttons
	switch (item.getItemId()) {
	case R.id.actionbar_component_editor_item_validate:
	    onClick_Validate();

	default:
	    return super.onOptionsItemSelected(item);
	}
    }

    /**
     * Appel du pav� num�rique
     */
    public void callNumericPad() {
	Intent intent = new Intent(this, Act_NumericPad.class);
	intent.putExtra("question", "Entrez la quantit� d'Aliment");
	startActivityForResult(intent, R.integer.NUMERICPAD);
    }

    /***************************************************************************************
     * Appel de la liste des aliments
     * 
     * @param v
     *            View
     **************************************************************************************/
    public void onClick_EnergyName(View v) {
	Intent intent = new Intent(this, Act_EnergyList.class);
	startActivityForResult(intent, R.integer.ACTY_ENERGIES_LIST);
    }

    /***************************************************************************************
     * 
     * @param v
     *            View
     **************************************************************************************/
    public void onClick_Unit(View v) {
	callUnitListView();
    }

    /***************************************************************************************
     * 
     * Appel de la Liste des unit�es.
     **************************************************************************************/
    public void callUnitListView() {
	Intent intent = new Intent(this, Act_UnitOfMeasureList.class);
	intent.putExtra(Act_UnitOfMeasureList.INPUT____ENERGY_ID, String.valueOf(edited_Component.getEnergy().getId()));
	startActivityForResult(intent, R.integer.ACTY_UNITS_LIST);

    }

    /**
     * Mettre � jour les informations saisies dans la base de donn�e.
     * 
     * @param v
     *            View
     */
    public void onClick_Validate() {

	// A la cr�ation, le save va initialiser l'ID
	this.edited_Component = factory.save(this.edited_Component);

	// on appelle setResult pour d�clancher le onActivityResult de
	// l'activity m�re.

	this.getIntent().putExtra(Act_Component_Editor.OUTPUT____COMP_ID, this.edited_Component.getId());
	setResult(RESULT_OK, this.getIntent());

	// On termine l'Actvity
	finish();

    }

    /******************************************************************************************
     * G�re le retour d'appel aux autres activit�es
     * 
     * @param requestCode
     *            code fonction utilis� par l'activit� appellante
     * @param resultCode
     *            code retour envoy� par l'activit�e appell�e
     * @param intent
     *            Intent servant de lien entre l'activit�e appelante et appell�e
     ******************************************************************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

	switch (requestCode) {

	case R.integer.NUMERICPAD:

	    if (resultCode == RESULT_OK) {

		this.edited_Component.getQty().setAmount(intent.getFloatExtra(Act_NumericPad.OUTPUT____AMOUNT, 0f));

	    }
	    break;

	case R.integer.ACTY_UNITS_LIST:

	    if (resultCode == RESULT_OK) {

		// on r�cup�re l'objet Unit. this.mUAC
		this.edited_Component.getQty().setUnity(
			factory.load_Unity(intent
				.getLongExtra(Act_UnitOfMeasureList.OUTPUT____UNIT_ID, AppConsts.NO_ID)));
	    }
	    break;

	case R.integer.ACTY_ENERGIES_LIST:

	    if (resultCode == RESULT_OK) {

		// on r�cup�re l'Energy choisi par l'utilisateur d'apr�s sont id
		// .

		this.edited_Component.setEnergy(factory.load_Energy(intent.getLongExtra(
			Act_EnergyList.OUTPUT____ID_OF_ENERGY, AppConsts.NO_ID)));

	    }
	    break;
	default:
	    break;

	}
    }

}