package com.rdupuis.amikcal.components.food;

import android.app.Activity;
import android.content.Intent;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.ManagedElement;
import com.rdupuis.amikcal.components.Act_Component_Editor;
import com.rdupuis.amikcal.components.Component_Manager;

/**
 * Component_Food_Manager permet au composant_Food d'utiliser des fonctionnalité de l'activity
 * ici on s'en sert pour appeler l'éditeur Food
 */

public class Component_Food_Manager extends Component_Manager {

    public Component_Food_Manager(Activity activity) {
        super(activity);
    }


    // Dans le cas d'une mise à jour on appelle l'éditeur avec l'ID de
    // l'activité à modifier
    @Override
    public void edit(ManagedElement element) {
        //Cast de l'élément en Component_Food
        Component_Food component_food = (Component_Food) element;

        //Préparation de Intent
        Intent intent = new Intent(getActivity(), Act_Component_Food_Editor.class);
        intent.putExtra(Act_Component_Editor.INPUT____COMP, component_food);

        //Start de l'activity
        getActivity().startActivityForResult(intent, R.integer.COMPONENT_EDITOR);
    }

    public Component_Food load(long id){
        return (Component_Food)super.load(id);
    }

}
