package com.rdupuis.amikcal.relations;

import android.content.ContentResolver;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.components.Component;

public class Relation_Equiv_Btwn2Components extends Relation {
    private Component mComponent1;
    private Component mComponent2;
    private long _id;

    public Relation_Equiv_Btwn2Components(Component component1, Component component2) {
	this.mComponent1=component1;
	this.mComponent2=component2;
	this._id = AppConsts.NO_ID;
    }

    @Override
    public String getParty1() {

	return String.valueOf(this.mComponent1.getDatabaseId());
    }

    @Override
    public String getParty2() {

	return String.valueOf(this.mComponent2.getDatabaseId());
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
	return REL_TYP_CD.COMP_EQUIV;
    }


}
