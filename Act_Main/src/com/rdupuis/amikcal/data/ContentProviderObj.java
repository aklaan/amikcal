package com.rdupuis.amikcal.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.rdupuis.amikcal.data.ContentDescriptorObj.REQUESTS_LIST;
import com.rdupuis.amikcal.data.ContentDescriptorObj.SQL_ORDER;
import com.rdupuis.amikcal.data.ContentDescriptorObj.TB_Party_rel;
import com.rdupuis.amikcal.data.ContentDescriptorObj.TOKEN_MAP;

public class ContentProviderObj extends ContentProvider {
    private DatabaseObj AmikcalDb;

    // private Resources mResources;

    /******************************************************************************
     * A la création de la base de donnée
     *****************************************************************************/
    @Override
    public boolean onCreate() {
	AmikcalDb = new DatabaseObj(getContext());
	// mResources = getContext().getResources();

	return true;

    }

    /******************************************************************************
     * Gestion des getType
     * 
     * ???? je ne sais pas à quoi = ça sert
     *****************************************************************************/
    @Override
    public String getType(Uri uri) {
	final int matchToken = ContentDescriptorObj.URI_MATCHER.match(uri);

	if (matchToken == -1) {
	    throw new IllegalArgumentException("Unknown URL");
	}

	TOKEN_MAP map = new TOKEN_MAP();
	REQUESTS_LIST request = map._in.get(matchToken);

	switch (request) {
	case MIME_ENERGY_DIR:
	    return ContentDescriptorObj.TB_Energies.CONTENT_TYPE_DIR;

	case MIME_ENERGY_TYPE:
	    return ContentDescriptorObj.TB_Energies.CONTENT_ITEM_TYPE;

	default:
	    throw new UnsupportedOperationException("URI " + uri + " is not supported.");
	}

    }

    /******************************************************************************
     * Gestion des INSERT on reçoit une URI et on doit trouver le TOKEN
     *****************************************************************************/

    @Override
    public Uri insert(Uri uri, ContentValues values) {
	SQLiteDatabase db = AmikcalDb.getWritableDatabase();
	int token = ContentDescriptorObj.URI_MATCHER.match(uri);

	TOKEN_MAP map = new TOKEN_MAP();
	REQUESTS_LIST request = map._in.get(token);

	switch (request) {

	case INSERT_ENERGY: {
	    long id = db.insert(ContentDescriptorObj.TB_Energies.TBNAME, null, values);
	    getContext().getContentResolver().notifyChange(uri, null);
	    return ContentDescriptorObj.TB_Energies.INSERT_ENERGY_URI.buildUpon().appendPath(String.valueOf(id))
		    .build();
	}

	case INSERT_UNITY: {
	    // on fait l'insert dans la table units
	    // la fonction update retourne n'Id de l'enregistrement créé.
	    long id = db.insert(ContentDescriptorObj.TB_Units.NAME, null, values);
	    getContext().getContentResolver().notifyChange(uri, null);
	    return ContentDescriptorObj.TB_Units.URI_SELECT_UNIT.buildUpon().appendPath(String.valueOf(id)).build();
	}

	case INSERT_USER_ACTIVITY: {
	    // on fait l'insert dans la table
	    // la fonction update retourne n'Id de l'enregistrement créé.
	    long id = db.insert(ContentDescriptorObj.TB_UserActivities.TBNAME, null, values);
	    getContext().getContentResolver().notifyChange(uri, null);
	    return ContentDescriptorObj.TB_UserActivities.SELECT_USER_ACTIVITY_BY_ID_URI.buildUpon()
		    .appendPath(String.valueOf(id)).build();
	}

	case INSERT_PARTY_REL: {
	    // on fait l'insert dans la table
	    // la fonction update retourne n'Id de l'enregistrement créé.
	    long id = db.insert(ContentDescriptorObj.TB_Party_rel.TBNAME, null, values);
	    getContext().getContentResolver().notifyChange(uri, null);
	    return ContentDescriptorObj.TB_Party_rel.S00_PARTY_REL_URI.buildUpon().appendPath(String.valueOf(id))
		    .build();
	}

	default: {
	    throw new UnsupportedOperationException("URI: " + uri + " pour INSERT non supportée.");
	}
	}
    }

    /*********************************************************************************************
     * Gestion des SELECT
     * 
     * On reçoit une Uri avec un path (chaine de caractère) on utilise le
     * UriMatcher pour trouver la corespondance Path <=> token (integer) une
     * fois que l'on a le token, on sait quel requête à éxécuter.
     * 
     * 
     * Request a specific record. Cursor managedCursor = managedQuery(
     * ContentUris.withAppendedId(Contacts.People.CONTENT_URI, 2), projection,
     * // Which columns to return. null, // WHERE clause. null, // WHERE clause
     * value substitution People.NAME + " ASC"); // Sort order.
     ************************************************************************************************/
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
	// on charge la base de donnée
	SQLiteDatabase db = AmikcalDb.getReadableDatabase();

	final int match = ContentDescriptorObj.URI_MATCHER.match(uri);
	TOKEN_MAP map = new TOKEN_MAP();
	REQUESTS_LIST request = map._in.get(match);

	switch (request) {

	case SELECT_ONE_ENERGY_BY_ID: {
	    SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
	    builder.setTables(ContentDescriptorObj.TB_Energies.TBNAME);
	    String whereClause = ContentDescriptorObj.TB_Energies.Columns.ID + "=" + uri.getLastPathSegment();
	    return builder.query(db, projection, whereClause, null, null, null, sortOrder);
	}

	case SELECT_ALL_ENERGIES: {
	    SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
	    builder.setTables(ContentDescriptorObj.TB_Energies.TBNAME);
	    return builder.query(db, projection, null, null, null, null, sortOrder);
	}

	case SELECT_ENERGIES_LIKE: {
	    SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
	    builder.setTables(ContentDescriptorObj.TB_Energies.TBNAME);
	    String whereClause = ContentDescriptorObj.TB_Energies.Columns.NAME + " like '%" + uri.getLastPathSegment()
		    + "%'";
	    return builder.query(db, projection, whereClause, null, null, null, sortOrder);
	}

	// -------------------------------------------------------------------------
	// Unitées
	// -------------------------------------------------------------------------
	case SELECT_ONE_UNITY_BY_ID: {
	    SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
	    builder.setTables(ContentDescriptorObj.TB_Units.NAME);

	    String whereClause = ContentDescriptorObj.TB_Units.NAME + "." + ContentDescriptorObj.TB_Units.Columns.ID
		    + "=" + uri.getLastPathSegment();
	    return builder.query(db, projection, whereClause, null, null, null, null);
	}

	case SELECT_ALL_UNITS: {
	    SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
	    builder.setTables(ContentDescriptorObj.TB_Units.NAME);
	    return builder.query(db, null, null, null, null, null, null);
	}

	// -------------------------------------------------------------------------
	// User Activity
	// -------------------------------------------------------------------------

	case SELECT_UAC: {
	    SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
	    builder.setTables(ContentDescriptorObj.View_UAC_Data.VIEWNAME);

	    String whereClause = ContentDescriptorObj.View_UAC_Data.VIEWNAME + "."
		    + ContentDescriptorObj.View_UAC_Data.Columns.UAC_ID + "=" + uri.getLastPathSegment();
	    return builder.query(db, projection, whereClause, null, null, null, sortOrder);

	}

	case SELECT_USER_ACTIVITIES_BY_DATE: {

	    SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
	    builder.setTables(ContentDescriptorObj.TB_UserActivities.TBNAME);

	    String whereClause = ContentDescriptorObj.TB_UserActivities.TBNAME + "."
		    + ContentDescriptorObj.TB_UserActivities.Columns.DATE + " LIKE '" + uri.getLastPathSegment() + "%'";

	    // Log.i("where",whereClause);
	    return builder.query(db, projection, whereClause, null, null, null, "datetime("
		    + ContentDescriptorObj.TB_UserActivities.Columns.DATE + ")");

	}

	case SELECT_USER_ACTIVITY: {
	    SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
	    builder.setTables(ContentDescriptorObj.TB_UserActivities.TBNAME);

	    String whereClause = ContentDescriptorObj.TB_UserActivities.TBNAME + "."
		    + ContentDescriptorObj.TB_UserActivities.Columns.ID + "=" + uri.getLastPathSegment();
	    return builder.query(db, projection, whereClause, null, null, null, sortOrder);
	}

	// -------------------------------------------------------------------------
	// Composant de référence
	// -------------------------------------------------------------------------
	case SELECT_COMPONENT_REF_OF_NRJ: {
	    SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
	    builder.setTables(ContentDescriptorObj.View_NRJ_ComponentRef.VIEWNAME);

	    String whereClause = ContentDescriptorObj.View_NRJ_ComponentRef.VIEWNAME + "."
		    + ContentDescriptorObj.View_NRJ_ComponentRef.Columns.ENERGY_ID + "=" + uri.getLastPathSegment();

	    return builder.query(db, projection, whereClause, null, null, null, sortOrder);

	}

	case SEARCH_COMPONENT_REF_RELATION: {

	    SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
	    builder.setTables(ContentDescriptorObj.View_NRJ_ComponentRef.VIEWNAME);

	    String delims = "x";
	    String[] params = uri.getLastPathSegment().split(delims);

	    String whereClause = ContentDescriptorObj.View_NRJ_ComponentRef.VIEWNAME + "."
		    + ContentDescriptorObj.View_NRJ_ComponentRef.Columns.ENERGY_ID + "=" + params[0] + " and ( "
		    + ContentDescriptorObj.View_NRJ_ComponentRef.Columns.QTY_ID + "=" + params[1] + ")";

	    return builder.query(db, projection, whereClause, null, null, null, sortOrder);

	}

	// -------------------------------------------------------------------------
	// QTY
	// -------------------------------------------------------------------------
	case SELECT_QTY_BY_ID: {
	    SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
	    builder.setTables(ContentDescriptorObj.View_Qty.VIEWNAME);

	    String whereClause = ContentDescriptorObj.View_Qty.VIEWNAME + "."
		    + ContentDescriptorObj.View_Qty.Columns.QTY_ID + "=" + uri.getLastPathSegment();

	    return builder.query(db, projection, whereClause, null, null, null, sortOrder);

	}

	case SELECT_ALL_EQUIV_OF_QTY: {
	    SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
	    builder.setTables(ContentDescriptorObj.View_qty_equiv.VIEWNAME);

	    String whereClause = ContentDescriptorObj.View_qty_equiv.VIEWNAME + "."
		    + ContentDescriptorObj.View_qty_equiv.Columns.QTY_ID + "=" + uri.getLastPathSegment();
	    return builder.query(db, projection, whereClause, null, null, null, sortOrder);

	}

	// -------------------------------------------------------------------------
	// Relations
	// -------------------------------------------------------------------------

	case SELECT_PARTY_REL_BY_ID: {
	    SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
	    builder.setTables(ContentDescriptorObj.TB_Party_rel.TBNAME);

	    String whereClause = ContentDescriptorObj.TB_Party_rel.TBNAME + "."
		    + ContentDescriptorObj.TB_Party_rel.Columns.ID + "=" + uri.getLastPathSegment();

	    return builder.query(db, projection, whereClause, null, null, null, sortOrder);

	}

	case SEARCH_RELATION: {

	    SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
	    builder.setTables(ContentDescriptorObj.TB_Party_rel.TBNAME);

	    String delims = "x";
	    String[] params = uri.getLastPathSegment().split(delims);

	    String whereClause = ContentDescriptorObj.TB_Party_rel.TBNAME + "."
		    + ContentDescriptorObj.TB_Party_rel.Columns.REL_TYP_CD + "='" + params[0] + "' and "
		    + ContentDescriptorObj.TB_Party_rel.TBNAME + "."
		    + ContentDescriptorObj.TB_Party_rel.Columns.PARTY_1 + "=" + params[1] + " and "
		    + ContentDescriptorObj.TB_Party_rel.Columns.PARTY_2 + "=" + params[2];

	    return builder.query(db, projection, whereClause, null, null, null, sortOrder);

	}

	case SELECT_ALL_COMPONENT_OF_UA: {
	    SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
	    builder.setTables(ContentDescriptorObj.View_UA_Comp_link.VIEW_NAME);

	    String whereClause = ContentDescriptorObj.View_UA_Comp_link.VIEW_NAME + "."
		    + ContentDescriptorObj.View_UA_Comp_link.Columns.UA_ID + "=" + uri.getLastPathSegment();
	    return builder.query(db, projection, whereClause, null, null, null, sortOrder);
	}

	case SELECT_DB_VERSION: {
	    return db.rawQuery("PRAGMA user_version", null);
	}

	
	case SELECT_ALL_EQUIV_OF_COMPONENT: {
	    SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
	    builder.setTables(ContentDescriptorObj.View_Component_equiv.VIEWNAME);

	    String whereClause = ContentDescriptorObj.View_Component_equiv.VIEWNAME + "."
		    + ContentDescriptorObj.View_Component_equiv.Columns.COMP1_ID + "=" + uri.getLastPathSegment();

	    return builder.query(db, projection, whereClause, null, null, null, sortOrder);

	}

	/*
	 * case ContentDescriptorObj.CustomQuery.SUM_ENERGY_OF_DAY_TOKEN:{
	 * SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
	 * builder.setTables(
	 * 
	 * ContentDescriptorObj.UserActivities.NAME + " INNER JOIN " +
	 * ContentDescriptorObj.ActivityComponent.NAME + " ON " +
	 * ContentDescriptorObj.UserActivities.NAME + "." +
	 * ContentDescriptorObj.UserActivities.Columns.ID + " = " +
	 * ContentDescriptorObj.ActivityComponent.NAME + "." +
	 * ContentDescriptorObj.ActivityComponent.Columns.FK_PARENT);
	 * 
	 * 
	 * 
	 * String[] selectClause = new String[]{"SUM(" +
	 * ContentDescriptorObj.ActivityComponent.NAME + "." +
	 * ContentDescriptorObj.ActivityComponent.Columns.MNT_ENERGY +
	 * ") as SUM_MT_ENERGY"};
	 * 
	 * String whereClause = ContentDescriptorObj.UserActivities.NAME + "."
	 * +ContentDescriptorObj.UserActivities.Columns.DATE +
	 * " LIKE'%"+uri.getLastPathSegment()+"%'"; return builder.query(db,
	 * selectClause , whereClause,null, null, null, sortOrder); }
	 * 
	 * 
	 * case ContentDescriptorObj.Equivalences.SEARCH_EQUIVALENCE_TOKEN:{
	 * SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
	 * builder.setTables(ContentDescriptorObj.Equivalences.NAME);
	 * 
	 * 
	 * String delims = "[+]"; String[] params =
	 * uri.getLastPathSegment().split(delims);
	 * 
	 * //Log.i("search Equivalence ", "FK_ENERGY="+params[0] +
	 * " FK_UNIT_IN=+" + params[1]);
	 * 
	 * String whereClause = ContentDescriptorObj.Equivalences.NAME + "." +
	 * ContentDescriptorObj.Equivalences.Columns.FK_ENERGY + "='" +
	 * params[0] + "'" + " AND " + ContentDescriptorObj.Equivalences.NAME +
	 * "." + ContentDescriptorObj.Equivalences.Columns.FK_UNIT_IN +
	 * "='"+params[1] + "'";
	 * 
	 * return builder.query(db, projection, whereClause,null, null, null,
	 * sortOrder); }
	 */
	default:
	    throw new IllegalArgumentException("URI Select non gérée: " + uri);
	}
    }

    /*****************************************************************************
     * Gestion des UPDATE
     ******************************************************************************/
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

	// Log.i("update URI : ",uri.getEncodedPath());

	SQLiteDatabase db = AmikcalDb.getWritableDatabase();
	int token = ContentDescriptorObj.URI_MATCHER.match(uri);
	TOKEN_MAP map = new TOKEN_MAP();
	REQUESTS_LIST request = map._in.get(token);

	switch (request) {

	case UPDATE_ENERGY: {

	    String whereClause = ContentDescriptorObj.TB_Energies.TBNAME + "."
		    + ContentDescriptorObj.TB_Energies.Columns.ID + "=" + selection;

	    db.update(ContentDescriptorObj.TB_Energies.TBNAME, values, whereClause, null);
	    getContext().getContentResolver().notifyChange(uri, null);
	    return 0;
	}
	case UPDATE_USER_ACTIVITY: {

	    String whereClause = ContentDescriptorObj.TB_UserActivities.TBNAME + "."
		    + ContentDescriptorObj.TB_UserActivities.Columns.ID + "=" + selection;

	    db.update(ContentDescriptorObj.TB_UserActivities.TBNAME, values, whereClause, null);
	    getContext().getContentResolver().notifyChange(uri, null);
	    return 0;
	}

	case UPDATE_UNITY: {

	    String whereClause = ContentDescriptorObj.TB_Units.NAME + "." + ContentDescriptorObj.TB_Units.Columns.ID
		    + "=" + selection;

	    db.update(ContentDescriptorObj.TB_Units.NAME, values, whereClause, null);
	    getContext().getContentResolver().notifyChange(uri, null);
	    return 0;
	}

	case UPDATE_PARTY_REL: {

	    String whereClause = ContentDescriptorObj.TB_Party_rel.TBNAME + "."
		    + ContentDescriptorObj.TB_Party_rel.Columns.ID + "=" + selection;

	    db.update(ContentDescriptorObj.TB_Party_rel.TBNAME, values, whereClause, null);
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

	// Log.i("Delete URI : ",uri.getEncodedPath());

	SQLiteDatabase db = AmikcalDb.getWritableDatabase();
	int token = ContentDescriptorObj.URI_MATCHER.match(uri);
	TOKEN_MAP map = new TOKEN_MAP();
	REQUESTS_LIST request = map._in.get(token);

	switch (request) {
	case DELETE_USER_ACTIVITY: {

	}

	    String whereClause = ContentDescriptorObj.TB_UserActivities.TBNAME + "."
		    + ContentDescriptorObj.TB_UserActivities.Columns.ID + "=" + uri.getLastPathSegment();

	    db.delete(ContentDescriptorObj.TB_UserActivities.TBNAME, whereClause, null);
	    getContext().getContentResolver().notifyChange(uri, null);
	    return 0;
	default:
	    break;
	}

	return 0;
    }
}
