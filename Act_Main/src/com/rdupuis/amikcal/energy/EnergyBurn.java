package com.rdupuis.amikcal.energy;

/**
 * <h1>EnergySource : une source d'énergie<h1>
 * 
 * <p>
 * une source d'énergie : - possède un nom - peut être représenté par une
 * quantité de calories - cette enérgie peu être absorbé (manger)ou dépensée
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
