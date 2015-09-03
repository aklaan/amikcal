package com.rdupuis.amikcal.components;

import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.rdupuis.amikcal.Qty.Qty;
import com.rdupuis.amikcal.Qty.Qty_Manager;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.energy.EnergySource;

public class Component_Reference_Manager extends Component_Manager_Commons {

    /**
     * Constructeur
     */

    public Component_Reference_Manager(Activity activity) {

        super(activity);
    }

    @Override
    public void save(Component component) {

    }


    @Override
    public void delete(Component component) {

    }


    @Override
    public void edit(Component component) {

    }

    /*****************************************************************************
     * <h1>load_ComponentReference(long nrj_id)</h1>
     * <p>
     * Permet de retourner le composant de référence associé à une source d'énergie
     * </p>
     ****************************************************************************/
    public Component_Reference load(EnergySource energy) {

        Component_Reference component = new Component_Reference();

        //cas où on est en train de créer une nouvelle énergie
        //on retourne un composant vide, mais initialisé
        if (energy.getDatabaseId() == AppConsts.NO_ID) {
            return component;
        }

        //Si l'énergie est déja connue dans la databse
        // on passe par la vue View_NRJ_Qty_link
        // elle nous permet d'avoir directement des info necessaires
        // id energy =>id relation => id Qty

        Uri selectUri = ContentUris.withAppendedId(
                ContentDescriptorObj.View_NRJ_ComponentRef.VIEW_COMPONENT_REF_BY_NRJ_ID_URI, energy.getDatabaseId());
        Cursor cursor = getActivity().getContentResolver().query(selectUri, null, null, null, null);

        // id de la relation QTY
        final int INDX_QTY_ID = cursor.getColumnIndex(ContentDescriptorObj.View_NRJ_ComponentRef.Columns.QTY_ID);

        // faire un move First pour positionner le pointeur, sinon on pointe sur
        // null

        if (cursor.moveToFirst()) {

            //Charger l'énergie
            EnergySource e = EnergyFactory.load_Energy(cursor.getLong(INDX_QTY_ID));

            // Charger la Qty
            Qty_Manager qty_manager = new Qty_Manager(getActivity());
            Qty q = qty_manager.load(cursor.getLong(INDX_QTY_ID));

            //créer le composant
            component = new Component_Reference(e, q);

            //réattribuer son datanbase ID
            component.setDatabaseId(cursor.getLong(INDX_QTY_ID));
        } else {
            Toast.makeText(getActivity(), "Component référence inconnu", Toast.LENGTH_LONG).show();
        }

        cursor.close();

        return component;
    }

}
