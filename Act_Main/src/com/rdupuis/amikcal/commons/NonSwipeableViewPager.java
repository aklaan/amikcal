package com.rdupuis.amikcal.commons;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NonSwipeableViewPager extends ViewPager{

	public NonSwipeableViewPager(Context context) {
		super(context);
		
	}

	public NonSwipeableViewPager(Context context, AttributeSet attrs) {
		super(context,attrs);
		
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0){
		// ne jamais autotiser le changement de page manuellement
		return false;
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		// ne jamais autotiser le changement de page manuellement
		return false;
		
	}
	
	
	
}
