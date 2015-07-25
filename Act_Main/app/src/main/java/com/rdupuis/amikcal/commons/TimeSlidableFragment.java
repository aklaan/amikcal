package com.rdupuis.amikcal.commons;

import java.util.Calendar;

import android.support.v4.app.Fragment;

public class TimeSlidableFragment extends Fragment{
	public final static String INTENT_INPUT____DAY = "____DAY";
	public Calendar currentDay;


public void initCurrentDay(){
	
	// On tente de r�cup�rer la date dans le bundle de l'activit� si elle est pr�sente
	// si elle n'est pas pr�sente, on met la date du jour.
	try {

		currentDay = ToolBox
				.parseCalendar(getArguments().getString(TimeSlidableFragment.INTENT_INPUT____DAY));

	} catch (Exception e) {
		currentDay = Calendar.getInstance();
	}
	;
}
}
