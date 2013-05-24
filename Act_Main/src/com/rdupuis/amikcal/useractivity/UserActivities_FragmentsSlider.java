package com.rdupuis.amikcal.useractivity;

import com.rdupuis.amikcal.R;

import com.rdupuis.amikcal.commons.Generic_FragmentsSlider;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.commons.Generic_FragmentsSlider.FragmentGroup;
import com.rdupuis.amikcal.data.ContentDescriptorObj;

import com.rdupuis.amikcal.useractivity.Act_UserActivityEditor;
import com.rdupuis.amikcal.useractivity.Frag_UserActivityList;
import com.rdupuis.amikcal.useractivitycomponent.Act_UserActivityComponentList;

import java.util.ArrayList;
import java.util.Calendar;
import android.content.ContentUris;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class UserActivities_FragmentsSlider extends Generic_FragmentsSlider {

	// Definition des Types de vues a afficher
	static enum ViewMode {
		VIEW_ACTIVITIES_OF_DAY, STATISTIC_VIEW_OF_DAY, STATISTIC_VIEW_OF_WEEK, STATISTIC_VIEW_OF_MONTH, STATISTIC_VIEW_OF_YEAR
	};

	private Intent mIntent;
	private ViewMode mCurrentViewMode;
	private Calendar mCurrentDay;
	private int mZoomLevel;
	private final int MIN_ZOOM_LEVEL = 0;
	private final int MAX_ZOOM_LEVEL = 1;
	
	public void setCurrentDay(Calendar calendar) {
		mCurrentDay = calendar;
	}

	public Calendar getCurrentDay() {
		return mCurrentDay;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		mIntent = getIntent();

		// * Recuperer les informations sauvees
		try {
			if (savedInstanceState != null) {
				// On restaure la date a laquelle on �tait
				mCurrentDay = (ToolBox.parseCalendar(savedInstanceState
						.getString("savedCurrentDay")));
				// On restaure le mode vue
				mCurrentViewMode = ViewMode.valueOf(savedInstanceState
						.getString("savedViewMode"));
			} else 
				//* s'il n'y a pas d'information sauvee, on va tenter le r�cup�rer une 
				// date dans l'Intent
				if (mIntent != null) {
				mCurrentDay = (ToolBox
						.parseCalendar(mIntent.getStringExtra(mProjectResources
								.getString(R.string.INTENT_INPUTNAME_FOR_UA_FRAGMENTSLIDER_DAY))));
			}
		} catch (Exception e) {
			//Sinon par d�faut, on prend la date du jour.
			mCurrentDay = (Calendar.getInstance());
		}
		;

		// Par d�faut la vue a afficher est la vue de synth�se du jour.
		// cette vue est associ� niveau de zoom 1
		if (mCurrentViewMode == null) {
			mZoomLevel = 1;
			mCurrentViewMode = getViewMode(mZoomLevel);
			}
		;

		// On souhaites conserver en memoire 9 pages en meme temps.
		getViewPager().setOffscreenPageLimit(9);

		createFragmentsGroups(mCurrentDay, getFagmentClassName());
		// Au demarrage le focus doit etre sur la page 4, c'est la page
		// centrale
		getViewPager().setCurrentItem(4, false);
	
	
		/**
		 * Ajout d'un listener pour modifier la date courante a chaque changement de page
		 */
		    super.getViewPager().setOnPageChangeListener(new OnPageChangeListener() {

			public void onPageScrollStateChanged(int state) {
		
				if (state ==  ViewPager.SCROLL_STATE_IDLE){
					
				UserActivities_FragmentsSlider.this.setCurrentDay( 
						UserActivities_FragmentsSlider.this.getArrayCalendar().get(
						getViewPager().getCurrentItem()));		
				}
			Log.i("nouvelle date courante",ToolBox.getSqlDate(UserActivities_FragmentsSlider.this.getCurrentDay()));	
			}
					
			public void onPageSelected(int pageNumber) {
				}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			});

	
	
	}

	/**
	 * Cette fonction va retourner le nom de la classe de fragment a afficher
	 * selon le type de la vue souhaitee.
	 */
	private ViewMode getViewMode(int level) {

		switch (level) {

		case 0: 
				return ViewMode.VIEW_ACTIVITIES_OF_DAY;
		case 1: 
			return ViewMode.STATISTIC_VIEW_OF_DAY;
		case 2: 
			return ViewMode.STATISTIC_VIEW_OF_WEEK;
		case 3: 
			return ViewMode.STATISTIC_VIEW_OF_MONTH;
		default : 
		}
		return ViewMode.STATISTIC_VIEW_OF_DAY;
	};
	
	/**
	 * Cette fonction va retourner le nom de la classe de fragment a afficher
	 * selon le type de la vue souhaitee.
	 */
	private String getFagmentClassName() {

		switch (mCurrentViewMode) {

		case VIEW_ACTIVITIES_OF_DAY:
			return Frag_UserActivityList.class.getName();

		case STATISTIC_VIEW_OF_DAY:
			return Frag_UserActivity_StatisticsOfDay.class.getName();

		}
		return "";
	};

	

	/**
	 * 
	 * @param id
	 *            Identifiant de l'activit�e utilisateur s�l�ction�e
	 */
	public void onClickActivity(String id) {
		Intent intent = new Intent(this, Act_UserActivityComponentList.class);
		intent.putExtra(
				mProjectResources
						.getString(R.string.INTENT_IN_USER_ACTIVITY_COMPONENT_LIST_ID_OF_USER_ACTIVITY),
				id);
		intent.putExtra("page", getCurrentPage());
		startActivityForResult(intent,
				mProjectResources.getInteger(R.integer.ACTY_COMPONENT_LIST));
	};

	
	


	/**
	 * 
	 * @param id
	 *            Identifiant de l'activit�e utilisateur � �diter
	 */
	public void onClickEdit(Fragment f, String id) {
		Intent intent = new Intent(this, Act_UserActivityEditor.class);
		intent.putExtra(
				mProjectResources
						.getString(R.string.INTENT_IN_USER_ACTIVITY_EDITOR_ID_OF_THE_USER_ACTIVITY),
				id);
		intent.putExtra(
				mProjectResources
						.getString(R.string.INTENT_IN_USER_ACTIVITY_EDITOR_DAY_OF_THE_USER_ACTIVITY),
				ToolBox.getSqlDate(getCurrentDay()));
		intent.putExtra("page", getCurrentPage());

		startActivityForResult(intent,
				mProjectResources
						.getInteger(R.integer.ACTY_USER_ACTIVITY_EDITOR));
	}

	/**
	 * 
	 * @param id
	 *            Identifiant de l'activit�e utilisateur � supprimer
	 */
	public void onClickDelete(String id) {

		// v�rifier s'il y a des enfants
		// s'il y a des enfant alerter et demander une confirmation

		Uri uriDelete = ContentUris.withAppendedId(
				ContentDescriptorObj.UserActivities.URI_DELETE_USER_ACTIVITIES,
				Long.parseLong(id));
		this.getContentResolver().delete(uriDelete, null, null);

	}

	public void onClickAdd(View v) {

		onClickEdit(null, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar_activities, menu);
		return true;

	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		switch (mZoomLevel) {
		// le niveau le plus bas correspond � la liste des activit�es du jour
		// on ajoute donc le bouton ADD pour pouvoir ajouter une activit�
		// lorsque l'on monte d'un niveau, le bouton ADD n'est plus utile, alors
		// on ne l'affiche plus.
		case MIN_ZOOM_LEVEL:
			Log.i("case","MIN_ZOOM_LEVEL");
			menu.findItem(R.id.menu_add).setShowAsAction(
					MenuItem.SHOW_AS_ACTION_ALWAYS);
			break;
		default :
			menu.findItem(R.id.menu_add).setShowAsAction(
					MenuItem.SHOW_AS_ACTION_NEVER);
			break;
		}
	
		return super.onPrepareOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean iSrefreshViewNeeded = false;
		switch (item.getItemId()) {
		case R.id.menu_zoom_in:
			// Si le niveau de zoom n'est pas d�ja au plus bas
			// on d�cr�mente le niveau
			if (mZoomLevel>MIN_ZOOM_LEVEL){
				mZoomLevel--;
				iSrefreshViewNeeded=true;
				}
			break;
		case R.id.menu_zoom_out:
			if (mZoomLevel<MAX_ZOOM_LEVEL){
				mZoomLevel++;
				iSrefreshViewNeeded=true;
				}
			break;
			
		default:
			return super.onOptionsItemSelected(item);
		}
	
		if (iSrefreshViewNeeded){
			mCurrentViewMode = getViewMode(mZoomLevel);
			createFragmentsGroups(getCurrentDay(), getFagmentClassName());
			// switchFragment();
			getViewPager().setCurrentItem(4, false);
		}
		Log.i("zoomLevel",String.valueOf(mZoomLevel));
		//invalidateOptionsMenu va appeller la m�thode onPrepareOptionsMenu();
		invalidateOptionsMenu();
		return true;
	}

	protected void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		//
		// Pour rappel le ViewPager utilise un PagerAdapter qui g�re les
		// �l�ments qui doivent �tre affich� ou non
		// par exemple, lorsque l'on veut afficher un fragment "A", le
		// pagerAdapter va cr�er une copie de A -> A' et afficher cette copie.
		// si A' n'est plus visible � l'�cran, le PagerAdapter peu prendre la
		// d�cision de supprimer A' pour
		// pr�server la m�moire.
		// si par la suite il faut r�afficher A, le pagerAdapter va de nouveau
		// en faire une copie A''.
		//
		// Lorsque j'appelle le onSaveInstanceState, le syst�me m�morise l'�tat
		// actuel des vues. dans mon cas,
		// il va m�moriser l'�tat des copies en cours de gestion par le
		// PagerAdapater. on ne m�morise donc pas
		// tous les fragments et �a pose des probl�mes d'index � la recr�ation.
		//
		// Au final, lors de la re-cr�ation de mon activit� je vais recr�er de
		// nouveaux fragments (nouvel id)
		// qui n'aurront plus aucun lien avec les fragments dont l'�tat a �t�
		// m�moris�.
		// je prend donc le parti de vider le savedInstanceState pour purger les
		// Fragmentstate inutiles.
		// Au lieu de faire un onCreate(null), je le conserve tout de m�me le
		// savedInstanceState pour pouvoir
		// m�morier la date � laquelle l'utilisateur s'est arr�t�.
		//
		savedInstanceState.clear();
		savedInstanceState.putString(
				"savedCurrentDay",
				ToolBox.getSqlDate(getCurrentDay()));

		savedInstanceState.putString("savedViewMode", mCurrentViewMode.name());
		Log.d("savedCurrentDay",
				savedInstanceState.getString("savedCurrentDay"));
		Log.d("savedViewMode", mCurrentViewMode.name());
	}
}
