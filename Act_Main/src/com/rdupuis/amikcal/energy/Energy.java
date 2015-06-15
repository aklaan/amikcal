package com.rdupuis.amikcal.energy;

import android.content.ContentValues;

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

public abstract class Energy implements i_CanBeSaved{

    public abstract String getName();

    public abstract void setName(String name);

    public abstract NRJ_EFFECT getEffect();

    public abstract long getId();

    public abstract void setId(long id);

    public abstract Component getReferenceComponent();
    public abstract void setReferenceComponent(Component refComponent);

    public abstract ContentValues getContentValues();
    
    }
