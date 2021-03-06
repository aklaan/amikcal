package com.rdupuis.amikcal.data.writers;

import android.content.ContentResolver;
import android.net.Uri;

public abstract class DBWriter {

public abstract Uri getUriUpdate();
public abstract Uri getUriInsert();

public abstract ContentResolver getContentResolver();
public abstract void setContentResolver(ContentResolver contentResolver);

public abstract void setUriUpdate(Uri uriUpdate);
public abstract void setUriInsert(Uri uriInsert);

public abstract void Save();

}
