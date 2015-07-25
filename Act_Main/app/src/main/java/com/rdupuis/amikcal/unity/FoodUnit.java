package com.rdupuis.amikcal.unity;

public class FoodUnit extends Unity {
	public static enum FOOD_COMPONENT_CLASS {
		LIPID, GLUCID, PROTEIN, VITAMIN
	}

	private FOOD_COMPONENT_CLASS FoodComponentClass;

	public FoodUnit() {
		
		super.setUnityClass(UNIT_CLASS.FOOD_COMPONENT);
	}

	public void setFoodComponentParty(FOOD_COMPONENT_CLASS foodComponent) {
		this.FoodComponentClass = foodComponent;
	}

	public FOOD_COMPONENT_CLASS getFoodComponentParty() {
		return this.FoodComponentClass;
	}
	@Override
	public void setUnityClass(UNIT_CLASS mClass) {
		// Ne rien faire
	}

}
