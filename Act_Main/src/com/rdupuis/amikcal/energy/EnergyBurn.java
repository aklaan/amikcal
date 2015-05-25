package com.rdupuis.amikcal.energy;

/**
 * <h1>EnergySource : une source d'�nergie<h1>
 * 
 * <p>
 * une source d'�nergie : - poss�de un nom - peut �tre repr�sent� par une
 * quantit� de calories - cette en�rgie peu �tre absorb� (manger)ou d�pens�e
 * (courir)
 * </p>
 * 
 * @author Rodolphe
 *
 */

public class EnergyBurn extends EnergySource {
		
	public EnergyBurn(){
		super();
	}
	
	@Override
	public NRJ_EFFECT getEffect() {
		return NRJ_EFFECT.BURN;
	}

}
