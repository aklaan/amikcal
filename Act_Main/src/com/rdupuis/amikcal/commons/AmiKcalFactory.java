package com.rdupuis.amikcal.commons;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.rdupuis.amikcal.commons.AppConsts.UA_CLASS_CD_MAP;
import com.rdupuis.amikcal.commons.AppConsts.UNIT_CLASS_MAP;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.energy.EnergyReference;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.equivalence.EquivalenceObj;
import com.rdupuis.amikcal.unity.Unity;
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

	private ContentResolver contentResolver;

	public AmiKcalFactory(Activity activity) {
		this.contentResolver = activity.getContentResolver();

	}

	/**
	 * Retourne une énergie stocké dans la base à partir de son id.
	 * 
	 * @since 01-06-2012
	 * @param _id
	 *            - Identifiant
	 * @return (Energy) un objet "énergie"
	 **/
	public EnergySource loadEnergy(long _id) {

		EnergySource e = new EnergySource();

		e.setId(_id);

		Uri selectUri = ContentUris.withAppendedId(
				ContentDescriptorObj.TB_Energies.URI_CONTENT_ENERGIES, _id);
		Cursor cursor = this.contentResolver.query(selectUri, null, null, null,
				null);

		final int INDX_NRJ_NAME = cursor
				.getColumnIndex(ContentDescriptorObj.TB_Energies.Columns.NAME);
		
		final int INDX_NRJ_EFFECT = cursor
				.getColumnIndex(ContentDescriptorObj.TB_Energies.Columns.EFFECT);
		
		final int INDX_NRJ_STRUCTURE = cursor
				.getColumnIndex(ContentDescriptorObj.TB_Energies.Columns.STRUCTURE);
		
		
		/*
		 * final int INDX_QUANTITY = cursor
		 * .getColumnIndex(ContentDescriptorObj.TB_Energies.Columns.QUANTITY);
		 * final int INDX_MNT_ENERGY = cursor
		 * .getColumnIndex(ContentDescriptorObj.TB_Energies.Columns.MNT_ENERGY);
		 * final int INDX_MNT_PROTEINS = cursor
		 * .getColumnIndex(ContentDescriptorObj
		 * .TB_Energies.Columns.MNT_PROTEINS); final int INDX_MNT_GLUCIDS =
		 * cursor
		 * .getColumnIndex(ContentDescriptorObj.TB_Energies.Columns.MNT_GLUCIDS
		 * ); final int INDX_MNT_LIPIDS = cursor
		 * .getColumnIndex(ContentDescriptorObj.TB_Energies.Columns.MNT_LIPIDS);
		 * 
		 * final int INDX_FK_UNIT = cursor
		 * .getColumnIndex(ContentDescriptorObj.Tb_Energies.Columns.FK_UNIT);
		 */
		// faire un move First pour positionner le pointeur, sinon on pointe sur
		// null
		if (cursor.moveToFirst()) {

			e.setName(cursor.getString(INDX_ENERGY_NAME));
			/*
			 * e.setCalories(Float.parseFloat(cursor.getString(INDX_MNT_ENERGY)))
			 * ;
			 * e.setProteins(Float.parseFloat(cursor.getString(INDX_MNT_PROTEINS
			 * )));
			 * e.setGlucids(Float.parseFloat(cursor.getString(INDX_MNT_GLUCIDS
			 * )));
			 * e.setLipids(Float.parseFloat(cursor.getString(INDX_MNT_LIPIDS)));
			 * e.setQuantityReference((Float.parseFloat(cursor
			 * .getString(INDX_QUANTITY)))); e.unit =
			 * this.createUnitOfMeasureObjFromId(Long.parseLong(cursor
			 * .getString(INDX_FK_UNIT)));
			 */
		} else {
			e.setName("");
			/*
			 * e.setCalories(0.0f); e.setProteins(0.0f); e.setGlucids(0.0f);
			 * e.setLipids(0.0f); e.setQuantityReference(0.0f); e.unit = new
			 * Unity();
			 */
		}

		cursor.close();
		return e;

	}

	/**
	 * Retourne une unitée de mesure stocké dans la base à partir de son id.
	 * 
	 * @since 01-06-2012
	 * @param _id
	 * @return (UnitOfMeasureObj) un objet "Unitée de mesure"
	 **/

	public Unity load_Unity(long _id) {

		Unity u = new Unity();

		u.setId(_id);

		Uri selectUri = ContentUris.withAppendedId(
				ContentDescriptorObj.TB_Units.URI_CONTENT_UNITS, _id);
		Cursor cursor = this.contentResolver.query(selectUri, null, null, null,
				null);

		final int INDX_NAME = cursor
				.getColumnIndex(ContentDescriptorObj.TB_Units.Columns.LONG_NAME);
		final int INDX_SYMBOL = cursor
				.getColumnIndex(ContentDescriptorObj.TB_Units.Columns.SHORT_NAME);

		// faire un move First pour positionner le pointeur, sinon on pointe sur
		// null
		if (cursor.moveToFirst()) {

			u.setLongName(cursor.getString(INDX_NAME));
			u.setShortName(cursor.getString(INDX_SYMBOL));
		} else {
			u.setLongName("");
			u.setShortName("");
		}
		cursor.close();
		return u;

	}

	/**
	 * Retourne une équivalence de mesure stocké dans la base à partir de son
	 * id.
	 * 
	 * @since 01-06-2012
	 * @param _id
	 * @return (EquivalenceObj) un objet "Equivalence"
	 **/
	public EquivalenceObj createEquivalenceObjFromId(long _id) {
		/*
		 * EquivalenceObj mEquivalence = new EquivalenceObj();
		 * 
		 * Uri selectUri = ContentUris .withAppendedId(
		 * ContentDescriptorObj.Equivalences.URI_CONTENT_EQUIVALENCES, _id);
		 * 
		 * // On crée un curseur pour lire la table des aliments Cursor cursor =
		 * this.contentResolver.query(selectUri, null, null, null, null);
		 * 
		 * final int INDX_FK_ENERGY = cursor
		 * .getColumnIndex(ContentDescriptorObj.Equivalences.Columns.FK_ENERGY);
		 * final int INDX_FK_UNIT_IN = cursor
		 * .getColumnIndex(ContentDescriptorObj
		 * .Equivalences.Columns.FK_UNIT_IN); final int INDX_QUANTITY_OUT =
		 * cursor
		 * .getColumnIndex(ContentDescriptorObj.Equivalences.Columns.QUANTITY_OUT
		 * ); final int INDX_FK_UNIT_OUT = cursor
		 * .getColumnIndex(ContentDescriptorObj
		 * .Equivalences.Columns.FK_UNIT_OUT);
		 * 
		 * // faire un move First pour positionner le pointeur, sinon on pointe
		 * sur // null if (cursor.moveToFirst()) {
		 * 
		 * mEquivalence.setId(_id); mEquivalence.energy =
		 * createEnergyFromId(cursor .getLong(INDX_FK_ENERGY));
		 * mEquivalence.unitIn = createUnitOfMeasureObjFromId(cursor
		 * .getLong(INDX_FK_UNIT_IN)); mEquivalence.unitOut =
		 * createUnitOfMeasureObjFromId(cursor .getLong(INDX_FK_UNIT_OUT));
		 * 
		 * Log.i("Qté lue", String.valueOf(cursor.getFloat(INDX_QUANTITY_OUT)));
		 * mEquivalence.setQuantity(cursor.getFloat(INDX_QUANTITY_OUT));
		 * 
		 * } else { Log.e("AmickalFactory.createEquivalenceObjFromId",
		 * "Equivalence non trouvé"); }
		 * 
		 * cursor.close(); return mEquivalence;
		 */
	}

	/**
	 * Retourne une Activité utilisateur stockée dans la base à partir de son
	 * id.
	 * 
	 * @since 01-06-2012
	 * @param _id
	 * @return (UserActivityObj) un objet "Activité d'utilisateur"
	 **/
	public UserActivity reloadUserActivityObjFromId(long _id) {

		Uri request = ContentUris
				.withAppendedId(
						ContentDescriptorObj.TB_UserActivities.URI_SELECT_USER_ACTIVITIES,
						_id);

		Cursor cur = this.contentResolver
				.query(request, null, null, null, null);

		final int ACTIVITY_ID = cur
				.getColumnIndex(ContentDescriptorObj.TB_UserActivities.Columns.ID);
		final int ACTIVITY_TITLE = cur
				.getColumnIndex(ContentDescriptorObj.TB_UserActivities.Columns.TITLE);
		final int ACTIVITY_CLASS = cur
				.getColumnIndex(ContentDescriptorObj.TB_UserActivities.Columns.CLASS);
		final int ACTIVITY_DATE = cur
				.getColumnIndex(ContentDescriptorObj.TB_UserActivities.Columns.DATE);

		UserActivity userActivity = null;
		// faire un move First pour positionner le pointeur, sinon on pointe sur
		// null
		if (cur.moveToFirst()) {

			// on charge le mapping des UA CLASS
			UA_CLASS_CD_MAP mapping = new UA_CLASS_CD_MAP();

			// en fonction du type d'activitée, on va retourner l'objet adequat

			switch (mapping._in.get(ACTIVITY_CLASS)) {
			case LUNCH:
				userActivity = new UserActivityLunch();
				break;
			case MOVE:
				userActivity = new UserActivityMove();
				break;
			case WEIGHT:
				userActivity = new UserActivityWeight();
				((UserActivityWeight) userActivity).setWeight(new WeightObj(cur
						.getString(ACTIVITY_TITLE)));
				break;
			}

			userActivity.set_id(cur.getLong(ACTIVITY_ID));
			userActivity.setTitle(cur.getString(ACTIVITY_TITLE));

			// userActivity.setTypeByName(cur.getString(ACTIVITY_TYPE));

			userActivity.setDay(ToolBox.parseSQLDatetime(cur
					.getString(ACTIVITY_DATE)));

		} else {
			// par défaut on fait une activité lunch
			userActivity = new UserActivityLunch();
			userActivity.setTitle("UserActivity Not Found");

		}
		return userActivity;
	}

	public UserActivityItem createUserActivityItemFromId(Activity activity,
			long _id) {
		UserActivity userActivity = reloadUserActivityObjFromId(_id);

		UserActivityItem userActivityItem = null;

		// en fonction du type d'activitée, on va retourner l'objet adequat
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
	 * <h1>loadComponent</h1>
	 * <p>
	 * Cette méthode permet de recharger un composant d'une activité
	 * Utilisateur</br> le chargement s'opère en 2 phases :
	 * <ul>
	 * <li>1 - initialiser le composant
	 * <li>2 - charger ses equivalences
	 * </ul>
	 * </p>
	 * 
	 * @param _id
	 *            : Identifiant du composant à recharger
	 */
	public UserActivityComponent load_UAComponent(Long _id) {
		// On fabrique l'Uri pour le contentProvider
		// celle-ci est du style content://xxxxx.xxxxxxxx.xxxxxxx/# où le dièse
		// est l'Id à rechercher

		UserActivityComponent mUAC = new UserActivityComponent();
		Uri selectUri = ContentUris.withAppendedId(
				ContentDescriptorObj.TB_Party_rel.SEL001_PARTY_REL_BY_ID_URI,
				_id);

		// On crée un curseur pour lire la table des aliments
		Cursor cur = contentResolver.query(selectUri, null, _id.toString(),
				null, null);

		final int INDX_REL_TYP_CD = cur
				.getColumnIndex(ContentDescriptorObj.TB_Party_rel.Columns.REL_TYP_CD);
		final int INDX_NRJ_ID = cur
				.getColumnIndex(ContentDescriptorObj.TB_Party_rel.Columns.FK_PARTY_1);
		final int INDX_UNIT_ID = cur
				.getColumnIndex(ContentDescriptorObj.TB_Party_rel.Columns.FK_PARTY_2);
		final int INDX_AMOUNT = cur
				.getColumnIndex(ContentDescriptorObj.TB_Party_rel.Columns.AMOUNT);
		// faire un move First pour positionner le pointeur, sinon on pointe sur
		// null
		if (cur.moveToFirst()) {

			mUAC.setEnergySource(this.loadEnergy(cur.getLong(INDX_NRJ_ID)));
			mUAC.getQty().setAmount(cur.getLong(INDX_AMOUNT));
			mUAC.getQty().setUnity(this.load_Unity(cur.getLong(INDX_UNIT_ID));
			);
			
		
		} else {
			String message = "Composant " + String.valueOf(_id) + " non trouvé";
			Log.e("loadComponent", message);

		}

		return mUAC;
	}

} // * end-class
