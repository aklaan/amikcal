package com.rdupuis.amikcal.components;

import java.util.ArrayList;

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
public abstract class Component implements I_Relation {

    public  abstract void setEnergy(Energy energy) ;

    public abstract Energy getEnergy() ;

    @Override
    public abstract long getId() ;

    @Override
    public abstract void setId(long id) ;

    public abstract Qty getQty() ;

    public abstract void setQty(Qty mQty) ;

    public abstract REL_TYP_CD getRelationClass() ;

    @Override
    public abstract String getParty1() ;

    @Override
    public abstract String getParty2() ;

  

    


}
