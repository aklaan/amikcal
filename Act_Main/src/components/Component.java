package components;

import java.util.ArrayList;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.HasEquivalences;
import com.rdupuis.amikcal.commons.I_Relation;
import com.rdupuis.amikcal.commons.Qty;
import com.rdupuis.amikcal.commons.Relation;
import com.rdupuis.amikcal.commons.Relation.REL_TYP_CD;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.equivalence.Equivalence;
import com.rdupuis.amikcal.useractivity.EditableObj;

/**
 * Un composant = une relation entre une quantit� et une source d'�nergie.
 * 
 * par exemple :
 * 
 * 150 grammes de pommes
 * 
 * 
 * @author Rodolphe
 * 
 */
public class Component implements I_Relation, EditableObj {

    private long id;
    // private UserActivity mUserActivity;
    private EnergySource mEnergySource;
    private Qty mQty;
    private REL_TYP_CD mRelationClass;
    private ArrayList<Equivalence> mEquivalences;

    /**
     * Constructeur
     */
    public Component() {
	id = AppConsts.NO_ID;
	mEnergySource = new EnergySource();
	this.mQty = new Qty();
	this.mRelationClass = REL_TYP_CD.UNDEFINED;
    }

    public void setEnergySource(EnergySource energySource) {
	this.mEnergySource = energySource;
	switch (this.mEnergySource.getEffect()) {
	case BURN:
	    this.mRelationClass = REL_TYP_CD.CMOVE;
	    break;

	case EARN:
	    this.mRelationClass = REL_TYP_CD.CFOOD;
	    break;
	default:
	    this.mRelationClass = REL_TYP_CD.UNDEFINED;
	    break;
	}

    }

    public EnergySource getEnergySource() {
	return mEnergySource;
    }

    public long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Qty getQty() {
	return mQty;
    }

    public void setQty(Qty mQty) {
	this.mQty = mQty;
    }

    public REL_TYP_CD get_Class() {
	return mRelationClass;
    }

    public ArrayList<Equivalence> getEquivalences() {
	return mEquivalences;
    }

    public void setEquivalences(ArrayList<Equivalence> mEquivalences) {
	this.mEquivalences = mEquivalences;
    }

    @Override
    public String getParty1() {
	return String.valueOf(this.mEnergySource.getId());
    }

    @Override
    public String getParty2() {

	return String.valueOf(this.mQty.getId());
    }

    @Override
    public void setId(long rel_id) {
	// TODO Auto-generated method stub

    }

    public void setRelationClass(REL_TYP_CD rel_typ_cd) {
	this.mRelationClass = rel_typ_cd;
    }

    @Override
    public REL_TYP_CD getRelationClass() {
	// TODO Auto-generated method stub
	return null;
    }

}