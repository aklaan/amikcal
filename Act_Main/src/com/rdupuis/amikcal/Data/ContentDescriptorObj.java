package com.rdupuis.amikcal.Data;

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
	public static final String SELECT  = "select";
	public static final String INSERT  = "insert";
	public static final String UPDATE  = "update";
	public static final String DELETE  = "delete";
	/***********************************************************************************
	 * 
	 **********************************************************************************/
	private ContentDescriptorObj(){};

	
	
	/***********************************************************************************
	 * 
	 * @return UriMatcher
	 **********************************************************************************/
	private static  UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = AUTHORITY;

        //Match pour la table ActivityComponent ( aliments consommés ou ingrédient d'une recette)
        matcher.addURI(authority, ActivityComponent.SELECT_ACTIVITY_COMPONENTS, ActivityComponent.SELECT_ACTIVITY_COMPONENTS_TOKEN);
		matcher.addURI(authority, ActivityComponent.SELECT_ACTIVITY_COMPONENTS_BY_ID, ActivityComponent.SELECT_ACTIVITY_COMPONENTS_BY_ID_TOKEN);
	
	    //Match pour la table des energies
        matcher.addURI(authority, Energies.SELECT_ENERGIES, Energies.SELECT_ENERGIES_TOKEN);
        matcher.addURI(authority, Energies.SELECT_ENERGIES_LIKE, Energies.SELECT_ENERGIES_LIKE_TOKEN);
        matcher.addURI(authority, Energies.SELECT_ENERGY_BY_ID, Energies.SELECT_ENERGY_BY_ID_TOKEN);
		matcher.addURI(authority, Energies.INSERT_ENERGY, Energies.INSERT_ENERGY_TOKEN);
		matcher.addURI(authority, Energies.UPDATE_ENERGY, Energies.UPDATE_ENERGY_TOKEN);
		
	    //Match pour la vue des composants d'une activitée
        matcher.addURI(authority, ViewActivityComponent.SELECT_VIEW_ACTIVITY_COMPONENTS, ViewActivityComponent.SELECT_VIEW_ACTIVITY_COMPONENTS_TOKEN);
		matcher.addURI(authority, ViewActivityComponent.SELECT_VIEW_ACTIVITY_COMPONENTS_BY_ID, ViewActivityComponent.SELECT_VIEW_ACTIVITY_COMPONENTS_BY_ID_TOKEN);
		matcher.addURI(authority, ViewActivityComponent.SELECT_VIEW_ACTIVITY_COMPONENTS_BY_ACTIVITY, ViewActivityComponent.SELECT_VIEW_ACTIVITY_COMPONENTS_BY_ACTIVITY_TOKEN);
		
		//Match pour l'insert d'un component
        matcher.addURI(authority, ActivityComponent.INSERT_ACTIVITY_COMPONENT, ActivityComponent.INSERT_ACTIVITY_COMPONENT_TOKEN);
		
        //Mactch pour les équivalences
        matcher.addURI(authority, Equivalences.SELECT_EQUIVALENCES, Equivalences.SELECT_EQUIVALENCES_TOKEN);
        matcher.addURI(authority, Equivalences.SEARCH_EQUIVALENCE, Equivalences.SEARCH_EQUIVALENCE_TOKEN);
        matcher.addURI(authority, Equivalences.SELECT_EQUIVALENCE_BY_ID, Equivalences.SELECT_EQUIVALENCE_BY_ID_TOKEN);
        
        matcher.addURI(authority, Equivalences.INSERT_EQUIVALENCE, Equivalences.INSERT_EQUIVALENCE_TOKEN);
        matcher.addURI(authority, Equivalences.UPDATE_EQUIVALENCE, Equivalences.UPDATE_EQUIVALENCE_TOKEN);
        
        //Match pour le select sur la table des unitées
        matcher.addURI(authority, Units.SELECT_UNITS, Units.SELECT_UNITS_TOKEN);
        matcher.addURI(authority, Units.SELECT_UNIT_BY_ID, Units.SELECT_UNIT_BY_ID_TOKEN);
		
        
    	//Match pour l'insert d'une unitée
        matcher.addURI(authority, Units.INSERT_UNIT, Units.INSERT_UNIT_TOKEN);
        matcher.addURI(authority, Units.UPDATE_UNIT, Units.UPDATE_UNIT_TOKEN);
        
        //Match pour l'insert d'une unitée
        matcher.addURI(authority, ActivityComponent.UPDATE_ACTIVITY_COMPONENT, ActivityComponent.UPDATE_ACTIVITY_COMPONENT_TOKEN);
    
        
        //Match pour l'insert d'une unitée
        matcher.addURI(authority, ViewEnergies.SELECT_VIEW_ENERGIES, ViewEnergies.SELECT_VIEW_ENERGIES_TOKEN);
		
        //Match pour l'insert d'une unitée
        matcher.addURI(authority, ViewEnergies.SELECT_VIEW_ENERGIES_BY_ID, ViewEnergies.SELECT_VIEW_ENERGIES_BY_ID_TOKEN);
        
        
      //**---- Match(s) pour la table user_activities ----->
        matcher.addURI(authority, UserActivities.SELECT_USER_ACTIVITIES,         UserActivities.SELECT_USER_ACTIVITIES_TOKEN);
        matcher.addURI(authority, UserActivities.SELECT_USER_ACTIVITIES_BY_ID,   UserActivities.SELECT_USER_ACTIVITIES_BY_ID_TOKEN);
        matcher.addURI(authority, UserActivities.SELECT_USER_ACTIVITIES_BY_DATE, UserActivities.SELECT_USER_ACTIVITIES_BY_DATE_TOKEN);
        matcher.addURI(authority, UserActivities.SELECT_USER_ACTIVITIES_BY_TYPE, UserActivities.SELECT_USER_ACTIVITIES_BY_TYPE_TOKEN);

        matcher.addURI(authority, UserActivities.INSERT_USER_ACTIVITIES,       UserActivities.INSERT_USER_ACTIVITIES_TOKEN);
        matcher.addURI(authority, UserActivities.UPDATE_USER_ACTIVITIES_BY_ID, UserActivities.UPDATE_USER_ACTIVITIES_BY_ID_TOKEN);
        matcher.addURI(authority, UserActivities.DELETE_USER_ACTIVITY, UserActivities.DELETE_USER_ACTIVITY_TOKEN);
          
        
  
        //Match pour la séléction d'une vue userActivity
        matcher.addURI(authority, ViewUserActivities.VIEW_USER_ACTIVITIES_BY_ID, ViewUserActivities.VIEW_USER_ACTIVITIES_BY_ID_TOKEN);
  
        
        matcher.addURI(authority, CustomQuery.SUM_ENERGY_OF_DAY, CustomQuery.SUM_ENERGY_OF_DAY_TOKEN);
        matcher.addURI(authority, CustomQuery.DB_VERSION, CustomQuery.DB_VERSION_TOKEN);
        matcher.addURI(authority, CustomQuery.LAST_WEIGHT_FROM, CustomQuery.LAST_WEIGHT_FROM_TOKEN);
        matcher.addURI(authority, CustomQuery.USED_UNITS_FOR_ENERGY, CustomQuery.USED_UNITS_FOR_ENERGY_TOKEN);
        return matcher;
	}

	
	
	public static final class CustomQuery{
		
		public static final int SUM_ENERGY_OF_DAY_TOKEN = 1000;
		public static final String SUM_ENERGY_OF_DAY = "sumEnergyOfDay/*";
		public static final Uri URI_SUM_ENERGY_OF_DAY = BASE_URI.buildUpon().appendPath("sumEnergyOfDay").build();
		

		public static final int DB_VERSION_TOKEN = 1100;
		public static final String DB_VERSION = "dbVersion";
		public static final Uri URI_DB_VERSION = BASE_URI.buildUpon().appendPath(DB_VERSION).build();
	
		
		public static final int LAST_WEIGHT_FROM_TOKEN = 1200;
		public static final String LAST_WEIGHT_FROM = "lastWeightFrom/*";
		public static final Uri URI_LAST_WEIGHT_FROM = BASE_URI.buildUpon().appendPath("lastWeightFrom").build();
	
		public static final int USED_UNITS_FOR_ENERGY_TOKEN = 1300;
		public static final String USED_UNITS_FOR_ENERGY = "usedUnitsForEnergy/*";
		public static final Uri URI_USED_UNITS_FOR_ENERGY = BASE_URI.buildUpon().appendPath("usedUnitsForEnergy").build();
	
	}
	
	
	
	
	
	
	/**********************************************************************************************
	 * Table des aliments consommés : components
	 * @author R.DUPUIS
	 *
	 **********************************************************************************/
	public static final class ActivityComponent implements BaseColumns {
		
		// Info concernant la table
		public static final String NAME = "components";
		
		//Path pour l'Uri de séléction de toute la table 
		public static final String SELECT_ACTIVITY_COMPONENTS = "components";
		public static final int SELECT_ACTIVITY_COMPONENTS_TOKEN = 100;
		
		//Path pour l'Uri de séléction d'un enregistrement
		//le dièse est un alias pour une chaine de caractère --> un fragment.
		// de ce fait, si on a unr URL du style ....../123 ou ..../Bonjour, on a la même signature /#
		public static final String SELECT_ACTIVITY_COMPONENTS_BY_ID = "components/#";
		public static final int SELECT_ACTIVITY_COMPONENTS_BY_ID_TOKEN = 110;
		
		
		public static final String INSERT_ACTIVITY_COMPONENT = "insert_component";
		public static final int INSERT_ACTIVITY_COMPONENT_TOKEN = 120;
		
		public static final String UPDATE_ACTIVITY_COMPONENT = "update_component";
		public static final int UPDATE_ACTIVITY_COMPONENT_TOKEN = 130;
		
		
		public static final Uri URI_SELECT_ACTIVITY_COMPONENTS = BASE_URI.buildUpon().appendPath(SELECT_ACTIVITY_COMPONENTS).build();
		public static final Uri URI_INSERT_ACTIVITY_COMPONENT = BASE_URI.buildUpon().appendPath(INSERT_ACTIVITY_COMPONENT).build();
		public static final Uri URI_UPDATE_ACTIVITY_COMPONENT = BASE_URI.buildUpon().appendPath(UPDATE_ACTIVITY_COMPONENT).build();
		
		public static  String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
		public static  String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.components";

		// Info concernant les colones de la table
		public static final class Columns  {
			public static final String ID = BaseColumns._ID; // on utilise la convention de format de colonne ID indiqué par BaseColumns
			public static final String FK_PARENT    = "fk_parent";
			public static final String FK_ENERGY    = "fk_energy";
			public static final String QUANTITY     = "quantity";
			public static final String FK_UNIT      = "fk_unit";
			public static final String SCORE        = "score";
			public static final String MNT_ENERGY   = "mnt_energy";
			public static final String MNT_LIPIDS   = "mnt_lipids";
			public static final String MNT_PROTEINS = "mnt_proteins";
			public static final String MNT_GLUCIDS  = "mnt_glucids";
			public static final String VITAMINS     = "vitamins";
			public static final String LAST_UPDATE  = "last_update";
			
		}

	}


	
		
	/***********************************************************************************
	 * Table des aliments et leurs référence énergétique : Energies
	 * @author R.DUPUIS
	 *
	 **********************************************************************************/
	public static final class Energies implements BaseColumns {
		
		// Info concernant la table
		public static final String NAME = "energies";
		
		//Path pour l'Uri de séléction de toute la table 
		public static final String SELECT_ENERGIES = "energies";
		public static final int SELECT_ENERGIES_TOKEN = 200;
		
		//Path pour l'Uri de séléction d'un enregistrement
		public static final String SELECT_ENERGY_BY_ID = "energies/#";
		public static final int SELECT_ENERGY_BY_ID_TOKEN = 210;
		
		//Path pour l'Uri de séléction d'un enregistrement
		public static final String ENERGIES_LIKE = "energieslike";
		public static final String SELECT_ENERGIES_LIKE = ENERGIES_LIKE + "/*";
		public static final int SELECT_ENERGIES_LIKE_TOKEN = 240;
		
		
		//Path pour l'Uri pour l'INSERT d'un enregistrement
		public static final String INSERT_ENERGY = "insert_energy";
		public static final int INSERT_ENERGY_TOKEN = 220;
		
		//Path pour l'Uri pour l'UPDATE d'un enregistrement
		public static final String UPDATE_ENERGY = "update_energy";
		public static final int UPDATE_ENERGY_TOKEN = 230;
		
		public static final Uri URI_CONTENT_ENERGIES = BASE_URI.buildUpon().appendPath(SELECT_ENERGIES).build();
		public static final Uri URI_ENERGIES_LIKE = BASE_URI.buildUpon().appendPath(ENERGIES_LIKE).build();
		
		public static final Uri URI_INSERT_ENERGY = BASE_URI.buildUpon().appendPath(INSERT_ENERGY).build();
		public static final Uri URI_UPDATE_ENERGY = BASE_URI.buildUpon().appendPath(UPDATE_ENERGY).build();
		
		public static  String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
		public static  String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.energies";

		// Info concernant les colones de la table
		public static final class Columns  {
			public static final String ID = BaseColumns._ID;  // on utilise la convention de format de colonne ID indiqué par BaseColumns
			public static final String CATEGORY      = "category"; // famille poisson, viande ..caddy 
			public static final String TYPE          = "type";    // aliment ou recette
			public static final String NAME          = "name";    // roti de boeuf, carotte, lasagnes maison
			public static final String ISLIQUID      = "isliquid"; // est un liquide Y/N
			public static final String QUANTITY      = "quantity";
			public static final String FK_UNIT       = "fk_unit";
			public static final String MNT_ENERGY    = "mnt_energy";
			public static final String MNT_LIPIDS    = "mnt_lipids";
			public static final String MNT_GLUCIDS   = "mnt_glucids";
			public static final String MNT_PROTEINS  = "mnt_proteins";
			public static final String VITAMINS      = "vitamins";
			public static final String LAST_UPDATE   = "last_update";
			
		}

	}
	
	
	
	/***********************************************************************************
	 * Table des jours de saisie: Days
	 * @author R.DUPUIS
	 *
	 **********************************************************************************/
	public static final class Days implements BaseColumns {
		
		// Info concernant la table
		public static final String NAME = "days";
		
		//Path pour l'Uri de séléction de toute la table 
		public static final String SELECT_DAYS = "days";
		public static final int SELECT_DAYS_TOKEN = 300;
		
		//Path pour l'Uri de séléction d'un enregistrement
		public static final String SELECT_DAY_BY_ID = "days/#";
		public static final int SELECT_DAY_BY_ID_TOKEN = 310;
		
		
	
		 
		
		public static final Uri URI_CONTENT_DAYS = BASE_URI.buildUpon().appendPath(SELECT_DAYS).build();
		
		public static  String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
		public static  String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.days";

		// Info concernant les colones de la table
		public static final class Columns  {
			public static final String DATE         = "day";
			public static final String SCORE        = "score";  
			public static final String COVER        = "cover";  
			public static final String MNT_ENERGY   = "mnt_energy";
			public static final String MNT_LIPIDS   = "mnt_lipids";
			public static final String MNT_GLUCIDS  = "mnt_glucids";
			public static final String MNT_PROTEINS = "mnt_proteins";
			public static final String VITAMINS     = "vitamins";
			public static final String LAST_UPDATE  = "last_update";
			
		}

	}
	

	/***********************************************************************************
	 * Table des conteneurs: Units
	 * @author R.DUPUIS
	 *
	 *  une unitée ça peut être le gramme, l'heure mais aussi le bolo , la cuillère
	 *
	 **********************************************************************************/
	public static final class Units implements BaseColumns {
		
		// Info concernant la table
		public static final String NAME = "units";
		
		//Path pour l'Uri de séléction de toute la table 
		public static final String SELECT_UNITS = "units";
		public static final int SELECT_UNITS_TOKEN = 400;
		
		//Path pour l'Uri de séléction d'un enregistrement
		public static final String SELECT_UNIT_BY_ID = "units/#";
		public static final int SELECT_UNIT_BY_ID_TOKEN = 410;
		
		public static final String INSERT_UNIT = "insert_unit";
		public static final int INSERT_UNIT_TOKEN = 420;
		
		public static final String UPDATE_UNIT = "update_unit";
		public static final int UPDATE_UNIT_TOKEN = 430;
		
		public static final Uri URI_CONTENT_UNITS = BASE_URI.buildUpon().appendPath(SELECT_UNITS).build();
		public static final Uri URI_INSERT_UNIT   = BASE_URI.buildUpon().appendPath(INSERT_UNIT).build();
		public static final Uri URI_UPDATE_UNIT   = BASE_URI.buildUpon().appendPath(UPDATE_UNIT).build();
		
		public static  String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
		public static  String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.Units";

		// Info concernant les colonnes de la table
		public static final class Columns  {
			public static final String ID                = BaseColumns._ID;  
			public static final String TYPE              = "type";
			// type = unitée internationale, Taille ou un conteneur.
			
			public static final String NAME              = "name";  
			
			// obligatoire si c'est une unitée internationale.
			public static final String SYMBOL            = "symbol";
			
			// Obligatoire si c'est un conteneur.
			public static final String CONTAINERFAMILLY  = "containerfamilly";
			// pour les activitées physique on n'affichera que les unités dont la famille est TIME.
			// le gramme, fait partie de la famille WEIGHT.
			
			public static final String LAST_UPDATE       = "last_update";
			
		}

	}
	
	/***********************************************************************************
	 * Table des contenances: Capacities
	 * @author R.DUPUIS
	 *
	 **********************************************************************************/
	public static final class Capacities implements BaseColumns {
		
		// Info concernant la table
		public static final String NAME = "capacities";
		
		//Path pour l'Uri de séléction de toute la table 
		public static final String SELECT_CAPACITIES = "capacities";
		public static final int SELECT_CAPACITIES_TOKEN = 500;
		
		//Path pour l'Uri de séléction d'un enregistrement
		public static final String SELECT_CAPACITY_BY_ID = "capacities/#";
		public static final int SELECT_CAPACITY_BY_ID_TOKEN = 510;
		
		public static final Uri URI_CONTENT_CAPACITIES = BASE_URI.buildUpon().appendPath(SELECT_CAPACITIES).build();
		
		public static  String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
		public static  String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.capacities";

		// Info concernant les colones de la table
		public static final class Columns  {
			public static final String ID                = BaseColumns._ID;  
			public static final String CONTAINERFAMILLY  = "containerfamilly";  // verre
			public static final String CAPACITY          = "capacity";          // 1/2
			public static final String PICTURE           = "picture";           // image 1/2 verre
			
			
			public static final String LAST_UPDATE     = "last_update";
			
		}

	}
	
	
	
	
	
	/***********************************************************************************
	 * Table des plages: Equivalences
	 * @author R.DUPUIS
	 *
	 **********************************************************************************/
	public static final class Equivalences implements BaseColumns {
		
		// Info concernant la table
		public static final String NAME = "equivalences";
		public static final String SEARCH = "search";
		
		//Path pour l'Uri de séléction de toute la table 
		public static final String SELECT_EQUIVALENCES = NAME;
		public static final int SELECT_EQUIVALENCES_TOKEN = 600;
		
		//Path pour l'Uri de séléction d'un enregistrement
		public static final String SELECT_EQUIVALENCE_BY_ID = SELECT_EQUIVALENCES + "/#";
		public static final int SELECT_EQUIVALENCE_BY_ID_TOKEN = 610;
		
		
		//Path pour l'Uri de recherche d'un enregistrement
		public static final String SEARCH_EQUIVALENCE = SELECT_EQUIVALENCES + "/" + SEARCH + "/*";
		public static final int SEARCH_EQUIVALENCE_TOKEN = 620;
				
		//Path pour l'Uri de recherche d'un enregistrement
		public static final String INSERT_EQUIVALENCE = SELECT_EQUIVALENCES + "/" + INSERT ;
		public static final int INSERT_EQUIVALENCE_TOKEN = 630;
	
		//Path pour l'Uri de recherche d'un enregistrement
		public static final String UPDATE_EQUIVALENCE = SELECT_EQUIVALENCES + "/" + UPDATE + "/*";
		public static final int UPDATE_EQUIVALENCE_TOKEN = 640;
	
		public static final Uri URI_CONTENT_EQUIVALENCES    = BASE_URI.buildUpon().appendPath(SELECT_EQUIVALENCES).build();
		public static final Uri URI_SEARCH_EQUIVALENCE      = URI_CONTENT_EQUIVALENCES.buildUpon().appendPath(SEARCH).build();
	    public static final Uri URI_INSERT_EQUIVALENCE = URI_CONTENT_EQUIVALENCES.buildUpon().appendPath(INSERT).build();
		public static final Uri URI_UPDATE_EQUIVALENCE = URI_CONTENT_EQUIVALENCES.buildUpon().appendPath(UPDATE).build();
	
		
		
		public static  String CONTENT_TYPE_DIR  = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
		public static  String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.equivalences";

		/** id    capacity container source -> quantity unit
		 * ----   -------- --------- ------    -------- ----   
		    000   1        H         vélo   -> 500      kcal
		    001   1        bol       soupe  -> 150      Kcal
		    002   1        bol       soupe  -> 100      ml
			003   REF012   nul       baguette
		*
		*/
		
		
		// Info concernant les colones de la table
		public static final class Columns  {
			public static final String ID             = BaseColumns._ID;  
			public static final String FK_ENERGY      = "fk_energy";    // soupe, vélo 
			public static final String FK_UNIT_IN     = "fk_unit_in";   // bol , heures, minutes
			public static final String QUANTITY_OUT   = "quantity_out";
			public static final String FK_UNIT_OUT    = "fk_unit_out";
			public static final String LAST_UPDATE    = "last_update";
			
		}

	}
	
	
	/***********************************************************************************
	 * Table des activités: UserActivities
	 * @author R.DUPUIS
	 *
	 **********************************************************************************/
	public static final class UserActivities implements BaseColumns {
		
		// Info concernant la table
		public static final String NAME = "user_activities";
		public static final Uri URI_BASE_USER_ACTIVITIES = BASE_URI.buildUpon().appendPath(NAME).build();
		
		
		//Path pour l'Uri de séléction de toute la table 
		public static final String SELECT_USER_ACTIVITIES = NAME + "/"+SELECT;
		public static final Uri    URI_SELECT_USER_ACTIVITIES = URI_BASE_USER_ACTIVITIES.buildUpon().appendPath(SELECT).build();
		public static final int    SELECT_USER_ACTIVITIES_TOKEN = 700;
		
		public static final String SELECT_USER_ACTIVITIES_BY_ID = SELECT_USER_ACTIVITIES + "/#";
		public static final int    SELECT_USER_ACTIVITIES_BY_ID_TOKEN = 701;

		public static final String SELECT_USER_ACTIVITIES_BY_TYPE = SELECT_USER_ACTIVITIES + "type/?";
		public static final Uri    URI_SELECT_USER_ACTIVITIES_BY_TYPE = URI_SELECT_USER_ACTIVITIES.buildUpon().appendPath("type").build();
		public static final int    SELECT_USER_ACTIVITIES_BY_TYPE_TOKEN = 702;
		
		public static final String SELECT_USER_ACTIVITIES_BY_DATE = SELECT_USER_ACTIVITIES + "/date/*";
		public static final Uri    URI_SELECT_USER_ACTIVITIES_BY_DATE = URI_SELECT_USER_ACTIVITIES.buildUpon().appendPath("date").build();
		public static final int    SELECT_USER_ACTIVITIES_BY_DATE_TOKEN = 703;
		

		//Path pour l'Uri de création d'un enregistrement
		public static final String INSERT_USER_ACTIVITIES = NAME + "/"+ INSERT;
		public static final int    INSERT_USER_ACTIVITIES_TOKEN = 710;
		
		//Path pour l'Uri de modification d'un enregistrement
		public static final String UPDATE_USER_ACTIVITIES = NAME + "/"+UPDATE;
		public static final int    UPDATE_USER_ACTIVITIES_TOKEN = 720;
		
		public static final String UPDATE_USER_ACTIVITIES_BY_ID = UPDATE_USER_ACTIVITIES + "/#";
		public static final int    UPDATE_USER_ACTIVITIES_BY_ID_TOKEN = 721;

		
		//Path pour l'Uri de supression d'un enregistrement
		public static final String DELETE_USER_ACTIVITIES = NAME + "/"+DELETE;
		public static final int DELETE_USER_ACTIVITIES_TOKEN = 730;
		
		public static final String DELETE_USER_ACTIVITY = DELETE_USER_ACTIVITIES + "/*";
		public static final int DELETE_USER_ACTIVITY_TOKEN = 731;
		
		
		public static final Uri URI_INSERT_USER_ACTIVITIES = URI_BASE_USER_ACTIVITIES.buildUpon().appendPath(INSERT).build();
		public static final Uri URI_UPDATE_USER_ACTIVITIES = URI_BASE_USER_ACTIVITIES.buildUpon().appendPath(UPDATE).build();
		public static final Uri URI_DELETE_USER_ACTIVITIES = URI_BASE_USER_ACTIVITIES.buildUpon().appendPath(DELETE).build();
		
		public static  String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
		public static  String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.useractivities";

		// Info concernant les colones de la table
		public static final class Columns  {
			public static final String ID           = BaseColumns._ID; // on utilise la convention de format de colonne ID indiqué par BaseColumns
			public static final String DATE         = "date";
			public static final String TITLE        = "title";
			public static final String TIMESLOT     = "timeSlot";
			public static final String DURATION     = "duration";
			public static final String TYPE         = "type"; // moving / eating / cooking
			public static final String SCORE        = "score";
			public static final String MNT_ENERGY   = "mnt_energy";
			public static final String MNT_LIPIDS   = "mnt_lipids";
			public static final String MNT_PROTEINS = "mnt_proteins";
			public static final String MNT_GLUCIDS  = "mnt_glucids";
			public static final String PICTURE      = "picture";
			public static final String VITAMINS     = "vitamins";
			public static final String LAST_UPDATE  = "last_update";
			
		}


	}
	
	
	/***********************************************************************************
	 * Vue des aliments consommées.
	 * @author R.DUPUIS
	 *
	 **********************************************************************************/
	public static final class ViewActivityComponent implements BaseColumns {
		
		// Info concernant la table
		public static final String NAME = "view_components";
		
		//Path pour l'Uri de séléction de toute la table 
		public static final String SELECT_VIEW_ACTIVITY_COMPONENTS = "view_components";
		public static final int SELECT_VIEW_ACTIVITY_COMPONENTS_TOKEN = 800;
		
		//Path pour l'Uri de séléction d'un enregistrement
		public static final String SELECT_VIEW_ACTIVITY_COMPONENTS_BY_ID = "view_components/#";
		public static final int SELECT_VIEW_ACTIVITY_COMPONENTS_BY_ID_TOKEN = 810;

		//Path pour l'Uri de séléction des composants d'une activitée
		public static final String SELECT_VIEW_ACTIVITY_COMPONENTS_BY_ACTIVITY = "view_components/activity/#";
		public static final int SELECT_VIEW_ACTIVITY_COMPONENTS_BY_ACTIVITY_TOKEN = 820;
		
		public static final Uri URI_CONTENT_VIEW_ACTIVITY_COMPONENTS = BASE_URI.buildUpon().appendPath(SELECT_VIEW_ACTIVITY_COMPONENTS).build();
		public static final Uri URI_VIEW_ACTIVITY_COMPONENTS_BY_ACTIVITY =URI_CONTENT_VIEW_ACTIVITY_COMPONENTS.buildUpon().appendPath("activity").build();
		
		
		public static  String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
		public static  String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.view_components";

		public static final class Columns  {
			public static final String ID = BaseColumns._ID; // on utilise la convention de format de colonne ID indiqué par BaseColumns
			public static final String ENERGY_ID    = "energy_id";
			public static final String ENERGY_NAME  = "energy_name";
			public static final String QUANTITY     = "quantity";
			public static final String UNIT_ID      = "unit_id";
			public static final String UNIT_NAME    = "unit_name";
			public static final String SCORE        = "score";
			public static final String MNT_ENERGY   = "mnt_energy";
			public static final String MNT_LIPIDS   = "mnt_lipids";
			public static final String MNT_PROTEINS = "mnt_proteins";
			public static final String MNT_GLUCIDS  = "mnt_glucids";
			public static final String VITAMINS     = "vitamins";
			public static final String LAST_UPDATE  = "last_update";
			public static final String PARENT_ID    = "parent_ID";
			
		}
		}

	
	
	/***********************************************************************************
	 * Vue des aliments consommées.
	 * @author R.DUPUIS
	 *
	 **********************************************************************************/
	public static final class ViewEnergies implements BaseColumns {
		
		// Info concernant la table
		public static final String NAME = "view_energies";
		
		//Path pour l'Uri de séléction de toute la table 
		public static final String SELECT_VIEW_ENERGIES = "view_energies";
		public static final int SELECT_VIEW_ENERGIES_TOKEN = 900;
		
		//Path pour l'Uri de séléction d'un enregistrement
		public static final String SELECT_VIEW_ENERGIES_BY_ID = "view_energies/#";
		public static final int SELECT_VIEW_ENERGIES_BY_ID_TOKEN = 910;
		
		public static final Uri URI_CONTENT_VIEW_ENERGIES = BASE_URI.buildUpon().appendPath(SELECT_VIEW_ENERGIES).build();
		
		public static  String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
		public static  String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.view_energies";

		public static final class Columns  {
			public static final String ID = BaseColumns._ID; // on utilise la convention de format de colonne ID indiqué par BaseColumns
			
			public static final String ENERGY_NAME  = "energy_name";
			public static final String QUANTITY     = "quantity";
			public static final String UNIT_ID      = "unit_id";
			public static final String UNIT_NAME    = "unit_name";
			public static final String MNT_ENERGY   = "mnt_energy";
			public static final String MNT_LIPIDS   = "mnt_lipids";
			public static final String MNT_PROTEINS = "mnt_proteins";
			public static final String MNT_GLUCIDS  = "mnt_glucids";
			public static final String VITAMINS     = "vitamins";
			

			
		}
	
	}
	
	
	/***********************************************************************************
	 * Vue des activités: ViewUserActivities
	 * @author R.DUPUIS
	 *
	 **********************************************************************************/
	public static final class ViewUserActivities implements BaseColumns {
		
		// Info concernant la table
		public static final String NAME = "View_user_activities";
		
		//Path pour l'Uri de séléction de toute la table 
		public static final String VIEW_USER_ACTIVITIES = NAME + "/"+SELECT;
		public static final int VIEW_USER_ACTIVITIES_TOKEN = 1000;
		
		public static final String VIEW_USER_ACTIVITIES_BY_ID = NAME + "/*";
		public static final int VIEW_USER_ACTIVITIES_BY_ID_TOKEN = 1001;
		
		public static final Uri URI_VIEW_USER_ACTIVITIES = BASE_URI.buildUpon().appendPath(NAME).build();
		
		
		public static  String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
		public static  String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.viewuseractivities";

		// Info concernant les colones de la table
		public static final class Columns  {
			public static final String ID           = BaseColumns._ID; // on utilise la convention de format de colonne ID indiqué par BaseColumns
		
			public static final String SUM_ENERGY   = "sum_energy";
			public static final String SUM_LIPIDS   = "sum_lipids";
			public static final String SUM_PROTEINS = "sum_proteins";
			public static final String SUM_GLUCIDS  = "sum_glucids";
			
			
			
			
		}


	}
	
	/***********************************************************************************
	 * Vue des activités: ViewDay
	 * @author R.DUPUIS
	 * descriptif de la vue permetant d'avoir une synthèse de la journée.
	 **********************************************************************************/
	public static final class ViewDay implements BaseColumns {
		
		// Info concernant la table
		public static final String NAME = "View_day";
		
		//Path pour l'Uri de séléction de toute la table 
		public static final String VIEW_DAY = NAME + "/"+SELECT;
		public static final int VIEW_DAY_TOKEN = 1100;
		
		public static final String VIEW_DAY_BY_DATE = VIEW_DAY + "/#";
		public static final int VIEW_USER_ACTIVITIES_BY_ID_TOKEN = 1101;
		
		public static final Uri URI_VIEW_DAY_SELECT = BASE_URI.buildUpon().appendPath(VIEW_DAY).build();
		
		
		public static  String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/vnd.rdupuis.amikcal";
		public static  String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.rdupuis.amikcal.viewday";

		// Info concernant les colones de la table
		public static final class Columns  {
			public static final String DATE         = "day";
			public static final String TYPE         = "type";
			public static final String SUM_ENERGY   = "sum_energy";
			public static final String SUM_LIPIDS   = "sum_lipids";
			public static final String SUM_PROTEINS = "sum_proteins";
			public static final String SUM_GLUCIDS  = "sum_glucids";
			
		}


	}
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	


