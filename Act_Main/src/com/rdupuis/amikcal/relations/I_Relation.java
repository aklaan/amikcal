package com.rdupuis.amikcal.relations;

import com.rdupuis.amikcal.relations.Relation.REL_TYP_CD;

public interface I_Relation {
    	
    //public void setId(long id);
    public String getParty1();
    public String getParty2() ;
    public long getId() ;
    public void setId(long rel_id);
    public REL_TYP_CD getRelationClass() ;
    
    
 
}
