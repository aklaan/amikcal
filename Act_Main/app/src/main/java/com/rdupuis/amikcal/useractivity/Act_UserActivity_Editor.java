package com.rdupuis.amikcal.useractivity;

import java.util.Calendar;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;

import com.rdupuis.amikcal.commons.Act_Editor;
import com.rdupuis.amikcal.commons.AmiKcalFactory;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.AppConsts.UA_CLASS_CD_MAP;
import com.rdupuis.amikcal.commons.Manager;
import com.rdupuis.amikcal.commons.ManagerBuilder;
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
 * <p/>
 * </ul>
 * </p>
 *
 * @author Rodolphe Dupuis
 * @version 0.1
 */
public class Act_UserActivity_Editor extends Act_Editor {

    //zone possible dans l'intent:
    //par rapport à la classe de base EDITOR, pour les UA on a besoin de gérer une date.
    public static final String INPUT____DAY = "_day";
    private Calendar input_day;
    private UserActivity edited_UserActivity;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.input_day = ToolBox.parseCalendar(getIntent().getStringExtra(this.INPUT____DAY));
        //ici on ne sait pas quel type de UserActivity on édite

        /**
         on aurrait pas ce problème en passant les objet dans l'intent
         */


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

    public Calendar getInput_day() {
        return input_day;
    }

    public void setInput_day(Calendar input_day) {
        this.input_day = input_day;
    }

    public UserActivity getEdited_UserActivity() {
        return edited_UserActivity;
    }

    public void setEdited_UserActivity(UserActivity edited_UserActivity) {
        this.edited_UserActivity = edited_UserActivity;
    }

}