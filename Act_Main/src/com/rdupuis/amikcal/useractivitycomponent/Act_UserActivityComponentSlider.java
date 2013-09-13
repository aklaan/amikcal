package com.rdupuis.amikcal.useractivitycomponent;

import com.rdupuis.amikcal.R;

import com.rdupuis.amikcal.commons.MyFragmentPagerAdapter;
import com.rdupuis.amikcal.commons.numericpad.Frag_NumericPad;
import com.rdupuis.amikcal.energy.Frag_EnergyList;
import com.rdupuis.amikcal.unitofmeasure.Frag_UnitOfMeasureList;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class Act_UserActivityComponentSlider extends FragmentActivity {

	ViewPager mViewPager;
	PagerAdapter mPagerAdapter;
	ArrayList<Fragment> mArrayfragments;
	/**
	 * Par cet intent, on va recevoir les infos des items a éditer (id
	 * energy,qté,id unit)
	 */
	private Intent mIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.view_uac_fragment_slider);

		// assignation du viewpager
		mViewPager = (ViewPager) findViewById(R.id.view_uac_fragment_slider_viewpager);

		// On souhaite conserver en memoire 3 pages en meme temps.
		mViewPager.setOffscreenPageLimit(3);

		// Initialisation du tableau des Fragments a faire défiler dans le
		// PagerAdapter
		mArrayfragments = new ArrayList<Fragment>();

		//le premier fragment est la liste des aliments / menus 
		mArrayfragments.add(Fragment.instantiate(this,
				Frag_EnergyList.class.getName()));

		//le second fragment est la liste des unitées
		mArrayfragments.add(Fragment.instantiate(this,
				Frag_UnitOfMeasureList.class.getName()));
		
		//le troisième fragment est le pavé numérique
		mArrayfragments.add(Fragment.instantiate(this,
				Frag_NumericPad.class.getName()));
		
		//alimenter les fragment dans le d'adapter qui va les gérer
		mPagerAdapter = new MyFragmentPagerAdapter(
				super.getSupportFragmentManager(), mArrayfragments);

		//Attribution de l'adapter au ViewPager pour affichage
		mViewPager.setAdapter(this.mPagerAdapter);

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

}
