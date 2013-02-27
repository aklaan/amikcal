package com.rdupuis.amikcal.Data;

import com.rdupuis.amikcal.R;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;


public class ContentProviderObj extends ContentProvider {
	private DatabaseObj AmikcalDb;
	private Resources mResources;
	
	/******************************************************************************
	 * A la création de la base de donnée
	 *****************************************************************************/	
	@Override
	public boolean onCreate() {
		AmikcalDb = new DatabaseObj(getContext());
		mResources = getContext().getResources();
		
		return true;
		
	}
	
	 /******************************************************************************
	  * Gestion des getType
	  *****************************************************************************/
	@Override
	public String getType(Uri uri) {
		final int match = ContentDescriptorObj.URI_MATCHER.match(uri);
		
		if (match == -1) {
            throw new IllegalArgumentException("Unknown URL");
        }
		
		switch(match){
		case ContentDescriptorObj.Energies.SELECT_ENERGIES_TOKEN:
			return ContentDescriptorObj.Energies.CONTENT_TYPE_DIR;
			
		case ContentDescriptorObj.Energies.SELECT_ENERGY_BY_ID_TOKEN:
			return ContentDescriptorObj.Energies.CONTENT_ITEM_TYPE;
			
        default:
            throw new UnsupportedOperationException ("URI " + uri + " is not supported.");
		}
	}
	
	 /******************************************************************************
	  * Gestion des INSERT 
	  *****************************************************************************/
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = AmikcalDb.getWritableDatabase();
		int token = ContentDescriptorObj.URI_MATCHER.match(uri);
		switch(token){
			case ContentDescriptorObj.Energies.SELECT_ENERGIES_TOKEN:{
				long id = db.insert(ContentDescriptorObj.Energies.NAME, null, values);
				getContext().getContentResolver().notifyChange(uri, null);
				return ContentDescriptorObj.Energies.URI_CONTENT_ENERGIES.buildUpon().appendPath(String.valueOf(id)).build();
			}
		
			case ContentDescriptorObj.ActivityComponent.INSERT_ACTIVITY_COMPONENT_TOKEN:{
				// on fait l'insert dans la table "component
				// la fonction retourne n'Id de l'enregistrement créé.
				long id = db.insert(ContentDescriptorObj.ActivityComponent.NAME, null, values);
				getContext().getContentResolver().notifyChange(uri, null);
				return ContentDescriptorObj.ActivityComponent.URI_SELECT_ACTIVITY_COMPONENTS.buildUpon().appendPath(String.valueOf(id)).build();
			}
			
			case ContentDescriptorObj.Units.INSERT_UNIT_TOKEN:{
				// on fait l'insert dans la table units
				// la fonction update retourne n'Id de l'enregistrement créé.
				long id = db.insert(ContentDescriptorObj.Units.NAME, null, values);
				getContext().getContentResolver().notifyChange(uri, null);
				return ContentDescriptorObj.Units.URI_CONTENT_UNITS.buildUpon().appendPath(String.valueOf(id)).build();
			}
			
			
			case ContentDescriptorObj.Energies.INSERT_ENERGY_TOKEN:{
				// on fait l'insert dans la table energies
				// la fonction update retourne n'Id de l'enregistrement créé.
				long id = db.insert(ContentDescriptorObj.Energies.NAME, null, values);
				getContext().getContentResolver().notifyChange(uri, null);
				return ContentDescriptorObj.Energies.URI_CONTENT_ENERGIES.buildUpon().appendPath(String.valueOf(id)).build();
			}
			
			
			case ContentDescriptorObj.UserActivities.INSERT_USER_ACTIVITIES_TOKEN:{
				// on fait l'insert dans la table 
				// la fonction update retourne n'Id de l'enregistrement créé.
				long id = db.insert(ContentDescriptorObj.UserActivities.NAME, null, values);
				getContext().getContentResolver().notifyChange(uri, null);
				return ContentDescriptorObj.UserActivities.URI_SELECT_USER_ACTIVITIES.buildUpon().appendPath(String.valueOf(id)).build();
			}
			
			case ContentDescriptorObj.Equivalences.INSERT_EQUIVALENCE_TOKEN:{
				// on fait l'insert dans la table 
				// la fonction update retourne n'Id de l'enregistrement créé.
				long id = db.insert(ContentDescriptorObj.Equivalences.NAME, null, values);
				getContext().getContentResolver().notifyChange(uri, null);
				return ContentDescriptorObj.Equivalences.URI_CONTENT_EQUIVALENCES.buildUpon().appendPath(String.valueOf(id)).build();
			}
			
            default: {
                throw new UnsupportedOperationException("URI: " + uri + " pour UPDATE non supportée.");
            }
		}
	}

	
	
	/*********************************************************************************************
	 * Gestion des SELECT
	 * 
	 * Request a specific record.
          Cursor managedCursor = managedQuery(
                ContentUris.withAppendedId(Contacts.People.CONTENT_URI, 2),
                projection,    // Which columns to return.
                null,          // WHERE clause.
                null,          // WHERE clause value substitution
                People.NAME + " ASC");   // Sort order.

	 ************************************************************************************************/
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		//on charge la base de donnée
		SQLiteDatabase db = AmikcalDb.getReadableDatabase();
		
		final int match = ContentDescriptorObj.URI_MATCHER.match(uri);
		 // Log.i("Query Match token code", String.valueOf(match));
		switch(match){
			// retrieve Amikcal list
			case ContentDescriptorObj.ActivityComponent.SELECT_ACTIVITY_COMPONENTS_TOKEN:{
				SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
				builder.setTables(ContentDescriptorObj.ActivityComponent.NAME);
				return builder.query(db, null, null, null, null, null, null);
			}
			
			case ContentDescriptorObj.ActivityComponent.SELECT_ACTIVITY_COMPONENTS_BY_ID_TOKEN:{
				SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
				builder.setTables(ContentDescriptorObj.ActivityComponent.NAME);
				String whereClause =  ContentDescriptorObj.ActivityComponent.Columns.ID + "=" +selection;
				return builder.query(db, projection, whereClause,null, null, null, sortOrder);
			}
			
			
			case ContentDescriptorObj.Energies.SELECT_ENERGY_BY_ID_TOKEN:{
				SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
				builder.setTables(ContentDescriptorObj.Energies.NAME);
				String whereClause =  ContentDescriptorObj.Energies.Columns.ID + "=" +uri.getLastPathSegment();
				return builder.query(db, projection, whereClause,null, null, null, sortOrder);
			}
			
			case ContentDescriptorObj.Energies.SELECT_ENERGIES_TOKEN:{
				SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
				builder.setTables(ContentDescriptorObj.Energies.NAME);
				return builder.query(db, projection, null,null, null, null, sortOrder);
			}
			
			
			case ContentDescriptorObj.Energies.SELECT_ENERGIES_LIKE_TOKEN:{
				SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
				builder.setTables(ContentDescriptorObj.Energies.NAME);
				String whereClause =  ContentDescriptorObj.Energies.Columns.NAME + " like '%" +uri.getLastPathSegment()+"%'";
				return builder.query(db, projection, whereClause,null, null, null, sortOrder);
			}
			
			
			case ContentDescriptorObj.ViewActivityComponent.SELECT_VIEW_ACTIVITY_COMPONENTS_TOKEN:{
				SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
				builder.setTables(ContentDescriptorObj.ViewActivityComponent.NAME);
				return builder.query(db, null, null, null, null, null, null);
			}
			
			case ContentDescriptorObj.ViewActivityComponent.SELECT_VIEW_ACTIVITY_COMPONENTS_BY_ID_TOKEN:{
				SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
				builder.setTables(ContentDescriptorObj.ViewActivityComponent.NAME);
				
				String whereClause =  
						ContentDescriptorObj.ViewActivityComponent.NAME 
						+ "." + ContentDescriptorObj.ViewActivityComponent.Columns.ID + "=" +selection;
				return builder.query(db, projection, whereClause,null, null, null, sortOrder);
			}
			
			case ContentDescriptorObj.ViewActivityComponent.SELECT_VIEW_ACTIVITY_COMPONENTS_BY_ACTIVITY_TOKEN:{
				SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
				builder.setTables(ContentDescriptorObj.ViewActivityComponent.NAME);
				
				String whereClause =  
						ContentDescriptorObj.ViewActivityComponent.NAME 
						+ "." + ContentDescriptorObj.ViewActivityComponent.Columns.PARENT_ID + "=" +uri.getLastPathSegment() ;
				return builder.query(db, projection, whereClause,null, null, null, sortOrder);
			}
			
			case ContentDescriptorObj.Units.SELECT_UNITS_TOKEN:{
				SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
				builder.setTables(ContentDescriptorObj.Units.NAME);
				return builder.query(db, null, null, null, null, null, null);
			}
			
			
			case ContentDescriptorObj.Units.SELECT_UNIT_BY_ID_TOKEN:{
				SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
				builder.setTables(ContentDescriptorObj.Units.NAME);
				
				String whereClause =  
						ContentDescriptorObj.Units.NAME 
						+ "." + ContentDescriptorObj.Units.Columns.ID + "=" +uri.getLastPathSegment() ;
				return builder.query(db, projection, whereClause, null, null, null, null);
			}
		
			

			case ContentDescriptorObj.ViewEnergies.SELECT_VIEW_ENERGIES_BY_ID_TOKEN:{
				SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
				builder.setTables(ContentDescriptorObj.ViewEnergies.NAME);
				
				String whereClause =  
						ContentDescriptorObj.ViewEnergies.NAME 
						+ "." + ContentDescriptorObj.ViewEnergies.Columns.ID + "=" +selection;
				return builder.query(db, projection, whereClause,null, null, null, sortOrder);
			}			
			
			
			case ContentDescriptorObj.UserActivities.SELECT_USER_ACTIVITIES_BY_DATE_TOKEN:{
				
				
				SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
				builder.setTables(ContentDescriptorObj.UserActivities.NAME);
				
				String whereClause =  
						ContentDescriptorObj.UserActivities.NAME 
						+ "." + ContentDescriptorObj.UserActivities.Columns.DATE + " LIKE '" +uri.getLastPathSegment() + "%'";
			
				//Log.i("where",whereClause);
				return builder.query(db, projection, whereClause,null, null, null, "datetime("+ContentDescriptorObj.UserActivities.Columns.DATE + ")");
			
			}			

			case ContentDescriptorObj.UserActivities.SELECT_USER_ACTIVITIES_BY_ID_TOKEN:{
				SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
				builder.setTables(ContentDescriptorObj.UserActivities.NAME);
				
				String whereClause =  
						ContentDescriptorObj.UserActivities.NAME 
						+ "." + ContentDescriptorObj.UserActivities.Columns.ID + "=" +uri.getLastPathSegment() ;
				return builder.query(db, projection, whereClause,null, null, null, sortOrder);
			}			
			
			case ContentDescriptorObj.UserActivities.SELECT_USER_ACTIVITIES_TOKEN:{
				SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
				builder.setTables(ContentDescriptorObj.UserActivities.NAME);
				
				
				// on ne séléctionne que les types 1 à 3 pour écarter les recettes
				String whereClause =  
						ContentDescriptorObj.UserActivities.NAME 
						+ "." + ContentDescriptorObj.UserActivities.Columns.TYPE + "<=3";
				return builder.query(db, projection, whereClause,null, null, null, sortOrder);
			}			
			
			
			case ContentDescriptorObj.ViewUserActivities.VIEW_USER_ACTIVITIES_BY_ID_TOKEN:{
				SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
				builder.setTables(ContentDescriptorObj.ViewUserActivities.NAME);
				
				String whereClause =  
						ContentDescriptorObj.ViewUserActivities.NAME 
						+ "." + ContentDescriptorObj.ViewUserActivities.Columns.ID + "='" +uri.getLastPathSegment() + "'";
				return builder.query(db, projection, whereClause,null, null, null, sortOrder);
			}
			
			
			case ContentDescriptorObj.Equivalences.SELECT_EQUIVALENCE_BY_ID_TOKEN:{
				SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
				builder.setTables(ContentDescriptorObj.Equivalences.NAME);
				
				String whereClause =  
						ContentDescriptorObj.Equivalences.NAME 
						+ "." + ContentDescriptorObj.Equivalences.Columns.ID + "='" +uri.getLastPathSegment() + "'";
				return builder.query(db, projection, whereClause,null, null, null, sortOrder);
			}
			
			
			case ContentDescriptorObj.Equivalences.SELECT_EQUIVALENCES_TOKEN:{
				SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
				builder.setTables(ContentDescriptorObj.Equivalences.NAME);
				return builder.query(db, projection, null,null, null, null, sortOrder);
			}
			
			
			
			case ContentDescriptorObj.CustomQuery.DB_VERSION_TOKEN:{
				return db.rawQuery("PRAGMA user_version", null);
			}
			
			case ContentDescriptorObj.CustomQuery.LAST_WEIGHT_FROM_TOKEN:{
			
				SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
				builder.setTables(ContentDescriptorObj.UserActivities.NAME);
				
				/*=========================================================
				 * SELECT (useractivities.title)
				 * where date(useractivities.date) <='2012-08-12'
				 =========================================================*/
				String[] selectClause = new String[]{"("
						+ ContentDescriptorObj.UserActivities.NAME + "."
						+ ContentDescriptorObj.UserActivities.Columns.TITLE + ") as maxweight"};
			

				String whereClause = 
						ContentDescriptorObj.UserActivities.NAME + "."
						+ ContentDescriptorObj.UserActivities.Columns.TYPE
						+ "= " + String.valueOf(mResources.getInteger(R.integer.ACTIVITY_WEIGHT)) +
						" AND DATE("+ ContentDescriptorObj.UserActivities.NAME + "."
				               + ContentDescriptorObj.UserActivities.Columns.DATE
						   + ") <= '"+uri.getLastPathSegment()+"'";
				
				String sortorder=
						ContentDescriptorObj.UserActivities.NAME + "."
								+ ContentDescriptorObj.UserActivities.Columns.DATE
								+ " DESC";
						
				
				
			//Log.i("where",whereClause + sortorder);
			
				return builder.query(db, selectClause , whereClause,null, null, null, sortorder);
			}
			
			
			case ContentDescriptorObj.CustomQuery.USED_UNITS_FOR_ENERGY_TOKEN:{
				
				SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
				builder.setTables(ContentDescriptorObj.Units.NAME);
				
				/*=========================================================
				 * SELECT * FROM units as t1
				 * where exists (select 1 from components as t2 where t2. fk_energy=1 and t2.fk_unit =t1._id); 
				 * 
				 * where date(useractivities.date) <='2012-08-12'
				 =========================================================*/

				
				String whereClause = 
						
						"EXISTS (SELECT 1 FROM "  
						+ ContentDescriptorObj.ActivityComponent.NAME
						+ " WHERE "
						+ ContentDescriptorObj.ActivityComponent.NAME
						+ "."
						+ ContentDescriptorObj.ActivityComponent.Columns.FK_ENERGY
						+"="
						+uri.getLastPathSegment()
						+ " AND "
						+ ContentDescriptorObj.ActivityComponent.NAME
						+ "."
						+ ContentDescriptorObj.ActivityComponent.Columns.FK_UNIT
						+ "="
						+ ContentDescriptorObj.Units.NAME
						+ "."
						+ ContentDescriptorObj.Units.Columns.ID
						+ ")";
						
						
			
				return builder.query(db, projection , whereClause,null, null, null, null);
			}
			
			
			case ContentDescriptorObj.CustomQuery.SUM_ENERGY_OF_DAY_TOKEN:{
				SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
				builder.setTables(
				
  				  ContentDescriptorObj.UserActivities.NAME
				+ " INNER JOIN " 
				+ ContentDescriptorObj.ActivityComponent.NAME
				+ " ON " 
				+ ContentDescriptorObj.UserActivities.NAME + "."
				+ ContentDescriptorObj.UserActivities.Columns.ID
				+ " = "
				+ ContentDescriptorObj.ActivityComponent.NAME + "."
				+ ContentDescriptorObj.ActivityComponent.Columns.FK_PARENT);
						
						
				
				String[] selectClause = new String[]{"SUM("
						+ ContentDescriptorObj.ActivityComponent.NAME + "."
						+ ContentDescriptorObj.ActivityComponent.Columns.MNT_ENERGY + ") as SUM_MT_ENERGY"};
				
				String whereClause = 
						ContentDescriptorObj.UserActivities.NAME + "."
				       +ContentDescriptorObj.UserActivities.Columns.DATE
						+ " LIKE'%"+uri.getLastPathSegment()+"%'";
				return builder.query(db, selectClause , whereClause,null, null, null, sortOrder);
			}
			
			
			case ContentDescriptorObj.Equivalences.SEARCH_EQUIVALENCE_TOKEN:{
				SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
				builder.setTables(ContentDescriptorObj.Equivalences.NAME);
			
				
				String delims = "[+]";
				String[] params = uri.getLastPathSegment().split(delims);
				
				//Log.i("search Equivalence ", "FK_ENERGY="+params[0] + " FK_UNIT_IN=+" + params[1]);
				
				String whereClause =  
						  ContentDescriptorObj.Equivalences.NAME + "."
						+ ContentDescriptorObj.Equivalences.Columns.FK_ENERGY + "='" + params[0] + "'"
						+ " AND " 
						+ ContentDescriptorObj.Equivalences.NAME + "."
						+ ContentDescriptorObj.Equivalences.Columns.FK_UNIT_IN + "='"+params[1] + "'";
				
				return builder.query(db, projection, whereClause,null, null, null, sortOrder);
			}
			
			default: throw new IllegalArgumentException("URI Select non gérée: " + uri);
		}
	}

		
	 /*****************************************************************************
	 * Gestion des UPDATE
	 ******************************************************************************/
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		
		//Log.i("update URI : ",uri.getEncodedPath());
		
		SQLiteDatabase db = AmikcalDb.getWritableDatabase();
		int token = ContentDescriptorObj.URI_MATCHER.match(uri);
		switch(token){
			case ContentDescriptorObj.ActivityComponent.UPDATE_ACTIVITY_COMPONENT_TOKEN:{
				
				String whereClause = 
						ContentDescriptorObj.ActivityComponent.NAME 
						+ "." + ContentDescriptorObj.ActivityComponent.Columns.ID + "=" +selection;
				
				db.update(ContentDescriptorObj.ActivityComponent.NAME, values, whereClause,null);
				getContext().getContentResolver().notifyChange(uri, null);
				return 0;
			}
			case ContentDescriptorObj.Energies.UPDATE_ENERGY_TOKEN:{
				
				String whereClause = 
						ContentDescriptorObj.Energies.NAME 
						+ "." + ContentDescriptorObj.Energies.Columns.ID + "=" +selection;
				
				db.update(ContentDescriptorObj.Energies.NAME, values, whereClause,null);
				getContext().getContentResolver().notifyChange(uri, null);
				return 0;
			}
			case ContentDescriptorObj.UserActivities.UPDATE_USER_ACTIVITIES_BY_ID_TOKEN:{
				
				String whereClause = 
						ContentDescriptorObj.UserActivities.NAME 
						+ "." + ContentDescriptorObj.UserActivities.Columns.ID + "=" +selection;
				
				db.update(ContentDescriptorObj.UserActivities.NAME, values, whereClause,null);
				getContext().getContentResolver().notifyChange(uri, null);
				return 0;
			}
			
			case ContentDescriptorObj.Units.UPDATE_UNIT_TOKEN:{
				
				String whereClause = 
						ContentDescriptorObj.Units.NAME 
						+ "." + ContentDescriptorObj.Units.Columns.ID + "=" +selection;
				
				db.update(ContentDescriptorObj.Units.NAME, values, whereClause,null);
				getContext().getContentResolver().notifyChange(uri, null);
				return 0;
			}
			
			case ContentDescriptorObj.Equivalences.UPDATE_EQUIVALENCE_TOKEN:{
				
				String whereClause = 
						ContentDescriptorObj.Equivalences.NAME 
						+ "." + ContentDescriptorObj.Equivalences.Columns.ID + "=" + "='" +uri.getLastPathSegment() + "'";
				
				db.update(ContentDescriptorObj.Equivalences.NAME, values, whereClause,null);
				getContext().getContentResolver().notifyChange(uri, null);
				return 0;
			}
			default: {
                throw new UnsupportedOperationException("URI: " + uri + " pour UPDATE non supportée.");
            }
		
	}
	
	}
	
	
	 /******************************************************************************
	  * Gestion des DELETE
	  *****************************************************************************/
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		
		
//Log.i("Delete URI : ",uri.getEncodedPath());
		
		SQLiteDatabase db = AmikcalDb.getWritableDatabase();
		int token = ContentDescriptorObj.URI_MATCHER.match(uri);
		switch(token){
			case ContentDescriptorObj.UserActivities.DELETE_USER_ACTIVITY_TOKEN:{
				
			}
				
				String whereClause = 
						ContentDescriptorObj.UserActivities.NAME 
						+ "." + ContentDescriptorObj.UserActivities.Columns.ID + "=" +uri.getLastPathSegment();
				
				db.delete(ContentDescriptorObj.UserActivities.NAME, whereClause,null);
				getContext().getContentResolver().notifyChange(uri, null);
				return 0;
			}
		
		
		
		
		
		return 0;
	}
}
