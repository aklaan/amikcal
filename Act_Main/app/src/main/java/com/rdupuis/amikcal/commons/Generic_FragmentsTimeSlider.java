package com.rdupuis.amikcal.commons;

import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

import com.rdupuis.amikcal.R;

/**
 * 
 * @author Rodolphe
 *
 * cet objet est une base commune pour permettre de faire
 * un slide de fragments au jour le jour
 */


public class Generic_FragmentsTimeSlider extends FragmentActivity {

	// Identifiant des 3 groupes de Fragments qui vont permettre 
	// le glissement dans le temps
	public enum GroupName {
		GROUP_A, GROUP_B, GROUP_C
	};

	private TimeFragmentPagerAdapter mPagerAdapter;

	private int mCurrentPage;
	private ArrayList<Calendar> mArrayCalendar;
	private ArrayList<Bundle> mArrayBundle;
	private ArrayList<TimeSlidableFragment> mArrayfragments;
	
	private ViewPager mViewPager;
	

	public ViewPager getViewPager() {
		return mViewPager;
	}

	public ArrayList<Calendar> getArrayCalendar() {
		return mArrayCalendar;
	}

	/**
	 * 
	 * @return
	 */
	public int getCurrentPage() {
		return mCurrentPage;
	}

	/**
	 * 
	 * @param mCurrentPage
	 */
	public void setCurrentPage(int currentPageNumber) {
		this.mCurrentPage = currentPageNumber;
	}

	/**
	 * @Override
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.view_fragment_slider);

		

		// Initialisation Tableau des dates a afficher
		mArrayCalendar = new ArrayList<Calendar>();

		// Initialisation du Tableau des bundle a affecter aux fragments
		mArrayBundle = new ArrayList<Bundle>();

		// Initialisation du tableau des Fragments a faire défiler dans le
		// PagerAdapter
		mArrayfragments = new ArrayList<TimeSlidableFragment>();

		// instanciation d'un ViewPager
		mViewPager = (ViewPager) super.findViewById(R.id.viewpager);

		// Creation de l'adapter qui s'occupera de l'affichage des fragments
		// dans le ViewPager
		this.mPagerAdapter = new TimeFragmentPagerAdapter(
				super.getSupportFragmentManager(), mArrayfragments);

		// Attribution de l'adapter au ViewPager
		mViewPager.setAdapter(this.mPagerAdapter);

		// Ajout d'un ecouteur sur le changement de page
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			boolean isPageDown = false;
			boolean isPageUp = false;

			public void onPageScrollStateChanged(int state) {

				Log.i("onPageScrollStateChanged", String.valueOf(state));
				// Log.i("currentDay", ToolBox.getSqlDate(currentDay));

				switch (state) {
				case ViewPager.SCROLL_STATE_IDLE:
					if (isPageUp) {

						if (mViewPager.getCurrentItem() == 5) {
							//on change de page sans faire d'animation scroll
							mViewPager.setCurrentItem(7, false);

							// mettre à jour les pages 0,1,2
							// on ajoute 3 jours
							updateGroup(GroupName.GROUP_A, +3);
						}
						;

						if (mViewPager.getCurrentItem() == 8) {
							mViewPager.setCurrentItem(1, false);
							// mettre à jour les pages 3,4,5
							// on ajoute 3 jours
							updateGroup(GroupName.GROUP_B, +3);
						}
						;

						if (mViewPager.getCurrentItem() == 2) {
							mViewPager.setCurrentItem(4, false);
							// mettre à jour les pages 6,7,8
							// on ajoute 3 jours
							updateGroup(GroupName.GROUP_C, +3);
						}
						;
						isPageUp = false;
					}
					;

					if (isPageDown) {

						if (mViewPager.getCurrentItem() == 0) {
							mViewPager.setCurrentItem(7, false);
							// mettre à jour les pages 3,4,5
							// on retire 3 jours
							updateGroup(GroupName.GROUP_B, -3);
						}
						;

						if (mViewPager.getCurrentItem() == 3) {
							mViewPager.setCurrentItem(1, false);
							// mettre à jour les pages 6,7,8
							// on retire 3 jours
							updateGroup(GroupName.GROUP_C, -3);
						}
						;

						if (mViewPager.getCurrentItem() == 6) {
							mViewPager.setCurrentItem(4, false);
							// mettre à jour les pages 0,1,2
							// on retire 3 jours
							updateGroup(GroupName.GROUP_A, -3);
						}
						;
						isPageDown = false;
					}
					;
					onUpdateGroup();
					break;
				case ViewPager.SCROLL_STATE_DRAGGING:
					break;
				case ViewPager.SCROLL_STATE_SETTLING:

					break;
				} // fin switch

			} // fin onPageScrollStateChanged

			/**
			 * Lorsque l'on change de page, on regarde si l'utilisateur a ete
			 * vers la droite (avance dans le temps -> UP) ou vers la gauche
			 * (recul dans le temps -> DOWN)
			 */

			public void onPageSelected(int pageNumber) {
				Log.i("onPageSelected", String.valueOf(pageNumber));
				switch (pageNumber) {
				case 0:
				case 3:
				case 6:
					isPageDown = true;
					break;
				case 2:
				case 5:
				case 8:
					isPageUp = true;
					break;
				}
			}

			/**
			 * onPageScrolled est appele lorsque la page est en mouvement aucune
			 * acion n'est prevue pour le moment.
			 */
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// Log.i("onPageScrolled", "yes");
			}

		}); // fin du listener.

	}

	/*
	 * la méthode onUpdateGroup est définie pour les enfants de la classe elle
	 * va leur permettre d'éffectuer des opérations personnalisé lorsque l'on
	 * rafraichi le groupe.
	 */

	protected void onUpdateGroup() {

	};

	public void createFragmentsGroups(Calendar wStartingDay,
			String wFragmentClassName) {

		// Log.d("creatFragment",wFragmentClassName);
		mArrayCalendar.clear();
		mArrayBundle.clear();
		mArrayfragments.clear();

		// initialisation dates des 9 pages qui representent les 3
		// groupes de cas possibles

		// Group A Group B Group C
		// _0_ | _1_ | 2 | _3_| 4 | _5_ | 6 | _7_ | _8_ |
		// j-2 | j-1 | J | j-1| J | J+1 | J | J+1 | J+2 |

		for (int i = 0; i <= 8; i++) {
			mArrayCalendar.add(i, (Calendar) wStartingDay.clone());
			mArrayBundle.add(i, new Bundle());

			switch (i) {
			case 0:
				mArrayCalendar.get(i).add(Calendar.DATE, -2);
				break;

			case 1:
			case 3:
				mArrayCalendar.get(i).add(Calendar.DATE, -1);
				break;

			case 5:
			case 7:
				mArrayCalendar.get(i).add(Calendar.DATE, +1);
				break;

			case 8:
				mArrayCalendar.get(i).add(Calendar.DATE, +2);
				break;
			}
			;

			// Pour chaques fragments, on prepare un bundle qui contient
			// la date a afficher et le numero de page
			mArrayBundle.get(i).putString(TimeSlidableFragment.INTENT_INPUT____DAY,
					ToolBox.getSqlDate(mArrayCalendar.get(i)));
			mArrayBundle.get(i).putString("page", String.valueOf(i));

			// On instancie les 9 fragments avec leur bundle respectifs.
			// Ces fragments sont inseres dans le tableau
			// mArrayfragments.
			mArrayfragments.add((TimeSlidableFragment) TimeSlidableFragment.instantiate(this, wFragmentClassName,
					mArrayBundle.get(i)));

		}
		;

		// Creation de l'adapter qui s'occupera de l'affichage des fragments
		// dans le ViewPager
		mPagerAdapter = null;
		mPagerAdapter = new TimeFragmentPagerAdapter(
				super.getSupportFragmentManager(), mArrayfragments);

		// Attribution de l'adapter au ViewPager
		mViewPager.setAdapter(mPagerAdapter);
		// mViewPager.setSaveEnabled(false);
		// mPagerAdapter.notifyDataSetChanged();
	}

	/**
	 * 
	 * @param groupe
	 * @param value
	 */
	private void updateGroup(GroupName groupe, int addValue) {

		int page1 = 0;
		int page2 = 0;
		int page3 = 0;

		FragmentManager fm = Generic_FragmentsTimeSlider.this
				.getSupportFragmentManager();

		// on détermine les index où se trouvent les dates du groupe
		// à modifier.
		switch (groupe) {
		case GROUP_A:
			page1 = 0;
			break;
		case GROUP_B:
			page1 = 3;
			break;
		case GROUP_C:
			page1 = 6;
			break;
		}
		;

		page2 = page1 + 1;
		page3 = page2 + 1;

		// on ajoute le nombre de jour souhaité aux 3 dates du groupe.
		mArrayCalendar.get(page1).add(Calendar.DATE, addValue);
		mArrayCalendar.get(page2).add(Calendar.DATE, addValue);
		mArrayCalendar.get(page3).add(Calendar.DATE, addValue);

		// On redéfinit la prorpiété "date" des fragments.
		mPagerAdapter
				.getItem(page1)
				.getArguments()
				.putString(TimeSlidableFragment.INTENT_INPUT____DAY,
						ToolBox.getSqlDate(mArrayCalendar.get(page1)));
		mPagerAdapter
				.getItem(page2)
				.getArguments()
				.putString(TimeSlidableFragment.INTENT_INPUT____DAY,
						ToolBox.getSqlDate(mArrayCalendar.get(page2)));
		mPagerAdapter
				.getItem(page3)
				.getArguments()
				.putString(TimeSlidableFragment.INTENT_INPUT____DAY,
						ToolBox.getSqlDate(mArrayCalendar.get(page3)));

		// l'utilisation d'un notifyDataSetChanged provoque un blink
		// désagréable
		// car la fonction efface tout et réaffiche tout.
		// mPagerAdapter.notifyDataSetChanged();

		// Pour actualiser la vue sans Blink, je détache les vues de
		// fragments
		// et je les réattaches

		// début de l'opération on fige l'affichage.
		FragmentTransaction ft = fm.beginTransaction();

		// je détache et réattache le premier fragment
		ft.detach(mPagerAdapter.getItem(page1));
		ft.attach(mPagerAdapter.getItem(page1));

		// je détache et réattache le second fragment
		ft.detach(mPagerAdapter.getItem(page2));
		ft.attach(mPagerAdapter.getItem(page2));

		// je détache et réattache le troisième fragment
		ft.detach(mPagerAdapter.getItem(page3));
		ft.attach(mPagerAdapter.getItem(page3));

		// Commit pour prendre en compte les modification dans la vue.
		ft.commit();

	}

	/**
	 * 
	 * @param groupe
	 * @param value
	 */
	public void switchFragment() {

		FragmentManager fm = Generic_FragmentsTimeSlider.this
				.getSupportFragmentManager();

		/*******************************
		 * pour plus tard mPagerAdapter .getItem(page3) .getArguments()
		 * .putString("date", ToolBox.getSqlDate(mArrayCalendar.get(page3)));
		 ****************** */

		// début de l'opération on fige l'affichage.
		FragmentTransaction ft = fm.beginTransaction();

		for (int i = 0; i < mPagerAdapter.getCount(); i++) {
			ft.detach(mPagerAdapter.getItem(i));
			ft.attach(mPagerAdapter.getItem(i));
		}

		// Commit pour prendre en compte les modification dans la vue.
		ft.commit();
		mPagerAdapter.notifyDataSetChanged();
	}

	public void onClickButtonCancel() {
		// TODO Auto-generated method stub

	}

	public void onClickButtonOK(String result) {
		// TODO Auto-generated method stub

	}

}
