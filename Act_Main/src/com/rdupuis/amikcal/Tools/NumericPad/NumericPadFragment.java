package com.rdupuis.amikcal.Tools.NumericPad;

import com.rdupuis.amikcal.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class NumericPadFragment extends Fragment{

	//les Activitées voulant utiliser ce fragment, devront implémenter
	// l'interface OnClickButtonOK pour pouvoir récupérer le résultat.
	public interface OnClickButtonOK{
		public void onClickButtonOK(String result);
	}
	
	public interface OnClickButtonCancel{
		public void onClickButtonCancel();
	}
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	 }
		
	@Override 
	public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

	}
	
	/******************************************************************************************
	 * method : OnAttach
	 * Created : 07-06-2012
	 * 
	 * On vérifie que l'activité qui va utiliser le fragment "pavé numérique", implémente bien 
	 * l'interface OnClickButtonOK, sinon l'activité ne pourra pas récupérer le résultat saisi par  
	 * l'utilisateur.
	 *******************************************************************************************/
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
		
			@SuppressWarnings("unused")
			OnClickButtonOK testImplement = (OnClickButtonOK) activity;
		} catch (ClassCastException e) {
		throw new ClassCastException(activity.toString()
		+ " must implement OnClickButtonOK");
		}
	}
		
	
    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState) {
     View mainView = inflater.inflate(R.layout.padnumber, container, false);
     
     OnClickListener onClickNumbrer = 
    		 new OnClickListener() {
         			public void onClick(View v) {
         					Button button = (Button)getView().findViewById(v.getId());
         					TextView ed = (TextView)getView().findViewById(R.id.padnumber_entry);
         					ed.setText(ed.getText() + button.getText().toString());
         }
     }; 
    		 
     Button myButton = (Button) mainView.findViewById(R.id.btnOne);
     myButton.setOnClickListener(onClickNumbrer);
    		 
     myButton = (Button) mainView.findViewById(R.id.btnTwo);
     myButton.setOnClickListener(onClickNumbrer);
     
     myButton = (Button) mainView.findViewById(R.id.btnThree);
     myButton.setOnClickListener(onClickNumbrer);
     
     myButton = (Button) mainView.findViewById(R.id.btnFour);
     myButton.setOnClickListener(onClickNumbrer);
     
     myButton = (Button) mainView.findViewById(R.id.btnFive);
     myButton.setOnClickListener(onClickNumbrer);
     
     myButton = (Button) mainView.findViewById(R.id.btnSix);
     myButton.setOnClickListener(onClickNumbrer);
     
     myButton = (Button) mainView.findViewById(R.id.btnSeven);
     myButton.setOnClickListener(onClickNumbrer);
     
     myButton = (Button) mainView.findViewById(R.id.btnHeight);
     myButton.setOnClickListener(onClickNumbrer);
     
     myButton = (Button) mainView.findViewById(R.id.btnNine);
     myButton.setOnClickListener(onClickNumbrer);
     
     myButton = (Button) mainView.findViewById(R.id.btnZero);
     myButton.setOnClickListener(onClickNumbrer);
    
     myButton = (Button) mainView.findViewById(R.id.btnDot);
     myButton.setOnClickListener(onClickNumbrer);
     
     
     OnClickListener onClickOK = 
    		 new OnClickListener() {
         			public void onClick(View v) {
         					
         				    TextView ed = (TextView)getView().findViewById(R.id.padnumber_entry);
         				   
         				    String result = ed.getText().toString();
         					
         				    if (result == "") {
         						result="0";
         					}
         					
         				    Fragment f = (NumericPadFragment) getFragmentManager().findFragmentById(R.id.NumericPadFragment);
         				    
         				    if (f != null && f.isInLayout()){
         				    ((OnClickButtonOK) f.getActivity()).onClickButtonOK(result);
         				    }
         }
     }; 
     
     myButton = (Button) mainView.findViewById(R.id.btnOK);
     myButton.setOnClickListener(onClickOK);
    
    
     OnClickListener onClickClear = 
    		 new OnClickListener() {
         			public void onClick(View v) {
         					
         					TextView ed = (TextView)getView().findViewById(R.id.padnumber_entry);
         					ed.setText("");
         }
     }; 
     
     myButton = (Button) mainView.findViewById(R.id.btnClear);
     myButton.setOnClickListener(onClickClear);
     
     
     
     OnClickListener onClickCancel = 
    		 new OnClickListener() {
         			public void onClick(View v) {
         					         					
         				    Fragment f = (NumericPadFragment) getFragmentManager().findFragmentById(R.id.NumericPadFragment);
         				    
         				    if (f != null && f.isInLayout()){
         				    ((OnClickButtonCancel) f.getActivity()).onClickButtonCancel();
         				    }
         }
     }; 
     
     myButton = (Button) mainView.findViewById(R.id.btnCancel);
     myButton.setOnClickListener(onClickCancel);
     
     
     return mainView;   
     
    }

    
    

    

}
