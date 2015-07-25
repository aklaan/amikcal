package com.rdupuis.amikcal.commons.numericpad;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rdupuis.amikcal.R;

public class Act_NumericPad extends Activity implements NumericPadListeners {

    TextView ed;
    String result;

    public static final String OUTPUT____AMOUNT = "_AMOUNT";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	// getIntent récupère l'Intent qui a déclanché l'Activity
	setContentView(R.layout.view_numericpad);
	ed = (TextView) findViewById(R.id.padnumber_entry);
    }

    @Override
    public void NumericPadListener_OnClick_btn_Number(View view) {
	Button button = (Button) findViewById(view.getId());
	ed.setText(ed.getText() + button.getText().toString());

    }

    @Override
    public void NumericPadListener_OnClick_btn_Erase(View view) {
	ed.setText("");

    }

    @Override
    public void NumericPadListener_OnClick_btn_Back(View view) {
	ed = (TextView) findViewById(R.id.padnumber_entry);
	if (ed.getText().length() > 0) {
	    ed.setText(((String) ed.getText()).substring(0, ed.getText().length() - 1));
	}

    }

    @Override
    public void NumericPadListener_OnClick_btn_Ok(View view) {
	// si l'utilisateur a saisi un nombre
	// on alimente le résultat dans l'Intent pour que l'Activity mère puisse
	// récupérer la valeur.
	// s'il n'a rien saisi, on

	if (ed.getText() != "") {
	    this.getIntent().putExtra(this.OUTPUT____AMOUNT,Float.parseFloat(ed.getText().toString()));

	    // on appelle setResult pour déclancher le onActivityResult de
	    // l'activity mère.
	    this.setResult(RESULT_OK, this.getIntent());
	}
	// On termine l'Actvity
	this.finish();

    }

    @Override
    public void NumericPadListener_OnClick_btn_Cancel(View view) {
	// On termine l'Actvity
	this.finish();

    }
}
