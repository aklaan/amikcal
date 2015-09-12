package com.rdupuis.amikcal.data.writers;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.data.Savable;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;

/**
 * Writer générique
 */
public class DBWriter_Generic extends DBWriter {
    private Uri mUriUpdate;
    private Uri mUriInsert;
    private ContentResolver mContentResolver;
    private Savable mSavable;

    /**
     * on fait le lien entre un objeht que l'on souhaite sauver et
     * le contentresolver qui va switcher sur les ordres SQL adéquat
     *
     * @param contentResolver
     * @param savable
     */
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

    }


    /**
     * pour sauver un objet, on va soit l'insérer s'il n'existe pas (id inconnu)
     * ou bien le mettre à jour.
     */
    public long save() {

        //on récupère les données à sauvegarder dans un ContentValue grace au Warper
        ContentValues values = getSavable().getDBWarper().getContentValues();

        if (getSavable().getDatabaseId() == AppConsts.NO_ID) {
            Uri uriInsert = this.getContentResolver().insert(this.getUriInsert(), values);
            //on met à jour le DatabseId
            getSavable().setDatabaseId(Long.parseLong(uriInsert.getLastPathSegment()));
        } else {

            Uri uriUpdate = ContentUris.withAppendedId(this.getUriUpdate(), getSavable().getDatabaseId());
            this.getContentResolver().update(uriUpdate, values, String.valueOf(getSavable().getDatabaseId()), null);
        }

        return 0;
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
