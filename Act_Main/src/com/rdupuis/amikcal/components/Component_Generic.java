package com.rdupuis.amikcal.components;

import java.util.ArrayList;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.Qty;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.equivalence.Equivalence;
import com.rdupuis.amikcal.relations.I_Relation;
import com.rdupuis.amikcal.relations.Relation.REL_TYP_CD;

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
public abstract class Component_Generic extends Component {

    EnergySource mEnergySource;
    long id;
    Qty mQty;
    ArrayList<Equivalence> mEquivalences;
    
    public Component_Generic() {
	this.setId(AppConsts.NO_ID);
	this.setEnergySource(new EnergySource());
	this.setQty(new Qty());
	
    }

    public  void setEnergySource(EnergySource energySource) {
	this.mEnergySource = energySource;
    }

    public EnergySource getEnergySource() {
	return mEnergySource;
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

    public ArrayList<Equivalence> getEquivalences() {
	return mEquivalences;
    }

    public void setEquivalences(ArrayList<Equivalence> mEquivalences) {
	this.mEquivalences = mEquivalences;
    }

    @Override
    public String getParty1() {
	return String.valueOf(this.mEnergySource.getId());
    }

    @Override
    public String getParty2() {

	return String.valueOf(this.mQty.getId());
    }

  

    


}
