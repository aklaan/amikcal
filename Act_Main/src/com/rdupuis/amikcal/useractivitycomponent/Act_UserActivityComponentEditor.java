package com.rdupuis.amikcal.useractivitycomponent;

/**
 * commentaires
 * 2013-08-23 : ajout de la barre de menu pour remplacer le bouton valider
 */

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AmiKcalFactory;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.Relation.REL_TYP_CD;
import com.rdupuis.amikcal.commons.numericpad.Act_NumericPad;
import com.rdupuis.amikcal.energy.Act_EnergyList;
import com.rdupuis.amikcal.unity.Act_UnitOfMeasureList;
import com.rdupuis.amikcal.useractivity.UserActivity;

public class Act_UserActivityComponentEditor extends Activity {

    UserActivity mUA;
    UserActivityComponent edited_UAC;
    ContentResolver contentResolver;
    AmiKcalFactory factory;
    public static final String INPUT____UA_ID = "ua_id";
    public static final String INPUT____UAC_INDEX = "uac_indx";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	factory = new AmiKcalFactory(this);

	/***************************************************************************
	 * ETAPE I : on r�cup�re les infos de l'intent
	 ***************************************************************************/

	// R�cup�rer l'id de UA des composants � traiter
	long input_UA_id = getIntent().getLongExtra(this.INPUT____UA_ID, AppConsts.NO_ID);

	// Un composant doit forc�ment appartenir � une UserActivity
	if (input_UA_id == AppConsts.NO_ID) {
	    Toast.makeText(this, "Erreur ! Activit� parente inconnue", Toast.LENGTH_LONG).show();
	    this.finish();
	} else {
	    mUA = factory.load_UserActivity(input_UA_id);
	}

	int component_index = getIntent().getIntExtra(this.INPUT____UAC_INDEX, AppConsts.NO_INDEX);

	// si l'indexe du composant � �diter est null, c'est que l'on souhaite
	// cr�er
	// un nouveau composant
	// dans le cas contraire, on r�cup�re les infos de la base
	// de donn�es
	if (component_index != AppConsts.NO_INDEX) {
	    // chargement du composant stock�

	    this.edited_UAC = mUA.getUAC_List().get(component_index);

	} else {
	    // Initialisation d'un nouveau composant
	    edited_UAC = new UserActivityComponent();
	    switch (mUA.getType()) {
	    case LUNCH:
		this.edited_UAC.setUAC_Class(REL_TYP_CD.UAC_FOOD);
		break;

	    case WEIGHT:
		this.edited_UAC.setUAC_Class(REL_TYP_CD.UAC_WEIGHT);
		break;

	    case MOVE:
		this.edited_UAC.setUAC_Class(REL_TYP_CD.UAC_MOVE);
		break;

	    default:
		this.edited_UAC.setUAC_Class(REL_TYP_CD.UNDEFINED);
		break;
	    }

	}

	/****************************************************************************
	 * ETAPE II : on charge l'�cran
	 ****************************************************************************/
	setContentView(R.layout.view_edit_food_component);

	/**
	 * ETAPE III : on initialise/Rafraichi les donn�es de l'�cran
	 */

	refreshScreen();

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

    /***************************************************************************************
     * 
     * @param v
     *            View
     **************************************************************************************/
    public void onClick_Quantity(View v) {
	callNumericPad();
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
	intent.putExtra(Act_UnitOfMeasureList.INPUT____ENERGY_ID, String.valueOf(edited_UAC.getEnergySource().getId()));
	startActivityForResult(intent, R.integer.ACTY_UNITS_LIST);

    }

    /**
     * Mettre � jour les informations saisies dans la base de donn�e.
     * 
     * @param v
     *            View
     */
    public void onClick_Validate() {

	// computeEnegy();

	// si on a cr�� une nouvelle UAC i lfaut l'ajouter � l'UA
	if (mUA.getUAC_List().indexOf(edited_UAC) == AppConsts.NO_INDEX) {
	    mUA.getUAC_List().add(edited_UAC);
	}
	;

	factory.save(mUA);

	// on appelle setResult pour d�clancher le onActivityResult de
	// l'activity m�re.
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

		this.edited_UAC.getQty().setAmount(
			Float.parseFloat(intent.getStringExtra(Act_NumericPad.OUTPUT____AMOUNT)));
	
	    }
	    break;

	case R.integer.ACTY_UNITS_LIST:

	    if (resultCode == RESULT_OK) {
		
		
		 // on r�cup�re l'objet Unit. this.mUAC
		 this.edited_UAC.getQty().setUnity(factory.load_Unity(Long.parseLong
		 (intent.getStringExtra(Act_UnitOfMeasureList.OUTPUT____UNIT_ID))));
		}
	    break;

	case R.integer.ACTY_ENERGIES_LIST:

	    if (resultCode == RESULT_OK) {

		// on r�cup�re l'Energy choisi par l'utilisateur d'apr�s sont id
		// .

		this.edited_UAC.setEnergySource(factory.load_Energy(Long.parseLong(intent
			.getStringExtra(Act_EnergyList.OUTPUT____ID_OF_ENERGY))));

	    }
	    break;
	default:
	    break;

	}
	refreshScreen();
    }

    /******************************************************************************************
     * 
     * @param _id
     *            Identifiant du composant
     ******************************************************************************************/

    /**
     * Rafraichissement de l'�can
     */
    private void refreshScreen() {
	Button b = (Button) findViewById(R.id.componentview_btn_EnergyName);

	// Gestion du libell� sur le bouton Energy
	if (edited_UAC.getEnergySource().getName() == "") {
	    b.setText(this.getResources().getString(R.string.empty));
	} else {
	    b.setText(this.edited_UAC.getEnergySource().getName());
	}

	// Gestion du libell� sur le bouton Unit
	b = (Button) findViewById(R.id.componentview_btn_unit);

	if (edited_UAC.getQty().getUnity().getLongName() == "") {
	    b.setText(this.getResources().getString(R.string.empty));
	} else {
	    b.setText(this.edited_UAC.getQty().getUnity().getLongName());
	}

	// Gestion du libell� sur le bouton quantity
	b = (Button) findViewById(R.id.componentview_btn_quantity);
	b.setText(Float.toString(this.edited_UAC.getQty().getAmount()));

    }

    /**
     * Recherche d'une �quivalence pour un aliment et une unit� de mesure
     * 
     * @param energy
     *            (aliment)
     * @param unitIn
     *            (unit� de d�part)
     * @return une �quivalence
     */

    /*
     * private EquivalenceObj findEquivalence(EnergyReference energy, Unity
     * unitIn) {
     * 
     * EquivalenceObj mEquivalence = new EquivalenceObj(); String searchKey =
     * String.valueOf(energy.getId()) + "+" + String.valueOf(unitIn.getId());
     * 
     * // On fabrique l'Uri pour le contentProvider Uri selectUri =
     * ContentDescriptorObj.Equivalences.URI_SEARCH_EQUIVALENCE
     * .buildUpon().appendPath(searchKey).build();
     * 
     * // On cr�e un curseur pour lire la table. Cursor cur =
     * this.getContentResolver().query(selectUri, null, null, null, null);
     * 
     * final int INDX_ID = cur
     * .getColumnIndex(ContentDescriptorObj.Equivalences.Columns.ID);
     * 
     * // faire un move First pour positionner le pointeur, sinon on pointe sur
     * // null if (cur.moveToFirst()) {
     * 
     * AmiKcalFactory factory = new AmiKcalFactory(); factory.contentResolver =
     * this.contentResolver; mEquivalence =
     * factory.createEquivalenceObjFromId(cur .getLong(INDX_ID)); } else {
     * Toast.makeText(this, "Equivalence non trouv�", Toast.LENGTH_SHORT)
     * .show();
     * 
     * }
     * 
     * return mEquivalence;
     * 
     * }
     */
    /**
     * Calcul des informations nutitionelles
     * (calories/lipides/glucides/prot�ines)
     */
    private void computeEnegy() {
	/*
	 * float wQuantity = this.mUAC.getQuantity();
	 * 
	 * Log.d("mUAC.getQuantity", String.valueOf(this.mUAC.getQuantity()));
	 * 
	 * // Si la quantit� d'aliment est exprim� dans une untit�e // diffr�nte
	 * de celle du r�f�rentiel des calories, on va rechercher //
	 * l'�quivalence. if (this.mUAC.getEnergy().unit.getId() !=
	 * this.mUAC.getUnitMeasure() .getId()) { Toast.makeText(this,
	 * "Unit�e de mesure diff�rentes", Toast.LENGTH_SHORT).show();
	 * 
	 * EquivalenceObj eq = findEquivalence(mUAC.getEnergy(),
	 * mUAC.getUnitMeasure());
	 * 
	 * Log.d("eq.getQuantity", String.valueOf(eq.getQuantity()));
	 * 
	 * wQuantity = eq.getQuantity() * wQuantity; }
	 * 
	 * Log.i("wQuantity", String.valueOf(wQuantity));
	 * 
	 * this.mUAC.setCalories((wQuantity * this.mUAC.getEnergy()
	 * .getCalories()) / this.mUAC.getEnergy().getQuantityReference());
	 * this.mUAC.setLipids((wQuantity * this.mUAC.getEnergy().getLipids()) /
	 * this.mUAC.getEnergy().getQuantityReference()); this.mUAC
	 * .setGlucids((wQuantity * this.mUAC.getEnergy().getGlucids()) /
	 * this.mUAC.getEnergy().getQuantityReference());
	 * this.mUAC.setProteins((wQuantity * this.mUAC.getEnergy()
	 * .getProteins()) / this.mUAC.getEnergy().getQuantityReference());
	 */
    }

}