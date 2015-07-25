package com.rdupuis.amikcal.commons.numericpad;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rdupuis.amikcal.R;

public class Frag_NumericPad extends Fragment {

	TextView ed;
	String result;
	public final static String OUTPUT____AMOUNT = "_amount";
	
	View mMainView;

	/** Called when the fragment is created. */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mMainView = inflater.inflate(R.layout.view_numericpad, container, false);
		// getIntent récupère l'Intent qui a déclanché l'Activity
		
		
		ed = (TextView) mMainView.findViewById(R.id.padnumber_entry);

		return mMainView;
	}

	

	
	  @Override
	  public void onAttach(Activity activity) {
	    super.onAttach(activity);
	    if (!(activity instanceof NumericPadListeners)) {
	      	      throw new ClassCastException(activity.toString()
	          + " must implemenet Frag_NumericPad.NumericPadListeners");
	    }
	  }

	

	
	
	/**
	 * 
	 * @param v
	 */
	public void OnClick_Btn_Number(int buttonId) {
		
		Button button = (Button) mMainView.findViewById(buttonId);
		ed.setText(ed.getText() + button.getText().toString());
	}

	/**
	 * 
	 * @param v
	 */
	public void OnClick_Btn_Erase() {
		ed.setText("");
	}

	/**
	 * 
	 * @param v
	 */
	public void OnClick_Btn_Back() {
		
		if (ed.getText().length() > 0) {
			ed.setText(((String) ed.getText()).substring(0, ed.getText()
					.length() - 1));
		}
	}

	/**
	 * 
	 * @param v
	 */
	public void OnClick_Btn_Ok() {

		// si l'utilisateur a saisi un nombre
		// on alimente le résultat dans l'Intent pour que l'Activity mère puisse
		// récupérer la valeur.
		// s'il n'a rien saisi, on

		if (ed.getText() != "") {
			this.getActivity().getIntent()
					.putExtra(this.OUTPUT____AMOUNT,
							ed.getText().toString());

			// on appelle setResult pour déclancher le onActivityResult de
			// l'activity mère.
			getActivity().setResult(Activity.RESULT_OK, this.getActivity().getIntent());
		}
		// On termine l'Actvity
		getActivity().finish();
	}

	/**
	 * 
	 * @param v
	 */
	public void OnClick_Btn_Cancel() {

		// On termine l'Actvity
		getActivity().finish();
	}
}
