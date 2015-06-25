package com.rdupuis.amikcal.commons;

import com.rdupuis.amikcal.energy.DBWarper;

import android.content.ContentResolver;

public interface Savable {
    
    public long getId();

    public void setId(long parseLong);

    public DBWarper getDBWarper();
    public DBWriter getDBWriter(ContentResolver contentResolver);

}
