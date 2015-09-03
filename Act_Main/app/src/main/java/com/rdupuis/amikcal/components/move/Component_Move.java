package com.rdupuis.amikcal.components.move;

import com.rdupuis.amikcal.Qty.Qty;
import com.rdupuis.amikcal.components.Component_Generic;
import com.rdupuis.amikcal.energy.PhysicalActivity;
import com.rdupuis.amikcal.relations.REL_TYP_CD;

public class Component_Move extends Component_Generic {

    /**
     * un composant Aliment Food c'est par exemple un bol de soupe 100 g de
     * chocolat 1 verre de vin
     * 
     * donc une source d'�nergie + une quantit� exprim�e avec un nombre et une
     * unti� de mesure
     * 
     * ici la particularit� c'est que par rapport � UserActivityComponent la
     * source d'�nergie est forc�ment un aliment de type FOOD.
     * 
     * par d�duction, on peu calculer la quantit�e de food components... si
     * l'information est pr�sente dans les �quivalences.
     * 
     * exemple : 100g de pommes = w kcal 100g de pommes = x lip 100g de pommes =
     * y glu 100g de pommes = z pro
     * 
     * on remarque que 100g de pomme c'est comme une UAC mais sans parent
     */

    /**
     * Constructeur
     */
    public Component_Move(PhysicalActivity phy, Qty qty) {
	super();
	this.setEnergy(phy);
	this.setQty(qty);
    }

    public Component_Move() {
	super();
	this.setEnergy(new PhysicalActivity());
    }

    public float getNbkcal() {

	// trouver une �quivalence en kcalorie existante pour cette �nergie
	// revient � chercher l'enregistrement "pomme" avec le nombre de
	// calories

	// on r�cup�re un

	// equiv = getEquivKCalories(this.getEnergySource();

	// equiv = 100g de pomme = 50kcal

	// si l�quivalence est exprim�e dans la m�me unit� que celle utilis�e
	// lors de la saisie
	// si equiv.unit_IN = this.unitMeasure

	// si les unit�es diff�res, il faut trouver une �valence entre l'unit�e
	// de r�f�rence et l'unit�e saisie

	// getEquivUnit(this.getUnite());

	// nbkcal = qty * equiv.ratio
	return 0f;
    };

    @Override
    public REL_TYP_CD getRelationClass() {
	return REL_TYP_CD.CMOVE;
    }
}
