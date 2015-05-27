package com.rdupuis.amikcal.useractivity;

import java.util.Calendar;

public interface EditableUA extends EditableObj{
// par rapport à un objet éditable, les UA éditable doivent pouvoir fournir une date
    // a laquelle elles seront initialisées
    public Calendar getDay();
}
