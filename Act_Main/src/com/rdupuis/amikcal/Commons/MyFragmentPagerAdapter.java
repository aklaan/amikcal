
package com.rdupuis.amikcal.Commons;

import java.util.List;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class MyFragmentPagerAdapter extends FragmentPagerAdapter 

{

	
	private final List<?> fragmentsList;

	//On fournit à l'adapter la liste des fragments à afficher
	public MyFragmentPagerAdapter(FragmentManager fm, List<?> fragments) {
		super(fm);
		this.fragmentsList = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		return (Fragment) this.fragmentsList.get(position);
	}

	@Override
	public int getCount() {
		return this.fragmentsList.size();
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