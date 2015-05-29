package com.rdupuis.amikcal.useractivity;

import java.util.Calendar;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;

import com.rdupuis.amikcal.commons.Act_Editor;
import com.rdupuis.amikcal.commons.AmiKcalFactory;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.AppConsts.UA_CLASS_CD_MAP;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.data.ContentDescriptorObj;

/**
 * <b>Ecran d'édition des activitées de l'utilisateur.</b>
 * <p>
 * les activitées sont :
 * <ul>
 * <li>les repas</li>
 * <li>les activitées physiques</li>
 * <li>les pesées</li>
 * <li>les recettes</li>
 * </ul>
 * </p>
 * 
 * @author Rodolphe Dupuis
 * @version 0.1
 */
public class Act_UserActivity_Editor extends Act_Editor {
       
    //zone possible dans l'intent:
    //par rapport à la classe de base EDITOR, pour les UA on a besoin de 
    //gérer une date.
    public static final String INPUT____DAY = "_day";
    Calendar input_day;

    //
    UserActivity edited_UserActivity;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	this.input_day = ToolBox.parseCalendar(getIntent().getStringExtra(this.INPUT____DAY));
    }

    // Recharger une Activitée
    public void reloadUserActivity(long _id) {

	AmiKcalFactory factory = new AmiKcalFactory(this);

	this.edited_UserActivity = factory.load_UserActivity(_id);

    }

    /******************************************************************************************
     * On prépare les données pour mla mise à jour de la base
     ******************************************************************************************/
    private ContentValues getContentValues() {
	ContentValues val = new ContentValues();

	// Ajout des infos communes à toutes les activitées
	setGenericValues(val);

	// Ajout des infos spécifiques ni necessaire
	setSpecificValues(val);
	return val;
    }

    /**
     * ========================================================================
     * Methode pour alimenter les données spécifiques si necessaire.
     * ========================================================================
     */
    public ContentValues setSpecificValues(ContentValues val) {
	return val;
	    }

    /**
     * =======================================================================
     * Ajout des infos communes à toutes les activitées
     * 
     * ======================================================================
     */
    private ContentValues setGenericValues(ContentValues val) {
	// id de l'activitée
	val.put(ContentDescriptorObj.TB_UserActivities.Columns.ID, (edited_UserActivity.getId() == AppConsts.NO_ID) ? null
		: edited_UserActivity.getId());
	// titre
	val.put(ContentDescriptorObj.TB_UserActivities.Columns.TITLE, edited_UserActivity.getTitle());
	// Date
	val.put(ContentDescriptorObj.TB_UserActivities.Columns.DATE, ToolBox.getSqlDateTime(edited_UserActivity.getDay()));

	// class : on utilise la mapping pour transformer l'ENUM Class en Byte
	// stoké dans la Database.
	UA_CLASS_CD_MAP ua_cd_map = new UA_CLASS_CD_MAP();
	val.put(ContentDescriptorObj.TB_UserActivities.Columns.CLASS, ua_cd_map._out.get((edited_UserActivity.getType())));

	// date de mise à jour
	val.put(ContentDescriptorObj.TB_UserActivities.Columns.LAST_UPDATE, ToolBox.getCurrentTimestamp());

	return val;
    }

    /*******************************************************************************************
     * Méthode : update() metre à jour l'enregistrement
     *******************************************************************************************/
    public void updateUActivity() {
	ContentValues val = getContentValues();
	Uri uriUpdate = ContentUris.withAppendedId(ContentDescriptorObj.TB_UserActivities.UPDATE_USER_ACTIVITY_URI,
		edited_UserActivity.getId());
	this.getContentResolver().update(uriUpdate, val, String.valueOf(this.edited_UserActivity.getId()), null);
    }

    /*******************************************************************************************
     * Méthode : insert() insérer une nouvelle occurence
     *******************************************************************************************/
    public void insertUActivity() {
	ContentValues val = getContentValues();
	this.getContentResolver().insert(ContentDescriptorObj.TB_UserActivities.INSERT_USER_ACTIVITY_URI, val);
    }

    /*******************************************************************************************
     * Méthode : closeActivity() ferme l'activité
     *******************************************************************************************/
    protected void closeEditor() {
	// on appelle setResult pour déclancher le onActivityResult de
	// l'activity mère.
	setResult(RESULT_OK, this.getIntent());
	// On termine l'Actvity
	finish();
    }

}