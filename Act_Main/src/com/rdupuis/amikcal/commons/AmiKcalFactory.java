package com.rdupuis.amikcal.commons;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.energy.EnergyReference;
import com.rdupuis.amikcal.equivalence.EquivalenceObj;
import com.rdupuis.amikcal.unitofmeasure.UnitOfMeasureObj;
import com.rdupuis.amikcal.useractivity.UserActivity;
import com.rdupuis.amikcal.useractivity.UserActivityItem;
import com.rdupuis.amikcal.useractivity.UserActivityLunch;
import com.rdupuis.amikcal.useractivity.UserActivityLunchItem;
import com.rdupuis.amikcal.useractivity.UserActivityMove;
import com.rdupuis.amikcal.useractivity.UserActivityMoveItem;
import com.rdupuis.amikcal.useractivity.UserActivityWeight;
import com.rdupuis.amikcal.useractivity.UserActivityWeightItem;
import com.rdupuis.amikcal.useractivitycomponent.UserActivityComponent;

public final class AmiKcalFactory {

	public ContentResolver contentResolver;

	/**
	 * Retourne une �nergie stock� dans la base � partir de son id.
	 * 
	 * @since 01-06-2012
	 * @param _id
	 *            - Identifiant
	 * @return (Energy) un objet "�nergie"
	 **/
	public EnergyReference createEnergyFromId(long _id) {

		EnergyReference e = new EnergyReference();

		e.setId(_id);

		Uri selectUri = ContentUris.withAppendedId(
				ContentDescriptorObj.Energies.URI_CONTENT_ENERGIES, _id);
		Cursor cursor = this.contentResolver.query(selectUri, null, null, null,
				null);

		final int INDX_ENERGY_NAME = cursor
				.getColumnIndex(ContentDescriptorObj.Energies.Columns.NAME);
		final int INDX_QUANTITY = cursor
				.getColumnIndex(ContentDescriptorObj.Energies.Columns.QUANTITY);
		final int INDX_MNT_ENERGY = cursor
				.getColumnIndex(ContentDescriptorObj.Energies.Columns.MNT_ENERGY);
		final int INDX_MNT_PROTEINS = cursor
				.getColumnIndex(ContentDescriptorObj.Energies.Columns.MNT_PROTEINS);
		final int INDX_MNT_GLUCIDS = cursor
				.getColumnIndex(ContentDescriptorObj.Energies.Columns.MNT_GLUCIDS);
		final int INDX_MNT_LIPIDS = cursor
				.getColumnIndex(ContentDescriptorObj.Energies.Columns.MNT_LIPIDS);

		final int INDX_FK_UNIT = cursor
				.getColumnIndex(ContentDescriptorObj.Energies.Columns.FK_UNIT);

		// faire un move First pour positionner le pointeur, sinon on pointe sur
		// null
		if (cursor.moveToFirst()) {

			e.setName(cursor.getString(INDX_ENERGY_NAME));
			e.setCalories(Float.parseFloat(cursor.getString(INDX_MNT_ENERGY)));
			e.setProteins(Float.parseFloat(cursor.getString(INDX_MNT_PROTEINS)));
			e.setGlucids(Float.parseFloat(cursor.getString(INDX_MNT_GLUCIDS)));
			e.setLipids(Float.parseFloat(cursor.getString(INDX_MNT_LIPIDS)));
			e.setQuantityReference((Float.parseFloat(cursor
					.getString(INDX_QUANTITY))));
			e.unit = this.createUnitOfMeasureObjFromId(Long.parseLong(cursor
					.getString(INDX_FK_UNIT)));

		} else {
			e.setName("");
			e.setCalories(0.0f);
			e.setProteins(0.0f);
			e.setGlucids(0.0f);
			e.setLipids(0.0f);
			e.setQuantityReference(0.0f);
			e.unit = new UnitOfMeasureObj();

		}

		cursor.close();
		return e;

	}

	/**
	 * Retourne une unit�e de mesure stock� dans la base � partir de son id.
	 * 
	 * @since 01-06-2012
	 * @param _id
	 * @return (UnitOfMeasureObj) un objet "Unit�e de mesure"
	 **/

	public UnitOfMeasureObj createUnitOfMeasureObjFromId(long _id) {

		UnitOfMeasureObj u = new UnitOfMeasureObj();

		u.setId(_id);

		Uri selectUri = ContentUris.withAppendedId(
				ContentDescriptorObj.Units.URI_CONTENT_UNITS, _id);
		Cursor cursor = this.contentResolver.query(selectUri, null, null, null,
				null);

		final int INDX_NAME = cursor
				.getColumnIndex(ContentDescriptorObj.Units.Columns.NAME);
		final int INDX_SYMBOL = cursor
				.getColumnIndex(ContentDescriptorObj.Units.Columns.SYMBOL);

		// faire un move First pour positionner le pointeur, sinon on pointe sur
		// null
		if (cursor.moveToFirst()) {

			u.setName(cursor.getString(INDX_NAME));
			u.setSymbol(cursor.getString(INDX_SYMBOL));
		} else {
			u.setName("");
			u.setSymbol("");
		}
		cursor.close();
		return u;

	}

	/**
	 * Retourne une �quivalence de mesure stock� dans la base � partir de son
	 * id.
	 * 
	 * @since 01-06-2012
	 * @param _id
	 * @return (EquivalenceObj) un objet "Equivalence"
	 **/
	public EquivalenceObj createEquivalenceObjFromId(long _id) {

		EquivalenceObj mEquivalence = new EquivalenceObj();

		Uri selectUri = ContentUris
				.withAppendedId(
						ContentDescriptorObj.Equivalences.URI_CONTENT_EQUIVALENCES,
						_id);

		// On cr�e un curseur pour lire la table des aliments
		Cursor cursor = this.contentResolver.query(selectUri, null, null, null,
				null);

		final int INDX_FK_ENERGY = cursor
				.getColumnIndex(ContentDescriptorObj.Equivalences.Columns.FK_ENERGY);
		final int INDX_FK_UNIT_IN = cursor
				.getColumnIndex(ContentDescriptorObj.Equivalences.Columns.FK_UNIT_IN);
		final int INDX_QUANTITY_OUT = cursor
				.getColumnIndex(ContentDescriptorObj.Equivalences.Columns.QUANTITY_OUT);
		final int INDX_FK_UNIT_OUT = cursor
				.getColumnIndex(ContentDescriptorObj.Equivalences.Columns.FK_UNIT_OUT);

		// faire un move First pour positionner le pointeur, sinon on pointe sur
		// null
		if (cursor.moveToFirst()) {

			mEquivalence.setId(_id);
			mEquivalence.energy = createEnergyFromId(cursor
					.getLong(INDX_FK_ENERGY));
			mEquivalence.unitIn = createUnitOfMeasureObjFromId(cursor
					.getLong(INDX_FK_UNIT_IN));
			mEquivalence.unitOut = createUnitOfMeasureObjFromId(cursor
					.getLong(INDX_FK_UNIT_OUT));

			Log.i("Qt� lue", String.valueOf(cursor.getFloat(INDX_QUANTITY_OUT)));
			mEquivalence.setQuantity(cursor.getFloat(INDX_QUANTITY_OUT));

		} else {
			Log.e("AmickalFactory.createEquivalenceObjFromId",
					"Equivalence non trouv�");
		}

		cursor.close();
		return mEquivalence;

	}

	/**
	 * Retourne une Activit� utilisateur stock�e dans la base � partir de son
	 * id.
	 * 
	 * @since 01-06-2012
	 * @param _id
	 * @return (UserActivityObj) un objet "Activit� d'utilisateur"
	 **/
	public UserActivity reloadUserActivityObjFromId(long _id) {

		Uri request = ContentUris.withAppendedId(
				ContentDescriptorObj.UserActivities.URI_SELECT_USER_ACTIVITIES,
				_id);

		Cursor cur = this.contentResolver
				.query(request, null, null, null, null);

		final int ACTIVITY_ID = cur
				.getColumnIndex(ContentDescriptorObj.UserActivities.Columns.ID);
		final int ACTIVITY_TITLE = cur
				.getColumnIndex(ContentDescriptorObj.UserActivities.Columns.TITLE);
		final int ACTIVITY_TYPE = cur
				.getColumnIndex(ContentDescriptorObj.UserActivities.Columns.TYPE);
		final int ACTIVITY_DATE = cur
				.getColumnIndex(ContentDescriptorObj.UserActivities.Columns.DATE);

		UserActivity userActivity =null;
		// faire un move First pour positionner le pointeur, sinon on pointe sur
		// null
		if (cur.moveToFirst()) {

			// en fonction du type d'activit�e, on va retourner l'objet adequat
			switch (UserActivity.UAType.valueOf(cur.getString(ACTIVITY_TYPE))) {
			case LUNCH:
				userActivity = new UserActivityLunch();
				break;
			case MOVE:
				userActivity = new UserActivityMove();
				break;
			case WEIGHT:
				userActivity = new UserActivityWeight();
				((UserActivityWeight) userActivity).setWeight(new WeightObj(
						cur.getString(ACTIVITY_TITLE)));
				break;
			}

			userActivity.set_id(cur.getLong(ACTIVITY_ID));
			userActivity.setTitle(cur.getString(ACTIVITY_TITLE));
			userActivity.setTypeByName(cur.getString(ACTIVITY_TYPE));
			userActivity.setDay(ToolBox.parseSQLDatetime(cur
					.getString(ACTIVITY_DATE)));

		} else {
			// par d�faut on fait une activit� lunch
			userActivity = new UserActivityLunch();
			userActivity.setTitle("UserActivity Not Found");

		}
		return userActivity;
	}

	
	
	
	
	public UserActivityItem createUserActivityItemFromId(Activity activity, long _id){
		UserActivity userActivity = reloadUserActivityObjFromId(_id);
		
		UserActivityItem userActivityItem = null;
		
		// en fonction du type d'activit�e, on va retourner l'objet adequat
		switch (userActivity.type) {
		case LUNCH:
			userActivityItem = new UserActivityLunchItem(activity);
			
			break;
		case MOVE:
			userActivityItem = new UserActivityMoveItem(activity);
			break;
		case WEIGHT:
			userActivityItem = new UserActivityWeightItem(activity);
			
			break;
		}

		userActivityItem.mUserActivity = userActivity;
	return userActivityItem;
	}
	
	
	
	/**
	 * 
	 * @param _id
	 */
	public UserActivityComponent loadComponent(Long _id) {
		// On fabrique l'Uri pour le contentProvider
		// celle-ci est du style content://xxxxx.xxxxxxxx.xxxxxxx/# o� le di�se
		// est l'Id � rechercher
		
		UserActivityComponent mUAC = new UserActivityComponent();
		Uri selectUri = ContentUris
				.withAppendedId(
						ContentDescriptorObj.ActivityComponent.URI_SELECT_ACTIVITY_COMPONENTS,
						_id);

		// On cr�e un curseur pour lire la table des aliments
		Cursor cur = contentResolver.query(selectUri, null,
				_id.toString(), null, null);

		final int INDX_ENERGY_ID = cur
				.getColumnIndex(ContentDescriptorObj.ActivityComponent.Columns.FK_ENERGY);
		final int INDX_QUANTITY = cur
				.getColumnIndex(ContentDescriptorObj.ActivityComponent.Columns.QUANTITY);
		final int INDX_UNIT_ID = cur
				.getColumnIndex(ContentDescriptorObj.ActivityComponent.Columns.FK_UNIT);

		// faire un move First pour positionner le pointeur, sinon on pointe sur
		// null
		if (cur.moveToFirst()) {

			mUAC.getEnergy().setId(cur.getLong(INDX_ENERGY_ID));
			// this.mUAC.getEnergy().setName(cur.getString(INDX_ENERGY_NAME));

			
			mUAC.setEnergy(this.createEnergyFromId(cur
					.getLong(INDX_ENERGY_ID)));
			mUAC.setUnitMeasure(this.createUnitOfMeasureObjFromId(cur
					.getLong(INDX_UNIT_ID)));
			mUAC
					.setQuantity(Float.parseFloat(cur.getString(INDX_QUANTITY)));
			

		} else {
			String message = "Composant " + String.valueOf(_id) + " non trouv�";
			Log.e("loadComponent", message);

		}

		return mUAC;
	}

	
	
	
} // * end-class
