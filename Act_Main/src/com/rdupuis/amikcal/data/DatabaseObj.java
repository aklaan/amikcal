package com.rdupuis.amikcal.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseObj extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "amikcal.db";
	public static final int DATABASE_VERSION = 4;

	public DatabaseObj(Context ctx){
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		
		//**********************************************************************************************
	    // Création de la table des unitées
		//**********************************************************************************************
		db.execSQL("CREATE TABLE " + ContentDescriptorObj.Units.NAME+ " ( " +
				ContentDescriptorObj.Units.Columns.ID            + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				ContentDescriptorObj.Units.Columns.TYPE          + " TEXT, "  +
				ContentDescriptorObj.Units.Columns.NAME          + " TEXT, "  +
				ContentDescriptorObj.Units.Columns.SYMBOL        + " TEXT, "  +
				ContentDescriptorObj.Units.Columns.CONTAINERFAMILLY   + " TEXT, "  +
				ContentDescriptorObj.Units.Columns.LAST_UPDATE   + " DATETIME, "  +
				"UNIQUE (" + ContentDescriptorObj.Units.Columns.ID + ") ON CONFLICT REPLACE)"
			);
		
		// dès la création de la base on insert les unités de mesure courantes.
		db.execSQL("INSERT INTO " + ContentDescriptorObj.Units.NAME
	
				+ " (" + ContentDescriptorObj.Units.Columns.ID         + ","  
				+ " " + ContentDescriptorObj.Units.Columns.TYPE        + ","
				+ " " + ContentDescriptorObj.Units.Columns.NAME        + ","
				+ " " + ContentDescriptorObj.Units.Columns.SYMBOL      + ","
				+ " " + ContentDescriptorObj.Units.Columns.CONTAINERFAMILLY      + ","
				+ " " + ContentDescriptorObj.Units.Columns.LAST_UPDATE 
				+ ") VALUES (1, 'international', 'Gramme', 'g' , null, CURRENT_TIMESTAMP)"); 
				
				
		
		
		//**********************************************************************************************
	    // Création de la table des contenances
		//**********************************************************************************************
		db.execSQL("CREATE TABLE " + ContentDescriptorObj.Capacities.NAME+ " ( " +
				ContentDescriptorObj.Capacities.Columns.ID               + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				ContentDescriptorObj.Capacities.Columns.CONTAINERFAMILLY + " TEXT, "  +
                ContentDescriptorObj.Capacities.Columns.CAPACITY         + " TEXT, "  +
                ContentDescriptorObj.Capacities.Columns.PICTURE          + " TEXT, "  +
                ContentDescriptorObj.Capacities.Columns.LAST_UPDATE      + " DATETIME, "  +
				"UNIQUE (" + ContentDescriptorObj.Capacities.Columns.ID + ") ON CONFLICT REPLACE)"
			);
		
		
		
         //********************************************************************************************** 		
	     // Création de la table des ennergies (table des calories brulées ou acquises)
		//**********************************************************************************************
		db.execSQL("CREATE TABLE " + ContentDescriptorObj.Energies.NAME+ " ( " +
				ContentDescriptorObj.Energies.Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				
				//poisson, viande, supermaché
				ContentDescriptorObj.Energies.Columns.CATEGORY      + " INTEGER, "  +
				
				//aliment ou recette
				ContentDescriptorObj.Energies.Columns.TYPE 	     + " INTEGER, " +
				
				ContentDescriptorObj.Energies.Columns.NAME          + " TEXT, " +
				//top liquide Y/N
				ContentDescriptorObj.Energies.Columns.ISLIQUID      + " CHAR, " +
				ContentDescriptorObj.Energies.Columns.QUANTITY      + " REAL, " +
				ContentDescriptorObj.Energies.Columns.FK_UNIT       + " INTEGER NOT NULL, " +
				ContentDescriptorObj.Energies.Columns.MNT_ENERGY    + " REAL, " +
				ContentDescriptorObj.Energies.Columns.MNT_LIPIDS    + " REAL, " +
				ContentDescriptorObj.Energies.Columns.MNT_GLUCIDS   + " REAL, " +
				ContentDescriptorObj.Energies.Columns.MNT_PROTEINS  + " REAL, " +
				ContentDescriptorObj.Energies.Columns.VITAMINS      + " TEXT, " +
				ContentDescriptorObj.Energies.Columns.LAST_UPDATE   + " DATETIME, "  +
				
				//Ajout d'une contrainte d'intégrité sur l'unité de mesure. 				
				"FOREIGN KEY(" + ContentDescriptorObj.Energies.Columns.FK_UNIT + ") REFERENCES "
				+ ContentDescriptorObj.Units.NAME + "("+ ContentDescriptorObj.Units.Columns.ID + ")" + 
				
				
				"UNIQUE (" + ContentDescriptorObj.Energies.Columns.ID + ") ON CONFLICT REPLACE)"
			);
	
	
		
        //********************************************************************************************** 		
	     // Création de la table des equivalences 
		
		//
		//**********************************************************************************************
		db.execSQL("CREATE TABLE " + ContentDescriptorObj.Equivalences.NAME+ " ( " +
				ContentDescriptorObj.Equivalences.Columns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				
				
				ContentDescriptorObj.Equivalences.Columns.FK_ENERGY      + " INTEGER, "  +
				ContentDescriptorObj.Equivalences.Columns.FK_UNIT_IN     + " INTEGER, " +
				ContentDescriptorObj.Equivalences.Columns.QUANTITY_OUT   + " REAL, " +
				ContentDescriptorObj.Equivalences.Columns.LAST_UPDATE    + " DATETIME, "  +
				
				ContentDescriptorObj.Equivalences.Columns.FK_UNIT_OUT + " INTEGER NOT NULL, " +
			
			
				//Ajout d'une contrainte d'intégrité sur l'énergy. 				
				"FOREIGN KEY(" + ContentDescriptorObj.Equivalences.Columns.FK_ENERGY + ") REFERENCES "
				+ ContentDescriptorObj.Energies.NAME + "("+ ContentDescriptorObj.Energies.Columns.ID + ")" + 

				
				//Ajout d'une contrainte d'intégrité sur l'unité de mesure en entrée. 				
				"FOREIGN KEY(" + ContentDescriptorObj.Equivalences.Columns.FK_UNIT_IN + ") REFERENCES "
				+ ContentDescriptorObj.Units.NAME + "("+ ContentDescriptorObj.Units.Columns.ID + ")" + 
				
				//Ajout d'une contrainte d'intégrité sur l'unité de mesure en sortie. 				
				"FOREIGN KEY(" + ContentDescriptorObj.Equivalences.Columns.FK_UNIT_OUT + ") REFERENCES "
				+ ContentDescriptorObj.Units.NAME + "("+ ContentDescriptorObj.Units.Columns.ID + ")" + 
								
				"UNIQUE (" + ContentDescriptorObj.Equivalences.Columns.ID + ") ON CONFLICT REPLACE)"
			);
	
	
	
	    //********************************************************************************************** 		
	     // Création de la table des journées de saisies 
		//**********************************************************************************************
		db.execSQL("CREATE TABLE " + ContentDescriptorObj.Days.NAME+ " ( " +
				ContentDescriptorObj.Days.Columns.DATE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				
				
				ContentDescriptorObj.Days.Columns.COVER         + " TEXT, "  +
				ContentDescriptorObj.Days.Columns.SCORE         + " INTEGER, " +
				ContentDescriptorObj.Days.Columns.MNT_ENERGY    + " REAL, " +
				ContentDescriptorObj.Days.Columns.MNT_GLUCIDS   + " REAL, " +
				ContentDescriptorObj.Days.Columns.MNT_LIPIDS    + " REAL, " +
				ContentDescriptorObj.Days.Columns.MNT_PROTEINS  + " REAL, " +
				ContentDescriptorObj.Days.Columns.VITAMINS      + " TEXT, " +
				ContentDescriptorObj.Days.Columns.LAST_UPDATE   + " DATETIME, "  +
				
				"UNIQUE (" + ContentDescriptorObj.Days.Columns.DATE + ") ON CONFLICT REPLACE)"
			);
	
		

	    //********************************************************************************************** 		
	     // Création de la table des components
		//**********************************************************************************************
		db.execSQL("CREATE TABLE " + ContentDescriptorObj.ActivityComponent.NAME+ " ( " +
				ContentDescriptorObj.ActivityComponent.Columns.ID            + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				ContentDescriptorObj.ActivityComponent.Columns.FK_PARENT     + " INTEGER, " +
				ContentDescriptorObj.ActivityComponent.Columns.FK_ENERGY     + " INTEGER, " +
				ContentDescriptorObj.ActivityComponent.Columns.QUANTITY      + " INTEGER, " +
				ContentDescriptorObj.ActivityComponent.Columns.FK_UNIT       + " INTEGER, " +
				ContentDescriptorObj.ActivityComponent.Columns.SCORE         + " INTEGER, " +
				ContentDescriptorObj.ActivityComponent.Columns.MNT_ENERGY    + " REAL, " +
				ContentDescriptorObj.ActivityComponent.Columns.MNT_GLUCIDS   + " REAL, " +
				ContentDescriptorObj.ActivityComponent.Columns.MNT_LIPIDS    + " REAL, " +
				ContentDescriptorObj.ActivityComponent.Columns.MNT_PROTEINS  + " REAL, " +
				ContentDescriptorObj.ActivityComponent.Columns.VITAMINS      + " TEXT, " +
				ContentDescriptorObj.ActivityComponent.Columns.LAST_UPDATE   + " DATETIME, " +
				
				"UNIQUE (" + ContentDescriptorObj.ActivityComponent.Columns.ID + ") ON CONFLICT REPLACE)"
			);
	
		
		
		 //********************************************************************************************** 		
	     // Création de la table des UserActivities
		//**********************************************************************************************
		db.execSQL("CREATE TABLE " + ContentDescriptorObj.UserActivities.NAME+ " ( " +
				ContentDescriptorObj.UserActivities.Columns.ID            + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				ContentDescriptorObj.UserActivities.Columns.DATE          + " DATETIME, "  +
				ContentDescriptorObj.UserActivities.Columns.TITLE         + " STRING, " +
				ContentDescriptorObj.UserActivities.Columns.TIMESLOT      + " INTEGER, " +
				ContentDescriptorObj.UserActivities.Columns.TYPE          + " INTEGER, " +
				ContentDescriptorObj.UserActivities.Columns.DURATION      + " INTEGER, " +
				ContentDescriptorObj.UserActivities.Columns.SCORE         + " INTEGER, " +
				ContentDescriptorObj.UserActivities.Columns.MNT_ENERGY    + " REAL, " +
				ContentDescriptorObj.UserActivities.Columns.MNT_GLUCIDS   + " REAL, " +
				ContentDescriptorObj.UserActivities.Columns.MNT_LIPIDS    + " REAL, " +
				ContentDescriptorObj.UserActivities.Columns.MNT_PROTEINS  + " REAL, " +
				ContentDescriptorObj.UserActivities.Columns.VITAMINS      + " TEXT, " +
				ContentDescriptorObj.UserActivities.Columns.PICTURE       + " TEXT, " +
				ContentDescriptorObj.UserActivities.Columns.LAST_UPDATE   + " DATETIME, "  +
				
				"UNIQUE (" + ContentDescriptorObj.UserActivities.Columns.ID + ") ON CONFLICT REPLACE)"
			);
		
	
	

	
	
	//********************************************************************************************** 		
    // Création de la vue
	//**********************************************************************************************
	db.execSQL("CREATE VIEW " + ContentDescriptorObj.ViewActivityComponent.NAME 
			+ " AS SELECT " 
			
			+ ContentDescriptorObj.ActivityComponent.NAME + "." 
            + ContentDescriptorObj.ActivityComponent.Columns.ID
            + " AS "
            + ContentDescriptorObj.ViewActivityComponent.Columns.ID
            
            //----------------------------------------------------------
            + "," 
            + ContentDescriptorObj.Energies.NAME + "." 
            + ContentDescriptorObj.Energies.Columns.ID          
            + " AS "  
            + ContentDescriptorObj.ViewActivityComponent.Columns.ENERGY_ID
            
            //----------------------------------------------------------
            + "," 
            + ContentDescriptorObj.Energies.NAME + "." 
            + ContentDescriptorObj.Energies.Columns.NAME             
            + " AS "  
            + ContentDescriptorObj.ViewActivityComponent.Columns.ENERGY_NAME
            //----------------------------------------------------------
            + "," 
            + ContentDescriptorObj.ActivityComponent.NAME + "." 
            + ContentDescriptorObj.ActivityComponent.Columns.QUANTITY
            + " AS "  
            + ContentDescriptorObj.ViewActivityComponent.Columns.QUANTITY
            
            //----------------------------------------------------------
            + "," 
            + ContentDescriptorObj.Units.NAME + "." 
            + ContentDescriptorObj.Units.Columns.ID
            + " AS "  
            + ContentDescriptorObj.ViewActivityComponent.Columns.UNIT_ID
                        
            //----------------------------------------------------------
            + "," 
            + ContentDescriptorObj.Units.NAME + "." 
            + ContentDescriptorObj.Units.Columns.NAME
            + " AS "  
            + ContentDescriptorObj.ViewActivityComponent.Columns.UNIT_NAME
                        
            
            //----------------------------------------------------------
            + "," 
            + ContentDescriptorObj.ActivityComponent.NAME + "." 
            + ContentDescriptorObj.ActivityComponent.Columns.SCORE
            + " AS "  
            + ContentDescriptorObj.ViewActivityComponent.Columns.SCORE
            
            //----------------------------------------------------------
			+ "," 
			+ ContentDescriptorObj.ActivityComponent.NAME + "." 
			+ ContentDescriptorObj.ActivityComponent.Columns.MNT_ENERGY
			+ " AS "  
	        + ContentDescriptorObj.ViewActivityComponent.Columns.MNT_ENERGY
			
            //----------------------------------------------------------
			+ "," 
			+ ContentDescriptorObj.ActivityComponent.NAME + "." 
			+ ContentDescriptorObj.ActivityComponent.Columns.MNT_GLUCIDS    
			+ " AS "  
	        + ContentDescriptorObj.ViewActivityComponent.Columns.MNT_GLUCIDS
	        
            //----------------------------------------------------------
			+ "," 
            + ContentDescriptorObj.ActivityComponent.NAME + "." 
			+ ContentDescriptorObj.ActivityComponent.Columns.MNT_LIPIDS
			+ " AS "  
	        + ContentDescriptorObj.ViewActivityComponent.Columns.MNT_LIPIDS
				        
            //----------------------------------------------------------
			+ "," 
            + ContentDescriptorObj.ActivityComponent.NAME + "." 
			+ ContentDescriptorObj.ActivityComponent.Columns.MNT_PROTEINS
			+ " AS "  
	        + ContentDescriptorObj.ViewActivityComponent.Columns.MNT_PROTEINS
			
            //----------------------------------------------------------
			+ "," 
            + ContentDescriptorObj.ActivityComponent.NAME + "." 
            + ContentDescriptorObj.ActivityComponent.Columns.VITAMINS
            + " AS "  
	        + ContentDescriptorObj.ViewActivityComponent.Columns.VITAMINS
			
	        //----------------------------------------------------------
			+ "," 
	        + ContentDescriptorObj.ActivityComponent.NAME + "." 
	        + ContentDescriptorObj.ActivityComponent.Columns.FK_PARENT
	        + " AS "  
		    + ContentDescriptorObj.ViewActivityComponent.Columns.PARENT_ID
		   //----------------------------------------------------------    
		        
	        //----------------------------------------------------------
			+ "," 
            + ContentDescriptorObj.ActivityComponent.NAME + "." 
            + ContentDescriptorObj.ActivityComponent.Columns.LAST_UPDATE
            + " AS "  
	        + ContentDescriptorObj.ViewActivityComponent.Columns.LAST_UPDATE
            //----------------------------------------------------------

			+ " FROM " 
			+ ContentDescriptorObj.ActivityComponent.NAME + " AS " + ContentDescriptorObj.ActivityComponent.NAME
			+ " INNER JOIN " 
			+ ContentDescriptorObj.Energies.NAME + " AS " + ContentDescriptorObj.Energies.NAME
			+ " ON "
			+ ContentDescriptorObj.ActivityComponent.NAME + "."	+ ContentDescriptorObj.ActivityComponent.Columns.FK_ENERGY
			+ " = "
			+ ContentDescriptorObj.Energies.NAME + "."	+ ContentDescriptorObj.Energies.Columns.ID
			
			+ " INNER JOIN " 
			+ ContentDescriptorObj.Units.NAME + " AS " + ContentDescriptorObj.Units.NAME
			+ " ON "
			+ ContentDescriptorObj.ActivityComponent.NAME + "."	+ ContentDescriptorObj.ActivityComponent.Columns.FK_UNIT
			+ " = "
			+ ContentDescriptorObj.Units.NAME + "."	+ ContentDescriptorObj.Units.Columns.ID
		);
	



	
	//********************************************************************************************** 		
    // Création de la vue
	//**********************************************************************************************
	db.execSQL("CREATE VIEW " + ContentDescriptorObj.ViewEnergies.NAME 
			+ " AS SELECT " 
			 
           
            
            + ContentDescriptorObj.Energies.NAME + "." 
            + ContentDescriptorObj.Energies.Columns.ID          
            + " AS "  
            + ContentDescriptorObj.ViewEnergies.Columns.ID
            
            //----------------------------------------------------------
            + "," 
            + ContentDescriptorObj.Energies.NAME + "." 
            + ContentDescriptorObj.Energies.Columns.NAME             
            + " AS "  
            + ContentDescriptorObj.ViewEnergies.Columns.ENERGY_NAME
            //----------------------------------------------------------
            + "," 
            + ContentDescriptorObj.Energies.NAME + "." 
            + ContentDescriptorObj.Energies.Columns.QUANTITY
            + " AS "  
            + ContentDescriptorObj.ViewEnergies.Columns.QUANTITY
            
            //----------------------------------------------------------
            + "," 
            + ContentDescriptorObj.Units.NAME + "." 
            + ContentDescriptorObj.Units.Columns.ID
            + " AS "  
            + ContentDescriptorObj.ViewEnergies.Columns.UNIT_ID
                        
            //----------------------------------------------------------
            + "," 
            + ContentDescriptorObj.Units.NAME + "." 
            + ContentDescriptorObj.Units.Columns.NAME
            + " AS "  
            + ContentDescriptorObj.ViewEnergies.Columns.UNIT_NAME
                        
            
          
            
            //----------------------------------------------------------
			+ "," 
			+ ContentDescriptorObj.Energies.NAME + "." 
			+ ContentDescriptorObj.Energies.Columns.MNT_ENERGY
			+ " AS "  
	        + ContentDescriptorObj.ViewEnergies.Columns.MNT_ENERGY
			
            //----------------------------------------------------------
			+ "," 
			+ ContentDescriptorObj.Energies.NAME + "." 
			+ ContentDescriptorObj.Energies.Columns.MNT_GLUCIDS    
			+ " AS "  
	        + ContentDescriptorObj.ViewEnergies.Columns.MNT_GLUCIDS
	        
            //----------------------------------------------------------
			+ "," 
            + ContentDescriptorObj.Energies.NAME + "." 
			+ ContentDescriptorObj.Energies.Columns.MNT_LIPIDS
			+ " AS "  
	        + ContentDescriptorObj.ViewEnergies.Columns.MNT_LIPIDS
				        
            //----------------------------------------------------------
			+ "," 
            + ContentDescriptorObj.Energies.NAME + "." 
			+ ContentDescriptorObj.Energies.Columns.MNT_PROTEINS
			+ " AS "  
	        + ContentDescriptorObj.ViewEnergies.Columns.MNT_PROTEINS
			
            //----------------------------------------------------------
			+ "," 
            + ContentDescriptorObj.Energies.NAME + "." 
            + ContentDescriptorObj.Energies.Columns.VITAMINS
            + " AS "  
	        + ContentDescriptorObj.ViewEnergies.Columns.VITAMINS
		  

			+ " FROM " 
			+ ContentDescriptorObj.Energies.NAME + " AS " + ContentDescriptorObj.Energies.NAME
						
			+ " INNER JOIN " 
			+ ContentDescriptorObj.Units.NAME + " AS " + ContentDescriptorObj.Units.NAME
			+ " ON "
			+ ContentDescriptorObj.Energies.NAME + "."	+ ContentDescriptorObj.Energies.Columns.FK_UNIT
			+ " = "
			+ ContentDescriptorObj.Units.NAME + "."	+ ContentDescriptorObj.Units.Columns.ID
		);
	


	
	
	
	//********************************************************************************************** 		
    // Création de la vue
	//**********************************************************************************************
	db.execSQL("CREATE VIEW " + ContentDescriptorObj.ViewUserActivities.NAME 
			+ " AS SELECT " 
			 
           
            
            + ContentDescriptorObj.UserActivities.NAME + "." 
            + ContentDescriptorObj.UserActivities.Columns.ID          
            + " AS "  
            + ContentDescriptorObj.ViewUserActivities.Columns.ID
            
           
            //----------------------------------------------------------
            + ",SUM(" 
            + ContentDescriptorObj.ActivityComponent.NAME + "." 
            + ContentDescriptorObj.ActivityComponent.Columns.MNT_ENERGY
            + ")"
            + " AS "  
            + ContentDescriptorObj.ViewUserActivities.Columns.SUM_ENERGY
            
            //----------------------------------------------------------
            + ",SUM(" 
            + ContentDescriptorObj.ActivityComponent.NAME + "." 
            + ContentDescriptorObj.ActivityComponent.Columns.MNT_GLUCIDS
            + ")"
            + " AS "  
            + ContentDescriptorObj.ViewUserActivities.Columns.SUM_GLUCIDS
          
           //----------------------------------------------------------
            + ",SUM(" 
            + ContentDescriptorObj.ActivityComponent.NAME + "." 
            + ContentDescriptorObj.ActivityComponent.Columns.MNT_LIPIDS
            + ")"
            + " AS "  
            + ContentDescriptorObj.ViewUserActivities.Columns.SUM_LIPIDS
            
            
            //----------------------------------------------------------
            + ",SUM(" 
            + ContentDescriptorObj.ActivityComponent.NAME + "." 
            + ContentDescriptorObj.ActivityComponent.Columns.MNT_PROTEINS
            + ")"
            + " AS "  
            + ContentDescriptorObj.ViewUserActivities.Columns.SUM_PROTEINS
            
            

			+ " FROM " 
			+ ContentDescriptorObj.UserActivities.NAME + " AS " + ContentDescriptorObj.UserActivities.NAME
						
			+ " INNER JOIN " 
			+ ContentDescriptorObj.ActivityComponent.NAME + " AS " + ContentDescriptorObj.ActivityComponent.NAME
			+ " ON "
			+ ContentDescriptorObj.UserActivities.NAME + "."	+ ContentDescriptorObj.UserActivities.Columns.ID
			+ " = "
			+ ContentDescriptorObj.ActivityComponent.NAME + "."	+ ContentDescriptorObj.ActivityComponent.Columns.FK_PARENT
		
			+ " GROUP BY "
			+ ContentDescriptorObj.UserActivities.NAME + "." 
	        + ContentDescriptorObj.UserActivities.Columns.ID         
			
			);
	

}
	
	
	
	
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
		Log.i("upd db to version",String.valueOf(DATABASE_VERSION));
		
		if(oldVersion < newVersion){
			
			for (int i=1;i<=newVersion;i++){
			 
				switch (i){
			 
					case 2: oldVersion=i;
							break;
			 
					case 3: oldVersion=i;
							break;
	 		 		 
					case 4: doUpgradeOfVersion4(db);
							oldVersion=i;
							break;
							
					case 5: doUpgradeOfVersion5(db);
							oldVersion=i;
							break; 
					default :break;
				}
			}
        }
	}

	
	
	
	
	private void doUpgradeOfVersion4(SQLiteDatabase db){
		db.execSQL("DROP TABLE IF EXISTS WORKINGTB");
		db.execSQL("CREATE TABLE WORKINGTB ( " +
				ContentDescriptorObj.Units.Columns.ID            + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				ContentDescriptorObj.Units.Columns.TYPE          + " TEXT, "  +
				ContentDescriptorObj.Units.Columns.NAME          + " TEXT, "  +
				ContentDescriptorObj.Units.Columns.SYMBOL        + " TEXT, "  +
				ContentDescriptorObj.Units.Columns.CONTAINERFAMILLY   + " TEXT, "  +
				ContentDescriptorObj.Units.Columns.LAST_UPDATE   + " DATETIME, "  +
				"UNIQUE (" + ContentDescriptorObj.Units.Columns.ID + ") ON CONFLICT REPLACE)"
			);
		
		
		db.execSQL("INSERT INTO WORKINGTB SELECT "
				+ ContentDescriptorObj.Units.Columns.ID     + ","
				+ ContentDescriptorObj.Units.Columns.TYPE   + ","
				+ ContentDescriptorObj.Units.Columns.NAME   + ","
				+ ContentDescriptorObj.Units.Columns.SYMBOL + "," 
				+ "NULL, "
				+ ContentDescriptorObj.Units.Columns.LAST_UPDATE 
				+ " FROM " +ContentDescriptorObj.Units.NAME );
		db.execSQL("DROP TABLE " +ContentDescriptorObj.Units.NAME);		
		db.execSQL("ALTER TABLE WORKINGTB RENAME TO "+ ContentDescriptorObj.Units.NAME  );
		
	}
	
	
	
	private void doUpgradeOfVersion5(SQLiteDatabase db){
	
		db.execSQL("DROP TABLE IF EXISTS WORKINGTB");
		db.execSQL("CREATE TABLE WORKINGTB ( " +
				ContentDescriptorObj.UserActivities.Columns.ID            + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				ContentDescriptorObj.UserActivities.Columns.DATE          + " DATETIME, "  +
				ContentDescriptorObj.UserActivities.Columns.TITLE         + " STRING, " +
				ContentDescriptorObj.UserActivities.Columns.TIMESLOT      + " INTEGER, " +
				ContentDescriptorObj.UserActivities.Columns.TYPE          + " INTEGER, " +
				ContentDescriptorObj.UserActivities.Columns.DURATION      + " INTEGER, " +
				ContentDescriptorObj.UserActivities.Columns.SCORE         + " INTEGER, " +
				ContentDescriptorObj.UserActivities.Columns.MNT_ENERGY    + " REAL, " +
				ContentDescriptorObj.UserActivities.Columns.MNT_GLUCIDS   + " REAL, " +
				ContentDescriptorObj.UserActivities.Columns.MNT_LIPIDS    + " REAL, " +
				ContentDescriptorObj.UserActivities.Columns.MNT_PROTEINS  + " REAL, " +
				ContentDescriptorObj.UserActivities.Columns.VITAMINS      + " TEXT, " +
				ContentDescriptorObj.UserActivities.Columns.PICTURE       + " TEXT, " +
				ContentDescriptorObj.UserActivities.Columns.LAST_UPDATE   + " DATETIME, "  +
				"UNIQUE (" + ContentDescriptorObj.UserActivities.Columns.ID + ") ON CONFLICT REPLACE)"
			);
		
		
	}
}
