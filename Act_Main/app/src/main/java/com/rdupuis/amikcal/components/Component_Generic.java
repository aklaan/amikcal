package com.rdupuis.amikcal.components;

import java.util.ArrayList;

import android.content.ContentResolver;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.Qty.Qty;
import com.rdupuis.amikcal.data.writers.DBWriter;
import com.rdupuis.amikcal.data.writers.DBWriter_Component;
import com.rdupuis.amikcal.energy.DBWarper;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.relations.REL_TYP_CD;

/**
 * Un composant_generic est le composant de base qui contient les attributs commun à tous les composants
 * <p/>
 * Un composant possède forcément :
 * <p/>
 * un id d'identifiant de la relation
 * une qty
 * une source d'énergie
 * <p/>
 * Le composant peut être composé d'autre composants
 * ex: dans 100 g de pain, on a 50 g de glucides et 6 g de protéines
 *
 * @author Rodolphe
 */
public class Component_Generic extends Component {

    long id;
    Qty mQty;
    EnergySource mEnergy;
    ArrayList<Component> mComponentList;

    public Component_Generic() {
        this.setDatabaseId(AppConsts.NO_ID);
        this.setEnergy(new EnergySource());
        this.setQty(new Qty());
        this.setComponentList(new ArrayList<Component>());
    }

    public void setEnergy(EnergySource energy) {
        this.mEnergy = energy;
    }

    public EnergySource getEnergy() {
        return mEnergy;
    }

    @Override
    public long getDatabaseId() {
        return id;
    }

    @Override
    public void setDatabaseId(long id) {
        this.id = id;
    }

    public Qty getQty() {
        return mQty;
    }

    public void setQty(Qty mQty) {
        this.mQty = mQty;
    }

    public REL_TYP_CD getRelationClass() {
        return REL_TYP_CD.COMPONENT;
    }

    @Override
    public String getParty1() {
        return String.valueOf(this.mEnergy.getDatabaseId());
    }

    @Override
    public String getParty2() {

        return String.valueOf(this.mQty.getDatabaseId());
    }

    @Override
    public ArrayList<Component> getComponentList() {

        return mComponentList;
    }

    @Override
    public void setComponentList(ArrayList<Component> componentList) {
        mComponentList = componentList;

    }


    @Override
    public DBWarper getDBWarper() {
        return new DBWarper_Component(this);

    }

    @Override
    public DBWriter getDBWriter(ContentResolver contentResolver) {
        // TODO Auto-generated method stub
        return new DBWriter_Component(contentResolver, this);
    }


}
