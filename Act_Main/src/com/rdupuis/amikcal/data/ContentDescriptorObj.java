package com.rdupuis.amikcal.data;

import java.util.HashMap;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * <b>Descripteur de contenu.</b>
 * 
 * @author Rodolphe Dupuis
 * @version 0.1
 */
public final class ContentDescriptorObj {
    public static final String AUTHORITY = "com.rdupuis.amikcal.AmikcalContentProvider";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final UriMatcher URI_MATCHER = buildUriMatcher();

    public enum SQL_ORDER {
	SELECT, INSERT, UPDATE, DELETE
    }

    public static final String SELECT = "select";
    public static final String INSERT = "insert";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";

    /***********************************************************************************
	 * 
	 **********************************************************************************/

    private static int getToken(int idDBObject, SQL_ORDER order, int seqNum) {
	int orderCode = 0;
	switch (order) {
	case SELECT:
	    orderCode = 1;
	    break;
	case INSERT:
	    orderCode = 2;
	    break;
	case UPDATE:
	    orderCode = 3;
	    break;
	case DELETE:
	    orderCode = 4;

	}

	return (idDBObject * 100000) + (orderCode * 1000) + seqNum;

    }

    /***********************************************************************************
     * 
     * @return UriMatcher
     **********************************************************************************/
    public static UriMatcher buildUriMatcher() {
	final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
	final String authority = AUTHORITY;

	// Liste des Match pour la table PARTY_REL
	// -------------------------------------------------------------------

	matcher.addURI(authority, TB_Party_rel.S00_PARTY_REL_PATH, TB_Party_rel.S00_PARTY_REL_TOKEN);
	matcher.addURI(authority, TB_Party_rel.S01_PARTY_REL_BY_ID_PATH, TB_Party_rel.S01_PARTY_REL_BY_ID_TOKEN);
	matcher.addURI(authority, TB_Party_rel.S02_ALL_UAC_FOR_UA_PATH, TB_Party_rel.S02_ALL_UAC_FOR_UA_TOKEN);
	matcher.addURI(authority, TB_Party_rel.S03_ALL_EQUIV_FOR_UAC_PATH,
		TB_Party_rel.S03_ALL_EQUIV_FOR_UAC_TOKEN);

	matcher.addURI(authority, TB_Party_rel.S04_QTY_REF_INTER_FOR_NRJ_PATH,
		TB_Party_rel.S04_QTY_REF_INTER_FOR_NRJ_TOKEN);

	matcher.addURI(authority, TB_Party_rel.S05_EQUIV_REF_INTER_FOR_NRJ_PATH,
		TB_Party_rel.S05_EQUIV_REF_INTER_FOR_NRJ_TOKEN);

	matcher.addURI(authority, TB_Party_rel.INS000_PARTY_REL_PATH, TB_Party_rel.INS000_PARTY_REL_TOKEN);
	matcher.addURI(authority, TB_Party_rel.UPD000_PARTY_REL_PATH, TB_Party_rel.UPD000_PARTY_REL_TOKEN);

	// ----------------------------------------------------------------
        // Match pour la table des untitées
	// ----------------------------------------------------------------
        
	// Match pour le select sur la table des unitées
	matcher.addURI(authority, TB_Units.SELECT_ALL_UNITS, TB_Units.SELECT_UNITS_TOKEN);
	matcher.addURI(authority, TB_Units.SELECT_UNITY, TB_Units.SELECT_UNITY_TOKEN);

	// Match pour l'insert d'une unitée
	matcher.addURI(authority, TB_Units.INSERT_UNIT, TB_Units.INSERT_UNIT_TOKEN);
	matcher.addURI(authority, TB_Units.UPDATE_UNIT, TB_Units.UPDATE_UNIT_TOKEN);

	// Match pour la vue de selection des liens UA_UAC
	// ----------------------------------------------------------------
	matcher.addURI(authority, View_UA_UAC_link.VIEW_UAC_FOR_UA_PATH, View_UA_UAC_link.VIEW_UAC_FOR_UA_TOKEN);

	// Match pour la vue de selection des liens UAC<->Qty
	// ----------------------------------------------------------------
	matcher.addURI(authority, View_UAC_Data.VIEW_UAC_DATA_PATH, View_UAC_Data.VIEW_UAC_DATA_TOKEN);

	// Match pour la vue de selection des liens NRJ<->QtyRef
	// ----------------------------------------------------------------
	matcher.addURI(authority, View_NRJ_QtyRef.VIEW_NRJ_QTYREF_PATH, View_UAC_Data.VIEW_UAC_DATA_TOKEN);

	// Match(s) pour la table user_activities 
	// ----------------------------------------------------------------
	matcher.addURI(authority, TB_UserActivities.S00_USER_ACTIVITIES_PATH,
		TB_UserActivities.S00_USER_ACTIVITIES_TOKEN);

	matcher.addURI(authority, TB_UserActivities.SELECT_USER_ACTIVITY_BY_ID_PATH,
		TB_UserActivities.SELECT_USER_ACTIVITY_BY_ID_TOKEN);

	matcher.addURI(authority, TB_UserActivities.SELECT_USER_ACTIVITIES_BY_DATE_PATH,
		TB_UserActivities.SELECT_USER_ACTIVITIES_BY_DATE_TOKEN);

	matcher.addURI(authority, TB_UserActivities.SELECT_USER_ACTIVITIES_BY_TYPE_PATH,
		TB_UserActivities.SELECT_USER_ACTIVITIES_BY_TYPE_TOKEN);

	matcher.addURI(authority, TB_UserActivities.INSERT_USER_ACTIVITY_PATH,
		TB_UserActivities.INSERT_USER_ACTIVITY_TOKEN);

	matcher.addURI(authority, TB_UserActivities.UPDATE_USER_ACTIVITY_PATH,
		TB_UserActivities.UPDATE_USER_ACTIVITY_TOKEN);

	matcher.addURI(authority, TB_UserActivities.DELETE_USER_ACTIVITY_PATH,
		TB_UserActivities.DELETE_USER_ACTIVITY_TOKEN);

	matcher.addURI(authority, CustomQuery.SUM_ENERGY_OF_DAY, CustomQuery.SUM_ENERGY_OF_DAY_TOKEN);
	matcher.addURI(authority, CustomQuery.DB_VERSION_PATH, CustomQuery.DB_VERSION_TOKEN);
	matcher.addURI(authority, CustomQuery.LAST_WEIGHT_FROM, CustomQuery.LAST_WEIGHT_FROM_TOKEN);
	matcher.addURI(authority, CustomQuery.USED_UNITS_FOR_ENERGY, CustomQuery.USED_UNITS_FOR_ENERGY_TOKEN);

	return matcher;
    }

    public static final class CustomQuery {

	private static final int CSTM_QUERY = 0;
	public static final int SUM_ENERGY_OF_DAY_TOKEN = getToken(CSTM_QUERY, SQL_ORDER.SELECT, +0);
	public static final String SUM_ENERGY_OF_DAY = "sumEnergyOfDay/*";
	public static final Uri URI_SUM_ENERGY_OF_DAY = BASE_URI.buildUpon().appendPath("sumEnergyOfDay").build();

	public static final String DB_VERSION_PATH = "dbVersion";
	public static final int DB_VERSION_TOKEN = getToken(CSTM_QUERY, SQL_ORDER.SELECT, +1);
	public static final Uri URI_DB_VERSION = BASE_URI.buildUpon().appendPath(DB_VERSION_PATH).build();

	public static final int LAST_WEIGHT_FROM_TOKEN = getToken(CSTM_QUERY, SQL_ORDER.SELECT, +2);
	public static final String LAST_WEIGHT_FROM = "lastWeightFrom/*";
	public static final Uri URI_LAST_WEIGHT_FROM = BASE_URI.buildUpon().appendPath("lastWeightFrom").build();

	public static final int USED_UNITS_FOR_ENERGY_TOKEN = getToken(CSTM_QUERY, SQL_ORDER.SELECT, +3);
	public static final String USED_UNITS_FOR_ENERGY = "usedUnitsForEnergy/*";
	public static final Uri URI_USED_UNITS_FOR_ENERGY = BASE_URI.buildUpon().appendPath("usedUnitsForEnergy")
		.build();

    }

    /**************************************************************************
     * <h1>PARTY_REL :</h1>
     * <p>
     * Table des relations entre les entitées
     * </p>
     * 
     * @author R.DUPUIS
     * 
     **********************************************************************************/
    public static final class TB_Party_rel implements BaseColumns {

	// Info concernant la table
	public static final String NAME = "party_rel";
	private static final int TABLE_ID = 1;

	/**
	 * <p>
	 * UAC_FOOD = relation de type composant d'un repas (100 g de pomme)
	 * </br> UAC_MOVE = relation de type composant d'une activité physique
	 * (10 min de marche) </br>
	 * 
	 * UAC_WEIGHT = relation de type composant d'une pesée (45 kg) </br>
	 * 
	 * UA_TO_UAC = relation entre une UA et ses UAC </br>
	 * 
	 * NRJ_REF_INTER = relation entre la source d'énergie et sa quantité /
	 * unité de référence internationale (ex:100 g de pomme) </br>
	 * 
	 * NRJ_REF_EQUIV = équivalence par rapport à la référence (ex : 100 g de
	 * pomme = 56 Kcal) </br>
	 * 
	 * CSTM_NRJ_REF = unité de référence personalisé pour une source (ex:
	 * Moyenne -> Pomme) </br>
	 * 
	 * UNIT_EQUIV = relation d'équivalence entre deux unitée de la même
	 * famille (ex gramme = 0,OOO1 kilogramme) </br>
	 * 
	 * UNIT_INTER_LINK = passerelle de convertion entre 2 systèmes de mesure
	 * (ex : 1 kg = x Livres)
	 * 
	 * </p>
	 */

	// ----------------------------------------------------------------------------
	// Path pour l'Uri de séléction de toute la table
	public static final String S00_PARTY_REL_PATH = "SELECT_party_rel";
	public static final int S00_PARTY_REL_TOKEN = getToken(TABLE_ID, SQL_ORDER.SELECT, +0);
	public static final Uri S00_PARTY_REL_URI = BASE_URI.buildUpon().appendPath(S00_PARTY_REL_PATH).build();

	// ----------------------------------------------------------------------------
	// Path pour l'Uri de séléction d'un enregistrement
	// le dièse est un alias pour une chaine de caractère --> un fragment.
	// de ce fait, si on a une URL du style ....../123 ou ..../Bonjour, on a
	// la même signature /#
	public static final String S01_PARTY_REL_BY_ID_PATH = "SELECT_party_rel/#";
	public static final int S01_PARTY_REL_BY_ID_TOKEN = getToken(TABLE_ID, SQL_ORDER.SELECT, +1);
	public static final Uri S01_PARTY_REL_BY_ID_URI = BASE_URI.buildUpon()
		.appendPath(S01_PARTY_REL_BY_ID_PATH).build();

	// ----------------------------------------------------------------------------
	// Path de séléction de tous les UAC d'une UA
	// a partir de id d'une UA, on, récupère les UAC food / move /weight
	public static final String S02_ALL_UAC_FOR_UA_PATH = "SELECT_all_uac_for_ua/#";
	public static final int S02_ALL_UAC_FOR_UA_TOKEN = getToken(TABLE_ID, SQL_ORDER.SELECT, +2);
	public static final Uri S02_ALL_UAC_FOR_UA_URI = BASE_URI.buildUpon().appendPath(S02_ALL_UAC_FOR_UA_PATH)
		.build();

	// ----------------------------------------------------------------------------
	// Path de séléction de toutes les équivalences d'une UAC
	// a partir de l'id d'une UAC on a trouver des équivalence en Kcal / lip
	// / glu...etc
	public static final String S03_ALL_EQUIV_FOR_UAC_PATH = "SELECT_all_equiv_for_uac/#";
	public static final int S03_ALL_EQUIV_FOR_UAC_TOKEN = getToken(TABLE_ID, SQL_ORDER.SELECT, +4);
	public static final Uri S03_ALL_EQUIV_FOR_UAC_URI = BASE_URI.buildUpon()
		.appendPath(S03_ALL_EQUIV_FOR_UAC_PATH).build();

	// ----------------------------------------------------------------------------
	// Path de séléction de la Qty de référence d'une source d'énergie
	// a partir de l'id d'une NRJ, on a trouver la QTY de référence (ex: 100
	// grammes)
	public static final String S04_QTY_REF_INTER_FOR_NRJ_PATH = "SELECT_qty_ref_inter_for_nrj/#";
	public static final int S04_QTY_REF_INTER_FOR_NRJ_TOKEN = getToken(TABLE_ID, SQL_ORDER.SELECT, +5);
	public static final Uri S04_QTY_REF_INTER_FOR_NRJ_URI = BASE_URI.buildUpon()
		.appendPath(S04_QTY_REF_INTER_FOR_NRJ_PATH).build();

	// ----------------------------------------------------------------------------
	// Path de séléction de des Qty equivalences de référence pour une
	// source d'énergie
	// a partir de l'id d'une QTY de référence d'énergie, on a trouver les
	// equivalences
	// ex : Pomme 100 g = 56 Kcal ; 78 GLU...etc
	public static final String S05_EQUIV_REF_INTER_FOR_NRJ_PATH = "SELECT_equiv_ref_inter_for_nrj/#";
	public static final int S05_EQUIV_REF_INTER_FOR_NRJ_TOKEN = getToken(TABLE_ID, SQL_ORDER.SELECT, +6);
	public static final Uri S05_EQUIV_REF_INTER_FOR_NRJ_URI = BASE_URI.buildUpon()
		.appendPath(S05_EQUIV_REF_INTER_FOR_NRJ_PATH).build();

	// Path pour l'uri d'insertion et son token
	public static final String INS000_PARTY_REL_PATH = "insert_" + NAME;
	public static final int INS000_PARTY_REL_TOKEN = getToken(TABLE_ID, SQL_ORDER.INSERT, +0);
	public static final Uri INS000_PARTY_REL_URI = BASE_URI.buildUpon().appendPath(INS000_PARTY_REL_PATH).build();

	// Path pour l'uri de mise à jour et son token
	public static final String UPD000_PARTY_REL_PATH = "update_" + NAME;
	public static final int UPD000_PARTY_REL_TOKEN = getToken(TABLE_ID, SQL_ORDER.UPDATE, +0);
	public static final Uri UP000_PARTY_REL_URI = BASE_URI.buildUpon().appendPath(UPD000_PARTY_REL_PATH).build();

	// Déclaration du tye mime
	public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
	public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.party_rel";

	public static final class PredefinedValues {
	    public static final class RelationsCodes {
		public static final byte UA_UAC = 0x00;
		public static final byte UAC_FOOD = 0x01;
		public static final byte UAC_MOVE = 0x02;
		public static final byte UAC_WEIGHT = 0x03;
		public static final byte UAC_EQUIV = 0x04;
		public static final byte NRJ_REF_INTRNL = 0x05;
		public static final byte NRJ_REF_EQUIV = 0x06;
		public static final byte CSTM_NRJ_REF = 0x07;
		public static final byte UNIT_EQUIV = 0x08;
		public static final byte UNIT_INTER_LINK = 0x09;
		public static final byte QTY = 0x10;

	    }
	}

	// Info concernant les colones de la table
	public static final class Columns {
	    public static final String ID = BaseColumns._ID; // on utilise la
	    // convention de
	    // format de
	    // colonne ID
	    // indiqué par
	    // BaseColumns
	    public static final String REL_TYP_CD = "rel_typ_cd";
	    public static final String PARTY_1 = "party_1";
	    public static final String PARTY_2 = "party_2";

	    public static final String LAST_UPDATE = "last_update";

	}

    }

    /***********************************************************************************
     * Table des aliments et leurs référence énergétique : Energies
     * 
     * @author R.DUPUIS
     * 
     **********************************************************************************/
    public static final class TB_Energies implements BaseColumns {

	// Info concernant la table
	public static final String NAME = "energies";
	public static final int TABLE_ID = 2;

	// Path pour l'Uri de séléction de toute la table
	public static final String SELECT_ENERGIES = "energies";
	// id table + code Select + n° de select

	public static final int SELECT_ENERGIES_TOKEN = getToken(TABLE_ID, SQL_ORDER.SELECT, +0);

	// Path pour l'Uri de séléction d'un enregistrement via son ID
	public static final String SELECT_ENERGY_BY_ID = "energies/#";
	public static final int SELECT_ENERGY_BY_ID_TOKEN = getToken(TABLE_ID, SQL_ORDER.SELECT, +1);

	// Path pour l'Uri de séléction d'un enregistrement dont le nom resemble
	public static final String ENERGIES_LIKE = "energieslike";
	public static final String SELECT_ENERGIES_LIKE = ENERGIES_LIKE + "/*";
	public static final int SELECT_ENERGIES_LIKE_TOKEN = getToken(TABLE_ID, SQL_ORDER.SELECT, +2);

	// Path pour l'Uri pour l'INSERT d'un enregistrement
	public static final String INSERT_ENERGY = "insert_energy";
	// id table + code INSERT + n° de select
	public static final int INSERT_ENERGY_TOKEN = getToken(TABLE_ID, SQL_ORDER.INSERT, +0);

	// Path pour l'Uri pour l'UPDATE d'un enregistrement
	public static final String UPDATE_ENERGY = "update_energy";
	// id table + code Select + n° de select
	public static final int UPDATE_ENERGY_TOKEN = getToken(TABLE_ID, SQL_ORDER.UPDATE, +0);

	public static final Uri URI_CONTENT_ENERGIES = BASE_URI.buildUpon().appendPath(SELECT_ENERGIES).build();

	public static final Uri URI_ENERGIES_LIKE = BASE_URI.buildUpon().appendPath(ENERGIES_LIKE).build();

	public static final Uri URI_INSERT_ENERGY = BASE_URI.buildUpon().appendPath(INSERT_ENERGY).build();

	public static final Uri URI_UPDATE_ENERGY = BASE_URI.buildUpon().appendPath(UPDATE_ENERGY).build();

	public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";

	public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.energies";

	//
	public static final class PredefinedValues {
	    public static final class StructureCode {
		public static final int LIQUID = 0x00;
		public static final int SOLID = 0x01;

	    }

	    public static final class EffectCodes {
		public static final int GIVE = 0x00;
		public static final int BURN = 0x01;
	    }

	}

	// Info concernant les colones de la table
	public static final class Columns {
	    public static final String ID = BaseColumns._ID; // on utilise la
	    // convention de
	    // format de
	    // colonne ID
	    // indiqué par
	    // BaseColumns
	    public static final String EFFECT = "effect"; // on absobe ou on
	    // brule
	    public static final String NAME = "name"; // roti de boeuf, Courir,
	    // lasagnes maison
	    public static final String STRUCTURE = "cd_struct"; // est un
	    // liquide
	    // 0, 1
	    public static final String LAST_UPDATE = "last_updt";

	}

    }

    /***********************************************************************************
     * Table des conteneurs: Units
     * 
     * @author R.DUPUIS
     * 
     *         une unitée ça peut être le gramme, l'heure mais aussi le bolo ,
     *         la cuillère
     * 
     **********************************************************************************/
    public static final class TB_Units implements BaseColumns {

	// Info concernant la table
	public static final String NAME = "units";
	public static int TABLE_ID = 3;

	// Path pour l'Uri de séléction de toute la table
	public static final String SELECT_ALL_UNITS = "units";
	public static final int SELECT_UNITS_TOKEN = getToken(TABLE_ID, SQL_ORDER.SELECT, +0);

	// Path pour l'Uri de séléction d'un enregistrement
	public static final String SELECT_UNITY = "units/#";
	public static final int SELECT_UNITY_TOKEN = getToken(TABLE_ID, SQL_ORDER.SELECT, +1);

	public static final String INSERT_UNIT = "insert_unit";
	public static final int INSERT_UNIT_TOKEN = getToken(TABLE_ID, SQL_ORDER.INSERT, +0);

	public static final String UPDATE_UNIT = "update_unit";
	public static final int UPDATE_UNIT_TOKEN = getToken(TABLE_ID, SQL_ORDER.UPDATE, +0);

	public static final Uri URI_SELECT_UNIT = BASE_URI.buildUpon().appendPath(SELECT_ALL_UNITS).build();
	public static final Uri URI_INSERT_UNIT = BASE_URI.buildUpon().appendPath(INSERT_UNIT).build();
	public static final Uri URI_UPDATE_UNIT = BASE_URI.buildUpon().appendPath(UPDATE_UNIT).build();

	public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
	public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.Units";

	//
	public static final class PredefinedValues {
	    public static final class ClassCodes {
		public static final byte INTERNATIONAL = 0x00;
		public static final byte CUSTOM = 0x01;
		public static final byte CONTAINER = 0x02;
		public static final byte TIME = 0X03;
	    }
	}

	// Info concernant les colonnes de la table
	public static final class Columns {
	    public static final String ID = BaseColumns._ID;

	    public static final String CLASS = "class";
	    // class = unitée internationale, Taille ou un conteneur.

	    public static final String LONG_NAME = "long_name";

	    // obligatoire si c'est une unitée internationale.
	    public static final String SHORT_NAME = "short_name";

	    // Obligatoire si c'est un conteneur.
	    public static final String CONTAINERFAMILLY = "containerfamilly";
	    // pour les activitées physique on n'affichera que les unités dont
	    // la famille est TIME.
	    // le gramme, fait partie de la famille WEIGHT.

	    public static final String LAST_UPDATE = "last_updt";

	}

    }

    /***********************************************************************************
     * Table des contenances: Capacities
     * 
     * @author R.DUPUIS
     * 
     **********************************************************************************/
    public static final class TB_Capacities implements BaseColumns {

	// Info concernant la table
	public static final String NAME = "capacities";
	private static int TABLE_ID = 4;

	// Path pour l'Uri de séléction de toute la table
	public static final String SELECT_CAPACITIES = "capacities";
	public static final int SELECT_CAPACITIES_TOKEN = getToken(TABLE_ID, SQL_ORDER.SELECT, +0);

	// Path pour l'Uri de séléction d'un enregistrement
	public static final String SELECT_CAPACITY_BY_ID = "capacities/#";
	public static final int SELECT_CAPACITY_BY_ID_TOKEN = getToken(TABLE_ID, SQL_ORDER.SELECT, +1);

	public static final Uri URI_CONTENT_CAPACITIES = BASE_URI.buildUpon().appendPath(SELECT_CAPACITIES).build();

	public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
	public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.capacities";

	// Info concernant les colones de la table
	public static final class Columns {
	    public static final String ID = BaseColumns._ID;
	    public static final String CONTAINERFAMILLY = "containerfamilly"; // verre
	    public static final String CAPACITY = "capacity"; // 1/2
	    public static final String PICTURE = "picture"; // image 1/2 verre

	    public static final String LAST_UPDATE = "last_update";

	}

    }

    /***********************************************************************************
     * Table des activités: UserActivities
     * 
     * @author R.DUPUIS
     * 
     **********************************************************************************/
    public static final class TB_UserActivities implements BaseColumns {

	// Info concernant la table
	public static final String NAME = "user_activities";
	private static int TABLE_ID = 5;
	public static final Uri URI_BASE_USER_ACTIVITIES = BASE_URI.buildUpon().appendPath(NAME).build();

	// URI de selections de toutes les UserActivities
	// URI: user_activities/select
	public static final String S00_USER_ACTIVITIES_PATH = NAME+"/"+SELECT;
	public static final int S00_USER_ACTIVITIES_TOKEN = getToken(TABLE_ID, SQL_ORDER.SELECT, +0);
	public static final Uri S00_USER_ACTIVITIES_URI = URI_BASE_USER_ACTIVITIES.buildUpon()
		.appendPath(S00_USER_ACTIVITIES_PATH).build();

	// URI de selections d'une UserActivity
	// URI: user_activities/select/#
	public static final String SELECT_USER_ACTIVITY_BY_ID_PATH = NAME+"/"+SELECT + "/#";
	public static final int SELECT_USER_ACTIVITY_BY_ID_TOKEN = getToken(TABLE_ID, SQL_ORDER.SELECT, +1);
	public static final Uri SELECT_USER_ACTIVITY_BY_ID_URI = URI_BASE_USER_ACTIVITIES.buildUpon()
		.appendPath(SELECT_USER_ACTIVITY_BY_ID_PATH).build();

	// URI de selections des UserActivities d'un même type
	// URI: user_activities/type/#
	public static final String SELECT_USER_ACTIVITIES_BY_TYPE_PATH = NAME+"/"+"type/?";
	public static final int SELECT_USER_ACTIVITIES_BY_TYPE_TOKEN = getToken(TABLE_ID, SQL_ORDER.SELECT, +2);
	public static final Uri SELECT_USER_ACTIVITIES_BY_TYPE_URI = URI_BASE_USER_ACTIVITIES.buildUpon()
		.appendPath(SELECT_USER_ACTIVITIES_BY_TYPE_PATH).build();

	
	
	// URI de selections des UserActivities d'une même journée
	// URI: user_activities/date/#
	// si je met * ça marche mais si le met # ça ne marche pas
	public static final String SELECT_USER_ACTIVITIES_BY_DATE_PATH = NAME+SELECT+"_day/*";
	public static final int SELECT_USER_ACTIVITIES_BY_DATE_TOKEN = 9999;
	public static final Uri SELECT_USER_ACTIVITIES_BY_DATE_URI = BASE_URI.buildUpon()
		.appendPath(NAME+SELECT+"_day").build();

	// Path pour l'Uri de création d'un enregistrement
	// URI: user_activities/insert
	public static final String INSERT_USER_ACTIVITY_PATH = INSERT;
	public static final int INSERT_USER_ACTIVITY_TOKEN = getToken(TABLE_ID, SQL_ORDER.INSERT, +0);
	public static final Uri INSERT_USER_ACTIVITY_URI = URI_BASE_USER_ACTIVITIES.buildUpon()
		.appendPath(INSERT_USER_ACTIVITY_PATH).build();

	// Path pour l'Uri de modification de tous les enregistrements
	// URI: user_activities/update_all
	public static final String UPDATE_USER_ACTIVITIES_PATH = UPDATE + "_all";
	public static final int UPDATE_USER_ACTIVITIES_TOKEN = getToken(TABLE_ID, SQL_ORDER.UPDATE, +0);
	public static final Uri UPDATE_USER_ACTIVITIES_URI = URI_BASE_USER_ACTIVITIES.buildUpon()
		.appendPath(UPDATE_USER_ACTIVITIES_PATH).build();

	// Path pour l'Uri de modification d'un enregistrement
	// URI: user_activities/update/#
	public static final String UPDATE_USER_ACTIVITY_PATH = UPDATE + "/#";
	public static final int UPDATE_USER_ACTIVITY_TOKEN = getToken(TABLE_ID, SQL_ORDER.UPDATE, +1);
	public static final Uri UPDATE_USER_ACTIVITY_URI = URI_BASE_USER_ACTIVITIES.buildUpon()
		.appendPath(UPDATE_USER_ACTIVITY_PATH).build();

	// Path pour l'Uri de supression de tous les enregistrements
	// URI: user_activities/delete_all
	public static final String DELETE_USER_ACTIVITIES_PATH = DELETE + "_all";
	public static final int DELETE_USER_ACTIVITIES_TOKEN = getToken(TABLE_ID, SQL_ORDER.DELETE, +0);
	public static final Uri DELETE_USER_ACTIVITIES_URI = URI_BASE_USER_ACTIVITIES.buildUpon().appendPath(DELETE)
		.build();

	// Path pour l'Uri de supression d'un enregistrement
	// URI : user_activities/delete/*
	public static final String DELETE_USER_ACTIVITY_PATH = DELETE + "/*";
	public static final int DELETE_USER_ACTIVITY_TOKEN = getToken(TABLE_ID, SQL_ORDER.DELETE, +1);
	public static final Uri DELETE_USER_ACTIVITY_URI = URI_BASE_USER_ACTIVITIES.buildUpon()
		.appendPath(DELETE_USER_ACTIVITY_PATH).build();

	public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
	public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.useractivities";

	//
	public static final class PredefinedValues {
	    public static final class UACodes {
		public static final int LUNCH = 0;
		public static final int MOVE = 1;
		public static final int WEIGHT = 2;

	    }
	}

	// Info concernant les colones de la table
	public static final class Columns {
	    public static final String ID = BaseColumns._ID; // on utilise la
	    // convention de
	    // format de
	    // colonne ID
	    // indiqué par
	    // BaseColumns
	    public static final String DATE = "date";
	    public static final String TITLE = "title";
	    public static final String CLASS = "class"; // moving / eating /
	    // cooking
	    public static final String LAST_UPDATE = "last_updt";

	}

    }

    /***********************************************************************************
     * Vue pour récupérer les information de la relation entre une NRJ et une
     * Qty.
     * 
     * cette relation peu être une UAC ex : pomme 1 moyenne : REL_TYP_CD =
     * UAC_FOOD
     * 
     * ou une Qty de rérérence ex: pomme 100g : REL_TYP_CD = NRJ_REF_INTRNL
     * 
     * rappel :
     * 
     * in(id UA) ==> rel(id; UA_UAC; id UA; id UAC) ==> OUT ==> id UAC
     * 
     * ====> in(id UAC) ==> rel(id uac; UAC_FOOD; id NRJ; id Qty) ==> id QTY
     * 
     * =========> in(id NRJ) ==> NRJ
     * 
     * =========> in(id QTY) ==> rel(id;QTY;amount;id Unity)
     * 
     * ==============> in(id Unity) ==> Unity
     * 
     * 
     * 
     * @author R.DUPUIS
     * 
     **********************************************************************************/
    public static final class View_NRJ_QtyRef {

	// Info concernant la table
	public static final String NAME = "View_nrj_qtyref";
	private static int VIEW_ID = 6;

	// Path pour l'Uri de séléction d'un enregistrement
	public static final String VIEW_NRJ_QTYREF_PATH = NAME + "/#";
	public static final int VIEW_NRJ_QTYREF_TOKEN = getToken(VIEW_ID, SQL_ORDER.SELECT, +0);

	public static final Uri VIEW_NRJ_QTYREF_URI = BASE_URI.buildUpon().appendPath(VIEW_NRJ_QTYREF_PATH).build();

	public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
	public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal." + NAME;

	public static final class Columns {
	    public static final String REL_NRJ_QTY_ID = "_id";
	    public static final String ENERGY_ID = "nrj_id";
	    public static final String QTY_ID = "qty_id";

	}
    }

    /***********************************************************************************
     * Vue pour récupérer les informations QTY d'une UAC
     * 
     * id rel_typ_cd party1 party2 ------ ---------- ------- ------ REL00 UA_UAC
     * UA uac01 (l'UAC uac01 est rattaché à l'UA) UAC01 UAC_FOOD nrj01 qty33
     * (uac01 est composé de l'énergie nrj01 et de la qty33) qty33 QTY 178
     * unit67 (la qty33 correspond à 178 g) eq88 QTY_EQUIV qty33 qty56 (la qty
     * 33 est évuivalente à la qty56) qty56 QTY 34 unit23 (la qty56 correspond à
     * 34 Kcal)
     * 
     * 
     * la vue doit ramener
     * 
     * uac_id uac_energy_id -> pour recharger nrj la Qty id -> pour recharger la
     * qty (amount+unity)
     * 
     * On utilisera une fonction spécifique pour recharger les équivalences de
     * la Qty
     * 
     * id UA => rel(id;UA_UAC;id UA;id UAC) ==> id UAC
     * 
     * UAC=> rel(id uac;UAC_FOOD;id NRJ;id Qty) ==> id QTY
     * 
     * QTY=> rel(id;QTY;amount;id Unity)
     * 
     * @author R.DUPUIS
     * 
     **********************************************************************************/
    public static final class View_UAC_Data {

	// Info concernant la table
	public static final String NAME = "view_uac_data";
	private static int VIEW_ID = 7;
	// Path pour l'Uri de séléction d'un enregistrement
	public static final String VIEW_UAC_DATA_PATH = NAME + "/#";
	public static final int VIEW_UAC_DATA_TOKEN = getToken(VIEW_ID, SQL_ORDER.SELECT, +1);

	public static final Uri VIEW_UAC_DATA_URI = BASE_URI.buildUpon().appendPath(VIEW_UAC_DATA_PATH).build();

	public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
	public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal" + NAME;

	public static final class Columns {
	    public static final String UAC_ID = "uac_id";
	    public static final String UAC_REL_TYP_CD = "uac_rel_typ_cd";
	    public static final String ENERGY_ID = "energy_id";
	    public static final String QTY_ID = "uac_qty_id";
	}
    }

    /***************************************************************************
     * Vue permettant de relire les UAC d'un UA
     * 
     * @author Rodolphe
     * 
     ***************************************************************************/
    public static final class View_UA_UAC_link {

	// Info concernant la table
	public static final String NAME = "view_ua_uac_link";
	private static int VIEW_ID = 8;
	// Path pour l'Uri de séléction d'un enregistrement
	public static final String VIEW_UAC_FOR_UA_PATH = NAME + "/#";
	public static final int VIEW_UAC_FOR_UA_TOKEN = getToken(VIEW_ID, SQL_ORDER.SELECT, +1);

	public static final Uri VIEW_UAC_FOR_UA_URI = BASE_URI.buildUpon().appendPath(VIEW_UAC_FOR_UA_PATH).build();

	public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
	public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal" + NAME;

	public static final class Columns {
	    public static final String REL_ID = "rel_id";
	    public static final String UA_ID = "ua_id";
	    public static final String UAC_ID = "uac_id";

	}
    }

    /***************************************************************************
     * Vue permettant de charger une Qty
     * 
     * @author Rodolphe
     * 
     ***************************************************************************/
    public static final class View_Qty {

	// Info concernant la table
	public static final String NAME = "view_qty";
	private static int VIEW_ID = 9;

	// Path pour l'Uri de séléction d'un enregistrement
	public static final String VIEW_QTY_BY_ID_PATH = NAME + "/#";
	public static final int VIEW_QTY_BY_ID_TOKEN = getToken(VIEW_ID, SQL_ORDER.SELECT, +0);

	public static final Uri VIEW_QTY_BY_ID_URI = BASE_URI.buildUpon().appendPath(VIEW_QTY_BY_ID_PATH).build();

	public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
	public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal" + NAME;

	public static final class Columns {
	    public static final String UNITY_ID = "unity_id";
	    public static final String AMOUNT = "amount";

	}
    }

    /***********************************************************************************
     * Vue des aliments consommées.
     * 
     * @author R.DUPUIS
     * 
     **********************************************************************************/
    public static final class XX_View_Energies implements BaseColumns {

	// Info concernant la table
	public static final String NAME = "view_energies";
	private static int VIEW_ID = 10;

	// Path pour l'Uri de séléction de toute la table
	public static final String SELECT_VIEW_ENERGIES = "view_energies";
	public static final int SELECT_VIEW_ENERGIES_TOKEN = getToken(VIEW_ID, SQL_ORDER.SELECT, +0);

	// Path pour l'Uri de séléction d'un enregistrement
	public static final String SELECT_VIEW_ENERGIES_BY_ID = "view_energies/#";
	public static final int SELECT_VIEW_ENERGIES_BY_ID_TOKEN = getToken(VIEW_ID, SQL_ORDER.SELECT, +1);

	public static final Uri URI_CONTENT_VIEW_ENERGIES = BASE_URI.buildUpon().appendPath(SELECT_VIEW_ENERGIES)
		.build();

	public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
	public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.view_energies";

	// si je dois mettre à jour la qty de référence, je dois connaitre l'id
	// de la relation
	// idem pour les équivalences
	// la vue ne consiste donc pas à récupérer uniquement les objets lies
	// entre eux.
	// il faut aussi récupérer les liens pour pouvoir les mettre à jour.

	/**
	 * notes :
	 * 
	 * si j'ai id nrj, je peux retrouver id relation qty ref
	 * 
	 * energy.getQty(setAmount(199)) ==> compliqué
	 * 
	 * id = Relation.getId(energy,qty) si je veux mettre à jour la quantité
	 * de calories je dois rechercher l'id de l'équivalence pour calories et
	 * mettre à jour
	 */
	// _id nrj nrj_name nrj_amount_ref nrj_unit_ref

	public static final class Columns {
	    public static final String ENERGY_ID = "energy_id";
	    public static final String ENERGY_NAME = "energy_name";
	    public static final String AMOUNT_REF = "amount_ref";
	    public static final String UNIT_REF_ID = "unit_ref_id";
	    public static final String UNIT_NAME = "unit_name";
	    public static final String LAST_UPDATE = "last_update";
	    public static final String PARENT_ID = "parent_ID";

	}

    }

    /**
     * Liste des requêtes prédéfinies
     * 
     * @author Rodolphe
     * 
     */
    public enum REQUESTS_LIST {
	SELECT_PARTY_REL_ALL, SELECT_PARTY_REL_BY_ID, SELECT_QTY_BY_ID, SELECT_ALL_EQUIV_FOR_UAC, VIEW_DAY_BY_DATE, NONE

	, SELECT_ALL_UAC_OF_UA,
	SELECT_QTYREF_OF_NRJ, UPDATE_UNITY, 
	SELECT_ALL_UNITS, SELECT_ONE_UNITY_BY_ID, INSERT_UNITY, INSERT_ENERGY, INSERT_USER_ACTIVITY

	, SELECT_ALL_ENERGIES, SELECT_ENERGY, SELECT_ENERGIES_LIKE, SELECT_DB_VERSION, UPDATE_ENERGY,

	MIME_ENERGY_DIR, SELECT_USER_ACTIVITY, UPDATE_USER_ACTIVITY, MIME_ENERGY_TYPE, SELECT_USER_ACTIVITIES_BY_DATE, DELETE_USER_ACTIVITY
    }

    public static final class TOKEN_MAP {
	// SparseArray

	public HashMap<Integer, REQUESTS_LIST> _in = new HashMap<Integer, REQUESTS_LIST>();

	public TOKEN_MAP() {
	    _in.put(TB_Party_rel.S00_PARTY_REL_TOKEN, REQUESTS_LIST.NONE);
	    _in.put(TB_Party_rel.S01_PARTY_REL_BY_ID_TOKEN, REQUESTS_LIST.NONE);
	    _in.put(TB_Party_rel.S02_ALL_UAC_FOR_UA_TOKEN, REQUESTS_LIST.NONE);
	    _in.put(TB_Party_rel.S03_ALL_EQUIV_FOR_UAC_TOKEN, REQUESTS_LIST.NONE);
	    _in.put(TB_Party_rel.S04_QTY_REF_INTER_FOR_NRJ_TOKEN, REQUESTS_LIST.NONE);
	    _in.put(TB_Party_rel.S05_EQUIV_REF_INTER_FOR_NRJ_TOKEN, REQUESTS_LIST.NONE);
	    _in.put(TB_Party_rel.INS000_PARTY_REL_TOKEN, REQUESTS_LIST.NONE);
	    _in.put(TB_Party_rel.UPD000_PARTY_REL_TOKEN, REQUESTS_LIST.NONE);

	    _in.put(TB_Energies.SELECT_ENERGIES_TOKEN, REQUESTS_LIST.SELECT_ALL_ENERGIES);
	    _in.put(TB_Energies.SELECT_ENERGY_BY_ID_TOKEN, REQUESTS_LIST.SELECT_ENERGY);
	    _in.put(TB_Energies.SELECT_ENERGIES_LIKE_TOKEN, REQUESTS_LIST.SELECT_ENERGIES_LIKE);
	    _in.put(TB_Energies.INSERT_ENERGY_TOKEN, REQUESTS_LIST.INSERT_ENERGY);
	    _in.put(TB_Energies.UPDATE_ENERGY_TOKEN, REQUESTS_LIST.NONE);

	    _in.put(TB_UserActivities.S00_USER_ACTIVITIES_TOKEN, REQUESTS_LIST.NONE);
	    _in.put(TB_UserActivities.SELECT_USER_ACTIVITY_BY_ID_TOKEN, REQUESTS_LIST.SELECT_USER_ACTIVITY);
	    _in.put(TB_UserActivities.SELECT_USER_ACTIVITIES_BY_DATE_TOKEN,
		    REQUESTS_LIST.SELECT_USER_ACTIVITIES_BY_DATE);
	    _in.put(TB_UserActivities.SELECT_USER_ACTIVITIES_BY_TYPE_TOKEN, REQUESTS_LIST.NONE);
	    _in.put(TB_UserActivities.INSERT_USER_ACTIVITY_TOKEN, REQUESTS_LIST.INSERT_USER_ACTIVITY);
	    _in.put(TB_UserActivities.DELETE_USER_ACTIVITIES_TOKEN, REQUESTS_LIST.NONE);
	    _in.put(TB_UserActivities.DELETE_USER_ACTIVITY_TOKEN, REQUESTS_LIST.DELETE_USER_ACTIVITY);

	    _in.put(TB_UserActivities.UPDATE_USER_ACTIVITIES_TOKEN, REQUESTS_LIST.NONE);

	    _in.put(TB_Units.SELECT_UNITS_TOKEN, REQUESTS_LIST.SELECT_ALL_UNITS);
	    _in.put(TB_Units.SELECT_UNITY_TOKEN, REQUESTS_LIST.SELECT_ONE_UNITY_BY_ID);
	    _in.put(TB_Units.INSERT_UNIT_TOKEN, REQUESTS_LIST.INSERT_UNITY);
	    _in.put(TB_Units.UPDATE_UNIT_TOKEN, REQUESTS_LIST.UPDATE_UNITY);

	    //
	    _in.put(View_NRJ_QtyRef.VIEW_NRJ_QTYREF_TOKEN, REQUESTS_LIST.SELECT_QTYREF_OF_NRJ);
	    //
	    _in.put(View_UA_UAC_link.VIEW_UAC_FOR_UA_TOKEN, REQUESTS_LIST.SELECT_ALL_UAC_OF_UA);
	    //
	    _in.put(View_Qty.VIEW_QTY_BY_ID_TOKEN, REQUESTS_LIST.SELECT_QTY_BY_ID);

	    //
	    _in.put(CustomQuery.DB_VERSION_TOKEN, REQUESTS_LIST.SELECT_DB_VERSION);
	    //
	    _in.put(TB_Energies.UPDATE_ENERGY_TOKEN, REQUESTS_LIST.UPDATE_ENERGY);
	    //
	    _in.put(TB_UserActivities.UPDATE_USER_ACTIVITY_TOKEN, REQUESTS_LIST.UPDATE_USER_ACTIVITY);

	}
    }

}
