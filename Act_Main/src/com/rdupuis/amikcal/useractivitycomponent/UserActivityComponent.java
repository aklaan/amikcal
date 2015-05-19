package com.rdupuis.amikcal.useractivitycomponent;

import java.util.ArrayList;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.Component;
import com.rdupuis.amikcal.commons.HasEquivalences;
import com.rdupuis.amikcal.commons.InterfaceRelation;
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
public class UserActivityComponent implements InterfaceRelation  {

    private long id;
    private Component mComponent;
    private UserActivity mUserActivity;
    private final REL_TYP_CD mUAC_Class = REL_TYP_CD.UA_UAC; 
    
    /**
     * Constructeur
     */
    public UserActivityComponent() {
	id = AppConsts.NO_ID;
	mComponent = new Component();
	mUserActivity = new UserActivity();

    }

    
    public long getId() {
	return id;
    }

    public void setUserActivity(UserActivity ua){
	this.mUserActivity = ua;
    }
    
    public Component getComponent(){
	return this.mComponent;
    }
    
    public UserActivity getUserActivity(){
	return this.mUserActivity;
    }
    
    public void setComponent(Component component ){
	 this.mComponent  =component;
    }
    
    
    public void setId(long id) {
	this.id = id;
    }

    public REL_TYP_CD getUAC_Class() {
	return mUAC_Class;
    }

    @Override
    public String getParty1() {
	return String.valueOf(mUserActivity.get_id());
    }

    @Override
    public String getParty2() {
	return String.valueOf(mComponent.getId());
    }


    @Override
    public REL_TYP_CD getRel_typ_cd() {
	return this.mUAC_Class;
    }

    

}
