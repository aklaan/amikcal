package com.rdupuis.amikcal.commons;

import com.rdupuis.amikcal.commons.Relation.REL_TYP_CD;
import com.rdupuis.amikcal.unity.Unity;

//une Qty est une relation entre une unité et un montant
public class Qty implements I_Relation {

	private long _id;
	private Unity mUnity;
	private  final REL_TYP_CD relationClass = REL_TYP_CD.QTY;
	private float amount;

	public Qty() {
		this.setId(AppConsts.NO_ID);
		this.mUnity = new Unity();
		this.amount = 0f;
	}

	public float getAmount() {
		return amount;

	}
		
	public void setAmount(float newAmount) {
		this.amount = newAmount;
	}

	
	public Unity getUnity() {
		return mUnity;
	}

	public void setUnity(Unity mUnity) {
		this.mUnity = mUnity;
		
	}

	
	public void setId(long id) {
		this._id = id;
	
	}
	
	public long getId() {
		
		return this._id;
	}
	
	@Override
	public String getParty1() {
		// TODO Auto-generated method stub
		return String.valueOf(this.amount);
	}

	@Override
	public String getParty2() {
		// TODO Auto-generated method stub
		return String.valueOf(this.getUnity().getId());
	}


	@Override
	public REL_TYP_CD getRelationClass() {
		// TODO Auto-generated method stub
		return relationClass;
	}

}
