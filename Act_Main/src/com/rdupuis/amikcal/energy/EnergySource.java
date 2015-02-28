package com.rdupuis.amikcal.energy;

import java.util.ArrayList;

import com.rdupuis.amikcal.commons.Qty;

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

public class EnergySource {

	public static enum NRJ_EFFECT {
		GIVE, BURN
	}

	private long id;
	private String name;
	private NRJ_EFFECT mEffect;
	private Qty mQty;
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

	public Qty getQtyRef() {
		return mQty;
	}

	public void setQtyRef(Qty qty) {
		this.mQty = qty;
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

	public Qty getQtyReference() {
	
		Qty result = new Qty();
		return result;
	}

}
