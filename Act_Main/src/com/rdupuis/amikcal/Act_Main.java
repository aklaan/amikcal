package com.rdupuis.amikcal;

import java.io.File;
import java.util.ArrayList;
//import com.rdupuis.amikcal.ContentDescriptor;
import com.rdupuis.amikcal.R;

import com.rdupuis.amikcal.commons.Act_Calendar;
import com.rdupuis.amikcal.commons.Act_Techinfo;
import com.rdupuis.amikcal.commons.AmiKcalFactory;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.data.DatabaseObj;
import com.rdupuis.amikcal.day.Act_Day;
import com.rdupuis.amikcal.energy.Act_EnergyList;
import com.rdupuis.amikcal.equivalence.Act_EquivalenceList;
import com.rdupuis.amikcal.useractivity.UserActivities_FragmentsSlider;
import com.rdupuis.amikcal.commons.numericpad.Act_NumericPad;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
//import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

enum ErrorStatus {
    NO_ERROR, ERROR_1, ERROR_2
};
/**
 * <b>Act_Main est la classe qui affiche la vue d'acceuil.</b>
 * @author Rodolphe Dupuis
 * @version 0.1
 */


public class Act_Main extends Activity {
    
	TextView tv;
	ContentResolver contentResolver;
	Cursor cur;
	SimpleCursorAdapter adapter;
	private Context mContext;
	private ProgressDialog mProgressDialog ;
	  
	    
	public static final int MSG_ERR = 0;
	public static final int MSG_CNF = 1;
	public static final int MSG_IND = 2;
	 
	    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.mainview);
		//setContentView(R.layout.fragment_day_user_activities_synthese);
        contentResolver = this.getContentResolver();
      //  verifierMiseAJourDB();
        
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/BIRDMAN_.TTF");
        TextView tv = (TextView) findViewById(R.id.main_tv_titre);
       
        ArrayList<Integer> button_list = new ArrayList<Integer>();
        button_list.add(R.id.main_tv_titre);
        button_list.add(R.id.main_btn_journee);
        button_list.add(R.id.main_btn_equiv);
        button_list.add(R.id.main_btn_recettes);
        button_list.add(R.id.main_btn_param);
        button_list.add(R.id.main_bt_calorie_table);
        button_list.add(R.id.main_bt_techinfo);
        button_list.add(R.id.main_btn_profil);
        button_list.add(R.id.main_btn_test);
        
        for (Integer i:button_list){
        	tv = (TextView) findViewById(i);	
        	tv.setTypeface(tf);
        }
       
       
        
        
        
    }
    
	
	private  Handler mHandler = new Handler() {
	    public void handleMessage(Message msg) {
	        String text2display = null;
	        switch (msg.what) {
	        case MSG_IND:
	            if (Act_Main.this.mProgressDialog.isShowing()) {
	            	Act_Main.this.mProgressDialog.setMessage(((String) msg.obj));
	            }
	            break;
	        case MSG_ERR:
	            text2display = (String) msg.obj;
	            Toast.makeText(Act_Main.this.mContext, "Error: " + text2display,
	                    Toast.LENGTH_LONG).show();
	            if (Act_Main.this.mProgressDialog.isShowing()) {
	            	Act_Main.this.mProgressDialog.dismiss();
	            }
	            break;
	        case MSG_CNF:
	            text2display = (String) msg.obj;
	            Toast.makeText(Act_Main.this.mContext, "Info: " + text2display,
	                    Toast.LENGTH_LONG).show();
	            if (Act_Main.this.mProgressDialog.isShowing()) {
	            	Act_Main.this.mProgressDialog.dismiss();
	            }
	            break;
	        default: // should never happen
	            break;
	        }
	    }
	};
	
	
	/**
	* readAmikcalDB tente d'effectuer une lecture de la base de donnée
	* cette lecture va permettre de déclancher la mise à jour de la base si la version de la DB a changée.
	*/
	protected ErrorStatus readAmikcalDB() {
	    try {
	        	    
	        AmiKcalFactory factory  = new AmiKcalFactory();
	        factory.contentResolver=this.getContentResolver();
	        factory.createUnitOfMeasureObjFromId(1);
	    
	    } catch (Exception e) {
	    }
	    return ErrorStatus.NO_ERROR;
	}
	
	
	/**********************************************
	* verifierMiseAJourDB
	* 
	* la mise à jour de la database est une opération qui peu être longue.
	* ici, on prévient d'utilisateur qu'il faut patienter si une mise 
	* à jour est necessaire.
    *	
	**********************************************/
	public void verifierMiseAJourDB(){
	try {
		if (ToolBox.getAmikcalDbVersion()<DatabaseObj.DATABASE_VERSION){
		
	       mProgressDialog = ProgressDialog.show(this, "Veuillez-patienter",
	                "Long operation starts...", true);
	       
	       new Thread((new Runnable() {
	           
	           public void run() {
	            	 
	                  Message msg = null;
	            	  String progressBarData = "Mise à jour de la base";
	            	 
	               // populates the message
	                  msg = mHandler.obtainMessage(MSG_IND, (Object) progressBarData);
	      
	               // sends the message to our handler
	                  mHandler.sendMessage(msg);
	                 readAmikcalDB();
	            	
	                  msg = mHandler.obtainMessage(MSG_CNF,
	                        "Mise à jour terminée OK !");
	               // sends the message to our handler
	                  mHandler.sendMessage(msg);
	            }
	        })).start();
		}
	} catch (Exception e){};
	}
	
	/**
	 * createRefActivityCatalog : méthode générique qui permet de créer un fichier 
	 * catalogue d'activitées dans le cas où i n'y en a pas
	 */
	public void createRefActivityCatalog(){
		
	}
	
	
	/**
	 * isActivityCatalogExist : teste l'existence d'un catalogue d'activité
	 * 
	 */
    public boolean isActivityCatalogExist(){
    	File fichier = new File(getString(R.string.file_refactivity_name));
    	return fichier.exists();
		
	}
	   
    
    /**
	 * isAppCalendarExist : teste l'existence d'un calendirer
	 * 
	 */
    public boolean isAppCalendarExist(){
    	File fichier = new File(getString(R.string.file_calendar_name));
    	return fichier.exists();
	}
    
    
    public void appelPadNum(View v){
    	
       // ça ça marche en utilisant les class
       // je jn'ai pas réussi via une URL.
    	Intent intent = new Intent(this,Act_NumericPad.class);
    	
       // on passe la variable result=0 en paramètre
       // ici ça ne sert à rien sinon pour l'exemple.
       // intent.putExtra("result", "0"); 
        
    	startActivityForResult(intent, R.integer.NUMERICPAD);
    	
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
       
    	switch (requestCode) {
    	
		case  R.integer.CALENDAR  :
			    if (resultCode == RESULT_OK){
				
			    //	tv= (TextView)findViewById(R.id.tv1);
			    //	tv.setText("Vous avez choisi la date du : " + intent.getStringExtra("result"));
			    }
			    
				break;
		case  R.integer.NUMERICPAD      :
			
			 	if (resultCode == RESULT_OK){
					
			    //	tv= (TextView)findViewById(R.id.tv1);
			   // 	tv.setText(" Vous avez saisi : " + intent.getStringExtra("result"));
			    }
				break;
		default :break;
		
		}
    
    	
    	
    }
    
    public void onClickEquivalence(View v){
    	Intent intent = new Intent(this,Act_EquivalenceList.class);
    	startActivityForResult(intent,R.integer.EQUIVALENCE_EDITOR);
 
    }
    
    
    public void callCalendar(View v){
    	Intent intent = new Intent(this,Act_Calendar.class);
    	startActivityForResult(intent,R.integer.CALENDAR);
    }
    
    
    public void callTechinfo(View v){
    	Intent intent = new Intent(this,Act_Techinfo.class);
    	startActivity(intent);
    }
    
  
    
    public void callEnergyList(View v){
    	Intent intent = new Intent(this,Act_EnergyList.class);
    	startActivity(intent);
 
    }
  
    
    public void callDay(View v){
    	Intent intent = new Intent(this,Act_Day.class);
    	startActivity(intent);
 
    }
    
    public void test(View v){
    	
    	//Intent intent = new Intent(this,TestHorizontalActivity.class);
    	//Intent intent = new Intent(this,FragAct_NumericPad.class);
    	//Intent intent = new Intent(this,FragmentsSliderActivity.class);
    	Intent intent = new Intent(this,UserActivities_FragmentsSlider.class);
   
    	startActivity(intent);
    }
    
    
}