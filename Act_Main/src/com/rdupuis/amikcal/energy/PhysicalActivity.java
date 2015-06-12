package com.rdupuis.amikcal.energy;

import java.util.ArrayList;

import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.equivalence.i_CanHaveEquivalences;

/**
 * <h1>PhysicalActivity : une activité physique<h1>
 * 
 * <p>
 * exemple : courrir 
 * </p>
 * 
 * @author Rodolphe
 *
 */

public class PhysicalActivity extends EnergyNegative implements i_CanHaveEquivalences{
    
    public PhysicalActivity() {
	super();
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
