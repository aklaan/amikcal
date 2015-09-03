package com.rdupuis.amikcal.commons;

import android.app.Activity;

import java.util.Objects;

/**
 * Created by rodol on 01/09/2015.
 */
public  class Manager {
    private Activity mActivity;

    public Manager(Activity activity){
        setActivity(activity);
    }

    public  void setActivity(Activity activity) {mActivity = activity;
    }

    public  Activity getActivity() {
        return mActivity;
    }



}
