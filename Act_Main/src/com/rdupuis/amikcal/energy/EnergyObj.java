package com.rdupuis.amikcal.energy;

import com.rdupuis.amikcal.unitofmeasure.UnitOfMeasureObj;

public class EnergyObj extends AbstractEnergy{

	private long id;
	private String name;
	private String familly;
	private String subCategory;
    private float quantityReference;
    public UnitOfMeasureObj unit;
    
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setFamilly(String familly) {
		this.familly = familly;
	}
	public String getFamilly() {
		return familly;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public String getSubCategory() {
		return subCategory;
	}
	
	public long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public float getQuantityReference() {
		return quantityReference;
	}
	public void setQuantityReference(float quantityReference) {
		this.quantityReference = quantityReference;
	}

	//* constructeur
	public EnergyObj() {
		this.unit = new UnitOfMeasureObj();
	}

	
	



}
