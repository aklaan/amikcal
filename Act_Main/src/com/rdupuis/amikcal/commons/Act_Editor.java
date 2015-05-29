package com.rdupuis.amikcal.commons;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class Act_Editor extends Activity {

    public enum EditMode {
	CREATE, EDIT
    }

    long edited_object_id = AppConsts.NO_ID;
    EditMode editMode;
    AmiKcalFactory factory;
    public static final String INPUT____ID = "_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	factory = new AmiKcalFactory(this);

	// dans l'Intent, on r�cup�re l'id de l'objet � �diter
	edited_object_id = getIntent().getLongExtra(this.INPUT____ID, AppConsts.NO_ID);


    }

    public EditMode getEditMode() {

	// si l'id de objet a �diter est connu, nous somme en mode Edition
	if (edited_object_id != AppConsts.NO_ID) {
	    this.editMode = EditMode.EDIT;
	    Toast.makeText(this, "Mode Edition", Toast.LENGTH_LONG).show();
	} else {
	    // si l'id est NO_ID, c'est que l'on cr�e un nouvel objet
	    this.editMode = EditMode.CREATE;
	    Toast.makeText(this, "Mode Creation", Toast.LENGTH_LONG).show();

	}
	;
	return editMode;

    }

}
