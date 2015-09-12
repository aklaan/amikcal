package com.rdupuis.amikcal.data.writers;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * le DBWriter fait le lien entre un objet que l'on peu sauver dans une base de donnée
 * et les les URI qui vont permettre de le faire.
 * <p/>
 * par exemple si on souhaite enregistrer une énergie dans une base A et une base B,
 * les Uri ne seront pas les mêmes. du coup l'URI n'est pas une propriété de l'énergie
 */
public abstract class DBWriter {

    public abstract Uri getUriUpdate();

    public abstract Uri getUriInsert();

    public abstract ContentResolver getContentResolver();

    public abstract void setContentResolver(ContentResolver contentResolver);

    public abstract void setUriUpdate(Uri uriUpdate);

    public abstract void setUriInsert(Uri uriInsert);

    public abstract long save();

}
