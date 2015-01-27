package com.rdupuis.amikcal.equivalence;




import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AmiKcalFactory;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.energy.Act_EnergyList;
import com.rdupuis.amikcal.commons.numericpad.Act_NumericPad;

import com.rdupuis.amikcal.unitofmeasure.Act_UnitOfMeasureList;


 

public class Act_EquivalenceEditor extends Activity {
 
	
     Intent mIntent;
     EquivalenceObj mEquivalence;
     Resources mResources;
     ContentResolver contentResolver;
     
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_edit_equivalence);
        
        mEquivalence = new EquivalenceObj();
        mResources = getResources();
        mIntent = getIntent();
        contentResolver = this.getContentResolver();
        
        try {
        	Long _id = Long.parseLong(mIntent.getStringExtra("Id"));
        	
        	AmiKcalFactory factory = new AmiKcalFactory();
        	factory.contentResolver=contentResolver;
        	
        	mEquivalence = factory.createEquivalenceObjFromId(_id);
        	
        	refreshScreen();
        } catch (Exception e) {Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();};
      
      
        
    }//fin du onCreate
    
   
  
    /******************************************************************************************
     * onValidateClick : Mettre à jour les informations saisies dans la base de donnée 
     * @param v
     ******************************************************************************************/
    public void onClickBtnOK(View v) {
    	
    	// On prépare les informations à mettre à jour
    	ContentValues val = new ContentValues();
    	
    	
    	
    	val.put(ContentDescriptorObj.Equivalences.Columns.FK_ENERGY, this.mEquivalence.energy.getId());
    	val.put(ContentDescriptorObj.Equivalences.Columns.FK_UNIT_IN, this.mEquivalence.unitIn.getId());
    	val.put(ContentDescriptorObj.Equivalences.Columns.FK_UNIT_OUT, this.mEquivalence.unitOut.getId());
    	val.put(ContentDescriptorObj.Equivalences.Columns.QUANTITY_OUT, this.mEquivalence.getQuantity());
    	
    	if (this.mEquivalence.getId()==0){
    		this.getContentResolver().insert(ContentDescriptorObj.Equivalences.URI_INSERT_EQUIVALENCE, val);
    	}
    	else {
    		
    		Uri request = ContentUris.withAppendedId(ContentDescriptorObj.Equivalences.URI_UPDATE_EQUIVALENCE,this.mEquivalence.getId());
    		this.getContentResolver().update(request, val, null,null);
    	};
    	
    	
		//on appelle setResult pour déclancher le onActivityResult de l'activity mère. 
		setResult(RESULT_OK, mIntent);
		
		//On termine l'Actvity
		finish();
    }
    
    
    /******************************************************************************************
     * refreshScreen : 
     *   
     ******************************************************************************************/
    private void refreshScreen(){
    	Button ed= (Button)findViewById(R.id.view_equivalence_btn_energy);
    	ed.setText(this.mEquivalence.energy.getName());
    
    	ed= (Button)findViewById(R.id.view_equivalence_btn_unit_in);
    	ed.setText(this.mEquivalence.unitIn.getName());
    	
    	ed= (Button)findViewById(R.id.view_equivalence_btn_unit_out);
        ed.setText(this.mEquivalence.unitOut.getName());
    	
        ed= (Button)findViewById(R.id.view_equivalence_btn_quantity);
        ed.setText(String.valueOf(this.mEquivalence.getQuantity()));
    	
    }
   
    
    
 public void onClickBtnUnitIn(View v){
	  	Intent intent = new Intent(this,Act_UnitOfMeasureList.class);
	   	startActivityForResult(intent, mResources.getInteger(R.integer.EQUIVALENCE_EDITOR_REQUEST_UNIT_IN));
	      	   	
 }
    
 public void onClickBtnUnitOut(View v){
	  	Intent intent = new Intent(this,Act_UnitOfMeasureList.class);
	   	startActivityForResult(intent, mResources.getInteger(R.integer.EQUIVALENCE_EDITOR_REQUEST_UNIT_OUT));
	      	   	
}

 public void onClickBtnQuantity(View v) {
	 Intent intent = new Intent(this,Act_NumericPad.class);
	  	intent.putExtra("question", "Entrez la quantité équivalente");
	   	startActivityForResult(intent, mResources.getInteger(R.integer.EQUIVALENCE_EDITOR_REQUEST_QUANTITY));
}

 
 public void onClickBtnEnergy(View v) {
	  	Intent intent = new Intent(this,Act_EnergyList.class);
	  	startActivityForResult(intent, mResources.getInteger(R.integer.EQUIVALENCE_EDITOR_REQUEST_ENERGY));
}
 
 protected void onActivityResult(int requestCode, int resultCode, Intent intent){
 	 
 	if (requestCode ==mResources.getInteger(R.integer.EQUIVALENCE_EDITOR_REQUEST_QUANTITY)) {
 			
			 	if (resultCode == RESULT_OK){
			 		this.mEquivalence.setQuantity(Float.parseFloat(intent.getStringExtra(mResources.getString(R.string.INTENT_OUT____NUMERICPAD_RESULT))));
			    }
 	} else if (requestCode == mResources.getInteger(R.integer.EQUIVALENCE_EDITOR_REQUEST_UNIT_IN )){
				
			 	if (resultCode == RESULT_OK){
			 		
			 		Long _id = Long.parseLong(intent.getStringExtra(mResources.getString(R.string.INTENT_OUT____UNITS_LIST____ID_OF_THE_UNIT)));
		        	
		        	AmiKcalFactory factory = new AmiKcalFactory();
		        	factory.contentResolver=contentResolver;
		        	mEquivalence.unitIn= factory.createUnitOfMeasureObjFromId(_id);
			    } 	 	
 	} else if (requestCode == mResources.getInteger(R.integer.EQUIVALENCE_EDITOR_REQUEST_UNIT_OUT )){
							
			 	if (resultCode == RESULT_OK){
					
			 		Long _id = Long.parseLong(intent.getStringExtra(mResources.getString(R.string.INTENT_OUT____UNITS_LIST____ID_OF_THE_UNIT)));
		        	
		        	AmiKcalFactory factory = new AmiKcalFactory();
		        	factory.contentResolver=contentResolver;
		        	mEquivalence.unitOut= factory.createUnitOfMeasureObjFromId(_id);
			    }
	} else if (requestCode == mResources.getInteger(R.integer.EQUIVALENCE_EDITOR_REQUEST_ENERGY)){ 
			 	if (resultCode == RESULT_OK){
					
			 		Long _id = Long.parseLong(intent.getStringExtra(mResources.getString(R.string.INTENT_OUT____ENERGY_LIST____ID_OF_ENERGY)));
		        	
		        	AmiKcalFactory factory = new AmiKcalFactory();
		        	factory.contentResolver=contentResolver;
		        	mEquivalence.energy= factory.createEnergyObjFromId(_id);
			    }	
		}
 
 	refreshScreen();
 }
 
 
    
}