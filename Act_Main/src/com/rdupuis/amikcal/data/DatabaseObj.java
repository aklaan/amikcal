package com.rdupuis.amikcal.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseObj extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "amikcal.db";
	public static final int DATABASE_VERSION = 1;

	public DatabaseObj(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// **********************************************************************************************
		// Création de la table des unitées
		// **********************************************************************************************
		db.execSQL("CREATE TABLE " + ContentDescriptorObj.TB_Units.NAME + " ( "
				+ ContentDescriptorObj.TB_Units.Columns.ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ ContentDescriptorObj.TB_Units.Columns.CLASS + " TEXT, "
				+ ContentDescriptorObj.TB_Units.Columns.LONG_NAME + " TEXT, "
				+ ContentDescriptorObj.TB_Units.Columns.SHORT_NAME + " TEXT, "
				+ ContentDescriptorObj.TB_Units.Columns.CONTAINERFAMILLY
				+ " TEXT, " + ContentDescriptorObj.TB_Units.Columns.LAST_UPDATE
				+ " DATETIME, " + "UNIQUE ("
				+ ContentDescriptorObj.TB_Units.Columns.ID
				+ ") ON CONFLICT REPLACE)");

		// **********************************************************************************************
		// Création de la table des contenances
		// **********************************************************************************************
		db.execSQL("CREATE TABLE " + ContentDescriptorObj.TB_Capacities.NAME
				+ " ( " + ContentDescriptorObj.TB_Capacities.Columns.ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ ContentDescriptorObj.TB_Capacities.Columns.CONTAINERFAMILLY
				+ " TEXT, "
				+ ContentDescriptorObj.TB_Capacities.Columns.CAPACITY
				+ " TEXT, "
				+ ContentDescriptorObj.TB_Capacities.Columns.PICTURE
				+ " TEXT, "
				+ ContentDescriptorObj.TB_Capacities.Columns.LAST_UPDATE
				+ " DATETIME, " + "UNIQUE ("
				+ ContentDescriptorObj.TB_Capacities.Columns.ID
				+ ") ON CONFLICT REPLACE)");

		/**
		 * ====================================================================
		 * Création de la table des ENERGIES
		 * 
		 * (table des sources permettant d'absorber ou de brûler de l'énergie
		 * 
		 * ====================================================================
		 */
		db.execSQL("CREATE TABLE " + ContentDescriptorObj.TB_Energies.NAME
				+ " ( " + ContentDescriptorObj.TB_Energies.Columns.ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "

				+ ContentDescriptorObj.TB_Energies.Columns.EFFECT
				+ " INTEGER, "

				+ ContentDescriptorObj.TB_Energies.Columns.NAME
				+ " TEXT, "
				// Top liquide Y/N
				+ ContentDescriptorObj.TB_Energies.Columns.STRUCTURE
				+ " INTERGER, "

				+ ContentDescriptorObj.TB_Energies.Columns.LAST_UPDATE
				+ " DATETIME, "

				+ "UNIQUE (" + ContentDescriptorObj.TB_Energies.Columns.ID
				+ ") ON CONFLICT REPLACE)");

		// **********************************************************************************************
		// Création de la table des relations entre entitées
		// **********************************************************************************************
		db.execSQL("CREATE TABLE " + ContentDescriptorObj.TB_Party_rel.NAME
				+ " ( " + ContentDescriptorObj.TB_Party_rel.Columns.ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ ContentDescriptorObj.TB_Party_rel.Columns.REL_TYP_CD
				+ " BYTE, " + ContentDescriptorObj.TB_Party_rel.Columns.PARTY_1
				+ " STRING, "
				+ ContentDescriptorObj.TB_Party_rel.Columns.PARTY_2
				+ " STRING, "
				+ ContentDescriptorObj.TB_Party_rel.Columns.LAST_UPDATE
				+ " DATETIME, " +

				"UNIQUE (" + ContentDescriptorObj.TB_Party_rel.Columns.ID
				+ ") ON CONFLICT REPLACE)");

		// **********************************************************************************************
		// Création de la table des UserActivities
		// Repas / pesées / activité physiques
		// **********************************************************************************************
		db.execSQL("CREATE TABLE "
				+ ContentDescriptorObj.TB_UserActivities.NAME + " ( "
				+ ContentDescriptorObj.TB_UserActivities.Columns.ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "

				+ ContentDescriptorObj.TB_UserActivities.Columns.CLASS
				+ " INTEGER, "

				+ ContentDescriptorObj.TB_UserActivities.Columns.DATE
				+ " DATETIME, "

				+ ContentDescriptorObj.TB_UserActivities.Columns.TITLE
				+ " STRING, "

				+ ContentDescriptorObj.TB_UserActivities.Columns.LAST_UPDATE
				+ " DATETIME, " +

				"UNIQUE (" + ContentDescriptorObj.TB_UserActivities.Columns.ID
				+ ") ON CONFLICT REPLACE)");

		/**
		 * ====================================================================
		 * 
		 * 
		 * CREATION DES VUES
		 * 
		 * 
		 * ====================================================================
		 * 
		 * */

		/**
		 * *********************************************************************
		 * ************************* Création de la vue
		 *
		 * select energy.id rel_nrj_qty.id rel_qty.id rel_qty.party1
		 * rel_qty.party2
		 * 
		 * from energies inner join party_rel as rel_nrj_qty on energies.id =
		 * rel_nrj_qty.id and rel_nrj_qty.rel_typ_cd = 0x00 inner join party_rel
		 * as rel_qty
		 *
		 **********************************************************************************************/

		db.execSQL("CREATE VIEW "
				+ ContentDescriptorObj.View_NRJ_Qty_link.NAME

				+ " AS SELECT "
				+ ContentDescriptorObj.TB_Energies.NAME
				+ "."
				+ ContentDescriptorObj.TB_Energies.Columns.ID
				+ " AS "
				+ ContentDescriptorObj.View_NRJ_Qty_link.Columns.ENERGY_ID

				+ ","
				+ "rel_nrj_qty."
				+ ContentDescriptorObj.TB_Party_rel.Columns.ID
				+ " AS "
				+ ContentDescriptorObj.View_NRJ_Qty_link.Columns.LINK_NRJ_QTY_ID

				+ ","
				+ "rel_nrj_qty."
				+ ContentDescriptorObj.TB_Party_rel.Columns.ID

				+ " AS "
				+ ContentDescriptorObj.View_NRJ_Qty_link.Columns.QTY_ID

				+ " FROM "
				+ ContentDescriptorObj.TB_Energies.NAME
				// --------------------------------------------------------
				+ " INNER JOIN "
				+ ContentDescriptorObj.TB_Party_rel.NAME
				+ " AS rel_nrj_qty "

				+ " ON "
				+ ContentDescriptorObj.TB_Energies.NAME
				+ "."
				+ ContentDescriptorObj.TB_Energies.Columns.ID

				+ " = rel_nrj_qty."
				+ ContentDescriptorObj.TB_Party_rel.Columns.PARTY_1
				+ ")"

		);

	}

	/*
	 * db.execSQL("CREATE VIEW "
	 * 
	 * + ContentDescriptorObj.ViewActivityComponent.NAME + " AS SELECT "
	 * 
	 * + ContentDescriptorObj.ActivityComponent.NAME + "." +
	 * ContentDescriptorObj.ActivityComponent.Columns.ID + " AS " +
	 * ContentDescriptorObj.ViewActivityComponent.Columns.ID
	 * 
	 * // ---------------------------------------------------------- + "," +
	 * ContentDescriptorObj.Energies.NAME + "." +
	 * ContentDescriptorObj.Energies.Columns.ID + " AS " +
	 * ContentDescriptorObj.ViewActivityComponent.Columns.ENERGY_ID
	 * 
	 * // ---------------------------------------------------------- + "," +
	 * ContentDescriptorObj.Energies.NAME + "." +
	 * ContentDescriptorObj.Energies.Columns.NAME + " AS " +
	 * ContentDescriptorObj.ViewActivityComponent.Columns.ENERGY_NAME //
	 * ---------------------------------------------------------- + "," +
	 * ContentDescriptorObj.ActivityComponent.NAME + "." +
	 * ContentDescriptorObj.ActivityComponent.Columns.QUANTITY + " AS " +
	 * ContentDescriptorObj.ViewActivityComponent.Columns.QUANTITY
	 * 
	 * // ---------------------------------------------------------- + "," +
	 * ContentDescriptorObj.Units.NAME + "." +
	 * ContentDescriptorObj.Units.Columns.ID + " AS " +
	 * ContentDescriptorObj.ViewActivityComponent.Columns.UNIT_ID
	 * 
	 * // ---------------------------------------------------------- + "," +
	 * ContentDescriptorObj.Units.NAME + "." +
	 * ContentDescriptorObj.Units.Columns.NAME + " AS " +
	 * ContentDescriptorObj.ViewActivityComponent.Columns.UNIT_NAME
	 * 
	 * // ---------------------------------------------------------- + "," +
	 * ContentDescriptorObj.ActivityComponent.NAME + "." +
	 * ContentDescriptorObj.ActivityComponent.Columns.SCORE + " AS " +
	 * ContentDescriptorObj.ViewActivityComponent.Columns.SCORE
	 * 
	 * // ---------------------------------------------------------- + "," +
	 * ContentDescriptorObj.ActivityComponent.NAME + "." +
	 * ContentDescriptorObj.ActivityComponent.Columns.MNT_ENERGY + " AS " +
	 * ContentDescriptorObj.ViewActivityComponent.Columns.MNT_ENERGY
	 * 
	 * // ---------------------------------------------------------- + "," +
	 * ContentDescriptorObj.ActivityComponent.NAME + "." +
	 * ContentDescriptorObj.ActivityComponent.Columns.MNT_GLUCIDS + " AS " +
	 * ContentDescriptorObj.ViewActivityComponent.Columns.MNT_GLUCIDS
	 * 
	 * // ---------------------------------------------------------- + "," +
	 * ContentDescriptorObj.ActivityComponent.NAME + "." +
	 * ContentDescriptorObj.ActivityComponent.Columns.MNT_LIPIDS + " AS " +
	 * ContentDescriptorObj.ViewActivityComponent.Columns.MNT_LIPIDS
	 * 
	 * // ---------------------------------------------------------- + "," +
	 * ContentDescriptorObj.ActivityComponent.NAME + "." +
	 * ContentDescriptorObj.ActivityComponent.Columns.MNT_PROTEINS + " AS " +
	 * ContentDescriptorObj.ViewActivityComponent.Columns.MNT_PROTEINS
	 * 
	 * // ---------------------------------------------------------- + "," +
	 * ContentDescriptorObj.ActivityComponent.NAME + "." +
	 * ContentDescriptorObj.ActivityComponent.Columns.VITAMINS + " AS " +
	 * ContentDescriptorObj.ViewActivityComponent.Columns.VITAMINS
	 * 
	 * // ---------------------------------------------------------- + "," +
	 * ContentDescriptorObj.ActivityComponent.NAME + "." +
	 * ContentDescriptorObj.ActivityComponent.Columns.FK_PARENT + " AS " +
	 * ContentDescriptorObj.ViewActivityComponent.Columns.PARENT_ID //
	 * ----------------------------------------------------------
	 * 
	 * // ---------------------------------------------------------- + "," +
	 * ContentDescriptorObj.ActivityComponent.NAME + "." +
	 * ContentDescriptorObj.ActivityComponent.Columns.LAST_UPDATE + " AS " +
	 * ContentDescriptorObj.ViewActivityComponent.Columns.LAST_UPDATE //
	 * ----------------------------------------------------------
	 * 
	 * + " FROM " + ContentDescriptorObj.ActivityComponent.NAME + " AS " +
	 * ContentDescriptorObj.ActivityComponent.NAME + " INNER JOIN " +
	 * ContentDescriptorObj.Energies.NAME + " AS " +
	 * ContentDescriptorObj.Energies.NAME + " ON " +
	 * ContentDescriptorObj.ActivityComponent.NAME + "." +
	 * ContentDescriptorObj.ActivityComponent.Columns.FK_ENERGY + " = " +
	 * ContentDescriptorObj.Energies.NAME + "." +
	 * ContentDescriptorObj.Energies.Columns.ID
	 * 
	 * + " INNER JOIN " + ContentDescriptorObj.Units.NAME + " AS " +
	 * ContentDescriptorObj.Units.NAME + " ON " +
	 * ContentDescriptorObj.ActivityComponent.NAME + "." +
	 * ContentDescriptorObj.ActivityComponent.Columns.FK_UNIT + " = " +
	 * ContentDescriptorObj.Units.NAME + "." +
	 * ContentDescriptorObj.Units.Columns.ID);
	 */

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		Log.i("upd db to version", String.valueOf(DATABASE_VERSION));

		if (oldVersion < newVersion) {

			for (int i = 1; i <= newVersion; i++) {

				switch (i) {

				case 2:
					oldVersion = i;
					break;

				case 3:
					oldVersion = i;
					break;

				case 4:
					doUpgradeOfVersion4(db);
					oldVersion = i;
					break;

				default:
					break;
				}
			}
		}
	}

	private void doUpgradeOfVersion4(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS WORKINGTB");
		/*
		 * db.execSQL("CREATE TABLE WORKINGTB ( " +
		 * ContentDescriptorObj.Units.Columns.ID +
		 * " INTEGER PRIMARY KEY AUTOINCREMENT, " +
		 * ContentDescriptorObj.Units.Columns.TYPE + " TEXT, " +
		 * ContentDescriptorObj.Units.Columns.NAME + " TEXT, " +
		 * ContentDescriptorObj.Units.Columns.SYMBOL + " TEXT, " +
		 * ContentDescriptorObj.Units.Columns.CONTAINERFAMILLY + " TEXT, " +
		 * ContentDescriptorObj.Units.Columns.LAST_UPDATE + " DATETIME, " +
		 * "UNIQUE (" + ContentDescriptorObj.Units.Columns.ID +
		 * ") ON CONFLICT REPLACE)");
		 * 
		 * db.execSQL("INSERT INTO WORKINGTB SELECT " +
		 * ContentDescriptorObj.Units.Columns.ID + "," +
		 * ContentDescriptorObj.Units.Columns.TYPE + "," +
		 * ContentDescriptorObj.Units.Columns.NAME + "," +
		 * ContentDescriptorObj.Units.Columns.SYMBOL + "," + "NULL, " +
		 * ContentDescriptorObj.Units.Columns.LAST_UPDATE + " FROM " +
		 * ContentDescriptorObj.Units.NAME); db.execSQL("DROP TABLE " +
		 * ContentDescriptorObj.Units.NAME);
		 * db.execSQL("ALTER TABLE WORKINGTB RENAME TO " +
		 * ContentDescriptorObj.Units.NAME);
		 */
	}

	private void init_Internationals_Units(SQLiteDatabase db) {
		db.execSQL("INSERT INTO " + ContentDescriptorObj.TB_Units.NAME + " ("
				+ ContentDescriptorObj.TB_Units.Columns.ID + "," + " "
				+ ContentDescriptorObj.TB_Units.Columns.CLASS + "," + " "
				+ ContentDescriptorObj.TB_Units.Columns.LONG_NAME + "," + " "
				+ ContentDescriptorObj.TB_Units.Columns.SHORT_NAME + "," + " "
				+ ContentDescriptorObj.TB_Units.Columns.CONTAINERFAMILLY + ","
				+ " " + ContentDescriptorObj.TB_Units.Columns.LAST_UPDATE
				+ ") VALUES (1, 0, 'Gramme', 'g' , null, CURRENT_TIMESTAMP)");

	}

}
