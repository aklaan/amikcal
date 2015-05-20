package com.rdupuis.amikcal.useractivitycomponent;

import java.util.ArrayList;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.Component;
import com.rdupuis.amikcal.commons.HasEquivalences;
import com.rdupuis.amikcal.commons.I_Relation;
import com.rdupuis.amikcal.commons.Qty;
import com.rdupuis.amikcal.commons.Relation.REL_TYP_CD;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.equivalence.Equivalence;
import com.rdupuis.amikcal.useractivity.UserActivity;

/**
 * Un composant d'une UserActivity c'est par exemple :
 * 
 * [150 grammes de pommes] en relation avec un [déjeuner]
 * 
 * donc : - une source d'énergie : la pomme - une quantité de cette source : 100
 * - une unité de mesure dans laquelle est exprimé cette quantité : gramme
 * 
 * Un composant est attaché à une activité parente dont il est composant.
 * 
 * @author Rodolphe
 * 
 */
public class UserActivityComponent implements I_Relation {

	private long id;
	private Component mComponent;
	private UserActivity mUserActivity;
	private REL_TYP_CD mRelationClass;

	/**
	 * Constructeur
	 */
	public UserActivityComponent() {
		id = AppConsts.NO_ID;
		mComponent = new Component();
		mUserActivity = new UserActivity();
		this.mRelationClass = REL_TYP_CD.UNDEFINED;
	}

	// ==================
	public void setId(long id) {
		this.id = id;
	}

	// ==================
	public long getId() {
		return id;
	}

	// ==================
	public void setUserActivity(UserActivity ua) {
		this.mUserActivity = ua;
	}

	// ==================
	public UserActivity getUserActivity() {
		return this.mUserActivity;
	}

	// ==================
	public void setComponent(Component component) {
		this.mComponent = component;
	}

	// ==================
	public Component getComponent() {
		return this.mComponent;
	}

	// ==================
	public REL_TYP_CD getRelationClass() {
		return mRelationClass;
	}

	// ==================
	public void setRelationClass(REL_TYP_CD relationClass) {
		this.mRelationClass = relationClass;
	}

	// ==================
	public String getParty1() {
		return String.valueOf(mUserActivity.get_id());
	}

	// ==================
	public String getParty2() {
		return String.valueOf(mComponent.getId());
	}

}
