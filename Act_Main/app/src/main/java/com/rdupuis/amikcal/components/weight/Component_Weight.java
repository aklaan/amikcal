package com.rdupuis.amikcal.components.weight;

import com.rdupuis.amikcal.components.Component_Generic;
import com.rdupuis.amikcal.relations.REL_TYP_CD;

public class Component_Weight extends Component_Generic {

    /**
     *
     *

     *    85 kg de l'utilisaeur 1
     *

     */

    /**
     * Constructeur
     */
    public Component_Weight() {
        super();
    }

    public float getNbkcal() {
        return 0f;
    }

    ;


    public REL_TYP_CD getRelationClass() {
        return REL_TYP_CD.CWEIGHT;
    }
}
