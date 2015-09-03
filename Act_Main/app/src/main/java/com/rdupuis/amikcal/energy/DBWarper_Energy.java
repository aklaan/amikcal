package com.rdupuis.amikcal.energy;

import android.content.ContentValues;

import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.commons.AppConsts.NRJ_CLASS_MAP;
import com.rdupuis.amikcal.data.ContentDescriptorObj;


/**
 * le DBWarper_Energy est un composant qui permet d'alimenter un ContentValues
 * avec les informations communes à toutes les énergies
 */

public class DBWarper_Energy extends DBWarper {
    public EnergySource mEnergySource;

    public DBWarper_Energy(EnergySource energySource) {
	this.mEnergySource = energySource;
    };

    public ContentValues getContentValues() {
	ContentValues val = new ContentValues();

	// Alimentation du nom 
	val.put(ContentDescriptorObj.TB_Energies.Columns.NAME, this.mEnergySource.getName());

	// Alimentation de la classe
	NRJ_CLASS_MAP class_map = new NRJ_CLASS_MAP();
	val.put(ContentDescriptorObj.TB_Energies.Columns.CLASS, class_map._out.get(this.mEnergySource.getEnergyClass()));

	// date de mise � jour
	val.put(ContentDescriptorObj.TB_Energies.Columns.LAST_UPDATE, ToolBox.getCurrentTimestamp());

	return val;
    }

}
