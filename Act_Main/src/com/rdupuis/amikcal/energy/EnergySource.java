package com.rdupuis.amikcal.energy;

/**
 * une source d'�nergie
 *  - a un nom
 *  - peut �tre repr�sent� par une quantit� de calories
 *  - cette en�rgie peu �tre absorb� ou d�pens�e
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
		// Si la source d'�nergie est n�gative (ex: on brule des colories)
		// on retourne un montant de calories n�gatif
		if (this.getEgergyType() == EnergyType.NEGATIVE){
			return nbCal * -1;
		} else 
		return nbCal;
	}

	public void setNbCalories(float nbCal) {
		//On s'assure que la valeur energ�tique soit toujours positive
		this.nbCal = (nbCal >0)? nbCal : (nbCal-1);
	}
}
