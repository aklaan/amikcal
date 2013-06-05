package com.rdupuis.amikcal.commons;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.energy.EnergyObj;
import com.rdupuis.amikcal.equivalence.EquivalenceObj;
import com.rdupuis.amikcal.unitofmeasure.UnitOfMeasureObj;
import com.rdupuis.amikcal.useractivity.UserActivityObj;

public final class AmiKcalFactory {

	public ContentResolver contentResolver;

	
/**
 * Retourne une énergie stocké dans la base à partir de son id. 
 * @since  01-06-2012
 * @param _id - Identifiant 
 * @return (EnergyObj) un objet "énergie"
 **/	
public  EnergyObj createEnergyObjFromId(long _id){
	
	EnergyObj e = new EnergyObj();
	
	e.setId(_id);
	
	Uri selectUri = ContentUris.withAppendedId(ContentDescriptorObj.Energies.URI_CONTENT_ENERGIES,_id);
	Cursor cursor = this.contentResolver.query(selectUri , null, null, null, null);	

       final int INDX_ENERGY_NAME = cursor.getColumnIndex(ContentDescriptorObj.Energies.Columns.NAME);
       final int INDX_QUANTITY = cursor.getColumnIndex(ContentDescriptorObj.Energies.Columns.QUANTITY);         
       final int INDX_MNT_ENERGY = cursor.getColumnIndex(ContentDescriptorObj.Energies.Columns.MNT_ENERGY);
       final int INDX_MNT_PROTEINS = cursor.getColumnIndex(ContentDescriptorObj.Energies.Columns.MNT_PROTEINS);
       final int INDX_MNT_GLUCIDS = cursor.getColumnIndex(ContentDescriptorObj.Energies.Columns.MNT_GLUCIDS);
       final int INDX_MNT_LIPIDS = cursor.getColumnIndex(ContentDescriptorObj.Energies.Columns.MNT_LIPIDS);
       
       final int INDX_FK_UNIT = cursor.getColumnIndex(ContentDescriptorObj.Energies.Columns.FK_UNIT);
       
       // faire un move First pour positionner le pointeur, sinon on pointe sur null
       if (cursor.moveToFirst()) {
       
       		e.setName(cursor.getString(INDX_ENERGY_NAME));
        	e.setCalories(Float.parseFloat(cursor.getString(INDX_MNT_ENERGY)));
        	e.setProteins(Float.parseFloat(cursor.getString(INDX_MNT_PROTEINS)));
        	e.setGlucids(Float.parseFloat(cursor.getString(INDX_MNT_GLUCIDS)));
        	e.setLipids(Float.parseFloat(cursor.getString(INDX_MNT_LIPIDS)));
        	e.setQuantityReference((Float.parseFloat(cursor.getString(INDX_QUANTITY))));
            e.unit=this.createUnitOfMeasureObjFromId(Long.parseLong(cursor.getString(INDX_FK_UNIT)));
       
       
       }
       else {
         	e.setName("");
         	e.setCalories(0.0f);
         	e.setProteins(0.0f);
        	e.setGlucids(0.0f);
        	e.setLipids(0.0f);
        	e.setQuantityReference(0.0f);
        	e.unit = new UnitOfMeasureObj();
        	
       }
       
	
	cursor.close();
	return e;
	
	
}

/**
 * Retourne une unitée de mesure stocké dans la base à partir de son id. 
 * @since 01-06-2012
 * @param _id
 * @return (UnitOfMeasureObj) un objet "Unitée de mesure"
 **/

public  UnitOfMeasureObj createUnitOfMeasureObjFromId(long _id){
	
	UnitOfMeasureObj u = new UnitOfMeasureObj();
		
	u.setId(_id);
	
	Uri selectUri = ContentUris.withAppendedId(ContentDescriptorObj.Units.URI_CONTENT_UNITS,_id);
	Cursor cursor = this.contentResolver.query(selectUri , null, null, null, null);	

       final int INDX_NAME = cursor.getColumnIndex(ContentDescriptorObj.Units.Columns.NAME);
       final int INDX_SYMBOL = cursor.getColumnIndex(ContentDescriptorObj.Units.Columns.SYMBOL);         
       
       // faire un move First pour positionner le pointeur, sinon on pointe sur null
       if (cursor.moveToFirst()) {
       
       		u.setName(cursor.getString(INDX_NAME));
        	u.setSymbol(cursor.getString(INDX_SYMBOL));
       }
       else {
         	u.setName("");
         	u.setSymbol("");
         	}
	cursor.close();
	return u;
	
	
}

/**
 * Retourne une équivalence de mesure stocké dans la base à partir de son id. 
 * @since 01-06-2012
 * @param _id
 * @return (EquivalenceObj) un objet "Equivalence"
 **/
public  EquivalenceObj createEquivalenceObjFromId(long _id){

	EquivalenceObj mEquivalence = new EquivalenceObj();

    Uri selectUri = ContentUris.withAppendedId(ContentDescriptorObj.Equivalences.URI_CONTENT_EQUIVALENCES,_id);
    
  //On crée un curseur pour lire la table des aliments    
    Cursor cursor = this.contentResolver.query(selectUri , null, null, null, null);
 
    final int INDX_FK_ENERGY = cursor.getColumnIndex(ContentDescriptorObj.Equivalences.Columns.FK_ENERGY);
    final int INDX_FK_UNIT_IN = cursor.getColumnIndex(ContentDescriptorObj.Equivalences.Columns.FK_UNIT_IN);
    final int INDX_QUANTITY_OUT = cursor.getColumnIndex(ContentDescriptorObj.Equivalences.Columns.QUANTITY_OUT);         
    final int INDX_FK_UNIT_OUT = cursor.getColumnIndex(ContentDescriptorObj.Equivalences.Columns.FK_UNIT_OUT);
 
 // faire un move First pour positionner le pointeur, sinon on pointe sur null
    if (cursor.moveToFirst()) {
 	
    	mEquivalence.setId(_id);
    	mEquivalence.energy=createEnergyObjFromId(cursor.getLong(INDX_FK_ENERGY));
    	mEquivalence.unitIn=createUnitOfMeasureObjFromId(cursor.getLong(INDX_FK_UNIT_IN));
    	mEquivalence.unitOut=createUnitOfMeasureObjFromId(cursor.getLong(INDX_FK_UNIT_OUT));
	 
    	Log.i("Qté lue",String.valueOf(cursor.getFloat(INDX_QUANTITY_OUT)));
    	mEquivalence.setQuantity(cursor.getFloat(INDX_QUANTITY_OUT));		 
	
    }
    else {
    	Log.e("AmickalFactory.createEquivalenceObjFromId","Equivalence non trouvé");
     }

	
	cursor.close();
	return mEquivalence;
	
	}


	/**
	 * 	Retourne une Activité utilisateur stockée dans la base à partir de son id. 
	 * @since 01-06-2012
	 * @param _id
	 * @return (UserActivityObj) un objet "Activité d'utilisateur"
	 **/
	public UserActivityObj createUserActivityObjFromId(long _id){
	 
	UserActivityObj mUserActivityObj = new UserActivityObj();
	
    Uri request = ContentUris.withAppendedId(ContentDescriptorObj.UserActivities.URI_SELECT_USER_ACTIVITIES ,_id);

    Cursor cur = this.contentResolver.query(request , null, null, null, null);
    
    final int ACTIVITY_ID = cur.getColumnIndex(ContentDescriptorObj.UserActivities.Columns.ID);
    final int ACTIVITY_TITLE = cur.getColumnIndex(ContentDescriptorObj.UserActivities.Columns.TITLE);
    final int ACTIVITY_TYPE = cur.getColumnIndex(ContentDescriptorObj.UserActivities.Columns.TYPE);
    final int ACTIVITY_DATE = cur.getColumnIndex(ContentDescriptorObj.UserActivities.Columns.DATE);
    
    // faire un move First pour positionner le pointeur, sinon on pointe sur null
    if (cur.moveToFirst()) {
     
    	mUserActivityObj.set_id(cur.getLong(ACTIVITY_ID ));
    	mUserActivityObj.setTitle(cur.getString(ACTIVITY_TITLE));
    	mUserActivityObj.setType(cur.getInt(ACTIVITY_TYPE));
    	mUserActivityObj.setDay(ToolBox.parseSQLDatetime(cur.getString(ACTIVITY_DATE)));
    
    	if (mUserActivityObj.getType()==3){
    		mUserActivityObj.setWeight(new WeightObj(mUserActivityObj.getTitle()));
    		
    	}
    	
    }
    else {
    	mUserActivityObj.setTitle("UserActivity Not Found");
    
    }
    return mUserActivityObj;
}

























} //* end-class
