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
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AmiKcalFactory;
import com.rdupuis.amikcal.commons.AmikcalVar;
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

	Intent mIntent;
	Long mId;
	boolean morning = true;
	int timeRange;
	Calendar currentDay;
	long currentId;
	Resources mResources;

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
		long _id = getIntent().getLongExtra(
				AmikcalVar.INPUT____UA_EDITOR____USER_ACTIVITY_ID,
				AmikcalVar.NO_ID);

		// si l'id de objet est correct, on tente de le recharger
		if (_id != AmikcalVar.NO_ID) {
			reloadUserActivity(_id);

			Toast.makeText(this, "Edition", Toast.LENGTH_LONG).show();
		} else {
			// si l'id est NO_ID, c'est que l'on crée un nouvel objet depuis l'écran de choix
			this.mUserActivity = new UserActivityLunch();
			this.mUserActivity.setDay(ToolBox
					.parseCalendar(getIntent().getStringExtra(AmikcalVar.INPUT____UA_EDITOR____DAY)));

		}
		;

	}

	// fin du onCreate

	// Recharger une Activitée
	public void reloadUserActivity(long _id) {

		AmiKcalFactory factory = new AmiKcalFactory();
		factory.contentResolver = this.getContentResolver();
		this.mUserActivity = factory.reloadUserActivityObjFromId(_id);

	}

	/******************************************************************************************
	 * On prépare les données pour mla mise à jour de la base
	 ******************************************************************************************/
	private ContentValues getContentValues() {
		ContentValues val = new ContentValues();

		// ajout des infos communes à toutes les activitées
		addGenericValues(val);

		// Ajout des infos propres aux activitées
		addSpecificValues(val);
		return val;
	}

	/**========================================================================
	 * Methode Abstraite pour forcer les "filles" à spécifier leur données
	 * spécifiques si elles en ont.
	 * ========================================================================
	 */
	abstract public ContentValues addSpecificValues(ContentValues val);

	/**
	 * =======================================================================
	 * Ajout des infos communes à toutes les activitées
	 * 
	 * ======================================================================
	 */
	private ContentValues addGenericValues(ContentValues val) {
		//id de l'activitée
		val.put(ContentDescriptorObj.UserActivities.Columns.ID, (mUserActivity
				.get_id() == AmikcalVar.NO_ID) ? null : mUserActivity.get_id());
		//titre
		val.put(ContentDescriptorObj.UserActivities.Columns.TITLE,
				mUserActivity.getTitle());
		//Date
		val.put(ContentDescriptorObj.UserActivities.Columns.DATE,
				ToolBox.getSqlDateTime(mUserActivity.getDay()));

		//type
		val.put(ContentDescriptorObj.UserActivities.Columns.TYPE, mUserActivity
				.getType().name());
		
		//date de mise à jour
		val.put(ContentDescriptorObj.UserActivities.Columns.LAST_UPDATE,
				ToolBox.getCurrentTimestamp());

		return val;
	}

	/*******************************************************************************************
	 * Méthode : update() metre à jour l'enregistrement
	 *******************************************************************************************/
	public void updateUActivity() {
		ContentValues val = getContentValues();
		Uri uriUpdate = ContentUris.withAppendedId(
				ContentDescriptorObj.UserActivities.URI_UPDATE_USER_ACTIVITIES,
				mUserActivity.get_id());
		this.getContentResolver().update(uriUpdate, val,
				this.mUserActivity.get_id().toString(), null);
	}

	/*******************************************************************************************
	 * Méthode : insert() insérer une nouvelle occurence
	 *******************************************************************************************/
	public void insertUActivity() {
		ContentValues val = getContentValues();
		this.getContentResolver().insert(
				ContentDescriptorObj.UserActivities.URI_INSERT_USER_ACTIVITIES,
				val);
	}

	/*******************************************************************************************
	 * Méthode : closeActivity() ferme l'activité
	 *******************************************************************************************/
	protected void closeEditor() {
		// on appelle setResult pour déclancher le onActivityResult de
		// l'activity mère.
		setResult(RESULT_OK, mIntent);
		// On termine l'Actvity
		finish();
	}

}