package com.rdupuis.amikcal.energy;

import java.util.ArrayList;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.HasEquivalences;
import com.rdupuis.amikcal.commons.Qty;
import com.rdupuis.amikcal.equivalence.Equivalence;

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

public class EnergySource  {

	public static enum NRJ_EFFECT {
		EARN, BURN,UNDEFINED
	}

	public static enum STRUCTURE {
		SOLID, LIQUID,POWDER,UNDEFINED
	}
	private long id;
	private String name;
	private NRJ_EFFECT mEffect;
	private Qty mQty_reference; // quantité de référence
	private ArrayList<Equivalence> equivalences;
	private STRUCTURE mStructure;
	
	public EnergySource(){
		id = AppConsts.NO_ID;
		name="";
		mEffect = NRJ_EFFECT.UNDEFINED;
		mQty_reference = new Qty();
		equivalences = new ArrayList<Equivalence>();
		setStructure(STRUCTURE.UNDEFINED);
	}
	
	
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
		this.mEffect = (effect !=null)?effect:NRJ_EFFECT.UNDEFINED;
	}

	public void setQtyReference(Qty qty) {
		this.mQty_reference = qty;
	}

	public Qty getQtyReference() {
		return mQty_reference;
	}

	public ArrayList<Equivalence> getEquivalences() {
		return equivalences;
	}

	public void setEquivalences(ArrayList<Equivalence> equivalences2) {
		this.equivalences = equivalences2;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public STRUCTURE getStructure() {
		return mStructure;
	}


	public void setStructure(STRUCTURE mStructure) {
		
		    
	    this.mStructure = (mStructure !=null)?mStructure:STRUCTURE.UNDEFINED;
	}

}
