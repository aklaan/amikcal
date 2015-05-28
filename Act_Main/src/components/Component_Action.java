package components;

import android.app.Activity;

public abstract class Component_Action {

	public Activity mActivity;
	public Component mComponent;
	
	public abstract void edit();
	public abstract void delete();
	
}
