package com.rdupuis.amikcal.commons;

import com.rdupuis.amikcal.unity.Unity;

//une Qty est une relation entre une unité et un montant
public class Qty extends Relation{
	private float mAmount;
	private Unity mUnity;

	public Qty(){
	this.setId(AppConsts.NO_ID);
	this.setRel_typ_cd(REL_TYP_CD.QTY);
	this.mAmount = 0f;
	this.mUnity = new Unity();
	}
	
	
public float getAmount() {
	return mAmount;
}
public void setAmount(float amount) {
	this.mAmount =amount;
}

public Unity getUnity() {
	return mUnity;
}
public void setUnity(Unity mUnity) {
	this.mUnity = mUnity;
}


}
