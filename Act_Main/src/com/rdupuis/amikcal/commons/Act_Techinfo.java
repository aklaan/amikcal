package com.rdupuis.amikcal.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import android.app.Activity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import com.rdupuis.amikcal.R;

public class Act_Techinfo extends Activity {

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_technical_informations);

		TextView tv = (TextView) findViewById(R.id.techinfo_tv_dbVersion);
		tv.setText(String.valueOf(ToolBox.getAmikcalDbVersion()));

	}

	public void importDB(View v) {

	}

	public void exportDB(View v) {
		
			File directory = new File(Environment.getExternalStorageDirectory()
					+ "/amikcalBK");

			// si le dossier n'existe pas
			// la methode le crée et revoi true
			// di le dossier existe deja la methode renvoie faux
			directory.mkdir();

			File sd = Environment.getExternalStorageDirectory();
			File data = Environment.getDataDirectory();

			if (sd.canWrite()) {
				String currentDBPath = "//data/"
						+ "com.rdupuis.amikcal" + "/databases/"
						+ "amikcal.db";

				String backupDBPath = "amikcalBK/backup";

				File currentDB = new File(data, currentDBPath);
				
				File backupDB = new File(sd, backupDBPath);

			
				
				try {
					
								
					
					backupDB.createNewFile();
					FileChannel src = new FileInputStream(currentDB).getChannel();
					FileChannel dst = new FileOutputStream(backupDB).getChannel();
					
					dst.transferFrom(src, 0, src.size());
					src.close();
					dst.close();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		

	}
}
