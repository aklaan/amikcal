package com.rdupuis.amikcal.unity;

import android.os.Parcel;
import android.os.Parcelable;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ManagedElement;

public class Unity implements ManagedElement, Parcelable {
    public static enum UNIT_CLASS {
        INTERNATIONAL, CUSTOM, CONTAINER, FOOD_COMPONENT, TIME, UNDEFINED
    }

    private long id;
    private String longName;
    private String shortName;
    private UNIT_CLASS mUnityClass;

    public Unity() {
        this.id = AppConsts.NO_ID;
        this.longName = "";
        this.shortName = "";
        this.mUnityClass = UNIT_CLASS.UNDEFINED;

    }

    @Override
    public long getDatabaseId() {
        return id;
    }

    public void setDatabaseId(Long id) {
        this.id = id;
    }

    public String getLongName() {
        return this.longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public UNIT_CLASS getUnityClass() {
        return mUnityClass;
    }

    public void setUnityClass(UNIT_CLASS mClass) {
        this.mUnityClass = mClass;
    }


    public Unity(Parcel parcel) {
        this.id = parcel.readLong();
        this.longName = parcel.readString();
        this.shortName = parcel.readString();

        AppConsts.UNIT_CLASS_MAP map = new AppConsts.UNIT_CLASS_MAP();
        this.mUnityClass = map._in.get(parcel.readString());
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.getDatabaseId());
        dest.writeString(this.getLongName());
        dest.writeString(this.getShortName());

        AppConsts.UNIT_CLASS_MAP map = new AppConsts.UNIT_CLASS_MAP();
        dest.writeString(map._out.get(this.getUnityClass()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Unity> CREATOR = new Parcelable.Creator<Unity>() {
        @Override
        public Unity createFromParcel(Parcel in) {
            return new Unity(in);
        }

        @Override
        public Unity[] newArray(int size) {
            return new Unity[size];

        }

    };


}
