package com.rdupuis.amikcal.unity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AmiKcalFactory;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ManagerBuilder;
import com.rdupuis.amikcal.unity.Unity.UNIT_CLASS;
import com.rdupuis.amikcal.useractivity.UserActivity_Manager;

public class Act_UnitOfMeasureEditor extends Activity {

    Unity mUnit;
    public static final String INPUT____UNITY_ID = "unity_id";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_edit_unit_of_measure);
        mUnit = new Unity();

        try {


            mUnit = (Unity) ManagerBuilder.build(this,mUnit).load(this.getIntent().getLongExtra(
                    this.INPUT____UNITY_ID, AppConsts.NO_ID));
            refreshScreen();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        ;

    }// fin du onCreate


    /******************************************************************************************
     * onValidateClick : Mettre à jour les informations saisies dans la base de
     * donnée
     *
     * @param v
     ******************************************************************************************/
    public void onValidateClick(View v) {

        getScreenData();
        this.mUnit.setId(ManagerBuilder.build(this,this.mUnit).save());

        // on appelle setResult pour déclancher le onActivityResult de
        // l'activity mère.
        setResult(RESULT_OK, this.getIntent());

        // On termine l'Actvity
        finish();
    }

    /******************************************************************************************
     * refreshScreen :
     ******************************************************************************************/
    private void refreshScreen() {
        EditText ed = (EditText) findViewById(R.id.unitview_name);
        ed.setText(this.mUnit.getLongName());

        ed = (EditText) findViewById(R.id.unitview_symbol);
        ed.setText(this.mUnit.getShortName());

        RadioButton rb1 = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton rb2 = (RadioButton) findViewById(R.id.radioButton2);
        rb1.setChecked(false);
        rb2.setChecked(false);

        switch (this.mUnit.getUnityClass()) {
            case INTERNATIONAL:
                rb1.setChecked(true);
                break;
            case CUSTOM:
                rb2.setChecked(true);
                break;
            default:
                break;

        }
    }

    /******************************************************************************************
     * refreshScreen :
     ******************************************************************************************/
    private void getScreenData() {
        EditText ed = (EditText) findViewById(R.id.unitview_name);
        this.mUnit.setLongName(ed.getText().toString());

        ed = (EditText) findViewById(R.id.unitview_symbol);
        this.mUnit.setShortName(ed.getText().toString());


    }

    public void onClickRdioInternational(View v) {
        this.mUnit.setUnityClass(UNIT_CLASS.INTERNATIONAL);
        RadioButton rb2 = (RadioButton) findViewById(R.id.radioButton2);
        rb2.setChecked(false);
    }

    public void onClickRdioCustom(View v) {
        this.mUnit.setUnityClass(UNIT_CLASS.CUSTOM);
        RadioButton rb1 = (RadioButton) findViewById(R.id.radioButton1);
        rb1.setChecked(false);

    }

}