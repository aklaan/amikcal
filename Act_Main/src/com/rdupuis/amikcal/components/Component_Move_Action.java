package com.rdupuis.amikcal.components;

import android.app.Activity;

public class Component_Move_Action extends Component_Action {
    
    
    public Component_Move_Action(Activity activity,Component cfood) {
	this.mActivity = activity;
	this.mComponent = cfood;
	
    }

    // Dans le cas d'une mise � jour on appelle l'�diteur avec l'ID de
    // l'activit� � modifier
    public void edit() {

	//Intent intent = new Intent(this.mActivity, Act_Component_Move_Editor.class);
	//intent.putExtra(Act_Component_Move_Editor.INPUT____COMPONENT_ID, this.mComponent.getId());
	//this.mActivity.startActivityForResult(intent, 0);
    }

    @Override
    public void delete() {
	// TODO Auto-generated method stub

    }


}
