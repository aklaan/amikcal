package com.rdupuis.amikcal.Food;

import java.util.ArrayList;
import com.rdupuis.amikcal.commons.FoodComponent;
import com.rdupuis.amikcal.energy.EnergySource;

public class Food extends EnergySource {

	public static enum STRUCTURE {
		SOLID, LIQUID
	}

	private STRUCTURE mStructure;
	
		
    // la liste des composants que je voulais mettre en place est en fait une
	// liste d'équivalences par rapport à la quantité de référence
	// ex :100g = 56 Lip, 45 Glu....etc
	// cette liste d'équivalence étant universelle à toutes les NRJ je n'ai plus besoin 
	// de créer un liste ici. elle est dans la classe mère.
	
	
	// Un aliment est composé de protéine / glucide /lipide /minéraux 
	// /vitamines..
	// Ici on pourrait seulement dire vrai ou faux sur la présence des 
	// composants car on a pas de notion de quantité 
	// 

	// pour le projet, le vrai/faux sur la présence des composants n'est pas
	// necessaire pour le moment
	// inutile donc de le gérer.
	// par exemple, on pourrait dire que dans l'eau il n'y a pas de lipides et
	// donc
	// si on essai d'alimenter le nombre de lipide un contrôle pourrait nous en
	// enpêcher.
	// c'est un peu lourd à gérer......
	// pour l'instant on va s'en passer.....

	// constructeur par défaut
	public Food() {
		//la consomation d'aliment va augmenter le nombre de calories 
		//la classe FOOD induit que ce type d'énergie va apporter de l'énergie au corp 
		this.setEffect(EnergySource.NRJ_EFFECT.GIVE);
	    
	}

	public STRUCTURE getStructure() {
		return mStructure;
	}

	public void setStructure(STRUCTURE mStrucure) {
		this.mStructure = mStrucure;
	}

	
}
