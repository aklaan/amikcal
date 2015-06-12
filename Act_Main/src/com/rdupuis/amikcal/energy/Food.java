package com.rdupuis.amikcal.energy;

import java.util.ArrayList;

import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.equivalence.i_CanHaveEquivalences;

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

public class Food extends EnergyPositive implements i_CanHaveEquivalences {
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

    @Override
    public void setEquivalences(ArrayList<? extends Component> mEquivalences) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void setReferenceComponent(Component refComponent) {
	// TODO Auto-generated method stub
	
    }

   }
