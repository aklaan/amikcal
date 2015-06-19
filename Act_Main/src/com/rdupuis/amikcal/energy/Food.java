package com.rdupuis.amikcal.energy;

import com.rdupuis.amikcal.components.Component;

/**
 * <h1>Food : un aliment<h1>
 * 
 * <p>
 * exemple : du pain
 * </p>
 * 
 * @author Rodolphe
 * 
 */

public class Food extends ContreteEnergySource implements HasBodyEffect {

    private STRUCTURE mStructure;

    public Food() {
	super();
    }

    public STRUCTURE getStructure() {
	return mStructure;
    }

    public void setStructure(STRUCTURE mStructure) {
	this.mStructure = mStructure;
    }

    @Override
    public void setReferenceComponent(Component refComponent) {
	// TODO Auto-generated method stub

    }

    @Override
    public NRJ_CLASS getEnergyClass() {
	return NRJ_CLASS.FOOD;
    }

    @Override
    public NRJ_EFFECT getEffect() {
	// TODO Auto-generated method stub
	return NRJ_EFFECT.EARN;
    }

    public DBWarper getDBWarper(){
	return new FoodDBWarper(this);
    }
}
