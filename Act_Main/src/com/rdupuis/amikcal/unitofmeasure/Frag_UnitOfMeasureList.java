package com.rdupuis.amikcal.unitofmeasure;

import java.util.ArrayList;
import java.util.HashMap;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.data.ContentDescriptorObj;

import android.app.AlertDialog;

import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

//2013-08-25 - R.Dupuis
//Fragment permetant d'afficher une liste d'unit� de mesure 
public class Frag_UnitOfMeasureList extends Fragment {
	Intent mIntent;
	private ListView maListViewPerso;
	static Long currentId;
	static Long energyId = 0l;
	private static Long WITH_NO_FILTER = 0l;
	private Resources mResources;
    private View mainView;
	//Cr�ation de la vue
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mainView = inflater.inflate(R.layout.view_unit_list,
				container, false);

		mIntent = getActivity().getIntent();
		mResources = getActivity().getResources();

		// si on demande une liste des unit�s avec un ID d'�nergy en entr�e
		// on n'affiche que les unit�s qui on �t� utilis� pour cet ID
		// dans le cas contraire, on affiche la liste de toutes les unit�es
		// connues.
		try {
			energyId = Long.parseLong(mIntent.getStringExtra(mResources
					.getString(R.string.INTENT_IN____UNITS_LIST____ID_OF_ENERGY)));
			generateList(energyId);
		} catch (Exception e) {
			generateList(WITH_NO_FILTER);
		}

		return mainView;
	}

	// Action de cliquer sur une unit�e
	// on lance l'�diteur d'unit�
	public void onClickUnit(View v, String id) {
		Intent intent = new Intent(getActivity(), Act_UnitOfMeasureEditor.class);
		intent.putExtra(mResources
				.getString(R.string.INTENT_IN____UNITS_EDITOR____ID_OF_UNIT), id);
		startActivityForResult(intent, R.integer.ACTY_UNIT);

	}

	//Ajouter une unit�
	public void onClickAdd(View v) {

		onClickUnit(v, null);

	}


	/*********************************************************************************************
     * 
     * 
     ********************************************************************************************/
	protected void cleanList() {
		maListViewPerso.removeAllViewsInLayout();
	}

	/*********************************************************************************************
     *
     *
     ********************************************************************************************/

	protected void generateList(long energyId) {

		Uri mUri;

		if (energyId == WITH_NO_FILTER) {
			mUri = ContentDescriptorObj.Units.URI_CONTENT_UNITS;

		} else {

			mUri = ContentUris.withAppendedId(
					ContentDescriptorObj.CustomQuery.URI_USED_UNITS_FOR_ENERGY,
					energyId);
		}

		// R�cup�ration de la listview cr��e dans le fichier customizedlist.xml
		maListViewPerso = (ListView) mainView.findViewById(R.id.listviewperso);

		// Cr�ation de la ArrayList qui nous permettra de remplire la listView
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

		// On d�clare la HashMap qui contiendra les informations pour un item
		HashMap<String, String> map;
		map = new HashMap<String, String>();

		// On cr� un curseur pour lire la table des unit�es
		Cursor cur = getActivity().getContentResolver().query(mUri, null, null, null,
				null);

		final int INDX_COL_ID = cur
				.getColumnIndex(ContentDescriptorObj.Units.Columns.ID);
		final int INDX_COL_NAME = cur
				.getColumnIndex(ContentDescriptorObj.Units.Columns.NAME);
		final int INDX_COL_SYMBOL = cur
				.getColumnIndex(ContentDescriptorObj.Units.Columns.SYMBOL);

		// faire un move First pour positionner le pointeur

		if (cur.moveToFirst()) {

			do {
				map = new HashMap<String, String>();

				map.put("id", cur.getString(INDX_COL_ID));
				map.put("name", cur.getString(INDX_COL_NAME));
				map.put("symbol", cur.getString(INDX_COL_SYMBOL));
				listItem.add(map);
			} while (cur.moveToNext());

		}
		cur.close();

		// Cr�ation d'un SimpleAdapter qui se chargera de mettre les items
		// pr�sent dans notre list (listItem)
		// dans la vue affichageitem
		SimpleAdapter mSchedule = new SimpleAdapter(getActivity().getBaseContext(),
				listItem, R.layout.list_item_unit, new String[] { "name",
						"symbol" }, new int[] { R.id.item_unit_name,
						R.id.item_unit_symbol });

		// On attribut � notre listView l'adapter que l'on vient de cr�er
		maListViewPerso.setAdapter(mSchedule);

		// ********************************************************
		// on met un �couteur d'�v�nement sur notre listView
		// ********************************************************
		maListViewPerso.setOnItemClickListener(new OnItemClickListener() {
			// @Override
			@SuppressWarnings("unchecked")
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// on r�cup�re la HashMap contenant les infos de notre item
				// (titre, description, img)
				HashMap<String, String> map = (HashMap<String, String>) maListViewPerso
						.getItemAtPosition(position);
				OnChooseUnit(view, map.get("id"));
			}
		});

		// ********************************************************
		// on met un �couteur d'�v�nement sur notre listView
		// ********************************************************
		maListViewPerso
				.setOnItemLongClickListener(new OnItemLongClickListener() {
					// @Override
					@SuppressWarnings({ "rawtypes", "unchecked" })
					public boolean onItemLongClick(AdapterView parent, View v,
							int position, long id) {
						HashMap<String, String> map = (HashMap<String, String>) maListViewPerso
								.getItemAtPosition(position);
						Frag_UnitOfMeasureList.currentId = Long.parseLong(map
								.get("id"));
						// cr�ation d'un boite de dialogue pour confirmer le
						// choix
						new AlertDialog.Builder(Frag_UnitOfMeasureList.this.getActivity())
								.setTitle("Confirmation")
								.setMessage("Que souhaitez-vous faire ?")
								.setPositiveButton("Editer",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int whichButton) {
												/*
												 * User clicked edit so do some
												 * stuff
												 */
												onClick_edit();

											}
										})
								.setNegativeButton("Supprimer",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int whichButton) {
												/*
												 * User delete Cancel so do some
												 * stuff
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
										}).show();

						return true;
					}// public boolean onItemLongClick
				}); // fin du OnItemLongClickListener

	}

	/*********************************************************************************
     * 
     * 
     **********************************************************************************/

	public void OnChooseUnit(View v, String id) {

		
		}

	/*********************************************************************************
     * 
     * 
     **********************************************************************************/
	public void OnDelete(Long id) {

		// afficher une demande de confirmation

	}

	public void onClick_edit() {
		editUnit(Frag_UnitOfMeasureList.currentId.toString());
	}

	public void editUnit(String id) {
		Intent intent = new Intent(getActivity(), Act_UnitOfMeasureEditor.class);
		intent.putExtra("UnitId", id);
		startActivityForResult(intent, R.integer.ACTY_UNIT);
	}

	
	
} // end class