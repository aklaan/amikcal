package com.rdupuis.amikcal.day;

import java.text.SimpleDateFormat;
import java.util.Calendar;


import com.rdupuis.amikcal.commons.ToolBox;
public class DayObj  {

	private Calendar day;
	
    
	/*********************************************************************
	 * Getter
	 */
	
	public Calendar getDay() {
		return day;
	}
	
	
	/*********************************************************************
	 * Setter
	 */
	
	public void setDay(Calendar value) {
		this.day = value;
	}
	
	public void setDay(String SqlDatevalue) {
		
		day = ToolBox.parseCalendar(SqlDatevalue);
	}
	
	
	public String getSqlDate(){
		
		SimpleDateFormat mSqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		mSqlDateFormat.setCalendar(this.getDay());
	//	String toto;
	//	toto =  day.get(Calendar.YEAR)
	//			+ "-" 
	//			+ (((day.get(Calendar.MONTH)+1)<10)?("0"+ String.valueOf(day.get(Calendar.MONTH)+1)):String.valueOf(day.get(Calendar.MONTH)+1))
	//			+ "-" 
    //			+ (	(day.get(Calendar.DATE)<10)?("0" + String.valueOf(day.get(Calendar.DATE))):String.valueOf(day.get(Calendar.DATE)));

		return  mSqlDateFormat.format(mSqlDateFormat.getCalendar().getTime());
	}


	

	
}
