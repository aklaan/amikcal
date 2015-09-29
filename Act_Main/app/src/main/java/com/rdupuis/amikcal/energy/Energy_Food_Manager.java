package com.rdupuis.amikcal.energy;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AppConsts.NRJ_CLASS_MAP;
import com.rdupuis.amikcal.commons.AppConsts.STRUCTURE_CD_MAP;
import com.rdupuis.amikcal.commons.ManagedElement;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.data.ContentDescriptorObj;

public class Energy_Food_Manager extends Energy_Manager {

    public Energy_Food_Manager(Activity activity) {
        super(activity);
    }

    @Override
    public ContentValues getContentValues(ManagedElement element) {
        ContentValues val = super.getContentValues(element);

        // Alimenter la structure de l' aliment
        STRUCTURE_CD_MAP stucture_map = new STRUCTURE_CD_MAP();
        val.put(ContentDescriptorObj.TB_Energies.Columns.STRUCTURE,
                stucture_map._out.get(((Food) element).getStructure()));

        return val;
    }

    @Override
    public Food load(long databaseId) {
        return (Food)super.load(databaseId);

    }

    @Override
    public void edit(ManagedElement element) {
        Food food = (Food) element;
        Intent intent = new Intent(getActivity(), Act_Food_Editor.class);
        intent.putExtra(Act_Food_Editor.INPUT____FOOD, food);
        getActivity().startActivityForResult(intent, R.integer.ACTY_ENERGY_FOOD_EDITOR);
    }




}