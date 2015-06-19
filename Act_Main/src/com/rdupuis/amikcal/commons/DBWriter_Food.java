package com.rdupuis.amikcal.commons;

import com.rdupuis.amikcal.energy.EnergySource;

import android.content.ContentResolver;
import android.net.Uri;

public class DBWriter_Food extends DBWriter_EnergySource {

    public DBWriter_Food(ContentResolver contentResolver) {
	super(contentResolver);
	// TODO Auto-generated constructor stub
    }

    public DBWriter_EnergySource(Uri uriUpdate, Uri uriInsert, ContentResolver contentResolver) {
	super(uriUpdate, uriInsert, contentResolver);
	// TODO Auto-generated constructor stub
    }

    public EnergySource Save(EnergySource energySource) {

	return (EnergySource) super.Save(energySource);

    }

}
