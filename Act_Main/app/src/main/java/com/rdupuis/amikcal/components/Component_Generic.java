package com.rdupuis.amikcal.components;

import java.util.ArrayList;

import android.content.ContentResolver;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.Qty;
import com.rdupuis.amikcal.data.writers.DBWriter;
import com.rdupuis.amikcal.data.writers.DBWriter_Component;
import com.rdupuis.amikcal.data.writers.DBWriter_Relation;
import com.rdupuis.amikcal.energy.DBWarper;
import com.rdupuis.amikcal.energy.DBWarper_Food;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.relations.DBWarper_Component;
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
public  class Component_Generic extends Component {

    long id;
    Qty mQty;
    EnergySource mEnergy;
    ArrayList<Component> mEquivalences;
    
    public Component_Generic() {
	this.setId(AppConsts.NO_ID);
	this.setEnergy(new EnergySource());
	this.setQty(new Qty());
	this.setEquivalences(new ArrayList<Component>());
    }

    public Component_Generic(EnergySource nrj , Qty qty) {
   	this.setId(AppConsts.NO_ID);
   	this.setEnergy(nrj);
   	this.setQty(qty);
   	this.setEquivalences(new ArrayList<Component>());
       }

    
    public  void setEnergy(EnergySource energy) {
	this.mEnergy = energy;
    }

    public EnergySource getEnergy() {
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

    @Override
    public ArrayList<Component> getEquivalences() {

	return mEquivalences;
    }

    @Override
    public void setEquivalences(ArrayList<Component> relations) {
	mEquivalences = relations;
	
    }

    
    public boolean hasEquivalences() {
	return (!this.getEquivalences().isEmpty());
    }

    
	@Override
	public DBWarper getDBWarper() {
		return new DBWarper_Component(this);
		
	}

	@Override
	public DBWriter getDBWriter(ContentResolver contentResolver) {
		// TODO Auto-generated method stub
		return new DBWriter_Component(contentResolver, this);
	}

   
}
