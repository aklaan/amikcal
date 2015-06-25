package com.rdupuis.amikcal.data.writers;

import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.relations.I_Relation;

import android.content.ContentResolver;

public class DBWriter_Relation extends DBWriter_Generic {
    

    public DBWriter_Relation(ContentResolver contentResolver,I_Relation relation) {
	super(contentResolver, relation);
	this.setUriInsert(ContentDescriptorObj.TB_Party_rel.INS000_PARTY_REL_URI);
	this.setUriUpdate(ContentDescriptorObj.TB_Party_rel.UP000_PARTY_REL_URI);
	  }

    public I_Relation getRelation(){
	return (I_Relation) this.getSavable();
    }
    
  
}
