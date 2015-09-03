package com.rdupuis.amikcal.components.food;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AppConsts.REL_TYP_CD_MAP;
import com.rdupuis.amikcal.components.Act_Component_Editor;
import com.rdupuis.amikcal.components.Act_Component_Food_Editor;
import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.components.Component_Manager;


import android.app.Activity;
import android.content.Intent;

/**
 * Component_Food_Manager permet au composant_Food d'utiliser des fonctionnalité de l'activity
 * ici on s'en sert pour appeler l'éditeur Food
 */

public class Component_Food_Manager extends Component_Manager {

    public Component_Food_Manager(Activity activity) {
        super(activity);
    }

    public Component_Food load(long databaseId) {
        return (Component_Food) super.load(databaseId);
    }

    public void save(Component_Food component_food) {
        super.save(component_food);
    }

    public void delete() {
    }

    // Dans le cas d'une mise à jour on appelle l'éditeur avec l'ID de
    // l'activité à modifier
    public  void edit(Component component_food) {

        Intent intent = new Intent(getActivity(), Act_Component_Food_Editor.class);
        intent.putExtra(Act_Component_Editor.INPUT____COMP_ID, component_food.getDatabaseId());
        REL_TYP_CD_MAP rel_typ_cd_map = new REL_TYP_CD_MAP();

        component_food.getRelationClass();
        intent.putExtra(Act_Component_Editor.INPUT____CLASS, rel_typ_cd_map._out.get(component_food.getRelationClass()));

        getActivity().startActivityForResult(intent, R.integer.COMPONENT_EDITOR);
    }


}
