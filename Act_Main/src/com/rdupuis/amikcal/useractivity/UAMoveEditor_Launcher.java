package com.rdupuis.amikcal.useractivity;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.rdupuis.amikcal.commons.ToolBox;

public class UAMoveEditor_Launcher extends EditorLauncher {

    public UAMoveEditor_Launcher(Activity activity, EditableUA uaMove) {
	this.mActivity = activity;
	this.mEditable = uaMove;
    }

    // Dans le cas d'une mise à jour on appelle l'éditeur avec l'ID de
    // l'activité à modifier
    public void start() {

	Intent intent = new Intent(this.mActivity, Act_UserActivity_EditMoveActivity.class);
	intent.putExtra(Act_UserActivity_EditMoveActivity.INPUT____UA_ID, this.mEditable.getId());
	intent.putExtra(Act_UserActivity_EditMoveActivity.INPUT____DAY, ToolBox.getSqlDate(((EditableUA) this.mEditable).getDay()));

	this.mActivity.startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
	Toast.makeText(this.mActivity, "on result lunchitem", Toast.LENGTH_LONG).show();
    }

    @Override
    public void delete() {
	// TODO Auto-generated method stub

    }

}
