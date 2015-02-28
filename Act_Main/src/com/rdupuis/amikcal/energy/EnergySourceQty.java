package com.rdupuis.amikcal.energy;

import com.rdupuis.amikcal.unity.Unity;
/**
 * 
 * row_id  par_row_id	  qte    unit_id      energy_id type 
 * ------  ----------	  -----  -----------  --------- ------
 *      1           - 	    100  U4 (gramme)       pomme      ref principale
 *      2           1	     54  Kcal         -          equiv par rapport au parent-
 *      3           1	     20  Glu          -          equiv par rapport au parent-
 *      
 *       
 *      4       	          -  UA_ID        UAC_ID     rel UA-UAC
 *      4           -	      2  moyenne      pomme      UAC
 *      
 *      
 *      5           -	      1  moyenne      pomme      ref perso  
 *      6           5	    0.8  toty         pomme      equiv en unit� internationale
 *            
 *      
 *      
 *      7           -	       1 U1 (kilogramme)   -          Relation entre 2 unit�es
 *      8           -	      10 U2 (decigramme)   U1         Relation entre 2 unit�es
 *      9           -	     100 U3 (centigramme)  U2         Relation entre 2 unit�es
 *     10           -	    1000 U4 (gramme)       U3         Relation entre 2 unit�es 
 *     
 *     11           -	       1 U5 (totx)         -          Relation entre 2 unit�es
 *     12          11	      10 U6 (toty)         -          Relation entre 2 unit�es
*      13          12	     100 U7 (totZ)         -          Relation entre 2 unit�es

 *     14           - 	    0,21 U1                U5-        Relation entre 2 unit�es
 *     
 *     * 
 * l'utilisateur a mang� 2 pommes moyennes
 * on veut savoir combien �a repr�sente de kcal
 * 
 * 
 * premi�re partie d�terminer la quantit� d'�ngergie de UAC
 * exprim� dans la m�me unit�e que la r�f�rence de l'�nergie
 * 
 * 1- aller chercher la quantit� de r�f�rences principale pour "pomme"
 *    energy_id = pomme + type = ref principale
 *    on r�cup�re l'enregistrement [1] --> on sait que l'unit�e de ref est [gramme] et que les �quivalences kcal/lip..etc en d�coulent
 * 
 * 
 * 2 - Objectif :  calculer la quantit� d'�nergie dans la m�me unit�e que l'unit�e de REF
 *   
 *    
 *    Est ce que l'unit� UAC est une unit� perso ou internationale ?
 *    
 *    si c'est une unit� perso, il faut aller chercher la correspondance en unit�e internationale.
 *    elle, doit forc�ment exister car l'utilisateur l'a cr�� personellement. 
 *    
 *    Phase 1
 *    ----------------------
 *    chercher dans les references perso celle disponibles pour "pomme + moyenne"
 *    
 *    (elle existe forcement car l'utilisateur a pu opter pour ce choix de mesure.
 *    quand on renseigne une unit�e de mesure perso, on doit obligatoirement demander
 *    a l'utilisateur de renseigner une equivalence dans une unit�e internationale (g, cl, dcl, kg, livre...)
 *    )
 *    
 *    type= ref perso  / energy=pomme / unit= moyenne
 *    ==> on trouve [5]
 *    
 *    aller chercher la fille de [5]
 *    (A prioris il n'y a pas de raison d'en avoir plus d'une)
 *    ==> on trouve [6]
 *    c'est l'�quivalence qui doit �tre forc�ment exprim� avec une unit�e internationale
 *    on voit que 1 pomme moyenne c'est 0.8 toty
 *    
 *   
 *       
 *    Phase 2
 *    -------------------------
 *    on recherche dans les conversions internationales si on ne peux pas 
 *    trouver une conversion de toty en gramme
 *    
 *    cela revient � trouver la chaine de conversion toty ==> .../... ==> .../... ==> gramme
 *    
 *    QTY = qt� UAC = structure [montant;unit�e]
 *    
 *    fonction QTY calculerQTY (QTY:QTY_IN(unit; Montant), unit�e REF)
 *                                     ((toty;2*0,8), gramme) 
 *    	est-ce que QTY_IN.Unit (toty) == REF (gramme) ?
 *    	--> oui : retourner QTY_IN
 *      --> sinon :
 *             pour chaques PARENT de QTY_IN.Unit
 *    	          1-Trouve PARENT [totx] 	
 *    		
 *    		          Est-ce que PARENT diff�re de QTY_IN.Unit actuelle ?
 *    		          --> non -> next PARENT
 *       			  --> oui -> QTY_Work = QTY_IN convertie en PARENT [totx]
 *    				             0,16 toty = 0,016 totx
 *    
 *    				  Est-ce que PARENT [totx] est l'unit� de REF (gramme) ?
 *    				  --> oui --> Return QTY_Work (fin de la fonction)
 *    				  --> non --> QTY_Work = calculerQTY (QTY:QTY_work, Unit:REF)
 *     	 *     
 *     								est-ce que QTY.Unit (totx) == REF (gramme)?
 *    								--> oui : retourner QTY
 *     								--> non :
 *             								  pour chaques PARENT de QTY.Unit
 *    	          								1-Trouve PARENT [totx] 	
 *    		
 *    		          Est-ce que PARENT diff�re de QTY.Unit actuelle ?
 *    		          --> non -> next PARENT
 *       			  --> oui -> QTY_Work = QTY convertie en PARENT [totx]
 *    				   
 *    				  Est-ce que PARENT [totx] est l'unit� de REF ?
 *    				  --> oui --> FIN, retourner QTY = QTY_Work
 *    				  --> non --> QTY_Work = calculerQTY (QTY_Work(unit, Montant), unit�e REF)
 *     	
 *     
 *     
 *     
 *     
 *     
 *     
 *     			 	 Next PARENT
 *      				
 *     
 *     			--> Fin des PARENTS
 *     
 *     				       
 *     pour chaques enfants de IN [toty]	
 *    
 *    
 *    									pour chaques parents de totx 
 *    												1-> kilogramme
 *    													est- ce que ce parent diff�re de l'unit� IN actuelle (totx) ? 			
 *    													non --> next parent
 *    													oui --> QTY = QTY convertie en kilogramme	
 *    													
 *    													est-ce que kilogramme est l'unit� de REF ?
 *    													oui --> retourner QTY
 * 					   									non --> calculerQTY (kilogramme, QTY, unit�e REF)
 * 
 *                  				    kilogramme a un enfant? ==> oui ==> decigramme
 *              								    est- ce que ce parent diff�re de l'unit� QTY actuelle ?
 *    												oui--> QTY = QTY convertie en decigramme	
 *    												       est-ce que decigramme est l'unit� de REF ?
 *    												
 *    												non --> decigramme a un parent? ==> oui
 *    
 *    
 *    .../...												
 *    plus de parents
 *    	toty a un enfant ==> totz
 *    
 *    
 *    				
 *    
 *          amont
 *    debut 1
 *    		aval	
 *    
 *    rechercher l'unit� de convertions kilogramme 
 *    
 *    
 *    
 *    ayant un parent gramme
 *    si pas trouv�
 *    echercher les unit� de convertions gramme ayant un parent kilogramme
 *    
 *    
 *    pour chaque filles, on test si unit� fille = unit� ref
 *    
 *    qty_evivalence = qyt UAC * (qty fille (out)/ qty equ(in))
 *    qty_evivalence = 2 * (80/1) = 160 g
 *    
 * 
 *    lorsque l'unit� de l'�quivalence est la m�me que l'unit� de la r�f�rence
 *    on peut faire la seconde partie
 *    
 *    pour chaque fille de la r�f�rence, r�cup�rer
 *    
 *    unit� de la fille + qty_equivalence * (qte fille/ qte ref)
 *    kcal + 160 * (54/100)
 *    
 *    
 * 
 * @author Rodolphe
 *
 */
public class EnergySourceQty {
	
	private boolean isReference; 
	private long id;
	private EnergySource energySource;
	private float quantity;
	private Unity unitMeasure;
	public EnergySource getEnergySource() {
		return energySource;
	}
	public void setEnergySource(EnergySource energySource) {
		this.energySource = energySource;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public Unity getUnitMeasure() {
		return unitMeasure;
	}
	public void setUnitMeasure(Unity unitMeasure) {
		this.unitMeasure = unitMeasure;
	}
	public boolean isReference() {
		return isReference;
	}
	public void setReference(boolean isReference) {
		this.isReference = isReference;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

}





