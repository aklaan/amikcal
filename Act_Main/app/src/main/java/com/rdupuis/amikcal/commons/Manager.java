package com.rdupuis.amikcal.commons;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;


/**
 * Created by rodol on 01/09/2015.
 */
public abstract class Manager {


    public abstract void setActivity(Activity activity);

    public abstract Activity getActivity();

    //public abstract ManagedElement getElement();

   // public abstract void setElement(ManagedElement element);

    public abstract long save(ManagedElement element);
    public abstract boolean checkBeforeWriting(ManagedElement element);
    public abstract void edit(ManagedElement element);

    public abstract ManagedElement load(long databaseId);

    public abstract ContentValues getContentValues(ManagedElement element);

    public abstract Uri getUriUpdate();

    public abstract Uri getUriInsert() ;

    public abstract void setUriUpdate(Uri uriUpdate);

    public abstract void setUriInsert(Uri uriInsert) ;

    public abstract void setReturnCode(RETURNCODE returnCode) ;
    public abstract RETURNCODE getReturnCode() ;

}
