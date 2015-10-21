package com.rdupuis.amikcal.components;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.rdupuis.amikcal.Qty.Qty;
import com.rdupuis.amikcal.Qty.Qty_Manager;
import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.Manager;
import com.rdupuis.amikcal.commons.ManagerBuilder;
import com.rdupuis.amikcal.commons.RETURNCODE;
import com.rdupuis.amikcal.commons.numericpad.Act_NumericPad;
import com.rdupuis.amikcal.energy.Act_Energy_Food_List;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.unity.Act_UnitOfMeasureList;
import com.rdupuis.amikcal.unity.Unity;

/**
 * cette classe est une base commune a tous les éditeurs d'objets de la famille des composants
 * <p/>
 * commentaires
 * 2013-08-23 : ajout de la barre de menu pour remplacer le bouton valider
 */

public class Act_Component_Editor extends Activity {

    public Component edited_Component;

    //nom des variables Intent gérée par la classe.
    public static final String INPUT____COMP = "in_comp"; // ID du composant à éditer
    public static final String OUTPUT____COMP = "out_comp"; //Id du composant qui a été édité

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, this.getClass().getCanonicalName(), Toast.LENGTH_SHORT).show();
        edited_Component = getIntent().getExtras().getParcelable(Act_Component_Editor.INPUT____COMP);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_component_editor, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action buttons
        switch (item.getItemId()) {
            case R.id.actionbar_component_editor_item_validate:
                onClick_Validate();
                return true; // d'après le modèle google c'est ce qu'il faut faire
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*******************************************************************************
     * Appel du pavé numérique
     *****************************************************************************/
    public void callNumericPad() {
        Intent intent = new Intent(this, Act_NumericPad.class);
        intent.putExtra("question", "Entrez la quantité d'Aliment");
        startActivityForResult(intent, R.integer.NUMERICPAD);
    }

    /***************************************************************************************
     * On Appel l'affichage de la liste des aliments
     *
     * @param v View
     **************************************************************************************/
    public void onClick_EnergyName(View v) {
        Intent intent = new Intent(this, Act_Energy_Food_List.class);
        startActivityForResult(intent, R.integer.ACTY_ENERGIES_LIST);
    }

    /***************************************************************************************
     * Appel de la Liste des unitées.
     *
     * @param v View
     **************************************************************************************/
    public void onClick_Unit(View v) {

        Intent intent = new Intent(this, Act_UnitOfMeasureList.class);
        intent.putExtra(Act_UnitOfMeasureList.INPUT____ENERGY_FILTER, edited_Component.getEnergy());
        startActivityForResult(intent, R.integer.ACTY_UNITS_LIST);

    }

    /**
     * Mettre à jour les informations saisies dans la base de donnée.
     */
    public void onClick_Validate() {

        //on sauve la quantitée saisie
        Qty_Manager qty_manager = new Qty_Manager(this);
        long newQtyId = qty_manager.save(this.edited_Component.getQty());
        this.edited_Component.getQty().setDatabaseId(newQtyId);

        //On ne sait pas quel type de composant est édité.
        //on passe par le builder pour récupérer le bon Manager
        Manager manager = ManagerBuilder.build(this, this.edited_Component);
        this.edited_Component.setDatabaseId(manager.save(this.edited_Component));


        // On termine l'Actvity seulement si le manager a bien terminé son taf
        if (manager.getReturnCode() == RETURNCODE.OK) {
            // on appelle setResult pour déclancher le onActivityResult de
            // l'activity mère.

            this.getIntent().putExtra(Act_Component_Editor.OUTPUT____COMP, this.edited_Component);
            setResult(RESULT_OK, this.getIntent());

            finish();
        }


    }

    /******************************************************************************************
     * Gère le retour d'appel aux autres activitées
     *
     * @param requestCode code fonction utilisé par l'activité appellante
     * @param resultCode  code retour envoyé par l'activitée appellée
     * @param intent      Intent servant de lien entre l'activitée appelante et appellée
     ******************************************************************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        switch (requestCode) {

            case R.integer.NUMERICPAD:

                if (resultCode == RESULT_OK) {

                    this.edited_Component.getQty().setAmount(intent.getFloatExtra(Act_NumericPad.OUTPUT____AMOUNT, 0f));

                }
                break;

            case R.integer.ACTY_UNITS_LIST:

                if (resultCode == RESULT_OK) {

                    // on récupère l'unité de mesure choisie
                    Unity unity = intent.getExtras().getParcelable(Act_UnitOfMeasureList.OUTPUT____CHOOSED_UNIT);
                    this.edited_Component.getQty().setUnity(unity);
                }
                break;

            case R.integer.ACTY_ENERGIES_LIST:

                if (resultCode == RESULT_OK) {

                    // on récupère l'Energy choisie par l'utilisateur
                    EnergySource energySource = intent.getExtras().getParcelable(Act_Energy_Food_List.OUTPUT____CHOOSED_ENERGY);
                    this.edited_Component.setEnergy(energySource);

                }
                break;
            default:
                break;

        }
    }

}