package com.rdupuis.amikcal.Tools.NumericPad;





import com.rdupuis.amikcal.R;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.widget.TextView;

public class FragAct_NumericPad extends FragmentActivity 
		implements NumericPadFragment.OnClickButtonOK , 
		           NumericPadFragment.OnClickButtonCancel
{

	
	
	
	
	Intent  mIntent ;
	Resources mResources;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getIntent récupère l'Intent qui a déclanché l'Activity
         setContentView(R.layout.view_numericpad);
         mIntent = getIntent();
         mResources = getResources();
         
         Display mDisplay = getWindowManager().getDefaultDisplay();
     	int width = 	mDisplay.getHeight();
     	int height = 	mDisplay.getWidth();
         
         
         TextView ed = (TextView)findViewById(R.id.view_numericpad_tv_question);
         ed.setText("bonjour" + width + "x"+height);
         String mQuestion = mIntent.getStringExtra("question");
         if (mQuestion != null){
        	 ed.setText(mIntent.getStringExtra("question"));
         }
    }

    
    public void onClickButtonOK(String result){
    	
    	// on alimente le résultat dans l'Intent pour que l'Activity mère puisse
    	//récupérer la valeur. 
    	Intent intent = new Intent();
    	
    	intent.putExtra(mResources.getString(R.string.INTENT_OUT_NUMERICPAD_RESULT), result); 
    	
    	//on appelle setResult pour déclancher le onActivityResult de l'activity mère. 
    	this.setResult(RESULT_OK, intent);
    	
    	//On termine l'Actvity
    	this.finish();
    	}

 public void onClickButtonCancel(){
    	    	
    	//on appelle setResult pour déclancher le onActivityResult de l'activity mère. 
    	this.setResult(RESULT_CANCELED);
    	
    	//On termine l'Actvity
    	this.finish();
    	}
    
    
}
