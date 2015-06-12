package com.rdupuis.amikcal.energy;

import java.util.ArrayList;

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

public abstract class Energy {

    public abstract String getName();

    public abstract void setName(String name);

    public abstract NRJ_EFFECT getEffect();

    public abstract long getId();

    public abstract void setId(long id);

    public abstract Component getReferenceComponent();
    public abstract void setReferenceComponent(Component refComponent);

    
    }
