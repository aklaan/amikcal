package com.rdupuis.amikcal.data.writers;

import com.rdupuis.amikcal.components.Component;

import android.content.ContentResolver;

public class DBWriter_Component extends DBWriter_Relation {

    public DBWriter_Component(ContentResolver contentResolver, Component component) {
        super(contentResolver, component);

    }

    public Component getComponent() {
        return (Component) this.getSavable();
    }

    @Override
    public long save() {

        long id = super.save();

        return id;
    }
}
