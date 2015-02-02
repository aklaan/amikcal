package com.rdupuis.amikcal.useractivity;

import java.util.Calendar;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AmikcalVar;
import com.rdupuis.amikcal.commons.ToolBox;

/**
 * <b>Ecran d'édition des activitées de l'utilisateur.</b>
 * <p>
 * les activitées sont :
 * <ul>
 * <li>les repas</li>
 * <li>les activitées physiques</li>
 * <li>les pesées</li>
 * <li>les recettes</li>
 * </ul>
 * </p>
 * 
 * @author Rodolphe Dupuis
 * @version 0.1
 */
public class Act_UserActivity_ChooseNewActivity extends Activity {

	Intent mIntent;
	Long mId;
	boolean morning = true;
	int timeRange;
	Calendar currentDay;
	long currentId;
	Resources mResources;
	final long NO_ID = -1l;

	ContentResolver contentResolver;
	UserActivity mUserActivity;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.choose_activity);

		// On tente de récupérer la date dans le bundle de l'activité si elle est présente
		// si elle n'est pas présente, on met la date du jour.
		try {

			currentDay = ToolBox
					.parseCalendar(getIntent().getStringExtra(AmikcalVar.INPUT____CHOOSE_NEW_ACTIVITY____DAY_OF_THE_USER_ACTIVITY));

		} catch (Exception e) {
			currentDay = Calendar.getInstance();
		}
		;

		
	}

	/**==============================================================================
	 * onActivityResult
	 * au retour de l'appel à l'écran d'édition, on ferme l'activitée
	 ===============================================================================*/
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
		setResult(RESULT_OK, mIntent);
		this.finish();
	
	}
	
	// fin du onCreate

	/*******************************************************************************************
	 * Méthode : onClickLunch() Gestion du bouton repas
	 *******************************************************************************************/
	public void onClickLunch(View v) {
		UserActivityItem userActivityItem = new UserActivityLunchItem(this);
		userActivityItem.create(this.currentDay);
	}

	/*******************************************************************************************
	 * Méthode : onClickPhysicalActivity() Gestion du bouton activité physique
	 *******************************************************************************************/
	public void onClickPhysicalActivity(View v) {
		setContentView(R.layout.view_edit_physical_activity);
		this.mUserActivity.setType(UserActivity.UAType.MOVE);
		this.mUserActivity.setTitle(this.mResources
				.getString(R.string.TITLE_PHYSICAL_ACTIVITY));
	}

	/*******************************************************************************************
	 * Méthode : onClickWeight() Gestion du bouton poids
	 *******************************************************************************************/
	public void onClickWeight(View v) {
		setContentView(R.layout.view_edit_weight);
		this.mUserActivity.setType(UserActivity.UAType.WEIGHT);

		// poids par defaut
		TextView tv = (TextView) findViewById(R.id.userActivity_Editor_tv_kilos);
		tv.setText("40");
		tv = (TextView) findViewById(R.id.userActivity_Editor_tv_grammes);
		tv.setText("00");
	}

}