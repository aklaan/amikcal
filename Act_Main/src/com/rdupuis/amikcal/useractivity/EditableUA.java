package com.rdupuis.amikcal.useractivity;

import java.util.Calendar;

public interface EditableUA extends EditableObj{
// par rapport � un objet �ditable, les UA �ditable doivent pouvoir fournir une date
    // a laquelle elles seront initialis�es
    public Calendar getDay();
}
