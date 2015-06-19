package com.rdupuis.amikcal.commons;

import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.energy.Food;

import android.content.ContentResolver;

public class DBWriter_Food extends DBWriter_EnergySource {

    public DBWriter_Food(ContentResolver contentResolver) {
	super(contentResolver);
	// TODO Auto-generated constructor stub
    }

    

    public EnergySource Save(Food food) {

	return (Food) super.Save(food);

    }

}
