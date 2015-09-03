package com.rdupuis.amikcal.commons;

import java.text.DecimalFormat;
import java.util.StringTokenizer;

public class WeightObj {

	private String separator;
	private int int_part;
	private int decimalPart;

	//constructeur par défaut
	public WeightObj(){
		this.separator=".";
	}
	
	public WeightObj(String weight)  {
		
		this.separator=".";
		StringBuilder s  =new StringBuilder();
		s.append(weight);
		
		if (s.indexOf(",")!=-1){
		this.separator = ",";	
		}else if (s.indexOf(".")!=-1){
			this.separator = ".";
		} 
		
		StringTokenizer tokens = new StringTokenizer(weight, this.separator);
		
		if (tokens.hasMoreTokens()){this.int_part=Integer.valueOf(tokens.nextToken());}
		if (tokens.hasMoreTokens()){this.decimalPart=Integer.valueOf(tokens.nextToken());}
		}
	 
	
	
	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public int getInt_part() {
		return int_part;
	}

	public void setInt_part(int int_part) {
		this.int_part = int_part;
	}

	public int getDecimalPart() {
		return decimalPart;
	}

	public void setDecimalPart(int decimalPart) {
		this.decimalPart = decimalPart;
	}
	
	
	/**
	 * Retourner le poids dans le format 999,99
	 * @since 2012-08-23
	 * @return String représentant le poids du format 999,99
	 **/
	public String format(){
		DecimalFormat decimalFormatInt_part = (DecimalFormat)DecimalFormat.getInstance();
	   	decimalFormatInt_part.applyPattern("##0");
		
	   	DecimalFormat decimalFormatDec_part = (DecimalFormat)DecimalFormat.getInstance();
	   	decimalFormatDec_part.applyPattern("0");
	   	
		return	decimalFormatInt_part.format(this.int_part)
				+ this.separator
				+ 	decimalFormatDec_part.format(this.decimalPart);
	}
}
