package com.rdupuis.amikcal.energy;

public abstract class AbstractEnergy {


	private float calories;
	private float glucids;
	private float lipids;
	private float proteins;
		
	public void setCalories(float calories) {
		this.calories = calories;
	}
	public float getCalories() {
		return calories;
	}
	public void setGlucids(float glucids) {
		this.glucids = glucids;
	}
	public float getGlucids() {
		return glucids;
	}
	public void setLipids(float lipids) {
		this.lipids = lipids;
	}
	public float getLipids() {
		return lipids;
	}
	public void setProteins(float proteins) {
		this.proteins = proteins;
	}
	public float getProteins() {
		return proteins;
	}
	






}
