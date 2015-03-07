package com.rdupuis.amikcal.commons;

import java.util.ArrayList;

public interface HasEquivalences {
	public ArrayList<Qty> mEquivalences = new ArrayList<Qty>();

	public ArrayList<Qty> getEquivalences();
	public void setEquivalences(ArrayList<Qty> equivalences);

}
