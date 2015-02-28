package com.rdupuis.amikcal.useractivity;

import com.rdupuis.amikcal.commons.WeightObj;

public class UserActivityWeight extends UserActivity{

	
	public WeightObj weight;

	public UserActivityWeight() {
		
		super();
		
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
