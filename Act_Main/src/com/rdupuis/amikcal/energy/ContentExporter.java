package com.rdupuis.amikcal.energy;

import android.content.ContentValues;

import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.commons.AppConsts.NRJ_CLASS_MAP;
import com.rdupuis.amikcal.data.ContentDescriptorObj;

public abstract class DBWarper {
    
    public abstract ContentValues getContentValues() ;
}
