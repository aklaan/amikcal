package com.rdupuis.amikcal.commons;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;

public class DBWriter_Generic extends DBWriter {
    private Uri mUriUpdate;
    private Uri mUriInsert;
    private ContentResolver mContentResolver;
    private Savable mSavable;

    public DBWriter_Generic(ContentResolver contentResolver, Savable savable) {
	this.setContentResolver(contentResolver);
	this.mUriInsert = null;
	this.mUriUpdate = null;
	this.setSavable(savable);
    }

    public Savable getSavable() {
	return this.mSavable;
    }

    public void setSavable(Savable savable) {
	this.mSavable = savable;
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

    public void Save() {

	ContentValues values = getSavable().getDBWarper().getContentValues();

	if (getSavable().getId() == AppConsts.NO_ID) {
	    Uri uriInsert = this.mContentResolver.insert(this.getUriInsert(), values);
	    getSavable().setId(Long.parseLong(uriInsert.getLastPathSegment()));
	} else {

	    Uri uriUpdate = ContentUris.withAppendedId(this.getUriUpdate(), getSavable().getId());
	    this.mContentResolver.update(uriUpdate, values, String.valueOf(getSavable().getId()), null);
	}

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
