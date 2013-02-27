
package com.rdupuis.amikcal.Commons;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.UserActivity.Frag_UserActivityList;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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




