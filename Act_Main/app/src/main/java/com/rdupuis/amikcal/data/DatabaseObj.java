package com.rdupuis.amikcal.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rdupuis.amikcal.R;

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

        // **********************************************************************************************
        // Création de la table des énergies
        // **********************************************************************************************
        db.execSQL("CREATE TABLE " + ContentDescriptorObj.TB_Energies.TBNAME
                + " ( " + ContentDescriptorObj.TB_Energies.Columns.ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "

                + ContentDescriptorObj.TB_Energies.Columns.CLASS
                + " TEXT, "

                + ContentDescriptorObj.TB_Energies.Columns.NAME
                + " TEXT, "
                // Top liquide Y/N
                + ContentDescriptorObj.TB_Energies.Columns.STRUCTURE
                + " TEXT, "

                + ContentDescriptorObj.TB_Energies.Columns.QTY_REF_ID
                + " TEXT, "

                + ContentDescriptorObj.TB_Energies.Columns.LAST_UPDATE
                + " DATETIME, "

                + "UNIQUE (" + ContentDescriptorObj.TB_Energies.Columns.ID
                + ") ON CONFLICT REPLACE)");

        // **********************************************************************************************
        // Création de la table des relations
        // **********************************************************************************************
        db.execSQL("CREATE TABLE " + ContentDescriptorObj.TB_Party_rel.TBNAME
                + " ( " + ContentDescriptorObj.TB_Party_rel.Columns.ROW_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ContentDescriptorObj.TB_Party_rel.Columns.PAR_ROW_ID + " INTEGER, "
                + ContentDescriptorObj.TB_Party_rel.Columns.REL_TYP_CD + " STRING, "
                + ContentDescriptorObj.TB_Party_rel.Columns.PARTY_1 + " STRING, "
                + ContentDescriptorObj.TB_Party_rel.Columns.PARTY_2 + " STRING, "
                + ContentDescriptorObj.TB_Party_rel.Columns.LAST_UPDATE
                + " DATETIME, " +

                "UNIQUE (" + ContentDescriptorObj.TB_Party_rel.Columns.ROW_ID
                + ") ON CONFLICT REPLACE)");

        // **********************************************************************************************
        // Création de la table des Activitiés de l'utilisateur
        // ex: Repas / pesées / activité physiques
        // **********************************************************************************************
        db.execSQL("CREATE TABLE "
                + ContentDescriptorObj.TB_UserActivities.TBNAME + " ( "
                + ContentDescriptorObj.TB_UserActivities.Columns.ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "

                + ContentDescriptorObj.TB_UserActivities.Columns.CLASS
                + " STRING, "

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


        //*********************************************************************
        // Création de la vue permettant de faire le lien entre une QTY et
        // ses équivalences.
        //***********************************************************************/

        db.execSQL("CREATE VIEW "
                        + ContentDescriptorObj.View_qty_equiv.VIEWNAME

                        + " AS SELECT "

                        // ID de la relation UA<->UAC
                        + ContentDescriptorObj.TB_Party_rel.TBNAME
                        + "."
                        + ContentDescriptorObj.TB_Party_rel.Columns.ROW_ID
                        + " AS "
                        + ContentDescriptorObj.View_qty_equiv.Columns.REL_ID
                        + ","

                        // ID de la source d'�nergie
                        + ContentDescriptorObj.TB_Party_rel.TBNAME
                        + "."
                        + ContentDescriptorObj.TB_Party_rel.Columns.PARTY_1
                        + " AS "
                        + ContentDescriptorObj.View_qty_equiv.Columns.QTY_ID
                        + ","

                        // ID de la QtyRef
                        + ContentDescriptorObj.TB_Party_rel.TBNAME
                        + "."
                        + ContentDescriptorObj.TB_Party_rel.Columns.PARTY_2
                        + " AS "
                        + ContentDescriptorObj.View_qty_equiv.Columns.QTY_EQUIV_ID

                        + " FROM "
                        + ContentDescriptorObj.TB_Party_rel.TBNAME

                        // On s�l�ctionne les liens de type QTY_EQUIVL
                        + " WHERE "
                        + ContentDescriptorObj.TB_Party_rel.TBNAME
                        + "."
                        + ContentDescriptorObj.TB_Party_rel.Columns.REL_TYP_CD
                        + " = '"
                        + ContentDescriptorObj.TB_Party_rel.PredefinedValues.RelationsCodes.QTY_EQUIV
                        + "'"


        );


        //*********************************************************************
        // Création de la vue permettant de faire le lien entre une QTY et
        // ses équivalences.
        //***********************************************************************/

        db.execSQL("CREATE VIEW "
                        + ContentDescriptorObj.View_Component_equiv.VIEWNAME

                        + " AS SELECT "

                        // ID de la relation UA<->UAC
                        + ContentDescriptorObj.TB_Party_rel.TBNAME
                        + "."
                        + ContentDescriptorObj.TB_Party_rel.Columns.ROW_ID
                        + " AS "
                        + ContentDescriptorObj.View_Component_equiv.Columns.REL_ID
                        + ","

                        // ID de la source d'�nergie
                        + ContentDescriptorObj.TB_Party_rel.TBNAME
                        + "."
                        + ContentDescriptorObj.TB_Party_rel.Columns.PARTY_1
                        + " AS "
                        + ContentDescriptorObj.View_Component_equiv.Columns.COMP1_ID
                        + ","

                        // ID de la QtyRef
                        + ContentDescriptorObj.TB_Party_rel.TBNAME
                        + "."
                        + ContentDescriptorObj.TB_Party_rel.Columns.PARTY_2
                        + " AS "
                        + ContentDescriptorObj.View_Component_equiv.Columns.COMP2_ID

                        + " FROM "
                        + ContentDescriptorObj.TB_Party_rel.TBNAME

                        // On s�l�ctionne les liens de type QTY_EQUIVL
                        + " WHERE "
                        + ContentDescriptorObj.TB_Party_rel.TBNAME
                        + "."
                        + ContentDescriptorObj.TB_Party_rel.Columns.REL_TYP_CD
                        + " = '"
                        + ContentDescriptorObj.TB_Party_rel.PredefinedValues.RelationsCodes.COMP_EQUIV
                        + "'"


        );

        //*********************************************************************
        // Création de la vue permettant de faire le lien entre une activité et
        // ses UAC composantes.
        //***********************************************************************/

        db.execSQL("CREATE VIEW "
                        + ContentDescriptorObj.View_Qty.VIEWNAME

                        + " AS SELECT "

                        // ID de la relation Qty
                        + ContentDescriptorObj.TB_Party_rel.TBNAME
                        + "."
                        + ContentDescriptorObj.TB_Party_rel.Columns.ROW_ID
                        + " AS "
                        + ContentDescriptorObj.View_Qty.Columns.QTY_ID
                        + ","

                        // Montant de la qty
                        + ContentDescriptorObj.TB_Party_rel.TBNAME
                        + "."
                        + ContentDescriptorObj.TB_Party_rel.Columns.PARTY_1
                        + " AS "
                        + ContentDescriptorObj.View_Qty.Columns.AMOUNT
                        + ","

                        // ID de l'unit�e
                        + ContentDescriptorObj.TB_Party_rel.TBNAME
                        + "."
                        + ContentDescriptorObj.TB_Party_rel.Columns.PARTY_2
                        + " AS "
                        + ContentDescriptorObj.View_Qty.Columns.UNITY_ID

                        + " FROM "
                        + ContentDescriptorObj.TB_Party_rel.TBNAME

                        // On s�l�ctionne les liens de type QTY
                        + " WHERE "
                        + ContentDescriptorObj.TB_Party_rel.TBNAME
                        + "."
                        + ContentDescriptorObj.TB_Party_rel.Columns.REL_TYP_CD
                        + " = '"
                        + ContentDescriptorObj.TB_Party_rel.PredefinedValues.RelationsCodes.QTY
                        + "'"


        );


/**
 * Initalisations de base
 */
        inititialize_Database(db);

    }


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

    private void inititialize_Database(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + ContentDescriptorObj.TB_Units.NAME + " ("
                + ContentDescriptorObj.TB_Units.Columns.ID + "," + " "
                + ContentDescriptorObj.TB_Units.Columns.CLASS + "," + " "
                + ContentDescriptorObj.TB_Units.Columns.LONG_NAME + "," + " "
                + ContentDescriptorObj.TB_Units.Columns.SHORT_NAME + "," + " "
                + ContentDescriptorObj.TB_Units.Columns.CONTAINERFAMILLY + ","
                + " " + ContentDescriptorObj.TB_Units.Columns.LAST_UPDATE
                + ") VALUES ("+ R.integer.Kcal_id +", 0, 'Kilo Calories', 'Kcal' , null, CURRENT_TIMESTAMP)"
                + " ,("+R.integer.Kcal_id+1 +", 0, 'Gramme', 'g' , null, CURRENT_TIMESTAMP)");

    }

}
