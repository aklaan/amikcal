package com.rdupuis.amikcal.useractivity;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.animation.FadeIn;
import com.rdupuis.amikcal.commons.AmikcalVar;
import com.rdupuis.amikcal.commons.Generic_FragmentsTimeSlider;
import com.rdupuis.amikcal.commons.ToolBox;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.useractivity.Frag_UserActivityList;
import com.rdupuis.amikcal.useractivitycomponent.Act_UserActivityComponentList;

import java.util.Calendar;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class UserActivities_FragmentsSlider extends Generic_FragmentsTimeSlider {

	// Definition des Types de vues a afficher
	static enum ViewMode {
		VIEW_ACTIVITIES_OF_DAY, STATISTIC_VIEW_OF_DAY, STATISTIC_VIEW_OF_WEEK, STATISTIC_VIEW_OF_MONTH, STATISTIC_VIEW_OF_YEAR
	};

	private ViewMode mCurrentViewMode;
	private Calendar mCurrentDay;
	private int mZoomLevel;
	private final int MIN_ZOOM_LEVEL = 0;
	private final int MAX_ZOOM_LEVEL = 1;
	private final String BUNDLE_VAR____CURRENT_DAY = "CurrentDay";
	private final String BUNDLE_VAR____VIEWMODE = "ViewMode";

	public void setCurrentDay(Calendar calendar) {
		mCurrentDay = calendar;
	}

	public Calendar getCurrentDay() {
		return mCurrentDay;
	}

	/**
	 * =============================================================== onCreate
	 *
	 * ===============================================================
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		this.getIntent();

		// * Recuperer les informations sauvees dans le Bundle lorsque
		// l'activité a été stopée
		try {
			if (savedInstanceState != null) {
				// On restaure la date a laquelle on était
				mCurrentDay = (ToolBox.parseCalendar(savedInstanceState
						.getString(BUNDLE_VAR____CURRENT_DAY)));
				// On restaure le mode vue
				mCurrentViewMode = ViewMode.valueOf(savedInstanceState
						.getString(BUNDLE_VAR____VIEWMODE));
			}

		} catch (Exception e) {
			// Sinon par défaut, on prend la date du jour.
			mCurrentDay = (Calendar.getInstance());
		}
		;

		// Par défaut la vue a afficher est la vue de synthèse du jour.
		// cette vue est associé niveau de zoom 1
		if (mCurrentViewMode == null) {
			mZoomLevel = 1;
			mCurrentViewMode = getViewMode(mZoomLevel);
			mCurrentDay = (Calendar.getInstance());
		}
		;

		// On souhaites conserver en memoire 9 pages en meme temps.
		// pour gérer correctement le timeSlide
		getViewPager().setOffscreenPageLimit(9);

		// on va créer le groupe de fragment à afficher
		createFragmentsGroups(mCurrentDay,
				getFagmentClassName(mCurrentViewMode));

		// Au demarrage le focus doit etre sur la page 4, c'est la page
		// centrale
		getViewPager().setCurrentItem(4, false);

		/**
		 * Ajout d'un listener pour modifier la date courante a chaque
		 * changement de page
		 */

	}

	/**
	 * ===============================================================
	 * onUpdateGroup on override la méthode onUpdateGroup pour mémoriser la date
	 * a laquelle l'utilisateur s'est positioné. (non-Javadoc)
	 * 
	 * @see com.rdupuis.amikcal.commons.Generic_FragmentsSlider#onUpdateGroup()
	 *      ===============================================================
	 */
	@Override
	protected void onUpdateGroup() {

		UserActivities_FragmentsSlider.this
				.setCurrentDay(UserActivities_FragmentsSlider.this
						.getArrayCalendar()
						.get(getViewPager().getCurrentItem()));

		/*
		 * Log.i("nouvelle date courante", ToolBox
		 * .getSqlDate(UserActivities_FragmentsSlider.this .getCurrentDay()));
		 */
	}

	/**
	 * ===============================================================
	 * getViewMode Cette fonction va retourner le nom de la vue a afficher selon
	 * le niveau de zoom sur lequel on se trouve.
	 * ===============================================================
	 */
	private ViewMode getViewMode(int zoomLevel) {

		switch (zoomLevel) {

		case 0:
			getActionBar().setTitle("Jounée");
			return ViewMode.VIEW_ACTIVITIES_OF_DAY;
		case 1:
			getActionBar().setTitle("jour");
			return ViewMode.STATISTIC_VIEW_OF_DAY;
		case 2:
			getActionBar().setTitle("Semaine");
			return ViewMode.STATISTIC_VIEW_OF_WEEK;
		case 3:
			getActionBar().setTitle("Mois");
			return ViewMode.STATISTIC_VIEW_OF_MONTH;
		default:
		}
		return ViewMode.STATISTIC_VIEW_OF_DAY;
	};

	/**
	 * ===============================================================
	 * getFagmentClassName Cette fonction va retourner le nom de la classe de
	 * fragment a utiliser pour afficher le type de la vue souhaitee.
	 * ===============================================================
	 */

	private String getFagmentClassName(ViewMode viewMode) {

		switch (viewMode) {

		case VIEW_ACTIVITIES_OF_DAY:
			return Frag_UserActivityList.class.getName();

		case STATISTIC_VIEW_OF_DAY:
		case STATISTIC_VIEW_OF_MONTH:
		case STATISTIC_VIEW_OF_WEEK:
		case STATISTIC_VIEW_OF_YEAR:
			return Frag_UserActivity_StatisticsOfDay.class.getName();

		}
		return "";
	};

	/**
	 * ===============================================================
	 * onClickActivity : Action a effectuer lorque l'utilisateur
	 * a séléctioné un item "UserActivity" dans une liste d'Activity
	 * 
	 *  - On affiche la liste des composants de l'activitée
	 * ===============================================================
	 */
	public void onClickActivity(String id) {
		Intent intent = new Intent(this, Act_UserActivityComponentList.class);
		intent.putExtra(
				Act_UserActivityComponentList.INTENT_IN____USER_ACTIVITY_COMPONENT_LIST____ID_OF_USER_ACTIVITY,
				id);
		intent.putExtra("page", getCurrentPage());
		startActivityForResult(intent,
				Act_UserActivityComponentList.ACTIVITY_ID);

	};

	/**
	 * ===============================================================
	 * onClickDelete
	 * 
	 * @param id
	 *            Identifiant de l'activitee utilisateur a supprimer
	 *            ============
	 *            ===================================================
	 */
	public void onClickDelete(String id) {

		// A faire : verifier s'il y a des enfants
		// s'il y a des enfant alerter et demander une confirmation

		Uri uriDelete = ContentUris.withAppendedId(
				ContentDescriptorObj.UserActivities.URI_DELETE_USER_ACTIVITIES,
				Long.parseLong(id));
		this.getContentResolver().delete(uriDelete, null, null);

	}

	/**
	 * ========================================================================
	 *  onClickAdd:
	 *  Action a effectuer lorsque l'on appuie sur le bouton ADD de la barre de menu
	 *  on appelle l'ecran de choix d'une activitee
	 * ===============================================
	 */

	public void onClickAdd() {
		// /appeler l'activité
		Intent intent = new Intent(this,
				Act_UserActivity_ChooseNewActivity.class);

		intent.putExtra(AmikcalVar.INPUT____CHOOSE_NEW_ACTIVITY____DAY_OF_THE_USER_ACTIVITY,
								ToolBox.getSqlDate(this.mCurrentDay));

		// en regardant si l'activité est présente dans le PackageManager, on
		// évite de
		// planter si on a oublié de déclarer l'activité dans le Manifest.
		if (intent.resolveActivity(getPackageManager()) != null) {

			//il faut faire un startActivityForResult pour déclancher le 
			// onActivityResult qui va permettre le rafraichissement de l'écran
			startActivityForResult(intent, 0);

		} else
			Toast.makeText(this,
					"Activité Act_UserActivity_ChooseNewActivity non trouvée",
					Toast.LENGTH_SHORT).show();
	}

	/**
	 * ========================================================================
	 * ===== onCreateOptionsMenu
	 * =============================
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar_activities, menu);
		return true;

	}

	/**
	 * ========================================================================
	 * ===== onPrepareOptionsMenu
	 * ================================================
	 * =============================
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		switch (mZoomLevel) {
		// le niveau le plus bas correspond à la liste des activitées du jour
		// on ajoute donc le bouton ADD pour pouvoir ajouter une activité
		// lorsque l'on monte d'un niveau, le bouton ADD n'est plus utile, alors
		// on ne l'affiche plus.
		case MIN_ZOOM_LEVEL:

			menu.findItem(R.id.menu_add).setShowAsAction(
					MenuItem.SHOW_AS_ACTION_ALWAYS);
			break;
		default:
			menu.findItem(R.id.menu_add).setShowAsAction(
					MenuItem.SHOW_AS_ACTION_NEVER);
			break;
		}

		return super.onPrepareOptionsMenu(menu);

	}

	/**
	 * ========================================================================
	 * ===== onOptionsItemSelected
	 * ==============================================
	 * ===============================
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean iSrefreshViewNeeded = false;
		switch (item.getItemId()) {
		case R.id.menu_zoom_in:
			// Si le niveau de zoom n'est pas déja au plus bas
			// on décrémente le niveau
			if (mZoomLevel > MIN_ZOOM_LEVEL) {
				mZoomLevel--;
				iSrefreshViewNeeded = true;
			}
			break;
		case R.id.menu_zoom_out:
			if (mZoomLevel < MAX_ZOOM_LEVEL) {
				mZoomLevel++;
				iSrefreshViewNeeded = true;
			}
			break;
		case R.id.menu_add:
			// ajout d'une nouvelle activitée
			// il faudrait appller l'activitée de choix
			this.onClickAdd();
			// onClickEdit(null, null, null);
			break;

		default:
			return super.onOptionsItemSelected(item);
		}

		if (iSrefreshViewNeeded) {

			mCurrentViewMode = getViewMode(mZoomLevel);
			createFragmentsGroups(getCurrentDay(),
					getFagmentClassName(mCurrentViewMode));
			// switchFragment();
			View v = getViewPager();

			getViewPager().setCurrentItem(4, false);
			// v.startAnimation(new FadeOut(v));
			v.startAnimation(new FadeIn(v));

		}

		// invalidateOptionsMenu va appeller la méthode onPrepareOptionsMenu();
		// pour rafraichir la barre des tâches
		invalidateOptionsMenu();
		return true;
	}

	/**
	 * ========================================================================
	 * == == Si la classe a lancé une activité, on rafraichit l'écran au retour
	 * afin de tenir compte des éventuelles mise à jours (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int,
	 *      android.content.Intent)
	 *      ==================================================
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		UserActivities_FragmentsSlider.this.getViewPager().getAdapter()
				.notifyDataSetChanged();

	}

	/**
	 * ========================================================================
	 * ===== onSaveInstanceState
	 * ================================================
	 * =============================
	 */
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		//
		// Pour rappel le ViewPager utilise un PagerAdapter qui gère les
		// éléments qui doivent être affiché ou non
		// par exemple, lorsque l'on veut afficher un fragment "A", le
		// pagerAdapter va créer une copie de A -> A' et afficher cette copie.
		// si A' n'est plus visible à l'écran, le PagerAdapter peu prendre la
		// décision de supprimer A' pour
		// préserver la mémoire.
		// si par la suite il faut réafficher A, le pagerAdapter va de nouveau
		// en faire une copie A''.
		//
		// Lorsque j'appelle le onSaveInstanceState, le système mémorise l'état
		// actuel des vues. dans mon cas,
		// il va mémoriser l'état des copies en cours de gestion par le
		// PagerAdapater. on ne mémorise donc pas
		// tous les fragments et ça pose des problèmes d'index à la recréation.
		//
		// Au final, lors de la re-création de mon activité je vais recréer de
		// nouveaux fragments (nouvel id)
		// qui n'aurront plus aucun lien avec les fragments dont l'état a été
		// mémorisé.
		// je prend donc le parti de vider le savedInstanceState pour purger les
		// Fragmentstate inutiles.
		// Au lieu de faire un onCreate(null), je le conserve tout de même le
		// savedInstanceState pour pouvoir
		// mémorier la date à laquelle l'utilisateur s'est arrêté.
		//
		savedInstanceState.clear();
		savedInstanceState.putString(BUNDLE_VAR____CURRENT_DAY,
				ToolBox.getSqlDate(getCurrentDay()));

		savedInstanceState.putString(BUNDLE_VAR____VIEWMODE,
				mCurrentViewMode.name());

	}
}
