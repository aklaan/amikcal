package com.rdupuis.amikcal.UserActivity;





import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.Commons.MyFragmentPagerAdapter;
import com.rdupuis.amikcal.Commons.ToolBox;
import com.rdupuis.amikcal.Commons.NumericPad.NumericPadFragment;

import com.rdupuis.amikcal.Data.ContentDescriptorObj;
import com.rdupuis.amikcal.UserActivityComponent.Act_UserActivityComponentList;

import java.util.ArrayList;
import java.util.Calendar;
import android.content.ContentUris;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;




public class UserActivityList_FragmentsSliderActivity extends FragmentActivity 
implements NumericPadFragment.OnClickButtonOK , 
NumericPadFragment.OnClickButtonCancel{

	public enum Groupe { A, B, C };
	
	private MyFragmentPagerAdapter mPagerAdapter;
	private Calendar    currentDay;
	private int         mCurrentPage;
	private ArrayList<Calendar> mArrayCalendar;
	private ArrayList<Bundle> mArrayBundle;
	private ArrayList<Fragment> mArrayfragments;
	private Intent      mIntent ;
	private Resources   mResources;

	private final static int  ACTY_COMPONENT_LIST = 0;
	private final static int  ACTY_USER_ACTIVITY_EDITOR = 1;
   private ViewPager mViewPager; 
	
	public int getmCurrentPage() {
	return mCurrentPage;
}



public void setmCurrentPage(int mCurrentPage) {
	this.mCurrentPage = mCurrentPage;
}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.viewpager);

		// Création de la liste de Fragments que fera défiler le PagerAdapter
		mArrayCalendar = new ArrayList<Calendar>();
		mArrayBundle = new ArrayList<Bundle> ();
		mArrayfragments = new ArrayList<Fragment>();
 		
	    mIntent = getIntent();
	    mResources = getResources();
	        
	        //* on tente de récupérer une date si l'intent nous en a envoyé une
	    try {
	      	currentDay=ToolBox.parseCalendar(mIntent.getStringExtra(mResources.getString(R.string.INTENT_IN_USER_ACTIVITY_LIST_DAY_OF_ACTIVITIES)));
	    }
	    catch (Exception e){
	       	currentDay  =Calendar.getInstance();
	    };
	        
	            
	            // initialisation dates des 9 pages qui représentent les 3 groupes de cas possibles 
	        	
	        	//      Group A           Group B           Group C
	            //  0  |  1  | 2 |      3 | 4 |  5  |     6 |  7  |  8  |
	        	// j-2 | j-1 | J |     j-1| J | J+1 |     J | J+1 | J+2 |
	        
	    for (int i=0;i<=8;i++){
	       	mArrayCalendar.add(i, (Calendar) currentDay.clone()); 
	       	mArrayBundle.add(i, new Bundle());
	            	
	            switch (i){
	            	case 0:	mArrayCalendar.get(i).add(Calendar.DATE, -2);
	            	break;
	            			
	            	case 1 : case 3: mArrayCalendar.get(i).add(Calendar.DATE, -1);
	            	break;	

	            	case 5 : case 7 : mArrayCalendar.get(i).add(Calendar.DATE, +1);
	            	break;
	            			
	            	case 8:	mArrayCalendar.get(i).add(Calendar.DATE, +2);
	            	break;
	            }
	           
	            
	           // Pour chaques fragments, on prépare un bundle qui contient la date à afficher et le n° de page
	            mArrayBundle.get(i).putString("date", ToolBox.getSqlDate(mArrayCalendar.get(i)));
	            mArrayBundle.get(i).putString("page", String.valueOf(i));
	            
	            // On instancie les 9 fragments avec leur bundle respectifs. 
	            // ces fragments sont insérés dans un tableau.
	            mArrayfragments.add(Fragment.instantiate(this,Frag_UserActivityList.class.getName(),mArrayBundle.get(i)));
	            };
	           

	    mViewPager = (ViewPager) super.findViewById(R.id.viewpager);
	
		
		// Création de l'adapter qui s'occupera de l'affichage de la liste de
		// Fragments
		this.mPagerAdapter = new MyFragmentPagerAdapter(super.getSupportFragmentManager(), mArrayfragments);

		// Affectation de l'adapter au ViewPager
		 mViewPager.setAdapter(this.mPagerAdapter);
				 
				 
		// au démarrage le focus doit être sur la page 4
		 mViewPager.setCurrentItem(4,false);
		 
		 // on souhaites conserver en mémoire 9 pages en même temps.
		 mViewPager.setOffscreenPageLimit(9);
				 
		 //ajout d'un ecouteur sur le changement de page
		 mViewPager.setOnPageChangeListener(new OnPageChangeListener(){
			 
			 boolean isPageDown = false;
			 boolean isPageUp = false;
			 
			public void onPageScrollStateChanged(int state) {
				
				//Log.i("onPageScrollStateChanged", String.valueOf(state));
				//Log.i("currentDay", ToolBox.getSqlDate(currentDay));
				
			     switch (state) {
			        case ViewPager.SCROLL_STATE_IDLE:
			        	   if (isPageUp ) {
				            	
			        		   if (mViewPager.getCurrentItem()==5) {
			        			   mViewPager.setCurrentItem(7,false);
			        			  // mettre à jour les pages 0,1,2
			        			   updateGroup(Groupe.A,+3);
			        		   }
			        		   
			        		   if (mViewPager.getCurrentItem()==8) {
			        			   mViewPager.setCurrentItem(1,false);
			        			   // mettre à jour les pages 3,4,5
			        			   updateGroup(Groupe.B,+3);
			        		   }
			        		   
			        		   if (mViewPager.getCurrentItem()==2) {
			        			   mViewPager.setCurrentItem(4,false);
			        			   // mettre à jour les pages 6,7,8
			        			   updateGroup(Groupe.C,+3);
			        		   }
			        		   isPageUp = false;
				            }
			        	
			        	   
			        	   if (isPageDown ) {
				            	
			        		   if (mViewPager.getCurrentItem()==0) {
			        			   mViewPager.setCurrentItem(7,false);
			        			  // mettre à jour les pages 3,4,5
			        			   updateGroup(Groupe.B,-3);
			        		   }
			        		   
			        		   if (mViewPager.getCurrentItem()==3) {
			        			   mViewPager.setCurrentItem(1,false);
			        			   // mettre à jour les pages 6,7,8
			        			   updateGroup(Groupe.C,-3);
			        		   }
			        		   
			        		   if (mViewPager.getCurrentItem()==6) {
			        			   mViewPager.setCurrentItem(4,false);
			        			   // mettre à jour les pages 0,1,2
			        			   updateGroup(Groupe.A,-3);
			        		   }
			        		   isPageDown = false;
				            }
			        	
			            break;
			        case ViewPager.SCROLL_STATE_DRAGGING:
			            break;
			        case ViewPager.SCROLL_STATE_SETTLING:
			         
			            break;
			        }
			}
			
			private void updateGroup(Groupe a ,int value){
				int page1=0;
				int page2=0;
				int page3=0;
				
				FragmentManager fm  = UserActivityList_FragmentsSliderActivity.this.getSupportFragmentManager();

				switch(a){
				case A: page1=0;break;
				case B: page1=3;break;
				case C: page1=6;break;
				};
				
				page2 = page1 + 1;
				page3 = page2 + 1;
				
                mArrayCalendar.get(page1).add(Calendar.DATE, value);
                mArrayCalendar.get(page2).add(Calendar.DATE, value);
                mArrayCalendar.get(page3).add(Calendar.DATE, value);
                
                mPagerAdapter.getItem(page1).getArguments().putString("date",ToolBox.getSqlDate(mArrayCalendar.get(page1)));
                mPagerAdapter.getItem(page2).getArguments().putString("date",ToolBox.getSqlDate(mArrayCalendar.get(page2)));
                mPagerAdapter.getItem(page3).getArguments().putString("date",ToolBox.getSqlDate(mArrayCalendar.get(page3)));
                //mPagerAdapter.notifyDataSetChanged();
				
               // je détache les vue et je les réattaches pour acutliser les données
               // si j'utilise notifyDataSetChanged cela provoque un blink désagréable
               // car la fonction efface tout et réaffiche tout.
                FragmentTransaction ft = fm.beginTransaction();
            	ft.detach(mPagerAdapter.getItem(page1));
            	ft.attach(mPagerAdapter.getItem(page1));
            	ft.detach(mPagerAdapter.getItem(page2));
            	ft.attach(mPagerAdapter.getItem(page2));
            	ft.detach(mPagerAdapter.getItem(page3));
            	ft.attach(mPagerAdapter.getItem(page3));
			    ft.commit();
	   
				
			}
			
			
			public void onPageSelected(int pageNumber) {
		//		Log.i("onPageSelected", String.valueOf(pageNumber));
				  switch (pageNumber) {
			        case 0: case 3: case 6:
			        	isPageDown = true;
			        	break;
			        case 2: case 5: case 8:
			        	isPageUp = true;
			        	break;
				  }
			}

			/**
			 * onPageScrolled est appelé lorsque la page est en mouvement
			 */
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			
			//	Log.i("onPageScrolled", "yes");
				
			}
				
			});
	
	
	}


	
	public void onClickButtonCancel() {
		// TODO Auto-generated method stub
		
	}

	public void onClickButtonOK(String result) {
		// TODO Auto-generated method stub
		
	}
	
	
	 /**
     * Gérer les retours d'appels aux autres activitées
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
    	
    	if (resultCode == RESULT_OK){
		 	mPagerAdapter.notifyDataSetChanged();
		    }
    	
    	switch (requestCode) {
 	
			case  ACTY_USER_ACTIVITY_EDITOR      :
			
			 	
				break;
			
			case  ACTY_COMPONENT_LIST     :
				
			 	if (resultCode == RESULT_OK){

			    }
				break;	
			
		
			default :break;
			}
    	
    }
    
    /**
  	* 
  	* @param id Identifiant de l'activitée utilisateur séléctionée
  	*/
  	public void onClickActivity(String id){
  		Intent intent = new Intent(this,Act_UserActivityComponentList.class);
  		intent.putExtra(mResources.getString(R.string.INTENT_IN_USER_ACTIVITY_COMPONENT_LIST_ID_OF_USER_ACTIVITY), id);
  		intent.putExtra("page", getmCurrentPage());
  		startActivityForResult(intent, ACTY_COMPONENT_LIST);
  	}
 
  	/**
  	 * 
  	 * @param id Identifiant de l'activitée utilisateur à éditer
  	 */
  	public void onClickEdit(Fragment f, String id){
	    Intent intent = new Intent(this,Act_UserActivityEditor.class);
	 	intent.putExtra(mResources.getString(R.string.INTENT_IN_USER_ACTIVITY_EDITOR_ID_OF_THE_USER_ACTIVITY), id);
	 	intent.putExtra(mResources.getString(R.string.INTENT_IN_USER_ACTIVITY_EDITOR_DAY_OF_THE_USER_ACTIVITY), ToolBox.getSqlDate(currentDay));
	 	intent.putExtra("page", getmCurrentPage());
	 	//intent.putExtra("idFragment", String.valueOf(f.getId()));
	 	startActivityForResult(intent, ACTY_USER_ACTIVITY_EDITOR);
  	}
  	/**
  	  * 
  	  * @param id Identifiant de l'activitée utilisateur à supprimer
  	  */
  	 public void onClickDelete(String id){
  		 
  		 // vérifier s'il y a des enfants
  		 // s'il y a des enfant alerter et demander une confirmation
  		
  	    	Uri uriDelete = ContentUris.withAppendedId(ContentDescriptorObj.UserActivities.URI_DELETE_USER_ACTIVITIES,Long.parseLong(id));
  	    	this.getContentResolver().delete(uriDelete, null,null);
  		    //refreshScreen();
  	}
  	 
  	 
  	 
  	 public void onClickAdd(View v){
  		 	
  		 onClickEdit(null,null);
  	 }

  


}