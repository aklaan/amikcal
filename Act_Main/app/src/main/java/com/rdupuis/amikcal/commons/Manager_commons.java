package com.rdupuis.amikcal.commons;

import android.app.Activity;
import android.content.ContentValues;

/**
 * Created by rodol on 01/09/2015.
 */
public class Manager_commons extends Manager {
    Activity mActivity;
    ManagedElement mElement;

    public Manager_commons(Activity activity, ManagedElement element) {
        this.setActivity(activity);
        this.setElement(element);
    }


    @Override
    public Activity getActivity() {
        return this.mActivity;
    }

    @Override
    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public ManagedElement getElement() {

        return this.mElement;
    }


    @Override
    public void setElement(ManagedElement element) {
    this.mElement = element;
    }


    @Override
    public long save() {
        return 0;
    }


    @Override
    public void edit() {
    }


    @Override
    public ManagedElement load(long databaseId) {

        return null;
    }


    @Override
    public ContentValues getContentValues() {
        return null;
    }

}
