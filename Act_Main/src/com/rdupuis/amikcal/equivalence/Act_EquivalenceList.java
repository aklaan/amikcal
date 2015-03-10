package com.rdupuis.amikcal.equivalence;




import java.util.ArrayList;
import java.util.HashMap;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AmiKcalFactory;
import com.rdupuis.amikcal.data.ContentDescriptorObj;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
 
public class Act_EquivalenceList extends Activity {
	Intent mIntent;
	private ListView maListViewPerso;
	static Long currentId;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_equivalences_list);
        mIntent = getIntent();
        generateList();
        getActionBar().setTitle("Equivalences");
      //  Button bt = (Button) findViewById(R.id.btnAdd);
      //  bt.setText("Ajouter une unit�e");
        }//fin du onCreate
    
    
    
    public void onClickEquivalenceItem(View v, String id){
    	Intent intent = new Intent(this,Act_EquivalenceEditor.class);
    	intent.putExtra("id", id);
    	startActivityForResult(intent, R.integer.ACTY_UNIT);
    	
    }
    
    public void onClickAdd(View v){
    	
    	onClickEquivalenceItem(v, null);
    	   	
    	
    }
    
    /**
     * G�rer les retours d'appels aux autres activit�es
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
			 		generateList();
		
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
    
    protected void generateList(){
		
    	   //R�cup�ration de la listview cr��e dans le fichier customizedlist.xml
        maListViewPerso = (ListView) findViewById(R.id.listviewperso);
 
        //Cr�ation de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
 
        //On d�clare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;
        map = new HashMap<String, String>();
        
        //On cr� un curseur pour lire la table des equivalences    
        ///Cursor cur = this.getContentResolver().query(ContentDescriptorObj.Equivalences.URI_CONTENT_EQUIVALENCES, null, null, null, null);
            
        
        //final int INDX_COL_ID      = cur.getColumnIndex(ContentDescriptorObj.Equivalences.Columns.ID);
        // faire un move First pour positionner le pointeur
             
        /*     if (cur.moveToFirst()) {
            	            	 
            	 do {
            		 
            		 AmiKcalFactory f = new AmiKcalFactory();
            		 f.contentResolver=this.getContentResolver(); 
            		 EquivalenceObj e = f.createEquivalenceObjFromId(cur.getLong(INDX_COL_ID));
            		 
            		 
            		 map = new HashMap<String, String>();
                  	 
                  	 map.put("id", String.valueOf(e.getId()));
                     map.put("energy_name", e.energy.getName());
                  	 map.put("unit_in_name", e.unitIn.getName());
                     map.put("unit_out_name", e.unitOut.getName());
                  	 listItem.add(map);
                 } while (cur.moveToNext());
            	
            }
             cur.close();
 */     
 
        //Cr�ation d'un SimpleAdapter qui se chargera de mettre les items pr�sent dans notre list (listItem)
        //dans la vue affichageitem
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.list_item_equivalence,
               new String[] {"energy_name", "unit_in_name","unit_out_name"}
             , new int[] {R.id.equivalence_item_tv_EnergyName,
        	              R.id.equivalence_item_tv_UnitIn,
        	              R.id.equivalence_item_tv_UnitOut});
 
        
        //On attribut � notre listView l'adapter que l'on vient de cr�er
        maListViewPerso.setAdapter(mSchedule);
 
        
        //********************************************************
        // on met un �couteur d'�v�nement sur notre listView
        //********************************************************
        maListViewPerso.setOnItemClickListener(
        		new OnItemClickListener() {
        		//	@Override
        			@SuppressWarnings("unchecked")
        			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        				//on r�cup�re la HashMap contenant les infos de notre item (titre, description, img)
        				HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position);
        				OnClick(view, map.get("id") );
        			}
        		}
        );
    
    
        //********************************************************
        // on met un �couteur d'�v�nement sur notre listView
        //********************************************************
        maListViewPerso.setOnItemLongClickListener(
        		new OnItemLongClickListener() {
        			//@Override
        			@SuppressWarnings( { "rawtypes", "unchecked" } )
        
        			public boolean onItemLongClick( AdapterView parent, View v, int position, long id) 
        			{
        				HashMap<String, String> map = (HashMap<String, String>) maListViewPerso.getItemAtPosition(position); 
        				Act_EquivalenceList.currentId = Long.parseLong(map.get("id"));
                    	//cr�ation d'un boite de dialogue pour confirmer le choix
                    	new AlertDialog.Builder(Act_EquivalenceList.this)
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
    
    public void OnClick(View v, String id){
    	
    	// on alimente le r�sultat dans l'Intent pour que l'Activity m�re puisse
    	//r�cup�rer la valeur. 
    	this.mIntent.putExtra("id", id); 
    	
    	//on appelle setResult pour d�clancher le onActivityResult de l'activity m�re. 
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
    	editEquivalence(Act_EquivalenceList.currentId.toString());
    }
    
    
    public void editEquivalence(String id){
    	Intent intent = new Intent(this,Act_EquivalenceEditor.class);
    	intent.putExtra("Id", id);
    	startActivityForResult(intent, R.integer.ACTY_UNIT);
    }
    
    
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar_equivalence_list, menu);
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
		case R.id.actionbar_equivalencelist_item_add:
			onClickAdd(null);
			break;
		case R.id.actionbar_equivalencelist_item_back:
			finish();
			break;

		default:
			return super.onOptionsItemSelected(item);
		}

		// invalidateOptionsMenu va appeller la m�thode onPrepareOptionsMenu();
		// qui va elle m�me rafraichir la barre de menu
		invalidateOptionsMenu();
		return true;
	}

    
    
//*************************************
} // end class