
package com.rdupuis.amikcal.commons;

import java.util.ArrayList;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class MyFragmentPagerAdapter extends FragmentPagerAdapter  

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
	  /*  if (object instanceof FirstPageFragment && mFragmentAtPos0 instanceof NextFragment)
	        return POSITION_NONE;
	    return POSITION_UNCHANGED;
	    */
		return POSITION_NONE;
	
	}

	

}




