package com.rdupuis.amikcal.components;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;

import com.rdupuis.amikcal.Qty.Qty;
import com.rdupuis.amikcal.Qty.Qty_Manager;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ManagedElement;
import com.rdupuis.amikcal.commons.Manager;
import com.rdupuis.amikcal.commons.ManagerBuilder;
import com.rdupuis.amikcal.commons.Manager_commons;
import com.rdupuis.amikcal.commons.RETURNCODE;
import com.rdupuis.amikcal.components.food.Component_Food;
import com.rdupuis.amikcal.components.move.Component_Move;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.energy.Energy_Manager;
import com.rdupuis.amikcal.energy.Food;
import com.rdupuis.amikcal.energy.PhysicalActivity;
import com.rdupuis.amikcal.relations.REL_TYP_CD;
import com.rdupuis.amikcal.relations.Relation;
import com.rdupuis.amikcal.relations.Relation_Manager;

/**
 * Component_Action est un objet qui va permetre à un composant de pouvoir utiliser les
 * possibilités d'une Activity
 * c'est une sorte d'Adapter....??
 */
public class Component_Manager extends Relation_Manager {

    public Component_Manager(Activity activity)

    {
        super(activity);
    }


    /**
     * Par rapport à une relation "classique" le composant peut avoir un parent
     * <p/>
     * Component :
     * row_id = 1
     * par_row_id = id de la user activity
     * party1 = id de l'énergy
     * party2 = id de la qty
     * <p/>
     * Qty de ref :
     * row_id = 1
     * par_row_id = id de l'énergy
     * party1 = amount
     * party2 = id de l'unity
     * <p/>
     * energy value
     * row_id
     * par_row_id = qty_reg
     * party1  =50
     * party2  =Kcal
     *
     * @param element
     * @return
     */
    @Override
    public ContentValues getContentValues(ManagedElement element) {
        Component component = (Component) element;
        ContentValues values = super.getContentValues(element);
        // Parent
        values.put(ContentDescriptorObj.TB_Party_rel.Columns.PAR_ROW_ID, component.getParentId());
        return values;
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
        Uri request = ContentUris.withAppendedId(ContentDescriptorObj.TB_Party_rel.SELECT_RELATION_BY_ID_URI, databaseId);

        Cursor cur = getActivity().getContentResolver().query(request, null, null, null, null);

        // On récupère des index des colones à récupérer
        final int REL_TYP_CD = cur.getColumnIndex(ContentDescriptorObj.TB_Party_rel.Columns.REL_TYP_CD);
        final int NRJ_ID = cur.getColumnIndex(ContentDescriptorObj.TB_Party_rel.Columns.PARTY_1);
        final int QTY_ID = cur.getColumnIndex(ContentDescriptorObj.TB_Party_rel.Columns.PARTY_2);
        final int PARENT_ID = cur.getColumnIndex(ContentDescriptorObj.TB_Party_rel.Columns.PAR_ROW_ID);

        // Faire un move First pour positionner le curseur, sinon on pointe sur
        // null
        if (cur.moveToFirst()) {

            //Etape 1 - on récupère l'énergie²
            Energy_Manager manager = new Energy_Manager(this.getActivity());
            EnergySource nrj = manager.load(cur.getLong(NRJ_ID));

            // Etape 2 - on récupère la Qty
            Qty_Manager qty_manager = new Qty_Manager(this.getActivity());
            Qty qty = qty_manager.load(cur.getLong(QTY_ID));

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

            //On réasigne l'Id du parent
            component.setParentId(cur.getLong(PARENT_ID));
        }
        cur.close();


        return component;
    }

    @Override
    public boolean checkBeforeWriting(ManagedElement element) {
        this.setReturnCode(RETURNCODE.KO);
        boolean check = false;
        Component component = (Component) element;

        if (component.getEnergy().getDatabaseId() == AppConsts.NO_ID) {

            // création d'une boite de dialogue
            new AlertDialog.Builder(this.getActivity()).setTitle("Attention")
                    .setMessage("Vous n'avez pas saisi d'énergie ")
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
                            Component_Manager.this.getActivity().finish();

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
