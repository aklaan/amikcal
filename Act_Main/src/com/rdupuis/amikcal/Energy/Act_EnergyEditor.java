package com.rdupuis.amikcal.Energy;



import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.Commons.AmiKcalFactory;
import com.rdupuis.amikcal.Tools.NumericPad.FragAct_NumericPad;
import com.rdupuis.amikcal.Data.ContentDescriptorObj;
import com.rdupuis.amikcal.UnitOfMeasure.Act_UnitOfMeasureList;


 
public class Act_EnergyEditor extends Activity {

	Intent mIntent;
     Resources mResources;
     EnergyObj mEnergy;
     final long NO_ID = -1l; 
     ContentResolver mContentResolver;
     
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.energyview);
        mIntent = getIntent();
        mContentResolver = this.getContentResolver();
        mEnergy = new EnergyObj();
        mResources=getResources();
        
        try {
        	AmiKcalFactory factory = new AmiKcalFactory();
        	factory.contentResolver = mContentResolver;
        	mEnergy = factory.createEnergyObjFromId(Long.parseLong(mIntent.getStringExtra(mResources.getString(R.string.INTENT_IN_ENERGY_EDITOR_ID_OF_ENERGY))));
        } catch (Exception e) {
        	mEnergy.setId(NO_ID);
        }
        
        
        ((TextView)findViewById(R.id.energyview_edTxt_energy_name)).addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                   mEnergy.setName(s.toString());
            }

			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
         
        });
        
     
        
        refresh();
        
    }//fin du onCreate
    
      
    /***************************************************************************************
     * 
     * @param v
     **************************************************************************************/
    public void onClick_Quantity(View v) {
    	  	Intent intent = new Intent(this,FragAct_NumericPad.class);
    	  	intent.putExtra("question", "Entrez la quantité de référence");
    	   	startActivityForResult(intent, R.integer.BTN_QUANTITY_OF_ENERGY_VIEW_CALL_NUMERICPAD);
    }
    

    /***************************************************************************************
     * 
     * @param v
     **************************************************************************************/
    public void onClick_MntEnergy(View v) {
    	  	Intent intent = new Intent(this,FragAct_NumericPad.class);
    	  	intent.putExtra("question", "Entrez le nomnre de Kilo Calories");
    	   	startActivityForResult(intent, R.integer.BTN_MNT_ENERGY_OF_ENERGY_VIEW_CALL_NUMERICPAD);
    }
    
    /***************************************************************************************
     * 
     * @param v
     **************************************************************************************/
    public void onClick_MntProteins(View v) {
    	  	Intent intent = new Intent(this,FragAct_NumericPad.class);
    	  	intent.putExtra("question", "Entrez la quantité de Protéines");
    	   	startActivityForResult(intent, R.integer.BTN_MNT_PROTEINS_OF_ENERGY_VIEW_CALL_NUMERICPAD);
    }
    
    
    /***************************************************************************************
     * 
     * @param v
     **************************************************************************************/
    public void onClick_MntLipids(View v) {
    	  	Intent intent = new Intent(this,FragAct_NumericPad.class);
    	  	intent.putExtra("question", "Entrez la quantité Lipides");
    	   	startActivityForResult(intent, R.integer.BTN_MNT_LIPIDS_OF_ENERGY_VIEW_CALL_NUMERICPAD);
    }
    
    
    
    /***************************************************************************************
     * 
     * @param v
     **************************************************************************************/
    public void onClick_MntGlucids(View v) {
    	  	Intent intent = new Intent(this,FragAct_NumericPad.class);
    	  	intent.putExtra("question", "Entrez la quantité de Glucides");
    	   	startActivityForResult(intent, R.integer.BTN_MNT_GLUCIDS_OF_ENERGY_VIEW_CALL_NUMERICPAD);
    	      	   	
    }
    
    /***************************************************************************************
     * 
     * @param v
     **************************************************************************************/
    public void onClick_Unit(View v) {
    	  	Intent intent = new Intent(this,Act_UnitOfMeasureList.class);
    	   	startActivityForResult(intent, R.integer.ACTY_UNITS_LIST);
    	      	   	
    }
   
        
    /******************************************************************************************
     * onActivityResult : on récupère les info saisies dans le pad numérique.
     * 
     ******************************************************************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
    	
    	switch (requestCode) {
    	
    	
			case  R.integer.BTN_MNT_ENERGY_OF_ENERGY_VIEW_CALL_NUMERICPAD      :
			
			 	if (resultCode == RESULT_OK){
					
			 		this.mEnergy.setCalories(Float.parseFloat(intent.getStringExtra(mResources.getString(R.string.INTENT_OUT_NUMERICPAD_RESULT))));
			    }
				break;
				
				
			case  R.integer.BTN_MNT_GLUCIDS_OF_ENERGY_VIEW_CALL_NUMERICPAD      :
				
			 	if (resultCode == RESULT_OK){
					
			 		this.mEnergy.setGlucids(Float.parseFloat(intent.getStringExtra(mResources.getString(R.string.INTENT_OUT_NUMERICPAD_RESULT))));
			    }
			 	break;
			 	
			case  R.integer.BTN_MNT_LIPIDS_OF_ENERGY_VIEW_CALL_NUMERICPAD     :
				
			 	if (resultCode == RESULT_OK){
					
			 		this.mEnergy.setLipids(Float.parseFloat(intent.getStringExtra(mResources.getString(R.string.INTENT_OUT_NUMERICPAD_RESULT))));
			    } 	
			 	break;
			 	
			case  R.integer.BTN_MNT_PROTEINS_OF_ENERGY_VIEW_CALL_NUMERICPAD   :
				
			 	if (resultCode == RESULT_OK){
					
			 		this.mEnergy.setProteins(Float.parseFloat(intent.getStringExtra(mResources.getString(R.string.INTENT_OUT_NUMERICPAD_RESULT))));
			    } 	
			 	break;
			 	
			case  R.integer.BTN_QUANTITY_OF_ENERGY_VIEW_CALL_NUMERICPAD   :
				
			 	if (resultCode == RESULT_OK){
					
			 		this.mEnergy.setQuantityReference(Float.parseFloat(intent.getStringExtra(mResources.getString(R.string.INTENT_OUT_NUMERICPAD_RESULT))));
			 		
			 		
			 		
			    } 	 	
			 	break;
			 	
			case  R.integer.ACTY_UNITS_LIST  :
				
			 	if (resultCode == RESULT_OK){
					
			 		AmiKcalFactory factory = new AmiKcalFactory();
		        	factory.contentResolver = mContentResolver;
		        	mEnergy.unit = factory.createUnitOfMeasureObjFromId(Long.parseLong(intent.getStringExtra(mResources.getString(R.string.INTENT_OUT_UNITS_LIST_ID_OF_THE_UNIT))));
			 		
			    }
			 	break;
		default :break;
		
		}
    
    	refresh();
    }
    
    
    
    
    
    
    /******************************************************************************************
     * onValidateClick : Mettre à jour les informations saisies dans la base de donnée 
     * @param v
     ******************************************************************************************/
    public void onClick_Validate(View v) {
    	
    	EditText ed= (EditText)findViewById(R.id.energyview_edTxt_energy_name);
    	this.mEnergy.setName(ed.getText().toString());
    	
    	// On prépare les informations à mettre à jour
    	ContentValues val = new ContentValues();
    	
    	val.put(ContentDescriptorObj.Energies.Columns.NAME, this.mEnergy.getName() );
    	val.put(ContentDescriptorObj.Energies.Columns.MNT_ENERGY, this.mEnergy.getCalories() );
    	val.put(ContentDescriptorObj.Energies.Columns.MNT_GLUCIDS, this.mEnergy.getGlucids() );
    	val.put(ContentDescriptorObj.Energies.Columns.MNT_PROTEINS, this.mEnergy.getProteins());
    	val.put(ContentDescriptorObj.Energies.Columns.MNT_LIPIDS, this.mEnergy.getLipids());
    	val.put(ContentDescriptorObj.Energies.Columns.FK_UNIT, this.mEnergy.unit.getId() );
    	val.put(ContentDescriptorObj.Energies.Columns.QUANTITY, this.mEnergy.getQuantityReference() );
    	
    	if (this.mEnergy.getId()==NO_ID){
    	this.getContentResolver().insert(ContentDescriptorObj.Energies.URI_INSERT_ENERGY, val);
    	}
    	else{
    		this.getContentResolver().update(ContentDescriptorObj.Energies.URI_UPDATE_ENERGY, val,String.valueOf(this.mEnergy.getId()),null);
    	}
    		
		//on appelle setResult pour déclancher le onActivityResult de l'activity mère. 
		setResult(RESULT_OK, mIntent);
		
		//On termine l'Actvity
		finish();
    }
    
    /***************************************************************************************
     * 

     **************************************************************************************/
    private void refresh(){
    	
    	
    	DecimalFormat decimalFormat = (DecimalFormat)DecimalFormat.getInstance();
 		decimalFormat.applyPattern("#,##0.00");
 		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator(',');
        decimalFormat.setDecimalFormatSymbols( dfs );
 		
       //	bt.setText(decimalFormat.format(this.mnt_quantity).toString());
    	
    	
        
        
        
    	EditText ed= (EditText)findViewById(R.id.energyview_edTxt_energy_name);
    	ed.setText(this.mEnergy.getName());
    	
    	Button bt= (Button)findViewById(R.id.energyview_btn_mnt_energy);
    	bt.setText(Float.toString(this.mEnergy.getCalories()));
    	
    	bt= (Button)findViewById(R.id.energyview_btn_quantity);
    	bt.setText(Float.toString(this.mEnergy.getQuantityReference()));
    	
    	bt= (Button)findViewById(R.id.energyview_btn_mnt_glucids);
    	bt.setText(Float.toString(this.mEnergy.getGlucids()));
    	
    	bt= (Button)findViewById(R.id.energyview_btn_mnt_lipids);
    	bt.setText(Float.toString(this.mEnergy.getLipids()));
    	
    	bt= (Button)findViewById(R.id.energyview_btn_mnt_proteins);
    	bt.setText(Float.toString(this.mEnergy.getProteins()));
    	
    	bt= (Button)findViewById(R.id.energyview_btn_unit);
    	bt.setText(this.mEnergy.unit.getName());
    	
    	
    } 
    
    
    /***************************************************************************************
     * 
     * @param v
     **************************************************************************************/
   public void onClick_Cancel(View v){
	   finish();
   }
    
    
}