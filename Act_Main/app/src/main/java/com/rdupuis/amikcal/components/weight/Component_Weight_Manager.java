package com.rdupuis.amikcal.components.weight;

import android.app.Activity;

import com.rdupuis.amikcal.components.Component_Manager_Commons;

public class Component_Weight_Manager extends Component_Manager_Commons {
    
    
    public Component_Weight_Manager(Activity activity,Component_Weight component_weight) {
	super(activity, component_weight);
	
    }

    // Dans le cas d'une mise � jour on appelle l'�diteur avec l'ID de
    // l'activit� � modifier
    public void edit() {

	//Intent intent = new Intent(this.mActivity, Act_Component_Weight_Editor.class);
	//intent.putExtra(Act_Component_Weight_Editor.INPUT____COMPONENT_ID, this.mComponent.getId());
	//this.mActivity.startActivityForResult(intent, 0);
    }

    @Override
    public void delete() {
	// TODO Auto-generated method stub

    }


}
