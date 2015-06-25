package com.rdupuis.amikcal.data.writers;

import com.rdupuis.amikcal.commons.Qty;
import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.energy.EnergySource;

import android.content.ContentResolver;

public class DBWriter_Qty extends DBWriter_Relation {

	public DBWriter_Qty(ContentResolver contentResolver, Qty qty) {
		super(contentResolver, qty);
		}

	public Qty getQty() {
		return (Qty) this.getSavable();
	}


}
