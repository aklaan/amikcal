package com.rdupuis.amikcal.Qty;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ManagedElement;
import com.rdupuis.amikcal.commons.Manager;
import com.rdupuis.amikcal.commons.Manager_commons;
import com.rdupuis.amikcal.commons.RETURNCODE;
import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.relations.Relation_Manager;
import com.rdupuis.amikcal.unity.Unity;
import com.rdupuis.amikcal.unity.Unity_Manager;

/**
 * Created by rodol on 31/08/2015.
 */
public class Qty_Manager extends Relation_Manager {

    public Qty_Manager(Activity activity) {
        super(activity);
    }

    /**
     * Lecture d'une Qty dans la base de données
     *
     * @param databaseId
     * @return
     */
    @Override
    public Qty load(long databaseId) {
        Qty qty = new Qty();

        if (databaseId == AppConsts.NO_ID) {
            return qty;
        }

        qty.setDatabaseId(databaseId);

        Uri selectUri = ContentUris.withAppendedId(ContentDescriptorObj.View_Qty.VIEW_QTY_BY_ID_URI, databaseId);

        // On crée un curseur pour lire la table des aliments
        Cursor cursor = getActivity().getContentResolver().query(selectUri, null, String.valueOf(databaseId), null, null);

        // On récupère les index des colonnes de la PARTY_REL qui nous intéressent
        final int INDX_AMOUNT = cursor.getColumnIndex(ContentDescriptorObj.View_Qty.Columns.AMOUNT);
        final int INDX_UNITY_ID = cursor.getColumnIndex(ContentDescriptorObj.View_Qty.Columns.UNITY_ID);

        // faire un move First pour positionner le curseur, sinon on pointe sur
        // null
        if (cursor.moveToFirst()) {
            qty.setAmount(cursor.getFloat(INDX_AMOUNT));

            //on recharge l'unitée
            Unity_Manager um = new Unity_Manager(getActivity());
            Unity unity = um.load(cursor.getLong(INDX_UNITY_ID));
            //on affecte l'unitée
            qty.setUnity(unity);
        } else {
            String message = "Qty: " + String.valueOf(databaseId) + " non trouvée";
            Log.e("Qty_Manager", message);


        }
        return qty;
    }

    /**
     * On ne peut pas enregistrer une Qty si l'unitée n'a pas été définie
     * @param element
     * @return
     */
    @Override
    public boolean checkBeforeWriting(ManagedElement element) {
        boolean check = false;
        this.setReturnCode(RETURNCODE.KO);
        Qty qty = (Qty) element;
        if (qty.getUnity().getDatabaseId() == AppConsts.NO_ID) {

            // création d'une boite de dialogue
            new AlertDialog.Builder(this.getActivity()).setTitle("Attention")
                    .setMessage("Vous n'avez pas saisi d'unitée")
                    .setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            /**
                             * Si l'utilisateur clique sur OK
                             * on ne fait rien.
                             * il reste sur l'éditeur et peux modifier sa saisie
                             */
                        }
                    })

                    .setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            /**
                             * Si l'utilisateur clique sur Annuler
                             * on ferme l'éditeur, ce qui annule la saisie
                             */
                            Qty_Manager.this.getActivity().finish();

                        }
                    })

                    .show();


            return check;
        }


        this.setReturnCode(RETURNCODE.OK);
        check = true;

        return check;
    }


    @Override
    public void edit(ManagedElement element) {
//TODO
    }

}
