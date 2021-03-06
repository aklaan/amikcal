package com.rdupuis.amikcal.unity;

import com.rdupuis.amikcal.commons.AppConsts;

public class Unity {
	public static enum UNIT_CLASS {
		INTERNATIONAL, CUSTOM, CONTAINER, FOOD_COMPONENT,TIME,UNDEFINED
	}

private long id;
private String longName;
private String shortName;
private UNIT_CLASS mUnityClass;

public Unity(){
    this.id = AppConsts.NO_ID;
    this.longName="";
    this.shortName="";
    this.mUnityClass = UNIT_CLASS.UNDEFINED;
    
}

public long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getLongName() {
	return this.longName;
}
public void setLongName(String longName) {
	this.longName = longName;
}
public String getShortName() {
	return this.shortName;
}
public void setShortName(String shortName) {
	this.shortName = shortName;
}
public UNIT_CLASS getUnityClass() {
	return mUnityClass;
}
public void setUnityClass(UNIT_CLASS mClass) {
	this.mUnityClass = mClass;
}


}
