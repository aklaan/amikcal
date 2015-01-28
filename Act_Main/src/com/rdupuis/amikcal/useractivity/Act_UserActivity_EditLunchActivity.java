package com.rdupuis.amikcal.useractivity;




import java.text.DecimalFormat;
import java.util.Calendar;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TimePicker;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.data.ContentDescriptorObj;


/**
 * <b>Ecran d'édition des repas de l'utilisateur.</b>
 * 
 * @author Rodolphe Dupuis
 * @version 0.1
 */
public class Act_UserActivity_EditLunchActivity extends Act_UserActivity_EditorCommons {
     
    /** Called when the activity is first created. */
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
               	setContentView(R.layout.view_edit_lunch);
			super.refreshScreen();
            
    }
    //fin du onCreate
    
    @Override
	public void createNewUserActivity(){
	this.mUserActivity = new UserActivityLunch();
	this.mUserActivity.set_id(this.NO_ID);
	this.mUserActivity.setDay(this.currentDay);
    
    
    
    
    };
	
  
    /******************************************************************************************
     * onClickOk : 
     *  - Mettre à jour les informations saisies dans la base de donnée
     *  - appeler la saisie d'un component (suivant le type moving / eating)
     *   
     * @param v vue 
     ******************************************************************************************/
    public void onClickOk(View v) {
    	
    	//récupérer l'heure
    	TimePicker tp = (TimePicker) findViewById(R.id.timePicker1);
    	
      	DecimalFormat decimalFormat = (DecimalFormat)DecimalFormat.getInstance();
       	decimalFormat.applyPattern("00");
    	
       	if (tp.getCurrentHour().intValue()>=12){
       		mUserActivity.getDay().set(Calendar.AM_PM,Calendar.PM);
       	} else {mUserActivity.getDay().set(Calendar.AM_PM,Calendar.AM);
       	}
       	
    	mUserActivity.getDay().set(Calendar.HOUR_OF_DAY, tp.getCurrentHour().intValue());
    	mUserActivity.getDay().set(Calendar.MINUTE, tp.getCurrentMinute().intValue());
    		

    	if (mUserActivity.get_id()==NO_ID){
    		insertLunch();
    	} else {
    		updateLunch();
    		}
     
    	closeActivity();
    }
    
    /******************************************************************************************
     * getContentValues  
     ******************************************************************************************/
    private ContentValues getLunchData(){
    
    	ContentValues val = new ContentValues();
            	
    	val.put(ContentDescriptorObj.UserActivities.Columns.ID, (mUserActivity.get_id()==NO_ID)?null:mUserActivity.get_id());
    	val.put(ContentDescriptorObj.UserActivities.Columns.TITLE, mUserActivity.getTitle());
    	val.put(ContentDescriptorObj.UserActivities.Columns.DATE, ToolBox.getSqlDateTime(mUserActivity.getDay()));
     	val.put(ContentDescriptorObj.UserActivities.Columns.TYPE, mUserActivity.getType().name());
    	val.put(ContentDescriptorObj.UserActivities.Columns.LAST_UPDATE, ToolBox.getCurrentTimestamp());
    	
    	return val;
    }
    
    
    /*******************************************************************************************
     * Méthode : update()
     * metre à jour l'enregistrement
     *******************************************************************************************/
    public void updateLunch(){
    	//On récupère la requête de mise à jour à laquelle on ajoute l'ID du repas à modifier.
    	Uri uriUpdate = ContentUris.withAppendedId(ContentDescriptorObj.UserActivities.URI_UPDATE_USER_ACTIVITIES,mUserActivity.get_id());
    	//on appelle la requête avec les nouvelles valeurs.
    	this.getContentResolver().update(uriUpdate, this.getLunchData(), this.mUserActivity.get_id().toString(),null);
    }
    
    /*******************************************************************************************
     * Méthode : insert()
     * insérer une nouvelle occurence 

     *******************************************************************************************/
    public void insertLunch(){
    	//On appelle la requête d'insert d'un nouveau repas
    	this.getContentResolver().insert(ContentDescriptorObj.UserActivities.URI_INSERT_USER_ACTIVITIES, getLunchData());
    }
    
        
    /*******************************************************************************************
     * Méthode : onClickLunch()
     *           Gestion du bouton repas  
     *******************************************************************************************/
    
  
  
 /*****************************************************************************************
  *  Gestion des radiobouton 
  * *************************************************************************************** */
 
 	public void onClickRdioBreakfast(View v){
	 	 setRdioLunch(1);
 	}
 
 	public void onClickRdioLunch(View v){
 		setRdioLunch(2);
 	}

 	public void onClickRdioDiner(View v){
 		setRdioLunch(3);
 	}

 	public void onClickRdioSnack(View v){
 		setRdioLunch(4);

 	}	
 
 	 /***********************************************************************************
	  * 
	  * 
	  ************************************************************************************/
	 private void setRdioLunch(int i){ 
	
		 RadioButton rbBreakfast = (RadioButton) findViewById(R.id.view_ua_editor_rdio_breakfast);
		 RadioButton rbLunch     = (RadioButton) findViewById(R.id.view_ua_editor_rdio_lunch);
		 RadioButton rbDiner     = (RadioButton) findViewById(R.id.view_ua_editor_rdio_diner);
		 RadioButton rbSnack     = (RadioButton) findViewById(R.id.view_ua_editor_rdio_snack);
		 
		 rbBreakfast.setChecked(false);
		 rbLunch.setChecked(false);
		 rbDiner.setChecked(false);
		 rbSnack.setChecked(false);
		 
		 switch (i){
		 
		 	case 1:rbBreakfast.setChecked(true);
		 			this.mUserActivity.setTitle("Petit dejeuner");	
		 			break;
		 		
		 	case 2: rbLunch.setChecked(true);
		 			this.mUserActivity.setTitle("Déjeuner");
		 			break;
		 		
		 	case 3: rbDiner.setChecked(true);
		 			this.mUserActivity.setTitle("Dîner");		
		 			break;
		 		
		 	case 4: rbSnack.setChecked(true);
		 			this.mUserActivity.setTitle("Pause");
		 			break;
		 }
		 
 }
 
	 
	 
	
	    
 

	    

	    
}