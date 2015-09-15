package com.rdupuis.amikcal.Qty;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ManagedElement;
import com.rdupuis.amikcal.commons.Manager;
import com.rdupuis.amikcal.commons.Manager_commons;
import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.unity.Unity;
import com.rdupuis.amikcal.unity.Unity_Manager;

/**
 * Created by rodol on 31/08/2015.
 */
public class Qty_Manager extends Manager_commons {

    Activity mActivity;

    public Qty_Manager(Activity activity) {
        super(activity);

    }

    @Override
    public long save(ManagedElement element) {
        Qty qty = (Qty) element;
        DBWriter_Qty dbw = new DBWriter_Qty(getActivity().getContentResolver(), qty);
        dbw.save();
        return 0;
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
            return null;
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


            Unity_Manager um = new Unity_Manager(getActivity());
            Unity unity = um.load(cursor.getLong(INDX_UNITY_ID));

        } else {
            String message = "Qty: " + String.valueOf(databaseId) + " non trouvée";
            Log.e("Qty_Manager", message);


        }
        return  qty;
    }


    @Override
    public void edit(ManagedElement element) {

    }

    @Override
    public ContentValues getContentValues(ManagedElement element){

        return null;
    }
}
