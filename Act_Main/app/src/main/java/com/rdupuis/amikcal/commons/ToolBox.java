package com.rdupuis.amikcal.commons;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.data.DatabaseObj;
/**
 * <b>Boite à outils.</b>
 * 
 * @author Rodolphe Dupuis
 * @version 0.1
 */
public final class ToolBox {

	public final static String SQL_DATE_YYYY_MM_DD = "yyyy-MM-dd";
	public final static String SQL_DATE_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * Retourne date au format SQL DATE
	 * @param calendar
	 * @return String qui représente une date au format yyyy-MM-dd
	 */
	public static String getSqlDate(Calendar calendar){
		String result="";
		try {
		SimpleDateFormat mSqlDateFormat = new SimpleDateFormat(SQL_DATE_YYYY_MM_DD);
		mSqlDateFormat.setCalendar(calendar);
		result =  mSqlDateFormat.format(mSqlDateFormat.getCalendar().getTime());
		}	
		catch (NullPointerException e){
		e.printStackTrace();
		}
		return result;
	};

	/**
	 *  Retourne la date et l'heure d'un calendrer au format SQL TIMESTAMP
	 * 	@param calendar
	 * 	@return String qui représente une date au format yyyy-MM-dd HH:mm:ss
	 */
	public static String getSqlDateTime(Calendar calendar){
		String result="";
		try {
			SimpleDateFormat mSqlDateFormat = new SimpleDateFormat(SQL_DATE_YYYY_MM_DD_HH_MM_SS);
			mSqlDateFormat.setCalendar(calendar);
			result =  mSqlDateFormat.format(mSqlDateFormat.getCalendar().getTime());
		}	
		catch (NullPointerException e){
			e.printStackTrace();
		}
		return result;
	};

	/**
	 *  Initialiser un calendrier avec une date de type SQL DATE
	 * 	@param sqlDate
	 * 	@return Calendar
	 */
	public static Calendar parseCalendar(String sqlDate){
		Calendar result = Calendar.getInstance();
		try {
			SimpleDateFormat mSqlDateFormat = new SimpleDateFormat(SQL_DATE_YYYY_MM_DD);
			mSqlDateFormat.parse(sqlDate);
			result = mSqlDateFormat.getCalendar();
		}	
		catch (ParseException e){
			e.printStackTrace();
		}
		return result;
	};

	/**
	 * Initialiser un calendrier avec une date de type SQL TIMESTAMP
	 * @param sqlDatetime
	 * @return Calendar
	 */
	public static Calendar parseSQLDatetime(String sqlDatetime){
		Calendar result = Calendar.getInstance();
		try {
			SimpleDateFormat mSqlDateFormat = new SimpleDateFormat(SQL_DATE_YYYY_MM_DD_HH_MM_SS);
			mSqlDateFormat.parse(sqlDatetime);
			result = mSqlDateFormat.getCalendar();
		}	
		catch (ParseException e){
			e.printStackTrace();
		}
		return result;
	};


	/**
	 * récupérer la date et l'heure courante
	 * @return String au format yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentTimestamp(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date date = new Date();
	return dateFormat.format(date);
}
	

	/**
	 *  récupérer la version de la Database
	 * 	@return n° de version de la base de donnée
	 */
	public static int getAmikcalDbVersion(){
		int version=0;
		try{
		  SQLiteDatabase db= SQLiteDatabase.openDatabase(("/data/data/"
    		+ "com.rdupuis.amikcal/databases/"
    		+DatabaseObj.DATABASE_NAME),null,SQLiteDatabase.OPEN_READONLY);
		  version =  db.getVersion();
		  db.close();
		} catch(Exception e){version=-1;}
		return version;
}

	/**
	 * Fabriquer une chaine de caract�re date au format SQL TIMESTAMP
	 * @param date - date au format yyyy-MM-dd
	 * @param hours - heure
	 * @param minutes - minutes 
	 * @param seconds - secondes
	 * @return String au format yyyy-MM-dd HH:mm:ss
	 */
	public static String makeDatetime(String date, int hours, int minutes,int seconds){
	DecimalFormat decimalFormat = (DecimalFormat)DecimalFormat.getInstance();
   	decimalFormat.applyPattern("00");
	
	return date
			+" "+
			decimalFormat.format(hours)
			+":"
			+ 	decimalFormat.format(minutes)
			+":"
			+	decimalFormat.format(seconds);
	
	
	}

	public static String getSumOfFoodEnergyForDay(Activity activity, String DateToSelect){
		 String result ="";
	   	
		Uri selectUri;
	   	selectUri = ContentDescriptorObj.CustomQuery.URI_SUM_ENERGY_OF_DAY.buildUpon().appendPath(DateToSelect).build();
	     
	    Cursor cur = activity.getContentResolver().query(selectUri, null, null, null, null);
	           
	    final int indexOfColumn_SUM_MT_ENERGY = cur.getColumnIndex("SUM_MT_ENERGY");
	            
	    if (cur.moveToFirst()) {
	           
	    	DecimalFormat decimalFormat = (DecimalFormat)DecimalFormat.getInstance();
	        decimalFormat.applyPattern("####0");
	        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
	        dfs.setDecimalSeparator('.');
	        decimalFormat.setDecimalFormatSymbols( dfs );
	           	 
	        result = decimalFormat.format(cur.getFloat(indexOfColumn_SUM_MT_ENERGY));
	    }
	    cur.close();
	    return result;
	}



}


