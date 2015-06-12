package com.rdupuis.amikcal.equivalence;

import java.util.ArrayList;

import com.rdupuis.amikcal.components.Component;

public interface i_CanHaveEquivalences {

    
    public void setEquivalences(ArrayList<? extends Component> mEquivalences);

    public ArrayList<? extends Component> getEquivalences() ;

}
