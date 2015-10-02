package com.rdupuis.amikcal.unity;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;


import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ManagedElement;
import com.rdupuis.amikcal.commons.Manager_commons;
import com.rdupuis.amikcal.commons.RETURNCODE;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.energy.EnergySource;

/**
 * Created by rodol on 27/08/2015.
 */
public final class Unity_Manager extends Manager_commons {

    public Unity_Manager(Activity activity) {
        super(activity);
        this.setUriUpdate(ContentDescriptorObj.TB_Units.URI_UPDATE_UNIT);
        this.setUriInsert(ContentDescriptorObj.TB_Units.URI_INSERT_UNIT);
    }

    @Override
    public void edit(ManagedElement element) {
        //Cast de l'élément en Component_Food
        Unity unity = (Unity) element;

        //Préparation de Intent
        Intent intent = new Intent(getActivity(), Act_UnitOfMeasureEditor.class);
        intent.putExtra(Act_UnitOfMeasureEditor.INPUT____UNITY, unity);

        //Start de l'activity
        getActivity().startActivityForResult(intent, R.integer.ACTY_UNIT);
    }


    /*********************************************************************************
     * Retourne une unitée de mesure stockée dans la base à partir de son id.
     *
     * @param databaseId
     * @return (Unity) un objet "Unitée de mesure"
     * @autor Rodolphe Dupuis
     * @since 01-06-2012
     *********************************************************************************/

    @Override
    public Unity load(long databaseId) {

        Unity u = new Unity();

        if (databaseId == AppConsts.NO_ID) {
            return u;
        }

        u.setDatabaseId(databaseId);

        Uri selectUri = ContentUris.withAppendedId(ContentDescriptorObj.TB_Units.URI_SELECT_UNIT, databaseId);
        Cursor cursor = getActivity().getContentResolver().query(selectUri, null, null, null, null);

        final int INDX_NAME = cursor.getColumnIndex(ContentDescriptorObj.TB_Units.Columns.LONG_NAME);
        final int INDX_SYMBOL = cursor.getColumnIndex(ContentDescriptorObj.TB_Units.Columns.SHORT_NAME);
        final int INDX_CLASS = cursor.getColumnIndex(ContentDescriptorObj.TB_Units.Columns.CLASS);

        // faire un move First pour positionner le pointeur, sinon on pointe sur
        // null
        if (cursor.moveToFirst()) {

            u.setLongName(cursor.getString(INDX_NAME));
            u.setShortName(cursor.getString(INDX_SYMBOL));

            AppConsts.UNIT_CLASS_MAP unit_class_map = new AppConsts.UNIT_CLASS_MAP();
            u.setUnityClass(unit_class_map._in.get(cursor.getString(INDX_CLASS)));

        } else {
            //Toast.makeText(this.mActivity, "Unity inconnue", Toast.LENGTH_LONG).show();
        }
        cursor.close();
        return u;

    }

    @Override
    public ContentValues getContentValues(ManagedElement element) {
        Unity unity = (Unity) element;
        ContentValues values = new ContentValues();
// LongName
        values.put(ContentDescriptorObj.TB_Units.Columns.LONG_NAME, unity.getLongName());

        // ShortName
        values.put(ContentDescriptorObj.TB_Units.Columns.SHORT_NAME, unity.getShortName());

        // Alimentation de la classe d'unitée
        AppConsts.UNIT_CLASS_MAP unit_class_map = new AppConsts.UNIT_CLASS_MAP();
        values.put(ContentDescriptorObj.TB_Units.Columns.CLASS, unit_class_map._out.get(unity.getUnityClass()));

        // date de mise à jour
        values.put(ContentDescriptorObj.TB_Units.Columns.LAST_UPDATE, ToolBox.getCurrentTimestamp());

        return values;

    }

    @Override
    public boolean checkBeforeWriting(ManagedElement element) {
        this.setReturnCode(RETURNCODE.KO);
        boolean check = false;
        Unity unity = (Unity) element;

        if (unity.getLongName().isEmpty() || unity.getShortName().isEmpty()) {
            String zone = (unity.getLongName().isEmpty()) ? "Nom" : "Symbole";

            // création d'une boite de dialogue
            new AlertDialog.Builder(this.getActivity()).setTitle("Attention")
                    .setMessage("Vous n'avez pas le " + zone + " de l'unitée")
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
                            Unity_Manager.this.getActivity().finish();

                        }
                    })

                    .show();

            return check;
        }

        //Si tout c'est bien passé on signale que le manager est OK
        this.setReturnCode(RETURNCODE.OK);
        check = true;
        return check;
    }


}
