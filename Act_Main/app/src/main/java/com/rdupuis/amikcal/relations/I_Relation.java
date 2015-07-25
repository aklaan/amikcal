package com.rdupuis.amikcal.relations;

import java.util.ArrayList;

import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.data.Savable;
import com.rdupuis.amikcal.relations.REL_TYP_CD;

public abstract class I_Relation implements Savable {
    	
    //public void setId(long id);
    public abstract String getParty1();
    public abstract String getParty2() ;
    public abstract long getId() ;
    public abstract void setId(long rel_id);
    public abstract REL_TYP_CD getRelationClass() ;
    
    
 
}
