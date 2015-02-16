package com.rdupuis.amikcal.commons;

import java.util.ArrayList;

import com.rdupuis.amikcal.energy.EnergySource;

public class Food extends EnergySource {

	public static enum FoodComponent{
		LIPIDS,GLUCIDS,PROTEINS,VITAMINS
	}
	
    private ArrayList<FoodComponent> FoodComponentsList;
    // boisson...

	// Un aliment est compos� de prot�ine / glucide /lipide /min�raux 
	// /vitamines..
	// Ici on pourrait seulement dire vrai ou faux sur la pr�sence des 
	// composants car on a pas de notion de quantit� 
	// 

	// pour le projet, le vrai/faux sur la pr�sence des composants n'est pas
	// necessaire pour le moment
	// inutile donc de le g�rer.
	// par exemple, on pourrait dire que dans l'eau il n'y a pas de lipides et
	// donc
	// si on essai d'alimenter le nombre de lipide un contr�le pourrait nous en
	// enp�cher.
	// c'est un peu lourd � g�rer......
	// pour l'instant on va s'en passer.....

	// constructeur par d�faut
	public Food() {
		//la consomation d'aliment va augmenter le nombre de calories 
		this.setEnergyType(EnergyType.POSITIVE);
	    this.FoodComponentsList = new ArrayList<FoodComponent>();
	}

	
}
