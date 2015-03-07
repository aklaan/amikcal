package com.rdupuis.amikcal.energy;

import java.util.ArrayList;

import com.rdupuis.amikcal.commons.HasEquivalences;
import com.rdupuis.amikcal.commons.HasQtyReference;
import com.rdupuis.amikcal.commons.Qty;

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

public class EnergySource implements HasEquivalences {

	public static enum NRJ_EFFECT {
		GIVE, BURN
	}

	private long id;
	private String name;
	private NRJ_EFFECT mEffect;
	private Qty mQty_reference; // quantit� de r�f�rence
	private ArrayList<Qty> equivalences;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public NRJ_EFFECT getEffect() {
		return mEffect;
	}

	public void setEffect(NRJ_EFFECT effect) {
		this.mEffect = effect;
	}

	public void setQtyReference(Qty qty) {
		this.mQty_reference = qty;
	}

	public Qty getQtyReference() {
		return mQty_reference;
	}

	public ArrayList<Qty> getEquivalences() {
		return equivalences;
	}

	public void setEquivalences(ArrayList<Qty> equivalences) {
		this.equivalences = equivalences;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}