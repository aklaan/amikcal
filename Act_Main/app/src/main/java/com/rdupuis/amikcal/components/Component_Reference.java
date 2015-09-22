package com.rdupuis.amikcal.components;

import android.os.Parcel;
import android.os.Parcelable;

import com.rdupuis.amikcal.Qty.Qty;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.relations.REL_TYP_CD;

public class Component_Reference extends Component_Generic {

    /**
     * Constructeur
     */

    public Component_Reference() {
        super();
    }

    public Component_Reference(Parcel parcel) {
        super(parcel);
    }


    public Component_Reference(EnergySource energySource, Qty qty) {
        super();
        this.setEnergy(energySource);
        this.setQty(qty);
    }


    @Override
    public REL_TYP_CD getRelationClass() {
        return REL_TYP_CD.NRJ_REF_INTRNL;
    }


    public static final Parcelable.Creator<Component_Reference> CREATOR = new Parcelable.Creator<Component_Reference>() {
        @Override
        public Component_Reference createFromParcel(Parcel in) {
            return new Component_Reference(in);
        }

        @Override
        public Component_Reference[] newArray(int size) {
            return new Component_Reference[size];

        }

    };


}
