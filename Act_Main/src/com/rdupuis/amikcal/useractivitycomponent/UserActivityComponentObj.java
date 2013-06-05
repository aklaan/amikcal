package com.rdupuis.amikcal.useractivitycomponent;

import com.rdupuis.amikcal.energy.AbstractEnergy;
import com.rdupuis.amikcal.energy.EnergyObj;
import com.rdupuis.amikcal.unitofmeasure.UnitOfMeasureObj;

public class UserActivityComponentObj extends AbstractEnergy  {

	private long id;
	private long parentId;
	private EnergyObj energy;
	private float quantity;
	private UnitOfMeasureObj unitMeasure;
	
	
	public void setEnergyObj(EnergyObj energyObj) {
		this.energy = energyObj;
	}
	public EnergyObj getEnergyObj() {
		return energy;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setUnitMeasure(UnitOfMeasureObj unitMeasure) {
		this.unitMeasure = unitMeasure;
	}
	public UnitOfMeasureObj getUnitMeasure() {
		return unitMeasure;
	}

	/**
	 * Constructeur
	 */
	public UserActivityComponentObj(){
		energy = new EnergyObj();
		quantity=0f;
		unitMeasure= new UnitOfMeasureObj();
		
	}
	

	public long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
