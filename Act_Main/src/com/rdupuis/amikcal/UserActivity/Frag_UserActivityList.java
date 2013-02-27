package com.rdupuis.amikcal.UserActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.HashMap;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.Commons.MultipleItemsActivityList;
import com.rdupuis.amikcal.Commons.ToolBox;
import com.rdupuis.amikcal.Commons.NumericPad.NumericPadFragment;
import com.rdupuis.amikcal.Commons.NumericPad.NumericPadFragment.OnClickButtonOK;
import com.rdupuis.amikcal.Data.ContentDescriptorObj;
import com.rdupuis.amikcal.UserActivity.Frag_UserActivityList;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

/**
 * <b>Liste des activitées de l'utilisateur.</b>
 * <p> les activitées sont : 
 * <ul> 
 * 		<li> les repas </li>
 * 	<li> les activitées physiques </li>
 * <li> les pesées </li>
 * <li> les recettes </li>
 * </ul>
 * </p>
 * @author Rodolphe Dupuis
 * @version 0.1
 */

public class Frag_UserActivityList extends Fragment {
 
	private ListView    mCustomListView;
	private Calendar    currentDay;
	private int         currentTypeOfList;
	private Intent      mIntent ;
	private long selectedItemId;
	private Resources   mResources;
	

    /** Called when the activity is first created. */
    @Override
    
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState) {
    	
    	View mainView = inflater.inflate(R.layout.user_activity_list_view_by_day, container, false);  
    	
   	 
        mCustomListView = (ListView) mainView.findViewById(R.id.listviewperso);
        mResources = getActivity().getResources();
        mIntent = getActivity().getIntent();
       
      
       // String page ="";
        //* on tente de récupérer une date si l'intent nous en a envoyé une
        try {
        	//currentDay=ToolBox.parseCalendar(mIntent.getStringExtra(mResources.getString(R.string.INTENT_IN_USER_ACTIVITY_LIST_DAY_OF_ACTIVITIES)));
         	currentDay=ToolBox.parseCalendar(getArguments().getString("date"));
         //	page=getArguments().getString("page");
         	//mainView.setId(getArguments().getInt("id"));
         	//Log.i("date reçue ",getArguments().getString("date"));
         
        }
        catch (Exception e){
        	currentDay  =Calendar.getInstance();
        };
        
        
     
        // Afficher la somme totale des calories "Aliments" du jour. 
        TextView tv = (TextView) mainView.findViewById(R.id.synth_food_nbKcal);
  		tv.setText(ToolBox.getSumOfFoodEnergyForDay(getActivity(),(ToolBox.getSqlDate(currentDay))));

        // Afficher la date du jour. 
    	tv= (TextView)mainView.findViewById(R.id.fragtextView);
     	//ed.setText("Page " + page + " - " + ToolBox.getSqlDate(currentDay));
    	tv.setText( ToolBox.getSqlDate(currentDay) + " / n°Fragment: " + getArguments().getString("page"));       
        
        //* on tente de récupérer le type de liste à afficher si l'intent nous en a envoyé un
        try {
        	currentTypeOfList =Integer.parseInt(mIntent.getStringExtra(mResources.getString(R.string.INTENT_IN_USER_ACTIVITY_LIST_TYPE_OF_LIST)));
            }
        catch (Exception e){
            	currentTypeOfList=mResources.getInteger(R.integer.USER_ACTIVITY_LIST_ACTIVITY);
     
            };
        
        
            refreshScreen();
     return mainView;
    };
    

    
    /**
     *  lorsque l'activité s'arrête, on doit appeller la fonction setResult
     *  pour éventuellement renvoyer les résultats aux activitées appelantes
     */
    public void onStop(){
     	super.onStop();
    	//Toast.makeText(this, "Activité UserActivity stopée", Toast.LENGTH_SHORT).show();
    	//this.setResult(Activity.RESULT_OK, mIntent);
    }
    
    
  

    /**
     * <p>On affiche la liste des activitées.
     * </p> 
     */
    
    protected void generateList(){
    	  
    	Uri request;
 
    	if (currentTypeOfList == mResources.getInteger(R.integer.USER_ACTIVITY_LIST_RECIPE)){
    		request = ContentUris.withAppendedId(ContentDescriptorObj.UserActivities.URI_SELECT_USER_ACTIVITIES_BY_TYPE,mResources.getInteger(R.integer.ACTIVITY_RECIPE));
    		
    	}else {
    		request  = ContentDescriptorObj.UserActivities.URI_SELECT_USER_ACTIVITIES_BY_DATE.buildUpon().appendPath(ToolBox.getSqlDate(currentDay)).build();
    	};
    	
        	
    	
     MultipleItemsActivityList mMultipleItemsList=new MultipleItemsActivityList(this);
    
     //On déclare la HashMap qui contiendra les informations pour un item
     HashMap<String, String> map;
     map = new HashMap<String, String>();
     
     Cursor cur = getActivity().getContentResolver().query(request, null, null, null, null);
         
     final int INDX_COL_ID = cur.getColumnIndex(ContentDescriptorObj.UserActivities.Columns.ID);
     final int INDX_COL_TITLE = cur.getColumnIndex(ContentDescriptorObj.UserActivities.Columns.TITLE);
     final int INDX_COL_DATE = cur.getColumnIndex(ContentDescriptorObj.UserActivities.Columns.DATE);
     final int INDX_COL_TYPE = cur.getColumnIndex(ContentDescriptorObj.UserActivities.Columns.TYPE);
          
          if (cur.moveToFirst()) {
         	            	 
         	 do {
         		 
         		 map = new HashMap<String, String>();
               	 map.put("titre", cur.getString(INDX_COL_TITLE));
                        
               	 Calendar mCalendar = ToolBox.parseSQLDatetime(cur.getString(INDX_COL_DATE)); 
               
               	 DecimalFormat decimalFormat = (DecimalFormat)DecimalFormat.getInstance();
               	 decimalFormat.applyPattern("00");
               		
               	 map.put("hour", String.valueOf(decimalFormat.format(mCalendar.get(Calendar.HOUR_OF_DAY))));
               	 map.put("minute", String.valueOf(decimalFormat.format(mCalendar.get(Calendar.MINUTE))));
               	 map.put("id", cur.getString(INDX_COL_ID));
               	 map.put("type", cur.getString(INDX_COL_TYPE));
               	 
             	 map.putAll(computeEnergy(Long.parseLong(cur.getString(INDX_COL_ID))));
             	
             	 mMultipleItemsList.addItem(map);
             	 
              } while (cur.moveToNext());
         	
         }
          cur.close();
   
     
     //On attribut à notre listView l'adapter que l'on vient de créer
     this.mCustomListView.setAdapter(mMultipleItemsList);

     //Enfin on met un écouteur d'évènement sur notre listView
     this.mCustomListView.setOnItemClickListener(
     		new OnItemClickListener() {
     	//		@Override
     			@SuppressWarnings("unchecked")
     			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
     				//on récupère la HashMap contenant les infos de notre item (titre, description, img)
     				HashMap<String, String> map = (HashMap<String, String>) mCustomListView.getItemAtPosition(position);
				    
     				UserActivityList_FragmentsSliderActivity ua = (UserActivityList_FragmentsSliderActivity) Frag_UserActivityList.this.getActivity();
     				ua.setmCurrentPage(Integer.parseInt(getArguments().getString("page")));
     				ua.onClickActivity(map.get("id"));
     				
     			}
     		}
     		);
 
 
     //Enfin on met un écouteur d'évènement long sur notre listView
     mCustomListView.setOnItemLongClickListener(
     		new OnItemLongClickListener() {
     			//@Override
     			@SuppressWarnings( "unchecked"  )
     
     			public boolean onItemLongClick( AdapterView<?> parent, View v, int position, long id) 
     			{
                 
     				HashMap<String, String> map = (HashMap<String, String>) mCustomListView.getItemAtPosition(position); 
     				Frag_UserActivityList.this.selectedItemId = Long.parseLong(map.get("id"));
     				
     				
     				//	int ilaposition=position;
                 	//création d'une boite de dialogue pour confirmer le choix
                 	
     				AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
     				
     				adb.setTitle("Confirmation");
     				adb.setMessage("Que voulez-vous faire ?");
     				
     				//*********************************************************************************************************
                    adb.setPositiveButton("Editer", new DialogInterface.OnClickListener() {
                  
                    	public void onClick(DialogInterface dialog, int whichButton){
                    		UserActivityList_FragmentsSliderActivity ua = (UserActivityList_FragmentsSliderActivity) Frag_UserActivityList.this.getActivity();
                            ua.onClickEdit(Frag_UserActivityList.this,String.valueOf(Frag_UserActivityList.this.selectedItemId));
                    		}								
                    	}
                    );
                    
                    //*********************************************************************************************************
                    
                     	adb.setNegativeButton("Supprimer", new DialogInterface.OnClickListener()
                     		{
                     			public void onClick(DialogInterface dialog, int whichButton)
                     			{
                     				//UserActivityListFragment.this.onClickDelete(String.valueOf(UserActivityListFragment.selectedItemId));
                     			}
                     		});
     
                     	adb.setNeutralButton("Annuler", new DialogInterface.OnClickListener()
                     		{
                     			public void onClick(DialogInterface dialog, int whichButton)
                     			{
                     				/* User clicked Cancel so do some stuff */
                     			}
                     		})	;
                     		
                     		
                     adb.show(); 
     
                     return true;
                 }// public boolean onItemLongClick
     		}); //fin du OnItemLongClickListener

	}
 
 
   
 	/**
 	* Efface tous les items de la liste affiché à l'écran
 	*/
  	protected void cleanList(){
		mCustomListView.removeAllViewsInLayout();
  	}
 
  	

 
 

 
private HashMap<String, String> computeEnergy(long UserActivityId){
	
	
	HashMap<String, String> map;
    map = new HashMap<String, String>();
	
    
    Uri selectUri = ContentUris.withAppendedId(ContentDescriptorObj.ViewUserActivities.URI_VIEW_USER_ACTIVITIES,UserActivityId);
    
    Cursor cur = getActivity().getContentResolver().query(selectUri, null, null, null, null);
	
    final int INDX_COL_SUM_ENERGY = cur.getColumnIndex(ContentDescriptorObj.ViewUserActivities.Columns.SUM_ENERGY);
    final int INDX_COL_SUM_LIPIDS = cur.getColumnIndex(ContentDescriptorObj.ViewUserActivities.Columns.SUM_LIPIDS);
    final int INDX_COL_SUM_GLUCIDS = cur.getColumnIndex(ContentDescriptorObj.ViewUserActivities.Columns.SUM_GLUCIDS);
    final int INDX_COL_SUM_PROTEINS = cur.getColumnIndex(ContentDescriptorObj.ViewUserActivities.Columns.SUM_PROTEINS);
   
         
         if (cur.moveToFirst()) {
        
        	 
        	   	DecimalFormat decimalFormat = (DecimalFormat)DecimalFormat.getInstance();
               	decimalFormat.applyPattern("000.0");
               	DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                dfs.setDecimalSeparator('.');
                decimalFormat.setDecimalFormatSymbols( dfs );
               
        	 do {
        		 map = new HashMap<String, String>();
              	 
        		 decimalFormat.applyPattern("###0.0");
        		 map.put("sumEnergy", decimalFormat.format(cur.getFloat(INDX_COL_SUM_ENERGY)));
              	 
        		 decimalFormat.applyPattern("##0.0");
              	              	
              	 map.put("sumGlucids", "g:"+decimalFormat.format(cur.getFloat(INDX_COL_SUM_GLUCIDS)));
              	 map.put("sumLipids", "l:"+decimalFormat.format(cur.getFloat(INDX_COL_SUM_LIPIDS)));
              	 map.put("sumProteins", "p:"+decimalFormat.format(cur.getFloat(INDX_COL_SUM_PROTEINS)));
             } while (cur.moveToNext());
        	
        }
         cur.close();
         return map;
	
}




public void refreshScreen(){
	
	
	
	
	cleanList();
		generateList();
	}
 



}
 
 
 
 	
 
 
 
 
 
 
 
 
 
