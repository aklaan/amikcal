package com.rdupuis.amikcal;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TestHorizontalActivity extends Activity {

	 private LinearLayout linearLayout;
	 private CustomHorizontalScrollView horizontalScrollView;

	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);

	  Display mDisplay = getWindowManager().getDefaultDisplay();
   	int width = 	mDisplay.getHeight();
   	int height = 	mDisplay.getWidth();
	  
	 // int width = this.getWindowManager().getDefaultDisplay().getWidth();
	 // int height = getWindowManager().getDefaultDisplay().getHeight();
	  horizontalScrollView = new CustomHorizontalScrollView(this, 3,
	    width);
	  setContentView(R.layout.horizontal);
	  linearLayout = (LinearLayout) findViewById(R.id.layer);
	  linearLayout.addView(horizontalScrollView);

	  LinearLayout container = new LinearLayout(this);
	  container.setLayoutParams(new LayoutParams(width, height));
	  // container.setHeight(height);

	  
	  
	  
	  TextView textView = new TextView(this);
	  textView.setWidth(width);
	  textView.setHeight(height);
	  textView.setGravity(Gravity.CENTER);
	  textView.setText("First  Screen");
	  textView.setBackgroundColor(Color.CYAN);
	  container.addView(textView);

	  textView = new TextView(this);
	  textView.setWidth(width);
	  textView.setHeight(height);
	  textView.setGravity(Gravity.CENTER);
	  textView.setText("Second  Screen");
	  textView.setBackgroundColor(Color.GREEN);
	  container.addView(textView);

	  textView = new TextView(this);
	  textView.setWidth(width);
	  textView.setHeight(height);
	  textView.setGravity(Gravity.CENTER);
	  textView.setText("Third  Screen");
	  textView.setBackgroundColor(Color.RED);
	  container.addView(textView);

	  horizontalScrollView.addView(container);
	 }

	}