package com.rdupuis.amikcal.relations;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ManagedElement;
import com.rdupuis.amikcal.commons.Manager;
import com.rdupuis.amikcal.commons.Manager_commons;
import com.rdupuis.amikcal.commons.RETURNCODE;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.energy.Energy_Food_Manager;
import com.rdupuis.amikcal.energy.Food;
import com.rdupuis.amikcal.useractivity.UserActivity;
import com.rdupuis.amikcal.useractivity.UserActivity_Manager;

/**
 * Created by rodol on 01/09/2015.
 */
public class Relation_Manager extends Manager_commons {

    public Relation_Manager(Activity activity) {

        super(activity);
        this.setUriInsert(ContentDescriptorObj.TB_Party_rel.INS000_PARTY_REL_URI);
        this.setUriUpdate(ContentDescriptorObj.TB_Party_rel.UP000_PARTY_REL_URI);
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

        // vérifier la présence de la relation
        if (relation.getParty1() != String.valueOf(AppConsts.NO_ID)
                && relation.getParty2() != String.valueOf(AppConsts.NO_ID)) {
            // rechercher si la relation existe déjà pour récupérer son ID
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


    @Override
    public ContentValues getContentValues(ManagedElement element) {
        //Cast de l'élément en Relation
        Relation relation = (Relation) element;

        // On prépare les informations à mettre à jour
        ContentValues values = new ContentValues();

        // Rel_typ_cd
        AppConsts.REL_TYP_CD_MAP rel_typ_cd_map = new AppConsts.REL_TYP_CD_MAP();
        values.put(ContentDescriptorObj.TB_Party_rel.Columns.REL_TYP_CD,
                rel_typ_cd_map._out.get(relation.getRelationClass()));

        // party 1
        values.put(ContentDescriptorObj.TB_Party_rel.Columns.PARTY_1, relation.getParty1());

        // Party 2
        values.put(ContentDescriptorObj.TB_Party_rel.Columns.PARTY_2, relation.getParty2());

        // date de mise à jour
        values.put(ContentDescriptorObj.TB_Party_rel.Columns.LAST_UPDATE, ToolBox.getCurrentTimestamp());


        return values;
    }

}
