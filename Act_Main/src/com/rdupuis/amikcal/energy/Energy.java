package com.rdupuis.amikcal.energy;

import java.util.ArrayList;

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

public abstract class Energy {

    public abstract String getName();

    public abstract void setName(String name);

    public abstract NRJ_EFFECT getEffect();

    public abstract long getId();

    public abstract void setId(long id);

    public abstract Component getReferenceComponent();
    public abstract void setReferenceComponent(Component refComponent);

    
    }
