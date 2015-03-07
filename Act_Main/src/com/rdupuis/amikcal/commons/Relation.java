package com.rdupuis.amikcal.commons;

/**
 * <h1>TYPE_REL_CD : Type de relations entre les entit�s</h1>

 * <p>
 * UAC_FOOD = relation de type composant d'un repas (100 g de pomme) </br>
 * UAC_MOVE = relation de type composant d'une activit� physique (10 min de
 * marche) </br>
 * 
 * UAC_WEIGHT = relation de type composant d'une pes�e (45 kg) </br>
 * 
 * UA_TO_UAC = relation entre une UA et ses UAC </br>
 * 
 * NRJ_REF_INTER = relation entre la source d'�nergie et sa quantit� / unit�
 * de r�f�rence internationale (ex:100 g de pomme) </br>
 * 
 * NRJ_REF_EQUIV = �quivalence par rapport � la r�f�rence (ex : 100 g de
 * pomme = 56 Kcal) </br>
 * 
 * CSTM_NRJ_REF = unit� de r�f�rence personalis� pour une source (ex:
 * Moyenne -> Pomme) </br>
 * 
 * UNIT_EQUIV = relation d'�quivalence entre deux unit�e de la m�me famille
 * (ex gramme = 0,OOO1 kilogramme) </br>
 * 
 * UNIT_INTER_LINK = passerelle de convertion entre 2 syst�mes de mesure (ex
 * : 1 kg = x Livres)
 * 
 * </p>
 */

public abstract class Relation {
	public static enum REL_TYP_CD {
		UAC_FOOD, UAC_MOVE, UAC_WEIGHT, UA_UAC, NRJ_REF_INTER, NRJ_REF_EQUIV, CSTM_NRJ_REF, UNIT_EQUIV, UNIT_INTER_LINK,UAC_EQUIV,QTY;
	
	}

	public long rel_id;
	public REL_TYP_CD rel_typ_cd;
	private String Party1;  
	private String Party2;
	
	public String getParty1() {
		return Party1;
	}
	public void setParty1(String party1) {
		Party1 = party1;
	}
	public String getParty2() {
		return Party2;
	}
	public void setParty2(String party2) {
		Party2 = party2;
	}

public void save(){
	
}
}