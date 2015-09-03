package com.rdupuis.amikcal.commons;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;

import com.rdupuis.amikcal.Qty.Qty;
import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.data.ContentDescriptorObj;

import com.rdupuis.amikcal.useractivity.UserActivity;

public class AmiKcalFactory {

    public static ContentResolver contentResolver;


    public AmiKcalFactory(ContentResolver contentResolver) {
        contentResolver = contentResolver;

    }






    /*********************************************************************************
     * @param activity
     * @return
     ********************************************************************************/

    /**
     * <h1>loadComponent</h1>
     * <p>
     *
     * notes on prend 'id d'une UA UA.Load() <arrayList of UAC> UA.get_UAC()
     *
     * <arrayList of QTY> UA.get_UAC_Equiv()
     *
     * on va rechercher les liens US_UAC
     *
     * UA.get_AUC va utiliser la factory pour cr�er des objets UAC a partir de
     * la DATABASE il faut r�cup�rer la liste des ID UAC de l'UA pour pouvoir
     * recreer les UAC
     *
     * une fonction : r�cup�rer la liste des ID UAC en relation avec une UA
     *
     * on peu aussi passer par une vue :
     *
     * select UA._id ,UAC._id ,EQUIV._id from party_rel as UA inner join
     * party_rel as UAC on UA.rel_typ_cd = "UA_UAC" and UA.party_2 = UAC.party_1
     *
     * * inner join party_rel as EQUIV on UAC.rel_typ_cd = "UAC_EQV" and
     * UA.party_1 = UAC.party_1
     *
     *
     * le chargement s'op�re en 2 phases :
     * <ul>
     * <li>1 - initialiser le composant
     * <li>2 - charger ses equivalences
     * </ul>
     * </p>
     *
     */

    /**
     * Quand j'utilise cette fonction, je suis d�j� sur une UA et j'ai d�ja �t�
     * recherch� ses ID d'UAC
     *
     *
     *
     * @param _id
     *            : Identifiant du composant � recharger
     */
    //
    // public UserActivityComponent load_UAC(Long _id) {
    // // On fabrique l'Uri pour le contentProvider
    // // celle-ci est du style content://xxxxx.xxxxxxxx.xxxxxxx/# o� le di�se
    // // est l'Id � rechercher
    //
    // // cette URI est g�n�rique. on ne sais pas quel type d'UAC on r�cup�re
    // UserActivityComponent mUAC = new UserActivityComponent();
    //
    // if (_id == AppConsts.NO_ID) {
    // return mUAC;
    // }
    //
    // Uri selectUri = ContentUris.withAppendedId(
    // ContentDescriptorObj.View_UAC_Data.VIEW_UAC_DATA_URI, _id);
    //
    // // On cr�e un curseur pour lire la vue
    // Cursor cursor = contentResolver.query(selectUri, null, _id.toString(),
    // null, null);
    //
    // // On r�cup�re les index des colonnes de la vue.
    // final int INDX_REL_TYP_CD = cursor
    // .getColumnIndex(ContentDescriptorObj.View_UAC_Data.Columns.UAC_REL_TYP_CD);
    // final int INDX_NRJ_ID = cursor
    // .getColumnIndex(ContentDescriptorObj.View_UAC_Data.Columns.ENERGY_ID);
    // final int INDX_QTY_ID = cursor
    // .getColumnIndex(ContentDescriptorObj.View_UAC_Data.Columns.QTY_ID);
    //
    // // faire un move First pour positionner le curseur, sinon on pointe sur
    // // null
    // if (cursor.moveToFirst()) {
    //
    // // on charge le mapping des code relations
    // REL_TYP_CD_MAP mapping = new REL_TYP_CD_MAP();
    //
    // // en fonction du type de relation, on va retourner le composant
    // // adequat
    // switch (mapping._in.get(cursor.getString(INDX_REL_TYP_CD))) {
    // case UA_CFOOD:
    // mUAC = new UAFoodComponent();
    // mUAC.setId(_id);
    //
    // break;
    // // pour le moment je ne g�re que les UA_CFOOD
    // default:
    //
    // break;
    // }
    //
    // mUAC.getComponent().setEnergy(
    // this.load_Energy(cursor.getLong(INDX_NRJ_ID)));
    // mUAC.getComponent().setQty(
    // this.load_Qty(cursor.getLong(INDX_QTY_ID)));
    // mUAC.getComponent().setEquivalences(
    // this.load_Equiv(mUAC.getComponent().getQty()));
    //
    // } else {
    // String message = "Composant " + String.valueOf(_id) + " non trouv�";
    // Log.e("loadComponent", message);
    //
    // }
    // cursor.close();
    // return mUAC;
    // }


    /*****************************************************************************************
     * @param qty
     * @return
     ******************************************************************************************/
    // recharger les �quivalences d'une Qty
    // l'�quivalence d'une Qty ne peut �tre qu'une Qty
    // exemple 1 litre = 1 kilogramme
    public ArrayList<Qty> load_Equiv(Qty qty) {
        ArrayList<Qty> equiv_list = new ArrayList<Qty>();

        if (qty.getDatabaseId() == AppConsts.NO_ID) {
            return equiv_list;
        }

        Uri request = ContentUris.withAppendedId(ContentDescriptorObj.View_qty_equiv.VIEW_ALL_QTY_EQUIV_URI,
                qty.getDatabaseId());

        Cursor cur = this.contentResolver.query(request, null, null, null, null);

        final int QTY_ID = cur.getColumnIndex(ContentDescriptorObj.View_qty_equiv.Columns.QTY_ID);
        final int EQUIV_ID = cur.getColumnIndex(ContentDescriptorObj.View_qty_equiv.Columns.REL_ID);
        final int EQUIV_QTY_ID = cur.getColumnIndex(ContentDescriptorObj.View_qty_equiv.Columns.QTY_EQUIV_ID);
        // faire un move First pour positionner le pointeur, sinon on pointe sur
        // null
        if (cur.moveToFirst()) {

            do {
                Qty eq = new Qty();
                // eq.setId(cur.getLong(EQUIV_ID));
                // eq.setQuantityIn(qty);
                // eq.setQuantityOut(this.load_Qty(cur.getLong(EQUIV_QTY_ID)));
                equiv_list.add(eq);

            } while (cur.moveToNext());
        }
        cur.close();

        return equiv_list;

    }

    /*****************************************************************************************
     *
     ******************************************************************************************/
    // recharger toutes les équivalences d'un composant
    public ArrayList<Component> load_Equiv(Component component) {
        ArrayList<Component> equiv_list = new ArrayList<Component>();

        Uri request = ContentUris.withAppendedId(
                ContentDescriptorObj.View_Component_equiv.VIEW_ALL_COMPONENT_EQUIV_URI, component.getDatabaseId());

        Cursor cur = this.contentResolver.query(request, null, null, null, null);

        final int EQUIV_ID = cur.getColumnIndex(ContentDescriptorObj.View_Component_equiv.Columns.REL_ID);
        final int COMP2_ID = cur.getColumnIndex(ContentDescriptorObj.View_Component_equiv.Columns.COMP2_ID);

        // faire un move First pour positionner le pointeur, sinon on pointe sur
        // null
        // on charge le mapping des CLASS

        if (cur.moveToFirst()) {

            do {

                //Component equivalence = this.load_Component(cur.getLong(COMP2_ID));
                //equiv_list.add(equivalence);

            } while (cur.moveToNext());
        }
        cur.close();

        return equiv_list;

    }

    /*****************************************************************************************
     * Enregister une UA dans la database
     * <p/>
     * mettre � jour la TB_UserActivities si l'UA poss�de des UAC pour chaque
     * UAC, sauver l'UAC *
     ******************************************************************************************/







} // * end-class
