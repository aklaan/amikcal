package com.rdupuis.amikcal.data;

import com.rdupuis.amikcal.data.writers.DBWriter;
import com.rdupuis.amikcal.energy.DBWarper;

import android.content.ContentResolver;

/**
 * un objet "savable" est un objet qu ipeu être sauvegardé dans la base de donnée.
 * cet objet doit donc spécifier un Warper qui va permettre d'alimenter un ContentValue
 * et un Writer qui va permettre d'écrire les informations en base
 */
public interface Savable {
    
    public long getDatabaseId();
    public void setDatabaseId(long parseLong);

    public DBWarper getDBWarper();
    public DBWriter getDBWriter(ContentResolver contentResolver);

}
