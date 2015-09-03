package com.rdupuis.amikcal.useractivity.weight;

import java.util.ArrayList;
import java.util.Calendar;

import com.rdupuis.amikcal.commons.WeightObj;
import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.useractivity.UserActivity;
import com.rdupuis.amikcal.useractivity.UA_CLASS_CD;
import com.rdupuis.amikcal.useractivity.UserActivity_Commons;

public class UserActivity_Weight extends UserActivity_Commons {

    public WeightObj weight;

    public UserActivity_Weight() {

	super();

	
	this.weight = new WeightObj();

    }

    public UserActivity_Weight(Calendar day) {
	super(day);
	
	this.weight = new WeightObj();
    }

    
    public WeightObj getWeight() {
	return weight;
    }

    public void setWeight(WeightObj weight) {
	this.weight = weight;
    }

    @Override
    public long getDatabaseId() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public void setDatabaseId(long _id) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public UA_CLASS_CD getActivityType() {
	
	return UA_CLASS_CD.WEIGHT;
    }

    @Override
    public String getTitle() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void setTitle(String title) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public Calendar getDay() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void setDay(Calendar day) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void setDay(int year, int month, int day, int hourOfDay, int minute) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public ArrayList<Component> getComponentsList() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void setComponentsList(ArrayList<Component> arrayList) {
	// TODO Auto-generated method stub
	
    }

}
