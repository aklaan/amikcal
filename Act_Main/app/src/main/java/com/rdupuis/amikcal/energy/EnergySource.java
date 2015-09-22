package com.rdupuis.amikcal.energy;

import android.os.Parcel;
import android.os.Parcelable;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ManagedElement;
import com.rdupuis.amikcal.components.Component_Reference;

/**
 * <h1>EnergySource : une source d'�nergie<h1>
 * <p/>
 * <p>
 * une source d'�nergie : - poss�de un nom - peut �tre repr�sent� par une
 * quantit� de calories - cette en�rgie peu �tre absorb� (manger)ou d�pens�e
 * (courir)
 * </p>
 *
 * @author Rodolphe
 */

public class EnergySource implements  ManagedElement,Parcelable {

    private long databaseId;
    private String name;
    private Component_Reference mReference;


    public EnergySource() {
        this.databaseId = AppConsts.NO_ID;
        name = "";
        this.mReference = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDatabaseId() {
        // TODO Auto-generated method stub
        return this.databaseId;
    }

    public void setDatabaseId(long databaseId) {
        this.databaseId = databaseId;
    }

    public Component_Reference getComponentReference() {
        // TODO Auto-generated method stub
        return this.mReference;
    }

    public void setComponentReference(Component_Reference component_reference) {
        this.mReference = component_reference;
    }

    public NRJ_CLASS getEnergyClass() {
        // TODO Auto-generated method stub
        return NRJ_CLASS.ENERGY;
    }

    public EnergySource(Parcel parcel) {
        this.databaseId = parcel.readLong();
        this.name = parcel.readString();
        this.mReference = parcel.readParcelable(Component_Reference.class.getClassLoader());
        //faire le reste des data
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.getDatabaseId());
        dest.writeString(this.getName());
        dest.writeParcelable(this.getComponentReference(), 0);

        //ajouter le reste
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<EnergySource> CREATOR = new Parcelable.Creator<EnergySource>() {
        @Override
        public EnergySource createFromParcel(Parcel in) {
            return new EnergySource(in);
        }

        @Override
        public EnergySource[] newArray(int size) {
            return new EnergySource[size];

        }

    };



}
