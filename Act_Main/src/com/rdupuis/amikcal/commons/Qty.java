package com.rdupuis.amikcal.commons;

import com.rdupuis.amikcal.unity.Unity;

public class Qty {
private float amount;

private Unity mUnity;
public float getAmount() {
	return amount;
}
public void setAmount(float amount) {
	this.amount = amount;
}
public Unity getUnity() {
	return mUnity;
}
public void setUnity(Unity mUnity) {
	this.mUnity = mUnity;
}
}
