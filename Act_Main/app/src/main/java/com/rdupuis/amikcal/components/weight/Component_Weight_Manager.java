package com.rdupuis.amikcal.components.weight;

import android.app.Activity;

import com.rdupuis.amikcal.commons.Manager;


public class Component_Weight_Manager extends Manager {
    
    
    public Component_Weight_Manager(Activity activity) {
	super(activity);
	
    }

    // Dans le cas d'une mise à jour on appelle l'éditeur avec l'ID de
    // l'activité à modifier
    public void edit() {

	//Intent intent = new Intent(this.mActivity, Act_Component_Weight_Editor.class);
	//intent.putExtra(Act_Component_Weight_Editor.INPUT____COMPONENT_ID, this.mComponent.getId());
	//this.mActivity.startActivityForResult(intent, 0);
    }


    public void delete() {
	// TODO Auto-generated method stub

    }


}
