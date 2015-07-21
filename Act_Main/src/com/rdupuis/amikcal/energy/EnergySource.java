package com.rdupuis.amikcal.energy;

import android.content.ContentResolver;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.Qty;
import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.components.Component_Reference;
import com.rdupuis.amikcal.data.Savable;
import com.rdupuis.amikcal.data.writers.DBWriter;
import com.rdupuis.amikcal.data.writers.DBWriter_EnergySource;

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

public class EnergySource implements Savable{

    private long id;
    private String name;
    private Qty mQtyReference; // Qty de r�f�rence

    public EnergySource() {
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

    public Component_Reference getComponentReference() {
	// TODO Auto-generated method stub
	return new Component_Reference(this);
    }

    
    public Qty getQtyReference() {
	// TODO Auto-generated method stub
	return this.mQtyReference;
    }
     public void setQtyReference(Qty qty) {
    	 this.mQtyReference = qty;
	
    }
     
    public NRJ_CLASS getEnergyClass() {
	// TODO Auto-generated method stub
	return NRJ_CLASS.UNDEFINED;
    }

    
    @Override
    public DBWarper getDBWarper() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public DBWriter getDBWriter(ContentResolver contentResolver) {
		return new DBWriter_EnergySource(contentResolver, this);
    }

    

	



    
}
