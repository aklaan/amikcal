package com.rdupuis.amikcal.relations;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ManagedElement;
import com.rdupuis.amikcal.commons.Manager;
import com.rdupuis.amikcal.commons.Manager_commons;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.data.ContentDescriptorObj;

/**
 * Created by rodol on 01/09/2015.
 */
public  class Relation_Manager extends Manager_commons {

public Relation_Manager(Activity activity, Relation relation){

    super(activity,relation);
}

    /*****************************************************************************************
     * Enregister une relation dans la database
     * <p/>
     * note : on ne doit pas vérifier si la relation existe déjà ici. car pour
     * les QTY ça n fonctionne pas. par exemple si on a 10g de pain pour UA1 et
     * que l'on met aussi 10g de pain pour l'UA2 il ne faut pas que les 2 UA
     * pointent sur la même Qty.
     ******************************************************************************************/
    @Override
    public long save() {
Relation relation = (Relation) getElement();
        long _id = relation.getDatabaseId();
        // ----------------------------------------------------------------------------
        // On prépare les informations à mettre à jour
        ContentValues val = new ContentValues();

        // Rel_typ_cd
        AppConsts.REL_TYP_CD_MAP rel_typ_cd_map = new AppConsts.REL_TYP_CD_MAP();
        val.put(ContentDescriptorObj.TB_Party_rel.Columns.REL_TYP_CD,
                rel_typ_cd_map._out.get(relation.getRelationClass()));

        // party 1
        val.put(ContentDescriptorObj.TB_Party_rel.Columns.PARTY_1, relation.getParty1());

        // Id unity
        val.put(ContentDescriptorObj.TB_Party_rel.Columns.PARTY_2, relation.getParty2());

        // date de mise à jour
        val.put(ContentDescriptorObj.TB_Party_rel.Columns.LAST_UPDATE, ToolBox.getCurrentTimestamp());

        // Sauver la relation
        if (relation.getDatabaseId() == AppConsts.NO_ID) {
            Uri result = getActivity().getContentResolver().insert(
                    ContentDescriptorObj.TB_Party_rel.INS000_PARTY_REL_URI, val);
           _id = Long.parseLong(result.getLastPathSegment());

        } else {

            Uri uriUpdate = ContentUris.withAppendedId(ContentDescriptorObj.TB_Party_rel.UP000_PARTY_REL_URI,
                    relation.getDatabaseId());
            getActivity().getContentResolver().update(uriUpdate, val, String.valueOf(relation.getDatabaseId()), null);
        }

        // il faut récupérer l'id de lenregistrement que l'on vient de créer
        return _id;
    }


    /*****************************************************************************************
     * verifier l'existence d'une relation dans la database
     ******************************************************************************************/
    private Boolean exists(Relation relation) {

        AppConsts.REL_TYP_CD_MAP map = new AppConsts.REL_TYP_CD_MAP();
        String relationClass = map._out.get(relation.getRelationClass());
        String firstParty = String.valueOf(relation.getParty1());
        String secondParty = String.valueOf(relation.getParty2());
        String searchedRelation = relationClass + "x" + firstParty + "x" + secondParty;

        // v�rifier la pr�sence de la relation
        if (relation.getParty1() != String.valueOf(AppConsts.NO_ID)
                && relation.getParty2() != String.valueOf(AppConsts.NO_ID)) {
            // rechercher si la relation existe d�j� pour r�cup�rer son ID
            Uri request = ContentDescriptorObj.TB_Party_rel.SEARCH_RELATION_URI.buildUpon()
                    .appendPath(searchedRelation).build();
            Cursor cur = getActivity().getContentResolver().query(request, null, null, null, null);

            final int RELATION_ID = cur.getColumnIndex(ContentDescriptorObj.TB_Party_rel.Columns.ID);

            // faire un move First pour positionner le pointeur, sinon on pointe
            // sur null
            // Si la relation existe on va passer dans le if pour r�cup�rer l'ID
            // sinon on passe directement au close curseur
            if (cur.moveToFirst()) {

                relation.setDatabaseId(cur.getLong(RELATION_ID));
            }
            cur.close();

        }
        // si l'id de la relation n'est pas nul, alors la relation existe.
        return (relation.getDatabaseId() != AppConsts.NO_ID);
    }


}
