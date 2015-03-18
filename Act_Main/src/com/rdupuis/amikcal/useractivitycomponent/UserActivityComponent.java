package com.rdupuis.amikcal.useractivitycomponent;

import java.util.ArrayList;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.HasEquivalences;
import com.rdupuis.amikcal.commons.Qty;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.useractivity.UserActivity;

/**
 * Un composant d'une UserActivity c'est par exemple :
 * 
 * 150 grammes de pommes
 * 
 * donc : - une source d'énergie : la pomme - une quantité de cette source : 100
 * - une unité de mesure dans laquelle est exprimé cette quantité : gramme
 * 
 * Un composant est attaché à une activité parente dont il est composant.
 * 
 * @author Rodolphe
 *
 */
public class UserActivityComponent implements HasEquivalences{

	private long id;
	private UserActivity mUserActivity;
	private EnergySource mEnergySource;
	private Qty mQty;
	private ArrayList<Qty> mEquivalences;
	
	
	/**
	 * Constructeur
	 */
	public UserActivityComponent() {
		id = AppConsts.NO_ID;
		mEnergySource = new EnergySource();
		setEquivalences(new ArrayList<Qty>()); 
	
	}

	public void setEnergySource(EnergySource energySource) {
		this.mEnergySource = energySource;
	}

	public EnergySource getEnergySource() {
		return mEnergySource;
	}

	public UserActivity getUserActivity() {
		return mUserActivity;
	}

	public void setUserActivity(UserActivity UA_Parent) {
		this.mUserActivity = UA_Parent;
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

public ArrayList<Qty> getEquivalences() {
	return mEquivalences;
}

public void setEquivalences(ArrayList<Qty> mEquivalences) {
	this.mEquivalences = mEquivalences;
};

	
	
	
}
