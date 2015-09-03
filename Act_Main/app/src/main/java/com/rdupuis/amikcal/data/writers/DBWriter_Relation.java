package com.rdupuis.amikcal.data.writers;

import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.relations.Relation;

import android.content.ContentResolver;

public class DBWriter_Relation extends DBWriter_Generic {
    

    public DBWriter_Relation(ContentResolver contentResolver,Relation relation) {
	super(contentResolver, relation);
	this.setUriInsert(ContentDescriptorObj.TB_Party_rel.INS000_PARTY_REL_URI);
	this.setUriUpdate(ContentDescriptorObj.TB_Party_rel.UP000_PARTY_REL_URI);
	  }

    public Relation getRelation(){
	return (Relation) this.getSavable();
    }
    
  
}
