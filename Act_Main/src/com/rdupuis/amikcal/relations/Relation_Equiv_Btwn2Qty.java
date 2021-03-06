package com.rdupuis.amikcal.relations;

import android.content.ContentResolver;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.Qty;
import com.rdupuis.amikcal.data.writers.DBWriter;
import com.rdupuis.amikcal.energy.DBWarper;
import com.rdupuis.amikcal.relations.REL_TYP_CD;

public class Relation_Equiv_Btwn2Qty extends I_Relation {
    private Qty mQty1;
    private Qty mQty2;
    private long _id;

    public Relation_Equiv_Btwn2Qty(Qty qty1, Qty qty2) {
	this.mQty1=qty1;
	this.mQty2=qty2;
	this._id = AppConsts.NO_ID;
    }

    @Override
    public String getParty1() {

	return String.valueOf(this.mQty1.getId());
    }

    @Override
    public String getParty2() {

	return String.valueOf(this.mQty2.getId());
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
	return REL_TYP_CD.QTY_EQUIV;
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
