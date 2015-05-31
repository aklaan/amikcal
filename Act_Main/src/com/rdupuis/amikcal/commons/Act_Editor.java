package com.rdupuis.amikcal.commons;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class Act_Editor extends Activity {

    public enum EditMode {
	CREATE, EDIT
    }

    private long edited_object_id = AppConsts.NO_ID;
    EditMode editMode;
    private AmiKcalFactory factory;
    public static final String INPUT____ID = "_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setFactory(new AmiKcalFactory(this));

	// dans l'Intent, on récupère l'id de l'objet à éditer
	edited_object_id = getIntent().getLongExtra(this.INPUT____ID, AppConsts.NO_ID);


    }

    public EditMode getEditMode() {

	// si l'id de objet a éditer est connu, nous somme en mode Edition
	if (edited_object_id != AppConsts.NO_ID) {
	    this.editMode = EditMode.EDIT;
	    Toast.makeText(this, "Mode Edition", Toast.LENGTH_LONG).show();
	} else {
	    // si l'id est NO_ID, c'est que l'on crée un nouvel objet
	    this.editMode = EditMode.CREATE;
	    Toast.makeText(this, "Mode Creation", Toast.LENGTH_LONG).show();

	}
	;
	return editMode;

    }

	public long getEdited_object_id() {
		return edited_object_id;
	}

	public void setEdited_object_id(long edited_object_id) {
		this.edited_object_id = edited_object_id;
	}

	public AmiKcalFactory getFactory() {
		return factory;
	}

	public void setFactory(AmiKcalFactory factory) {
		this.factory = factory;
	}

}
