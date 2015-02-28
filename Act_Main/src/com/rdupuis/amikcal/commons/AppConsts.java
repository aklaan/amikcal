package com.rdupuis.amikcal.commons;

import java.util.EnumMap;
import java.util.HashMap;

import com.rdupuis.amikcal.Food.Food.STRUCTURE;
import com.rdupuis.amikcal.data.ContentDescriptorObj.TB_Units;
import com.rdupuis.amikcal.data.ContentDescriptorObj.TB_UserActivities;
import com.rdupuis.amikcal.energy.EnergySource.NRJ_EFFECT;
import com.rdupuis.amikcal.unity.Unity.UNIT_CLASS;
import com.rdupuis.amikcal.useractivity.UserActivity.UA_CLASS_CD;

/**
 * <h1>AppConsts</h1> Classe contenant les contstantes utilisées par
 * l'application
 */
public final class AppConsts {

	public static String INPUT____UA_EDITOR____USER_ACTIVITY_ID = "UA_ID";
	public static String INPUT____UA_EDITOR____DAY = "UA_DAY";

	public static String INPUT____CHOOSE_NEW_ACTIVITY____DAY_OF_THE_USER_ACTIVITY = "_DAY";

	public static long NO_ID = -1l;
	public static String BREAKFAST = "BREAKFAST";
	public static String LUNCH = "LUNCH";
	public static String DINER = "DINER";
	public static String SNACK = "SNACK";

	public static String INPUT____USER_ACTIVITY_COMPONENT_EDITOR____ID_OF_PARENT_USER_ACTIVITY = "_PARENT_ID";
	public static String INPUT____USER_ACTIVITY_COMPONENT_EDITOR____COMPONENT_ID = "COMP_ID";

	/**
	 * <h1>TYPE_REL_CD : Type de relations entre les entités</h1>
	
	 * <p>
	 * UAC_FOOD = relation de type composant d'un repas (100 g de pomme) </br>
	 * UAC_MOVE = relation de type composant d'une activité physique (10 min de
	 * marche) </br>
	 * 
	 * UAC_WEIGHT = relation de type composant d'une pesée (45 kg) </br>
	 * 
	 * UA_TO_UAC = relation entre une UA et ses UAC </br>
	 * 
	 * NRJ_REF_INTER = relation entre la source d'énergie et sa quantité / unité
	 * de référence internationale (ex:100 g de pomme) </br>
	 * 
	 * NRJ_REF_EQUIV = équivalence par rapport à la référence (ex : 100 g de
	 * pomme = 56 Kcal) </br>
	 * 
	 * CSTM_NRJ_REF = unité de référence personalisé pour une source (ex:
	 * Moyenne -> Pomme) </br>
	 * 
	 * UNIT_EQUIV = relation d'équivalence entre deux unitée de la même famille
	 * (ex gramme = 0,OOO1 kilogramme) </br>
	 * 
	 * UNIT_INTER_LINK = passerelle de convertion entre 2 systèmes de mesure (ex
	 * : 1 kg = x Livres)
	 * 
	 * </p>
	 */
	public static enum TYPE_REL_CD {
		UAC_FOOD, UAC_MOVE, UAC_WEIGHT, UA_TO_UAC, NRJ_REF_INTER, NRJ_REF_EQUIV, CSTM_NRJ_REF, UNTI_EQUIV, UNIT_INTER_LINK
	}

	public static final class TYPE_REL_CD_MAP {
		public EnumMap<TYPE_REL_CD, Integer> Map = new EnumMap<TYPE_REL_CD, Integer>(
				TYPE_REL_CD.class);

		public TYPE_REL_CD_MAP() {
			Map.put(TYPE_REL_CD.UAC_FOOD, 1);
			Map.put(TYPE_REL_CD.UAC_MOVE, 2);
			Map.put(TYPE_REL_CD.UAC_WEIGHT, 3);
			Map.put(TYPE_REL_CD.UA_TO_UAC, 4);
			Map.put(TYPE_REL_CD.NRJ_REF_INTER, 5);
			Map.put(TYPE_REL_CD.NRJ_REF_EQUIV, 6);
			Map.put(TYPE_REL_CD.CSTM_NRJ_REF, 7);
			Map.put(TYPE_REL_CD.UNTI_EQUIV, 8);
			Map.put(TYPE_REL_CD.UNIT_INTER_LINK, 9);

		}
	}

	/**
	 * NRJ_Class_map : Mapping entre l'énumération NRJ_Class et les valeurs que l'on va stocker dans 
	 * la base de donnée. 
	 * 
	 */
	
	public static final class NRJ_EFFECT_MAP {
		public EnumMap<NRJ_EFFECT, Integer> Map = new EnumMap<NRJ_EFFECT, Integer>(
				NRJ_EFFECT.class);

		public NRJ_EFFECT_MAP() {
			Map.put(NRJ_EFFECT.GIVE, 0);
			Map.put(NRJ_EFFECT.BURN, 1);

		}
	}

	
	public static final class STRUCTURE_CD_MAP {
		public EnumMap<STRUCTURE, Integer> Map = new EnumMap<STRUCTURE, Integer>(
				STRUCTURE.class);

		public STRUCTURE_CD_MAP() {
			Map.put(STRUCTURE.SOLID, 0);
			Map.put(STRUCTURE.LIQUID, 1);

		}
	}

	
	public static final class UNIT_CLASS_MAP {
		public EnumMap<UNIT_CLASS, Integer> _out = new EnumMap<UNIT_CLASS, Integer>(
				UNIT_CLASS.class);
		
		public	HashMap<Integer,UNIT_CLASS> _in = new HashMap<Integer,UNIT_CLASS>();
			
		public UNIT_CLASS_MAP() {
			_out.put(UNIT_CLASS.INTERNATIONAL, TB_Units.PredefinedValues.ClassCodes.INTERNATIONAL);
			_out.put(UNIT_CLASS.CUSTOM, TB_Units.PredefinedValues.ClassCodes.CUSTOM);
			_out.put(UNIT_CLASS.CONTAINER, TB_Units.PredefinedValues.ClassCodes.CONTAINER);
			_out.put(UNIT_CLASS.TIME, TB_Units.PredefinedValues.ClassCodes.TIME);
		
			
			_in.put(TB_Units.PredefinedValues.ClassCodes.INTERNATIONAL, UNIT_CLASS.INTERNATIONAL);
			_in.put(TB_Units.PredefinedValues.ClassCodes.CUSTOM,UNIT_CLASS.CUSTOM);
			_in.put(TB_Units.PredefinedValues.ClassCodes.CONTAINER,UNIT_CLASS.CONTAINER);
			_in.put( TB_Units.PredefinedValues.ClassCodes.TIME,UNIT_CLASS.TIME);
		
		}
	
	}


	

	public static final class UA_CLASS_CD_MAP {
		public EnumMap<UA_CLASS_CD, Integer> _out = new EnumMap<UA_CLASS_CD, Integer>(
				UA_CLASS_CD.class);

		public	HashMap<Integer,UA_CLASS_CD> _in = new HashMap<Integer,UA_CLASS_CD>();
		
		public UA_CLASS_CD_MAP() {
			_out.put(UA_CLASS_CD.LUNCH, 0);
			_out.put(UA_CLASS_CD.MOVE, 1);
			_out.put(UA_CLASS_CD.WEIGHT, 3);
		
			_in.put(TB_UserActivities.PredefinedValues.UACodes.LUNCH, UA_CLASS_CD.LUNCH);
			_in.put(TB_UserActivities.PredefinedValues.UACodes.MOVE, UA_CLASS_CD.MOVE);
			_in.put(TB_UserActivities.PredefinedValues.UACodes.WEIGHT, UA_CLASS_CD.WEIGHT);
			
		
		
		}
	}



	
	




}
