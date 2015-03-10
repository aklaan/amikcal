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
	// liste d'�quivalences par rapport � la quantit� de r�f�rence
	// ex :100g = 56 Lip, 45 Glu....etc
	// cette liste d'�quivalence �tant universelle � toutes les NRJ je n'ai plus besoin 
	// de cr�er un liste ici. elle est dans la classe m�re.
	
	
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
		//la classe FOOD induit que ce type d'�nergie va apporter de l'�nergie au corp 
		this.setEffect(EnergySource.NRJ_EFFECT.GIVE);
	    
	}

	public STRUCTURE getStructure() {
		return mStructure;
	}

	public void setStructure(STRUCTURE mStrucure) {
		this.mStructure = mStrucure;
	}

	
}
