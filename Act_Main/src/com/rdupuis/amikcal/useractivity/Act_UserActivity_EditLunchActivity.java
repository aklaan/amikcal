package com.rdupuis.amikcal.useractivity;

import java.text.DecimalFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.rdupuis.amikcal.Act_Main;
import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AmiKcalFactory;
import com.rdupuis.amikcal.commons.AmikcalVar;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.useractivity.UserActivityLunch.LunchType;

/**
 * <b>Ecran d'édition des repas de l'utilisateur.</b>
 * 
 * @author Rodolphe Dupuis
 * @version 0.1
 */
public class Act_UserActivity_EditLunchActivity extends
		Act_UserActivity_EditorCommons {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_edit_lunch);
		refreshScreen();

	}

	// fin du onCreate

	/******************************************************************************************
	 * onClickOk : - Mettre à jour les informations saisies dans la base de
	 * donnée -
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

		// Si l'activité n'a pas d'ID c'est qu'il s'agit d'une nouvelle
		// Activité.
		// dans ce cas on l'insère dans la database
		// dans le cas contraire, on la met à jour.
		if (mUserActivity.get_id() == AmikcalVar.NO_ID) {
			insertUActivity();
		} else {
			updateUActivity();
		}

		closeEditor();
	}

	/****************************************************
	 * Gestion des radiobouton
	 * 
	 *****************************************************/

	public void onClickRdioBreakfast(View v) {
		setRdioLunch(LunchType.BREAKFAST);
	}

	public void onClickRdioLunch(View v) {
		setRdioLunch(LunchType.LUNCH);
	}

	public void onClickRdioDiner(View v) {
		setRdioLunch(LunchType.DINER);
	}

	public void onClickRdioSnack(View v) {
		setRdioLunch(LunchType.SNACK);

	}

	/***********************************************************************************
	 * 
	 * 
	 ************************************************************************************/
	private void setRdioLunch(LunchType i) {

		RadioButton rbBreakfast = (RadioButton) findViewById(R.id.view_ua_editor_rdio_breakfast);
		RadioButton rbLunch = (RadioButton) findViewById(R.id.view_ua_editor_rdio_lunch);
		RadioButton rbDiner = (RadioButton) findViewById(R.id.view_ua_editor_rdio_diner);
		RadioButton rbSnack = (RadioButton) findViewById(R.id.view_ua_editor_rdio_snack);

		rbBreakfast.setChecked(false);
		rbLunch.setChecked(false);
		rbDiner.setChecked(false);
		rbSnack.setChecked(false);

		switch (i) {

		case BREAKFAST:
			rbBreakfast.setChecked(true);
			this.mUserActivity.setTitle(i.name());
			break;

		case LUNCH:
			rbLunch.setChecked(true);
			this.mUserActivity.setTitle(i.name());
			break;

		case DINER:
			rbDiner.setChecked(true);
			this.mUserActivity.setTitle(i.name());
			break;

		case SNACK:
			rbSnack.setChecked(true);
			this.mUserActivity.setTitle(i.name());
			break;
		}

	}

	/*******************************************************************************************
	 * Méthode : refreshScreen() alimente les composants de la vue avec les
	 * donnée de du UserActicity en cours
	 *******************************************************************************************/
	private void refreshScreen() {
		this.refreshHour();
		this.refreshLunchType();

	}

	private void refreshHour() {
		TimePicker tp = (TimePicker) findViewById(R.id.timePicker1);
		tp.setIs24HourView(true);
		// EditText edtxt = (EditText)
		// findViewById(R.id.useractivity_editor_view_edTxt_name);
		// edtxt.setText(this.mUserActivity.getTitle());
		Calendar c = mUserActivity.getDay();
		tp.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
		tp.setCurrentMinute(c.get(Calendar.MINUTE));

	}

	private void refreshLunchType() {
		// par defaut, le radioButton sélectionné est Breakfast
		RadioButton radioButton = (RadioButton) findViewById(R.id.view_ua_editor_rdio_breakfast);
		UserActivityLunch.LunchType type;
		
		//Au cas où le type de repas n'est pas reconnu on valorise le 
		//type à UNKNOWN
		try{
		 type = UserActivityLunch.LunchType
				.valueOf(this.mUserActivity.getTitle());
		} catch (Exception e){
		type = UserActivityLunch.LunchType.UNKNOWN;
		}
		
		switch (type) {

		case BREAKFAST:
			radioButton = (RadioButton) findViewById(R.id.view_ua_editor_rdio_breakfast);
			break;
		case LUNCH:
			radioButton = (RadioButton) findViewById(R.id.view_ua_editor_rdio_lunch);
			break;
		case DINER:
			radioButton = (RadioButton) findViewById(R.id.view_ua_editor_rdio_diner);
			break;
		case SNACK : 
				radioButton = (RadioButton) findViewById(R.id.view_ua_editor_rdio_snack);
				break;
		default :
			//si le titre ne correspond pas un type connu ou est vide
			//on initialise le titre a BREAKFAST par defaut
			radioButton = (RadioButton) findViewById(R.id.view_ua_editor_rdio_breakfast);
			this.mUserActivity.setTitle(UserActivityLunch.LunchType.BREAKFAST.name());
		}

		radioButton.setChecked(true);
	}

	@Override
	public ContentValues addSpecificValues(ContentValues val) {
		// TODO Auto-generated method stub
		return null;
	}

}