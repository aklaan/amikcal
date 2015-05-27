package com.rdupuis.amikcal.useractivity;

import java.util.Calendar;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

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
public abstract class Act_UserActivity_EditorCommons extends Activity {

    public enum EditMode {
	CREATE, EDIT
    }

    Long mId;
    boolean morning = true;
    int timeRange;
    Calendar currentDay;
    long currentId;
    EditMode editMode;
    public static final String INPUT____DAY = "_day";
    public static final String INPUT____UA_ID = "ua_id";

    // ne doit pas servir car si on fait un nouveau, on prend la date du jour
    // et si on edite un existant, la date est déjà connue
    // static final String
    // INTENT_IN____UA_EDITOR_COMMONS____DAY_OF_THE_USER_ACTIVITY =
    // "DAY_OF_THE_USER_ACTIVITY";
    ContentResolver contentResolver;
    UserActivity mUserActivity;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	// mIntent = getIntent();
	// contentResolver = this.getContentResolver();
	// mResources = getResources();

	// dans l'Intent, on récupère l'id de l'objet à éditer
	long _id = getIntent().getLongExtra(this.INPUT____UA_ID, AppConsts.NO_ID);

	// si l'id de objet est correct, on tente de le recharger
	if (_id != AppConsts.NO_ID) {
	    this.editMode = EditMode.EDIT;
	    reloadUserActivity(_id);

	    Toast.makeText(this, "Edition", Toast.LENGTH_LONG).show();
	} else {
	    // si l'id est NO_ID, c'est que l'on crée un nouvel objet depuis
	    // l'écran de choix
	    this.editMode = EditMode.CREATE;

	}
	;

    }

    // fin du onCreate

    // Recharger une Activitée
    public void reloadUserActivity(long _id) {

	AmiKcalFactory factory = new AmiKcalFactory(this);

	this.mUserActivity = factory.load_UserActivity(_id);

    }

    /******************************************************************************************
     * On prépare les données pour mla mise à jour de la base
     ******************************************************************************************/
    private ContentValues getContentValues() {
	ContentValues val = new ContentValues();

	// Ajout des infos communes à toutes les activitées
	setGenericValues(val);

	// Ajout des infos propres aux activitées
	setSpecificValues(val);
	return val;
    }

    /**
     * ========================================================================
     * Methode Abstraite pour forcer les "filles" à spécifier leur données
     * spécifiques si elles en ont.
     * ========================================================================
     */
    abstract public ContentValues setSpecificValues(ContentValues val);

    /**
     * =======================================================================
     * Ajout des infos communes à toutes les activitées
     * 
     * ======================================================================
     */
    private ContentValues setGenericValues(ContentValues val) {
	// id de l'activitée
	val.put(ContentDescriptorObj.TB_UserActivities.Columns.ID, (mUserActivity.getId() == AppConsts.NO_ID) ? null
		: mUserActivity.getId());
	// titre
	val.put(ContentDescriptorObj.TB_UserActivities.Columns.TITLE, mUserActivity.getTitle());
	// Date
	val.put(ContentDescriptorObj.TB_UserActivities.Columns.DATE, ToolBox.getSqlDateTime(mUserActivity.getDay()));

	// class : on utilise la mapping pour transformer l'ENUM Class en Byte
	// stoké dans la Database.
	UA_CLASS_CD_MAP ua_cd_map = new UA_CLASS_CD_MAP();
	val.put(ContentDescriptorObj.TB_UserActivities.Columns.CLASS, ua_cd_map._out.get((mUserActivity.getType())));

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
		mUserActivity.getId());
	this.getContentResolver().update(uriUpdate, val, String.valueOf(this.mUserActivity.getId()), null);
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