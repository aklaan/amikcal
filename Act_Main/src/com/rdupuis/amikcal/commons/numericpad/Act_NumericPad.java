package com.rdupuis.amikcal.commons.numericpad;

import com.rdupuis.amikcal.R;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Act_NumericPad extends Activity {

	TextView ed;
	String result;
	Intent intent;
	Resources mResources;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getIntent récupère l'Intent qui a déclanché l'Activity
		intent = getIntent();
		mResources = getResources();
		setContentView(R.layout.view_numericpad);
		ed = (TextView) findViewById(R.id.padnumber_entry);
	}

	/**
	 * 
	 * @param v
	 */
	public void OnClickAdd(View v) {
		Button button = (Button) findViewById(v.getId());
		ed.setText(ed.getText() + button.getText().toString());
	}

	/**
	 * 
	 * @param v
	 */
	public void OnClickErase(View v) {
		ed.setText("");
	}

	/**
	 * 
	 * @param v
	 */
	public void OnClickBack(View v) {
		ed = (TextView) findViewById(R.id.padnumber_entry);
		if (ed.getText().length() > 0) {
			ed.setText(((String) ed.getText()).substring(0, ed.getText()
					.length() - 1));
		}
	}

	/**
	 * 
	 * @param v
	 */
	public void OnClickOk(View v) {

		// si l'utilisateur a saisi un nombre
		// on alimente le résultat dans l'Intent pour que l'Activity mère puisse
		// récupérer la valeur.
		// s'il n'a rien saisi, on 

		if (ed.getText() != "") {
			this.intent
					.putExtra(mResources
							.getString(R.string.INTENT_OUT_NUMERICPAD_RESULT),
							ed.getText().toString());

			// on appelle setResult pour déclancher le onActivityResult de
			// l'activity mère.
			this.setResult(RESULT_OK, intent);
		}
		// On termine l'Actvity
		this.finish();
	}

	/**
	 * 
	 * @param v
	 */
	public void OnClickCancel(View v) {

		// On termine l'Actvity
		this.finish();
	}
}
