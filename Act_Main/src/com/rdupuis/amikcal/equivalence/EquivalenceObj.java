package com.rdupuis.amikcal.equivalence;

import com.rdupuis.amikcal.energy.EnergyObj;
import com.rdupuis.amikcal.unitofmeasure.UnitOfMeasureObj;

public class EquivalenceObj {

	private long            id;
	public EnergyObj        energy;
	public UnitOfMeasureObj unitIn;
	public UnitOfMeasureObj unitOut;
    private float            quantity;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public EquivalenceObj(){
	energy = new EnergyObj();
	unitIn = new UnitOfMeasureObj();
	unitOut = new UnitOfMeasureObj();
	
	
	}
	
	
}
