package com.rdupuis.amikcal.useractivity.weight;

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
import com.rdupuis.amikcal.useractivity.Act_UserActivity_Editor;

/**
 * <b>Ecran d'�dition des pes�es.</b>
 * 
 * @author Rodolphe Dupuis
 * @version 0.1
 */
public class Act_UserActivity_Weight_Editor extends
		Act_UserActivity_Editor {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**************************************************************************************
		 * Step I : R�cup�rer les informations de l'Intent --> RAS , ce qui est
		 * fait dans Act_UserActivity_EditorCommons suffit --> V�rifier si on
		 * est en mode �dition ou cr�ation
		 ****************************************************************************************/
		switch (this.getEditMode()) {

		case EDIT:
			// En cas d'�dition, la classe m�re a du recharger l'objet
			// UserActivityWeight
			break;
		case CREATE:
			// En cas de cr�ation, la classe m�re n'a pas pu recharger l'objet
			// UserActivityWeight
			// on doit en cr�er un � la date du jour r�cup�r� de l'Intent
			this.setEdited_UserActivity(new UserActivity_Weight());
			this.getEdited_UserActivity().setDay(this.getInput_day());

			break;

		}

		/***************************************************************************************
		 * Step II : Chargement de l'�cran
		 ***************************************************************************************/
		setContentView(R.layout.view_edit_weight);

		/***************************************************************************************
		 * Step III : Initialisation des donn�es � l'�cran
		 ***************************************************************************************/
		refreshScreen();

	}

	// fin du onCreate

	private void refreshScreen() {
		this.refreshWeight();
		this.refreshHour();

	}

	/******************************************************************************************
	 * onClickOk : - Mettre � jour les informations saisies dans la base de
	 * donn�e - appeler la saisie d'un component (suivant le type moving /
	 * eating)
	 * 
	 * @param v
	 *            vue
	 ******************************************************************************************/
	public void onClickOk(View v) {

		// r�cup�rer l'heure
		TimePicker tp = (TimePicker) findViewById(R.id.timePicker1);

		DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat
				.getInstance();
		decimalFormat.applyPattern("00");

		if (tp.getCurrentHour().intValue() >= 12) {
		    getEdited_UserActivity().getDay().set(Calendar.AM_PM, Calendar.PM);
		} else {
		    getEdited_UserActivity().getDay().set(Calendar.AM_PM, Calendar.AM);
		}

		getEdited_UserActivity().getDay().set(Calendar.HOUR_OF_DAY,
				tp.getCurrentHour().intValue());
		getEdited_UserActivity().getDay().set(Calendar.MINUTE,
				tp.getCurrentMinute().intValue());

		if (getEdited_UserActivity().getDatabaseId() == AppConsts.NO_ID) {
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
		((UserActivity_Weight) getEdited_UserActivity()).getWeight().setInt_part(
				Integer.parseInt(tv.getText().toString()));

		tv = (TextView) findViewById(R.id.userActivity_Editor_tv_grammes);
		((UserActivity_Weight) getEdited_UserActivity()).getWeight().setDecimalPart(
				Integer.parseInt(tv.getText().toString()));

		getEdited_UserActivity().setTitle(((UserActivity_Weight) getEdited_UserActivity()).getWeight()
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
		Calendar c = getEdited_UserActivity().getDay();
		tp.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
		tp.setCurrentMinute(c.get(Calendar.MINUTE));

	}

	private void refreshWeight() {

		// Par defaut on affiche 50 Kg
		String kilos = "50";
		String grammes = "00";

		// Si l'id de l'activit� n'est pas NO_ID alors on est en train
		// d'�diter une pes�e. Dans ce cas, on affiche ce que l'utilisateur
		// avait enregistr�
		if (this.getEdited_UserActivity().getDatabaseId() != AppConsts.NO_ID) {

			WeightObj w = ((UserActivity_Weight) this.getEdited_UserActivity()).getWeight();
			kilos = String.valueOf(w.getInt_part());
			grammes = String.valueOf(w.getDecimalPart());
		}

		TextView tv = (TextView) findViewById(R.id.userActivity_Editor_tv_kilos);
		tv.setText(kilos);

		tv = (TextView) findViewById(R.id.userActivity_Editor_tv_grammes);
		tv.setText(grammes);

	}
}