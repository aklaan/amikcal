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
 *      6           5	    0.8  toty         pomme      equiv en unité internationale
 *            
 *      
 *      
 *      7           -	       1 U1 (kilogramme)   -          Relation entre 2 unitées
 *      8           -	      10 U2 (decigramme)   U1         Relation entre 2 unitées
 *      9           -	     100 U3 (centigramme)  U2         Relation entre 2 unitées
 *     10           -	    1000 U4 (gramme)       U3         Relation entre 2 unitées 
 *     
 *     11           -	       1 U5 (totx)         -          Relation entre 2 unitées
 *     12          11	      10 U6 (toty)         -          Relation entre 2 unitées
*      13          12	     100 U7 (totZ)         -          Relation entre 2 unitées

 *     14           - 	    0,21 U1                U5-        Relation entre 2 unitées
 *     
 *     * 
 * l'utilisateur a mangé 2 pommes moyennes
 * on veut savoir combien ça représente de kcal
 * 
 * 
 * première partie déterminer la quantité d'éngergie de UAC
 * exprimé dans la même unitée que la référence de l'énergie
 * 
 * 1- aller chercher la quantité de références principale pour "pomme"
 *    energy_id = pomme + type = ref principale
 *    on récupère l'enregistrement [1] --> on sait que l'unitée de ref est [gramme] et que les équivalences kcal/lip..etc en découlent
 * 
 * 
 * 2 - Objectif :  calculer la quantité d'énergie dans la même unitée que l'unitée de REF
 *   
 *    
 *    Est ce que l'unité UAC est une unité perso ou internationale ?
 *    
 *    si c'est une unité perso, il faut aller chercher la correspondance en unitée internationale.
 *    elle, doit forcément exister car l'utilisateur l'a créé personellement. 
 *    
 *    Phase 1
 *    ----------------------
 *    chercher dans les references perso celle disponibles pour "pomme + moyenne"
 *    
 *    (elle existe forcement car l'utilisateur a pu opter pour ce choix de mesure.
 *    quand on renseigne une unitée de mesure perso, on doit obligatoirement demander
 *    a l'utilisateur de renseigner une equivalence dans une unitée internationale (g, cl, dcl, kg, livre...)
 *    )
 *    
 *    type= ref perso  / energy=pomme / unit= moyenne
 *    ==> on trouve [5]
 *    
 *    aller chercher la fille de [5]
 *    (A prioris il n'y a pas de raison d'en avoir plus d'une)
 *    ==> on trouve [6]
 *    c'est l'équivalence qui doit être forcément exprimé avec une unitée internationale
 *    on voit que 1 pomme moyenne c'est 0.8 toty
 *    
 *   
 *       
 *    Phase 2
 *    -------------------------
 *    on recherche dans les conversions internationales si on ne peux pas 
 *    trouver une conversion de toty en gramme
 *    
 *    cela revient à trouver la chaine de conversion toty ==> .../... ==> .../... ==> gramme
 *    
 *    QTY = qté UAC = structure [montant;unitée]
 *    
 *    fonction QTY calculerQTY (QTY:QTY_IN(unit; Montant), unitée REF)
 *                                     ((toty;2*0,8), gramme) 
 *    	est-ce que QTY_IN.Unit (toty) == REF (gramme) ?
 *    	--> oui : retourner QTY_IN
 *      --> sinon :
 *             pour chaques PARENT de QTY_IN.Unit
 *    	          1-Trouve PARENT [totx] 	
 *    		
 *    		          Est-ce que PARENT diffère de QTY_IN.Unit actuelle ?
 *    		          --> non -> next PARENT
 *       			  --> oui -> QTY_Work = QTY_IN convertie en PARENT [totx]
 *    				             0,16 toty = 0,016 totx
 *    
 *    				  Est-ce que PARENT [totx] est l'unité de REF (gramme) ?
 *    				  --> oui --> Return QTY_Work (fin de la fonction)
 *    				  --> non --> QTY_Work = calculerQTY (QTY:QTY_work, Unit:REF)
 *     	 *     
 *     								est-ce que QTY.Unit (totx) == REF (gramme)?
 *    								--> oui : retourner QTY
 *     								--> non :
 *             								  pour chaques PARENT de QTY.Unit
 *    	          								1-Trouve PARENT [totx] 	
 *    		
 *    		          Est-ce que PARENT diffère de QTY.Unit actuelle ?
 *    		          --> non -> next PARENT
 *       			  --> oui -> QTY_Work = QTY convertie en PARENT [totx]
 *    				   
 *    				  Est-ce que PARENT [totx] est l'unité de REF ?
 *    				  --> oui --> FIN, retourner QTY = QTY_Work
 *    				  --> non --> QTY_Work = calculerQTY (QTY_Work(unit, Montant), unitée REF)
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
 *    													est- ce que ce parent diffère de l'unité IN actuelle (totx) ? 			
 *    													non --> next parent
 *    													oui --> QTY = QTY convertie en kilogramme	
 *    													
 *    													est-ce que kilogramme est l'unité de REF ?
 *    													oui --> retourner QTY
 * 					   									non --> calculerQTY (kilogramme, QTY, unitée REF)
 * 
 *                  				    kilogramme a un enfant? ==> oui ==> decigramme
 *              								    est- ce que ce parent diffère de l'unité QTY actuelle ?
 *    												oui--> QTY = QTY convertie en decigramme	
 *    												       est-ce que decigramme est l'unité de REF ?
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
 *    rechercher l'unité de convertions kilogramme 
 *    
 *    
 *    
 *    ayant un parent gramme
 *    si pas trouvé
 *    echercher les unité de convertions gramme ayant un parent kilogramme
 *    
 *    
 *    pour chaque filles, on test si unité fille = unité ref
 *    
 *    qty_evivalence = qyt UAC * (qty fille (out)/ qty equ(in))
 *    qty_evivalence = 2 * (80/1) = 160 g
 *    
 * 
 *    lorsque l'unité de l'équivalence est la même que l'unité de la référence
 *    on peut faire la seconde partie
 *    
 *    pour chaque fille de la référence, récupérer
 *    
 *    unité de la fille + qty_equivalence * (qte fille/ qte ref)
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





