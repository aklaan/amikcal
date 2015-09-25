package com.rdupuis.amikcal.unity;

import android.app.Activity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;


import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ManagedElement;
import com.rdupuis.amikcal.commons.Manager_commons;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.data.ContentDescriptorObj;

/**
 * Created by rodol on 27/08/2015.
 */
public final class Unity_Manager extends Manager_commons {

    public Unity_Manager(Activity activity) {
        super(activity);

    }
    @Override
    public void edit(ManagedElement element) {
        //Cast de l'élément en Component_Food
        Unity unity = (Unity) element;

        //Préparation de Intent
        Intent intent = new Intent(getActivity(), Act_UnitOfMeasureEditor.class);
        intent.putExtra(Act_UnitOfMeasureEditor.INPUT____UNITY, unity);

        //Start de l'activity
        getActivity().startActivityForResult(intent, R.integer.ACTY_UNIT);
    }



    /*****************************************************************************************
     * Enregister une unitée dans la database
     ******************************************************************************************/
    @Override
    public long save(ManagedElement element) {
        long _id;

        _id = element.getDatabaseId();
        // On prépare les informations à mettre à jour
        ContentValues val = this.getContentValues(element);

        // Sauver l'unitée
        if (_id == AppConsts.NO_ID) {
            Uri result = getActivity().getContentResolver().insert(ContentDescriptorObj.TB_Units.URI_INSERT_UNIT, val);
            _id = Long.parseLong(result.getLastPathSegment());
        } else {
            getActivity().getContentResolver().update(ContentDescriptorObj.TB_Units.URI_UPDATE_UNIT, val,
                    String.valueOf(_id), null);
        }
        return _id;
    }


    /*********************************************************************************
     * Retourne une unitée de mesure stockée dans la base à partir de son id.
     *
     * @param databaseId
     * @return (Unity) un objet "Unitée de mesure"
     * @autor Rodolphe Dupuis
     * @since 01-06-2012
     *********************************************************************************/

   @Override
   public Unity load(long databaseId) {

        Unity u = new Unity();

        if (databaseId == AppConsts.NO_ID) {
            return u;
        }

        u.setDatabaseId(databaseId);

        Uri selectUri = ContentUris.withAppendedId(ContentDescriptorObj.TB_Units.URI_SELECT_UNIT, databaseId);
        Cursor cursor = getActivity().getContentResolver().query(selectUri, null, null, null, null);

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

@Override
    public ContentValues getContentValues(ManagedElement element){
    Unity unity = (Unity) element;
    ContentValues values = new ContentValues();
// LongName
    values.put(ContentDescriptorObj.TB_Units.Columns.LONG_NAME, unity.getLongName());

    // ShortName
    values.put(ContentDescriptorObj.TB_Units.Columns.SHORT_NAME, unity.getShortName());

    // Alimentation de la classe d'unitée
    AppConsts.UNIT_CLASS_MAP unit_class_map = new AppConsts.UNIT_CLASS_MAP();
    values.put(ContentDescriptorObj.TB_Units.Columns.CLASS, unit_class_map._out.get(unity.getUnityClass()));

    // date de mise à jour
    values.put(ContentDescriptorObj.TB_Units.Columns.LAST_UPDATE, ToolBox.getCurrentTimestamp());

return values;

}
}
