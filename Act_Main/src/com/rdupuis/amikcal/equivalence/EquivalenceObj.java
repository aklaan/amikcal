package com.rdupuis.amikcal.equivalence;

import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.unity.Unity;

/**
 * Une �quivalence permet de transposer une source d'�nergie d'une unit�e de
 * mesure � une autre par exemple : 1 Verre de Vin = 46 kcal
 * 
 * ici la source d'�nergie c'est :vin 
 * l'unit� d'entr�e c'est le conteneur : "Verre"
 *  l'unit�e de sortie c'est
 * :kcal la quantit� de kcal pour
 * 1 verre est :46
 * 
 * La quantit� IN est toujours 1
 * 
 * @author Rodolphe
 *
 */
public class EquivalenceObj {

	private long id;
	private float quantity_IN = 1f;
	public Unity unit_IN;
	public EnergySource energySource;
	private float quantity_OUT;
	public Unity unit_OUT;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getQuantityOut() {
		return quantity_OUT;
	}

	public void setQuantityOut(float quantity) {
		this.quantity_OUT = quantity;
	}

	public EquivalenceObj() {
		this.energySource = new EnergySource();
		this.unit_IN = new Unity();
		this.unit_OUT = new Unity();
		this.setQuantityIn(1f);
		this.setQuantityOut(0);

	}

	public float getQuantityIn() {
		return quantity_IN;
	}

	public void setQuantityIn(float f) {
		this.quantity_IN = f;
	}

	public float ratio() {
		return this.quantity_OUT / this.quantity_IN;
	}
}
