package com.rdupuis.amikcal.Tools.NumericPad;





import com.rdupuis.amikcal.R;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Act_NumericPad extends Activity{

	TextView ed;
	String result;
	Intent intent;
	Resources mResources;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getIntent r�cup�re l'Intent qui a d�clanch� l'Activity
        intent = getIntent();
        mResources = getResources();
        setContentView(R.layout.padnumber);
        ed = (TextView)findViewById(R.id.padnumber_entry);
    }

public void OnClickAdd(View v){
	Button button = (Button)findViewById(v.getId());
	ed.setText(ed.getText() + button.getText().toString());
}

public void OnClickErase(View v){
		ed.setText("");
}

public void OnClickBack(View v){
	ed = (TextView)findViewById(R.id.padnumber_entry);
	if (ed.getText().length()>0){
		ed.setText(((String) ed.getText()).substring(0, ed.getText().length()-1));
	}
}

public void OnClickOk(View v){
	
	// on alimente le r�sultat dans l'Intent pour que l'Activity m�re puisse
	//r�cup�rer la valeur. 
	this.intent.putExtra(mResources.getString(R.string.INTENT_OUT_NUMERICPAD_RESULT), ed.getText().toString()); 
	
	//on appelle setResult pour d�clancher le onActivityResult de l'activity m�re. 
	this.setResult(RESULT_OK, intent);
	
	//On termine l'Actvity
	this.finish();
	}


}
