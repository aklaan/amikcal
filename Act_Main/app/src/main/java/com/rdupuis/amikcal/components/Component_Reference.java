package com.rdupuis.amikcal.components;

import com.rdupuis.amikcal.Qty.Qty;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.relations.REL_TYP_CD;

public class Component_Reference extends Component_Generic {

    /**
     * Constructeur
     */

    public Component_Reference() {
        super();
    }

    public Component_Reference(EnergySource energySource, Qty qty) {
        super();
    this.setEnergy(energySource);
    this.setQty(qty);
    }


    @Override
    public REL_TYP_CD getRelationClass() {
        return REL_TYP_CD.NRJ_REF_INTRNL;
    }
}
