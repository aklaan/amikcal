package com.rdupuis.amikcal.energy;

import com.rdupuis.amikcal.components.Component;

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

public class PhysicalActivity extends AbstractEnergySource implements HasBodyEffect {
    
    public PhysicalActivity() {
	super();
    }

    
    @Override
    public void setReferenceComponent(Component refComponent) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public NRJ_EFFECT getEffect() {
	return NRJ_EFFECT.BURN;
}
    


   }
