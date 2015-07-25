package com.rdupuis.amikcal.equivalence;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AmiKcalFactory;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.numericpad.Act_NumericPad;
import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.energy.Act_EnergyList;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.unity.Act_UnitOfMeasureList;

public class Act_EquivalenceEditor extends Activity {

    public final static String INPUT____ID_NRJ = "id_nrj";
    public final static String INPUT____INDX_EQUIV = "indx_equiv";
    private final int CHOOSE_NRJ = 1;
    private final int CHOOSE_UNITY_IN = 2;
    private final int CHOOSE_UNITY_OUT = 3;
    private final int CHOOSE_QTY_OUT = 4;

    Component edited_Equivalence;
    AmiKcalFactory factory;

    EnergySource nrj;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	// Etape 1 : initalisations
	super.onCreate(savedInstanceState);
	
	factory = new AmiKcalFactory(this);
	nrj = new EnergySource();
	// Etape 2 : prise en compte de l'Intent
	long nrj_id = this.getIntent().getLongExtra(this.INPUT____ID_NRJ, AppConsts.NO_ID);
	int equiv_indx = this.getIntent().getIntExtra(this.INPUT____INDX_EQUIV, AppConsts.NO_INDEX);

	if (nrj_id != AppConsts.NO_ID) {
	    nrj = factory.load_Energy(nrj_id);

	    //
	    edited_Equivalence = nrj.getReferenceComponent();
	    
	    //si on souhaite éditer une équivalence particulière, on la séléctionne
	    //sinon les zones sont vide pour en ajouter une nouvelle
	    if (equiv_indx != AppConsts.NO_INDEX) {
		this.edited_Equivalence = nrj.getReferenceComponent().getEquivalences().get(equiv_indx);
	    }

	}

	// Etape 3 : chargement de la vue
	setContentView(R.layout.view_edit_equivalence);

	// Etape 4 : rafraichissement des informations affichées
	refreshScreen();

    }// fin du onCreate

    /******************************************************************************************
     * onValidateClick : Mettre à jour les informations saisies dans la base de
     * donnée
     * 
     * @param v
     ******************************************************************************************/
    public void onClickBtnOK(View v) {

	// regarder si l'énergie possède une équivalence du même type que celle
	// édité
	// si oui on la met à jour en sauvant l'énergie
	// si non, on doit l'ajouter à la liste des équivalences avant de sauver
	// l'énergie

	int indx = this.nrj.getReferenceComponent().getEquivalences().indexOf(edited_Equivalence);
	if (indx == AppConsts.NO_INDEX) {
	    this.nrj.getReferenceComponent().getEquivalences().add(edited_Equivalence);
	}
	factory.save(this.nrj);

	// On prépare les informations à mettre à jour
	/*
	 * ContentValues val = new ContentValues();
	 * 
	 * 
	 * 
	 * val.put(ContentDescriptorObj.Equivalences.Columns.FK_ENERGY,
	 * this.mEquivalence.energy.getId());
	 * val.put(ContentDescriptorObj.Equivalences.Columns.FK_UNIT_IN,
	 * this.mEquivalence.unitIn.getId());
	 * val.put(ContentDescriptorObj.Equivalences.Columns.FK_UNIT_OUT,
	 * this.mEquivalence.unitOut.getId());
	 * val.put(ContentDescriptorObj.Equivalences.Columns.QUANTITY_OUT,
	 * this.mEquivalence.getQuantity());
	 * 
	 * if (this.mEquivalence.getId()==0){
	 * this.getContentResolver().insert(ContentDescriptorObj
	 * .Equivalences.URI_INSERT_EQUIVALENCE, val); } else {
	 * 
	 * Uri request =
	 * ContentUris.withAppendedId(ContentDescriptorObj.Equivalences
	 * .URI_UPDATE_EQUIVALENCE,this.mEquivalence.getId());
	 * this.getContentResolver().update(request, val, null,null); };
	 * 
	 * */
	  //on appelle setResult pour déclancher le onActivityResult de
	  //l'activity mère. 
	  setResult(RESULT_OK, this.getIntent());
	  
	  //On termine l'Actvity
	 finish();
	 
    }

    /******************************************************************************************
     * refreshScreen :
     * 
     ******************************************************************************************/
    private void refreshScreen() {

	TextView tv = (TextView) findViewById(R.id.view_equivalence_txtview_amount_in);
	tv.setText(String.valueOf(this.nrj.getReferenceComponent().getQty().getAmount()));
	
	Button ed = (Button) findViewById(R.id.view_equivalence_btn_energy);
	ed.setText(this.nrj.getName());

	ed = (Button) findViewById(R.id.view_equivalence_btn_unit_in);
	ed.setText(this.nrj.getReferenceComponent().getQty().getUnity().getLongName());

	ed = (Button) findViewById(R.id.view_equivalence_btn_unit_out);
	ed.setText(this.edited_Equivalence.getQty().getUnity().getLongName());

	ed = (Button) findViewById(R.id.view_equivalence_btn_quantity);
	ed.setText(String.valueOf(this.edited_Equivalence.getQty().getAmount()));

    }

    public void onClickBtnEnergy(View v) {
	Intent intent = new Intent(this, Act_EnergyList.class);
	startActivityForResult(intent, this.CHOOSE_NRJ);
    }

    public void onClickBtnUnitIn(View v) {
	Intent intent = new Intent(this, Act_UnitOfMeasureList.class);
	startActivityForResult(intent, this.CHOOSE_UNITY_IN);

    }

    public void onClickBtnUnitOut(View v) {
	Intent intent = new Intent(this, Act_UnitOfMeasureList.class);
	startActivityForResult(intent, this.CHOOSE_UNITY_OUT);

    }

    public void onClickBtnQuantity(View v) {
	Intent intent = new Intent(this, Act_NumericPad.class);
	intent.putExtra("question", "Entrez la quantité équivalente");
	startActivityForResult(intent, this.CHOOSE_QTY_OUT);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

	if (resultCode == RESULT_OK) {

	    switch (requestCode) {
	    case CHOOSE_QTY_OUT:

		this.edited_Equivalence.getQty().setAmount(
			intent.getFloatExtra(Act_NumericPad.OUTPUT____AMOUNT, 0f));
		break;

	    case CHOOSE_UNITY_OUT:
		long unity_out_id = intent.getLongExtra(Act_UnitOfMeasureList.OUTPUT____UNIT_ID, AppConsts.NO_ID);
		this.edited_Equivalence.getQty().setUnity(factory.load_Unity(unity_out_id));
		break;

	    case CHOOSE_UNITY_IN:

		long unity_in_id = intent.getLongExtra(Act_UnitOfMeasureList.OUTPUT____UNIT_ID, AppConsts.NO_ID);
		this.edited_Equivalence.getQty().setUnity(factory.load_Unity(unity_in_id));
		break;

	    case CHOOSE_NRJ:

		long nrj_id = intent.getLongExtra(Act_EnergyList.OUTPUT____ID_OF_ENERGY, AppConsts.NO_ID);
		this.nrj = factory.load_Energy(nrj_id);
		break;

	    default:
	    }

	    refreshScreen();

	}
    }
}