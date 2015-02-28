package com.rdupuis.amikcal.data;

import com.rdupuis.amikcal.unity.Unity.UNIT_CLASS;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * <b>Descripteur de contenu.</b>
 * 
 * @author Rodolphe Dupuis
 * @version 0.1
 */
public class ContentDescriptorObj {
	public static final String AUTHORITY = "com.rdupuis.amikcal.AmikcalContentProvider";
	public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
	public static final UriMatcher URI_MATCHER = buildUriMatcher();
	public static final String SELECT = "select";
	public static final String INSERT = "insert";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";

	/***********************************************************************************
	 * 
	 **********************************************************************************/
	private ContentDescriptorObj() {
	};

	/***********************************************************************************
	 * 
	 * @return UriMatcher
	 **********************************************************************************/
	private static UriMatcher buildUriMatcher() {
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		final String authority = AUTHORITY;

		// Liste des Match pour la table PARTY_REL
		// -------------------------------------------------------------------

		matcher.addURI(authority, TB_Party_rel.SEL000_PARTY_REL_PATH,
				TB_Party_rel.SEL000_PARTY_REL_TOKEN);

		matcher.addURI(authority, TB_Party_rel.SEL001_PARTY_REL_BY_ID_PATH,
				TB_Party_rel.SEL001_PARTY_REL_BY_ID_TOKEN);

		matcher.addURI(authority, TB_Party_rel.SEL002_ALL_UAC_FOR_UA_PATH,
				TB_Party_rel.SEL002_ALL_UAC_FOR_UA_TOKEN);

		matcher.addURI(authority, TB_Party_rel.SEL003_ALL_EQUIV_FOR_UAC_PATH,
				TB_Party_rel.SEL003_ALL_EQUIV_FOR_UAC_TOKEN);

		matcher.addURI(authority,
				TB_Party_rel.SEL004_QTY_REF_INTER_FOR_NRJ_PATH,
				TB_Party_rel.SEL004_QTY_REF_INTER_FOR_NRJ_TOKEN);

		matcher.addURI(authority,
				TB_Party_rel.SEL005_EQUIV_REF_INTER_FOR_NRJ_PATH,
				TB_Party_rel.SEL005_EQUIV_REF_INTER_FOR_NRJ_TOKEN);

		matcher.addURI(authority, TB_Party_rel.INS000_PARTY_REL_PATH,
				TB_Party_rel.INS000_PARTY_REL_TOKEN);

		matcher.addURI(authority, TB_Party_rel.UPD000_PARTY_REL_PATH,
				TB_Party_rel.UPD000_PARTY_REL_TOKEN);

		// ----------------------------------------------------------------
		// ----------------------------------------------------------------

		// Match pour le select sur la table des unitées
		matcher.addURI(authority, TB_Units.SELECT_UNITS,
				TB_Units.SELECT_UNITS_TOKEN);
		matcher.addURI(authority, TB_Units.SELECT_UNIT_BY_ID,
				TB_Units.SELECT_UNIT_BY_ID_TOKEN);

		// Match pour l'insert d'une unitée
		matcher.addURI(authority, TB_Units.INSERT_UNIT,
				TB_Units.INSERT_UNIT_TOKEN);
		matcher.addURI(authority, TB_Units.UPDATE_UNIT,
				TB_Units.UPDATE_UNIT_TOKEN);

		// Match pour l'insert d'une unitée
		matcher.addURI(authority, ViewEnergies.SELECT_VIEW_ENERGIES,
				ViewEnergies.SELECT_VIEW_ENERGIES_TOKEN);

		// Match pour l'insert d'une unitée
		matcher.addURI(authority, ViewEnergies.SELECT_VIEW_ENERGIES_BY_ID,
				ViewEnergies.SELECT_VIEW_ENERGIES_BY_ID_TOKEN);

		// **---- Match(s) pour la table user_activities ----->
		matcher.addURI(authority, TB_UserActivities.SELECT_USER_ACTIVITIES,
				TB_UserActivities.SELECT_USER_ACTIVITIES_TOKEN);

		matcher.addURI(authority,
				TB_UserActivities.SELECT_USER_ACTIVITIES_BY_ID,
				TB_UserActivities.SELECT_USER_ACTIVITIES_BY_ID_TOKEN);

		matcher.addURI(authority,
				TB_UserActivities.SELECT_USER_ACTIVITIES_BY_DATE,
				TB_UserActivities.SELECT_USER_ACTIVITIES_BY_DATE_TOKEN);

		matcher.addURI(authority,
				TB_UserActivities.SELECT_USER_ACTIVITIES_BY_TYPE,
				TB_UserActivities.SELECT_USER_ACTIVITIES_BY_TYPE_TOKEN);

		matcher.addURI(authority, TB_UserActivities.INSERT_USER_ACTIVITIES,
				TB_UserActivities.INSERT_USER_ACTIVITIES_TOKEN);

		matcher.addURI(authority,
				TB_UserActivities.UPDATE_USER_ACTIVITIES_BY_ID,
				TB_UserActivities.UPDATE_USER_ACTIVITIES_BY_ID_TOKEN);

		matcher.addURI(authority, TB_UserActivities.DELETE_USER_ACTIVITY,
				TB_UserActivities.DELETE_USER_ACTIVITY_TOKEN);

		// Match pour la séléction d'une vue userActivity
		matcher.addURI(authority,
				ViewUserActivities.VIEW_USER_ACTIVITIES_BY_ID,
				ViewUserActivities.VIEW_USER_ACTIVITIES_BY_ID_TOKEN);

		matcher.addURI(authority, CustomQuery.SUM_ENERGY_OF_DAY,
				CustomQuery.SUM_ENERGY_OF_DAY_TOKEN);
		matcher.addURI(authority, CustomQuery.DB_VERSION,
				CustomQuery.DB_VERSION_TOKEN);
		matcher.addURI(authority, CustomQuery.LAST_WEIGHT_FROM,
				CustomQuery.LAST_WEIGHT_FROM_TOKEN);
		matcher.addURI(authority, CustomQuery.USED_UNITS_FOR_ENERGY,
				CustomQuery.USED_UNITS_FOR_ENERGY_TOKEN);
		return matcher;
	}

	public static final class CustomQuery {

		public static final int SUM_ENERGY_OF_DAY_TOKEN = 1000;
		public static final String SUM_ENERGY_OF_DAY = "sumEnergyOfDay/*";
		public static final Uri URI_SUM_ENERGY_OF_DAY = BASE_URI.buildUpon()
				.appendPath("sumEnergyOfDay").build();

		public static final int DB_VERSION_TOKEN = 1100;
		public static final String DB_VERSION = "dbVersion";
		public static final Uri URI_DB_VERSION = BASE_URI.buildUpon()
				.appendPath(DB_VERSION).build();

		public static final int LAST_WEIGHT_FROM_TOKEN = 1200;
		public static final String LAST_WEIGHT_FROM = "lastWeightFrom/*";
		public static final Uri URI_LAST_WEIGHT_FROM = BASE_URI.buildUpon()
				.appendPath("lastWeightFrom").build();

		public static final int USED_UNITS_FOR_ENERGY_TOKEN = 1300;
		public static final String USED_UNITS_FOR_ENERGY = "usedUnitsForEnergy/*";
		public static final Uri URI_USED_UNITS_FOR_ENERGY = BASE_URI
				.buildUpon().appendPath("usedUnitsForEnergy").build();

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
		public static final String SEL000_PARTY_REL_PATH = "select_party_rel";
		public static final int SEL000_PARTY_REL_TOKEN = 1000;
		public static final Uri SEL000_PARTY_REL_URI = BASE_URI.buildUpon()
				.appendPath(SEL000_PARTY_REL_PATH).build();

		// ----------------------------------------------------------------------------
		// Path pour l'Uri de séléction d'un enregistrement
		// le dièse est un alias pour une chaine de caractère --> un fragment.
		// de ce fait, si on a une URL du style ....../123 ou ..../Bonjour, on a
		// la même signature /#
		public static final String SEL001_PARTY_REL_BY_ID_PATH = "select_party_rel/#";
		public static final int SEL001_PARTY_REL_BY_ID_TOKEN = 1001;
		public static final Uri SEL001_PARTY_REL_BY_ID_URI = BASE_URI
				.buildUpon().appendPath(SEL001_PARTY_REL_BY_ID_PATH).build();

		// ----------------------------------------------------------------------------
		// Path de séléction de tous les UAC d'une UA
		// a partir de id d'une UA, on, récupère les UAC food / move /weight
		public static final String SEL002_ALL_UAC_FOR_UA_PATH = "select_all_uac_for_ua/#";
		public static final int SEL002_ALL_UAC_FOR_UA_TOKEN = 1002;
		public static final Uri SEL002_ALL_UAC_FOR_UA_URI = BASE_URI
				.buildUpon().appendPath(SEL002_ALL_UAC_FOR_UA_PATH).build();

		// ----------------------------------------------------------------------------
		// Path de séléction de toutes les équivalences d'une UAC
		// a partir de l'id d'une UAC on a trouver des équivalence en Kcal / lip
		// / glu...etc
		public static final String SEL003_ALL_EQUIV_FOR_UAC_PATH = "select_all_equiv_for_uac/#";
		public static final int SEL003_ALL_EQUIV_FOR_UAC_TOKEN = 1003;
		public static final Uri SEL003_ALL_EQUIV_FOR_UAC_URI = BASE_URI
				.buildUpon().appendPath(SEL003_ALL_EQUIV_FOR_UAC_PATH).build();

		// ----------------------------------------------------------------------------
		// Path de séléction de la Qty de référence d'une source d'énergie
		// a partir de l'id d'une NRJ, on a trouver la QTY de référence (ex: 100
		// grammes)
		public static final String SEL004_QTY_REF_INTER_FOR_NRJ_PATH = "select_qty_ref_inter_for_nrj/#";
		public static final int SEL004_QTY_REF_INTER_FOR_NRJ_TOKEN = 1004;
		public static final Uri SEL004_QTY_REF_INTER_FOR_NRJ_URI = BASE_URI
				.buildUpon().appendPath(SEL004_QTY_REF_INTER_FOR_NRJ_PATH)
				.build();

		// ----------------------------------------------------------------------------
		// Path de séléction de des Qty equivalences de référence pour une
		// source d'énergie
		// a partir de l'id d'une QTY de référence d'énergie, on a trouver les
		// equivalences
		// ex : Pomme 100 g = 56 Kcal ; 78 GLU...etc
		public static final String SEL005_EQUIV_REF_INTER_FOR_NRJ_PATH = "select_equiv_ref_inter_for_nrj/#";
		public static final int SEL005_EQUIV_REF_INTER_FOR_NRJ_TOKEN = 1005;
		public static final Uri SEL005_EQUIV_REF_INTER_FOR_NRJ_URI = BASE_URI
				.buildUpon().appendPath(SEL005_EQUIV_REF_INTER_FOR_NRJ_PATH)
				.build();

		// Path pour l'uri d'insertion et son token
		public static final String INS000_PARTY_REL_PATH = "insert_" + NAME;
		public static final int INS000_PARTY_REL_TOKEN = 1100;
		public static final Uri INS000_PARTY_REL_URI = BASE_URI.buildUpon()
				.appendPath(INS000_PARTY_REL_PATH).build();

		// Path pour l'uri de mise à jour et son token
		public static final String UPD000_PARTY_REL_PATH = "update_" + NAME;
		public static final int UPD000_PARTY_REL_TOKEN = 1200;
		public static final Uri UP000_PARTY_REL_URI = BASE_URI.buildUpon()
				.appendPath(UPD000_PARTY_REL_PATH).build();

		// Déclaration du tye mime
		public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
		public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.party_rel";

		// Info concernant les colones de la table
		public static final class Columns {
			public static final String ID = BaseColumns._ID; // on utilise la
																// convention de
																// format de
																// colonne ID
																// indiqué par
																// BaseColumns
			public static final String REL_TYP_CD = "rel_typ_cd";
			public static final String FK_PARTY_1 = "fk_party_1";
			public static final String FK_PARTY_2 = "fk_party_2";
			public static final String AMOUNT = "amount";
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

		// Path pour l'Uri de séléction de toute la table
		public static final String SELECT_ENERGIES = "energies";
		public static final int SELECT_ENERGIES_TOKEN = 200;

		// Path pour l'Uri de séléction d'un enregistrement via son ID
		public static final String SELECT_ENERGY_BY_ID = "energies/#";
		public static final int SELECT_ENERGY_BY_ID_TOKEN = 210;

		// Path pour l'Uri de séléction d'un enregistrement dont le nom resemble
		public static final String ENERGIES_LIKE = "energieslike";
		public static final String SELECT_ENERGIES_LIKE = ENERGIES_LIKE + "/*";
		public static final int SELECT_ENERGIES_LIKE_TOKEN = 240;

		// Path pour l'Uri pour l'INSERT d'un enregistrement
		public static final String INSERT_ENERGY = "insert_energy";
		public static final int INSERT_ENERGY_TOKEN = 220;

		// Path pour l'Uri pour l'UPDATE d'un enregistrement
		public static final String UPDATE_ENERGY = "update_energy";
		public static final int UPDATE_ENERGY_TOKEN = 230;

		public static final Uri URI_CONTENT_ENERGIES = BASE_URI.buildUpon()
				.appendPath(SELECT_ENERGIES).build();
		public static final Uri URI_ENERGIES_LIKE = BASE_URI.buildUpon()
				.appendPath(ENERGIES_LIKE).build();

		public static final Uri URI_INSERT_ENERGY = BASE_URI.buildUpon()
				.appendPath(INSERT_ENERGY).build();
		public static final Uri URI_UPDATE_ENERGY = BASE_URI.buildUpon()
				.appendPath(UPDATE_ENERGY).build();

		public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
		public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.energies";

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

		// Path pour l'Uri de séléction de toute la table
		public static final String SELECT_UNITS = "units";
		public static final int SELECT_UNITS_TOKEN = 400;

		// Path pour l'Uri de séléction d'un enregistrement
		public static final String SELECT_UNIT_BY_ID = "units/#";
		public static final int SELECT_UNIT_BY_ID_TOKEN = 410;

		public static final String INSERT_UNIT = "insert_unit";
		public static final int INSERT_UNIT_TOKEN = 420;

		public static final String UPDATE_UNIT = "update_unit";
		public static final int UPDATE_UNIT_TOKEN = 430;

		public static final Uri URI_CONTENT_UNITS = BASE_URI.buildUpon()
				.appendPath(SELECT_UNITS).build();
		public static final Uri URI_INSERT_UNIT = BASE_URI.buildUpon()
				.appendPath(INSERT_UNIT).build();
		public static final Uri URI_UPDATE_UNIT = BASE_URI.buildUpon()
				.appendPath(UPDATE_UNIT).build();

		public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
		public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.Units";

		//
		public static final class PredefinedValues {
			public static final class ClassCodes {
				public static final int INTERNATIONAL = 0x00;
				public static final int CUSTOM = 0x01;
				public static final int CONTAINER = 0x02;
				public static final int TIME = 0X03;
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

		// Path pour l'Uri de séléction de toute la table
		public static final String SELECT_CAPACITIES = "capacities";
		public static final int SELECT_CAPACITIES_TOKEN = 500;

		// Path pour l'Uri de séléction d'un enregistrement
		public static final String SELECT_CAPACITY_BY_ID = "capacities/#";
		public static final int SELECT_CAPACITY_BY_ID_TOKEN = 510;

		public static final Uri URI_CONTENT_CAPACITIES = BASE_URI.buildUpon()
				.appendPath(SELECT_CAPACITIES).build();

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
		public static final Uri URI_BASE_USER_ACTIVITIES = BASE_URI.buildUpon()
				.appendPath(NAME).build();

		// Path pour l'Uri de séléction de toute la table
		public static final String SELECT_USER_ACTIVITIES = NAME + "/" + SELECT;
		public static final Uri URI_SELECT_USER_ACTIVITIES = URI_BASE_USER_ACTIVITIES
				.buildUpon().appendPath(SELECT).build();
		public static final int SELECT_USER_ACTIVITIES_TOKEN = 700;

		public static final String SELECT_USER_ACTIVITIES_BY_ID = SELECT_USER_ACTIVITIES
				+ "/#";
		public static final int SELECT_USER_ACTIVITIES_BY_ID_TOKEN = 701;

		public static final String SELECT_USER_ACTIVITIES_BY_TYPE = SELECT_USER_ACTIVITIES
				+ "type/?";
		public static final Uri URI_SELECT_USER_ACTIVITIES_BY_TYPE = URI_SELECT_USER_ACTIVITIES
				.buildUpon().appendPath("type").build();
		public static final int SELECT_USER_ACTIVITIES_BY_TYPE_TOKEN = 702;

		public static final String SELECT_USER_ACTIVITIES_BY_DATE = SELECT_USER_ACTIVITIES
				+ "/date/*";
		public static final Uri URI_SELECT_USER_ACTIVITIES_BY_DATE = URI_SELECT_USER_ACTIVITIES
				.buildUpon().appendPath("date").build();
		public static final int SELECT_USER_ACTIVITIES_BY_DATE_TOKEN = 703;

		// Path pour l'Uri de création d'un enregistrement
		public static final String INSERT_USER_ACTIVITIES = NAME + "/" + INSERT;
		public static final int INSERT_USER_ACTIVITIES_TOKEN = 710;

		// Path pour l'Uri de modification d'un enregistrement
		public static final String UPDATE_USER_ACTIVITIES = NAME + "/" + UPDATE;
		public static final int UPDATE_USER_ACTIVITIES_TOKEN = 720;

		public static final String UPDATE_USER_ACTIVITIES_BY_ID = UPDATE_USER_ACTIVITIES
				+ "/#";
		public static final int UPDATE_USER_ACTIVITIES_BY_ID_TOKEN = 721;

		// Path pour l'Uri de supression d'un enregistrement
		public static final String DELETE_USER_ACTIVITIES = NAME + "/" + DELETE;
		public static final int DELETE_USER_ACTIVITIES_TOKEN = 730;

		public static final String DELETE_USER_ACTIVITY = DELETE_USER_ACTIVITIES
				+ "/*";
		public static final int DELETE_USER_ACTIVITY_TOKEN = 731;

		public static final Uri URI_INSERT_USER_ACTIVITIES = URI_BASE_USER_ACTIVITIES
				.buildUpon().appendPath(INSERT).build();
		public static final Uri URI_UPDATE_USER_ACTIVITIES = URI_BASE_USER_ACTIVITIES
				.buildUpon().appendPath(UPDATE).build();
		public static final Uri URI_DELETE_USER_ACTIVITIES = URI_BASE_USER_ACTIVITIES
				.buildUpon().appendPath(DELETE).build();

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
	 * Vue pour récupérer les informations d'une source d'énergie.
	 * 
	 * @author R.DUPUIS
	 *
	 **********************************************************************************/
	public static final class VIEW_NRJ_SRC implements BaseColumns {

		// Info concernant la table
		public static final String VIEW_NAME = "view_nrj_src";

		// Path pour l'Uri de séléction de toute la table
		public static final String SELECT_VIEW_NRJ_SRC = VIEW_NAME;
		public static final int SELECT_VIEW_NRJ_SRC_TOKEN = 800;

		// Path pour l'Uri de séléction d'un enregistrement
		public static final String SELECT_VIEW_NRJ_SRC_BY_ID = VIEW_NAME + "/#";
		public static final int SELECT_VIEW_NRJ_SRC_BY_ID_TOKEN = 810;

		public static final Uri URI_CONTENT_VIEW_PARTY_REL = BASE_URI
				.buildUpon().appendPath(SELECT_VIEW_NRJ_SRC).build();

		public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
		public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.view_components";

		public static final class Columns {
			public static final String ID = BaseColumns._ID; // on utilise la
																// convention de
																// format de
																// colonne ID
																// indiqué par
																// BaseColumns
			public static final String ENERGY_ID = "energy_id";
			public static final String ENERGY_NAME = "energy_name";
			public static final String QUANTITY = "quantity";
			public static final String UNIT_ID = "unit_id";
			public static final String UNIT_NAME = "unit_name";
			public static final String LAST_UPDATE = "last_update";
			public static final String PARENT_ID = "parent_ID";

		}
	}

	/***********************************************************************************
	 * Vue des aliments consommées.
	 * 
	 * @author R.DUPUIS
	 *
	 **********************************************************************************/
	public static final class ViewEnergies implements BaseColumns {

		// Info concernant la table
		public static final String NAME = "view_energies";

		// Path pour l'Uri de séléction de toute la table
		public static final String SELECT_VIEW_ENERGIES = "view_energies";
		public static final int SELECT_VIEW_ENERGIES_TOKEN = 900;

		// Path pour l'Uri de séléction d'un enregistrement
		public static final String SELECT_VIEW_ENERGIES_BY_ID = "view_energies/#";
		public static final int SELECT_VIEW_ENERGIES_BY_ID_TOKEN = 910;

		public static final Uri URI_CONTENT_VIEW_ENERGIES = BASE_URI
				.buildUpon().appendPath(SELECT_VIEW_ENERGIES).build();

		public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
		public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.view_energies";

		public static final class Columns {
			public static final String ID = BaseColumns._ID; // on utilise la
																// convention de
																// format de
																// colonne ID
																// indiqué par
																// BaseColumns

			public static final String ENERGY_NAME = "energy_name";
			public static final String QUANTITY = "quantity";
			public static final String UNIT_ID = "unit_id";
			public static final String UNIT_NAME = "unit_name";
			public static final String MNT_ENERGY = "mnt_energy";
			public static final String MNT_LIPIDS = "mnt_lipids";
			public static final String MNT_PROTEINS = "mnt_proteins";
			public static final String MNT_GLUCIDS = "mnt_glucids";
			public static final String VITAMINS = "vitamins";

		}

	}

	/***********************************************************************************
	 * Vue des activités: ViewUserActivities
	 * 
	 * @author R.DUPUIS
	 *
	 **********************************************************************************/
	public static final class ViewUserActivities implements BaseColumns {

		// Info concernant la table
		public static final String NAME = "View_user_activities";

		// Path pour l'Uri de séléction de toute la table
		public static final String VIEW_USER_ACTIVITIES = NAME + "/" + SELECT;
		public static final int VIEW_USER_ACTIVITIES_TOKEN = 1000;

		public static final String VIEW_USER_ACTIVITIES_BY_ID = NAME + "/*";
		public static final int VIEW_USER_ACTIVITIES_BY_ID_TOKEN = 1001;

		public static final Uri URI_VIEW_USER_ACTIVITIES = BASE_URI.buildUpon()
				.appendPath(NAME).build();

		public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
		public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.viewuseractivities";

		// Info concernant les colones de la table
		public static final class Columns {
			public static final String ID = BaseColumns._ID; // on utilise la
																// convention de
																// format de
																// colonne ID
																// indiqué par
																// BaseColumns

			public static final String SUM_ENERGY = "sum_energy";
			public static final String SUM_LIPIDS = "sum_lipids";
			public static final String SUM_PROTEINS = "sum_proteins";
			public static final String SUM_GLUCIDS = "sum_glucids";

		}

	}

	/***********************************************************************************
	 * Vue des activités: ViewDay
	 * 
	 * @author R.DUPUIS descriptif de la vue permetant d'avoir une synthèse de
	 *         la journée.
	 **********************************************************************************/
	public static final class ViewDay implements BaseColumns {

		// Info concernant la table
		public static final String NAME = "View_day";

		// Path pour l'Uri de séléction de toute la table
		public static final String VIEW_DAY = NAME + "/" + SELECT;
		public static final int VIEW_DAY_TOKEN = 1100;

		public static final String VIEW_DAY_BY_DATE = VIEW_DAY + "/#";
		public static final int VIEW_USER_ACTIVITIES_BY_ID_TOKEN = 1101;

		public static final Uri URI_VIEW_DAY_SELECT = BASE_URI.buildUpon()
				.appendPath(VIEW_DAY).build();

		public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
		public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.viewday";

		// Info concernant les colones de la table
		public static final class Columns {
			public static final String DATE = "day";
			public static final String TYPE = "type";
			public static final String SUM_ENERGY = "sum_energy";
			public static final String SUM_LIPIDS = "sum_lipids";
			public static final String SUM_PROTEINS = "sum_proteins";
			public static final String SUM_GLUCIDS = "sum_glucids";

		}

	}
}
