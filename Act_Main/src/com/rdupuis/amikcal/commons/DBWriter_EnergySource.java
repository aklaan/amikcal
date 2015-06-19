package com.rdupuis.amikcal.commons;

import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.energy.EnergySource;

import android.content.ContentResolver;
import android.net.Uri;

public class DBWriter_EnergySource extends DBWriter_Generic {

    public DBWriter_EnergySource(ContentResolver contentResolver) {
	this.setUriInsert(ContentDescriptorObj.TB_Energies.INSERT_ENERGY_URI);
	this.setUriUpdate(ContentDescriptorObj.TB_Energies.UPDATE_ENERGY_ID_URI);
	this.setContentResolver(contentResolver);
	// TODO Auto-generated constructor stub
    }

    public EnergySource Save(EnergySource energySource) {

	return (EnergySource) super.Save(energySource);

    }

}
