package com.rdupuis.amikcal.commons;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;

public class DBWriter_Generic extends DBWriter {
    private Uri mUriUpdate;
    private Uri mUriInsert;
    private ContentResolver mContentResolver;

    public DBWriter_Generic() {
	this.mContentResolver = null;
	this.mUriInsert = null;
	this.mUriUpdate = null;
    }
    
    
    
    

    public Uri getUriUpdate() {
	return this.mUriUpdate;

    }

    public Uri getUriInsert() {
	return this.mUriInsert;
    }

    public void setUriUpdate(Uri uriUpdate) {
	this.mUriUpdate = uriUpdate;
    }

    public void setUriInsert(Uri uriInsert) {
	this.mUriInsert = uriInsert;

    };

    public Savable Save(Savable savable) {

	ContentValues values = savable.getDBWarper().getContentValues();

	if (savable.getId() == AppConsts.NO_ID) {
	    Uri uriInsert = this.mContentResolver.insert(this.getUriInsert(), values);
	    savable.setId(Long.parseLong(uriInsert.getLastPathSegment()));
	} else {

	    Uri uriUpdate = ContentUris.withAppendedId(this.getUriUpdate(), savable.getId());
	    this.mContentResolver.update(uriUpdate, values, String.valueOf(savable.getId()), null);
	}

	return savable;

    }

    @Override
    public ContentResolver getContentResolver() {
	return this.mContentResolver;
    }

    @Override
    public void setContentResolver(ContentResolver contentResolver) {
	this.mContentResolver = contentResolver;

    }

}
