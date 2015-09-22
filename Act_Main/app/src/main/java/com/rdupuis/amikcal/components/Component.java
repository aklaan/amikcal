package com.rdupuis.amikcal.components;

import android.os.Parcelable;

import java.util.ArrayList;

import com.rdupuis.amikcal.Qty.Qty;
import com.rdupuis.amikcal.commons.ManagedElement;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.relations.Relation;
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
public abstract class Component extends Relation implements ManagedElement,Parcelable{

    public  abstract void setEnergy(EnergySource energy) ;

    public abstract EnergySource getEnergy() ;

    public abstract Qty getQty() ;

    public abstract void setQty(Qty mQty) ;

    public abstract REL_TYP_CD getRelationClass() ;

    public abstract ArrayList<Component> getComponentList();

    public abstract void setComponentList(ArrayList<Component> Component);


    
}