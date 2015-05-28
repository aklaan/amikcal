package components;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.useractivity.Act_UserActivity_EditLunchActivity;

public class Component_Weight_Action extends Component_Action {
    
    
    public Component_Weight_Action(Activity activity,Component cfood) {
	this.mActivity = activity;
	this.mComponent = cfood;
	
    }

    // Dans le cas d'une mise à jour on appelle l'éditeur avec l'ID de
    // l'activité à modifier
    public void edit() {

	//Intent intent = new Intent(this.mActivity, Act_Component_Weight_Editor.class);
	//intent.putExtra(Act_Component_Weight_Editor.INPUT____COMPONENT_ID, this.mComponent.getId());
	//this.mActivity.startActivityForResult(intent, 0);
    }

    @Override
    public void delete() {
	// TODO Auto-generated method stub

    }


}
