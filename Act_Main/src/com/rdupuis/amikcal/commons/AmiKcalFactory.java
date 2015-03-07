package com.rdupuis.amikcal.commons;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.rdupuis.amikcal.commons.AppConsts.NRJ_EFFECT_MAP;
import com.rdupuis.amikcal.commons.AppConsts.REL_TYP_CD_MAP;
import com.rdupuis.amikcal.commons.AppConsts.UA_CLASS_CD_MAP;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.energy.EnergySource.NRJ_EFFECT;
import com.rdupuis.amikcal.unity.Unity;
import com.rdupuis.amikcal.useractivity.UserActivity;
import com.rdupuis.amikcal.useractivity.UserActivityItem;
import com.rdupuis.amikcal.useractivity.UserActivityLunch;
import com.rdupuis.amikcal.useractivity.UserActivityLunchItem;
import com.rdupuis.amikcal.useractivity.UserActivityMove;
import com.rdupuis.amikcal.useractivity.UserActivityMoveItem;
import com.rdupuis.amikcal.useractivity.UserActivityWeight;
import com.rdupuis.amikcal.useractivity.UserActivityWeightItem;
import com.rdupuis.amikcal.useractivitycomponent.UAC_Food;
import com.rdupuis.amikcal.useractivitycomponent.UserActivityComponent;

public final class AmiKcalFactory {

	private ContentResolver contentResolver;
	private Activity mActivity;

	public AmiKcalFactory(Activity activity) {
		this.mActivity = activity;
		this.contentResolver = activity.getContentResolver();

	}

	/*********************************************************************************
	 * <h1>loadEnergy()</h1>
	 * 
	 * Retourne une énergie stockée dans la Database à partir de son id.
	 * 
	 * @since 01-06-2012
	 * @param _id
	 *            - Identifiant
	 * @return (Energy) un objet "énergie"
	 ********************************************************************************/
	public EnergySource loadEnergy(long _id) {

		EnergySource energy = new EnergySource();

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

		final int INDX_NRJ_QTY_REF_ID = cursor
				.getColumnIndex(ContentDescriptorObj.TB_Energies.Columns.STRUCTURE);

		
		// faire un move First pour positionner le pointeur, sinon on pointe sur
		// null
		if (cursor.moveToFirst()) {

			// on charge le mapping des NRJ_EFFECT
			NRJ_EFFECT_MAP mapping = new NRJ_EFFECT_MAP();

			energy.setId(_id);
			energy.setName(cursor.getString(INDX_NRJ_NAME));
			energy.setEffect(mapping._in.get(cursor.getInt(INDX_NRJ_EFFECT)));

			// charger la quantitée de référence
			Qty qtyRef = this.loadQtyReference(energy);
			energy.setQtyReference(qtyRef);

			// charger les équivalences de la qty ref
			ArrayList<Qty> equivalences = new ArrayList<Qty>();
			equivalences = this.loadEquiv(qtyRef);
			energy.setEquivalences(equivalences);

		} else {
			Toast.makeText(this.mActivity, "Energy Source inconnue",
					Toast.LENGTH_LONG).show();
			/*
			 * e.setCalories(0.0f); e.setProteins(0.0f); e.setGlucids(0.0f);
			 * e.setLipids(0.0f); e.setQuantityReference(0.0f); e.unit = new
			 * Unity();
			 */
		}

		cursor.close();
		return energy;

	}

	/**
	 * <h1>loadQtyReference(EnergySource energySource)</h1>
	 * <p>
	 * Permet de charger la quantité de référence d'une source d'énergie
	 * </p>
	 * 
	 * @param energySource
	 */
	public Qty loadQtyReference(EnergySource nrj) {

		//on passe par la vue VIEW_REL_NRJ_QTY_REF
		//elle nous permet d'avoir directement des info necessaires
		// id energy =>id relation => id relation Qty;amount;id unity

		
		Uri selectUri = ContentUris.withAppendedId(
				ContentDescriptorObj.View_Rel_NRJ_QtyRef.VIEW_BY_NRJ_ID_URI,
				nrj.getId());
		Cursor cursor = this.contentResolver.query(selectUri, null, null, null,
				null);

		// id de la relation QTY
		final int INDX_QTY_ID = cursor
				.getColumnIndex(ContentDescriptorObj.View_Rel_NRJ_QtyRef.Columns.REL_QTY_ID);

		// Id de l'unité
		final int INDX_UNIT_ID = cursor
				.getColumnIndex(ContentDescriptorObj.View_Rel_NRJ_QtyRef.Columns.QTY_UNITY_ID);

		// Montant de la quantitée
		final int INDX_AMOUNT = cursor
				.getColumnIndex(ContentDescriptorObj.View_Rel_NRJ_QtyRef.Columns.QTY_AMOUNT);

		
		Qty qty = new Qty();

		// faire un move First pour positionner le pointeur, sinon on pointe sur
		// null
		
		if (cursor.moveToFirst()) {

			qty.rel_id = cursor.getLong(INDX_QTY_ID);
			qty.setAmount(cursor.getFloat(INDX_AMOUNT));
			qty.setUnity(this.load_Unity(cursor.getLong(INDX_UNIT_ID)));

		} else {
			Toast.makeText(this.mActivity, "Qty référence inconnue",
					Toast.LENGTH_LONG).show();
		}

		cursor.close();

		return qty;
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
			Toast.makeText(this.mActivity, "Unity inconnue", Toast.LENGTH_LONG)
					.show();
		}
		cursor.close();
		return u;

	}

	/**
	 * Retourne une Activité utilisateur stockée dans la base à partir de son
	 * id.
	 * 
	 * @since 01-06-2012
	 * @param _id
	 * @return (UserActivityObj) un objet "Activité d'utilisateur"
	 **/
	public UserActivity loadUserActivity(long _id) {

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
			Toast.makeText(this.mActivity, "User Acivity inconnue",
					Toast.LENGTH_LONG).show();

		}
		return userActivity;
	}

	public UserActivityItem createUserActivityItemFromId(Activity activity,
			long _id) {
		UserActivity userActivity = loadUserActivity(_id);
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
	 * 
	 * notes on prend 'id d'une UA UA.Load() <arrayList of UAC> UA.get_UAC()
	 * 
	 * <arrayList of QTY> UA.get_UAC_Equiv()
	 * 
	 * on va rechercher les liens US_UAC
	 * 
	 * UA.get_AUC va utiliser la factory pour créer des objets UAC a partir de
	 * la DATABASE il faut récupérer la liste des ID UAC de l'UA pour pouvoir
	 * recreer les UAC
	 * 
	 * une fonction : récupérer la liste des ID UAC en relation avec une UA
	 * 
	 * on peu aussi passer par une vue :
	 * 
	 * select UA._id ,UAC._id ,EQUIV._id from party_rel as UA inner join
	 * party_rel as UAC on UA.rel_typ_cd = "UA_UAC" and UA.party_2 = UAC.party_1
	 * 
	 * * inner join party_rel as EQUIV on UAC.rel_typ_cd = "UAC_EQV" and
	 * UA.party_1 = UAC.party_1
	 * 
	 * 
	 * le chargement s'opère en 2 phases :
	 * <ul>
	 * <li>1 - initialiser le composant
	 * <li>2 - charger ses equivalences
	 * </ul>
	 * </p>
	 * 
	 * 
	 * Quand j'utilise cette fonction, je suis déjà sur une UA et j'ai déja été
	 * cherché ses ID d'UAC
	 * 
	 * @param _id
	 *            : Identifiant du composant à recharger
	 */
	public UserActivityComponent loadUAC(Long _id) {
		// On fabrique l'Uri pour le contentProvider
		// celle-ci est du style content://xxxxx.xxxxxxxx.xxxxxxx/# où le dièse
		// est l'Id à rechercher

		// cette URI est générique. on ne sais pas quel type d'UAC on récupère
		UserActivityComponent mUAC = new UserActivityComponent();
		Uri selectUri = ContentUris.withAppendedId(
				ContentDescriptorObj.View_UAC_Qty.VIEW_QTY_FOR_UAC_URI,
				_id);

		// On crée un curseur pour lire la table des aliments
		Cursor cursor = contentResolver.query(selectUri, null, _id.toString(),
				null, null);

		// On récupère les index des colonnes de pa PARTY_REL qui nous
		// intéressent
		final int INDX_REL_TYP_CD = cursor
				.getColumnIndex(ContentDescriptorObj.View_UAC_Qty.Columns.UAC_REL_TYP_CD);
		final int INDX_NRJ_ID = cursor
				.getColumnIndex(ContentDescriptorObj.View_UAC_Qty.Columns.ENERGY_ID);
		final int INDX_QTY_ID = cursor
				.getColumnIndex(ContentDescriptorObj.View_UAC_Qty.Columns.QTY_ID);
		

		// faire un move First pour positionner le curseur, sinon on pointe sur
		// null
		if (cursor.moveToFirst()) {

			// on charge le mapping des code relations
			REL_TYP_CD_MAP mapping = new REL_TYP_CD_MAP();

			// en fonction du type de relation, on va retourner le composant
			// adequat
			switch (mapping._in.get(INDX_REL_TYP_CD)) {
			case UAC_FOOD:
				mUAC = new UAC_Food();
				mUAC.setId(_id);

				break;
			// pour le moment je ne gère que les UAC_FOOD
			default:

				break;
			}

			mUAC.setEnergySource(this.loadEnergy(cursor.getLong(INDX_NRJ_ID)));
			mUAC.setQty(this.loadQty(cursor.getLong(INDX_QTY_ID)));
			mUAC.setEquivalences(this.loadEquiv(mUAC.getQty()));

		} else {
			String message = "Composant " + String.valueOf(_id) + " non trouvé";
			Log.e("loadComponent", message);

		}

		return mUAC;
	}

	/**
	 * <h1>loadQty(long _id)</h1>
	 * <p> Fonction de rechargement d'une quantitée à partir de sont identifiant.
	 * </p>
	 * @param _id
	 * @return
	 */
	public Qty loadQty(long _id){
	Qty qty = new Qty();
		return qty;
	}
	
	
	// recharger les équivalences d'un objet
	// QUESTION ! faut-il créer des UAC_EQUIV ou bien les recalculer ?
	
	public ArrayList<Qty> loadEquiv(Qty qty) {
		return null;

	}
} // * end-class
