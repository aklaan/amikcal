package com.rdupuis.amikcal.commons;

import com.rdupuis.amikcal.Qty.Qty;

import java.util.ArrayList;

public interface HasEquivalences {
	public ArrayList<Qty> mEquivalences = new ArrayList<Qty>();

	public ArrayList<Qty> getEquivalences();
	public void setEquivalences(ArrayList<Qty> equivalences);

}
