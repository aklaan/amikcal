package com.rdupuis.amikcal.useractivity.move;

import java.text.DecimalFormat;
import java.util.Calendar;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.useractivity.Act_UserActivity_Editor;
import com.rdupuis.amikcal.useractivity.lunch.UserActivity_Lunch_Manager;

/**
 * <b>Ecran d'�dition des activit�es physiques de l'utilisateur.</b>
 * 
 * @author Rodolphe Dupuis
 */
public class Act_UserActivity_Move_Editor extends
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
			this.setEdited_UserActivity(new UserActivity_Move());
			this.getEdited_UserActivity().setDay(this.getInput_day());

			break;

		}
		/***************************************************************************************
		 * Step II : Chargement de l'�cran
		 ***************************************************************************************/
		setContentView(R.layout.view_edit_physical_activity);

		/***************************************************************************************
		 * Step III : Initialisation des donn�es � l'�cran
		 ***************************************************************************************/
		refreshScreen();

	}

	// fin du onCreate

	public void refreshScreen() {
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

		// récupérer l'heure
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

		//UserActivity_Move_Manager uam = new UserActivity_Move_Manager(this);
		//uam.save(getEdited_UserActivity());

		closeEditor();
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

	
}