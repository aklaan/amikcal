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

    public static final class CustomQuery {

	private static final int CSTM_QUERY = 0;
	public static final int SUM_ENERGY_OF_DAY_TOKEN = 99100;
	public static final String SUM_ENERGY_OF_DAY = "sumEnergyOfDay/*";
	public static final Uri URI_SUM_ENERGY_OF_DAY = BASE_URI.buildUpon().appendPath("sumEnergyOfDay").build();

	public static final String DB_VERSION_PATH = "dbVersion";
	public static final int DB_VERSION_TOKEN = 99101;
	public static final Uri URI_DB_VERSION = BASE_URI.buildUpon().appendPath(DB_VERSION_PATH).build();

	public static final int LAST_WEIGHT_FROM_TOKEN = 99102;
	public static final String LAST_WEIGHT_FROM = "lastWeightFrom/*";
	public static final Uri URI_LAST_WEIGHT_FROM = BASE_URI.buildUpon().appendPath("lastWeightFrom").build();

	public static final int USED_UNITS_FOR_ENERGY_TOKEN = 99103;
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
	public static final String TBNAME = "party_rel";
	private static final int TABLE_ID = 1;
	public static final Uri URI_BASE_PARTY_REL = BASE_URI.buildUpon().appendPath(TBNAME).build();

	/**
	 * <p>
	 * UA_CFOOD = relation de type composant d'un repas (100 g de pomme)
	 * </br> UA_CMOVE = relation de type composant d'une activité physique
	 * (10 min de marche) </br>
	 * 
	 * UA_CWEIGHT = relation de type composant d'une pesée (45 kg) </br>
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
	public static final String S00_PARTY_REL_PATH = TBNAME + "/" + SELECT;
	public static final int S00_PARTY_REL_TOKEN = 1100;
	public static final Uri S00_PARTY_REL_URI = URI_BASE_PARTY_REL.buildUpon().appendPath(SELECT).build();

	// ----------------------------------------------------------------------------
	// Path pour l'Uri de séléction d'un enregistrement
	// le dièse est un alias pour une chaine de caractère --> un fragment.
	// de ce fait, si on a une URL du style ....../123 ou ..../Bonjour, on a
	// la même signature /#
	// Attention : '#' correspond forcément à un nombre
	// si on souhaite une chaine de caractère on utilise '*'

	public static final String S01_PARTY_REL_BY_ID_PATH = TBNAME + "/" + SELECT + "/#";
	public static final int S01_PARTY_REL_BY_ID_TOKEN = 1101;
	public static final Uri S01_PARTY_REL_BY_ID_URI = URI_BASE_PARTY_REL.buildUpon().appendPath(SELECT).build();

	// ----------------------------------------------------------------------------
	// rechercher une relation
	// Attention : '#' correspond forcément à un nombre
	// si on souhaite une chaine de caractère on utilise '*'
	
		public static final String SEARCH_RELATION_PATH = TBNAME + "/" + "SELECT_RELATION/*";
		public static final int SEARCH_RELATION_TOKEN = 1102;
		public static final Uri SEARCH_RELATION_URI = URI_BASE_PARTY_REL.buildUpon().appendPath("SELECT_RELATION").build();

	
	
	
	// Path pour l'uri d'insertion et son token
	public static final String INS000_PARTY_REL_PATH = TBNAME + "/" + INSERT;
	public static final int INS000_PARTY_REL_TOKEN = 1200;
	public static final Uri INS000_PARTY_REL_URI = URI_BASE_PARTY_REL.buildUpon().appendPath(INSERT).build();

	// Path pour l'uri de mise à jour et son token
	public static final String UPD000_PARTY_REL_PATH = TBNAME + "/" + UPDATE + "/#";
	public static final int UPD000_PARTY_REL_TOKEN = 1300;
	public static final Uri UP000_PARTY_REL_URI = URI_BASE_PARTY_REL.buildUpon().appendPath(UPDATE).build();

	
	
	// Déclaration du tye mime
	public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
	public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.party_rel";

	public static final class PredefinedValues {
	    public static final class RelationsCodes {
		public static final String UNDEFINED = "UNDEF";
		public static final String UA_COMP = "UA_COMP";
		
		public static final String CFOOD = "CFOOD";
		public static final String CMOVE = "CMOVE";
		public static final String CWEIGHT = "CFWEIGHT";
		public static final String COMP_EQUIV = "COMP_EQV";
		public static final String NRJ_REF_INTRNL = "REF_INT";
		public static final String NRJ_REF_EQUIV = "REF_EQV";
		public static final String CSTM_NRJ_REF = "REF_CTM";
		public static final String UNIT_EQUIV = "UNIT_EQV";
		public static final String UNIT_INTER_LINK = "UNIT_INT_REL";
		public static final String QTY = "QTY";
		
		public static final String QTY_EQUIV = "QTY_EQU";
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
	public static final String TBNAME = "TB_Energies";

	// URI de base à toutes les requêtes
	public static final Uri URI_BASE_ENERGIES = BASE_URI.buildUpon().appendPath(TBNAME).build();

	// Path pour l'Uri de séléction de toute les énergies
	public static final String SELECT_ALL_ENERGIES_PATH = TBNAME + "/" + SELECT + "_all";
	public static final int SELECT_ALL_ENERGIES_TOKEN = 2100;
	public static final Uri SELECT_ALL_ENERGIES_URI = URI_BASE_ENERGIES.buildUpon().appendPath(SELECT + "_all")
		.build();

	// Path pour l'Uri de séléction d'une énergie via son ID
	// # := obligatoirement un numérique
	public static final String SELECT_ONE_ENERGY_BY_ID_PATH = TBNAME + "/" + SELECT + "_id/#";
	public static final int SELECT_ONE_ENERGY_BY_ID_TOKEN = 2101;
	public static final Uri SELECT_ONE_ENERGIES_BY_ID_URI = URI_BASE_ENERGIES.buildUpon()
		.appendPath(SELECT + "_id").build();

	// Path pour l'Uri de séléction d'un enregistrement dont le nom resemble
	// * := chaine de caractère
	public static final String SELECT_ENERGIES_LIKE_PATH = TBNAME + "/" + SELECT + "_like/*";
	public static final int SELECT_ENERGIES_LIKE_TOKEN = 2102;
	public static final Uri SELECT_ENERGIES_LIKE_URI = URI_BASE_ENERGIES.buildUpon().appendPath(SELECT + "_like")
		.build();

	// Path pour l'Uri pour l'INSERT d'un enregistrement
	public static final String INSERT_ENERGY_PATH = TBNAME + "/" + INSERT;
	public static final int INSERT_ENERGY_TOKEN = 2200;
	public static final Uri INSERT_ENERGY_URI = URI_BASE_ENERGIES.buildUpon().appendPath(INSERT).build();

	// Path pour l'Uri pour l'UPDATE d'un enregistrement
	public static final String UPDATE_ENERGY_ID_PATH = TBNAME + "/" + UPDATE + "_id/#";
	public static final int UPDATE_ENERGY_ID_TOKEN = 2300;
	public static final Uri UPDATE_ENERGY_ID_URI = URI_BASE_ENERGIES.buildUpon().appendPath(UPDATE + "_id").build();

	public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
	public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.energies";

	//
	public static final class PredefinedValues {
	    public static final class StructureCode {
		public static final String UNDEFINED = "UNDEF";
		public static final String LIQUID = "LIQUID";
		public static final String SOLID = "SOLID";
		public static final String POWDER = "POWDER";

	    }

	    public static final class EnergyClass {
		public static final String UNDEFINED = "UNDEF";
		public static final String FOOD = "FOOD";
		public static final String PHYSICAL_ACTIVITY = "PHYS";
		
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
	    public static final String CLASS = "class"; // on absobe ou on
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
	public static final String SELECT_ALL_UNITS = "units";
	public static final int SELECT_UNITS_TOKEN = 3100;

	// Path pour l'Uri de séléction d'un enregistrement
	public static final String SELECT_UNITY = "units/#";
	public static final int SELECT_UNITY_TOKEN = 3101;

	public static final String INSERT_UNIT = "insert_unit";
	public static final int INSERT_UNIT_TOKEN = 3200;

	public static final String UPDATE_UNIT = "update_unit";
	public static final int UPDATE_UNIT_TOKEN = 3300;

	public static final Uri URI_SELECT_UNIT = BASE_URI.buildUpon().appendPath(SELECT_ALL_UNITS).build();
	public static final Uri URI_INSERT_UNIT = BASE_URI.buildUpon().appendPath(INSERT_UNIT).build();
	public static final Uri URI_UPDATE_UNIT = BASE_URI.buildUpon().appendPath(UPDATE_UNIT).build();

	public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
	public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.Units";

	//
	public static final class PredefinedValues {
	    public static final class ClassCodes {
		public static final String UNDEFINED = "UNDEF";
		public static final String INTERNATIONAL = "INT";
		public static final String CUSTOM = "CSTM";
		public static final String CONTAINER = "CONT";
		public static final String TIME = "TIME";
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
	public static final int SELECT_CAPACITIES_TOKEN = 4100;

	// Path pour l'Uri de séléction d'un enregistrement
	public static final String SELECT_CAPACITY_BY_ID = "capacities/#";
	public static final int SELECT_CAPACITY_BY_ID_TOKEN = 4101;

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
	public static final String TBNAME = "user_activities";

	public static final Uri URI_BASE_USER_ACTIVITIES = BASE_URI.buildUpon().appendPath(TBNAME).build();

	// URI de selections de toutes les UserActivities
	// URI: user_activities/select
	public static final String S00_USER_ACTIVITIES_PATH = TBNAME + "/" + SELECT;
	public static final int S00_USER_ACTIVITIES_TOKEN = 5100;
	public static final Uri S00_USER_ACTIVITIES_URI = URI_BASE_USER_ACTIVITIES.buildUpon().appendPath(SELECT)
		.build();

	// URI de selections d'une UserActivity
	// URI: /user_activities/select_id/#
	public static final String SELECT_USER_ACTIVITY_BY_ID_PATH = TBNAME + "/" + SELECT + "_id/#";
	public static final int SELECT_USER_ACTIVITY_BY_ID_TOKEN = 5101;
	public static final Uri SELECT_USER_ACTIVITY_BY_ID_URI = URI_BASE_USER_ACTIVITIES.buildUpon()
		.appendPath(SELECT + "_id").build();

	// URI de selections des UserActivities d'un même type
	// URI: user_activities/select_type/#
	public static final String SELECT_USER_ACTIVITIES_BY_TYPE_PATH = TBNAME + "/" + SELECT + "_type/#";
	public static int SELECT_USER_ACTIVITIES_BY_TYPE_TOKEN = 5102;
	public static final Uri SELECT_USER_ACTIVITIES_BY_TYPE_URI = URI_BASE_USER_ACTIVITIES.buildUpon()
		.appendPath(SELECT + "_type").build();

	// URI de selections des UserActivities d'une même journée
	// URI: user_activities/date/#
	// si je met * ça marche mais si le met # ça ne marche pas
	// réponse :
	// "#" remplace un nombre hors la date est en String "yyyy-mm-dd"
	// "*" est le bon caractère à utiliser car il remplace un String.
	public static final String SELECT_USER_ACTIVITIES_BY_DATE_PATH = TBNAME + "/" + SELECT + "_day/*";
	public static final int SELECT_USER_ACTIVITIES_BY_DATE_TOKEN = 5103;
	public static final Uri SELECT_USER_ACTIVITIES_BY_DATE_URI = URI_BASE_USER_ACTIVITIES.buildUpon()
		.appendPath(SELECT + "_day").build();

	// Path pour l'Uri de création d'un enregistrement
	// URI: user_activities/insert
	public static final String INSERT_USER_ACTIVITY_PATH = TBNAME + "/" + INSERT;
	public static final int INSERT_USER_ACTIVITY_TOKEN = 5201;
	public static final Uri INSERT_USER_ACTIVITY_URI = URI_BASE_USER_ACTIVITIES.buildUpon().appendPath(INSERT)
		.build();

	// Path pour l'Uri de modification de tous les enregistrements
	// URI: user_activities/update_all
	public static final int UPDATE_USER_ACTIVITIES_TOKEN = 5300;
	public static final String UPDATE_USER_ACTIVITIES_PATH = TBNAME + "/" + UPDATE + "_all";
	public static final Uri UPDATE_USER_ACTIVITIES_URI = URI_BASE_USER_ACTIVITIES.buildUpon()
		.appendPath(UPDATE + "_all").build();

	// Path pour l'Uri de modification d'un enregistrement
	// URI: user_activities/update/#
	public static final String UPDATE_USER_ACTIVITY_PATH = TBNAME + "/" + UPDATE + "_id/#";
	public static final int UPDATE_USER_ACTIVITY_TOKEN = 5301;
	public static final Uri UPDATE_USER_ACTIVITY_URI = URI_BASE_USER_ACTIVITIES.buildUpon()
		.appendPath(UPDATE + "_id").build();

	// Path pour l'Uri de supression de tous les enregistrements
	// URI: user_activities/delete_all
	public static final String DELETE_USER_ACTIVITIES_PATH = TBNAME + "/" + DELETE + "_all";
	public static final int DELETE_USER_ACTIVITIES_TOKEN = 5400;
	public static final Uri DELETE_USER_ACTIVITIES_URI = URI_BASE_USER_ACTIVITIES.buildUpon()
		.appendPath(DELETE + "_all").build();

	// Path pour l'Uri de supression d'un enregistrement
	// URI : user_activities/delete_id/#
	public static final String DELETE_USER_ACTIVITY_PATH = TBNAME + "/" + DELETE + "_id/#";
	public static final int DELETE_USER_ACTIVITY_TOKEN = 5401;

	public static final Uri DELETE_USER_ACTIVITY_URI = URI_BASE_USER_ACTIVITIES.buildUpon()
		.appendPath(DELETE + "_id").build();

	public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
	public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.useractivities";

	//
	public static final class PredefinedValues {
	    public static final class UACodes {
		public static final String LUNCH = "LUNCH";
		public static final String MOVE = "MOVE";
		public static final String WEIGHT = "WEIGHT";

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
     * UA_CFOOD
     * 
     * ou une Qty de rérérence ex: pomme 100g : REL_TYP_CD = NRJ_REF_INTRNL
     * 
     * rappel :
     * 
     * in(id UA) ==> rel(id; UA_UAC; id UA; id UAC) ==> OUT ==> id UAC
     * 
     * ====> in(id UAC) ==> rel(id uac; UA_CFOOD; id NRJ; id Qty) ==> id QTY
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
	public static final String VIEWNAME = "View_nrj_qtyref";
	private static int VIEW_ID = 6;

	public static final Uri URI_BASE_VIEW_NRJ_QTYREF = BASE_URI.buildUpon().appendPath(VIEWNAME).build();

	// Path pour l'Uri de séléction d'un enregistrement a partir de l'id
	// Energy
	public static final String VIEW_QTYREF_BY_NRJ_ID_PATH = VIEWNAME + "/NRJ_id/#";
	public static final int VIEW_QTYREF_BY_NRJ_ID_TOKEN = 6100;
	public static final Uri VIEW_QTYREF_BY_NRJ_ID_URI = URI_BASE_VIEW_NRJ_QTYREF.buildUpon().appendPath("NRJ_id")
		.build();

	// Path pour l'Uri de séléction d'un enregistrement a partir de l'id
	// Energy
	public static final String VIEW_NRJ_QTYREF_PATH = VIEWNAME + "/relation/*";
	public static final int VIEW_NRJ_QTYREF_TOKEN = 6101;
	public static final Uri VIEW_NRJ_QTYREF_URI = URI_BASE_VIEW_NRJ_QTYREF.buildUpon().appendPath("relation")
		.build();

	public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
	public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal." + VIEWNAME;

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
     * UA uac01 (l'UAC uac01 est rattaché à l'UA) UAC01 UA_CFOOD nrj01 qty33
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
     * UAC=> rel(id uac;UA_CFOOD;id NRJ;id Qty) ==> id QTY
     * 
     * QTY=> rel(id;QTY;amount;id Unity)
     * 
     * @author R.DUPUIS
     * 
     **********************************************************************************/
    public static final class View_UAC_Data {

	// Info concernant la table
	public static final String VIEWNAME = "view_uac_data";
	private static int VIEW_ID = 7;
	public static final Uri URI_BASE_VIEW_UAC_DATA = BASE_URI.buildUpon().appendPath(VIEWNAME).build();
	// Path pour l'Uri de séléction d'un enregistrement
	public static final String VIEW_UAC_DATA_PATH = VIEWNAME + "/" + SELECT + "/#";
	public static final int VIEW_UAC_DATA_TOKEN = 7100;

	public static final Uri VIEW_UAC_DATA_URI = URI_BASE_VIEW_UAC_DATA.buildUpon().appendPath(SELECT).build();

	public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
	public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal" + VIEWNAME;

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
    public static final class View_UA_Comp_link {
	// n°de vue = 8
	// Info concernant la table
	public static final String VIEW_NAME = "view_ua_comp_link";
	public static final Uri URI_BASE_VIEW_UA_COMP_REL = BASE_URI.buildUpon().appendPath(VIEW_NAME).build();

	// Path pour l'Uri de séléction d'un enregistrement
	public static final String SELECT_COMP_OF_UA_PATH = VIEW_NAME + "/" + SELECT + "/#";
	public static final int SELECT_COMP_OF_UA_TOKEN = 8100;
	public static final Uri SELECT_COMP_OF_UA_URI = URI_BASE_VIEW_UA_COMP_REL.buildUpon().appendPath(SELECT).build();

	public static final String SEARCH_RELATION_PATH = VIEW_NAME + "/relation/*";
	public static final int SEARCH_RELATION_TOKEN = 8101;
	public static final Uri SEARCH_RELATION_URI = URI_BASE_VIEW_UA_COMP_REL.buildUpon().appendPath("relation")
		.build();

	public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
	public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal" + VIEW_NAME;

	public static final class Columns {
	    public static final String REL_ID = "rel_id";
	    public static final String UA_ID = "ua_id";
	    public static final String COMP_ID = "comp_id";

	}
    }

    /***************************************************************************
     * <h1>View_qty_equiv</h1>
     * <p>
     * Vue permettant de équupérer les equivalences d'une qty
     * </p>
     * <p>
     * Token = <b>10</b>[x]nn
     * </p>
     * 
     * @author Rodolphe
     * 
     ***************************************************************************/
    public static final class View_qty_equiv {
	//
	// Info concernant la table
	public static final String VIEWNAME = "view_qty_equiv";
	public static final Uri URI_BASE_VIEW_QTY_EQUIV = BASE_URI.buildUpon().appendPath(VIEWNAME).build();

	// Path pour l'Uri de séléction d'un enregistrement
	public static final String VIEW_EQUIV_OF_QTY_PATH = VIEWNAME + "/" + SELECT + "/#";
	public static final int VIEW_EQUIV_OF_QTY_TOKEN = 10100;
	public static final Uri VIEW_ALL_QTY_EQUIV_URI = URI_BASE_VIEW_QTY_EQUIV.buildUpon().appendPath(SELECT).build();

	public static final String SEARCH_RELATION_PATH = VIEWNAME + "/relation/*";
	public static final int SEARCH_RELATION_TOKEN = 10101;
	public static final Uri SEARCH_RELATION_URI = URI_BASE_VIEW_QTY_EQUIV.buildUpon().appendPath("relation")
		.build();

	public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
	public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal" + VIEWNAME;

	public static final class Columns {
	    public static final String REL_ID = "rel_id";
	    public static final String QTY_ID = "qty_id";
	    public static final String QTY_EQUIV_ID = "qty_equiv_id";

	}
    }

    /***************************************************************************
     * Vue permettant de charger une Qty
     * 
     * @author Rodolphe
     * 
     ***************************************************************************/
    public static final class View_Qty {
	// n°de vue = 9
	// Info concernant la table
	public static final String VIEWNAME = "view_qty";
	private static int VIEW_ID = 9;
	public static final Uri URI_BASE_VIEW_QTY = BASE_URI.buildUpon().appendPath(VIEWNAME).build();

	// Path pour l'Uri de séléction d'un enregistrement
	public static final String VIEW_QTY_BY_ID_PATH = VIEWNAME + "/" + SELECT + "_id/#";
	public static final int VIEW_QTY_BY_ID_TOKEN = 9100;

	public static final Uri VIEW_QTY_BY_ID_URI = URI_BASE_VIEW_QTY.buildUpon().appendPath(SELECT + "_id").build();

	public static String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
	public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal" + VIEWNAME;

	public static final class Columns {
	    public static final String QTY_ID = "qty_id";
	    public static final String UNITY_ID = "unity_id";
	    public static final String AMOUNT = "amount";

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

	, SELECT_ALL_COMPONENT_OF_UA, SELECT_QTYREF_OF_NRJ, UPDATE_UNITY, SELECT_ALL_UNITS, SELECT_ONE_UNITY_BY_ID, INSERT_UNITY, INSERT_ENERGY, INSERT_USER_ACTIVITY

	, SELECT_ALL_ENERGIES, SELECT_ONE_ENERGY_BY_ID, SELECT_ENERGIES_LIKE, UPDATE_ENERGY, SELECT_QTYREF_RELATION,

	MIME_ENERGY_DIR, SELECT_USER_ACTIVITY, UPDATE_USER_ACTIVITY, MIME_ENERGY_TYPE, SELECT_USER_ACTIVITIES_BY_DATE, DELETE_USER_ACTIVITY, SELECT_DB_VERSION, SELECT_REL_NRJ_QTYREF

	, INSERT_PARTY_REL, UPDATE_PARTY_REL, UPDATE_ENERGY_ID, SEARCH_REL_UA_UAC, SELECT_UAC,SELECT_ALL_EQUIV_OF_QTY
    ,SEARCH_QTY_EQUIV,SEARCH_RELATION    }

    public static final class TOKEN_MAP {
	// SparseArray

	public HashMap<Integer, REQUESTS_LIST> _in = new HashMap<Integer, REQUESTS_LIST>();

	public TOKEN_MAP() {
	    _in.put(TB_Party_rel.S00_PARTY_REL_TOKEN, REQUESTS_LIST.NONE);
	    _in.put(TB_Party_rel.S01_PARTY_REL_BY_ID_TOKEN, REQUESTS_LIST.SELECT_PARTY_REL_BY_ID);
	    _in.put(TB_Party_rel.SEARCH_RELATION_TOKEN, REQUESTS_LIST.SEARCH_RELATION);
	    
	    _in.put(TB_Party_rel.INS000_PARTY_REL_TOKEN, REQUESTS_LIST.INSERT_PARTY_REL);
	    _in.put(TB_Party_rel.UPD000_PARTY_REL_TOKEN, REQUESTS_LIST.UPDATE_PARTY_REL);

	    _in.put(TB_Energies.SELECT_ALL_ENERGIES_TOKEN, REQUESTS_LIST.SELECT_ALL_ENERGIES);
	    _in.put(TB_Energies.SELECT_ONE_ENERGY_BY_ID_TOKEN, REQUESTS_LIST.SELECT_ONE_ENERGY_BY_ID);
	    _in.put(TB_Energies.SELECT_ENERGIES_LIKE_TOKEN, REQUESTS_LIST.SELECT_ENERGIES_LIKE);
	    _in.put(TB_Energies.INSERT_ENERGY_TOKEN, REQUESTS_LIST.INSERT_ENERGY);
	    _in.put(TB_Energies.UPDATE_ENERGY_ID_TOKEN, REQUESTS_LIST.UPDATE_ENERGY_ID);

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
	    _in.put(View_NRJ_QtyRef.VIEW_NRJ_QTYREF_TOKEN, REQUESTS_LIST.SELECT_QTYREF_RELATION);
	    _in.put(View_NRJ_QtyRef.VIEW_QTYREF_BY_NRJ_ID_TOKEN, REQUESTS_LIST.SELECT_QTYREF_OF_NRJ);

	    //
	    _in.put(View_UA_Comp_link.SELECT_COMP_OF_UA_TOKEN, REQUESTS_LIST.SELECT_ALL_COMPONENT_OF_UA);
	    //
	    _in.put(View_Qty.VIEW_QTY_BY_ID_TOKEN, REQUESTS_LIST.SELECT_QTY_BY_ID);
	    _in.put(View_UA_Comp_link.SEARCH_RELATION_TOKEN, REQUESTS_LIST.SEARCH_REL_UA_UAC);

	    _in.put(View_UAC_Data.VIEW_UAC_DATA_TOKEN, REQUESTS_LIST.SELECT_UAC);

	    //
	    _in.put(CustomQuery.DB_VERSION_TOKEN, REQUESTS_LIST.SELECT_DB_VERSION);
	    //
	    _in.put(TB_Energies.UPDATE_ENERGY_ID_TOKEN, REQUESTS_LIST.UPDATE_ENERGY);
	    //
	    _in.put(TB_UserActivities.UPDATE_USER_ACTIVITY_TOKEN, REQUESTS_LIST.UPDATE_USER_ACTIVITY);

	    _in.put(View_qty_equiv.VIEW_EQUIV_OF_QTY_TOKEN, REQUESTS_LIST.SELECT_ALL_EQUIV_OF_QTY);
	    _in.put(View_qty_equiv.SEARCH_RELATION_TOKEN, REQUESTS_LIST.SEARCH_QTY_EQUIV);

	    
	}
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
	matcher.addURI(authority, TB_Party_rel.SEARCH_RELATION_PATH, TB_Party_rel.SEARCH_RELATION_TOKEN);
	matcher.addURI(authority, TB_Party_rel.INS000_PARTY_REL_PATH, TB_Party_rel.INS000_PARTY_REL_TOKEN);
	matcher.addURI(authority, TB_Party_rel.UPD000_PARTY_REL_PATH, TB_Party_rel.UPD000_PARTY_REL_TOKEN);

	// ----------------------------------------------------------------
	// Match pour la table des energies
	// ----------------------------------------------------------------

	// Match pour le select sur la table des unitées
	matcher.addURI(authority, TB_Energies.SELECT_ALL_ENERGIES_PATH, TB_Energies.SELECT_ALL_ENERGIES_TOKEN);
	matcher.addURI(authority, TB_Energies.SELECT_ONE_ENERGY_BY_ID_PATH, TB_Energies.SELECT_ONE_ENERGY_BY_ID_TOKEN);
	matcher.addURI(authority, TB_Energies.SELECT_ENERGIES_LIKE_PATH, TB_Energies.SELECT_ENERGIES_LIKE_TOKEN);
	matcher.addURI(authority, TB_Energies.INSERT_ENERGY_PATH, TB_Energies.INSERT_ENERGY_TOKEN);
	matcher.addURI(authority, TB_Energies.UPDATE_ENERGY_ID_PATH, TB_Energies.UPDATE_ENERGY_ID_TOKEN);
	// ----------------------------------------------------------------
	// Match pour la table des untitées
	// ----------------------------------------------------------------

	// Match pour le select sur la table des unitées
	matcher.addURI(authority, TB_Units.SELECT_ALL_UNITS, TB_Units.SELECT_UNITS_TOKEN);
	matcher.addURI(authority, TB_Units.SELECT_UNITY, TB_Units.SELECT_UNITY_TOKEN);

	// Match pour l'insert d'une unitée
	matcher.addURI(authority, TB_Units.INSERT_UNIT, TB_Units.INSERT_UNIT_TOKEN);
	matcher.addURI(authority, TB_Units.UPDATE_UNIT, TB_Units.UPDATE_UNIT_TOKEN);

	// Match pour la vue de selection des liens UA<->Composants
	// ----------------------------------------------------------------
	matcher.addURI(authority, View_UA_Comp_link.SELECT_COMP_OF_UA_PATH, View_UA_Comp_link.SELECT_COMP_OF_UA_TOKEN);
	matcher.addURI(authority, View_UA_Comp_link.SEARCH_RELATION_PATH, View_UA_Comp_link.SEARCH_RELATION_TOKEN);

	// Match pour la vue de selection des liens Composant<->Qty
	// ----------------------------------------------------------------
	matcher.addURI(authority, View_UAC_Data.VIEW_UAC_DATA_PATH, View_UAC_Data.VIEW_UAC_DATA_TOKEN);

	// Match pour la vue de selection des liens NRJ<->QtyRef
	// ----------------------------------------------------------------
	matcher.addURI(authority, View_NRJ_QtyRef.VIEW_NRJ_QTYREF_PATH, View_NRJ_QtyRef.VIEW_NRJ_QTYREF_TOKEN);
	matcher.addURI(authority, View_NRJ_QtyRef.VIEW_QTYREF_BY_NRJ_ID_PATH,
		View_NRJ_QtyRef.VIEW_QTYREF_BY_NRJ_ID_TOKEN);

	matcher.addURI(authority, View_Qty.VIEW_QTY_BY_ID_PATH, View_Qty.VIEW_QTY_BY_ID_TOKEN);
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
	
	matcher.addURI(authority, View_qty_equiv.VIEW_EQUIV_OF_QTY_PATH, View_qty_equiv.VIEW_EQUIV_OF_QTY_TOKEN);
	

	matcher.addURI(authority, View_qty_equiv.SEARCH_RELATION_PATH, View_qty_equiv.SEARCH_RELATION_TOKEN);
	
	return matcher;
    }

}
