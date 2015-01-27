package com.rdupuis.amikcal.useractivitycomponent;

import java.util.ArrayList;
import java.util.HashMap;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.data.ContentDescriptorObj;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Act_UserActivityComponentList extends Activity {
	Intent mIntent;
	Resources mResources;
	public UserActivityComponentObj CurrentUAComponent;
	private ListView maListViewPerso;
	public static final String INTENT_IN____USER_ACTIVITY_COMPONENT_LIST____ID_OF_USER_ACTIVITY = "ID_OF_USER_ACTIVITY";
	public static final int ACTIVITY_ID = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CurrentUAComponent = new UserActivityComponentObj();
		setContentView(R.layout.view_components_list);
		mResources = getResources();
		mIntent = getIntent();

		try {
			CurrentUAComponent
					.setParentId(Long.parseLong(mIntent.getStringExtra(mResources
							.getString(R.string.INTENT_IN____USER_ACTIVITY_COMPONENT_LIST____ID_OF_USER_ACTIVITY))));
			generateUserActivityComponentList(CurrentUAComponent.getParentId());
		} catch (Exception e) {
			CurrentUAComponent.setParentId(0l);
		}
	}// fin du onCreate

	// lorsque l'activité s'arrête, il faut renvoyer le résultat à l'écran
	// UserActivity
	public void onStop() {
		super.onStop();
		Toast.makeText(this, "Activité Activity Component stopée",
				Toast.LENGTH_SHORT).show();
		this.setResult(RESULT_OK, mIntent);
	}

	/*
	 * public void onPause(){ super.onPause(); Toast.makeText(this,
	 * "Activité pause", Toast.LENGTH_SHORT).show();
	 * 
	 * //on appelle setResult pour déclancher le onActivityResult de l'activity
	 * mère. this.setResult(RESULT_OK, mIntent);
	 * 
	 * //On termine l'Acitvity this.finish(); }
	 */

	public void onClickComponent(String componentId, String activityId) {
		// Intent intent = new Intent(this,
		// Act_UserActivityComponentEditor.class);

		Intent intent = new Intent(this, Act_UserActivityComponentSlider.class);

		intent.putExtra(
				mResources
						.getString(R.string.INTENT_IN____USER_ACTIVITY_COMPONENT_EDITOR____ID_OF_COMPONENT),
				componentId);
		intent.putExtra(
				mResources
						.getString(R.string.INTENT_IN____USER_ACTIVITY_COMPONENT_EDITOR____ID_OF_USER_ACTIVITY),
				activityId);
		startActivityForResult(intent, R.integer.ACTY_COMPONENT);
	}

	public void onClickAdd(View v) {
		onClickComponent(null, String.valueOf(CurrentUAComponent.getParentId()));
	}

	/**
	 * Gère le retour de l'appel à une autre activitée
	 * 
	 * @param requestCode
	 * @param resultCode
	 * @param intent
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		switch (requestCode) {

		case R.integer.ACTY_COMPONENT:

			if (resultCode == RESULT_OK) {

				cleanList();
				generateUserActivityComponentList(CurrentUAComponent
						.getParentId());

			}
			break;
		default:
			break;

		}
	}

	protected void cleanList() {
		maListViewPerso.removeAllViewsInLayout();
	}

	protected void generateUserActivityComponentList(Long parentId) {

		// Récupération de la listview créée dans le fichier customizedlist.xml
		maListViewPerso = (ListView) findViewById(R.id.listviewperso);

		// Création de la ArrayList qui nous permettra de remplire la listView
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

		// On déclare la HashMap qui contiendra les informations pour un item
		HashMap<String, String> map;
		map = new HashMap<String, String>();

		Uri selectionUri = ContentUris
				.withAppendedId(
						ContentDescriptorObj.ViewActivityComponent.URI_VIEW_ACTIVITY_COMPONENTS_BY_ACTIVITY,
						parentId);

		// On cré un curseur pour lire la table des aliments consommés
		Cursor cur = this.getContentResolver().query(selectionUri, null, null,
				null,
				ContentDescriptorObj.ViewActivityComponent.Columns.LAST_UPDATE);

		final int INDX_COL_ID = cur
				.getColumnIndex(ContentDescriptorObj.ViewActivityComponent.Columns.ID);
		final int INDX_COL_NAME = cur
				.getColumnIndex(ContentDescriptorObj.ViewActivityComponent.Columns.ENERGY_NAME);
		final int INDX_COL_QTY = cur
				.getColumnIndex(ContentDescriptorObj.ViewActivityComponent.Columns.QUANTITY);
		final int INDX_COL_UNIT = cur
				.getColumnIndex(ContentDescriptorObj.ViewActivityComponent.Columns.UNIT_NAME);
		final int INDX_COL_ACTIVITY = cur
				.getColumnIndex(ContentDescriptorObj.ViewActivityComponent.Columns.PARENT_ID);

		final int INDX_COL_NBKCAL = cur
				.getColumnIndex(ContentDescriptorObj.ViewActivityComponent.Columns.MNT_ENERGY);
		final int INDX_COL_NBGLU = cur
				.getColumnIndex(ContentDescriptorObj.ViewActivityComponent.Columns.MNT_GLUCIDS);
		final int INDX_COL_NBLIP = cur
				.getColumnIndex(ContentDescriptorObj.ViewActivityComponent.Columns.MNT_LIPIDS);
		final int INDX_COL_NBPRO = cur
				.getColumnIndex(ContentDescriptorObj.ViewActivityComponent.Columns.MNT_PROTEINS);

		// faire un move First pour positionner le pointeur
		// cur.moveToFirst();

		if (cur.moveToFirst()) {

			do {

				map = new HashMap<String, String>();
				map.put("componentId", cur.getString(INDX_COL_ID));
				map.put("name", cur.getString(INDX_COL_NAME));
				map.put("quantity",
						cur.getString(INDX_COL_QTY) + " "
								+ cur.getString(INDX_COL_UNIT));
				map.put("energy", cur.getString(INDX_COL_NBKCAL) + " " + "Kcal");
				map.put("equiv", cur.getString(INDX_COL_NBKCAL) + "g");
				map.put("nbglu", cur.getString(INDX_COL_NBGLU) + "g");
				map.put("nbpro", cur.getString(INDX_COL_NBPRO) + "g");
				map.put("nblip", cur.getString(INDX_COL_NBLIP) + "g");
				map.put("activityId", cur.getString(INDX_COL_ACTIVITY));
				listItem.add(map);
			} while (cur.moveToNext());

		}
		cur.close();

		// Création d'un SimpleAdapter qui se chargera de mettre les items
		// présent dans notre list (listItem)
		// dans la vue affichageitem
		SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(),
				listItem, R.layout.list_item_activity_lunch_component,
				new String[] { "name", "quantity", "energy", "equiv", "nbglu",
						"nbpro", "nblip" }, new int[] { R.id.itemfood_name,
						R.id.itemfood_quantity, R.id.itemfood_nbkcal,
						R.id.itemfood_equiv, R.id.itemfood_glu,
						R.id.itemfood_pro, R.id.itemfood_lip });

		// On attribut à notre listView l'adapter que l'on vient de créer
		maListViewPerso.setAdapter(mSchedule);

		// Enfin on met un écouteur d'évènement sur notre listView
		maListViewPerso.setOnItemClickListener(new OnItemClickListener() {
			// @Override
			@SuppressWarnings("unchecked")
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// on récupère la HashMap contenant les infos de notre item
				// (titre, description, img)
				HashMap<String, String> map = (HashMap<String, String>) maListViewPerso
						.getItemAtPosition(position);

				/*
				 * //on créer une boite de dialogue AlertDialog.Builder adb =
				 * new AlertDialog.Builder(Acty_ActivityComponentList.this);
				 * //on attribut un titre à notre boite de dialogue
				 * adb.setTitle("Sélection Item"); //on insère un message à
				 * notre boite de dialogue, et ici on affiche le titre de l'item
				 * cliqué adb.setMessage("Votre choix : "+map.get("titre"));
				 * //on indique que l'on veut le bouton ok à notre boite de
				 * dialogue adb.setPositiveButton("Ok", null); //on affiche la
				 * boite de dialogue adb.show();
				 */

				onClickComponent(map.get("componentId"), map.get("activityId"));

			}
		});

		// Enfin on met un écouteur d'évènement long sur notre listView
		maListViewPerso
				.setOnItemLongClickListener(new OnItemLongClickListener() {
					// @Override
					@SuppressWarnings("rawtypes")
					public boolean onItemLongClick(AdapterView parent, View v,
							int position, long id) {
						// int ilaposition=position;
						// création d'un boite de dialogue pour confirmer le
						// choix

						new AlertDialog.Builder(parent.getContext())

								// new
								// AlertDialog.Builder(Act_UserActivityComponentList.this)
								.setTitle("Confirmation")
								.setMessage(
										"Voulez vous supprimer cet component de la liste")
								.setPositiveButton("OUI",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int whichButton) {
												// aTableauComponentaff.remove(ilaposition);
												// aTableauComponent.remove(ilaposition);
												// maListViewPerso = (ListView)
												// findViewById(android.R.id.list);
												// final ArrayAdapter adapter2 =
												// new
												// ArrayAdapter(getBaseContext(),
												// android.R.layout.simple_list_item_1,aTableauComponentaff);
												// maListViewPerso.setAdapter(adapter2);
												//
											}
										})
								.setNegativeButton("NON",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int whichButton) {
												/*
												 * User clicked Cancel so do
												 * some stuff
												 */
											}
										})

								.show();

						return true;
					}// public boolean onItemLongClick
				}); // fin du OnItemLongClickListener

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar_component_list, menu);
		return true;

	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);

	}

	/**
	 * 
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.actionbar_componentlist_item_add:
			onClickAdd(null);
			break;
		case R.id.actionbar_componentlist_item_back:
			finish();
			break;

		default:
			return super.onOptionsItemSelected(item);
		}

		// invalidateOptionsMenu va appeller la méthode onPrepareOptionsMenu();
		invalidateOptionsMenu();
		return true;
	}

	// *************************************
} // end class