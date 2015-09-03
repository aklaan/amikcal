package com.rdupuis.amikcal.unity;

public class InternationalUnit extends Unity{
private long id;
private String name;
private String symbol;

public InternationalUnit(){
	super.setUnityClass(UNIT_CLASS.INTERNATIONAL);
}

public long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getSymbol() {
	return symbol;
}
public void setSymbol(String symbol) {
	this.symbol = symbol;
}

@Override
public void setUnityClass(UNIT_CLASS mClass) {
// on override pour empêcher la modification
}


}
