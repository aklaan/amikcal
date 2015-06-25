package com.rdupuis.amikcal.data.writers;

import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.energy.EnergySource;

import android.content.ContentResolver;

public class DBWriter_Component extends DBWriter_Relation {

	public DBWriter_Component(ContentResolver contentResolver,
			Component component) {
		super(contentResolver, component);
		
	}

	public Component getComponent() {
		return (Component) this.getSavable();
	}

	@Override
	public void Save() {
		// sauver un composant, c'est sauver la relation Qty associée puis la
		// relation "Compoant" elle même.
		this.getComponent().getQty().getDBWriter(this.getContentResolver())
				.Save();
		super.Save();
	}
}
