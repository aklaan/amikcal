package com.rdupuis.amikcal.relations;

import java.util.ArrayList;

import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.data.Savable;
import com.rdupuis.amikcal.relations.REL_TYP_CD;

public abstract class Relation implements Savable {
    	
    //public void setId(long id);
    public abstract String getParty1();
    public abstract String getParty2() ;
    public abstract long getDatabaseId() ;
    public abstract void setDatabaseId(long databaseId);
    public abstract REL_TYP_CD getRelationClass() ;
    

}
