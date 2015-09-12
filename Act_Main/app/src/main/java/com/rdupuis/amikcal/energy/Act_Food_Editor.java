package com.rdupuis.amikcal.energy;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.rdupuis.amikcal.R;
import com.rdupuis.amikcal.commons.AmiKcalFactory;
import com.rdupuis.amikcal.commons.AppConsts;
import com.rdupuis.amikcal.commons.Manager;
import com.rdupuis.amikcal.commons.ManagerBuilder;
import com.rdupuis.amikcal.commons.numericpad.Act_NumericPad;
import com.rdupuis.amikcal.components.Component;
import com.rdupuis.amikcal.energy.ContreteEnergySource.STRUCTURE;
import com.rdupuis.amikcal.equivalence.Act_EquivalenceEditor;
import com.rdupuis.amikcal.relations.Relation;
import com.rdupuis.amikcal.unity.Act_UnitOfMeasureList;
import com.rdupuis.amikcal.unity.Unity;
import com.rdupuis.amikcal.unity.Unity_Manager;

/**
 * Cette vue permet de renseigner une nouvelle energie et ses equivalences
 *
 * @author Rodolphe
 */
public class Act_Food_Editor extends Activity {

    Food mFood;
    AmiKcalFactory factory;
    private ListView maListViewPerso;
    public static final String INPUT____ID_OF_FOOD = "FOOD_ID";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_edit_energy_food2);

        long nrj_id = getIntent().getLongExtra(INPUT____ID_OF_FOOD, AppConsts.NO_ID);

        // Si l'ID en entr�e indique que l"on souhaite �diter un aliment en particulier, on le recharge
        //sinon on en g�n�re un nouveau
        if (nrj_id != AppConsts.NO_ID) {
            Energy_Manager em = new Energy_Manager(this);
            mFood = (Food) em.load(getIntent().getLongExtra(INPUT____ID_OF_FOOD, AppConsts.NO_ID));
        } else {
            mFood = new Food();
        }

        ((TextView) findViewById(R.id.energyview_edTxt_energy_name)).addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mFood.setName(s.toString());
            }

            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

        });

        refreshScreen();

    }// fin du onCreate

    /***************************************************************************************
     * @param v
     **************************************************************************************/
    public void onClick_bt_amount(View v) {
        Intent intent = new Intent(this, Act_NumericPad.class);
        intent.putExtra("question", "Entrez la quantit� de r�f�rence");
        startActivityForResult(intent, R.integer.BTN_QUANTITY_OF_ENERGY_VIEW_CALL_NUMERICPAD);
    }

    /***************************************************************************************
     * @param v
     **************************************************************************************/
    public void onClick_bt_unity(View v) {
        Intent intent = new Intent(this, Act_UnitOfMeasureList.class);
        startActivityForResult(intent, R.integer.ACTY_UNITS_LIST);

    }

    public void onClick_RdioBt_Liquid(View v) {
        this.mFood.setStructure(STRUCTURE.LIQUID);
        ((CompoundButton) findViewById(R.id.rdiobt_liquid)).setChecked(true);
        ((CompoundButton) findViewById(R.id.rdiobt_solid)).setChecked(false);
        ((CompoundButton) findViewById(R.id.rdiobt_powder)).setChecked(false);
    }

    public void onClick_RdioBt_Solid(View v) {
        this.mFood.setStructure(STRUCTURE.SOLID);
        ((CompoundButton) findViewById(R.id.rdiobt_liquid)).setChecked(false);
        ((CompoundButton) findViewById(R.id.rdiobt_solid)).setChecked(true);
        ((CompoundButton) findViewById(R.id.rdiobt_powder)).setChecked(false);
    }

    public void onClick_RdioBt_Powder(View v) {
        this.mFood.setStructure(STRUCTURE.POWDER);
        ((CompoundButton) findViewById(R.id.rdiobt_liquid)).setChecked(false);
        ((CompoundButton) findViewById(R.id.rdiobt_solid)).setChecked(false);
        ((CompoundButton) findViewById(R.id.rdiobt_powder)).setChecked(true);
    }

    /*******************
     * ***********************************************************************
     * onActivityResult : on récupère les info saisies dans le pad numérique.
     ******************************************************************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        switch (requestCode) {

            case R.integer.BTN_QUANTITY_OF_ENERGY_VIEW_CALL_NUMERICPAD:

                if (resultCode == RESULT_OK) {

                    this.mFood.getComponentReference().getQty().setAmount(intent.getFloatExtra(Act_NumericPad.OUTPUT____AMOUNT, 0f));

                }
                break;

            case R.integer.ACTY_UNITS_LIST:

                if (resultCode == RESULT_OK) {

                    Unity unity = new Unity();
                    Manager manager = ManagerBuilder.build(this, unity);
                    long _id = intent.getLongExtra(Act_UnitOfMeasureList.OUTPUT____UNIT_ID, AppConsts.NO_ID);
                    unity = (Unity) manager.load(_id);

                    this.mFood.getComponentReference().getQty().setUnity(unity);


                }
                break;
            default:
                break;

        }

        refreshScreen();
    }

    /******************************************************************************************
     * onValidateClick : Mettre à jour les informations saisies dans la base de
     * donnée
     *
     * @param v
     ******************************************************************************************/
    public void onClick_Validate(View v) {

        //récupérer le libéllé de l'énergie
        EditText ed = (EditText) findViewById(R.id.energyview_edTxt_energy_name);
        this.mFood.setName(ed.getText().toString());

        //Enregister l'énergie
        Manager manager = ManagerBuilder.build(this, this.mFood);
        manager.save();


        // on appelle setResult pour déclancher le onActivityResult de
        // l'activity mère.
        setResult(RESULT_OK, getIntent());

        // On termine l'Actvity
        finish();
    }


    public void onClick_Add_Equiv(View view) {
        Intent intent = new Intent(this, Act_EquivalenceEditor.class);
        intent.putExtra(Act_EquivalenceEditor.INPUT____ID_NRJ, this.mFood.getDatabaseId());
        startActivityForResult(intent, R.integer.EQUIVALENCE_EDITOR);

    }

    /***************************************************************************************
     *

     **************************************************************************************/
    private void refreshScreen() {

        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance();
        decimalFormat.applyPattern("#,##0.00");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator(',');
        decimalFormat.setDecimalFormatSymbols(dfs);

        // recharger les modification qui ont pu �tre effectu� sur l'energie
        // sauf si c'est une nouvelle. dans ce cas
//	if (this.mEnergy.getId() != AppConsts.NO_ID) {
//	    this.mEnergy = factory.load_Energy(this.mEnergy.getId());
//	}

        Button bt = (Button) findViewById(R.id.energyview_btn_amount);
        bt.setText(decimalFormat.format(this.mFood.getComponentReference().getQty().getAmount()));

        bt = (Button) findViewById(R.id.energyview_btn_unity);
        bt.setText(this.mFood.getComponentReference().getQty().getUnity().getLongName());

        EditText ed = (EditText) findViewById(R.id.energyview_edTxt_energy_name);
        ed.setText(this.mFood.getName());

        switch (this.mFood.getStructure()) {
            case LIQUID:
                ((CompoundButton) findViewById(R.id.rdiobt_liquid)).setChecked(true);
                ((CompoundButton) findViewById(R.id.rdiobt_solid)).setChecked(false);
                ((CompoundButton) findViewById(R.id.rdiobt_powder)).setChecked(false);
                break;
            case SOLID:
                ((CompoundButton) findViewById(R.id.rdiobt_liquid)).setChecked(false);
                ((CompoundButton) findViewById(R.id.rdiobt_solid)).setChecked(true);
                ((CompoundButton) findViewById(R.id.rdiobt_powder)).setChecked(false);
                break;
            case POWDER:
                ((CompoundButton) findViewById(R.id.rdiobt_liquid)).setChecked(false);
                ((CompoundButton) findViewById(R.id.rdiobt_solid)).setChecked(false);
                ((CompoundButton) findViewById(R.id.rdiobt_powder)).setChecked(true);
            default:
                ((CompoundButton) findViewById(R.id.rdiobt_liquid)).setChecked(false);
                ((CompoundButton) findViewById(R.id.rdiobt_solid)).setChecked(false);
                ((CompoundButton) findViewById(R.id.rdiobt_powder)).setChecked(false);

                break;
        }

        // R�cup�ration de la listview cr��e dans le fichier customizedlist.xml
        maListViewPerso = (ListView) findViewById(R.id.listviewperso);

        // effacer la liste actuelle
        maListViewPerso.removeAllViewsInLayout();

        // Cr�ation de la ArrayList qui nous permettra de remplir la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

        // On d�clare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map;
        map = new HashMap<String, String>();

        // Pour chaque �quivalence au composant de r�f�rence..

        /*
        for (Relation equiv : this.mFood.getComponentReference().getEquivalences()) {

            map = new HashMap<String, String>();
            map.put("EQUIV_INDEX", String.valueOf(this.mFood.getQtyReference().getEquivalences().indexOf(equiv)));

            if (equiv instanceof Component) {

                map.put("quantity", String.valueOf(((Component) equiv).getQty().getAmount()));
                map.put("unity", ((Component) equiv).getQty().getUnity().getLongName());
                map.put("nrj", ((Component) equiv).getEnergy().getName());
            }
            listItem.add(map);
        }
*/
        // Création d'un SimpleAdapter qui se chargera de mettre les items
        // présent dans notre list (listItem)
        // dans la vue affichageitem
        SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(), listItem,
                R.layout.list_item_activity_lunch_component, new String[]{"name", "quantity", "energy", "equiv",
                "nbglu", "nbpro", "nblip"}, new int[]{R.id.itemfood_name, R.id.itemfood_quantity,
                R.id.itemfood_nbkcal, R.id.itemfood_equiv, R.id.itemfood_glu, R.id.itemfood_pro,
                R.id.itemfood_lip});

        // On attribut à notre listView l'adapter que l'on vient de créer
        maListViewPerso.setAdapter(mSchedule);

    }

    /***************************************************************************************
     * @param v
     **************************************************************************************/
    public void onClick_Cancel(View v) {
        finish();
    }

}