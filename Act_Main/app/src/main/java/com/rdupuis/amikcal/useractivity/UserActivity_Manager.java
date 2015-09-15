package com.rdupuis.amikcal.useractivity;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ManagedElement;
import com.rdupuis.amikcal.commons.Manager;
import com.rdupuis.amikcal.commons.ManagerBuilder;
import com.rdupuis.amikcal.commons.Manager_commons;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.components.ComponentFactory;
import com.rdupuis.amikcal.components.Component_Generic;
import com.rdupuis.amikcal.components.Component_Manager;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.relations.Relation_Manager;
import com.rdupuis.amikcal.relations.Relation_UserActivity_vs_Component;
import com.rdupuis.amikcal.useractivity.lunch.UserActivity_Lunch;
import com.rdupuis.amikcal.useractivity.move.UserActivity_Move;
import com.rdupuis.amikcal.useractivity.weight.UserActivity_Weight;

import java.util.ArrayList;

/**
 * Cette classe va permettre de spécialiser des commandes
 * en fonction des différent UserActivity possibles
 * <p/>
 * exemple : pour une activité food, edit va lancer l'éditeur de food
 */
public class UserActivity_Manager extends Manager_commons {

    public UserActivity_Manager(Activity activity) {
        super(activity);

    }


    @Override
    public void edit(ManagedElement element) {
// mettre ici les option communes
    }


    @Override
    public long save(ManagedElement element) {
        UserActivity ua = (UserActivity) element;
        ContentValues val = this.getContentValues(ua);
        long new_id = ua.getDatabaseId();
        if (ua.getDatabaseId() == AppConsts.NO_ID) {
            Uri result = getActivity().getContentResolver().insert(
                    ContentDescriptorObj.TB_UserActivities.INSERT_USER_ACTIVITY_URI, val);
            new_id = Long.parseLong(result.getLastPathSegment());


            if (!ua.getComponentsList().isEmpty()) {


                for (Component component : ua.getComponentsList()) {

                    //ManagerBuilder(getActivity(), component).save();
                    //Relation_UserActivity_vs_Component UAC_rel = new Relation_UserActivity_vs_Component(UA, component);
                    //Relation_Manager rm = new Relation_Manager(getActivity());

                    //if (!this.relation_Exists(UAC_rel)) {
                    //    saveRelation(UAC_rel);
                }
            }


        } else {

            Uri uriUpdate =

                    ContentUris.withAppendedId(ContentDescriptorObj.TB_UserActivities.UPDATE_USER_ACTIVITY_URI, ua.getDatabaseId());
            getActivity().getContentResolver().update(uriUpdate, val, String.valueOf(ua.getDatabaseId()), null);

        }
        // si l'UA poss�de des Composants, on doit les sauver et cr�er les liens
        // UA_Composants


        return new_id;
    }


    /*****************************************************************************
     * load_UA_ComponentsList(UserActivity)
     * <p/>
     * Rechercher les liens UA<->Compnent dans la table des relations puis
     * ajouter les composants
     *
     * @param UA
     * @return
     ****************************************************************************/
    public ArrayList<Component> getComponentsList(UserActivity UA) {
        ArrayList<Component> component_list = new ArrayList<Component>();

        if (UA.getDatabaseId() == AppConsts.NO_ID) {
            return component_list;
        }

        Uri request = ContentUris.withAppendedId(ContentDescriptorObj.View_UA_Comp_link.SELECT_COMP_OF_UA_URI,
                UA.getDatabaseId());

        Cursor cur = getActivity().getContentResolver().query(request, null, null, null, null);

        final int COMPONENT_ID = cur.getColumnIndex(ContentDescriptorObj.View_UA_Comp_link.Columns.COMP_ID);

        // faire un move First pour positionner le pointeur, sinon on pointe sur
        // null
        Component_Generic component = new Component_Generic();
        Manager manager = ManagerBuilder.build(this.getActivity(), component);
        if (cur.moveToFirst()) {
            do {

                component = (Component_Generic) manager.load(cur.getLong(COMPONENT_ID));
                component_list.add(component);
            } while (cur.moveToNext());
        }
        cur.close();

        return component_list;

    }


    /*****************************************************************************
     * Retourne une Activité utilisateur stockée dans la base à partir de son
     * id.
     * <p/>
     * - récupérer l'UA
     * - récupérer ses composants
     *
     * @param databaseId
     * @return (UserActivityObj) un objet "Activité d'utilisateur"
     * @since 01-06-2012
     ****************************************************************************/
    @Override
    public UserActivity load(long databaseId) {

        //Si l'ID en entrée est nul aucune activity ne peux être trouvée
        if (databaseId == AppConsts.NO_ID) {
            // Toast.makeText(this.mActivity, "ID � recharger vide", Toast.LENGTH_LONG).show();
            return null;
        }
        Uri request = ContentUris.withAppendedId(ContentDescriptorObj.TB_UserActivities.SELECT_USER_ACTIVITY_BY_ID_URI,
                databaseId);

        Cursor cur = getActivity().getContentResolver().query(request, null, null, null, null);

        final int ACTIVITY_ID = cur.getColumnIndex(ContentDescriptorObj.TB_UserActivities.Columns.ID);
        final int ACTIVITY_TITLE = cur.getColumnIndex(ContentDescriptorObj.TB_UserActivities.Columns.TITLE);
        final int ACTIVITY_CLASS = cur.getColumnIndex(ContentDescriptorObj.TB_UserActivities.Columns.CLASS);
        final int ACTIVITY_DATE = cur.getColumnIndex(ContentDescriptorObj.TB_UserActivities.Columns.DATE);

        UserActivity userActivity = null;
        // faire un move First pour positionner le pointeur, sinon on pointe sur
        // null
        if (cur.moveToFirst()) {

            // on charge le mapping des UA CLASS
            AppConsts.UA_CLASS_CD_MAP mapping = new AppConsts.UA_CLASS_CD_MAP();
            UA_CLASS_CD UA_Class_cd = mapping._in.get(cur.getString(ACTIVITY_CLASS));
            // en fonction du type d'activitée, on va retourner l'objet adequat

            switch (UA_Class_cd) {
                case LUNCH:
                    userActivity = new UserActivity_Lunch();
                    break;
                case MOVE:
                    userActivity = new UserActivity_Move();
                    break;
                case WEIGHT:
                    userActivity = new UserActivity_Weight();
                    // ((UserActivityWeight) userActivity).setWeight(new
                    // WeightObj(cur.getString(ACTIVITY_TITLE)));
                    break;
            }

            userActivity.setDatabaseId(cur.getLong(ACTIVITY_ID));
            userActivity.setTitle(cur.getString(ACTIVITY_TITLE));
            userActivity.setDay(ToolBox.parseSQLDatetime(cur.getString(ACTIVITY_DATE)));

            // recharger les Composants qui sont attachés à cette UA
            //    userActivity.setComponentsList(ComponentFactory.load_UA_ComponentsList(userActivity));

        } else {
            // Toast.makeText(this.mActivity, "User Activity inconnue", Toast.LENGTH_LONG).show();

        }

        return  userActivity;
    }


    /****************************************************************************
     * Générer un contenValies communs à toutes les activitées
     * <p/>
     ****************************************************************************/
    @Override
    public ContentValues getContentValues(ManagedElement element) {
        ContentValues val = new ContentValues();
        UserActivity userActivity = (UserActivity) element;
        // id de l'activitée
        val.put(ContentDescriptorObj.TB_UserActivities.Columns.ID, (userActivity.getDatabaseId() == AppConsts.NO_ID) ? null
                : userActivity.getDatabaseId());
        // titre
        val.put(ContentDescriptorObj.TB_UserActivities.Columns.TITLE, userActivity.getTitle());
        // Date
        val.put(ContentDescriptorObj.TB_UserActivities.Columns.DATE, ToolBox.getSqlDateTime(userActivity.getDay()));

        // class : on utilise la mapping pour transformer l'ENUM Class en Byte
        // stoké dans la Database.
        AppConsts.UA_CLASS_CD_MAP ua_cd_map = new AppConsts.UA_CLASS_CD_MAP();
        val.put(ContentDescriptorObj.TB_UserActivities.Columns.CLASS, ua_cd_map._out.get((userActivity.getActivityType())));

        // date de mise à jour
        val.put(ContentDescriptorObj.TB_UserActivities.Columns.LAST_UPDATE, ToolBox.getCurrentTimestamp());

        return val;
    }


}
