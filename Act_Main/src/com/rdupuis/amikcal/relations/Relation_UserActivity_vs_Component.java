package com.rdupuis.amikcal.relations;

import android.content.ContentResolver;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.data.writers.DBWriter;
import com.rdupuis.amikcal.energy.DBWarper;
import com.rdupuis.amikcal.relations.REL_TYP_CD;
import com.rdupuis.amikcal.useractivity.UserActivity;

public class Relation_UserActivity_vs_Component extends I_Relation {
    private UserActivity mUserActivity;
    private Component mComponent;
    private long _id;

    public Relation_UserActivity_vs_Component(UserActivity userActivity, Component component) {
	this.mUserActivity = userActivity;
	this.mComponent = component;
	this._id = AppConsts.NO_ID;
    }

    @Override
    public String getParty1() {

	return String.valueOf(mUserActivity.getId());
    }

    @Override
    public String getParty2() {

	return String.valueOf(mComponent.getId());
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
	return REL_TYP_CD.UA_COMP;
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
