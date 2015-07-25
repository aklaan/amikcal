package com.rdupuis.amikcal.relations;

import android.content.ContentValues;

import com.rdupuis.amikcal.commons.AppConsts.NRJ_CLASS_MAP;
import com.rdupuis.amikcal.commons.AppConsts.REL_TYP_CD_MAP;
import com.rdupuis.amikcal.commons.AppConsts.STRUCTURE_CD_MAP;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.energy.DBWarper;

public class DBWarper_Relation extends DBWarper {
private I_Relation mRelation;
	
    public DBWarper_Relation(I_Relation relation) {
	this.mRelation = relation;

    };

    public I_Relation getRelation(){
		return mRelation;
    	
    }
    
    public void setRelation(I_Relation relation){
		this.mRelation = relation;
    	
    }
    
    public ContentValues getContentValues() {
		// On prépare les informations à mettre à jour
		ContentValues values = new ContentValues();

		// Rel_typ_cd
		REL_TYP_CD_MAP rel_typ_cd_map = new REL_TYP_CD_MAP();
		values.put(ContentDescriptorObj.TB_Party_rel.Columns.REL_TYP_CD,
			rel_typ_cd_map._out.get(this.mRelation.getRelationClass()));

		// party 1
		values.put(ContentDescriptorObj.TB_Party_rel.Columns.PARTY_1, this.mRelation.getParty1());

		// Id unity
		values.put(ContentDescriptorObj.TB_Party_rel.Columns.PARTY_2, this.mRelation.getParty2());

		// date de mise à jour
		values.put(ContentDescriptorObj.TB_Party_rel.Columns.LAST_UPDATE, ToolBox.getCurrentTimestamp());

	
	return values;
    }

}
