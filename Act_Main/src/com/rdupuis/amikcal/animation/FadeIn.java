package com.rdupuis.amikcal.animation;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;

public class FadeIn extends Animation implements Animation.AnimationListener {
	  

		private View view;
		private float delta;
		
		
		
		public FadeIn(View v) {
			
	       view = v;		
			delta  = 1/10f;
			this.setDuration(1);
			setRepeatCount(10);
			setFillAfter(false);
			setInterpolator(new AccelerateInterpolator());
			setAnimationListener(this);
		}

		public void onAnimationEnd(Animation anim) {
			// TODO Auto-generated method stub
			
		}

		public void onAnimationRepeat(Animation anim) {
			// TODO Auto-generated method stub
			Log.i("alpha-in",String.valueOf(view.getAlpha()));
			Log.i("delta",String.valueOf(delta));
			view.setAlpha(view.getAlpha()+delta);
	
					}

		public void onAnimationStart(Animation anim) {
			// TODO Auto-generated method stub
			view.setAlpha(0f);	
			
		}
		
			


		
	}



