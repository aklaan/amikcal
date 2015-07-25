package com.rdupuis.amikcal.energy;

import java.util.ArrayList;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.components.Component;

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

public class CopyOfEnergySource {

    private long id;
    private String name;
    private Component mReference_Component; // composant de référence
    private ArrayList<Component> mEquivalences;

    public CopyOfEnergySource() {
	id = AppConsts.NO_ID;
	name = "e";

    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    
    public long getId() {
	// TODO Auto-generated method stub
	return this.id;
    }

    public void setId(long id) {
	this.id = id;

    }

    public Component getReferenceComponent() {
	// TODO Auto-generated method stub
	return this.mReference_Component;
    }

    public ArrayList<Component> getEquivalences() {
	// TODO Auto-generated method stub
	return this.mEquivalences;
    }

    public void setReferenceComponent(Component refComponent) {
	// TODO Auto-generated method stub
	
    }

	

	



    
}
