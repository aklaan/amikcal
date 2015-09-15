package com.rdupuis.amikcal.commons;

import android.app.Activity;
import android.content.ContentValues;

/**
 * Created by rodol on 01/09/2015.
 */
public class Manager_commons extends Manager {
    Activity mActivity;
    ManagedElement mElement;

    public Manager_commons(Activity activity) {
        this.setActivity(activity);

    }


    @Override
    public Activity getActivity() {
        return this.mActivity;
    }

    @Override
    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }

/**    @Override
    public ManagedElement getElement() {

        return this.mElement;
    }


    @Override
    public void setElement(ManagedElement element) {
    this.mElement = element;
    }
*/

    @Override
    public long save(ManagedElement element) {

        return 0;
    }


    @Override
    public void edit(ManagedElement element) {
    }


    @Override
    public ManagedElement load(long databaseId) {

        return null;
    }


    @Override
    public ContentValues getContentValues(ManagedElement element) {
        return null;
    }

}
