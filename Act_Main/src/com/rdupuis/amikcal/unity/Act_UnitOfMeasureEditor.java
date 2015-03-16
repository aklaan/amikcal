package com.rdupuis.amikcal.unity;




import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.data.ContentDescriptorObj;


 
public class Act_UnitOfMeasureEditor extends Activity {
 
	
     Intent mIntent;
     
     Date last_updt;
     String unit_name;
     Unity mUnit;
     ContentResolver contentResolver;
     
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_edit_unit_of_measure);
        mUnit = new Unity();
        mIntent = getIntent();
        contentResolver = this.getContentResolver();
     
        
        try {
        	mUnit.setId(Long.parseLong(mIntent.getStringExtra("UnitId")));
        	loadUnitFromDb(mUnit);	
        	refreshScreen();
        } catch (Exception e) {Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();};
      
      
        
    }//fin du onCreate
    
    /****************************************************************************************
     * 
     * @param wrkUnit
     ***************************************************************************************/
    private void loadUnitFromDb(Unity wrkUnit){
    	
        Uri request = ContentUris.withAppendedId(ContentDescriptorObj.TB_Units.URI_SELECT_UNIT,wrkUnit.getId());

        Cursor cur = this.getContentResolver().query(request , null, null, null, null);
        
        final int INDX_UNIT_NAME   = cur.getColumnIndex(ContentDescriptorObj.TB_Units.Columns.LONG_NAME);
        final int INDX_UNIT_SYMBOL = cur.getColumnIndex(ContentDescriptorObj.TB_Units.Columns.SHORT_NAME);
        
        // faire un move First pour positionner le pointeur, sinon on pointe sur null
        if (cur.moveToFirst()) {
         
        	wrkUnit.setLongName(cur.getString(INDX_UNIT_NAME));
        	wrkUnit.setShortName(cur.getString(INDX_UNIT_SYMBOL));
        	
        }
        else {
        	Toast.makeText(this, "Unitée inconnue", Toast.LENGTH_SHORT).show();
        
        }
    	
    	
    }
  
    /******************************************************************************************
     * onValidateClick : Mettre à jour les informations saisies dans la base de donnée 
     * @param v
     ******************************************************************************************/
    public void onValidateClick(View v) {
    	
    	// On prépare les informations à mettre à jour
    	ContentValues val = new ContentValues();
    	
    	refreshUnit();
    	
    	val.put(ContentDescriptorObj.TB_Units.Columns.LONG_NAME, this.mUnit.getLongName() );
    	val.put(ContentDescriptorObj.TB_Units.Columns.SHORT_NAME, this.mUnit.getShortName() );
    	
    	
    	if (this.mUnit.getId()==0){
    		this.getContentResolver().insert(ContentDescriptorObj.TB_Units.URI_INSERT_UNIT, val);
    	}
    	else {
    		this.getContentResolver().update(ContentDescriptorObj.TB_Units.URI_UPDATE_UNIT, val, String.valueOf(this.mUnit.getId()),null);
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
    	EditText ed= (EditText)findViewById(R.id.unitview_name);
    	ed.setText(this.mUnit.getLongName());
    
    	ed= (EditText)findViewById(R.id.unitview_symbol);
    	ed.setText(this.mUnit.getShortName());
    }
   
    
    /******************************************************************************************
     * refreshUnit : 
     *   
     ******************************************************************************************/
    private void refreshUnit(){
    	EditText ed= (EditText)findViewById(R.id.unitview_name);
    	this.mUnit.setLongName(ed.getText().toString());
    
    	ed= (EditText)findViewById(R.id.unitview_symbol);
    	this.mUnit.setShortName(ed.getText().toString());
    }
 
    

    
}