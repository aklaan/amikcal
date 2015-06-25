package com.rdupuis.amikcal.commons;

import android.content.ContentResolver;

import com.rdupuis.amikcal.data.Savable;
import com.rdupuis.amikcal.data.writers.DBWriter;
import com.rdupuis.amikcal.data.writers.DBWriter_Qty;
import com.rdupuis.amikcal.energy.DBWarper;
import com.rdupuis.amikcal.energy.DBWarper_Qty;
import com.rdupuis.amikcal.relations.I_Relation;
import com.rdupuis.amikcal.relations.REL_TYP_CD;
import com.rdupuis.amikcal.unity.Unity;

//une Qty est une relation entre une unité et un montant
public class Qty implements I_Relation, Savable {

	private long _id;
	private Unity mUnity;
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
		return REL_TYP_CD.QTY;
	}

	@Override
	public DBWarper getDBWarper() {
		return new DBWarper_Qty(this);
	}

	@Override
	public DBWriter getDBWriter(ContentResolver contentResolver) {
		return new DBWriter_Qty(contentResolver, this);
	}

}
