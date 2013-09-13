package com.rdupuis.amikcal.commons.numericpad;

import com.rdupuis.amikcal.R;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Frag_NumericPad extends Fragment {

	TextView ed;
	String result;
	Intent intent;
	Resources mResources;
	View mMainView;

	/** Called when the fragment is created. */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mMainView = inflater.inflate(R.layout.view_numericpad, container, false);
		// getIntent récupère l'Intent qui a déclanché l'Activity
		intent = getActivity().getIntent();
		mResources = getResources();
		
		ed = (TextView) mMainView.findViewById(R.id.padnumber_entry);

		return mMainView;
	}

	/**
	 * 
	 * @param v
	 */
	public void OnClickAdd(View v) {
		Button button = (Button) mMainView.findViewById(v.getId());
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
		ed = (TextView) mMainView.findViewById(R.id.padnumber_entry);
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
			getActivity().setResult(Activity.RESULT_OK, intent);
		}
		// On termine l'Actvity
		getActivity().finish();
	}

	/**
	 * 
	 * @param v
	 */
	public void OnClickCancel(View v) {

		// On termine l'Actvity
		getActivity().finish();
	}
}
