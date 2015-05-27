package components;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.useractivity.Act_UserActivity_EditLunchActivity;
import com.rdupuis.amikcal.useractivity.EditableObj;
import com.rdupuis.amikcal.useractivity.EditableUA;
import com.rdupuis.amikcal.useractivity.EditorLauncher;

public class ComponentFood_EditorLauncher extends EditorLauncher {
    
    
    public ComponentFood_EditorLauncher(Activity activity,EditableObj cfood) {
	this.mActivity = activity;
	this.mEditable = cfood;
	
    }

    // Dans le cas d'une mise à jour on appelle l'éditeur avec l'ID de
    // l'activité à modifier
    public void start() {

	Intent intent = new Intent(this.mActivity, Act_ComponentFood_Editor.class);
	intent.putExtra(Act_ComponentFood_Editor.INPUT____COMPONENT_ID, this.mEditable.getId());
	this.mActivity.startActivityForResult(intent, 0);
    }

    @Override
    public void delete() {
	// TODO Auto-generated method stub

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	// TODO Auto-generated method stub
	
    }


}
