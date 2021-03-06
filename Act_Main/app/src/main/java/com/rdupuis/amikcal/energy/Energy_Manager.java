package com.rdupuis.amikcal.energy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;

import android.widget.Toast;

import com.rdupuis.amikcal.Qty.Qty;
import com.rdupuis.amikcal.Qty.Qty_Manager;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ManagedElement;
import com.rdupuis.amikcal.commons.Manager_commons;
import com.rdupuis.amikcal.commons.RETURNCODE;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.data.ContentDescriptorObj;

/**
 * Created by rodol on 31/08/2015.
 */
public class Energy_Manager extends Manager_commons {


    public Energy_Manager(Activity activity) {
        super(activity);
        this.setUriInsert(ContentDescriptorObj.TB_Energies.INSERT_ENERGY_URI);
        this.setUriUpdate(ContentDescriptorObj.TB_Energies.UPDATE_ENERGY_ID_URI);
    }




        /*********************************************************************************
         * <h1>loadEnergy()</h1>
         * <p/>
         * Retourne une énergie stockée dans la Database à partir de son id.
         *
         * @param databaseId - Identifiant
         * @return (Energy) un objet "énergie"
         * @since 01-06-2012
         ********************************************************************************/
    @Override
    public EnergySource load(long databaseId) {

        EnergySource energy = null;

        if (databaseId == AppConsts.NO_ID) {

            return new EnergySource();
            // il faudrait retourner une exception maison du style badId
            // Excetpion

        }
        Uri selectUri = ContentUris.withAppendedId(ContentDescriptorObj.TB_Energies.SELECT_ONE_ENERGIES_BY_ID_URI, databaseId);
        Cursor cursor = getActivity().getContentResolver().query(selectUri, null, null, null, null);

        final int INDX_NRJ_NAME = cursor.getColumnIndex(ContentDescriptorObj.TB_Energies.Columns.NAME);
        final int INDX_NRJ_CLASS = cursor.getColumnIndex(ContentDescriptorObj.TB_Energies.Columns.CLASS);
        final int INDX_NRJ_STRUCTURE = cursor.getColumnIndex(ContentDescriptorObj.TB_Energies.Columns.STRUCTURE);
        final int INDX_NRJ_QTYREF_ID = cursor.getColumnIndex(ContentDescriptorObj.TB_Energies.Columns.QTY_REF_ID);

        // faire un move First pour positionner le pointeur, sinon on pointe sur
        // null
        if (cursor.moveToFirst()) {

            // on charge le mapping des CLASS
            AppConsts.NRJ_CLASS_MAP map_effect = new AppConsts.NRJ_CLASS_MAP();

            switch (map_effect._in.get(cursor.getString(INDX_NRJ_CLASS))) {

                case FOOD:
                    energy = new Food();

                    // on charge le mapping des NRJ_STRUCTURE
                    AppConsts.STRUCTURE_CD_MAP map_struct = new AppConsts.STRUCTURE_CD_MAP();
                    ((Food) energy).setStructure(map_struct._in.get(cursor.getString(INDX_NRJ_STRUCTURE)));

                    break;
                case PHYSICAL_ACTIVITY:
                    energy = new PhysicalActivity();
                default:

                    // il faut gérer exception

            }

            energy.setDatabaseId(databaseId);
            energy.setName(cursor.getString(INDX_NRJ_NAME));

            /** Charger la Qty de référence
             *
             * La Qty de référence est par exemple : 1OO g  pour du Pain
             * c'est la référence sur laquelle on va se baser pour exprimer :
             *  - l'équivalence en calories
             *  ex: 100 g de pain = 120Kcal
             *
             *  - la répartion des composants
             *  ex: dans 100 g de pain on a 50g de glucides
             *
             *  pour récupérer la Qty de référence
             *  on ajoute un Id de la référence dans la DB
             *

             *
             */


            Qty_Manager manager = new Qty_Manager(this.getActivity());
            Qty qty = manager.load(cursor.getLong((INDX_NRJ_QTYREF_ID)));
            energy.setQtyReference(qty);


        } else {
            Toast.makeText(getActivity(), "Energy Source inconnue", Toast.LENGTH_LONG).show();
        /*
         * e.setCalories(0.0f); e.setProteins(0.0f); e.setGlucids(0.0f);
	     * e.setLipids(0.0f); e.setQuantityReference(0.0f); e.unit = new
	     * Unity();
	     */

        }
        cursor.close();

        return energy;
    }

    @Override
    public ContentValues getContentValues(ManagedElement element) {

        ContentValues val = new ContentValues();
        EnergySource nrj = (EnergySource) element;

        // Alimentation du nom
        val.put(ContentDescriptorObj.TB_Energies.Columns.NAME, nrj.getName());

        // Alimentation de la classe d'énergie
        AppConsts.NRJ_CLASS_MAP class_map = new AppConsts.NRJ_CLASS_MAP();
        val.put(ContentDescriptorObj.TB_Energies.Columns.CLASS, class_map._out.get(nrj.getEnergyClass()));

        // ID de la Qty de référence
        val.put(ContentDescriptorObj.TB_Energies.Columns.QTY_REF_ID, nrj.getQtyReference().getDatabaseId());

        // date de mise à jour
        val.put(ContentDescriptorObj.TB_Energies.Columns.LAST_UPDATE, ToolBox.getCurrentTimestamp());

        return val;
    }

    @Override
    public void edit(ManagedElement element) {

    }
    @Override
    public boolean checkBeforeWriting(ManagedElement element) {
        this.setReturnCode(RETURNCODE.KO);
        boolean check = false;
        EnergySource energySource = (EnergySource) element;

        if (energySource.getName().isEmpty()) {

            // création d'une boite de dialogue
            new AlertDialog.Builder(this.getActivity()).setTitle("Attention")
                    .setMessage("Vous n'avez pas le nom de l'énergie ")
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
                            Energy_Manager.this.getActivity().finish();

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
