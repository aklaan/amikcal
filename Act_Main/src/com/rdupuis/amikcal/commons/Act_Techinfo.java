package com.rdupuis.amikcal.commons;

import android.app.Activity;

import android.os.Bundle;
import android.widget.TextView;
import com.rdupuis.amikcal.R;

public class Act_Techinfo  extends Activity {

	/** Called when the activity is first created. */
		 
		@Override
	       public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.view_technical_informations);
	     	        
	        TextView tv= (TextView)findViewById(R.id.techinfo_tv_dbVersion);
	        tv.setText(String.valueOf(ToolBox.getAmikcalDbVersion()));	
		
		}

}
