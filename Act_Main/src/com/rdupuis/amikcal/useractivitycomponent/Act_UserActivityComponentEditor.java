package com.rdupuis.amikcal.useractivitycomponent;




import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rdupuis.amikcal.R;

import com.rdupuis.amikcal.commons.AmiKcalFactory;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.energy.Act_EnergyList;
import com.rdupuis.amikcal.energy.EnergyObj;
import com.rdupuis.amikcal.equivalence.EquivalenceObj;
import com.rdupuis.amikcal.commons.numericpad.Act_NumericPad;

import com.rdupuis.amikcal.unitofmeasure.Act_UnitOfMeasureList;
import com.rdupuis.amikcal.unitofmeasure.UnitOfMeasureObj;


 
public class Act_UserActivityComponentEditor extends Activity {
 	
     Intent mIntent;
     UserActivityComponentObj mUAC;
     ContentResolver contentResolver;
     Resources mResources;
     
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_edit_food_component);
        mResources = getResources();
        mIntent = getIntent();
        contentResolver = this.getContentResolver();
        
        //Initialisation d'un composant
        mUAC = new UserActivityComponentObj();
        
        try {
        	mUAC.setParentId(Long.valueOf(mIntent.getStringExtra(mResources.getString(R.string.INTENT_IN_USER_ACTIVITY_COMPONENT_EDITOR_ID_OF_USER_ACTIVITY))));
        }
        catch (Exception e) {Toast.makeText(this, "Activité parente inconnue", Toast.LENGTH_SHORT).show();	
        }
        
        
        // si l'Id de l'Intent est null, c'est que l'on souhaite créer un nouvel enregistrement
        // sinon on récupère les infos de la base de donnée

        try {
        	//On récupère l'ID du composant
        	this.mUAC.setId(Long.parseLong(mIntent.getStringExtra(mResources.getString(R.string.INTENT_IN_USER_ACTIVITY_COMPONENT_EDITOR_ID_OF_COMPONENT))));
        	loadComponentFromDb(this.mUAC.getId());
        	refreshScreen();
        	} 
        catch	(Exception e) {}
 
    }//fin du onCreate
    
    
    
    /***************************************************************************************
     * 
     * @param v View
     **************************************************************************************/
    public void onClick_Quantity(View v) {
    	callNumericPad();
    }
    
    /**
     * Appel du pavé numérique
     */
    public void callNumericPad() {
    	Intent intent = new Intent(this,Act_NumericPad.class);
	  	intent.putExtra("question", "Entrez la quantité d'Aliment");
	  	startActivityForResult(intent, R.integer.NUMERICPAD);
    }
  
    
    /***************************************************************************************
     * Appel de la liste des aliments
     * @param v View
     **************************************************************************************/
    public void  onClick_EnergyName(View v) {
    	  	Intent intent = new Intent(this,Act_EnergyList.class);
    	   	startActivityForResult(intent, R.integer.ACTY_ENERGIES_LIST);
    }
    
    
    /***************************************************************************************
     * 
     * @param v View
     **************************************************************************************/
    public void onClick_Unit(View v) {
    	  	callUnitListView();
    }
    
    
    /***************************************************************************************
     * 
     * Appel de la Liste des unitées.
     **************************************************************************************/
    public void callUnitListView() {
    	  	Intent intent = new Intent(this,Act_UnitOfMeasureList.class);
    	  	intent.putExtra(mResources.getString(R.string.INTENT_IN_UNITS_LIST_ID_OF_ENERGY), String.valueOf(mUAC.getEnergyObj().getId()));
    	  	startActivityForResult(intent, R.integer.ACTY_UNITS_LIST);
    	      	   	
    }
    
    
    /**
     * Mettre à jour les informations saisies dans la base de donnée. 
     * @param v View
     */
    public void onClick_Validate(View v) {

    	computeEnegy();
    	// on prépare les informations à mettre à jour
    	ContentValues val = new ContentValues();
    
    	val.put(ContentDescriptorObj.ActivityComponent.Columns.FK_ENERGY,    this.mUAC.getEnergyObj().getId() );
    	val.put(ContentDescriptorObj.ActivityComponent.Columns.FK_UNIT,      this.mUAC.getUnitMeasure().getId());
    	val.put(ContentDescriptorObj.ActivityComponent.Columns.FK_PARENT,    mUAC.getParentId() );
    	val.put(ContentDescriptorObj.ActivityComponent.Columns.QUANTITY,     mUAC.getQuantity() );
    	val.put(ContentDescriptorObj.ActivityComponent.Columns.MNT_ENERGY,   mUAC.getCalories()  );
    	val.put(ContentDescriptorObj.ActivityComponent.Columns.MNT_GLUCIDS,  mUAC.getGlucids());
    	val.put(ContentDescriptorObj.ActivityComponent.Columns.MNT_PROTEINS, mUAC.getProteins());
    	val.put(ContentDescriptorObj.ActivityComponent.Columns.MNT_LIPIDS,   mUAC.getLipids() );
        val.put(ContentDescriptorObj.ActivityComponent.Columns.LAST_UPDATE,  ToolBox.getCurrentTimestamp() );
    	
        
        // si l'ID est NULL, on doit faire un INSERT sinon on fait un UPDATE
    	if (this.mUAC.getId()==0){
    		this.getContentResolver().insert(ContentDescriptorObj.ActivityComponent.URI_INSERT_ACTIVITY_COMPONENT, val);
    	}
    	else {
    		this.getContentResolver().update(ContentDescriptorObj.ActivityComponent.URI_UPDATE_ACTIVITY_COMPONENT, val, String.valueOf(this.mUAC.getId()),null);
    	};
    	
    	
    	// on alimente le résultat dans l'Intent pour que l'Activity mère puisse
		//récupérer la valeur. 
    	Button bt= (Button)findViewById(R.id.componentview_btn_quantity);
		mIntent.putExtra("result", bt.getText()); 
		
		//on appelle setResult pour déclancher le onActivityResult de l'activity mère. 
		setResult(RESULT_OK, mIntent);
		
		//On termine l'Actvity
		finish();
    }
    
    /******************************************************************************************
     * Gère le retour d'appel aux autres activitées
     * @param requestCode code fonction utilisé par l'activité appellante
     * @param resultCode code retour envoyé par l'activitée appellée
     * @param intent Intent servant de lien entre l'activitée appelante et appellée
     ******************************************************************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
       
    	AmiKcalFactory mFactory = new AmiKcalFactory();
    	mFactory.contentResolver = this.getContentResolver();
    	
    	switch (requestCode) {
    	
			case  R.integer.NUMERICPAD      :
			
			 	if (resultCode == RESULT_OK){
					
			 		this.mUAC.setQuantity(Float.parseFloat(intent.getStringExtra(mResources.getString(R.string.INTENT_OUT_NUMERICPAD_RESULT))));
			 					 		
			 		if (this.mUAC.getUnitMeasure().getId()==0l){
			 			this.callUnitListView();
			 		}
			    }
				break;
				
				
			case  R.integer.ACTY_UNITS_LIST      :
				
			 	if (resultCode == RESULT_OK){
					
			 		// on récupère l'objet Unit.
		        	this.mUAC.setUnitMeasure(mFactory.createUnitOfMeasureObjFromId(Long.parseLong(intent.getStringExtra(mResources.getString(R.string.INTENT_OUT_UNITS_LIST_ID_OF_THE_UNIT)))));
			    }
			 	break;
			 	
			case  R.integer.ACTY_ENERGIES_LIST   :
				
			 	if (resultCode == RESULT_OK){
					
			 		// on récupère l'objet Energy d'après l'id choisi par l'utilisateur .

		        	this.mUAC.setEnergyObj(mFactory.createEnergyObjFromId(Long.parseLong(intent.getStringExtra(mResources.getString(R.string.INTENT_OUT_ENERGY_LIST_ID_OF_ENERGY)))));
			 		
			 		if (this.mUAC.getQuantity() == 0f){
			 			this.callNumericPad();
			 		}
			    }
			 	break;
		default :break;
		
		}
    refreshScreen();
    }

    
    /******************************************************************************************
     * 
     * @param _id Identifiant du composant 
     ******************************************************************************************/
    private void loadComponentFromDb(Long _id){
    	// On fabrique l'Uri pour le contentProvider 
    	// celle-ci est du style content://xxxxx.xxxxxxxx.xxxxxxx/# où le dièse est l'Id à rechercher
           Uri selectUri = ContentUris.withAppendedId(ContentDescriptorObj.ActivityComponent.URI_SELECT_ACTIVITY_COMPONENTS,_id);
    
        //On crée un curseur pour lire la table des aliments    
          Cursor cur = this.getContentResolver().query(selectUri , null, _id.toString(), null, null);
        
        final int INDX_ENERGY_ID = cur.getColumnIndex(ContentDescriptorObj.ActivityComponent.Columns.FK_ENERGY);
        final int INDX_QUANTITY = cur.getColumnIndex(ContentDescriptorObj.ActivityComponent.Columns.QUANTITY);         
        final int INDX_UNIT_ID = cur.getColumnIndex(ContentDescriptorObj.ActivityComponent.Columns.FK_UNIT);
        
        
        // faire un move First pour positionner le pointeur, sinon on pointe sur null
        if (cur.moveToFirst()) {
        	
        	this.mUAC.getEnergyObj().setId(cur.getLong(INDX_ENERGY_ID));
        	//this.mUAC.getEnergyObj().setName(cur.getString(INDX_ENERGY_NAME));
        	
        	AmiKcalFactory mFactory = new AmiKcalFactory();
        	mFactory.contentResolver = this.getContentResolver();
        	this.mUAC.setEnergyObj(mFactory.createEnergyObjFromId(cur.getLong(INDX_ENERGY_ID)));
        	this.mUAC.setUnitMeasure(mFactory.createUnitOfMeasureObjFromId(cur.getLong(INDX_UNIT_ID)));
        	this.mUAC.setQuantity(Float.parseFloat(cur.getString(INDX_QUANTITY)));
        	computeEnegy();
        
        }
        else {
        	Toast.makeText(this, "Composant non trouvé", Toast.LENGTH_SHORT).show();
        
        }
        
    }
    
    /**
     * Rafraichissement de l'écan
     */
    private void refreshScreen(){
    	 Button b = (Button)findViewById(R.id.componentview_btn_EnergyName);
    	  
    	 //Gestion du libellé sur le bouton Energy    	 
    	 if (mUAC.getEnergyObj().getName() == ""){
        	 b.setText(mResources.getString(R.string.empty));
         } else {
        	 b.setText(this.mUAC.getEnergyObj().getName());
         }
    	 
    	//Gestion du libellé sur le bouton Unit         
         b = (Button)findViewById(R.id.componentview_btn_unit);

         if (mUAC.getUnitMeasure().getName() == ""){
        	 b.setText(mResources.getString(R.string.empty));
         } else {
        	 b.setText(this.mUAC.getUnitMeasure().getName());
         }
         
       //Gestion du libellé sur le bouton quantity
         b = (Button)findViewById(R.id.componentview_btn_quantity);
         b.setText(Float.toString(this.mUAC.getQuantity()));
         
    }
    
    
    
  
    /**
     * Recherche d'une équivalence pour un aliment et une unité de mesure 
     * @param energy (aliment)
     * @param unitIn (unité de départ)
     * @return une équivalence
     */

 private EquivalenceObj findEquivalence(EnergyObj energy, UnitOfMeasureObj unitIn){
	 
	 EquivalenceObj mEquivalence = new EquivalenceObj();
	 String searchKey = String.valueOf(energy.getId()) +"+"+ String.valueOf(unitIn.getId());
	 
	 // On fabrique l'Uri pour le contentProvider 
        Uri selectUri = ContentDescriptorObj.Equivalences.URI_SEARCH_EQUIVALENCE.buildUpon().appendPath(searchKey).build(); 
        
     //On crée un curseur pour lire la table.    
       Cursor cur = this.getContentResolver().query(selectUri , null, null, null, null);
     
     final int INDX_ID = cur.getColumnIndex(ContentDescriptorObj.Equivalences.Columns.ID);         
     
     // faire un move First pour positionner le pointeur, sinon on pointe sur null
     if (cur.moveToFirst()) {
    	 
    	 AmiKcalFactory factory = new AmiKcalFactory();
    	 factory.contentResolver=this.contentResolver;
    	 mEquivalence = factory.createEquivalenceObjFromId(cur.getLong(INDX_ID));
     }
     else {
     	Toast.makeText(this, "Equivalence non trouvé", Toast.LENGTH_SHORT).show();
     
     }

	 return mEquivalence;
	 
 }
  
  
/**
 * Calcul des informations nutitionelles (calories/lipides/glucides/protéines)    
 */
  private void computeEnegy(){
	  
	  float wQuantity = this.mUAC.getQuantity();
	  
	  Log.d("mUAC.getQuantity",String.valueOf(this.mUAC.getQuantity()));
	  
	  // Si la quantité d'aliment est exprimé dans une untitée
	  // diffrénte de celle du référentiel des calories, on va rechercher l'équivalence.
	  if (this.mUAC.getEnergyObj().unit.getId() != this.mUAC.getUnitMeasure().getId()){
		  Toast.makeText(this, "Unitée de mesure différentes", Toast.LENGTH_SHORT).show();
	 
		  EquivalenceObj eq = findEquivalence(mUAC.getEnergyObj(),mUAC.getUnitMeasure());
		 
		  Log.d("eq.getQuantity",String.valueOf(eq.getQuantity()));
		  
		  wQuantity = eq.getQuantity()*wQuantity;
	  }
	  
	  Log.i("wQuantity",String.valueOf(wQuantity));
	  
	  
	  this.mUAC.setCalories((wQuantity * this.mUAC.getEnergyObj().getCalories())/this.mUAC.getEnergyObj().getQuantityReference());
	  this.mUAC.setLipids((wQuantity * this.mUAC.getEnergyObj().getLipids())/this.mUAC.getEnergyObj().getQuantityReference());
	  this.mUAC.setGlucids((wQuantity * this.mUAC.getEnergyObj().getGlucids())/this.mUAC.getEnergyObj().getQuantityReference());
	  this.mUAC.setProteins((wQuantity * this.mUAC.getEnergyObj().getProteins())/this.mUAC.getEnergyObj().getQuantityReference());
	  
  }
    
    
    
}