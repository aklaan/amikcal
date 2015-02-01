package com.rdupuis.amikcal.useractivity;




import java.text.DecimalFormat;
import java.util.Calendar;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AmikcalVar;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.data.ContentDescriptorObj;


/**
 * <b>Ecran d'édition des activitées de l'utilisateur.</b>
 * <p> les activitées sont : 
 * <ul> 
 * 		<li> les repas </li>
 * 	<li> les activitées physiques </li>
 * <li> les pesées </li>
 * <li> les recettes </li>
 * </ul>
 * </p>
 * @author Rodolphe Dupuis
 * @version 0.1
 */
public class Act_UserActivity_EditMoveActivity extends Act_UserActivity_EditorCommons {
 
	
     
    /** Called when the activity is first created. */
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
               
                
        	
        	 	setContentView(R.layout.view_edit_physical_activity);
        		super.refreshScreen();
        
    }
    //fin du onCreate
    
  
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
    		

    	if (mUserActivity.get_id()==AmikcalVar.NO_ID){
    		insertMove();
    	} else {
    		updateMove();
    		}
     
    	closeActivity();
    }
    
    /******************************************************************************************
     * getContentValues  
     ******************************************************************************************/
    private ContentValues getContentValues(){
    
    	ContentValues val = new ContentValues();
        
    	
    	val.put(ContentDescriptorObj.UserActivities.Columns.ID, (mUserActivity.get_id()==AmikcalVar.NO_ID)?null:mUserActivity.get_id());
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
    public void updateMove(){
    	ContentValues val = getContentValues();
    	Uri uriUpdate = ContentUris.withAppendedId(ContentDescriptorObj.UserActivities.URI_UPDATE_USER_ACTIVITIES,mUserActivity.get_id());
    	this.getContentResolver().update(uriUpdate, val, this.mUserActivity.get_id().toString(),null);
    }
    
    /*******************************************************************************************
     * Méthode : insert()
     * insérer une nouvelle occurence 

     *******************************************************************************************/
    public void insertMove(){
    	ContentValues val = getContentValues();
    	this.getContentResolver().insert(ContentDescriptorObj.UserActivities.URI_INSERT_USER_ACTIVITIES, val);
    }
    
    
    
    
   
    
 	    
}