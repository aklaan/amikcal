package com.rdupuis.amikcal.commons;

import com.rdupuis.amikcal.unity.Unity;

//une Qty est une relation entre une unité et un montant
public class Qty extends Relation {

    private Unity mUnity;

    public Qty() {
	this.setId(AppConsts.NO_ID);
	this.setRel_typ_cd(REL_TYP_CD.QTY);
	this.mUnity = new Unity();
    }

    public float getAmount() {

	if (this.getParty1() == "")

	    return 0;
	else
	    return Float.parseFloat(this.getParty1());
    }

    public void setAmount(float amount) {
	this.setParty1(String.valueOf(amount));
    }

    public Unity getUnity() {
	return mUnity;
    }

    public void setUnity(Unity mUnity) {
	this.mUnity = mUnity;
	this.setParty2(String.valueOf(mUnity.getId()));
    }

}
