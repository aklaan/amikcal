package com.rdupuis.amikcal.Equivalence;

import com.rdupuis.amikcal.Energy.EnergyObj;
import com.rdupuis.amikcal.UnitOfMeasure.UnitOfMeasureObj;

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
