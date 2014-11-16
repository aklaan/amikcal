package com.rdupuis.amikcal.useractivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.useractivity.Frag_UserActivity_StatisticsOfDay;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.TextView;

/**
 * <b>Liste des activitées de l'utilisateur.</b>
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

public class Frag_UserActivity_StatisticsOfDay extends Fragment {

	private ListView mCustomListView;
	private Calendar currentDay;
	private int currentTypeOfList;
	private Intent mIntent;
	private long selectedItemId;
	private Resources mResources;

	/** Called when the activity is first created. */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View mainView = inflater.inflate(R.layout.view_ua_statistics_of_day,
				container, false);

		mResources = getActivity().getResources();
		mIntent = getActivity().getIntent();

		// * on tente de récupérer la date dans le bundle si elle est présente
		try {

			currentDay = ToolBox
					.parseCalendar(getArguments().getString("date"));

		} catch (Exception e) {
			currentDay = Calendar.getInstance();
		}
		;

		// Afficher la date du jour.
		TextView tv = (TextView) mainView
				.findViewById(R.id.view_ua_statistic_of_day_tv_date);
		// ed.setText("Page " + page + " - " + ToolBox.getSqlDate(currentDay));
		tv.setText(ToolBox.getSqlDate(currentDay));

		tv = (TextView) (TextView) mainView
				.findViewById(R.id.view_ua_statistic_of_day_tv_nbcal_in);
		tv.setText(getTotalEnergyOfDay(ToolBox.getSqlDate(currentDay)));

		return mainView;
	};

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
