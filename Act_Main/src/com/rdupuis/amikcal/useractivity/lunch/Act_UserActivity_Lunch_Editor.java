package com.rdupuis.amikcal.useractivity.lunch;

import java.text.DecimalFormat;
import java.util.Calendar;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TimePicker;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AmiKcalFactory;
import com.rdupuis.amikcal.useractivity.Act_UserActivity_Editor;

/**
 * <b>Ecran d'�dition des repas de l'utilisateur.</b>
 * 
 * @author Rodolphe Dupuis
 * @version 0.1
 */
public class Act_UserActivity_Lunch_Editor extends Act_UserActivity_Editor {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

	// dans la m�thode onCreate m�re, on a initialis� les donn�es d'apr�s
	// l'intent.
	super.onCreate(savedInstanceState);

	setContentView(R.layout.view_edit_lunch);

	switch (this.getEditMode()) {

	case EDIT:
	    // En cas d'�dition, la classe m�re a du recharger l'objet
	    // UserActivityLunch
	    break;
	case CREATE:
	    // En cas de cr�ation, la classe m�re n'a pas pu recharger l'objet
	    // UserActivity
	    // on doit en cr�er un � la date du jour r�cup�r� de l'Intent
	    this.setEdited_UserActivity(new UserActivity_Lunch());
	    this.getEdited_UserActivity().setDay(this.getInput_day());

	    break;

	}

	refreshScreen();

    }

    // fin du onCreate

    /******************************************************************************************
     * onClickOk : - Mettre � jour les informations saisies dans la base de
     * donn�e -
     * 
     * @param v
     *            vue
     ******************************************************************************************/
    public void onClickOk(View v) {

	// r�cup�rer l'heure
	TimePicker tp = (TimePicker) findViewById(R.id.timePicker1);

	DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance();
	decimalFormat.applyPattern("00");

	if (tp.getCurrentHour().intValue() >= 12) {
	    getEdited_UserActivity().getDay().set(Calendar.AM_PM, Calendar.PM);
	} else {
	    getEdited_UserActivity().getDay().set(Calendar.AM_PM, Calendar.AM);
	}

	getEdited_UserActivity().getDay().set(Calendar.HOUR_OF_DAY, tp.getCurrentHour().intValue());

	getEdited_UserActivity().getDay().set(Calendar.MINUTE, tp.getCurrentMinute().intValue());

	AmiKcalFactory factory = new AmiKcalFactory(this);
	factory.save(getEdited_UserActivity());
	closeEditor();
    }

    /****************************************************
     * Gestion des radiobouton
     * 
     *****************************************************/

    public void onClickRdioBreakfast(View v) {
	setRdioLunch(LUNCH_TYPE.BREAKFAST);
    }

    public void onClickRdioLunch(View v) {
	setRdioLunch(LUNCH_TYPE.LUNCH);
    }

    public void onClickRdioDiner(View v) {
	setRdioLunch(LUNCH_TYPE.DINER);
    }

    public void onClickRdioSnack(View v) {
	setRdioLunch(LUNCH_TYPE.SNACK);

    }

    /***********************************************************************************
	 * 
	 * 
	 ************************************************************************************/
    private void setRdioLunch(LUNCH_TYPE i) {

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
	    this.getEdited_UserActivity().setTitle(i.name());
	    break;

	case LUNCH:
	    rbLunch.setChecked(true);
	    this.getEdited_UserActivity().setTitle(i.name());
	    break;

	case DINER:
	    rbDiner.setChecked(true);
	    this.getEdited_UserActivity().setTitle(i.name());
	    break;

	case SNACK:
	    rbSnack.setChecked(true);
	    this.getEdited_UserActivity().setTitle(i.name());
	    break;
	default:
	    break;
	}

    }

    /*******************************************************************************************
     * M�thode : refreshScreen() alimente les composants de la vue avec les
     * donn�e de du UserActicity en cours
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
	Calendar c = getEdited_UserActivity().getDay();
	tp.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
	tp.setCurrentMinute(c.get(Calendar.MINUTE));

    }

    private void refreshLunchType() {
	// par defaut, le radioButton s�lectionn� est Breakfast
	RadioButton radioButton = (RadioButton) findViewById(R.id.view_ua_editor_rdio_breakfast);
	
	switch (((UserActivity_Lunch) this.getEdited_UserActivity()).getTypeOfLunch()) {

	case BREAKFAST:
	    radioButton = (RadioButton) findViewById(R.id.view_ua_editor_rdio_breakfast);
	    break;
	case LUNCH:
	    radioButton = (RadioButton) findViewById(R.id.view_ua_editor_rdio_lunch);
	    break;
	case DINER:
	    radioButton = (RadioButton) findViewById(R.id.view_ua_editor_rdio_diner);
	    break;
	case SNACK:
	    radioButton = (RadioButton) findViewById(R.id.view_ua_editor_rdio_snack);
	    break;
	default:
	    // si le titre ne correspond pas un type connu ou est vide
	    // on initialise le titre a BREAKFAST par defaut
	    radioButton = (RadioButton) findViewById(R.id.view_ua_editor_rdio_breakfast);
	    this.getEdited_UserActivity().setTitle(LUNCH_TYPE.BREAKFAST.name());
	}

	radioButton.setChecked(true);
    }

    

}