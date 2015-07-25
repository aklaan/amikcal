package com.rdupuis.amikcal.components;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AppConsts.REL_TYP_CD_MAP;



import android.app.Activity;
import android.content.Intent;

public class Component_Food_Action extends Component_Action {

    public Component_Food_Action(Activity activity, Component cfood) {
	this.mActivity = activity;
	this.mComponent = cfood;

    }

    // Dans le cas d'une mise à jour on appelle l'éditeur avec l'ID de
    // l'activité à modifier
    public void edit() {

	Intent intent = new Intent(this.mActivity, Act_Component_Food_Editor.class);
	intent.putExtra(Act_Component_Editor.INPUT____COMP_ID, this.mComponent.getId());
	REL_TYP_CD_MAP rel_typ_cd_map = new REL_TYP_CD_MAP();
	
	this.mComponent.getRelationClass();
	intent.putExtra(Act_Component_Editor.INPUT____CLASS, rel_typ_cd_map._out.get(this.mComponent.getRelationClass()));

	this.mActivity.startActivityForResult(intent, R.integer.COMPONENT_EDITOR);
    }

    @Override
    public void delete() {
	// TODO Auto-generated method stub

    }

}
