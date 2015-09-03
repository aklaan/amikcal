package com.rdupuis.amikcal.commons;

import com.rdupuis.amikcal.Qty.Qty;

public interface HasQtyReference {


	public Qty qtyReference = new Qty();
	
	public void setQtyReference(Qty qtyReference);
	public Qty getQtyReference();

}
