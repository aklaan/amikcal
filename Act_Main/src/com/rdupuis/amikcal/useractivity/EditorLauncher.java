package com.rdupuis.amikcal.useractivity;

import android.app.Activity;
import android.content.Intent;

public abstract class EditorLauncher {

	public Activity mActivity;
	public EditableObj mEditable;
	
	public abstract void start();
	public abstract void delete();
	public abstract void onActivityResult(int requestCode, int resultCode, Intent intent);
	    
	
}
