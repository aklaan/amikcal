package com.rdupuis.amikcal.Commons;





import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.Tools.NumericPad.NumericPadFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class FragmentsSliderActivity extends FragmentActivity 
implements NumericPadFragment.OnClickButtonOK , 
NumericPadFragment.OnClickButtonCancel{

	private PagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.viewpager);

		// Création de la liste de Fragments que fera défiler le PagerAdapter
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();

		// Ajout des Fragments dans la liste
		fragments.add(Fragment.instantiate(this,NumericPadFragment.class.getName()));
		fragments.add(Fragment.instantiate(this,NumericPadFragment.class.getName()));
		fragments.add(Fragment.instantiate(this,NumericPadFragment.class.getName()));

		// Création de l'adapter qui s'occupera de l'affichage de la liste de
		// Fragments
		this.mPagerAdapter = new MyFragmentPagerAdapter(super.getSupportFragmentManager(), fragments);

		ViewPager pager = (ViewPager) super.findViewById(R.id.viewpager);
		// Affectation de l'adapter au ViewPager
		pager.setAdapter(this.mPagerAdapter);
	}

	public void onClickButtonCancel() {
		// TODO Auto-generated method stub
		
	}

	public void onClickButtonOK(String result) {
		// TODO Auto-generated method stub
		
	}
}