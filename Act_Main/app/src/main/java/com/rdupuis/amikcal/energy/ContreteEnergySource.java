package com.rdupuis.amikcal.energy;


public abstract  class ContreteEnergySource extends EnergySource {

	public static enum STRUCTURE {
		SOLID, LIQUID, POWDER, UNDEFINED
	}
    
    public abstract STRUCTURE getStructure();

public abstract void setStructure(STRUCTURE mStructure) ;
}
