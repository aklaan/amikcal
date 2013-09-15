package com.rdupuis.amikcal.useractivitycomponent;

import com.rdupuis.amikcal.R;

import com.rdupuis.amikcal.commons.MyAdapter;
import com.rdupuis.amikcal.commons.MyFragmentPagerAdapter;
import com.rdupuis.amikcal.commons.numericpad.Frag_NumericPad;
import com.rdupuis.amikcal.commons.numericpad.Frag_NumericPad.Frag_NumericPadListeners;
import com.rdupuis.amikcal.energy.Frag_EnergyList;
import com.rdupuis.amikcal.unitofmeasure.Frag_UnitOfMeasureList;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Act_UserActivityComponentSlider extends FragmentActivity implements Frag_NumericPadListeners{

	ViewPager mViewPager;
	MyFragmentPagerAdapter mPagerAdapter;
	ArrayList<Fragment> mArrayfragments;
	/**
	 * Par cet intent, on va recevoir les infos des items a éditer (id
	 * energy,qté,id unit)
	 */
	private Intent mIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.view_choose_energy_for_uac_fragment_slider);
		

		// assignation du viewpager
		mViewPager = (ViewPager) findViewById(R.id.view_uac_fragment_slider_viewpager);

		// On souhaite conserver en memoire 3 pages en meme temps.
		//mViewPager.setOffscreenPageLimit(3);

		// Initialisation du tableau des Fragments a faire défiler dans le
		// PagerAdapter
		
		mArrayfragments = new ArrayList<Fragment>();
		mArrayfragments.add(Fragment.instantiate(this,Frag_EnergyList.class.getName()));
		mArrayfragments.add(Fragment.instantiate(this,Frag_UnitOfMeasureList.class.getName()));
		mArrayfragments.add(Fragment.instantiate(this,Frag_NumericPad.class.getName()));
		
	
		mPagerAdapter = new MyFragmentPagerAdapter(
				super.getSupportFragmentManager(), mArrayfragments);
		
		// Attribution de l'adapter au ViewPager
		mViewPager.setAdapter(mPagerAdapter);
		
		//positioner le viewpager sur le premier fragment à afficher
		mViewPager.setCurrentItem(0, false);

		
		
	
		/**
		 * Ajout d'un listener pour modifier la date courante a chaque
		 * changement de page
		 */

	}

	/**
	 * Gestion des boutons menu dans l'action BAR
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar_activities, menu);
		return true;

	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// invalidateOptionsMenu va appeller la méthode onPrepareOptionsMenu();
		invalidateOptionsMenu();
		return true;
	}

	/**
	 * La fonction NumericPadListener_OnClick_btn_Number est imposée par l'utilisation du fragment 
	 * Frag_NumericPad.
	 * 
	 * lorsque l'on clique sur un bouton du Frag_NumericPad, ce n'est pas le fragment qui réagit, mais l'activity
	 * il faut donc que l'activity puisse capter le okclick puis appeler la méthode adéquat dans le fragment.
	 *	 
	 * ce que je trouve pas terrible, c'est que le programmeur doit savoir quel est la méthode corespondante a appeller
	 * dans le fragement. a ma connaissance cette information n'est pas déductible.  
	 *
	 */
	
	public void NumericPadListener_OnClick_btn_Number(View v) {
		Frag_NumericPad fragment = (Frag_NumericPad) mPagerAdapter.getItem(2);
	        if (fragment != null && fragment.isVisible()) {
	          fragment.OnClick_Btn_Number(v.getId());
	        } 
	}

	
	public void NumericPadListener_OnClick_btn_Erase(View v) {
		// TODO Auto-generated method stub
		
	}

	
	public void NumericPadListener_OnClick_btn_Back(View v) {
		// TODO Auto-generated method stub
		
	}

	
	public void NumericPadListener_OnClick_btn_Ok(View v) {
		// TODO Auto-generated method stub
		
	}

	
	public void NumericPadListener_OnClick_btn_Cancel(View view) {
		// TODO Auto-generated method stub
		
	}

}
