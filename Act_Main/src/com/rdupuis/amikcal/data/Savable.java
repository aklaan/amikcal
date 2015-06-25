package com.rdupuis.amikcal.data;

import com.rdupuis.amikcal.data.writers.DBWriter;
import com.rdupuis.amikcal.energy.DBWarper;

import android.content.ContentResolver;

public interface Savable {
    
    public long getId();

    public void setId(long parseLong);

    public DBWarper getDBWarper();
    public DBWriter getDBWriter(ContentResolver contentResolver);

}
