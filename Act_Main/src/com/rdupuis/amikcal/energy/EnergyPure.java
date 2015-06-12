package com.rdupuis.amikcal.energy;

import java.util.ArrayList;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.components.Component;

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

public class EnergyPure extends Energy {

    private long id;
    private String name;
    private Component mReference_Component; // composant de r�f�rence
    private ArrayList<Component> mEquivalences;

    public EnergyPure() {
	id = AppConsts.NO_ID;
	name = "e";

    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    // cette classe repr�sente l'�nergie dans l'absolu
    public NRJ_EFFECT getEffect() {
	return NRJ_EFFECT.ABSOLUTE;
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

    @Override
    public void setReferenceComponent(Component refComponent) {
	// TODO Auto-generated method stub
	
    }

    
}
