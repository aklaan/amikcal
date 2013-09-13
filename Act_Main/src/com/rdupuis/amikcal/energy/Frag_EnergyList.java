package com.rdupuis.amikcal.energy;

import java.util.ArrayList;
import java.util.HashMap;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.data.ContentDescriptorObj;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * <b>Act_EnergyList est la classe qui affiche la vue liste d'aliments.</b>
 * <p>
 * Cette vue comporte :
 * <ul>
 * <li>Une zone de recherche pour filtrer les aliments par nom.</li>
 * <li>Une liste d'aliment.</li>
 * </ul>
 * </p>
 * 
 * 
 * 
 * @author Rodolphe Dupuis
 * @version 0.1
 */

public class Frag_EnergyList extends Fragment {
	Intent mIntent;
	static Long currentId;
	String currentFilter = "";
	Resources mResources;
	private ListView maListViewPerso;
	private View mainView;

	/**
	 * This function is Called when the activity is first created.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mainView = inflater
				.inflate(R.layout.view_energy_list, container, false);

		mIntent = getActivity().getIntent();
		mResources = getResources();
		// temporaire.......................
		// on alimente l'action bar de l'activité !!
		// attention il risque de ne pas y en avoir
		// ce n'est pas au fragment de gérer ceci !!

		getActivity().getActionBar().setTitle("Aliments");
		generateList();

		((TextView) mainView.findViewById(R.id.energy_list_view_tv_filter))
				.addTextChangedListener(new TextWatcher() {

					public void afterTextChanged(Editable s) {
						// ((TextView)findViewById(R.id.numcaratteri)).setText(String.format(getString(R.string.caratteri),
						// s.length()));

					}

					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {
						// TODO Auto-generated method stub

					}

					public void onTextChanged(CharSequence s, int start,
							int before, int count) {

						cleanList();
						currentFilter = s.toString();
						generateList();

					}

				});
		return mainView;
	}// fin du onCreate

	/**
	 * Appel la vue d'édition d'un aliment (energy)
	 */
	public void editEnergy(String id) {
		Intent intent = new Intent(getActivity(), Act_EnergyEditor.class);
		intent.putExtra(mResources
				.getString(R.string.INTENT_IN_ENERGY_EDITOR_ID_OF_ENERGY), id);
		startActivityForResult(intent, R.integer.ACTY_ENERGY);
	}

	/**
	 * onClickAdd est générique dans les écrans du même type on doit donc
	 * redéfinir la fonction
	 * 
	 */
	public void onClickAdd(View v) {

		editEnergy(null);

	}

	/**
	 * Gère les actions à effectuer en cas de retours des Intent appelés
	 * 
	 * 
	 * 
	 * <p>
	 * Liste des actions actuellement gérées
	 * <ul>
	 * <li>Retour depuis l'écran d'édition d'un aliment:</li>
	 * <ul>
	 * <li>Effacer la liste actuelle.</li>
	 * <li>Regénérer la liste avec les données actualisées.</li>
	 * </ul>
	 * 
	 * </ul>
	 * </p>
	 * 
	 * @param requestCode
	 *            le code le l'action demandée
	 * 
	 * @param resultCode
	 *            le code retour envoyé par l'activitée appellée
	 * 
	 * @param intent
	 *            l'intent utilisée
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		switch (requestCode) {

		case R.integer.ACTY_ENERGY:

			if (resultCode == Activity.RESULT_OK) {

				cleanList();
				generateList();

			}
			break;
		default:
			break;

		}
	}

	/**
	 * ____________________________________________________________________________
	 * effacer la liste
	 * 
	 */
	protected void cleanList() {
		maListViewPerso.removeAllViewsInLayout();
	}

	/**
	 * ________________________________________________________________________
	 * générer la liste des énergie à partir des informations stockées dans la
	 * base de donnée.
	 */
	protected void generateList() {

		Uri selectUri;

		if (currentFilter == null || currentFilter.length() == 0) {
			selectUri = ContentDescriptorObj.Energies.URI_CONTENT_ENERGIES;
		} else {
			selectUri = ContentDescriptorObj.Energies.URI_ENERGIES_LIKE
					.buildUpon().appendPath(currentFilter).build();
		}

		// Récupération de la listview créée dans le fichier customizedlist.xml
		maListViewPerso = (ListView) mainView.findViewById(R.id.listviewperso);

		// Création de la ArrayList qui nous permettra de remplire la listView
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

		// On déclare la HashMap qui contiendra les informations pour un item
		HashMap<String, String> map;
		map = new HashMap<String, String>();

		// On cré un curseur pour lire la table des aliments consommés
		Cursor cur = getActivity().getContentResolver().query(selectUri, null,
				null, null, null);

		final int INDX_COL_ID = cur
				.getColumnIndex(ContentDescriptorObj.Energies.Columns.ID);
		final int INDX_COL_NAME = cur
				.getColumnIndex(ContentDescriptorObj.Energies.Columns.NAME);
		final int INDX_COL_NBKCAL = cur
				.getColumnIndex(ContentDescriptorObj.Energies.Columns.MNT_ENERGY);
		// final int INDX_COL_NBGLU =
		// cur.getColumnIndex(ContentDescriptorObj.Energies.Columns.MNT_GLUCIDS);
		// final int INDX_COL_NBLIP =
		// cur.getColumnIndex(ContentDescriptorObj.Energies.Columns.MNT_LIPIDS);
		// final int INDX_COL_NBPRO =
		// cur.getColumnIndex(ContentDescriptorObj.Energies.Columns.MNT_PROTEINS);
		// final int INDX_COL_ISLIQUID =
		// cur.getColumnIndex(ContentDescriptorObj.Energies.Columns.ISLIQUID);
		// faire un move First pour positionner le pointeur
		// cur.moveToFirst();

		// System.out.println(cur.getString(INDX_COL_NAME));

		if (cur.moveToFirst()) {

			do {
				map = new HashMap<String, String>();
				map.put("id", cur.getString(INDX_COL_ID));
				map.put("name", cur.getString(INDX_COL_NAME));
				map.put("energy", cur.getString(INDX_COL_NBKCAL) + " Kcal");
				// map.put("lipids", cur.getString(INDX_COL_NBLIP)+"g");
				// map.put("proteins", cur.getString(INDX_COL_NBPRO)+"g");
				// map.put("glucids", cur.getString(INDX_COL_NBGLU) +"g");
				// map.put("isliquid", cur.getString(INDX_COL_ISLIQUID) +"g");

				listItem.add(map);
			} while (cur.moveToNext());

		}
		cur.close();

		// Création d'un SimpleAdapter qui se chargera de mettre les items
		// présent dans notre list (listItem)
		// dans la vue affichageitem
		SimpleAdapter mSchedule = new SimpleAdapter(getActivity()
				.getBaseContext(), listItem, R.layout.list_item_energy_food,

		new String[] { "name", "energy" }, new int[] {
				R.id.item_energy_food_name, R.id.item_energy_food_nbkcal

		/*
		 * new String[] {"name", "energy", "lipids","proteins","glucids"}, new
		 * int[] {R.id.item_energy_food_name, R.id.item_energy_food_nbkcal,
		 * R.id.item_energy_food_lip, R.id.item_energy_food_pro,
		 * R.id.item_energy_food_glu
		 */

		});

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

				OnChooseEnergy(view, map.get("id"));

			}
		});

		// Enfin on met un écouteur d'évènement long sur notre listView
		maListViewPerso
				.setOnItemLongClickListener(new OnItemLongClickListener() {
					// @Override
					@SuppressWarnings("unchecked")
					public boolean onItemLongClick(AdapterView<?> parent,
							View v, int position, long id) {

						HashMap<String, String> map = (HashMap<String, String>) maListViewPerso
								.getItemAtPosition(position);
						Frag_EnergyList.currentId = Long.parseLong(map
								.get("id"));

						// int ilaposition=position;
						// création d'un boite de dialogue pour confirmer le
						// choix
						new AlertDialog.Builder(Frag_EnergyList.this
								.getActivity())
								.setTitle("Confirmation")
								.setMessage("Que voulez-vous faire ?")
								.setPositiveButton("Editer",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int whichButton) {

												onClick_edit();
											}
										})
								.setNegativeButton("Supprimer",
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

								.setNeutralButton("Annuler",
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

	/**
	 * ________________________________________________________ Action a
	 * effectuer lorsque l'on séléctionne une énergie
	 */

	public void OnChooseEnergy(View v, String id) {

		// on alimente le résultat dans l'Intent pour que l'Activity mère puisse
		// récupérer la valeur.
		this.mIntent.putExtra(mResources
				.getString(R.string.INTENT_OUT_ENERGY_LIST_ID_OF_ENERGY), id);

		// on appelle setResult pour déclencher le onActivityResult de
		// l'activity mère.
		getActivity().setResult(Activity.RESULT_OK, mIntent);

		// On termine l'Activity
		getActivity().finish();
	}

	/**
	 * _________________________________________________________________________
	 * Action a effectuer lorsque l'on clique sur éditer l'énergie séléctionnée
	 */
	public void onClick_edit() {
		editEnergy(Frag_EnergyList.currentId.toString());
	}

	// *************************************
} // end class