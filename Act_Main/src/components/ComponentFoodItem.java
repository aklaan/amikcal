package components;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.useractivity.Act_UserActivity_EditLunchActivity;
import com.rdupuis.amikcal.useractivity.UserActivityItem;
import com.rdupuis.amikcal.useractivity.UserActivityLunch;

public class ComponentFoodItem extends AbstractComponentItem {

	public ComponentFoodItem(Activity activity) {
		super(activity);
		this.mComponent = new ComponentFood();
	}

	// Dans le cas d'une mise � jour on appelle l'�diteur avec l'ID de 
	// l'activit� � modifier
	public void edit() {
				
		Intent intent = new Intent(this.mActivity,
				Act_UserActivity_EditLunchActivity.class);
		intent.putExtra(AppConsts.INPUT____UA_EDITOR____USER_ACTIVITY_ID,
				this.mUserActivity._id);
		
		this.mActivity
				.startActivityForResult(intent, 0);
	}

	
	// Dans le cas d'une cr�ation on appelle l'�diteur avec la date o�
	// on veut cr�er l'activit�
	public void create(Calendar day){
		Intent intent = new Intent(this.mActivity,
				Act_UserActivity_EditLunchActivity.class);
				  intent.putExtra( AppConsts.INPUT____UA_EDITOR____DAY , ToolBox.getSqlDate(day));
			
		this.mActivity
				.startActivityForResult(intent, 0);

		
	}
	

		
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent){
		Toast.makeText(this.mActivity, "on result lunchitem",
                Toast.LENGTH_LONG).show();
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	
}
