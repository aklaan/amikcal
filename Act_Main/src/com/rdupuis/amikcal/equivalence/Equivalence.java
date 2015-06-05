package com.rdupuis.amikcal.equivalence;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.Qty;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.relations.I_Relation;
import com.rdupuis.amikcal.relations.REL_TYP_CD;

/**
 * Une équivalence permet de transposer la qty d'une source d'énergie en une qty
 * équivalente par exemple : 1 Verre de Vin = 46 kcal
 * 
 * composant = nrj+qty : 100 g de pain
 * qty = montant + unitée
 * 
 * on peu avoir des équivalences
 * composant <> composant
 * 1000 g  de pain = 1kg pain
 * 
 * composant <> qty
 * 100 g de pain = 500kcal
 * 
 * qty <> qty
 * 1 livre = 0,5 Kg
 * 
 * 
 * Basiquement, c'est un lien entre 2 objets
 * 
 * component et qty ont en commun
 * un montant
 * une unité
 * 
 *  la seule chose qui diffère c'est le nom de l'énergie
 * 
 * 
 * @author Rodolphe
 *
 */
public class Equivalence implements I_Relation {

    private long id;
    private Qty qty_IN;
    private final REL_TYP_CD rel_typ_cd = REL_TYP_CD.QTY_EQUIV;
    public EnergySource energySource;
    private Qty qty_OUT;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public Qty getQuantityOut() {
	return qty_OUT;
    }

    public Qty getQuantityIn() {
	return qty_IN;
    }

    public void setQuantityOut(Qty quantity) {
	this.qty_OUT = quantity;
    }

    public void setQuantityIn(Qty quantity) {
	this.qty_IN = quantity;
    }

    public Equivalence() {
	this.id = AppConsts.NO_ID;
	this.energySource = new EnergySource();
	this.qty_IN = new Qty();
	this.qty_OUT = new Qty();

    }

    public float ratio() {
	return this.qty_OUT.getAmount() / this.qty_IN.getAmount();
    }

    @Override
    public String getParty1() {
	return String.valueOf(qty_IN.getId());

    }

    @Override
    public String getParty2() {
	return String.valueOf(qty_OUT.getId());
    }

    @Override
    public REL_TYP_CD getRelationClass() {
	return this.rel_typ_cd;

    }

}
