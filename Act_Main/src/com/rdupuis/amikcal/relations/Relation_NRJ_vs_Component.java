package com.rdupuis.amikcal.relations;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.energy.EnergySource;

public class Relation_NRJ_vs_Component implements I_Relation {
    private EnergySource mEnergy;
    private Component mComponent;
    private long _id;

    public Relation_NRJ_vs_Component(EnergySource energy, Component component) {
	this.mEnergy=energy;
	this.mComponent = component;
	this._id = AppConsts.NO_ID;
    }

    @Override
    public String getParty1() {

	return String.valueOf(this.mEnergy.getId());
    }

    @Override
    public String getParty2() {

	return String.valueOf(this.mComponent.getId());
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
