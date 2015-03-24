package com.rdupuis.amikcal.energy;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AmiKcalFactory;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.AppConsts.NRJ_EFFECT_MAP;
import com.rdupuis.amikcal.commons.AppConsts.STRUCTURE_CD_MAP;
import com.rdupuis.amikcal.commons.Qty;
import com.rdupuis.amikcal.commons.Relation.REL_TYP_CD;
import com.rdupuis.amikcal.commons.numericpad.Act_NumericPad;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.unity.Act_UnitOfMeasureList;
import com.rdupuis.amikcal.unity.Unity.UNIT_CLASS;

/**
 * Cette vue permet de renseigner une nouvelle energie et ses equivalences
 * 
 * @author Rodolphe
 * 
 */
public class Act_EnergyEditor extends Activity {

    EnergySource mEnergy;
    public static final String INPUT____ID_OF_ENERGY = "NRJ_ID";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.view_edit_energy_food);
	mEnergy = new EnergySource();

	try {
	    AmiKcalFactory factory = new AmiKcalFactory(this);
	    mEnergy = factory.load_Energy(Long.parseLong(getIntent().getStringExtra(INPUT____ID_OF_ENERGY)));
	} catch (Exception e) {
	    Toast.makeText(this, "Editer Nouvelle energie", Toast.LENGTH_LONG).show();
	    mEnergy.setId(AppConsts.NO_ID);
	}

	((TextView) findViewById(R.id.energyview_edTxt_energy_name)).addTextChangedListener(new TextWatcher() {
	    public void onTextChanged(CharSequence s, int start, int before, int count) {
		mEnergy.setName(s.toString());
	    }

	    public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub

	    }

	    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub

	    }

	});

	refreshScreen();

    }// fin du onCreate

    /***************************************************************************************
     * 
     * @param v
     **************************************************************************************/
    public void onClick_Quantity(View v) {
	Intent intent = new Intent(this, Act_NumericPad.class);
	intent.putExtra("question", "Entrez la quantité de référence");
	startActivityForResult(intent, R.integer.BTN_QUANTITY_OF_ENERGY_VIEW_CALL_NUMERICPAD);
    }

    /***************************************************************************************
     * 
     * @param v
     **************************************************************************************/
    public void onClick_MntEnergy(View v) {
	Intent intent = new Intent(this, Act_NumericPad.class);
	intent.putExtra("question", "Entrez le nomnre de Kilo Calories");
	startActivityForResult(intent, R.integer.BTN_MNT_ENERGY_OF_ENERGY_VIEW_CALL_NUMERICPAD);
    }

    /***************************************************************************************
     * 
     * @param v
     **************************************************************************************/
    public void onClick_MntProteins(View v) {
	Intent intent = new Intent(this, Act_NumericPad.class);
	intent.putExtra("question", "Entrez la quantité de Protéines");
	startActivityForResult(intent, R.integer.BTN_MNT_PROTEINS_OF_ENERGY_VIEW_CALL_NUMERICPAD);
    }

    /***************************************************************************************
     * 
     * @param v
     **************************************************************************************/
    public void onClick_MntLipids(View v) {
	Intent intent = new Intent(this, Act_NumericPad.class);
	intent.putExtra("question", "Entrez la quantité Lipides");
	startActivityForResult(intent, R.integer.BTN_MNT_LIPIDS_OF_ENERGY_VIEW_CALL_NUMERICPAD);
    }

    /***************************************************************************************
     * 
     * @param v
     **************************************************************************************/
    public void onClick_MntGlucids(View v) {
	Intent intent = new Intent(this, Act_NumericPad.class);
	intent.putExtra("question", "Entrez la quantité de Glucides");
	startActivityForResult(intent, R.integer.BTN_MNT_GLUCIDS_OF_ENERGY_VIEW_CALL_NUMERICPAD);

    }

    /***************************************************************************************
     * 
     * @param v
     **************************************************************************************/
    public void onClick_Unit(View v) {
	Intent intent = new Intent(this, Act_UnitOfMeasureList.class);
	startActivityForResult(intent, R.integer.ACTY_UNITS_LIST);

    }

    /******************************************************************************************
     * onActivityResult : on récupère les info saisies dans le pad numérique.
     * 
     ******************************************************************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

	switch (requestCode) {

	case R.integer.BTN_MNT_ENERGY_OF_ENERGY_VIEW_CALL_NUMERICPAD:

	    if (resultCode == RESULT_OK) {

		Qty nbKcal = new Qty();
		nbKcal.setAmount(Float.parseFloat(intent.getStringExtra(Act_NumericPad.OUTPUT____AMOUNT)));
		// récupérer l'id de l'unité "Kcal"

		// Ajouter l'équivalence
		this.mEnergy.getEquivalences().add(nbKcal);
	    }
	    break;

	case R.integer.BTN_MNT_GLUCIDS_OF_ENERGY_VIEW_CALL_NUMERICPAD:

	    if (resultCode == RESULT_OK) {

		// this.mEnergy.setGlucids(Float.parseFloat(intent
		// .getStringExtra(getResources().getString(
		// R.string.INTENT_OUT____NUMERICPAD_RESULT))));
	    }
	    break;

	case R.integer.BTN_MNT_LIPIDS_OF_ENERGY_VIEW_CALL_NUMERICPAD:

	    if (resultCode == RESULT_OK) {

		// this.mEnergy.setLipids(Float.parseFloat(intent
		// .getStringExtra(getResources().getString(
		// R.string.INTENT_OUT____NUMERICPAD_RESULT))));
	    }
	    break;

	case R.integer.BTN_MNT_PROTEINS_OF_ENERGY_VIEW_CALL_NUMERICPAD:

	    if (resultCode == RESULT_OK) {

		// this.mEnergy.setProteins(Float.parseFloat(intent
		// .getStringExtra(getResources().getString(
		// R.string.INTENT_OUT____NUMERICPAD_RESULT))));
	    }
	    break;

	case R.integer.BTN_QUANTITY_OF_ENERGY_VIEW_CALL_NUMERICPAD:

	    if (resultCode == RESULT_OK) {

		this.mEnergy.getQtyReference().setAmount(
			Float.parseFloat(intent.getStringExtra(Act_NumericPad.OUTPUT____AMOUNT)));

	    }
	    break;

	case R.integer.ACTY_UNITS_LIST:

	    if (resultCode == RESULT_OK) {

		AmiKcalFactory factory = new AmiKcalFactory(this);

		mEnergy.getQtyReference().setUnity(
			factory.load_Unity(Long.parseLong(intent
				.getStringExtra(Act_UnitOfMeasureList.OUTPUT____UNIT_ID))));

	    }
	    break;
	default:
	    break;

	}

	refreshScreen();
    }

    /******************************************************************************************
     * onValidateClick : Mettre à jour les informations saisies dans la base de
     * donnée
     * 
     * @param v
     ******************************************************************************************/
    public void onClick_Validate(View v) {

	EditText ed = (EditText) findViewById(R.id.energyview_edTxt_energy_name);
	this.mEnergy.setName(ed.getText().toString());

	AmiKcalFactory factory = new AmiKcalFactory(this);

	factory.save(this.mEnergy);

	// on appelle setResult pour déclancher le onActivityResult de
	// l'activity mère.
	setResult(RESULT_OK, getIntent());

	// On termine l'Actvity
	finish();
    }

    /***************************************************************************************
     * 

     **************************************************************************************/
    private void refreshScreen() {

	DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance();
	decimalFormat.applyPattern("#,##0.00");
	DecimalFormatSymbols dfs = new DecimalFormatSymbols();
	dfs.setDecimalSeparator(',');
	decimalFormat.setDecimalFormatSymbols(dfs);

	// bt.setText(decimalFormat.format(this.mnt_quantity).toString());

	EditText ed = (EditText) findViewById(R.id.energyview_edTxt_energy_name);
	ed.setText(this.mEnergy.getName());

	Button bt = (Button) findViewById(R.id.energyview_btn_quantity);
	bt.setText(String.valueOf(this.mEnergy.getQtyReference().getAmount()));

	bt = (Button) findViewById(R.id.energyview_btn_unit);
	bt.setText(this.mEnergy.getQtyReference().getUnity().getLongName());

	/*
	 * 
	 * Button bt = (Button) findViewById(R.id.energyview_btn_mnt_energy);
	 * bt.setText(Float.toString(this.mEnergy.getCalories()));
	 * 
	 * 
	 * bt = (Button) findViewById(R.id.energyview_btn_mnt_glucids);
	 * bt.setText(Float.toString(this.mEnergy.getGlucids()));
	 * 
	 * bt = (Button) findViewById(R.id.energyview_btn_mnt_lipids);
	 * bt.setText(Float.toString(this.mEnergy.getLipids()));
	 * 
	 * bt = (Button) findViewById(R.id.energyview_btn_mnt_proteins);
	 * bt.setText(Float.toString(this.mEnergy.getProteins()));
	 */

    }

    /***************************************************************************************
     * 
     * @param v
     **************************************************************************************/
    public void onClick_Cancel(View v) {
	finish();
    }

}