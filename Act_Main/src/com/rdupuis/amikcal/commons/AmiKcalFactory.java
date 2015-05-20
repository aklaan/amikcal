package com.rdupuis.amikcal.commons;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.rdupuis.amikcal.Food.Food;
import com.rdupuis.amikcal.commons.AppConsts.NRJ_EFFECT_MAP;
import com.rdupuis.amikcal.commons.AppConsts.REL_TYP_CD_MAP;
import com.rdupuis.amikcal.commons.AppConsts.STRUCTURE_CD_MAP;
import com.rdupuis.amikcal.commons.AppConsts.UA_CLASS_CD_MAP;
import com.rdupuis.amikcal.commons.AppConsts.UNIT_CLASS_MAP;
import com.rdupuis.amikcal.commons.Relation.REL_TYP_CD;
import com.rdupuis.amikcal.data.ContentDescriptorObj;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.equivalence.Equivalence;
import com.rdupuis.amikcal.unity.Unity;
import com.rdupuis.amikcal.useractivity.UserActivity;
import com.rdupuis.amikcal.useractivity.UserActivity.UA_CLASS_CD;
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
    public EnergySource load_Energy(long _id) {

	EnergySource energy = new EnergySource();

	if (_id == AppConsts.NO_ID) {
	    return energy;
	}
	
	Uri selectUri = ContentUris.withAppendedId(ContentDescriptorObj.TB_Energies.SELECT_ONE_ENERGIES_BY_ID_URI, _id);
	Cursor cursor = this.contentResolver.query(selectUri, null, null, null, null);

	final int INDX_NRJ_NAME = cursor.getColumnIndex(ContentDescriptorObj.TB_Energies.Columns.NAME);

	final int INDX_NRJ_EFFECT = cursor.getColumnIndex(ContentDescriptorObj.TB_Energies.Columns.EFFECT);

	final int INDX_NRJ_STRUCTURE = cursor.getColumnIndex(ContentDescriptorObj.TB_Energies.Columns.STRUCTURE);

	// faire un move First pour positionner le pointeur, sinon on pointe sur
	// null
	if (cursor.moveToFirst()) {

	    // on charge le mapping des NRJ_EFFECT
	    NRJ_EFFECT_MAP map_effect = new NRJ_EFFECT_MAP();
	    energy.setEffect(map_effect._in.get(cursor.getString(INDX_NRJ_EFFECT)));

	    switch (map_effect._in.get(cursor.getString(INDX_NRJ_EFFECT))) {

	    // si l'effet de l'énergie est de "donner", alors il s'agit d'une
	    // énergie "Aliment" et donc elle possède une structure.
	    case GIVE:
		energy = new Food();
		break;
	    case BURN:
		// TODO
	    default:
	    }

	    energy.setId(_id);
	    energy.setName(cursor.getString(INDX_NRJ_NAME));

	    // on charge le mapping des NRJ_STRUCTURE
	    STRUCTURE_CD_MAP map_struct = new STRUCTURE_CD_MAP();
	    energy.setStructure(map_struct._in.get(cursor.getString(INDX_NRJ_STRUCTURE)));

	    // Charger la quantitée de référence
	    Qty qtyRef = this.load_QtyReference(energy.getId());
	    energy.setQtyReference(qtyRef);

	    // Charger les équivalences de la qty ref
	    ArrayList<Equivalence> equivalences = new ArrayList<Equivalence>();
	    equivalences = this.load_Equiv(qtyRef);
	    energy.setEquivalences(equivalences);

	} else {
	    Toast.makeText(this.mActivity, "Energy Source inconnue", Toast.LENGTH_LONG).show();
	    /*
	     * e.setCalories(0.0f); e.setProteins(0.0f); e.setGlucids(0.0f);
	     * e.setLipids(0.0f); e.setQuantityReference(0.0f); e.unit = new
	     * Unity();
	     */

	}
	cursor.close();
	return energy;

    }

    /*****************************************************************************
     * <h1>loadQtyReference(long _id)</h1>
     * <p>
     * Permet de retourner la quantité de référence d'une source d'énergie
     * </p>
     * 
     * @param energySource
     ****************************************************************************/
    public Qty load_QtyReference(long NRJ_id) {

	Qty qty = new Qty();
	
	if (NRJ_id == AppConsts.NO_ID) {
	    return qty;
	}
	
	
	// on passe par la vue View_NRJ_Qty_link
	// elle nous permet d'avoir directement des info necessaires
	// id energy =>id relation => id Qty

	Uri selectUri = ContentUris.withAppendedId(ContentDescriptorObj.View_NRJ_QtyRef.VIEW_QTYREF_BY_NRJ_ID_URI,
		NRJ_id);
	Cursor cursor = this.contentResolver.query(selectUri, null, null, null, null);

	// id de la relation QTY
	final int INDX_QTY_ID = cursor.getColumnIndex(ContentDescriptorObj.View_NRJ_QtyRef.Columns.QTY_ID);

	
	// faire un move First pour positionner le pointeur, sinon on pointe sur
	// null

	if (cursor.moveToFirst()) {

	    qty = this.load_Qty(cursor.getLong(INDX_QTY_ID));

	} else {
	    Toast.makeText(this.mActivity, "Qty référence inconnue", Toast.LENGTH_LONG).show();
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

	if (_id == AppConsts.NO_ID) {
	    return u;
	}
	
	u.setId(_id);

	Uri selectUri = ContentUris.withAppendedId(ContentDescriptorObj.TB_Units.URI_SELECT_UNIT, _id);
	Cursor cursor = this.contentResolver.query(selectUri, null, null, null, null);

	final int INDX_NAME = cursor.getColumnIndex(ContentDescriptorObj.TB_Units.Columns.LONG_NAME);
	final int INDX_SYMBOL = cursor.getColumnIndex(ContentDescriptorObj.TB_Units.Columns.SHORT_NAME);

	final int INDX_CLASS = cursor.getColumnIndex(ContentDescriptorObj.TB_Units.Columns.CLASS);

	// faire un move First pour positionner le pointeur, sinon on pointe sur
	// null
	if (cursor.moveToFirst()) {

	    u.setLongName(cursor.getString(INDX_NAME));
	    u.setShortName(cursor.getString(INDX_SYMBOL));

	    UNIT_CLASS_MAP unit_class_map = new UNIT_CLASS_MAP();
	    u.setUnityClass(unit_class_map._in.get(cursor.getString(INDX_CLASS)));

	} else {
	    Toast.makeText(this.mActivity, "Unity inconnue", Toast.LENGTH_LONG).show();
	}
	cursor.close();
	return u;

    }

    /**
     * Retourne une Activité utilisateur stockée dans la base à partir de son
     * id.
     * 
     * - récupérer l'UA - récupérer les UAC
     * 
     * 
     * @since 01-06-2012
     * @param _id
     * @return (UserActivityObj) un objet "Activité d'utilisateur"
     **/
    public UserActivity load_UserActivity(long _id) {

	UserActivity userActivity = new UserActivity();
	
	if (_id == AppConsts.NO_ID) {
	    return userActivity;
	}
	
	
	Uri request = ContentUris.withAppendedId(ContentDescriptorObj.TB_UserActivities.SELECT_USER_ACTIVITY_BY_ID_URI,
		_id);

	Cursor cur = this.contentResolver.query(request, null, null, null, null);

	final int ACTIVITY_ID = cur.getColumnIndex(ContentDescriptorObj.TB_UserActivities.Columns.ID);
	final int ACTIVITY_TITLE = cur.getColumnIndex(ContentDescriptorObj.TB_UserActivities.Columns.TITLE);
	final int ACTIVITY_CLASS = cur.getColumnIndex(ContentDescriptorObj.TB_UserActivities.Columns.CLASS);
	final int ACTIVITY_DATE = cur.getColumnIndex(ContentDescriptorObj.TB_UserActivities.Columns.DATE);

	
	// faire un move First pour positionner le pointeur, sinon on pointe sur
	// null
	if (cur.moveToFirst()) {

	    // on charge le mapping des UA CLASS
	    UA_CLASS_CD_MAP mapping = new UA_CLASS_CD_MAP();
	    UA_CLASS_CD UA_Class_cd = mapping._in.get(cur.getString(ACTIVITY_CLASS));
	    // en fonction du type d'activitée, on va retourner l'objet adequat

	    switch (UA_Class_cd) {
	    case LUNCH:
		userActivity = new UserActivityLunch();
		break;
	    case MOVE:
		userActivity = new UserActivityMove();
		break;
	    case WEIGHT:
		userActivity = new UserActivityWeight();
		((UserActivityWeight) userActivity).setWeight(new WeightObj(cur.getString(ACTIVITY_TITLE)));
		break;
	    }

	    userActivity.set_id(cur.getLong(ACTIVITY_ID));
	    userActivity.setTitle(cur.getString(ACTIVITY_TITLE));
	    userActivity.setDay(ToolBox.parseSQLDatetime(cur.getString(ACTIVITY_DATE)));

	    // recharger les UAC
	    userActivity.setUAC_List(this.load_UAC_List(userActivity));

	} else {
	    Toast.makeText(this.mActivity, "User Activity inconnue", Toast.LENGTH_LONG).show();

	}

	return userActivity;
    }

    /*****************************************************************************
     * load_UAC_List(UserActivity) rechar
     * 
     * @param UA
     * @return
     ****************************************************************************/
    public ArrayList<UserActivityComponent> load_UAC_List(UserActivity UA) {
	ArrayList<UserActivityComponent> UAC_list = new ArrayList<UserActivityComponent>();

	if (UA.get_id() == AppConsts.NO_ID) {
	    return UAC_list;
	}
	
	Uri request = ContentUris
		.withAppendedId(ContentDescriptorObj.View_UA_UAC_link.VIEW_UAC_FOR_UA_URI, UA.get_id());

	Cursor cur = this.contentResolver.query(request, null, null, null, null);

	final int UAC_ID = cur.getColumnIndex(ContentDescriptorObj.View_UA_UAC_link.Columns.UAC_ID);

	// faire un move First pour positionner le pointeur, sinon on pointe sur
	// null
	if (cur.moveToFirst()) {

	    do {
		UserActivityComponent UAC = this.load_UAC(cur.getLong(UAC_ID));
		UAC_list.add(UAC);

	    } while (cur.moveToNext());
	}
	cur.close();

	return UAC_list;

    }

    /*********************************************************************************
     * 
     * @param activity
     * @param _id
     * @return
     ********************************************************************************/
    public UserActivityItem createUserActivityItemFromId(Activity activity, long _id) {
	UserActivity userActivity = load_UserActivity(_id);
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
     */

    /**
     * Quand j'utilise cette fonction, je suis déjà sur une UA et j'ai déja été
     * recherché ses ID d'UAC
     * 
     * 
     * 
     * @param _id
     *            : Identifiant du composant à recharger
     */

    public UserActivityComponent load_UAC(Long _id) {
	// On fabrique l'Uri pour le contentProvider
	// celle-ci est du style content://xxxxx.xxxxxxxx.xxxxxxx/# où le dièse
	// est l'Id à rechercher

	// cette URI est générique. on ne sais pas quel type d'UAC on récupère
	UserActivityComponent mUAC = new UserActivityComponent();
	
	
	if (_id == AppConsts.NO_ID) {
	    return mUAC;
	}
	
	
	Uri selectUri = ContentUris.withAppendedId(ContentDescriptorObj.View_UAC_Data.VIEW_UAC_DATA_URI, _id);

	// On crée un curseur pour lire la vue
	Cursor cursor = contentResolver.query(selectUri, null, _id.toString(), null, null);

	// On récupère les index des colonnes de la vue.
	final int INDX_REL_TYP_CD = cursor.getColumnIndex(ContentDescriptorObj.View_UAC_Data.Columns.UAC_REL_TYP_CD);
	final int INDX_NRJ_ID = cursor.getColumnIndex(ContentDescriptorObj.View_UAC_Data.Columns.ENERGY_ID);
	final int INDX_QTY_ID = cursor.getColumnIndex(ContentDescriptorObj.View_UAC_Data.Columns.QTY_ID);

	// faire un move First pour positionner le curseur, sinon on pointe sur
	// null
	if (cursor.moveToFirst()) {

	    // on charge le mapping des code relations
	    REL_TYP_CD_MAP mapping = new REL_TYP_CD_MAP();

	    // en fonction du type de relation, on va retourner le composant
	    // adequat
	    switch (mapping._in.get(cursor.getString(INDX_REL_TYP_CD))) {
	    case UAC_FOOD:
		mUAC = new UAC_Food();
		mUAC.setId(_id);

		break;
	    // pour le moment je ne gère que les UAC_FOOD
	    default:

		break;
	    }

	    mUAC.getComponent().setEnergySource(this.load_Energy(cursor.getLong(INDX_NRJ_ID)));
	    mUAC.getComponent().setQty(this.load_Qty(cursor.getLong(INDX_QTY_ID)));
	    mUAC.getComponent().setEquivalences(this.load_Equiv(mUAC.getComponent().getQty()));

	} else {
	    String message = "Composant " + String.valueOf(_id) + " non trouvé";
	    Log.e("loadComponent", message);

	}
	cursor.close();
	return mUAC;
    }

    /*****************************************************************************************
     * <h1>loadQty(long _id)</h1>
     * <p>
     * Fonction de rechargement d'une quantitée à partir de sont identifiant.
     * </p>
     * 
     * @param _id
     * @return
     *****************************************************************************************/
    public Qty load_Qty(long _id) {
	Qty qty = new Qty();
	
	if (_id == AppConsts.NO_ID) {
	    return qty;
	}
	
	
	
	qty.setId(_id);

	Uri selectUri = ContentUris.withAppendedId(ContentDescriptorObj.View_Qty.VIEW_QTY_BY_ID_URI, _id);

	// On crée un curseur pour lire la table des aliments
	Cursor cursor = contentResolver.query(selectUri, null, String.valueOf(_id), null, null);

	// On récupère les index des colonnes de pa PARTY_REL qui nous
	// intéressent
	final int INDX_AMOUNT = cursor.getColumnIndex(ContentDescriptorObj.View_Qty.Columns.AMOUNT);

	final int INDX_QTY_ID = cursor.getColumnIndex(ContentDescriptorObj.View_Qty.Columns.UNITY_ID);

	// faire un move First pour positionner le curseur, sinon on pointe sur
	// null
	if (cursor.moveToFirst()) {
	    qty.setAmount(cursor.getFloat(INDX_AMOUNT));
	    qty.setUnity(this.load_Unity(cursor.getLong(INDX_QTY_ID)));
	} else {
	    String message = "Qty: " + String.valueOf(_id) + " non trouvé";
	    Log.e("loadComponent", message);

	}

	return qty;
    }

    /*****************************************************************************************
     * 
     * @param qty
     * @return
     ******************************************************************************************/
    // recharger les équivalences d'un objet
    // QUESTION ! faut-il créer des UAC_EQUIV ou bien les recalculer ?

    public ArrayList<Equivalence> load_Equiv(Qty qty) {
	ArrayList<Equivalence> equiv_list = new ArrayList<Equivalence>();
	
	if (qty.getId() == AppConsts.NO_ID) {
	    return equiv_list;
	}
	
	
	Uri request = ContentUris.withAppendedId(ContentDescriptorObj.View_qty_equiv.VIEW_ALL_QTY_EQUIV_URI,
		qty.getId());

	Cursor cur = this.contentResolver.query(request, null, null, null, null);

	final int QTY_ID = cur.getColumnIndex(ContentDescriptorObj.View_qty_equiv.Columns.QTY_ID);
	final int EQUIV_ID = cur.getColumnIndex(ContentDescriptorObj.View_qty_equiv.Columns.REL_ID);
	final int EQUIV_QTY_ID = cur.getColumnIndex(ContentDescriptorObj.View_qty_equiv.Columns.QTY_EQUIV_ID);
	// faire un move First pour positionner le pointeur, sinon on pointe sur
	// null
	if (cur.moveToFirst()) {

	    do {
		Equivalence eq = new Equivalence();
		eq.setId(cur.getLong(EQUIV_ID));
		eq.setQuantityIn(qty);
		eq.setQuantityOut(this.load_Qty(cur.getLong(EQUIV_QTY_ID)));
		equiv_list.add(eq);

	    } while (cur.moveToNext());
	}
	cur.close();

	return equiv_list;

    }

    /*****************************************************************************************
     * 
     * @param qty
     * @return
     ******************************************************************************************/
    // recharger toutes les équivalences

    public ArrayList<Equivalence> load_Equiv() {
	ArrayList<Equivalence> equiv_list = new ArrayList<Equivalence>();
	
	
	Uri request = ContentDescriptorObj.View_qty_equiv.VIEW_ALL_QTY_EQUIV_URI;

	Cursor cur = this.contentResolver.query(request, null, null, null, null);

	final int QTY_ID = cur.getColumnIndex(ContentDescriptorObj.View_qty_equiv.Columns.QTY_ID);
	final int EQUIV_ID = cur.getColumnIndex(ContentDescriptorObj.View_qty_equiv.Columns.REL_ID);
	final int EQUIV_QTY_ID = cur.getColumnIndex(ContentDescriptorObj.View_qty_equiv.Columns.QTY_EQUIV_ID);
	// faire un move First pour positionner le pointeur, sinon on pointe sur
	// null
	if (cur.moveToFirst()) {

	    do {
		Equivalence eq = new Equivalence();
		eq.setId(cur.getLong(EQUIV_ID));
		eq.setQuantityIn(this.load_Qty(cur.getLong(QTY_ID)));
		eq.setQuantityOut(this.load_Qty(cur.getLong(EQUIV_QTY_ID)));
		equiv_list.add(eq);

	    } while (cur.moveToNext());
	}
	cur.close();

	return equiv_list;

    }

    /*****************************************************************************************
     * Enregister une UA dans la database
     * 
     * mettre à jour la TB_UserActivities si l'UA possède des UAC pour chaque
     * UAC, sauver l'UAC *
     * 
     * 
     ******************************************************************************************/

    public void save(UserActivity UA) {
	ContentValues val = new ContentValues();

	// id de l'activitée
	val.put(ContentDescriptorObj.TB_UserActivities.Columns.ID,
		(UA.get_id() == AppConsts.NO_ID) ? null : UA.get_id());
	// titre
	val.put(ContentDescriptorObj.TB_UserActivities.Columns.TITLE, UA.getTitle());
	// Date
	val.put(ContentDescriptorObj.TB_UserActivities.Columns.DATE, ToolBox.getSqlDateTime(UA.getDay()));

	// class : on utilise la mapping pour transformer l'ENUM Class en Byte
	// stoké dans la Database.
	UA_CLASS_CD_MAP ua_cd_map = new UA_CLASS_CD_MAP();
	val.put(ContentDescriptorObj.TB_UserActivities.Columns.CLASS, ua_cd_map._out.get((UA.getType())));

	// date de mise à jour
	val.put(ContentDescriptorObj.TB_UserActivities.Columns.LAST_UPDATE, ToolBox.getCurrentTimestamp());

	if (UA.get_id() == AppConsts.NO_ID) {
	    Uri result = this.mActivity.getContentResolver().insert(
		    ContentDescriptorObj.TB_UserActivities.INSERT_USER_ACTIVITY_URI, val);
	    UA.set_id(Long.parseLong(result.getLastPathSegment()));
	} else {

	    Uri uriUpdate =

	    ContentUris.withAppendedId(ContentDescriptorObj.TB_UserActivities.UPDATE_USER_ACTIVITY_URI, UA.get_id());
	    this.mActivity.getContentResolver().update(uriUpdate, val, UA.get_id().toString(), null);

	}
	// si l'UA possède des UAC, on doit les sauver aussi

	if (!UA.getUAC_List().isEmpty()) {

	    for (UserActivityComponent UAC : UA.getUAC_List()) {
		this.save_UAC(UAC);
		save_UA_UAC_Relation(UA, UAC);
	    }
	}

    }

    /*****************************************************************************************
     * Sauver le composant, c'est :
     *   - Créer/mettre à jour la QTY
     *   - Créer/mettre à jour le lien energie/Qty (UAC_QTY)
     *    
    ******************************************************************/
public void save_component(Component component){
	// On sauve la Qty. ceci nous permet d'avoir une ID pour cette Qty
	// si elle n'existait pas dans la DB.
	component.setQty(save(component.getQty()));
	saveRelation(component);

    
    }
    
    
    
    /*****************************************************************************************
     * Enregister une UAC dans la database c'est  :
     * 
     * - sauver la relation entre l'UA et un composant (UA_UAC) 
     * - sauver le composant, c'est à dire :
     *         - Créer/mettre à jour le lien energie/Qty (UAC_QTY)
     *         - Créer/mettre à jour la QTY
     * 
     ******************************************************************************************/

    
    
    public void save_UAC(UserActivityComponent UAC) {

	// On sauve la Qty. ceci nous permet d'voit une ID pour cette Qty
	// si elle n'existait pas dans la DB.
	UAC.getComponent().setQty(save(UAC.getComponent().getQty()));

	ContentValues val = new ContentValues();

	// id de la relation composant
	val.put(ContentDescriptorObj.TB_Party_rel.Columns.ID, (UAC.getId() == AppConsts.NO_ID) ? null : UAC.getId());

	// classe de la realtion composant : on utilise la mapping pour
	// transformer l'ENUM Class en Byte
	// stoké dans la Database.
	REL_TYP_CD_MAP rel_typ_cd_map = new REL_TYP_CD_MAP();
	val.put(ContentDescriptorObj.TB_Party_rel.Columns.REL_TYP_CD, rel_typ_cd_map._out.get((UAC.getRelationClass())));

	// id de l'énergie
	val.put(ContentDescriptorObj.TB_Party_rel.Columns.PARTY_1, String.valueOf(UAC.getComponent().getEnergySource().getId()));

	// id de la qty
	val.put(ContentDescriptorObj.TB_Party_rel.Columns.PARTY_2, String.valueOf(UAC.getComponent().getQty().getId()));

	// date de mise à jour
	val.put(ContentDescriptorObj.TB_Party_rel.Columns.LAST_UPDATE, ToolBox.getCurrentTimestamp());

	if (UAC.getId() == AppConsts.NO_ID) {
	    Uri result = this.mActivity.getContentResolver().insert(
		    ContentDescriptorObj.TB_Party_rel.INS000_PARTY_REL_URI, val);
	    UAC.setId(Long.parseLong(result.getLastPathSegment()));
	} else {

	    Uri uriUpdate = ContentUris.withAppendedId(ContentDescriptorObj.TB_Party_rel.UP000_PARTY_REL_URI,
		    UAC.getId());

	    this.mActivity.getContentResolver().update(uriUpdate, val, String.valueOf(UAC.getId()), null);

	}

    }

    /*****************************************************************************************
     * Enregister une source d'énergie dans la database
     ******************************************************************************************/
    public void save(EnergySource nrj) {
	// On prépare les informations à mettre à jour
	ContentValues val = new ContentValues();

	val.put(ContentDescriptorObj.TB_Energies.Columns.NAME, nrj.getName());

	// Alimentation de la structure
	STRUCTURE_CD_MAP structure_map = new STRUCTURE_CD_MAP();

	val.put(ContentDescriptorObj.TB_Energies.Columns.STRUCTURE, structure_map._out.get(nrj.getStructure()));

	// Alimentation de l'effet
	NRJ_EFFECT_MAP effect_map = new NRJ_EFFECT_MAP();
	val.put(ContentDescriptorObj.TB_Energies.Columns.EFFECT, effect_map._out.get(nrj.getEffect()));

	// date de mise à jour
	val.put(ContentDescriptorObj.TB_Energies.Columns.LAST_UPDATE, ToolBox.getCurrentTimestamp());

	// Sauver l'énergie
	if (nrj.getId() == AppConsts.NO_ID) {
	    Uri result = this.mActivity.getContentResolver().insert(ContentDescriptorObj.TB_Energies.INSERT_ENERGY_URI,
		    val);
	    nrj.setId(Long.parseLong(result.getLastPathSegment()));
	} else {

	    Uri uriUpdate = ContentUris.withAppendedId(ContentDescriptorObj.TB_Energies.UPDATE_ENERGY_ID_URI,
		    nrj.getId());
	    this.mActivity.getContentResolver().update(uriUpdate, val, String.valueOf(nrj.getId()), null);
	}

	// Sauver la Qty de référence
	// si on fait un INSERT, on va récupérer un ID pour cette Qty.
	// comme JAVA ne travaille pas par référence, mais par valeur, on est
	// obligé de
	// réasigner la qty pour avoir la nouvelle qty à jour avec l'id
	nrj.setQtyReference(save(nrj.getQtyReference()));

	// Sauver le lien NRJ_Qty de référence
	save_NRj_Qtyref_Relation(nrj, nrj.getQtyReference());

	
	// Sauver les équivalences

	if (!nrj.getEquivalences().isEmpty()) {

	    for (Equivalence equiv : nrj.getEquivalences()) {
		this.save(equiv);
		
	    }
	}
	
    }

    /*****************************************************************************************
     * Enregister une unitée dans la databse
     ******************************************************************************************/
    public void save(Unity unity) {
	// On prépare les informations à mettre à jour
	ContentValues val = new ContentValues();

	// LongName
	val.put(ContentDescriptorObj.TB_Units.Columns.LONG_NAME, unity.getLongName());

	// ShortName
	val.put(ContentDescriptorObj.TB_Units.Columns.SHORT_NAME, unity.getShortName());

	// Alimentation de la classe d'unitée
	UNIT_CLASS_MAP unit_class_map = new UNIT_CLASS_MAP();
	val.put(ContentDescriptorObj.TB_Units.Columns.CLASS, unit_class_map._out.get(unity.getUnityClass()));

	// date de mise à jour
	val.put(ContentDescriptorObj.TB_Units.Columns.LAST_UPDATE, ToolBox.getCurrentTimestamp());

	// Sauver l'unitée
	if (unity.getId() == AppConsts.NO_ID) {
	    Uri result = this.mActivity.getContentResolver().insert(ContentDescriptorObj.TB_Units.URI_INSERT_UNIT, val);
	    unity.setId(Long.parseLong(result.getLastPathSegment()));
	} else {
	    this.mActivity.getContentResolver().update(ContentDescriptorObj.TB_Units.URI_UPDATE_UNIT, val,
		    String.valueOf(unity.getId()), null);
	}

    }

    
    

    /*****************************************************************************************
     * Enregister une équivalence dans la database
     ******************************************************************************************/
    public Equivalence save(Equivalence equiv) {
		
    	//Une equivalence est une relation entre 2 QTY.
    	//sauver une équivalence, c'est donc sauver les 2 QTY puis le liens les unisants
    
    	//si les Qty n'ont jamais été enregistrées, leur ID est vide (NO_ID)
    	//Au retour de la sauvegarde, il sera alimenté.
    	equiv.setQuantityIn(save(equiv.getQuantityIn()));
    	equiv.setQuantityOut(save(equiv.getQuantityOut()));
    	
    	equiv = (Equivalence) saveRelation(equiv);
    	    	
    	
    	return equiv;
    
       
    
    }
    
    
    /*****************************************************************************************
     * Enregister une Qty dans la database
     ******************************************************************************************/
    public Qty save(Qty qty) {

	// qty est une sorte de relation
	// on réutilise le save des relations
	// save n'est pas une méthode de "relation", c'est une outil de la
	// factory
	// on ne peux donc pas jouer surle lien de parenté entre relation est
	// qty
	qty = (Qty) saveRelation(qty);

	// java ne sait pas passer des variables par référence donc si on veut
	// mettre à jour la qty
	// en entrée, il faut retourner une qty modifiée.
	return qty;

    }

    /*****************************************************************************************
     * Enregister la relation NRJ / Qty de référence dans la database
     ******************************************************************************************/
    public void save_NRj_Qtyref_Relation(EnergySource nrj, Qty qtyref) {

	Relation relation = new Relation();

	// rechercher si la relation existe déjà pour récupérer son ID
	Uri request = ContentDescriptorObj.View_NRJ_QtyRef.VIEW_NRJ_QTYREF_URI.buildUpon()
		.appendPath(nrj.getId() + "x" + qtyref.getId()).build();
	Cursor cur = this.contentResolver.query(request, null, null, null, null);

	final int RELATION_ID = cur.getColumnIndex(ContentDescriptorObj.View_NRJ_QtyRef.Columns.REL_NRJ_QTY_ID);

	// faire un move First pour positionner le pointeur, sinon on pointe sur
	// null
	// Si la relation existe on va passer dans le if...sinon
	// on passe directement au close curseur
	if (cur.moveToFirst()) {

	    relation.setId(cur.getLong(RELATION_ID));
	}
	cur.close();

	// ---------------------------------
	relation.setParty1(String.valueOf(nrj.getId()));
	relation.setParty2(String.valueOf(qtyref.getId()));

	switch (qtyref.getUnity().getUnityClass()) {
	case INTERNATIONAL:
	    relation.setRelationClass(REL_TYP_CD.NRJ_REF_INTRNL);
	    break;
	case CUSTOM:
	    relation.setRelationClass(REL_TYP_CD.CSTM_NRJ_REF);
	    break;
	default:
	    Toast.makeText(this.mActivity, "Classe de l'unitée incorecte pour une référence", Toast.LENGTH_LONG).show();
	    relation.setRelationClass(REL_TYP_CD.UNDEFINED);
	}

	// en cas d'insert, la fonction save va retourner un ID
	// je n'ai a prioris pas besoin de récupérer cet id
	// c'est transparent pour l'utilisateur
	saveRelation(relation);

    }

    /*****************************************************************************************
     * Enregister la relation NRJ / Qty de référence dans la database
     ******************************************************************************************/
    public void save_UA_UAC_Relation(UserActivity UA, UserActivityComponent UAC) {

	Relation relation = new Relation();

	// rechercher si la relation existe déjà pour récupérer son ID
	Uri request = ContentDescriptorObj.View_UA_UAC_link.SEARCH_RELATION_URI.buildUpon()
		.appendPath(String.valueOf(UA.get_id()) + "x" + String.valueOf(UAC.getId())).build();
	Cursor cur = this.contentResolver.query(request, null, null, null, null);

	final int RELATION_ID = cur.getColumnIndex(ContentDescriptorObj.View_UA_UAC_link.Columns.REL_ID);

	// faire un move First pour positionner le pointeur, sinon on pointe sur
	// null
	// Si la relation existe on va passer dans le if...sinon
	// on passe directement au close curseur
	if (cur.moveToFirst()) {

	    relation.setId(cur.getLong(RELATION_ID));
	}
	cur.close();

	// ---------------------------------
	relation.setParty1(String.valueOf(UA.get_id()));
	relation.setParty2(String.valueOf(UAC.getId()));
	relation.setRel_typ_cd(REL_TYP_CD.UA_UAC);

	// en cas d'insert, la fonction save va retourner un ID
	// je n'ai a prioris pas besoin de récupérer cet id
	// c'est transparent pour l'utilisateur
	saveRelation(relation);

    }

    /*****************************************************************************************
     * Enregister une relation dans la database
     ******************************************************************************************/
    public I_Relation saveRelation(I_Relation relation) {

	// On prépare les informations à mettre à jour
	ContentValues val = new ContentValues();

	// Rel_typ_cd
	REL_TYP_CD_MAP rel_typ_cd_map = new REL_TYP_CD_MAP();
	val.put(ContentDescriptorObj.TB_Party_rel.Columns.REL_TYP_CD, rel_typ_cd_map._out.get(relation.getRelationClass()));

	// party 1
	val.put(ContentDescriptorObj.TB_Party_rel.Columns.PARTY_1, relation.getParty1());

	// Id unity
	val.put(ContentDescriptorObj.TB_Party_rel.Columns.PARTY_2, relation.getParty2());

	// date de mise à jour
	val.put(ContentDescriptorObj.TB_Party_rel.Columns.LAST_UPDATE, ToolBox.getCurrentTimestamp());

	// Sauver l'unitée
	if (relation.getId() == AppConsts.NO_ID) {
	    Uri result = this.mActivity.getContentResolver().insert(
		    ContentDescriptorObj.TB_Party_rel.INS000_PARTY_REL_URI, val);
	    relation.setId(Long.parseLong(result.getLastPathSegment()));
	} else {

	    Uri uriUpdate = ContentUris.withAppendedId(ContentDescriptorObj.TB_Party_rel.UP000_PARTY_REL_URI,
		    relation.getId());
	    this.mActivity.getContentResolver().update(uriUpdate, val, String.valueOf(relation.getId()), null);
	}

	// il faut récupérer l'id de lenregistrement que l'on vient de créer
	return relation;
    }

} // * end-class
