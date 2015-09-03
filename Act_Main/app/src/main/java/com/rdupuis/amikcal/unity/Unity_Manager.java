package com.rdupuis.amikcal.unity;

import android.app.Activity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.rdupuis.amikcal.commons.AmiKcalFactory;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.Manager;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.data.ContentDescriptorObj;

/**
 * Created by rodol on 27/08/2015.
 */
public final class Unity_Manager extends Manager {

    public Unity_Manager(Activity activity) {
        super(activity);
    }


    /*****************************************************************************************
     * Enregister une unit�e dans la databse
     ******************************************************************************************/
    public void save(Unity unity) {
        // On pr�pare les informations � mettre � jour
        ContentValues val = new ContentValues();

        // LongName
        val.put(ContentDescriptorObj.TB_Units.Columns.LONG_NAME, unity.getLongName());

        // ShortName
        val.put(ContentDescriptorObj.TB_Units.Columns.SHORT_NAME, unity.getShortName());

        // Alimentation de la classe d'unit�e
        AppConsts.UNIT_CLASS_MAP unit_class_map = new AppConsts.UNIT_CLASS_MAP();
        val.put(ContentDescriptorObj.TB_Units.Columns.CLASS, unit_class_map._out.get(unity.getUnityClass()));

        // date de mise � jour
        val.put(ContentDescriptorObj.TB_Units.Columns.LAST_UPDATE, ToolBox.getCurrentTimestamp());

        // Sauver l'unit�e
        if (unity.getId() == AppConsts.NO_ID) {
            Uri result = getActivity().getContentResolver().insert(ContentDescriptorObj.TB_Units.URI_INSERT_UNIT, val);
            unity.setId(Long.parseLong(result.getLastPathSegment()));
        } else {
            getActivity().getContentResolver().update(ContentDescriptorObj.TB_Units.URI_UPDATE_UNIT, val,
                    String.valueOf(unity.getId()), null);
        }

    }


    /*********************************************************************************
     * Retourne une unitée de mesure stockée dans la base à partir de son id.
     *
     * @param databaseId
     * @return (Unity) un objet "Unitée de mesure"
     * @autor Rodolphe Dupuis
     * @since 01-06-2012
     *********************************************************************************/

    public static Unity load(long databaseId) {

        Unity u = new Unity();

        if (databaseId == AppConsts.NO_ID) {
            return u;
        }

        u.setId(databaseId);

        Uri selectUri = ContentUris.withAppendedId(ContentDescriptorObj.TB_Units.URI_SELECT_UNIT, databaseId);
        Cursor cursor = AmiKcalFactory.contentResolver.query(selectUri, null, null, null, null);

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


}
