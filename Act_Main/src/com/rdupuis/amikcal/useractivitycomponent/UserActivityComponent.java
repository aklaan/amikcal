package com.rdupuis.amikcal.useractivitycomponent;

import java.util.ArrayList;
import java.util.HashMap;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.equivalence.EquivalenceObj;
import com.rdupuis.amikcal.unitofmeasure.UnitOfMeasureObj;

/**
 * Un composant d'une UserActivity c'est par exemple :
 * 
 * 150 grammes de pommes
 * 
 * donc : - une source d'�nergie : la pomme - une quantit� de cette source : 100
 * - une unit� de mesure dans laquelle est exprim� cette quantit� : gramme
 * 
 * Un composant est attach� � une activit� parente dont il est composant.
 * 
 * @author Rodolphe
 *
 */
public class UserActivityComponent {

	private long id;
	private long parent_id;
	private EnergySourceQty mEnergySourceQty;
	public ArrayList<EquivalenceObj> equivalences;
	
	/**
	 * Constructeur
	 */
	public UserActivityComponent() {
		this.parent_id = AppConsts.NO_ID;
		this.mEnergySourceQty = new EnergySourceQty();

	}

	public void setEnergySourceQty(EnergySourceQty energySourceQty) {
		this.mEnergySourceQty = energySourceQty;
	}

	public EnergySourceQty getEnergySourceQty() {
		return mEnergySourceQty;
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
 * Fonction qui va calculer les �quivalences par rapport � la quantit� et le r�f�rentiel des �quivalences
 * 
 * dans le r�f�rentiel : EnergySourceQty = 100g de pommes (id= 00001)
 * 
 *  00001 = 67 Kcal
 *  00001 = 39 Glu
 *  00001 = 01 lip
 *  etc....
 * 	
 * ici on a EnergySourceQty = 200 g de pomme
 * 
 *  Aller voir si on a une EnergySourceQty de r�f�rence qui concerne la EnergySource "Pomme"
 * si oui poursuivre
 * @return
 */
	public void  computeEquivalences() {

		//
		
		
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
		
	};

	
	
	
}
