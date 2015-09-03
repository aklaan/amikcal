package com.rdupuis.amikcal.relations;

import android.content.ContentResolver;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.data.writers.DBWriter;
import com.rdupuis.amikcal.energy.DBWarper;
import com.rdupuis.amikcal.energy.EnergySource;

public class Relation_NRJ_vs_Component extends Relation {
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

	return String.valueOf(this.mEnergy.getDatabaseId());
    }

    @Override
    public String getParty2() {

	return String.valueOf(this.mComponent.getDatabaseId());
    }

    @Override
    public long getDatabaseId() {

	return _id;
    }

    @Override
    public void setDatabaseId(long rel_id) {
	this._id = rel_id;

    }

    @Override
    public REL_TYP_CD getRelationClass() {
	// TODO Auto-generated method stub
	return REL_TYP_CD.NRJ_REF_INTRNL;
    }

	@Override
	public DBWarper getDBWarper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBWriter getDBWriter(ContentResolver contentResolver) {
		// TODO Auto-generated method stub
		return null;
	}

}
