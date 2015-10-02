package com.rdupuis.amikcal.components;

import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.rdupuis.amikcal.Qty.Qty;
import com.rdupuis.amikcal.Qty.Qty_Manager;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.components.food.Component_Food;
import com.rdupuis.amikcal.components.food.Component_Food_Manager;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.useractivity.UserActivity;
import com.rdupuis.amikcal.useractivity.lunch.UserActivity_Lunch;

import java.util.ArrayList;

/**
 * Created by rodol on 23/09/2015.
 */
public class Equivalence_List_Builder {
    Activity activity;

    public Equivalence_List_Builder(Activity activity) {
        this.activity = activity;
    }

    private Activity getActivity() {
        return this.activity;
    }

    private ArrayList<Qty> getQty_Equivalence_List(Qty qty) {
        ArrayList<Qty> list = new ArrayList<Qty>();

        if (qty.getDatabaseId() != AppConsts.NO_ID) {
            long _id = qty.getDatabaseId();
            Uri selectUri = ContentUris.withAppendedId(
                    ContentDescriptorObj.View_qty_equiv.URI_BASE_VIEW_QTY_EQUIV, _id);

            // On crée un curseur pour lire la vue
            Cursor cursor = this.getActivity().getContentResolver().query(selectUri, null, String.valueOf(_id), null, null);

            // On récupère les index des colonnes de la vue.
            final int INDX_COMP_ID = cursor.getColumnIndex(ContentDescriptorObj.View_qty_equiv.Columns.QTY_EQUIV_ID);

            Qty_Manager manager = new Qty_Manager(getActivity());

            // faire un move First pour positionner le curseur, sinon on pointe sur null

            if (cursor.moveToFirst()) {

                do {


                    Qty equivalence = manager.load(cursor.getLong(INDX_COMP_ID));
                    list.add(equivalence);

                } while (cursor.moveToNext());
            } else {
                String message = "Qty: " + String.valueOf(_id) + " non trouvé";
                Log.e("Equiv_List_Builder", message);

            }
            cursor.close();

        }
        return list;
    }

    //les équivalences d'un composant sont d'autres composants ??
    //Exemple :  1 verre de lait =  25 cl de lait
    // ==> FAUX c'est une équivalence de QTY
    //      ==> 1 verre  =25 cl

    // 100g de pain = 78 Kcl d'énergie
    //      ==> oui, c'est une équivalence énergétique mais elle est déduite de l'aliment de base
    //      ==> 250 g de pain = prorata d'énergie pour 100 g de pain (100 g étant la QyREF)

    // 100g de pain = 35 g de glucides
    //      ==> Non, ce n'est pas une équivalence
    //      ==> c'est une relation de composition
    //      ==> 100g de pain est composé (en partie) de 35g de glucides


    // on constate qu'il n'y a pas d'équivalences pour un composant "classique"
    // en revanche on en a pour l'énergie et sa QtyRef qui forment un composant
    // particulier.

    // on pourrait faire une série de lien dans la table des relations
    // clef type    party1  party2
    //  1    QTY     100      (1)g
    //  2    QTY     75      (2)Kcal
    //  3    CREF   (1)pain   2

    //mais on va plutot stocker un lien vers la Qty d'énergie dans la table des energie
    // energy.id
    // energy.name
    // energy.qtyRef --> Party_rel --> Qty : 100 g
    // energy.qtyNrj --> Party_rel --> Qty : 56 Kcal





}