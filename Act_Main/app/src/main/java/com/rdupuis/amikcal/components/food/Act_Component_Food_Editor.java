package com.rdupuis.amikcal.components.food;

/**
 * commentaires
 * 2013-08-23 : ajout de la barre de menu pour remplacer le bouton valider
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.numericpad.Act_NumericPad;
import com.rdupuis.amikcal.components.Act_Component_Editor;
import com.rdupuis.amikcal.energy.Act_Energy_Food_List;
import com.rdupuis.amikcal.energy.EnergySource;
import com.rdupuis.amikcal.unity.Act_UnitOfMeasureList;
import com.rdupuis.amikcal.unity.Unity;


public class Act_Component_Food_Editor extends Act_Component_Editor {


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, this.getClass().getCanonicalName(), Toast.LENGTH_SHORT).show();
        /****************************************************************************
         * ETAPE I : r�cup�ration des donn�es
         ****************************************************************************/
        // fait dans le onCreate m�re

        /****************************************************************************
         * ETAPE II : on charge l'�cran
         ****************************************************************************/
        setContentView(R.layout.view_edit_food_component);

        /*****************************************************************************
         * ETAPE III : on initialise/Rafraichi les donn�es de l'�cran
         ****************************************************************************/

        refreshScreen();

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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /***************************************************************************************
     * @param v View
     **************************************************************************************/
    public void onClick_Quantity(View v) {
        callNumericPad();
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
        Intent intent = new Intent(this, Act_Energy_Food_List.class);
        startActivityForResult(intent, R.integer.ACTY_ENERGIES_LIST);
    }

    /***************************************************************************************
     * @param v View
     **************************************************************************************/
    public void onClick_Unit(View v) {
        callUnitListView();
    }

    /***************************************************************************************
     * Appel de la Liste des unit�es.
     **************************************************************************************/
    public void callUnitListView() {
        Intent intent = new Intent(this, Act_UnitOfMeasureList.class);
        intent.putExtra(Act_UnitOfMeasureList.INPUT____ENERGY_FILTER, edited_Component.getEnergy());
        startActivityForResult(intent, R.integer.ACTY_UNITS_LIST);

    }


    /******************************************************************************************
     * G�re le retour d'appel aux autres activit�es
     *
     * @param requestCode code fonction utilis� par l'activit� appellante
     * @param resultCode  code retour envoy� par l'activit�e appell�e
     * @param intent      Intent servant de lien entre l'activit�e appelante et appell�e
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

                    // On récupère l'Unitée.
                    Unity unity = intent.getExtras().getParcelable(Act_UnitOfMeasureList.OUTPUT____CHOOSED_UNIT);
                    this.edited_Component.getQty().setUnity(unity);
                }
                break;

            case R.integer.ACTY_ENERGIES_LIST:

                if (resultCode == RESULT_OK) {


                    // on récupère l'Energy choisie par l'utilisateur
                    // .
                    EnergySource energySource = intent.getExtras().getParcelable(Act_Energy_Food_List.OUTPUT____CHOOSED_ENERGY);
                    this.edited_Component.setEnergy(energySource);

                }
                break;

            case R.integer.COMPONENT_EDITOR :
                if (resultCode == RESULT_OK) {
                }

                    default:
                break;

        }
        refreshScreen();
    }

    /******************************************************************************************
     *
     * @param _id
     *            Identifiant du composant
     ******************************************************************************************/

    /**
     * Rafraichissement de l'�can
     */
    private void refreshScreen() {
        Button b = (Button) findViewById(R.id.componentview_btn_EnergyName);

        // Gestion du libell� sur le bouton Energy
        if (edited_Component.getEnergy().getName() == "") {
            b.setText(this.getResources().getString(R.string.empty));
        } else {
            b.setText(this.edited_Component.getEnergy().getName());
        }

        // Gestion du libell� sur le bouton Unit
        b = (Button) findViewById(R.id.componentview_btn_unit);

        if (edited_Component.getQty().getUnity().getLongName().isEmpty()) {
            b.setText(this.getResources().getString(R.string.empty));
        } else {
            b.setText(this.edited_Component.getQty().getUnity().getLongName());
        }

        // Gestion du libellé sur le bouton quantity
        b = (Button) findViewById(R.id.componentview_btn_quantity);
        b.setText(Float.toString(this.edited_Component.getQty().getAmount()));

    }

    /**
     * Recherche d'une équivalence pour un aliment et une unité de mesure
     *
     * @param energy
     *            (aliment)
     * @param unitIn
     *            (unité de départ)
     * @return une équivalence
     */

    /*
     * private EquivalenceObj findEquivalence(EnergyReference energy, Unity
     * unitIn) {
     * 
     * EquivalenceObj mEquivalence = new EquivalenceObj(); String searchKey =
     * String.valueOf(energy.getId()) + "+" + String.valueOf(unitIn.getId());
     * 
     * // On fabrique l'Uri pour le contentProvider Uri selectUri =
     * ContentDescriptorObj.Equivalences.URI_SEARCH_EQUIVALENCE
     * .buildUpon().appendPath(searchKey).build();
     * 
     * // On cr�e un curseur pour lire la table. Cursor cur =
     * this.getContentResolver().query(selectUri, null, null, null, null);
     * 
     * final int INDX_ID = cur
     * .getColumnIndex(ContentDescriptorObj.Equivalences.Columns.ID);
     * 
     * // faire un move First pour positionner le pointeur, sinon on pointe sur
     * // null if (cur.moveToFirst()) {
     * 
     * AmiKcalFactory factory = new AmiKcalFactory(); factory.contentResolver =
     * this.contentResolver; mEquivalence =
     * factory.createEquivalenceObjFromId(cur .getLong(INDX_ID)); } else {
     * Toast.makeText(this, "Equivalence non trouv�", Toast.LENGTH_SHORT)
     * .show();
     * 
     * }
     * 
     * return mEquivalence;
     * 
     * }
     */

    /**
     * Calcul des informations nutitionelles
     * (calories/lipides/glucides/protéines)
     */
    private void computeEnegy() {
    /*
     * float wQuantity = this.mUAC.getQuantity();
	 * 
	 * Log.d("mUAC.getQuantity", String.valueOf(this.mUAC.getQuantity()));
	 * 
	 * // Si la quantit� d'aliment est exprim� dans une untit�e // diffr�nte
	 * de celle du r�f�rentiel des calories, on va rechercher //
	 * l'�quivalence. if (this.mUAC.getEnergy().unit.getId() !=
	 * this.mUAC.getUnitMeasure() .getId()) { Toast.makeText(this,
	 * "Unit�e de mesure diff�rentes", Toast.LENGTH_SHORT).show();
	 * 
	 * EquivalenceObj eq = findEquivalence(mUAC.getEnergy(),
	 * mUAC.getUnitMeasure());
	 * 
	 * Log.d("eq.getQuantity", String.valueOf(eq.getQuantity()));
	 * 
	 * wQuantity = eq.getQuantity() * wQuantity; }
	 * 
	 * Log.i("wQuantity", String.valueOf(wQuantity));
	 * 
	 * this.mUAC.setCalories((wQuantity * this.mUAC.getEnergy()
	 * .getCalories()) / this.mUAC.getEnergy().getQuantityReference());
	 * this.mUAC.setLipids((wQuantity * this.mUAC.getEnergy().getLipids()) /
	 * this.mUAC.getEnergy().getQuantityReference()); this.mUAC
	 * .setGlucids((wQuantity * this.mUAC.getEnergy().getGlucids()) /
	 * this.mUAC.getEnergy().getQuantityReference());
	 * this.mUAC.setProteins((wQuantity * this.mUAC.getEnergy()
	 * .getProteins()) / this.mUAC.getEnergy().getQuantityReference());
	 */
    }

}