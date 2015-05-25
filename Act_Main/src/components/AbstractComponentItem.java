package components;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;

public abstract class AbstractComponentItem {

	public Activity mActivity;
	public Component mComponent;
	
	public abstract void edit();
	public abstract void create(Calendar day) ;
	public abstract void delete();
	public abstract void onActivityResult(int requestCode, int resultCode, Intent intent);
}
