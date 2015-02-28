package com.rdupuis.amikcal.useractivitycomponent;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.MyFragmentPagerAdapter;
import com.rdupuis.amikcal.commons.ZoomOutPageTransformer;
import com.rdupuis.amikcal.commons.numericpad.Frag_NumericPad;
import com.rdupuis.amikcal.commons.numericpad.NumericPadListeners;
import com.rdupuis.amikcal.energy.Frag_EnergyList;
import com.rdupuis.amikcal.unity.Frag_UnityList;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Act_UserActivityComponentSlider extends FragmentActivity implements NumericPadListeners{

	final int ENERGY_LIST_POSITION = 0;
	final int UNITS_LIST_POSITION = 1;
	final int NUMERICPAD_POSITION = 2;
	
	ViewPager mViewPager;
	MyFragmentPagerAdapter mPagerAdapter;
	ArrayList<Fragment> mArrayfragments;
	int currentItem = 0;
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
		mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

		// Initialisation du tableau des Fragments a faire défiler dans le
		// PagerAdapter
		
		mArrayfragments = new ArrayList<Fragment>();
		mArrayfragments.add(ENERGY_LIST_POSITION,Fragment.instantiate(this,Frag_EnergyList.class.getName()));
		mArrayfragments.add(UNITS_LIST_POSITION,Fragment.instantiate(this,Frag_UnityList.class.getName()));
		mArrayfragments.add(NUMERICPAD_POSITION,Fragment.instantiate(this,Frag_NumericPad.class.getName()));
		
		mPagerAdapter = new MyFragmentPagerAdapter(
				super.getSupportFragmentManager(), mArrayfragments);
		
		// Attribution de l'adapter au ViewPager
		mViewPager.setAdapter(mPagerAdapter);
		
		//positioner le viewpager sur le premier fragment à afficher
		// le second paramètre de la fonction à TRUE indique que le Viewpager doit jouer l'animation
		// de transition. lorqu'il est à FALSE, on passe d'une vue à l'autre tout de suite.
		mViewPager.setCurrentItem(currentItem, true);

			
	
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
	
	
	/**
	 * Si appui sur un bouton "nombre" on appel la fonction OnClick_Btn_Number du fragment NumericPad
	 */
	public void NumericPadListener_OnClick_btn_Number(View v) {
		Frag_NumericPad numericPad = (Frag_NumericPad) mPagerAdapter.getItem(NUMERICPAD_POSITION);
	        if (numericPad != null && numericPad.isVisible()) {
	        	numericPad.OnClick_Btn_Number(v.getId());
	        } 
	}

	/**
	 * Si appui sur un bouton "Erase" on appel la fonction OnClick_Btn_Erase du fragment NumericPad
	 */
	public void NumericPadListener_OnClick_btn_Erase(View v) {
		Frag_NumericPad numericPad = (Frag_NumericPad) mPagerAdapter.getItem(NUMERICPAD_POSITION);
        if (numericPad != null && numericPad.isVisible()) {
        	numericPad.OnClick_Btn_Erase();
        }
		
	}

	
	public void NumericPadListener_OnClick_btn_Back(View v) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Si appui sur un bouton "OK" on appel la fonction OnClick_Btn_Ok du fragment NumericPad
	 */
	public void NumericPadListener_OnClick_btn_Ok(View v) {
		Frag_NumericPad numericPad = (Frag_NumericPad) mPagerAdapter.getItem(NUMERICPAD_POSITION);
        if (numericPad != null && numericPad.isVisible()) {
        	numericPad.OnClick_Btn_Ok();
        }
		
	}

	/**
	 * Si appui sur un bouton "Cancel" on appel la fonction OnClick_Btn_Cancel du fragment NumericPad
	 */
	public void NumericPadListener_OnClick_btn_Cancel(View view) {
		Frag_NumericPad numericPad = (Frag_NumericPad) mPagerAdapter.getItem(NUMERICPAD_POSITION);
        if (numericPad != null && numericPad.isVisible()) {
        	numericPad.OnClick_Btn_Cancel();
        }
		
	}

	public void onclicknext(View v){
		
		currentItem = (currentItem+1>3)? 0:currentItem+1;
		// le second paramètre de la fonction à TRUE indique que le Viewpager doit jouer l'animation
		// de transition. lorqu'il est à FALSE, on passe d'une vue à l'autre tout de suite.
		mViewPager.setCurrentItem(currentItem, true);
	}
	
}
