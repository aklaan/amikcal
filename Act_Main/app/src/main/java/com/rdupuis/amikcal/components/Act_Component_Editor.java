package com.rdupuis.amikcal.components;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AmiKcalFactory;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.AppConsts.REL_TYP_CD_MAP;
import com.rdupuis.amikcal.commons.Manager;
import com.rdupuis.amikcal.commons.ManagerBuilder;
import com.rdupuis.amikcal.commons.numericpad.Act_NumericPad;
import com.rdupuis.amikcal.components.food.Component_Food;
import com.rdupuis.amikcal.energy.Act_EnergyList;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.energy.Energy_Manager;
import com.rdupuis.amikcal.unity.Act_UnitOfMeasureList;
import com.rdupuis.amikcal.unity.Unity;
import com.rdupuis.amikcal.unity.Unity_Manager;
import com.rdupuis.amikcal.useractivity.UserActivity;

/**
 * cette classe est une base commune a tous les éditeurs d'objets de la famille des composants
 * <p/>
 * commentaires
 * 2013-08-23 : ajout de la barre de menu pour remplacer le bouton valider
 */

public class Act_Component_Editor extends Activity {

    UserActivity mUA;
    Component edited_Component;
    ContentResolver contentResolver;
    AmiKcalFactory factory;

    //nom des varibles Intent gérée par la classe.
    public static final String INPUT____COMP_ID = "in_comp_id"; // ID du composant à éditer
    public static final String INPUT____CLASS = "in_class"; //type du composant à créer
    public static final String OUTPUT____COMP_ID = "out_comp_id"; //Id du composant qui a été édité

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /***************************************************************************
         * ETAPE I : on récupère les infos de l'intent
         ***************************************************************************/

        // Récupérer l'id du composant à éditer
        long edited_comp_id = getIntent().getLongExtra(Act_Component_Editor.INPUT____COMP_ID, AppConsts.NO_ID);

        // en cas de création d'un nouveau composant, récupérer le type de composant souhaité
        String input_comp_class = getIntent().getStringExtra(Act_Component_Editor.INPUT____CLASS);

        // si l'ID n'est pas nul on charge le composant à éditer
        if (edited_comp_id != AppConsts.NO_ID) {
            // chargement du composant stocké
            Manager manager = ManagerBuilder.build(this, this.edited_Component);
            this.edited_Component = (Component) manager.load(edited_comp_id);

        } else {
            // Initialisation d'un nouveau composant en fonction de la class
            // souhaitée
            REL_TYP_CD_MAP rel_typ_cd_map = new REL_TYP_CD_MAP();

            switch (rel_typ_cd_map._in.get(input_comp_class)) {
                case CFOOD:
                    edited_Component = new Component_Food();
                    break;
                // default prend en charge les UNDEFINED
                default:
                    Toast.makeText(this, "type Component inconnu", Toast.LENGTH_LONG).show();
                    finish();
            }

        }

    }// fin du onCreate

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

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Appel du pav� num�rique
     */
    public void callNumericPad() {
        Intent intent = new Intent(this, Act_NumericPad.class);
        intent.putExtra("question", "Entrez la quantit� d'Aliment");
        startActivityForResult(intent, R.integer.NUMERICPAD);
    }

    /***************************************************************************************
     * Appel de la liste des aliments
     *
     * @param v View
     **************************************************************************************/
    public void onClick_EnergyName(View v) {
        Intent intent = new Intent(this, Act_EnergyList.class);
        startActivityForResult(intent, R.integer.ACTY_ENERGIES_LIST);
    }

    /***************************************************************************************
     * @param v View
     **************************************************************************************/
    public void onClick_Unit(View v) {
        callUnitListView();
    }

    /***************************************************************************************
     * Appel de la Liste des unitées.
     **************************************************************************************/
    public void callUnitListView() {
        Intent intent = new Intent(this, Act_UnitOfMeasureList.class);
        intent.putExtra(Act_UnitOfMeasureList.INPUT____ENERGY_ID, String.valueOf(edited_Component.getEnergy().getDatabaseId()));
        startActivityForResult(intent, R.integer.ACTY_UNITS_LIST);

    }

    /**
     * Mettre à jour les informations saisies dans la base de donnée.
     */
    public void onClick_Validate() {

        // A la création, le save va initialiser l'ID
        Manager manager = ManagerBuilder.build(this, this.edited_Component);
        this.edited_Component.setDatabaseId(manager.save());

        // on appelle setResult pour déclancher le onActivityResult de
        // l'activity mère.

        this.getIntent().putExtra(Act_Component_Editor.OUTPUT____COMP_ID, this.edited_Component.getDatabaseId());
        setResult(RESULT_OK, this.getIntent());

        // On termine l'Actvity
        finish();

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
                    Unity unity = new Unity();
                    Manager manager = ManagerBuilder.build(this, unity);
                    unity = (Unity) manager.load(intent.getLongExtra(Act_UnitOfMeasureList.OUTPUT____UNIT_ID, AppConsts.NO_ID));
                    this.edited_Component.getQty().setUnity(unity);
                }
                break;

            case R.integer.ACTY_ENERGIES_LIST:

                if (resultCode == RESULT_OK) {

                    // on récupère l'Energy choisie par l'utilisateur
                    EnergySource energySource = new EnergySource();
                    Manager manager = ManagerBuilder.build(this, energySource);
                    energySource = (EnergySource) manager.load(intent.getLongExtra(Act_EnergyList.OUTPUT____ID_OF_ENERGY, AppConsts.NO_ID));
                    this.edited_Component.setEnergy(energySource);

                }
                break;
            default:
                break;

        }
    }

}