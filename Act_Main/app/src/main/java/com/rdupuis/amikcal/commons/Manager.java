package com.rdupuis.amikcal.commons;

import android.app.Activity;
import android.content.ContentValues;



/**
 * Created by rodol on 01/09/2015.
 */
public abstract class Manager {


    public abstract void setActivity(Activity activity);

    public abstract Activity getActivity();

    public abstract ManagedElement getElement();

    public abstract void setElement(ManagedElement element);

    public abstract long save();

    public abstract void edit();

    public abstract ManagedElement load(long databaseId);

    public abstract ContentValues getContentValues();

}
