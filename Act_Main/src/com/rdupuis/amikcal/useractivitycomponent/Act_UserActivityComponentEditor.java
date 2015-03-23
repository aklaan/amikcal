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
import com.rdupuis.amikcal.commons.numericpad.Act_NumericPad;
import com.rdupuis.amikcal.energy.Act_EnergyList;
import com.rdupuis.amikcal.unity.Act_UnitOfMeasureList;

public class Act_UserActivityComponentEditor extends Activity {

    UserActivityComponent mUAC;
    ContentResolver contentResolver;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	/***************************************************************************
	 * ETAPE I : on récupère les infos de l'intent
	 ***************************************************************************/

	long component_id = getIntent().getLongExtra(AppConsts.INPUT____USER_ACTIVITY_COMPONENT_EDITOR____COMPONENT_ID,
		AppConsts.NO_ID);

	// si l'Id composant de l'Intent est null, c'est que l'on souhaite créer
	// un nouveau composant
	// dans le cas contraire, on récupère les infos de la base
	// de données
	if (component_id != AppConsts.NO_ID) {
	    // chargement du composant stocké
	    load_UAC(component_id);

	} else {
	    // Initialisation d'un nouveau composant
	    mUAC = new UserActivityComponent();

	}

	// Un composant doit forcément appartenir à une UserActivity
	long parent_id = getIntent().getLongExtra(
		AppConsts.INPUT____USER_ACTIVITY_COMPONENT_EDITOR____ID_OF_PARENT_USER_ACTIVITY, AppConsts.NO_ID);

	if (parent_id == AppConsts.NO_ID) {
	    Toast.makeText(this, "Erreur ! Activité parente inconnue", Toast.LENGTH_LONG).show();
	    this.finish();
	} else {
	    load_UA(parent_id);
	}

	/****************************************************************************
	 * ETAPE II : on charge l'écran
	 ****************************************************************************/
	setContentView(R.layout.view_edit_food_component);

	/**
	 * ETAPE III : on initialise/Rafraichi les données de l'écran
	 */

	refreshScreen();

    }// fin du onCreate

    public void load_UAC(long _id) {
	AmiKcalFactory factory = new AmiKcalFactory(this);
	this.mUAC = factory.load_UAC(_id);

    }

    public void load_UA(long _id) {
	AmiKcalFactory factory = new AmiKcalFactory(this);
	this.mUAC.setUserActivity(factory.load_UserActivity(_id));

    }

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
     * Appel du pavé numérique
     */
    public void callNumericPad() {
	Intent intent = new Intent(this, Act_NumericPad.class);
	intent.putExtra("question", "Entrez la quantité d'Aliment");
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
     * Appel de la Liste des unitées.
     **************************************************************************************/
    public void callUnitListView() {
	Intent intent = new Intent(this, Act_UnitOfMeasureList.class);
	intent.putExtra(Act_UnitOfMeasureList.INPUT____ENERGY_ID,
		String.valueOf(mUAC.getEnergySource().getId()));
	startActivityForResult(intent, R.integer.ACTY_UNITS_LIST);

    }

    /**
     * Mettre à jour les informations saisies dans la base de donnée.
     * 
     * @param v
     *            View
     */
    public void onClick_Validate() {

	computeEnegy();
	// on prépare les informations à mettre à jour
	ContentValues val = new ContentValues();
	/*
	 * val.put(ContentDescriptorObj.ActivityComponent.Columns.FK_ENERGY,
	 * this.mUAC.getEnergySource().getId());
	 * val.put(ContentDescriptorObj.ActivityComponent.Columns.FK_UNIT,
	 * this.mUAC.getUnitMeasure().getId());
	 * val.put(ContentDescriptorObj.ActivityComponent.Columns.FK_PARENT,
	 * mUAC.getParentId());
	 * val.put(ContentDescriptorObj.ActivityComponent.Columns.QUANTITY,
	 * mUAC.getQuantity());
	 * val.put(ContentDescriptorObj.ActivityComponent.Columns.MNT_ENERGY,
	 * mUAC.getCalories());
	 * val.put(ContentDescriptorObj.ActivityComponent.Columns.MNT_GLUCIDS,
	 * mUAC.getGlucids());
	 * val.put(ContentDescriptorObj.ActivityComponent.Columns.MNT_PROTEINS,
	 * mUAC.getProteins());
	 * val.put(ContentDescriptorObj.ActivityComponent.Columns.MNT_LIPIDS,
	 * mUAC.getLipids());
	 * val.put(ContentDescriptorObj.ActivityComponent.Columns.LAST_UPDATE,
	 * ToolBox.getCurrentTimestamp());
	 */
	// si l'ID est NULL, on doit faire un INSERT sinon on fait un UPDATE

	/*
	 * if (this.mUAC.getId() == 0) { this.getContentResolver()
	 * .insert(ContentDescriptorObj
	 * .ActivityComponent.URI_INSERT_ACTIVITY_COMPONENT, val); } else {
	 * this.getContentResolver()
	 * .update(ContentDescriptorObj.ActivityComponent
	 * .URI_UPDATE_ACTIVITY_COMPONENT, val,
	 * String.valueOf(this.mUAC.getId()), null); } ;
	 */
	// on alimente le résultat dans l'Intent pour que l'Activity mère puisse
	// récupérer la valeur.
	Button bt = (Button) findViewById(R.id.componentview_btn_quantity);
	this.getIntent().putExtra("result", bt.getText());

	// on appelle setResult pour déclancher le onActivityResult de
	// l'activity mère.
	setResult(RESULT_OK, this.getIntent());

	// On termine l'Actvity
	finish();

    }

    /******************************************************************************************
     * Gère le retour d'appel aux autres activitées
     * 
     * @param requestCode
     *            code fonction utilisé par l'activité appellante
     * @param resultCode
     *            code retour envoyé par l'activitée appellée
     * @param intent
     *            Intent servant de lien entre l'activitée appelante et appellée
     ******************************************************************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

	AmiKcalFactory mFactory = new AmiKcalFactory(this);

	switch (requestCode) {

	case R.integer.NUMERICPAD:

	    if (resultCode == RESULT_OK) {

		this.mUAC.getQty().setAmount(
			Float.parseFloat(intent.getStringExtra(Act_NumericPad.OUTPUT____AMOUNT)));

		if (this.mUAC.getQty().getUnity().getId() == 0l) {
		    /* this.callUnitListView(); */
		}
	    }
	    break;

	case R.integer.ACTY_UNITS_LIST:

	    if (resultCode == RESULT_OK) {
		/*
		 * // on récupère l'objet Unit. this.mUAC
		 * .setUnitMeasure(mFactory
		 * .createUnitOfMeasureObjFromId(Long.parseLong
		 * (intent.getStringExtra(mResources
		 * .getString(R.string.INTENT_OUT____UNITS_LIST____ID_OF_THE_UNIT
		 * )))));
		 */}
	    break;

	case R.integer.ACTY_ENERGIES_LIST:

	    if (resultCode == RESULT_OK) {

		// on récupère l'objet Energy d'après l'id choisi par
		// l'utilisateur .
		/*
		 * this.mUAC
		 * .setEnergy(mFactory.createEnergyFromId(Long.parseLong
		 * (intent.getStringExtra(mResources
		 * .getString(R.string.INTENT_OUT____ENERGY_LIST____ID_OF_ENERGY
		 * )))));
		 * 
		 * if (this.mUAC.getQuantity() == 0f) {
		 * 
		 * this.callNumericPad(); }
		 */
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
     * Rafraichissement de l'écan
     */
    private void refreshScreen() {
	Button b = (Button) findViewById(R.id.componentview_btn_EnergyName);

	// Gestion du libellé sur le bouton Energy
	if (mUAC.getEnergySource().getName() == "") {
	    b.setText(this.getResources().getString(R.string.empty));
	} else {
	    b.setText(this.mUAC.getEnergySource().getName());
	}

	// Gestion du libellé sur le bouton Unit
	b = (Button) findViewById(R.id.componentview_btn_unit);

	if (mUAC.getQty().getUnity().getLongName() == "") {
	    b.setText(this.getResources().getString(R.string.empty));
	} else {
	    b.setText(this.mUAC.getQty().getUnity().getLongName());
	}

	// Gestion du libellé sur le bouton quantity
	b = (Button) findViewById(R.id.componentview_btn_quantity);
	b.setText(Float.toString(this.mUAC.getQty().getAmount()));

    }

    /**
     * Recherche d'une équivalence pour un aliment et une unité de mesure
     * 
     * @param energy
     *            (aliment)
     * @param unitIn
     *            (unité de départ)
     * @return une équivalence
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
     * // On crée un curseur pour lire la table. Cursor cur =
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
     * Toast.makeText(this, "Equivalence non trouvé", Toast.LENGTH_SHORT)
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
     * (calories/lipides/glucides/protéines)
     */
    private void computeEnegy() {
	/*
	 * float wQuantity = this.mUAC.getQuantity();
	 * 
	 * Log.d("mUAC.getQuantity", String.valueOf(this.mUAC.getQuantity()));
	 * 
	 * // Si la quantité d'aliment est exprimé dans une untitée // diffrénte
	 * de celle du référentiel des calories, on va rechercher //
	 * l'équivalence. if (this.mUAC.getEnergy().unit.getId() !=
	 * this.mUAC.getUnitMeasure() .getId()) { Toast.makeText(this,
	 * "Unitée de mesure différentes", Toast.LENGTH_SHORT).show();
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