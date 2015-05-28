package com.rdupuis.amikcal.useractivity;

import java.text.DecimalFormat;
import java.util.Calendar;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.commons.WeightObj;

/**
 * <b>Ecran d'édition des pesées.</b>
 * 
 * @author Rodolphe Dupuis
 * @version 0.1
 */
public class Act_UserActivity_EditWeightActivity extends
		Act_UserActivity_Editor {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**************************************************************************************
		 * Step I : Récupérer les informations de l'Intent --> RAS , ce qui est
		 * fait dans Act_UserActivity_EditorCommons suffit --> Vérifier si on
		 * est en mode édition ou création
		 ****************************************************************************************/
		switch (this.editMode) {

		case EDIT:
			// En cas d'édition, la classe mère a du recharger l'objet
			// UserActivityWeight
			break;
		case CREATE:
			// En cas de création, la classe mère n'a pas pu recharger l'objet
			// UserActivityWeight
			// on doit en créer un à la date du jour récupéré de l'Intent
			this.mUserActivity = new UserActivity_Weight();
			this.mUserActivity.setDay(ToolBox.parseCalendar(getIntent()
					.getStringExtra(AppConsts.INPUT____UA_EDITOR____DAY)));

			break;

		}

		/***************************************************************************************
		 * Step II : Chargement de l'écran
		 ***************************************************************************************/
		setContentView(R.layout.view_edit_weight);

		/***************************************************************************************
		 * Step III : Initialisation des données à l'écran
		 ***************************************************************************************/
		refreshScreen();

	}

	// fin du onCreate

	private void refreshScreen() {
		this.refreshWeight();
		this.refreshHour();

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

		if (mUserActivity.getId() == AppConsts.NO_ID) {
			insertUActivity();
		} else {
			updateUActivity();
		}

		closeEditor();
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
		((UserActivity_Weight) mUserActivity).getWeight().setInt_part(
				Integer.parseInt(tv.getText().toString()));

		tv = (TextView) findViewById(R.id.userActivity_Editor_tv_grammes);
		((UserActivity_Weight) mUserActivity).getWeight().setDecimalPart(
				Integer.parseInt(tv.getText().toString()));

		mUserActivity.setTitle(((UserActivity_Weight) mUserActivity).getWeight()
				.format());
	}

	@Override
	public ContentValues setSpecificValues(ContentValues val) {
		// TODO Auto-generated method stub
		return val;
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

	private void refreshWeight() {

		// Par defaut on affiche 50 Kg
		String kilos = "50";
		String grammes = "00";

		// Si l'id de l'activité n'est pas NO_ID alors on est en train
		// d'éditer une pesée. Dans ce cas, on affiche ce que l'utilisateur
		// avait enregistré
		if (this.mUserActivity.getId() != AppConsts.NO_ID) {

			WeightObj w = ((UserActivity_Weight) this.mUserActivity).getWeight();
			kilos = String.valueOf(w.getInt_part());
			grammes = String.valueOf(w.getDecimalPart());
		}

		TextView tv = (TextView) findViewById(R.id.userActivity_Editor_tv_kilos);
		tv.setText(kilos);

		tv = (TextView) findViewById(R.id.userActivity_Editor_tv_grammes);
		tv.setText(grammes);

	}
}