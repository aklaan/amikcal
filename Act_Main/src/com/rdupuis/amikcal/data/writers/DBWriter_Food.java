package com.rdupuis.amikcal.data.writers;

import com.rdupuis.amikcal.energy.Food;

import android.content.ContentResolver;

public class DBWriter_Food extends DBWriter_EnergySource {

    public DBWriter_Food(ContentResolver contentResolver, Food food) {
	super(contentResolver,food);
	
    }

    
}
