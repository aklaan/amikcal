package com.rdupuis.amikcal;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.view.View.OnTouchListener;


public class CustomHorizontalScrollView extends HorizontalScrollView implements
OnTouchListener, OnGestureListener {

      private static final int SWIPE_MIN_DISTANCE = 300;

private static final int SWIPE_THRESHOLD_VELOCITY = 300;
private static final int SWIPE_PAGE_ON_FACTOR = 10;


private GestureDetector gestureDetector;
private int scrollTo = 0;
private int maxItem = 0;
private int activeItem = 0;
private float prevScrollX = 0;
private boolean start = true;
private int itemWidth = 0;
private float currentScrollX;
private boolean flingDisable = true;

public CustomHorizontalScrollView(Context context) {
super(context);
setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
  LayoutParams.FILL_PARENT));
}

public CustomHorizontalScrollView(Context context, int maxItem,
 int itemWidth) {
this(context);
this.maxItem = maxItem;
this.itemWidth = itemWidth;
gestureDetector = new GestureDetector(this);
this.setOnTouchListener(this);
}


public boolean onTouch(View v, MotionEvent event) {
if (gestureDetector.onTouchEvent(event)) {
 return true;
}
Boolean returnValue = gestureDetector.onTouchEvent(event);

int x = (int) event.getRawX();

switch (event.getAction()) {
case MotionEvent.ACTION_MOVE:
 if (start) {
  this.prevScrollX = x;
  start = false;
 }
 break;
case MotionEvent.ACTION_UP:
 start = true;
 this.currentScrollX = x;
 int minFactor = itemWidth
   / SWIPE_PAGE_ON_FACTOR;

 if ((this.prevScrollX - this.currentScrollX) > minFactor) {
  if (activeItem < maxItem - 1)
   activeItem = activeItem + 1;

 } else if ((this.currentScrollX - this.prevScrollX) > minFactor) {
  if (activeItem > 0)
   activeItem = activeItem - 1;
 }
 System.out.println("horizontal : " + activeItem);
 scrollTo = activeItem * itemWidth;
 this.smoothScrollTo(scrollTo, 0);
 returnValue = true;
 break;
}
return returnValue;
}


public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
 float velocityY) {
if (flingDisable)
 return false;
boolean returnValue = false;
float ptx1 = 0, ptx2 = 0;
if (e1 == null || e2 == null)
 return false;
ptx1 = e1.getX();
ptx2 = e2.getX();
// right to left

if (ptx1 - ptx2 > SWIPE_MIN_DISTANCE
  && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
 if (activeItem < maxItem - 1)
  activeItem = activeItem + 1;

 returnValue = true;

} else if (ptx2 - ptx1 > SWIPE_MIN_DISTANCE
  && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
 if (activeItem > 0)
  activeItem = activeItem - 1;

 returnValue = true;
}
scrollTo = activeItem * itemWidth;
this.smoothScrollTo(0, scrollTo);
return returnValue;
}


public boolean onDown(MotionEvent e) {
return false;
}


public void onLongPress(MotionEvent e) {
}


public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
 float distanceY) {
return false;
}

public void onShowPress(MotionEvent e) {
}


public boolean onSingleTapUp(MotionEvent e) {
return false;
}
}