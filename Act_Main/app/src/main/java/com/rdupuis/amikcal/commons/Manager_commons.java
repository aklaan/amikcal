package com.rdupuis.amikcal.commons;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;

/**
 * Created by rodol on 01/09/2015.
 */
public class Manager_commons extends Manager {
    Activity mActivity;
    Uri mUriUpdate;
    Uri mUriInsert;
    ManagedElement mElement;

    public Manager_commons(Activity activity) {
        this.setActivity(activity);


    }

    @Override
    public Uri getUriUpdate() {
        return this.mUriUpdate;
    }

    @Override
    public Uri getUriInsert() {
        return this.mUriInsert;
    }

    @Override
    public void setUriUpdate(Uri uriUpdate) {
        this.mUriUpdate = uriUpdate;
    }

    @Override
    public void setUriInsert(Uri uriInsert) {
        this.mUriInsert = uriInsert;

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
    public long save(ManagedElement element) {


        long _id = element.getDatabaseId();
        ContentValues values = this.getContentValues(element);

        //On tente de faire un Update, mais si ça ne fonctionne pas, on fait un insert
        try {
            Uri uriUpdate = ContentUris.withAppendedId(this.getUriUpdate(), _id);
            this.getActivity().getContentResolver().update(uriUpdate, values, String.valueOf(_id), null);
        } catch (Exception e) {
            Uri uriInsert = this.getActivity().getContentResolver().insert(this.getUriInsert(), values);
            //on récupère l'identifiant de l'objet dans la DB
            _id = Long.parseLong(uriInsert.getLastPathSegment());
        }

        return _id;
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
