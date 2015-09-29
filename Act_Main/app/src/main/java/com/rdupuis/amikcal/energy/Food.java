package com.rdupuis.amikcal.energy;


import android.os.Parcel;
import android.os.Parcelable;

import com.rdupuis.amikcal.Qty.Qty;
import com.rdupuis.amikcal.commons.AppConsts;

/**
 * <h1>Food : un aliment<h1>
 * <p/>
 * <p>
 * exemple : du pain
 * </p>
 *
 * @author Rodolphe
 */

public class Food extends ContreteEnergySource implements HasBodyEffect {

    private STRUCTURE mStructure;

    public Food() {
        super();
        this.setName("");
        this.setQtyReference(new Qty());
        this.setStructure(STRUCTURE.UNDEFINED);
    }

    public STRUCTURE getStructure() {
        return mStructure;
    }

    public void setStructure(STRUCTURE mStructure) {
        this.mStructure = mStructure;
    }

    @Override
    public NRJ_CLASS getEnergyClass() {
        return NRJ_CLASS.FOOD;
    }

    @Override
    public NRJ_EFFECT getEffect() {
        // TODO Auto-generated method stub
        return NRJ_EFFECT.EARN;
    }

    public Food(Parcel parcel) {
        this.setDatabaseId(parcel.readLong());
        this.setName(parcel.readString());

        AppConsts.STRUCTURE_CD_MAP map = new AppConsts.STRUCTURE_CD_MAP();
        this.setStructure(map._in.get(parcel.readString()));
        this.setQtyReference((Qty) parcel.readParcelable(Qty.class.getClassLoader()));

        //faire le reste des data
    }


    public static final Parcelable.Creator<Food> CREATOR = new Parcelable.Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {

            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];

        }


    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.getDatabaseId());
        dest.writeString(this.getName());
        AppConsts.STRUCTURE_CD_MAP map = new AppConsts.STRUCTURE_CD_MAP();
        dest.writeString(map._out.get(this.getStructure()));
        dest.writeParcelable(this.getQtyReference(), 0);

        //ajouter le reste
    }


}
