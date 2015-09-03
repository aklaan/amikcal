package com.rdupuis.amikcal.components.move;

import android.app.Activity;

import com.rdupuis.amikcal.components.Component_Manager;

public class Component_Move_Manager extends Component_Manager {


    public Component_Move_Manager(Activity activity, Component_Move component) {
        super(activity, component);
    }

    public Component_Move load(long databaseId) {
        return (Component_Move) super.load(databaseId);
    }

    // Dans le cas d'une mise � jour on appelle l'�diteur avec l'ID de
    // l'activit� � modifier
    public void edit() {

        //Intent intent = new Intent(this.mActivity, Act_Component_Move_Editor.class);
        //intent.putExtra(Act_Component_Move_Editor.INPUT____COMPONENT_ID, this.mComponent.getId());
        //this.mActivity.startActivityForResult(intent, 0);
    }


    public void delete() {
        // TODO Auto-generated method stub

    }


}
