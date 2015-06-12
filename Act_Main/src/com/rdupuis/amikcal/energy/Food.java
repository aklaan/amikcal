package com.rdupuis.amikcal.energy;

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

public class Food extends EnergyPositive {
    public static enum STRUCTURE {
	SOLID, LIQUID, POWDER, UNDEFINED
    }

    private STRUCTURE mStructure;
        
    public Food() {
	super();
    }
   
    public STRUCTURE getStructure() {
	return mStructure;
    }

    public void setStructure(STRUCTURE mStructure) {

	this.mStructure = (mStructure != null) ? mStructure : STRUCTURE.UNDEFINED;
    }

   }
