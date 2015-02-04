package com.rdupuis.amikcal.useractivity;

import java.text.DecimalFormat;
import java.util.Calendar;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ToolBox;

/**
 * <b>Ecran d'�dition des activit�es physiques de l'utilisateur.</b>
 * 
 * @author Rodolphe Dupuis
 */
public class Act_UserActivity_EditMoveActivity extends
		Act_UserActivity_EditorCommons {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**************************************************************************************
		 * Step I : R�cup�rer les informations de l'Intent --> RAS , ce qui est
		 * fait dans Act_UserActivity_EditorCommons suffit --> V�rifier si on
		 * est en mode �dition ou cr�ation
		 ****************************************************************************************/
		switch (this.editMode) {

		case EDIT:
			// En cas d'�dition, la classe m�re a du recharger l'objet
			// UserActivityWeight
			break;
		case CREATE:
			// En cas de cr�ation, la classe m�re n'a pas pu recharger l'objet
			// UserActivityWeight
			// on doit en cr�er un � la date du jour r�cup�r� de l'Intent
			this.mUserActivity = new UserActivityMove();
			this.mUserActivity.setDay(ToolBox.parseCalendar(getIntent()
					.getStringExtra(AppConsts.INPUT____UA_EDITOR____DAY)));

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

		// r�cup�rer l'heure
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

		if (mUserActivity.get_id() == AppConsts.NO_ID) {
			insertUActivity();
		} else {
			updateUActivity();
		}

		closeEditor();
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

	
}