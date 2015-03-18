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
 * <b>Ecran de choix de cr�ation des activit�es.</b>
 * <p>
 * les activit�es sont :
 * <ul>
 * <li>les repas</li>
 * <li>les activit�es physiques</li>
 * <li>les pes�es</li>
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
		 * Etape I - On r�cup�re les donn�es fournies dans l'Intent
		 * ======================================================================
		 */
		// On tente de r�cup�rer la date dans le bundle de l'activit� si elle
		// est pr�sente
		// si elle n'est pas pr�sente, on met la date du jour.
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
		 * Etape II- On charge l'�cran
		 ****************************************************************************************/
		setContentView(R.layout.choose_activity);

	}

	/*********************************************************************************************
	 *  onActivityResult : 
	 *  au retour de l'appel � l'�cran d'�dition, on ferme l'activit�e
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
	 * Gestion du bouton "activit� physique"
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