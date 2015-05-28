package com.rdupuis.amikcal.useractivity;

import java.util.Calendar;

import com.rdupuis.amikcal.commons.WeightObj;

public class UserActivity_Weight extends UserActivity {

    public WeightObj weight;

    public UserActivity_Weight() {

	super();

	this.type = UserActivity.UA_CLASS_CD.WEIGHT;
	this.weight = new WeightObj();

    }

    public UserActivity_Weight(Calendar day) {
	super(day);
	this.type = UserActivity.UA_CLASS_CD.WEIGHT;
	this.weight = new WeightObj();
    }

    
    public WeightObj getWeight() {
	return weight;
    }

    public void setWeight(WeightObj weight) {
	this.weight = weight;
    }

}
