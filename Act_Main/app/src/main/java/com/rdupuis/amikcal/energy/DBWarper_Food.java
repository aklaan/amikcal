package com.rdupuis.amikcal.energy;

import android.content.ContentValues;

import com.rdupuis.amikcal.commons.AppConsts.NRJ_CLASS_MAP;
import com.rdupuis.amikcal.commons.AppConsts.STRUCTURE_CD_MAP;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.data.ContentDescriptorObj;

public class DBWarper_Food extends DBWarper_Energy {

    public DBWarper_Food(Food food) {
	super(food);

    };

    public ContentValues getContentValues() {
	ContentValues val = super.getContentValues();

	
	// alimenter la structure
	STRUCTURE_CD_MAP stucture_map = new STRUCTURE_CD_MAP();
	val.put(ContentDescriptorObj.TB_Energies.Columns.STRUCTURE,
		stucture_map._out.get(((Food) this.mEnergySource).getStructure()));

	return val;
    }

}
