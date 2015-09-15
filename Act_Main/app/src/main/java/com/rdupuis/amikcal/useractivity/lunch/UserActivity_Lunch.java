package com.rdupuis.amikcal.useractivity.lunch;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.useractivity.UA_CLASS_CD;
import com.rdupuis.amikcal.useractivity.UserActivity_Commons;

public class UserActivity_Lunch extends UserActivity_Commons {

    private LUNCH_CATEGORY mTypeOfLunch;


    public UserActivity_Lunch() {
        super();
        this.setTypeOfLunch(LUNCH_CATEGORY.UNDEFINED);

    }

    public UserActivity_Lunch(Calendar day) {
        super(day);

    }


    public UserActivity_Lunch(Parcel parcel) {
        super(parcel);


    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        // ce mapping est superflu compte tenu qu'il est redoncant avec l'objet en lui même
        // mapping du type de l'activitée
        //   AppConsts.UA_CLASS_CD_MAP ua_class_cd_map = new AppConsts.UA_CLASS_CD_MAP();
        //   dest.writeString(ua_class_cd_map._out.get(this.getActivityType()));

        //Mapping du typ de repas
        //ca n'existe pas encore, le type de repas est dans le title !!!

    }


    public static final Parcelable.Creator<UserActivity_Lunch> CREATOR = new Parcelable.Creator<UserActivity_Lunch>() {
        @Override
        public UserActivity_Lunch createFromParcel(Parcel in) {
            return new UserActivity_Lunch(in);
        }

        @Override
        public UserActivity_Lunch[] newArray(int size) {
            return new UserActivity_Lunch[size];

        }

    };


    @Override
    public UA_CLASS_CD getActivityType() {
        return UA_CLASS_CD.LUNCH;
    }

    public LUNCH_CATEGORY getTypeOfLunch() {
        return mTypeOfLunch;
    }

    public void setTypeOfLunch(LUNCH_CATEGORY mTypeOfLunch) {
        this.mTypeOfLunch = mTypeOfLunch;
    }


}
