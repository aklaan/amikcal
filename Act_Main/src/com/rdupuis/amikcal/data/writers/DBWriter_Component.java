package com.rdupuis.amikcal.data.writers;

import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.energy.EnergySource;

import android.content.ContentResolver;

public class DBWriter_Component extends DBWriter_Relation {

    public DBWriter_Component(ContentResolver contentResolver, Component component) {
	super(contentResolver, component);

    }

    public Component getComponent() {
	return (Component) this.getSavable();
    }

    @Override
    public void Save() {
	// Sauver un composant, c'est :
	// 1 - sauver la relation Qty associ�e
	this.getComponent().getQty().getDBWriter(this.getContentResolver()).Save();
	// 2 - Sauver la relation composant elle m�me .
	super.Save();
	// 3 - Sauver les �quivalences du composants
//	if (this.getComponent().hasEquivalences()){
//	    for (Component equiv : (this.getComponent().getEquivalences())) 
//		equiv.getDBWriter(this.getContentResolver()).Save();
//	}
    }
}
