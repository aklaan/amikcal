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
import com.rdupuis.amikcal.commons.ActivityType;
import com.rdupuis.amikcal.commons.AmiKcalFactory;
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
public class Act_UserActivity_EditorCommons extends Activity {

	Intent mIntent;
	Long mId;
	boolean morning = true;
	int timeRange;
	Calendar currentDay;
	long currentId;
	Resources mResources;
	final long NO_ID = -1l;

	// il me semble important de référencer dans la classe les noms de variables
	// attendues dans les INTENT. comme ça les autres classes peuvent savoir
	// quel sont les noms de variables qui peuvent être traité.
	static final String INTENT_IN____USER_ACTIVITY_EDITOR____ID_OF_THE_USER_ACTIVITY = "ID_OF_THE_USER_ACTIVITY";
	
	// ne doit pas servir car si on fait un nouveau, on prend la date du jour
	// et si on edite un existant, la date est déjà connue
	//static final String INTENT_IN____USER_ACTIVITY_EDITOR____DAY_OF_THE_USER_ACTIVITY = "DAY_OF_THE_USER_ACTIVITY";
	ContentResolver contentResolver;
	UserActivity mUserActivity;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mIntent = getIntent();
		contentResolver = this.getContentResolver();
		mResources = getResources();
		 
		// On récupère de l'Intent l'ID de l'activité séléctionnée.
		// si cet ID est null c'est que l'utilisateur souhaite créer une
		// nouvelle activitée
		try {
			
			long _id = Long.parseLong(mIntent
							.getStringExtra(INTENT_IN____USER_ACTIVITY_EDITOR____ID_OF_THE_USER_ACTIVITY));
	
			reloadUserActivity(_id);	
		} catch (Exception e) {
			
			createNewUserActivity();
			
			//mUserActivity.setDay(currentDay);
		}

		// * choix de la vue a afficher en fonction du type d'activité choisie
		if (this.mUserActivity.getType() == ActivityType.LUNCH) {
			setContentView(R.layout.view_edit_lunch);
			refreshScreen();

		} else if (this.mUserActivity.getType() == ActivityType.MOVE) {

			setContentView(R.layout.view_edit_physical_activity);
			refreshScreen();
		} else if (this.mUserActivity.getType() == ActivityType.WEIGHT) {

			setContentView(R.layout.view_edit_weight);

			TextView tv = (TextView) findViewById(R.id.userActivity_Editor_tv_kilos);

			tv.setText(String.valueOf(((UserActivityWeight) mUserActivity)
					.getWeight().getInt_part()));
			tv = (TextView) findViewById(R.id.userActivity_Editor_tv_grammes);
			tv.setText(String.valueOf(((UserActivityWeight) mUserActivity)
					.getWeight().getDecimalPart()));
			refreshScreen();
		} else {
			setContentView(R.layout.choose_activity);
		}

	}

	// fin du onCreate

	
	public void createNewUserActivity(){
		mUserActivity.set_id(NO_ID);
	};
	
	/**========================================================================
	 * 
	 ========================================================================*/
	
	public void reloadUserActivity(long _id){
	
		AmiKcalFactory factory = new AmiKcalFactory();
		factory.contentResolver = this.getContentResolver();
		this.mUserActivity =  factory.createUserActivityObjFromId(_id);
		
	}
	
	/******************************************************************************************
	 * onClickOk : - Mettre à jour les informations saisies dans la base de
	 * donnée - appeler la saisie d'un component (suivant le type moving /
	 * eating)
	 * 
	 * @param v
	 *            vue
	 ******************************************************************************************/
	public void onClickOk(View v) {

		// récupérer l'heure
		TimePicker tp = (TimePicker) findViewById(R.id.timePicker1);

		DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat
				.getInstance();
		decimalFormat.applyPattern("00");

		if (tp.getCurrentHour().intValue() >= 12) {
			mUserActivity.getDay().set(Calendar.AM_PM, Calendar.PM);
		} else {
			mUserActivity.getDay().set(Calendar.AM_PM, Calendar.AM);
		}

		mUserActivity.getDay().set(Calendar.HOUR_OF_DAY,
				tp.getCurrentHour().intValue());
		mUserActivity.getDay().set(Calendar.MINUTE,
				tp.getCurrentMinute().intValue());

		if (mUserActivity.get_id() == NO_ID) {
			insert();
		} else {
			update();
		}

		closeActivity();
	}

	/******************************************************************************************
	 * getContentValues
	 ******************************************************************************************/
	private ContentValues getContentValues() {

		ContentValues val = new ContentValues();

		val.put(ContentDescriptorObj.UserActivities.Columns.ID, (mUserActivity
				.get_id() == NO_ID) ? null : mUserActivity.get_id());
		val.put(ContentDescriptorObj.UserActivities.Columns.TITLE,
				mUserActivity.getTitle());
		val.put(ContentDescriptorObj.UserActivities.Columns.DATE,
				ToolBox.getSqlDateTime(mUserActivity.getDay()));

		val.put(ContentDescriptorObj.UserActivities.Columns.TYPE, mUserActivity
				.getType().name());
		val.put(ContentDescriptorObj.UserActivities.Columns.LAST_UPDATE,
				ToolBox.getCurrentTimestamp());

		return val;
	}

	/*******************************************************************************************
	 * Méthode : update() metre à jour l'enregistrement
	 *******************************************************************************************/
	public void update() {
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
	public void insert() {
		ContentValues val = getContentValues();
		this.getContentResolver().insert(
				ContentDescriptorObj.UserActivities.URI_INSERT_USER_ACTIVITIES,
				val);
	}

	/*******************************************************************************************
	 * Méthode : refreshScreen() alimente les composants de la vue avec les
	 * donnée de du UserActicity en cours
	 *******************************************************************************************/
	protected void refreshScreen() {

		TimePicker tp = (TimePicker) findViewById(R.id.timePicker1);
		tp.setIs24HourView(true);
		// EditText edtxt = (EditText)
		// findViewById(R.id.useractivity_editor_view_edTxt_name);
		// edtxt.setText(this.mUserActivity.getTitle());
		Calendar c = mUserActivity.getDay();
		tp.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
		tp.setCurrentMinute(c.get(Calendar.MINUTE));
	}

	/*******************************************************************************************
	 * Méthode : closeActivity() ferme l'activité
	 *******************************************************************************************/
	protected void closeActivity() {

		// on appelle setResult pour déclancher le onActivityResult de
		// l'activity mère.
		setResult(RESULT_OK, mIntent);
		// On termine l'Actvity
		finish();
	}

	/*******************************************************************************************
	 * Méthode : onClickLunch() Gestion du bouton repas
	 *******************************************************************************************/

	public void onClickLunch(View v) {

		setContentView(R.layout.view_edit_lunch);
		this.mUserActivity.setType(ActivityType.LUNCH);
		TimePicker tp = (TimePicker) findViewById(R.id.timePicker1);
		tp.setIs24HourView(true);
		tp.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));

	}

	/*******************************************************************************************
	 * Méthode : onClickPhysicalActivity() Gestion du bouton activité physique
	 *******************************************************************************************/
	public void onClickPhysicalActivity(View v) {
		setContentView(R.layout.view_edit_physical_activity);
		this.mUserActivity.setType(ActivityType.MOVE);
		this.mUserActivity.setTitle(this.mResources
				.getString(R.string.TITLE_PHYSICAL_ACTIVITY));
	}

	/*******************************************************************************************
	 * Méthode : onClickWeight() Gestion du bouton poids
	 *******************************************************************************************/
	public void onClickWeight(View v) {
		setContentView(R.layout.view_edit_weight);
		this.mUserActivity.setType(ActivityType.WEIGHT);

		// poids par defaut
		TextView tv = (TextView) findViewById(R.id.userActivity_Editor_tv_kilos);
		tv.setText("40");
		tv = (TextView) findViewById(R.id.userActivity_Editor_tv_grammes);
		tv.setText("00");
	}

	/*****************************************************************************************
	 * Gestion des radiobouton
	 * **************************************************
	 * *************************************
	 */

	public void onClickRdioBreakfast(View v) {
		setRdioLunch(1);
	}

	public void onClickRdioLunch(View v) {
		setRdioLunch(2);
	}

	public void onClickRdioDiner(View v) {
		setRdioLunch(3);
	}

	public void onClickRdioSnack(View v) {
		setRdioLunch(4);

	}

	/***********************************************************************************
	  * 
	  * 
	  ************************************************************************************/
	private void setRdioLunch(int i) {

		RadioButton rbBreakfast = (RadioButton) findViewById(R.id.view_ua_editor_rdio_breakfast);
		RadioButton rbLunch = (RadioButton) findViewById(R.id.view_ua_editor_rdio_lunch);
		RadioButton rbDiner = (RadioButton) findViewById(R.id.view_ua_editor_rdio_diner);
		RadioButton rbSnack = (RadioButton) findViewById(R.id.view_ua_editor_rdio_snack);

		rbBreakfast.setChecked(false);
		rbLunch.setChecked(false);
		rbDiner.setChecked(false);
		rbSnack.setChecked(false);

		switch (i) {

		case 1:
			rbBreakfast.setChecked(true);
			this.mUserActivity.setTitle("Petit dejeuner");
			break;

		case 2:
			rbLunch.setChecked(true);
			this.mUserActivity.setTitle("Déjeuner");
			break;

		case 3:
			rbDiner.setChecked(true);
			this.mUserActivity.setTitle("Dîner");
			break;

		case 4:
			rbSnack.setChecked(true);
			this.mUserActivity.setTitle("Pause");
			break;
		}

	}

	public void addKilo(View v) {
		TextView tv = (TextView) findViewById(R.id.userActivity_Editor_tv_kilos);
		tv.setText(String.valueOf(Integer.parseInt(tv.getText().toString()) + 1));
		setWeight();
	}

	public void removeKilo(View v) {
		TextView tv = (TextView) findViewById(R.id.userActivity_Editor_tv_kilos);
		tv.setText(String
				.valueOf((Integer.parseInt(tv.getText().toString()) - 1 <= 0) ? 0
						: Integer.parseInt(tv.getText().toString()) - 1));
		setWeight();
	}

	public void addGramme(View v) {
		TextView tv = (TextView) findViewById(R.id.userActivity_Editor_tv_grammes);
		tv.setText(String
				.valueOf((Integer.parseInt(tv.getText().toString()) + 1 > 9) ? 0
						: Integer.parseInt(tv.getText().toString()) + 1));
		setWeight();
	}

	public void removeGramme(View v) {
		TextView tv = (TextView) findViewById(R.id.userActivity_Editor_tv_grammes);
		tv.setText(String
				.valueOf((Integer.parseInt(tv.getText().toString()) - 1 < 0) ? 9
						: Integer.parseInt(tv.getText().toString()) - 1));
		setWeight();
	}

	private void setWeight() {

		TextView tv = (TextView) findViewById(R.id.userActivity_Editor_tv_kilos);
		((UserActivityWeight) mUserActivity).getWeight().setInt_part(
				Integer.parseInt(tv.getText().toString()));

		tv = (TextView) findViewById(R.id.userActivity_Editor_tv_grammes);
		((UserActivityWeight) mUserActivity).getWeight().setDecimalPart(
				Integer.parseInt(tv.getText().toString()));

		mUserActivity.setTitle(((UserActivityWeight) mUserActivity).getWeight()
				.format());
	}

}