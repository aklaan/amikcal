package com.rdupuis.amikcal.energy;

import android.content.ContentValues;

import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.commons.AppConsts.NRJ_CLASS_MAP;
import com.rdupuis.amikcal.data.ContentDescriptorObj;

public class EnergyDBWarper extends DBWarper {
    public EnergySource mEnergySource;

    public EnergyDBWarper(EnergySource energySource) {
	this.mEnergySource = energySource;
    };

    public ContentValues getContentValues() {
	ContentValues val = new ContentValues();

	// Alimentation du nom 
	val.put(ContentDescriptorObj.TB_Energies.Columns.NAME, this.mEnergySource.getName());

	// Alimentation de la classe
	NRJ_CLASS_MAP class_map = new NRJ_CLASS_MAP();
	val.put(ContentDescriptorObj.TB_Energies.Columns.CLASS, class_map._out.get(this.mEnergySource.getEnergyClass()));

	// date de mise à jour
	val.put(ContentDescriptorObj.TB_Energies.Columns.LAST_UPDATE, ToolBox.getCurrentTimestamp());

	return val;
    }

}
