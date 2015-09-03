package com.rdupuis.amikcal.Qty;

import com.rdupuis.amikcal.Qty.Qty;
import com.rdupuis.amikcal.data.writers.DBWriter_Relation;

import android.content.ContentResolver;

public class DBWriter_Qty extends DBWriter_Relation {

	public DBWriter_Qty(ContentResolver contentResolver, Qty qty) {
		super(contentResolver, qty);
		}

	public Qty getQty() {
		return (Qty) this.getSavable();
	}


}
