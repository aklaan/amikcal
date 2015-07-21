package com.rdupuis.amikcal.data.writers;

import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.energy.EnergySource;

import android.content.ContentResolver;

public class DBWriter_EnergySource extends DBWriter_Generic {

    public DBWriter_EnergySource(ContentResolver contentResolver, EnergySource energySource) {
	super(contentResolver, energySource);
	this.setUriInsert(ContentDescriptorObj.TB_Energies.INSERT_ENERGY_URI);
	this.setUriUpdate(ContentDescriptorObj.TB_Energies.UPDATE_ENERGY_ID_URI);
    }

    public EnergySource getEnergySource() {
	return (EnergySource) this.getSavable();
    }

    @Override
    public void Save() {
	
	//sauver une énergie, c'est :
	//1 - sauver l'énergie
	super.Save();
	//2 - sauver le composant de référence
	this.getEnergySource().getComponentReference().getDBWriter(this.getContentResolver()).Save();
    }

}
