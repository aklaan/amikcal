package com.rdupuis.amikcal.components;

import java.util.ArrayList;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.Qty;
import com.rdupuis.amikcal.energy.Energy;
import com.rdupuis.amikcal.relations.I_Relation;
import com.rdupuis.amikcal.relations.REL_TYP_CD;

/**
 * Un composant = une relation entre une quantité et une source d'énergie.
 * 
 * par exemple :
 * 
 * 150 grammes de pommes
 * 
 * 
 * @author Rodolphe
 * 
 */
public  class Component_Commons extends Component {

    long id;
    Qty mQty;
    Energy mEnergy;
    ArrayList<? extends Component> mEquivalences;
    
    public Component_Commons() {
	this.setId(AppConsts.NO_ID);
	this.setQty(new Qty());
	
    }

    public  void setEnergy(Energy energy) {
	this.mEnergy = energy;
    }

    public Energy getEnergy() {
	return mEnergy;
    }

    @Override
    public long getId() {
	return id;
    }

    @Override
    public void setId(long id) {
	this.id = id;
    }

    public Qty getQty() {
	return mQty;
    }

    public void setQty(Qty mQty) {
	this.mQty = mQty;
    }

    public REL_TYP_CD getRelationClass() {
	return REL_TYP_CD.UNDEFINED;
    }

        @Override
    public String getParty1() {
	return String.valueOf(this.mEnergy.getId());
    }

    @Override
    public String getParty2() {

	return String.valueOf(this.mQty.getId());
    }

   
}
