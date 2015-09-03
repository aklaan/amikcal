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
 * <b>Ecran d'�dition des activit�es de l'utilisateur.</b>
 * <p>
 * les activit�es sont :
 * <ul>
 * <li>les repas</li>
 * <li>les activit�es physiques</li>
 * <li>les pes�es</li>
 * <li>les recettes</li>
 * </ul>
 * </p>
 * 
 * @author Rodolphe Dupuis
 * @version 0.1
 */
public class Act_UserActivity_Editor extends Act_Editor {
       
    //zone possible dans l'intent:
    //par rapport � la classe de base EDITOR, pour les UA on a besoin de 
    //g�rer une date.
    public static final String INPUT____DAY = "_day";
    private Calendar input_day;
    private UserActivity edited_UserActivity;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	this.input_day = ToolBox.parseCalendar(getIntent().getStringExtra(this.INPUT____DAY));
	UserActivity_Manager uam = new UserActivity_Manager(this);
		this.setEdited_UserActivity(uam.load(this.getEdited_object_id()));
    }

    
    /******************************************************************************************
     * On pr�pare les donn�es pour mla mise � jour de la base
     ******************************************************************************************/
    private ContentValues getContentValues() {
	ContentValues val = new ContentValues();

	// Ajout des infos communes � toutes les activit�es
	setGenericValues(val);

	// Ajout des infos sp�cifiques ni necessaire
	setSpecificValues(val);
	return val;
    }

    /**
     * ========================================================================
     * Methode pour alimenter les donn�es sp�cifiques si necessaire.
     * ========================================================================
     */
    public ContentValues setSpecificValues(ContentValues val) {
	return val;
	    }

    /**
     * =======================================================================
     * Ajout des infos communes � toutes les activit�es
     * 
     * ======================================================================
     */
    private ContentValues setGenericValues(ContentValues val) {
	// id de l'activit�e
	val.put(ContentDescriptorObj.TB_UserActivities.Columns.ID, (getEdited_UserActivity().getDatabaseId() == AppConsts.NO_ID) ? null
		: getEdited_UserActivity().getDatabaseId());
	// titre
	val.put(ContentDescriptorObj.TB_UserActivities.Columns.TITLE, getEdited_UserActivity().getTitle());
	// Date
	val.put(ContentDescriptorObj.TB_UserActivities.Columns.DATE, ToolBox.getSqlDateTime(getEdited_UserActivity().getDay()));

	// class : on utilise la mapping pour transformer l'ENUM Class en Byte
	// stok� dans la Database.
	UA_CLASS_CD_MAP ua_cd_map = new UA_CLASS_CD_MAP();
	val.put(ContentDescriptorObj.TB_UserActivities.Columns.CLASS, ua_cd_map._out.get((getEdited_UserActivity().getActivityType())));

	// date de mise � jour
	val.put(ContentDescriptorObj.TB_UserActivities.Columns.LAST_UPDATE, ToolBox.getCurrentTimestamp());

	return val;
    }

    /*******************************************************************************************
     * M�thode : update() metre � jour l'enregistrement
     *******************************************************************************************/
    public void updateUActivity() {
	ContentValues val = getContentValues();
	Uri uriUpdate = ContentUris.withAppendedId(ContentDescriptorObj.TB_UserActivities.UPDATE_USER_ACTIVITY_URI,
		getEdited_UserActivity().getDatabaseId());
	this.getContentResolver().update(uriUpdate, val, String.valueOf(this.getEdited_UserActivity().getDatabaseId()), null);
    }

    /*******************************************************************************************
     * M�thode : insert() ins�rer une nouvelle occurence
     *******************************************************************************************/
    public void insertUActivity() {
	ContentValues val = getContentValues();
	this.getContentResolver().insert(ContentDescriptorObj.TB_UserActivities.INSERT_USER_ACTIVITY_URI, val);
    }

    /*******************************************************************************************
     * M�thode : closeActivity() ferme l'activit�
     *******************************************************************************************/
    protected void closeEditor() {
	// on appelle setResult pour d�clancher le onActivityResult de
	// l'activity m�re.
	setResult(RESULT_OK, this.getIntent());
	// On termine l'Actvity
	finish();
    }

	public Calendar getInput_day() {
		return input_day;
	}

	public void setInput_day(Calendar input_day) {
		this.input_day = input_day;
	}

	public UserActivity getEdited_UserActivity() {
		return edited_UserActivity;
	}

	public void setEdited_UserActivity(UserActivity edited_UserActivity) {
		this.edited_UserActivity = edited_UserActivity;
	}

}