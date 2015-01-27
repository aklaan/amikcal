package com.rdupuis.amikcal.commons.numericpad;

import android.view.View;

//interface devant obliger les utilsateurs du fragement d'implémenter les méthodes 
	// exploitées par le NuméricPad
	public interface NumericPadListeners {
		public void NumericPadListener_OnClick_btn_Number(View view);
		public void NumericPadListener_OnClick_btn_Erase(View view);
		public void NumericPadListener_OnClick_btn_Back(View view);
		public void NumericPadListener_OnClick_btn_Ok(View view);
		public void NumericPadListener_OnClick_btn_Cancel(View view);
	  }
