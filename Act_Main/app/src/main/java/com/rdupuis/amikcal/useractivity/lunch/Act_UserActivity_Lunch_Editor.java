package com.rdupuis.amikcal.useractivity.lunch;

import java.text.DecimalFormat;
import java.util.Calendar;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TimePicker;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AmiKcalFactory;
import com.rdupuis.amikcal.commons.Manager;
import com.rdupuis.amikcal.commons.ManagerBuilder;
import com.rdupuis.amikcal.useractivity.Act_UserActivity_Editor;
import com.rdupuis.amikcal.useractivity.UserActivity_Manager;

/**
 * <b>Ecran d'édition des repas de l'utilisateur.</b>
 *
 * @author Rodolphe Dupuis
 * @version 0.1
 */
public class Act_UserActivity_Lunch_Editor extends Act_UserActivity_Editor {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        // dans la méthode onCreate mêre, on a initialisé les données d'aprés
        // l'intent.
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_edit_lunch);

        switch (this.getEditMode()) {

            case EDIT:
                // UserActivity ua = new UserActivity_Manager(this);

                //this.setEdited_UserActivity(uam.load(this.getEdited_object_id()));


                break;
            case CREATE:
                // En cas de création, la classe mère n'a pas pu recharger l'objet
                // UserActivity
                // on doit en créer un à la date du jour récupéré de l'Intent
                this.setEdited_UserActivity(new UserActivity_Lunch());
                this.getEdited_UserActivity().setDay(this.getInput_day());

                break;

        }

        refreshScreen();

    }

    // fin du onCreate

    /******************************************************************************************
     * onClickOk : - Mettre à jour les informations saisies dans la base de
     * donnée -
     *
     * @param v vue
     ******************************************************************************************/
    public void onClickOk(View v) {

        // récupérer l'heure
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

        //on récupère le manager de la userActivity
        Manager manager = ManagerBuilder.build(this, getEdited_UserActivity());
        //lorsque l'on sauve la première fois, on récupère un nouvel id.
        long new_id = manager.save(getEdited_UserActivity());
        //on attribue l'id
        getEdited_UserActivity().setDatabaseId(new_id);

        closeEditor();
    }

    /****************************************************
     * Gestion des radiobouton
     *****************************************************/

    public void onClickRdioBreakfast(View v) {
        setRdioLunch(LUNCH_CATEGORY.BREAKFAST);
    }

    public void onClickRdioLunch(View v) {
        setRdioLunch(LUNCH_CATEGORY.LUNCH);
    }

    public void onClickRdioDiner(View v) {
        setRdioLunch(LUNCH_CATEGORY.DINER);
    }

    public void onClickRdioSnack(View v) {
        setRdioLunch(LUNCH_CATEGORY.SNACK);

    }

    /***********************************************************************************
     *
     *
     ************************************************************************************/
    private void setRdioLunch(LUNCH_CATEGORY i) {

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
        Calendar c = getEdited_UserActivity().getDay();
        tp.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
        tp.setCurrentMinute(c.get(Calendar.MINUTE));

    }

    private void refreshLunchType() {
        // par defaut, le radioButton sélectionné est Breakfast
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
                this.getEdited_UserActivity().setTitle(LUNCH_CATEGORY.BREAKFAST.name());
        }

        radioButton.setChecked(true);
    }


}