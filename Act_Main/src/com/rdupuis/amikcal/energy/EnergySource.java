package com.rdupuis.amikcal.energy;

/**
 * une source d'énergie
 *  - a un nom
 *  - peut être représenté par une quantité de calories
 *  - cette enérgie peu être absorbé ou dépensée
 * 
 * @author Rodolphe
 *
 */

public class EnergySource {
	private String name;
	public enum EnergyType {
		POSITIVE, NEGATIVE
		}
	private EnergyType mType;
	private float nbCal;
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EnergyType getEgergyType() {
		return mType;
	}

	public void setEnergyType(EnergyType mType) {
		this.mType = mType;
	}

	public float getNbCalories() {
		// Si la source d'énergie est négative (ex: on brule des colories)
		// on retourne un montant de calories négatif
		if (this.getEgergyType() == EnergyType.NEGATIVE){
			return nbCal * -1;
		} else 
		return nbCal;
	}

	public void setNbCalories(float nbCal) {
		//On s'assure que la valeur energétique soit toujours positive
		this.nbCal = (nbCal >0)? nbCal : (nbCal-1);
	}
}
