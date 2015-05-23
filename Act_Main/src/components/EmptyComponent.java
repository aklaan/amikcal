package components;

import com.rdupuis.amikcal.commons.Relation.REL_TYP_CD;

public class EmptyComponent extends Component {

    public final REL_TYP_CD mRelationClass = REL_TYP_CD.UNDEFINED;

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
	public EmptyComponent() {
		super();
			
	}

	
}
