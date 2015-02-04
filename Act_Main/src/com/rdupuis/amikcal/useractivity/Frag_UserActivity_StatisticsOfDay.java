package com.rdupuis.amikcal.useractivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.TimeSlidableFragment;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.useractivity.Frag_UserActivity_StatisticsOfDay;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * <b>Liste des activit�es de l'utilisateur.</b>
 * <p>
 * les activit�es sont :
 * <ul>
 * <li>les repas</li>
 * <li>les activit�es physiques</li>
 * <li>les pes�es</li>
 * <li>les recettes</li>
 * </ul>
 * </p>
 * 
 * @author Rodolphe Dupuis
 * @version 0.1
 */

public class Frag_UserActivity_StatisticsOfDay extends TimeSlidableFragment {

	/** Called when the activity is first created. */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// la premi�re chose � faire est d'initialiser la date � afficher.
		this.initCurrentDay();
		
		//on s'occupe de l'affichage
		View mainView = inflater.inflate(R.layout.view_ua_statistics_of_day,
				container, false);
		
		// Afficher la date du jour.
		TextView tv = (TextView) mainView
				.findViewById(R.id.view_ua_statistic_of_day_tv_date);
		tv.setText(ToolBox.getSqlDate(currentDay));

		//Afficher le nombre de calories du jour.
		tv = (TextView) (TextView) mainView
				.findViewById(R.id.view_ua_statistic_of_day_tv_nbcal_in);
		tv.setText(getTotalEnergyOfDay(ToolBox.getSqlDate(currentDay)));

		return mainView;
	};

	
	/**
	 * Calculer le nombre total de calories pour une journ�e.
	 * @param DateToSelect
	 * @return
	 */
	protected String getTotalEnergyOfDay(String DateToSelect) {
		String result = "";

		Uri selectUri;
		selectUri = ContentDescriptorObj.CustomQuery.URI_SUM_ENERGY_OF_DAY
				.buildUpon().appendPath(DateToSelect).build();

		Cursor cur = getActivity().getContentResolver().query(selectUri, null,
				null, null, null);

		final int INDX_COL_ID = cur.getColumnIndex("SUM_MT_ENERGY");

		if (cur.moveToFirst()) {

			DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat
					.getInstance();
			decimalFormat.applyPattern("###0.00");
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			decimalFormat.setDecimalFormatSymbols(dfs);

			result = decimalFormat.format(cur.getFloat(INDX_COL_ID));
		}
		cur.close();
		return result;
	}

}
