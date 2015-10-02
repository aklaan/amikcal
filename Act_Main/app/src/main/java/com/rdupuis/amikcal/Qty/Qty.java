package com.rdupuis.amikcal.Qty;

import android.content.ContentResolver;
import android.os.Parcel;
import android.os.Parcelable;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ManagedElement;
import com.rdupuis.amikcal.relations.REL_TYP_CD;
import com.rdupuis.amikcal.relations.Relation;
import com.rdupuis.amikcal.unity.Unity;

//une Qty est une relation entre une unitée et un montant
public class Qty extends Relation implements ManagedElement, Parcelable {

    private long _id;
    private Unity mUnity;
    private float amount;

    public Qty() {
        this.setDatabaseId(AppConsts.NO_ID);
        this.mUnity = new Unity();
        this.amount = 0f;
    }

    public Qty(Parcel parcel) {
        this._id = parcel.readLong();
        this.amount = parcel.readFloat();
        this.mUnity = parcel.readParcelable(Unity.class.getClassLoader());

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.getDatabaseId());
        dest.writeFloat(this.amount);
        dest.writeParcelable(this.mUnity, 0);

    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Qty> CREATOR = new Parcelable.Creator<Qty>() {
        @Override
        public Qty createFromParcel(Parcel in) {
            return new Qty(in);
        }

        @Override
        public Qty[] newArray(int size) {
            return new Qty[size];

        }

    };

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
        return String.valueOf(this.getUnity().getDatabaseId());
    }

    @Override
    public REL_TYP_CD getRelationClass() {
        // TODO Auto-generated method stub
        return REL_TYP_CD.QTY;
    }



}
