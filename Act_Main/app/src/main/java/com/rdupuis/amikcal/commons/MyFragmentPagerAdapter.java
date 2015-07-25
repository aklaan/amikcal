
package com.rdupuis.amikcal.commons;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter  

{
 
	    private ArrayList<Fragment> myFragments = new ArrayList<Fragment>();
	   
	
	
	//On fournit à l'adapter la liste des fragments à afficher
	public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
		super(fm);
       
		myFragments = fragments;
	
	}

	

	@Override
	public Fragment getItem(int position) {
				
		return (Fragment) myFragments.get(position);
	}

	@Override
	public int getCount() {
		return this.myFragments.size();
	}

	@Override
	public int getItemPosition(Object object)
	{
	  
		/*
		 * By default, getItemPosition() returns POSITION_UNCHANGED,
		 * which means, "This object is fine where it is, don't destroy or remove it."
		 * Returning POSITION_NONE fixes the problem by instead saying,
		 * "This object is no longer an item I'm displaying, remove it." 
		 * So it has the effect of removing and recreating every single item 
		 * in your adapter.
		 */
		
		/*  if (object instanceof FirstPageFragment && mFragmentAtPos0 instanceof NextFragment)
	        return POSITION_NONE;
	    return POSITION_UNCHANGED;*/
		
	    
		return POSITION_NONE;
	
	}

	

}




