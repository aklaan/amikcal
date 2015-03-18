package com.rdupuis.amikcal.useractivity;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ToolBox;

/**
 * <b>Ecran de choix de création des activitées.</b>
 * <p>
 * les activitées sont :
 * <ul>
 * <li>les repas</li>
 * <li>les activitées physiques</li>
 * <li>les pesées</li>
 * </ul>
 * </p>
 * 
 * @author Rodolphe Dupuis
 * @version 0.1
 */
public class Act_UserActivity_ChooseNewActivity extends Activity {

	Calendar currentDay;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**
		 * ===================================================================== 
		 * Etape I - On récupère les données fournies dans l'Intent
		 * ======================================================================
		 */
		// On tente de récupérer la date dans le bundle de l'activité si elle
		// est présente
		// si elle n'est pas présente, on met la date du jour.
		try {

			currentDay = ToolBox
					.parseCalendar(getIntent()
							.getStringExtra(
									AppConsts.INPUT____CHOOSE_NEW_ACTIVITY____DAY_OF_THE_USER_ACTIVITY));

		} catch (Exception e) {
			currentDay = Calendar.getInstance();
		}
		;

		/****************************************************************************************
		 * Etape II- On charge l'écran
		 ****************************************************************************************/
		setContentView(R.layout.choose_activity);

	}

	/*********************************************************************************************
	 *  onActivityResult : 
	 *  au retour de l'appel à l'écran d'édition, on ferme l'activitée
	 * **************************************************************************************
	  */
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		setResult(RESULT_OK, this.getIntent());
		this.finish();
	}


	/*******************************************************************************************
	 * onClickLunch() :
	 * Gestion du bouton "repas"
	 *******************************************************************************************/
	public void onClickLunch(View v) {
		UserActivityItem userActivityItem = new UserActivityLunchItem(this);
		userActivityItem.create(this.currentDay);
	}

	/*******************************************************************************************
	 * onClickPhysicalActivity()  :
	 * Gestion du bouton "activité physique"
	 *******************************************************************************************/
	public void onClickPhysicalActivity(View v) {
		UserActivityItem userActivityItem = new UserActivityMoveItem(this);
		userActivityItem.create(this.currentDay);
	}

	/*******************************************************************************************
	 * onClickWeight():
	 * Gestion du bouton "poids"
	 *******************************************************************************************/
	public void onClickWeight(View v) {
		UserActivityItem userActivityItem = new UserActivityWeightItem(this);
		userActivityItem.create(this.currentDay);
	}

}