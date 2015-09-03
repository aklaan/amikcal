package com.rdupuis.amikcal.energy;

import android.content.ContentResolver;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.components.Component_Reference;
import com.rdupuis.amikcal.data.Savable;
import com.rdupuis.amikcal.data.writers.DBWriter;
import com.rdupuis.amikcal.data.writers.DBWriter_EnergySource;

/**
 * <h1>EnergySource : une source d'�nergie<h1>
 * <p/>
 * <p>
 * une source d'�nergie : - poss�de un nom - peut �tre repr�sent� par une
 * quantit� de calories - cette en�rgie peu �tre absorb� (manger)ou d�pens�e
 * (courir)
 * </p>
 *
 * @author Rodolphe
 */

public class EnergySource implements Savable {

    private long databaseId;
    private String name;
    private Component_Reference mReference;


    public EnergySource() {
        this.databaseId = AppConsts.NO_ID;
        name = "";
        this.mReference = new Component_Reference();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDatabaseId() {
        // TODO Auto-generated method stub
        return this.databaseId;
    }

    public void setDatabaseId(long databaseId) {
        this.databaseId = databaseId;
    }

    public Component_Reference getComponentReference() {
        // TODO Auto-generated method stub
        return this.mReference;
    }

    public void setComponentReference(Component_Reference component_reference) {
        this.mReference = component_reference;
    }

    public NRJ_CLASS getEnergyClass() {
        // TODO Auto-generated method stub
        return NRJ_CLASS.ENERGY;
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
