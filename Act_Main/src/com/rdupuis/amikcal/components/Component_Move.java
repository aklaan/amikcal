package com.rdupuis.amikcal.components;

import java.util.ArrayList;

import com.rdupuis.amikcal.commons.Qty;
import com.rdupuis.amikcal.energy.Food;
import com.rdupuis.amikcal.energy.PhysicalActivity;
import com.rdupuis.amikcal.equivalence.i_CanHaveEquivalences;
import com.rdupuis.amikcal.relations.REL_TYP_CD;

public class Component_Move extends Component_Commons implements i_CanHaveEquivalences{

    /**
     * un composant Aliment Food c'est par exemple un bol de soupe 100 g de
     * chocolat 1 verre de vin
     * 
     * donc une source d'énergie + une quantité exprimée avec un nombre et une
     * untié de mesure
     * 
     * ici la particularité c'est que par rapport à UserActivityComponent la
     * source d'énergie est forcément un aliment de type FOOD.
     * 
     * par déduction, on peu calculer la quantitée de food components... si
     * l'information est présente dans les équivalences.
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

	// trouver une équivalence en kcalorie existante pour cette énergie
	// revient à chercher l'enregistrement "pomme" avec le nombre de
	// calories

	// on récupère un

	// equiv = getEquivKCalories(this.getEnergySource();

	// equiv = 100g de pomme = 50kcal

	// si léquivalence est exprimée dans la même unité que celle utilisée
	// lors de la saisie
	// si equiv.unit_IN = this.unitMeasure

	// si les unitées diffères, il faut trouver une évalence entre l'unitée
	// de référence et l'unitée saisie

	// getEquivUnit(this.getUnite());

	// nbkcal = qty * equiv.ratio
	return 0f;
    };

    @Override
    public REL_TYP_CD getRelationClass() {
	return REL_TYP_CD.CMOVE;
    }

    @Override
    public void setEquivalences(ArrayList<? extends Component> mEquivalences) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public ArrayList<? extends Component> getEquivalences() {
	// TODO Auto-generated method stub
	return null;
    }
}
