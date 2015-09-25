package com.rdupuis.amikcal.energy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.data.ContentDescriptorObj;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * <b>Act_EnergyList est la classe qui affiche la vue liste d'aliments.</b>
 * <p>
 * Cette vue comporte :
 * <ul>
 * <li>Une zone de recherche pour filtrer les aliments par nom.</li>
 * <li>Une liste d'aliment.</li>
 * </ul>
 * </p>
 *
 * @author Rodolphe Dupuis
 * @version 0.1
 */

public class Act_Energy_List extends Activity {
    String currentFilter = "";
    public ListView maListViewPerso;
    public long chosedEnergyId;
    public final static String OUTPUT____CHOOSED_ENERGY = "output_choosed_energy";

    /**
     * This function is Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(this, this.getClass().getCanonicalName(), Toast.LENGTH_SHORT).show();

    }// fin du onCreate



    /**
     * ____________________________________________________________________________
     * effacer la liste
     */
    protected void cleanList() {
        maListViewPerso.removeAllViewsInLayout();
    }


    /**
     * ________________________________________________________ Action a
     * effectuer lorsque l'on séléctionne une énergie
     */

    public void OnChooseEnergy(EnergySource energySource) {

        // on alimente le résultat dans l'Intent pour que l'Activity mère puisse récupérer la valeur.
        this.getIntent().putExtra(this.OUTPUT____CHOOSED_ENERGY, energySource);

        // on appelle setResult pour déclencher le onActivityResult de l'activity mère.
        this.setResult(RESULT_OK, this.getIntent());

        // On termine l'Activity
        this.finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_energy_list, menu);
        return true;

    }

    // rafraichir la barre de menu
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);

    }

    /**
     *
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionbar_energylist_item_add:
              //  onClickAdd(null);
                break;
            case R.id.actionbar_energylist_item_back:
                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        // invalidateOptionsMenu va appeller la m�thode onPrepareOptionsMenu();
        // qui va elle m�me rafraichir la barre de menu
        invalidateOptionsMenu();
        return true;
    }

    // *************************************
} // end class