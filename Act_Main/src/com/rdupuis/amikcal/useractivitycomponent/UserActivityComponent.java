package com.rdupuis.amikcal.useractivitycomponent;

import java.util.ArrayList;
import java.util.HashMap;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.Qty;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.energy.EnergySourceQty;
import com.rdupuis.amikcal.equivalence.EquivalenceObj;
import com.rdupuis.amikcal.unity.Unity;

/**
 * Un composant d'une UserActivity c'est par exemple :
 * 
 * 150 grammes de pommes
 * 
 * donc : - une source d'énergie : la pomme - une quantité de cette source : 100
 * - une unité de mesure dans laquelle est exprimé cette quantité : gramme
 * 
 * Un composant est attaché à une activité parente dont il est composant.
 * 
 * @author Rodolphe
 *
 */
public class UserActivityComponent {

	private long id;
	private long parent_id;
	private EnergySource mEnergySource;
	private Qty mQty;
	
	
	/**
	 * Constructeur
	 */
	public UserActivityComponent() {
		this.parent_id = AppConsts.NO_ID;
	
	}

	public void setEnergySource(EnergySource energySource) {
		this.mEnergySource = energySource;
	}

	public EnergySource getEnergySource() {
		return mEnergySource;
	}

	public long getParentId() {
		return parent_id;
	}

	public void setParentId(Long parentId) {
		this.parent_id = parentId;
	}

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
/**
 * Fonction qui va calculer les équivalences par rapport à la quantité et le référentiel des équivalences
 * 
 * dans le référentiel : EnergySourceQty = 100g de pommes (id= 00001)
 * 
 *  00001 = 67 Kcal
 *  00001 = 39 Glu
 *  00001 = 01 lip
 *  etc....
 * 	
 * ici on a EnergySourceQty = 200 g de pomme
 * 
 *  Aller voir si on a une EnergySourceQty de référence qui concerne la EnergySource "Pomme"
 * si oui poursuivre
 * @return
 */
	public void  computeEquivalences() {

		//
		
		
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
		
	}

public Qty getQty() {
	return mQty;
}

public void setQty(Qty mQty) {
	this.mQty = mQty;
};

	
	
	
}
