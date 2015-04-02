package com.rdupuis.amikcal.commons;

import com.rdupuis.amikcal.commons.Relation.REL_TYP_CD;

public interface ReadableRelationInterface {

    public void setId(long id);
    public String getParty1();
    public String getParty2() ;
    public long getId() ;
    public REL_TYP_CD getRel_typ_cd() ;

    
 
}
