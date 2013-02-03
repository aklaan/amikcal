package com.rdupuis.amikcal.UserActivity;





import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.Commons.MyFragmentPagerAdapter;
import com.rdupuis.amikcal.Commons.ToolBox;

import com.rdupuis.amikcal.Data.ContentDescriptorObj;
import com.rdupuis.amikcal.Tools.NumericPad.NumericPadFragment;
import com.rdupuis.amikcal.UserActivityComponent.Act_UserActivityComponentList;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import android.content.ContentUris;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;



public class UserActivityList_FragmentsSliderActivity extends FragmentActivity 
implements NumericPadFragment.OnClickButtonOK , 
NumericPadFragment.OnClickButtonCancel{

	private FragmentPagerAdapter mPagerAdapter;

	private Calendar    currentDay;
	private Calendar    precDay;
	private Calendar    nextDay;
	private int         currentTypeOfList;
	private Intent      mIntent ;
	private Resources   mResources;
	private  int        idFragment1;
	private  int        idFragment2;
	private  int        idFragment3;
	private final static int  ACTY_COMPONENT_LIST = 0;
	private final static int  ACTY_USER_ACTIVITY_EDITOR = 1;
   private ViewPager mViewPager; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.viewpager);

		 
	        mIntent = getIntent();
	        mResources = getResources();
	        
	        //* on tente de récupérer une date si l'intent nous en a envoyé une
	        try {
	        	currentDay=ToolBox.parseCalendar(mIntent.getStringExtra(mResources.getString(R.string.INTENT_IN_USER_ACTIVITY_LIST_DAY_OF_ACTIVITIES)));
	        }
	        catch (Exception e){
	        	currentDay  =Calendar.getInstance();
	        };
	        
	        //* on tente de récupérer le type de liste à afficher si l'intent nous en a envoyé un
	        try {
	        	currentTypeOfList =Integer.parseInt(mIntent.getStringExtra(mResources.getString(R.string.INTENT_IN_USER_ACTIVITY_LIST_TYPE_OF_LIST)));
	            }
	        catch (Exception e){
	            	currentTypeOfList=mResources.getInteger(R.integer.USER_ACTIVITY_LIST_ACTIVITY);
	     
	            };
	            
	            precDay = (Calendar) currentDay.clone();
	            precDay.add(Calendar.DATE, -1);
	            nextDay = (Calendar) currentDay.clone();
	            nextDay.add(Calendar.DATE, 1);
	        //* rafraichissement de l'écran.    
	       // refreshScreen();
	        
		
		
		
		// Création de la liste de Fragments que fera défiler le PagerAdapter
		List<Fragment> fragments = new Vector<Fragment>();
		
		Bundle bundle1=new Bundle();
		Bundle bundle2=new Bundle();
		Bundle bundle3=new Bundle();
		
		bundle1.putString("date", ToolBox.getSqlDate(precDay));
		// Ajout des Fragments dans la liste
		Fragment f = Fragment.instantiate(this,Frag_UserActivityList.class.getName(),bundle1);

		idFragment1 = f.getId();
		fragments.add(f);
		bundle2.putString("date", ToolBox.getSqlDate(currentDay));
		fragments.add(Fragment.instantiate(this,Frag_UserActivityList.class.getName(),bundle2));
		idFragment2 = f.getId();
		bundle3.putString("date", ToolBox.getSqlDate(nextDay));
		fragments.add(Fragment.instantiate(this,Frag_UserActivityList.class.getName(),bundle3));
		idFragment3 = f.getId();
		// Création de l'adapter qui s'occupera de l'affichage de la liste de
		// Fragments
		this.mPagerAdapter = new MyFragmentPagerAdapter(super.getSupportFragmentManager(), fragments);

		
		
		
		 mViewPager = (ViewPager) super.findViewById(R.id.viewpager);
		// Affectation de l'adapter au ViewPager
		 mViewPager.setAdapter(this.mPagerAdapter);
		
		 
		 
		 /*
      	 
		 
		 bundle2.putString("date", ToolBox.getSqlDate(currentDay));
		 Fragment f2 = Fragment.instantiate(this,Frag_UserActivityList.class.getName(),bundle2);
		 ft.add(R.id.viewpager,f2,"datejour");
		
		 
		 bundle3.putString("date", ToolBox.getSqlDate(nextDay));
		 Fragment f3 = Fragment.instantiate(this,Frag_UserActivityList.class.getName(),bundle3);
		 ft.add(R.id.viewpager,f3,"datenext");
		 
		 
		 ft.commitAllowingStateLoss();
		 */
		 

		// idFragment2=fm.findFragmentByTag("datejour").getId();
		// idFragment3=fm.findFragmentByTag("datenext").getId();
		 
		 mViewPager.setOnPageChangeListener(new OnPageChangeListener(){

			 
			 
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				Log.i("onPageScrollStateChanged", String.valueOf(arg0));
				
				
				if (arg0 ==  ViewPager.SCROLL_STATE_SETTLING){
					Log.i("aaaaaaaaaa", String.valueOf(arg0));
					 
					if (UserActivityList_FragmentsSliderActivity.this.mViewPager.getCurrentItem()==2){
						Log.i("aaaaaaaaaa", String.valueOf(arg0));
					UserActivityList_FragmentsSliderActivity.this.mPagerAdapter.getItem(1).getArguments().putString("date","2012-11-21");
					UserActivityList_FragmentsSliderActivity.this.mPagerAdapter.getItem(2).getArguments().putString("date","2012-11-22");
						
					UserActivityList_FragmentsSliderActivity.this.mPagerAdapter.notifyDataSetChanged();
				}
				}
			}

			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				Log.i("onPageSelected", String.valueOf(arg0));
				if (arg0 ==2){
				UserActivityList_FragmentsSliderActivity.this.mViewPager.setCurrentItem(1,false);
				}
				//	FragmentManager fm = UserActivityList_FragmentsSliderActivity.this.getSupportFragmentManager();
					//fm.findFragmentById(UserActivityList_FragmentsSliderActivity.this.idFragment1);		
					
				//	Bundle bundle3=new Bundle();
					// bundle3.putString("date", ToolBox.getSqlDate(nextDay));
					// Fragment newFragment = Fragment.instantiate(UserActivityList_FragmentsSliderActivity.this,Frag_UserActivityList.class.getName(),bundle3);
					
					
				//	FragmentTransaction ft = fm.beginTransaction();
				//	ft.remove(mPagerAdapter.getItem(1));
					//ft.add(R.id.viewpager,newFragment);
					
					//ft.replace(fm.findFragmentById(UserActivityList_FragmentsSliderActivity.this.idFragment2).getId(),newFragment,"datejour");
					
					//Fragment oldFragment = fm.findFragmentById(UserActivityList_FragmentsSliderActivity.this.idFragment2);
					//oldFragment=null;
					
					//UserActivityList_FragmentsSliderActivity.this.idFragment2=fm.findFragmentByTag("datejour").getId();
					
					//ft.commitAllowingStateLoss();
                    //mFragmentAtPos0 = NextFragment.newInstance();
					
				
					
				
				
				
				
				
				
				

			}

			/**
			 * onPageScrolled est appelé lorsque la page est en mouvement
			 */
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				//Log.i("onPageScrolled", "yes");
				
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
    	
    	
    	switch (requestCode) {
 	
			case  ACTY_USER_ACTIVITY_EDITOR      :
			
			 	if (resultCode == RESULT_OK){
			 	mPagerAdapter.notifyDataSetChanged();
			    }
				break;
			
			case  ACTY_COMPONENT_LIST     :
				
			 	if (resultCode == RESULT_OK){
		
			    }
				break;	
				
			default :break;
			}
    	//refreshScreen();
    }
    
    /**
  	* 
  	* @param id Identifiant de l'activitée utilisateur séléctionée
  	*/
  	public void onClickActivity(String id){
  		Intent intent = new Intent(this,Act_UserActivityComponentList.class);
  		intent.putExtra(mResources.getString(R.string.INTENT_IN_USER_ACTIVITY_COMPONENT_LIST_ID_OF_USER_ACTIVITY), id);
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
	 	intent.putExtra("idFragment", String.valueOf(f.getId()));
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