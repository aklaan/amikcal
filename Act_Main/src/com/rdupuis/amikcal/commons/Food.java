package com.rdupuis.amikcal.commons;

import java.util.ArrayList;

import com.rdupuis.amikcal.energy.EnergySource;

public class Food extends EnergySource {

	public static enum FoodComponent{
		LIPIDS,GLUCIDS,PROTEINS,VITAMINS
	}
	
    private ArrayList<FoodComponent> FoodComponentsList;
    // boisson...

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
		this.setEnergyType(EnergyType.POSITIVE);
	    this.FoodComponentsList = new ArrayList<FoodComponent>();
	}

	
}
