package com.rdupuis.amikcal.day;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.Act_Calendar;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.useractivity.Act_UserActivityList;

public class Act_Day extends Activity{

	Calendar mCalendar;
	DayObj mDay; 
	Resources mResources;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResources = getResources();
        
        setContentView(R.layout.view_ua_statistics_of_day);
        init();
        
    }

    /*************************************************************************
     * init : liste des actions a effectuer à la création
     * - par défaut, on initialise la date à la date du jour.
     * - on ajoute 1 au mois car android commence l'année au mois 0
     */
    private void init(){
    	mDay = new DayObj();
    	mDay.setDay(Calendar.getInstance()) ;
    	this.setTheme(R.style.CustomButton);
    	refreshScreen();
    }

    
  
    
    /**
     * Rafraichir l'écran
     */
    private void refreshScreen(){
    //	TextView tv  = (TextView)findViewById(R.id.dayview_navbar_tv_DayLabel);
   // 	tv.setText(mDay.getSqlDate()); 
    		
    	TextView tv = (TextView)findViewById(R.id.view_ua_statistic_of_day_tv_data_total_cube_energy);
    	tv.setText(getTotalEnergyOfDay(mDay.getSqlDate()));
        	
    	
    }
    
    
     
   
   
    /**
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
       
    	switch (requestCode) {
    	
		case  R.integer.CALENDAR  :
			    if (resultCode == RESULT_OK){
				
			    	mDay.setDay(intent.getStringExtra("result"));
			    	
			    }
			    
				break;
		
		default :break;
		
		}
    	refreshScreen();
    }
    
    
    /*************************************************************************
     * onClickCalendar:
     * 
     * Appel au calendrier pour choisir une date en particulier
     * 
     *************************************************************************/
    public void onClickCalendar(View v){
    	Intent intent = new Intent(this,Act_Calendar.class);
    	startActivityForResult(intent,R.integer.CALENDAR);
    	
    }
    
    /*************************************************************************
     * onClickActivities
     *
     * -Afficher les activitées du jour
     *
     *************************************************************************/
     
    public void onClickActivities(View v){
    	Intent intent = new Intent(this,Act_UserActivityList.class);
    	intent.putExtra(Act_UserActivityList.INPUT____DAY, mDay.getSqlDate()); 
    	startActivityForResult(intent,0);
    }
    
    
    /*************************************************************************
     * onClickBack
     *
     * -reculer d'un jour
     *
     *************************************************************************/
     
    public void onClickBack(View v){
    	this.mDay.getDay().add(Calendar.DATE,-1);
    	refreshScreen();
    }
    
    /*************************************************************************
     * onClickBack
     *
     * -reculer d'un jour
     *
     *************************************************************************/
     
    public void onClickNext(View v){
    	this.mDay.getDay().add(Calendar.DATE,1);
    	refreshScreen();
    }
    
    
    
    
 protected String getTotalEnergyOfDay(String DateToSelect){
	 String result ="";
    	
		Uri selectUri;
    	selectUri = ContentDescriptorObj.CustomQuery.URI_SUM_ENERGY_OF_DAY.buildUpon().appendPath(DateToSelect).build();
      
        Cursor cur = this.getContentResolver().query(selectUri, null, null, null, null);
            
        final int INDX_COL_ID = cur.getColumnIndex("SUM_MT_ENERGY");
             
             if (cur.moveToFirst()) {
            
            	 DecimalFormat decimalFormat = (DecimalFormat)DecimalFormat.getInstance();
                	decimalFormat.applyPattern("###0.00");
                	DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                    dfs.setDecimalSeparator('.');
                    decimalFormat.setDecimalFormatSymbols( dfs );
            	 
            	 result = decimalFormat.format(cur.getFloat(INDX_COL_ID));
            }
             cur.close();
    return result;
 }
    
 
 protected String getLastWeight(String DateToSelect){
	 String result = "";
 	
		Uri selectUri;
 	selectUri = ContentDescriptorObj.CustomQuery.URI_LAST_WEIGHT_FROM.buildUpon().appendPath(DateToSelect).build();
   
     Cursor cur = this.getContentResolver().query(selectUri, null, null, null, null);
         
     final int INDX_COL_ID = cur.getColumnIndex("maxweight");
          
          if (cur.moveToFirst()) {
         		
        		DecimalFormat decimalFormat = (DecimalFormat)DecimalFormat.getInstance();
               	decimalFormat.applyPattern("###.00");
               	DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                dfs.setDecimalSeparator('.');
                decimalFormat.setDecimalFormatSymbols( dfs );
        	  
        	  result = decimalFormat.format(cur.getFloat(INDX_COL_ID));
         }
          cur.close();
 return result;
}
 
 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
