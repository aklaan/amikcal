package com.rdupuis.amikcal.useractivity;




import java.text.DecimalFormat;
import java.util.Calendar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AmiKcalFactory;
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
public class Act_UserActivityEditor extends Activity {
 
	
     Intent mIntent;
     Long mId;
     boolean morning = true;
     int timeRange;
     Calendar currentDay;
     long  currentId;
     Resources mResources;
     final long NO_ID = -1l;
     
     ContentResolver contentResolver;
     UserActivityObj mUserActivity ;
    
    /** Called when the activity is first created. */
    @Override
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
               
        mIntent = getIntent();
        contentResolver = this.getContentResolver();
        mUserActivity = new UserActivityObj();
        mResources  = getResources();             
        
       // on récupère la date de l'activité en provenance de l'écran précendent
       // en théorie il ne doit jamais être NULL
        try {
        	currentDay =ToolBox.parseCalendar(mIntent.getStringExtra(mResources.getString(R.string.INTENT_IN_USER_ACTIVITY_EDITOR_DAY_OF_THE_USER_ACTIVITY)));
        }
        catch (Exception e) {
        	Toast.makeText(this, "Date du jour non renseignée", Toast.LENGTH_SHORT).show();
        }
   
      
        //On récupère de l'Intent l'ID de l'activité séléctionnée.
        // si cet ID est null c'est que l'utilisateur souhaite créer une nouvelle activitée
        try {
        	mUserActivity.set_id(Long.parseLong(mIntent.getStringExtra(mResources.getString(R.string.INTENT_IN_USER_ACTIVITY_EDITOR_ID_OF_THE_USER_ACTIVITY))));
        	AmiKcalFactory factory = new AmiKcalFactory();
        	factory.contentResolver=this.getContentResolver();
        	mUserActivity = factory.createUserActivityObjFromId(mUserActivity.get_id()); 
        }
        catch (Exception e) {
        	mUserActivity.set_id(NO_ID);
        	mUserActivity.setDay(currentDay);
        }

        
        //* choix de la vue a afficher en fonction du type d'activité choisie
        if (this.mUserActivity.getType()==mResources.getInteger(R.integer.ACTIVITY_LUNCH)){
        	setContentView(R.layout.view_edit_lunch);
			refreshScreen();
        
        } else if (this.mUserActivity.getType()==mResources.getInteger(R.integer.ACTIVITY_ACTIVITY)){
        	
        	 	setContentView(R.layout.view_edit_physical_activity);
        		refreshScreen();
        } else if (this.mUserActivity.getType()==mResources.getInteger(R.integer.ACTIVITY_WEIGHT)){
        
        		setContentView(R.layout.view_edit_weight);
        		
        		
        		TextView tv =(TextView)findViewById(R.id.userActivity_Editor_tv_kilos);
        		tv.setText(String.valueOf(mUserActivity.getWeight().getInt_part()));
        		tv =(TextView)findViewById(R.id.userActivity_Editor_tv_grammes);
        		tv.setText(String.valueOf(mUserActivity.getWeight().getDecimalPart()));
        		refreshScreen();
        } else { setContentView(R.layout.choose_activity);       	
        		}        	
        
       
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
    		

    	if (mUserActivity.get_id()==NO_ID){
    		insert();
    	} else {
    		update();
    		}
     
    	closeActivity();
    }
    
    /******************************************************************************************
     * getContentValues  
     ******************************************************************************************/
    private ContentValues getContentValues(){
    
    	ContentValues val = new ContentValues();
        
    	
    	val.put(ContentDescriptorObj.UserActivities.Columns.ID, (mUserActivity.get_id()==NO_ID)?null:mUserActivity.get_id());
    	val.put(ContentDescriptorObj.UserActivities.Columns.TITLE, mUserActivity.getTitle());
    	val.put(ContentDescriptorObj.UserActivities.Columns.DATE, ToolBox.getSqlDateTime(mUserActivity.getDay()));
    	
    	val.put(ContentDescriptorObj.UserActivities.Columns.TYPE, mUserActivity.getType());
    	val.put(ContentDescriptorObj.UserActivities.Columns.LAST_UPDATE, ToolBox.getCurrentTimestamp());
    	
    	return val;
    }
    
    
    /*******************************************************************************************
     * Méthode : update()
     * metre à jour l'enregistrement
     *******************************************************************************************/
    public void update(){
    	ContentValues val = getContentValues();
    	Uri uriUpdate = ContentUris.withAppendedId(ContentDescriptorObj.UserActivities.URI_UPDATE_USER_ACTIVITIES,mUserActivity.get_id());
    	this.getContentResolver().update(uriUpdate, val, this.mUserActivity.get_id().toString(),null);
    }
    
    /*******************************************************************************************
     * Méthode : insert()
     * insérer une nouvelle occurence 

     *******************************************************************************************/
    public void insert(){
    	ContentValues val = getContentValues();
    	this.getContentResolver().insert(ContentDescriptorObj.UserActivities.URI_INSERT_USER_ACTIVITIES, val);
    }
    
    
    
    /*******************************************************************************************
     * Méthode : refreshScreen()
     * alimente les composants de la vue avec les donnée de du UserActicity en cours
     *******************************************************************************************/
    private void refreshScreen(){
    	
    	 TimePicker tp = (TimePicker) findViewById(R.id.timePicker1);
         tp.setIs24HourView(true);
   // EditText edtxt = (EditText) findViewById(R.id.useractivity_editor_view_edTxt_name);
   // edtxt.setText(this.mUserActivity.getTitle());
         Calendar c = mUserActivity.getDay();
         tp.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
         tp.setCurrentMinute(c.get(Calendar.MINUTE));
    }
 
    /*******************************************************************************************
     * Méthode : closeActivity()
     * ferme l'activité 
     *******************************************************************************************/
    private void closeActivity(){
    	
    	//on appelle setResult pour déclancher le onActivityResult de l'activity mère. 
    			setResult(RESULT_OK, mIntent);
    			//On termine l'Actvity
    			finish();
    }
    
    
   
    
    /*******************************************************************************************
     * Méthode : onClickLunch()
     *           Gestion du bouton repas  
     *******************************************************************************************/
    
 public void onClickLunch(View v){
    
	 setContentView(R.layout.view_edit_lunch);
	 this.mUserActivity.setType(this.mResources.getInteger(R.integer.ACTIVITY_LUNCH));
	 TimePicker tp = (TimePicker) findViewById(R.id.timePicker1);
     tp.setIs24HourView(true);
     tp.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));  
	 
    }
 
 /*******************************************************************************************
  * Méthode : onClickPhysicalActivity()
  *           Gestion du bouton activité physique  
  *******************************************************************************************/
 public void onClickPhysicalActivity(View v){
	 setContentView(R.layout.view_edit_physical_activity);
	 this.mUserActivity.setType(this.mResources.getInteger(R.integer.ACTIVITY_ACTIVITY));
	 this.mUserActivity.setTitle(this.mResources.getString(R.string.TITLE_PHYSICAL_ACTIVITY));
 }
  
 
 
 /*******************************************************************************************
  * Méthode : onClickWeight()
  *           Gestion du bouton poids  
  *******************************************************************************************/
 public void onClickWeight(View v){
	 setContentView(R.layout.view_edit_weight);
	 this.mUserActivity.setType(this.mResources.getInteger(R.integer.ACTIVITY_WEIGHT));
	 

 // 	poids par defaut
	 TextView tv =(TextView)findViewById(R.id.userActivity_Editor_tv_kilos);
		tv.setText("40");
	 tv =(TextView)findViewById(R.id.userActivity_Editor_tv_grammes);
		tv.setText("00");
 }
 
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
 
	 
	 
	
	    
 

	    

public void addKilo(View v){
	TextView tv =(TextView)findViewById(R.id.userActivity_Editor_tv_kilos);
	tv.setText(String.valueOf(Integer.parseInt(tv.getText().toString())+1));
	setWeight();
}

public void removeKilo(View v){
	TextView tv =(TextView)findViewById(R.id.userActivity_Editor_tv_kilos);
	tv.setText(String.valueOf((Integer.parseInt(tv.getText().toString())-1 <=0)?0:Integer.parseInt(tv.getText().toString())-1));
	setWeight();
}


public void addGramme(View v){
	TextView tv =(TextView)findViewById(R.id.userActivity_Editor_tv_grammes);
	tv.setText(String.valueOf((Integer.parseInt(tv.getText().toString())+1 >9)?0:Integer.parseInt(tv.getText().toString())+1));
	setWeight();
}


public void removeGramme(View v){
	TextView tv =(TextView)findViewById(R.id.userActivity_Editor_tv_grammes);
	tv.setText(String.valueOf((Integer.parseInt(tv.getText().toString())-1 <0)?9:Integer.parseInt(tv.getText().toString())-1));
	setWeight();
}



private void setWeight(){
	
	TextView tv =(TextView)findViewById(R.id.userActivity_Editor_tv_kilos);
	mUserActivity.getWeight().setInt_part(Integer.parseInt(tv.getText().toString()));
	
	tv =(TextView)findViewById(R.id.userActivity_Editor_tv_grammes);
	mUserActivity.getWeight().setDecimalPart(Integer.parseInt(tv.getText().toString()));
	
	mUserActivity.setTitle(mUserActivity.getWeight().format());
}
	    
}