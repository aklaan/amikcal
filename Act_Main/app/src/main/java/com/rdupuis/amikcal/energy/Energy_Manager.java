package com.rdupuis.amikcal.energy;

import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;

import android.widget.Toast;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.Manager;
import com.rdupuis.amikcal.components.Component_Reference;
import com.rdupuis.amikcal.components.Component_Reference_Manager;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.data.writers.DBWriter_EnergySource;

/**
 * Created by rodol on 31/08/2015.
 */
public class Energy_Manager extends Manager {

    public Energy_Manager(Activity activity) {
        super(activity);
    }

    public void save(EnergySource energySource) {
        DBWriter_EnergySource dbw = new DBWriter_EnergySource(getActivity().getContentResolver(), energySource);
        dbw.save();
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
    public EnergySource load(long databaseId) {

        EnergySource energy = null;

        if (databaseId == AppConsts.NO_ID) {

            // il faudrait retourner une exception maison du style badId
            // Excetpion
            return energy;
        }

        Uri selectUri = ContentUris.withAppendedId(ContentDescriptorObj.TB_Energies.SELECT_ONE_ENERGIES_BY_ID_URI, databaseId);
        Cursor cursor = getActivity().getContentResolver().query(selectUri, null, null, null, null);

        final int INDX_NRJ_NAME = cursor.getColumnIndex(ContentDescriptorObj.TB_Energies.Columns.NAME);

        final int INDX_NRJ_CLASS = cursor.getColumnIndex(ContentDescriptorObj.TB_Energies.Columns.CLASS);

        final int INDX_NRJ_STRUCTURE = cursor.getColumnIndex(ContentDescriptorObj.TB_Energies.Columns.STRUCTURE);

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

                    // il faut g�rer exception

            }

            energy.setDatabaseId(databaseId);
            energy.setName(cursor.getString(INDX_NRJ_NAME));

            /** Charger le composant de référence
             *
             *
             * Le composant de référence est par exemple : 1OO g de Pain
             * c'est la référence pour laquelle on va se baser pour exprimer :
             *  - l'équivalence en calories
             *  ex: 100 g de pain = 120Kcal
             *
             *  - la répartion des composants
             *  ex: dans 100 g de pain on a 50g de glucides

             */


            Component_Reference_Manager cmr = new Component_Reference_Manager(getActivity());
            Component_Reference refComponent = cmr.load(energy);
            //energy.setReferenceComponent(refComponent);


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

}
