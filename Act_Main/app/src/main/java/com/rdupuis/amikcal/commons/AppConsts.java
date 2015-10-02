package com.rdupuis.amikcal.commons;

import java.util.EnumMap;
import java.util.HashMap;

import com.rdupuis.amikcal.data.ContentDescriptorObj.TB_Energies;
import com.rdupuis.amikcal.data.ContentDescriptorObj.TB_Party_rel;
import com.rdupuis.amikcal.data.ContentDescriptorObj.TB_Units;
import com.rdupuis.amikcal.data.ContentDescriptorObj.TB_UserActivities;
import com.rdupuis.amikcal.energy.ContreteEnergySource.STRUCTURE;
import com.rdupuis.amikcal.energy.NRJ_CLASS;
import com.rdupuis.amikcal.energy.NRJ_EFFECT;
import com.rdupuis.amikcal.relations.REL_TYP_CD;
import com.rdupuis.amikcal.unity.Unity.UNIT_CLASS;
import com.rdupuis.amikcal.useractivity.UA_CLASS_CD;

/**
 * <h1>AppConsts</h1> Classe contenant les contstantes utilisées par
 * l'application
 */
public final class AppConsts {

    public static String INPUT____UA_EDITOR____USER_ACTIVITY_ID = "UA_ID";
    public static String INPUT____UA_EDITOR____DAY = "UA_DAY";

    public static String INPUT____CHOOSE_NEW_ACTIVITY____DAY_OF_THE_USER_ACTIVITY = "_DAY";

    public static long NO_ID = -1l;
    public static int NO_INDEX = -1;
    public static String BREAKFAST = "BREAKFAST";
    public static String LUNCH = "LUNCH";
    public static String DINER = "DINER";
    public static String SNACK = "SNACK";

    public static String INPUT____USER_ACTIVITY_COMPONENT_LIST____ID_OF_PARENT_USER_ACTIVITY = "_PARENT_UA_ID";

    public static String INPUT____USER_ACTIVITY_COMPONENT_EDITOR____ID_OF_PARENT_USER_ACTIVITY = "_PARENT_UA_ID";
    public static String INPUT____USER_ACTIVITY_COMPONENT_EDITOR____COMPONENT_ID = "COMP_ID";
   
    /*****************************************************************************
     * <h1>NRJ_EFFECT_MAP :</h1>
     * <p>
     * Mapping d'Entrée/Sortie Database pour les NRJ_EFFECT
     * </p>
     ****************************************************************************/

    public static final class NRJ_CLASS_MAP {
	public EnumMap<NRJ_CLASS, String> _out = new EnumMap<NRJ_CLASS, String>(NRJ_CLASS.class);

	public HashMap<String, NRJ_CLASS> _in = new HashMap<String, NRJ_CLASS>();

	public NRJ_CLASS_MAP() {

	    _out.put(NRJ_CLASS.ENERGY, TB_Energies.PredefinedValues.EnergyClass.UNDEFINED);
	    _out.put(NRJ_CLASS.FOOD, TB_Energies.PredefinedValues.EnergyClass.FOOD);
	    _out.put(NRJ_CLASS.PHYSICAL_ACTIVITY, TB_Energies.PredefinedValues.EnergyClass.PHYSICAL_ACTIVITY);
	    
	    _in.put(TB_Energies.PredefinedValues.EnergyClass.UNDEFINED, NRJ_CLASS.ENERGY);
	    _in.put(TB_Energies.PredefinedValues.EnergyClass.FOOD, NRJ_CLASS.FOOD);
	    _in.put(TB_Energies.PredefinedValues.EnergyClass.PHYSICAL_ACTIVITY, NRJ_CLASS.PHYSICAL_ACTIVITY);
	    	}
    }

    /*****************************************************************************
     * <h1>STRUCTURE_CD_MAP :</h1>
     * <p>
     * Mapping d'Entrée/Sortie Database pour les STRUCTURE_CD_MAP
     * </p>
     ****************************************************************************/
    public static final class STRUCTURE_CD_MAP {
	public EnumMap<STRUCTURE, String> _out = new EnumMap<STRUCTURE, String>(STRUCTURE.class);

	public HashMap<String, STRUCTURE> _in = new HashMap<String, STRUCTURE>();

	public STRUCTURE_CD_MAP() {

	    _out.put(STRUCTURE.UNDEFINED, TB_Energies.PredefinedValues.StructureCode.UNDEFINED);
	    _out.put(STRUCTURE.SOLID, TB_Energies.PredefinedValues.StructureCode.SOLID);
	    _out.put(STRUCTURE.LIQUID, TB_Energies.PredefinedValues.StructureCode.LIQUID);
	    _out.put(STRUCTURE.POWDER, TB_Energies.PredefinedValues.StructureCode.POWDER);

	    // ---------------------------------------------------------
	    _in.put(TB_Energies.PredefinedValues.StructureCode.UNDEFINED, STRUCTURE.UNDEFINED);
	    _in.put(TB_Energies.PredefinedValues.StructureCode.SOLID, STRUCTURE.SOLID);
	    _in.put(TB_Energies.PredefinedValues.StructureCode.LIQUID, STRUCTURE.LIQUID);
	    _in.put(TB_Energies.PredefinedValues.StructureCode.POWDER, STRUCTURE.POWDER);

	}
    }

    /*****************************************************************************
     * <h1>UNIT_CLASS_MAP :</h1>
     * <p>
     * Mapping d'Entrée/Sortie Database pour les UNIT_CLASS_MAP
     * </p>
     ****************************************************************************/
    public static final class UNIT_CLASS_MAP {
	public EnumMap<UNIT_CLASS, String> _out = new EnumMap<UNIT_CLASS, String>(UNIT_CLASS.class);

	public HashMap<String, UNIT_CLASS> _in = new HashMap<String, UNIT_CLASS>();

	public UNIT_CLASS_MAP() {

	    _out.put(UNIT_CLASS.UNDEFINED, TB_Units.PredefinedValues.ClassCodes.UNDEFINED);
	    _out.put(UNIT_CLASS.INTERNATIONAL, TB_Units.PredefinedValues.ClassCodes.INTERNATIONAL);
	    _out.put(UNIT_CLASS.CUSTOM, TB_Units.PredefinedValues.ClassCodes.CUSTOM);
	    _out.put(UNIT_CLASS.CONTAINER, TB_Units.PredefinedValues.ClassCodes.CONTAINER);
	    _out.put(UNIT_CLASS.TIME, TB_Units.PredefinedValues.ClassCodes.TIME);

	    _in.put(TB_Units.PredefinedValues.ClassCodes.UNDEFINED, UNIT_CLASS.UNDEFINED);

	    _in.put(TB_Units.PredefinedValues.ClassCodes.INTERNATIONAL, UNIT_CLASS.INTERNATIONAL);
	    _in.put(TB_Units.PredefinedValues.ClassCodes.CUSTOM, UNIT_CLASS.CUSTOM);
	    _in.put(TB_Units.PredefinedValues.ClassCodes.CONTAINER, UNIT_CLASS.CONTAINER);
	    _in.put(TB_Units.PredefinedValues.ClassCodes.TIME, UNIT_CLASS.TIME);

	}

    }

    /*****************************************************************************
     * <h1>UA_CLASS_CD_MAP :</h1>
     * <p>
     * Mapping d'Entrée/Sortie Database pour les UA_CLASS_CD_MAP
     * </p>
     ****************************************************************************/
    public static final class UA_CLASS_CD_MAP {
	public EnumMap<UA_CLASS_CD, String> _out = new EnumMap<UA_CLASS_CD, String>(UA_CLASS_CD.class);

	public HashMap<String, UA_CLASS_CD> _in = new HashMap<String, UA_CLASS_CD>();

	public UA_CLASS_CD_MAP() {
	    _out.put(UA_CLASS_CD.LUNCH, TB_UserActivities.PredefinedValues.UACodes.LUNCH);
	    _out.put(UA_CLASS_CD.MOVE, TB_UserActivities.PredefinedValues.UACodes.MOVE);
	    _out.put(UA_CLASS_CD.WEIGHT, TB_UserActivities.PredefinedValues.UACodes.WEIGHT);

	    _in.put(TB_UserActivities.PredefinedValues.UACodes.LUNCH, UA_CLASS_CD.LUNCH);
	    _in.put(TB_UserActivities.PredefinedValues.UACodes.MOVE, UA_CLASS_CD.MOVE);
	    _in.put(TB_UserActivities.PredefinedValues.UACodes.WEIGHT, UA_CLASS_CD.WEIGHT);

	}
    }

    /*****************************************************************************
     * <h1>REL_TYP_CD_MAP :</h1>
     * <p>
     * Mapping d'Entrée/Sortie Database pour les REL_TYP_CD_MAP
     * </p>
     ****************************************************************************/
    public static final class REL_TYP_CD_MAP {
	public EnumMap<REL_TYP_CD, String> _out = new EnumMap<REL_TYP_CD, String>(REL_TYP_CD.class);

	public HashMap<String, REL_TYP_CD> _in = new HashMap<String, REL_TYP_CD>();

	public REL_TYP_CD_MAP() {
	    _out.put(REL_TYP_CD.UNDEFINED, TB_Party_rel.PredefinedValues.RelationsCodes.UNDEFINED);
	    _out.put(REL_TYP_CD.CFOOD, TB_Party_rel.PredefinedValues.RelationsCodes.CFOOD);
	    _out.put(REL_TYP_CD.CMOVE, TB_Party_rel.PredefinedValues.RelationsCodes.CMOVE);
	    _out.put(REL_TYP_CD.CWEIGHT, TB_Party_rel.PredefinedValues.RelationsCodes.CWEIGHT);

	    _out.put(REL_TYP_CD.NRJ_REF_INTRNL, TB_Party_rel.PredefinedValues.RelationsCodes.NRJ_REF_INTRNL);
	    _out.put(REL_TYP_CD.NRJ_REF_EQUIV, TB_Party_rel.PredefinedValues.RelationsCodes.NRJ_REF_EQUIV);
	    _out.put(REL_TYP_CD.CSTM_NRJ_REF, TB_Party_rel.PredefinedValues.RelationsCodes.CSTM_NRJ_REF);
	    _out.put(REL_TYP_CD.UNIT_EQUIV, TB_Party_rel.PredefinedValues.RelationsCodes.UNIT_EQUIV);
	    _out.put(REL_TYP_CD.UNIT_INTER_LINK, TB_Party_rel.PredefinedValues.RelationsCodes.UNIT_INTER_LINK);
	    _out.put(REL_TYP_CD.COMP_EQUIV, TB_Party_rel.PredefinedValues.RelationsCodes.COMP_EQUIV);
	    _out.put(REL_TYP_CD.QTY, TB_Party_rel.PredefinedValues.RelationsCodes.QTY);
	    _out.put(REL_TYP_CD.QTY_EQUIV, TB_Party_rel.PredefinedValues.RelationsCodes.QTY_EQUIV);


	    _in.put(TB_Party_rel.PredefinedValues.RelationsCodes.UNDEFINED, REL_TYP_CD.UNDEFINED);
	    _in.put(TB_Party_rel.PredefinedValues.RelationsCodes.CFOOD, REL_TYP_CD.CFOOD);
	    _in.put(TB_Party_rel.PredefinedValues.RelationsCodes.CMOVE, REL_TYP_CD.CMOVE);
	    _in.put(TB_Party_rel.PredefinedValues.RelationsCodes.CWEIGHT, REL_TYP_CD.CWEIGHT);


	    _in.put(TB_Party_rel.PredefinedValues.RelationsCodes.NRJ_REF_INTRNL, REL_TYP_CD.NRJ_REF_INTRNL);
	    _in.put(TB_Party_rel.PredefinedValues.RelationsCodes.NRJ_REF_EQUIV, REL_TYP_CD.NRJ_REF_EQUIV);
	    _in.put(TB_Party_rel.PredefinedValues.RelationsCodes.CSTM_NRJ_REF, REL_TYP_CD.CSTM_NRJ_REF);
	    _in.put(TB_Party_rel.PredefinedValues.RelationsCodes.UNIT_EQUIV, REL_TYP_CD.UNIT_EQUIV);
	    _in.put(TB_Party_rel.PredefinedValues.RelationsCodes.UNIT_INTER_LINK, REL_TYP_CD.UNIT_INTER_LINK);
	    _in.put(TB_Party_rel.PredefinedValues.RelationsCodes.COMP_EQUIV, REL_TYP_CD.COMP_EQUIV);
	    _in.put(TB_Party_rel.PredefinedValues.RelationsCodes.QTY, REL_TYP_CD.QTY);
	}
    }

}
