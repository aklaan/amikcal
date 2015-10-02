package com.rdupuis.amikcal.relations;

import com.rdupuis.amikcal.commons.ManagedElement;

public abstract class Relation implements ManagedElement {
    	
    //public void setId(long id);
    public abstract String getParty1();
    public abstract String getParty2() ;
    public abstract long getDatabaseId() ;
    public abstract void setDatabaseId(long databaseId);

    public abstract REL_TYP_CD getRelationClass() ;
    

}
