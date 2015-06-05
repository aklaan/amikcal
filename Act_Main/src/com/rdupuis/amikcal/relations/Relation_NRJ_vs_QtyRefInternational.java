package com.rdupuis.amikcal.relations;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.Qty;
import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.relations.REL_TYP_CD;
import com.rdupuis.amikcal.useractivity.UserActivity;

public class Relation_NRJ_vs_QtyRefInternational implements I_Relation {
    private EnergySource mEnergySource;
    private Qty mQtyReference;
    private long _id;

    public Relation_NRJ_vs_QtyRefInternational(EnergySource energySource, Qty qtyReference) {
	this.mEnergySource=energySource;
	this.mQtyReference = qtyReference;
	this._id = AppConsts.NO_ID;
    }

    @Override
    public String getParty1() {

	return String.valueOf(this.mEnergySource.getId());
    }

    @Override
    public String getParty2() {

	return String.valueOf(this.mQtyReference.getId());
    }

    @Override
    public long getId() {

	return _id;
    }

    @Override
    public void setId(long rel_id) {
	this._id = rel_id;

    }

    @Override
    public REL_TYP_CD getRelationClass() {
	// TODO Auto-generated method stub
	return REL_TYP_CD.NRJ_REF_INTRNL;
    }

}
