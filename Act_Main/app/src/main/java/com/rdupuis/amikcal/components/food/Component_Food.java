package com.rdupuis.amikcal.components.food;

import android.os.Parcel;
import android.os.Parcelable;

import com.rdupuis.amikcal.Qty.Qty;
import com.rdupuis.amikcal.components.Component_Generic;
import com.rdupuis.amikcal.energy.Food;
import com.rdupuis.amikcal.relations.REL_TYP_CD;

public class Component_Food extends Component_Generic {


    /**
     * un composant Aliment Food c'est par exemple
     * un bol de soupe
     * 100 g de chocolat
     * 1 verre de vin
     *
     * c'est une relation entre une source d'énergie + une quantité exprimée avec un nombre et une
     * untiée de mesure
     *
     * ici la particularité c'est que par rapport à UserActivityComponent la
     * source d'énergie est forcément un aliment de type FOOD.
     *
     * par déduction, on pourra calculer la quantitée de food components... si
     * l'information est présente dans les équivalences.
     *
     * exemple : 100g de pommes = w kcal 100g de pommes = x lip 100g de pommes =
     * y glu 100g de pommes = z pro
     *
     * on remarque que 100g de pomme c'est comme une UAC mais sans parent
     */

    /**
     * Constructeur
     */

    public Component_Food(Food food, Qty qty) {
        super();
        this.setEnergy(food);
        this.setQty(qty);
    }


    public Component_Food() {
        super();
        this.setEnergy(new Food());
    }


    public float getNbkcal() {

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
        return 0f;
    }


    /**
     * un composant FOOD est une relation de type qty - énergie de type Food
     *
     * @return
     */
    @Override
    public REL_TYP_CD getRelationClass() {
        return REL_TYP_CD.CFOOD;
    }

    public Component_Food(Parcel parcel) {
        super(parcel);

    }



    public static final Parcelable.Creator<Component_Food> CREATOR = new Parcelable.Creator<Component_Food>() {
        @Override
        public Component_Food createFromParcel(Parcel in) {
            return new Component_Food(in);
        }

        @Override
        public Component_Food[] newArray(int size) {
            return new Component_Food[size];

        }

    };

}
