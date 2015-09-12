package com.rdupuis.amikcal.Qty;

import java.util.ArrayList;

import android.content.ContentResolver;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ManagedElement;
import com.rdupuis.amikcal.data.writers.DBWriter;
import com.rdupuis.amikcal.energy.DBWarper;
import com.rdupuis.amikcal.energy.DBWarper_Qty;
import com.rdupuis.amikcal.relations.REL_TYP_CD;
import com.rdupuis.amikcal.relations.Relation;
import com.rdupuis.amikcal.unity.Unity;

//une Qty est une relation entre une unitée et un montant
public class Qty extends Relation implements ManagedElement{

    private long _id;
    private Unity mUnity;
    private float amount;
    private ArrayList<Qty> mEquivalences;

    public Qty() {
        this.setDatabaseId(AppConsts.NO_ID);
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

    public void setDatabaseId(long id) {
        this._id = id;

    }

    public long getDatabaseId() {

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

    public ArrayList<Qty> getEquivalences() {
        // TODO Auto-generated method stub
        return this.mEquivalences;
    }

    public void setEquivalences(ArrayList<Qty> equivalences) {
        // TODO Auto-generated method stub
        this.mEquivalences = equivalences;

    }

}
