package com.rdupuis.amikcal.components;

import java.util.ArrayList;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.I_Relation;
import com.rdupuis.amikcal.commons.Qty;
import com.rdupuis.amikcal.commons.Relation.REL_TYP_CD;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.equivalence.Equivalence;

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
public abstract class Component implements I_Relation {

    public abstract void setEnergySource(EnergySource energySource);

    public abstract EnergySource getEnergySource();

    @Override
    public abstract long getId();

    @Override
    public abstract void setId(long id);

    public abstract Qty getQty();

    public abstract void setQty(Qty mQty);

    public abstract REL_TYP_CD getRelationClass();

    public abstract ArrayList<Equivalence> getEquivalences();

    public abstract void setEquivalences(ArrayList<Equivalence> mEquivalences);

    @Override
    public abstract String getParty1();

    @Override
    public abstract String getParty2();


}
