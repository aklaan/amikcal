package com.rdupuis.amikcal.unity;




import java.util.ArrayList;
import java.util.HashMap;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.data.ContentDescriptorObj;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
 
public class Act_UnitOfMeasureList extends Activity {
	Intent mIntent;
	private ListView maListViewPerso;
	static Long currentId;
    static Long energyId=0l;
	private static Long WITH_NO_FILTER=0l;
	private Resources mResources;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_unit_list);
        mIntent = getIntent();
        mResources = getResources();
        getActionBar().setTitle("Unitées");        
    
        try{
        	energyId=Long.parseLong(mIntent.getStringExtra(mResources.getString(R.string.INTENT_IN____UNITS_LIST____ID_OF_ENERGY)));	
        	generateList(energyId);
        } catch (Exception e){
        	generateList(WITH_NO_FILTER);
        }
        
       
        }//fin du onCreate
    
    
    
    public void onClickUnit(View v, String id){
    	Intent intent = new Intent(this,Act_UnitOfMeasureEditor.class);
    	intent.putExtra(mResources.getString(R.string.INTENT_IN____UNITS_EDITOR____ID_OF_UNIT), id);
    	startActivityForResult(intent, R.integer.ACTY_UNIT);
    	
    }
    
    public void onClickAdd(View v){
    	
    	onClickUnit(v, null);
    	   	
    	
    }
    
    /**
     * Gère le retour de l'appel à une autre activitée
     * @param requestCode
     * @param resultCode
     * @param intent
     */
   
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
       
    	switch (requestCode) {
    	
			case  R.integer.ACTY_UNIT      :
			
			 	if (resultCode == RESULT_OK){
					
			 		cleanList();
			 		generateList(WITH_NO_FILTER);
		
			    }
				break;
		default :break;
		 
		}
    }
    
    /*********************************************************************************************
     * 
     * 
     ********************************************************************************************/
    protected void cleanList(){
    		maListViewPerso.removeAllViewsInLayout();
    	}
    
    
    /*********************************************************************************************
     *
     *
     ********************************************************************************************/
    
    protected void generateList(long energyId){
		
    	Uri mUri;
    	
    	if (energyId==WITH_NO_FILTER){
    		mUri = ContentDescriptorObj.TB_Units.URI_SELECT_UNIT;
    		
    	} else{
    		
    		mUri=ContentUris.withAppendedId(ContentDescriptorObj.CustomQuery.URI_USED_UNITS_FOR_ENERGY,energyId);
    	}
    	
    	   //Récupération de la listview créée dans le fichier customizedlist.xml
        maListViewPerso = (ListView) findViewById(R.id.listviewperso);
 
        //Création de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
 
        //On déclare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;
        map = new HashMap<String, String>();
        
        //On cré un curseur pour lire la table des unitées    
        Cursor cur = this.getContentResolver().query(mUri, null, null, null, null);
            
        final int INDX_COL_ID = cur.getColumnIndex(ContentDescriptorObj.TB_Units.Columns.ID);
        final int INDX_COL_NAME = cur.getColumnIndex(ContentDescriptorObj.TB_Units.Columns.LONG_NAME);
        final int INDX_COL_SYMBOL = cur.getColumnIndex(ContentDescriptorObj.TB_Units.Columns.SHORT_NAME);
        
        // faire un move First pour positionner le pointeur
             
             if (cur.moveToFirst()) {
            	            	 
            	 do {
            		 map = new HashMap<String, String>();
                  	 
                  	 map.put("id", cur.getString(INDX_COL_ID));
                     map.put("name", cur.getString(INDX_COL_NAME));
                  	 map.put("symbol", cur.getString(INDX_COL_SYMBOL));
                  	 listItem.add(map);
                 } while (cur.moveToNext());
            	
            }
             cur.close();
      
 
        //Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem)
        //dans la vue affichageitem
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.list_item_unit,
               new String[] {"name", "symbol"}, new int[] {R.id.item_unit_name, R.id.item_unit_symbol});
 
        
        //On attribut à notre listView l'adapter que l'on vient de créer
        maListViewPerso.setAdapter(mSchedule);
 
        
        //********************************************************
        // on met un écouteur d'évènement sur notre listView
        //********************************************************
        maListViewPerso.setOnItemClickListener(
        		new OnItemClickListener() {
        		//	@Override
        			@SuppressWarnings("unchecked")
        			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        				//on récupère la HashMap contenant les infos de notre item (titre, description, img)
        				HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
        				OnChooseUnit(view, map.get("id") );
        			}
        		}
        );
    
    
        //********************************************************
        // on met un écouteur d'évènement sur notre listView
        //********************************************************
        maListViewPerso.setOnItemLongClickListener(
        		new OnItemLongClickListener() {
        			//@Override
        			@SuppressWarnings( { "rawtypes", "unchecked" } )
        
        			public boolean onItemLongClick( AdapterView parent, View v, int position, long id) 
        			{
        				HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position); 
        				Act_UnitOfMeasureList.currentId = Long.parseLong(map.get("id"));
                    	//création d'un boite de dialogue pour confirmer le choix
                    	new AlertDialog.Builder(Act_UnitOfMeasureList.this)
                        	.setTitle("Confirmation")
                        	.setMessage("Que souhaitez-vous faire ?")
                        	.setPositiveButton("Editer", new DialogInterface.OnClickListener() {
                        		 	public void onClick(DialogInterface dialog, int whichButton) {
                        		 		/* User clicked edit so do some stuff */		
                        		 		onClick_edit();
                        		 		
                        		 	}								
                        		}
                        	)
                        	.setNegativeButton("Supprimer", new DialogInterface.OnClickListener() {
                        			public void onClick(DialogInterface dialog, int whichButton) {
                        				/* User delete Cancel so do some stuff */
                        			}
                        		}
                        	)
                        	.setNeutralButton("Annuler",  new DialogInterface.OnClickListener() {
                        			public void onClick(DialogInterface dialog, int whichButton) {
                        				/* User clicked Cancel so do some stuff */
                        			}
                        		}
                        	)
                        .show(); 
        
                        return true;
                    }// public boolean onItemLongClick
        		}); //fin du OnItemLongClickListener
    	
    	
	}
    


    /*********************************************************************************
     * 
     * 
     **********************************************************************************/
    
    public void OnChooseUnit(View v, String id){
    	
    	// on alimente le résultat dans l'Intent pour que l'Activity mère puisse
    	//récupérer la valeur. 
    	this.mIntent.putExtra(mResources.getString(R.string.INTENT_OUT____UNITS_LIST____ID_OF_THE_UNIT), id); 
    	
    	//on appelle setResult pour déclancher le onActivityResult de l'activity mère. 
    	this.setResult(RESULT_OK, mIntent);
    	
    	//On termine l'Acitvity
    	this.finish();
    	}
    
    /*********************************************************************************
     * 
     * 
     **********************************************************************************/
    public void OnDelete(Long id ){
    	
    	// afficher une demande de confirmation
    	
    }

    
    public void onClick_edit(){
    	editUnit(Act_UnitOfMeasureList.currentId.toString());
    }
    
    
    public void editUnit(String id){
    	Intent intent = new Intent(this,Act_UnitOfMeasureEditor.class);
    	intent.putExtra("UnitId", id);
    	startActivityForResult(intent, R.integer.ACTY_UNIT);
    }
    
    
    
    
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar_unit_list, menu);
		return true;

	}

	//rafraichir la barre de menu
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);

	}

	/**
	 * 
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.actionbar_unitlist_item_add:
			onClickAdd(null);
			break;
		case R.id.actionbar_unitlist_item_back:
			finish();
			break;

		case R.id.actionbar_unitlist_item_showall:
			cleanList(); 
	    	generateList(WITH_NO_FILTER);
			break;

		default:
			return super.onOptionsItemSelected(item);
		}

		// invalidateOptionsMenu va appeller la méthode onPrepareOptionsMenu();
		// qui va elle même rafraichir la barre de menu
		invalidateOptionsMenu();
		return true;
	}

//*************************************
} // end class