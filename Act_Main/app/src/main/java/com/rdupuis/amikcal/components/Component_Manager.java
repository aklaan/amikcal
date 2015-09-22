package com.rdupuis.amikcal.components;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.rdupuis.amikcal.Qty.Qty;
import com.rdupuis.amikcal.Qty.Qty_Manager;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ManagedElement;
import com.rdupuis.amikcal.commons.Manager;
import com.rdupuis.amikcal.commons.ManagerBuilder;
import com.rdupuis.amikcal.commons.Manager_commons;
import com.rdupuis.amikcal.components.food.Component_Food;
import com.rdupuis.amikcal.components.move.Component_Move;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.energy.Energy_Manager;
import com.rdupuis.amikcal.energy.Food;
import com.rdupuis.amikcal.energy.PhysicalActivity;
import com.rdupuis.amikcal.relations.REL_TYP_CD;
import com.rdupuis.amikcal.relations.Relation_Manager;

/**
 * Component_Action est un objet qui va permetre à un composant de pouvoir utiliser les
 * possibilités d'une Activity
 * c'est une sorte d'Adapter....??
 */
public class Component_Manager extends Manager_commons {

    public Component_Manager(Activity activity)
    {
        super(activity);
    }




    /***********************************************************************
     * récupérer un Component qui a été stocké dans la Database
     * <p/>
     * on va lire la table des relations pour récupérer le lien COMPONENT, puis
     * en récupérer les ID NRJ / Qty contenues dans ce lien pour recharger les
     * objets
     *
     * @param databaseId
     * @return
     ************************************************************************/

    @Override
    public Component load(long databaseId) {
        Component component = null;

        // si l'id du composant à charger est nul on retourne un composant vide.
        if (databaseId == AppConsts.NO_ID) {
            return null;
        }

        // rechercher la relation "component" à partir de son id.
        Uri request = ContentUris.withAppendedId(ContentDescriptorObj.TB_Party_rel.S01_PARTY_REL_BY_ID_URI, databaseId);

        Cursor cur = getActivity().getContentResolver().query(request, null, null, null, null);

        // On récupère des index des colones à récupérer
        final int REL_TYP_CD = cur.getColumnIndex(ContentDescriptorObj.TB_Party_rel.Columns.REL_TYP_CD);
        final int NRJ_ID = cur.getColumnIndex(ContentDescriptorObj.TB_Party_rel.Columns.PARTY_1);
        final int QTY_ID = cur.getColumnIndex(ContentDescriptorObj.TB_Party_rel.Columns.PARTY_2);

        // Faire un move First pour positionner le curseur, sinon on pointe sur
        // null
        if (cur.moveToFirst()) {

            //Etape 1 - on récupère l'énergie²
            EnergySource nrj = new EnergySource();
            Manager manager = ManagerBuilder.build(this.getActivity(), nrj);
            nrj = (EnergySource) manager.load(cur.getLong(NRJ_ID));

            // Etape 2 - on récupère la Qty
            Qty qty = new Qty();
            manager = ManagerBuilder.build(this.getActivity(), qty);
            qty = (Qty) manager.load(cur.getLong(QTY_ID));

            //on récupère le mapping des type de relation pour faire la correspondance
            // entre le code stocké dans la database et sa signification fonctionnelle
            AppConsts.REL_TYP_CD_MAP map = new AppConsts.REL_TYP_CD_MAP();
            REL_TYP_CD rel_typ_cd = map._in.get(cur.getString(REL_TYP_CD));

            switch (rel_typ_cd) {

                case CFOOD:
                    component = new Component_Food((Food) nrj, qty);
                    break;
                case CMOVE:
                    component = new Component_Move((PhysicalActivity) nrj, qty);
                    break;
                default: // nothing
            }

            //on réasigne l'Id de la database
            component.setDatabaseId(databaseId);

        }
        cur.close();


        return  component;
    }

}
