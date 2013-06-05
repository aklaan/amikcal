package com.rdupuis.amikcal.recipe;

import java.util.List;

import com.rdupuis.amikcal.useractivitycomponent.UserActivityComponentObj;

public class RecipeObj {

	
		private List<UserActivityComponentObj> ComponentList;
		private boolean cooking; // cuisson
		private float massBeforeCooking; 
		private float massAfterCooking;
		
		
		public void setComponentList(List<UserActivityComponentObj> componentList) {
			ComponentList = componentList;
		}

		public List<UserActivityComponentObj> getComponentList() {
			return ComponentList;
		}

		public void setCooking(boolean cooking) {
			this.cooking = cooking;
		}

		public boolean isCooking() {
			return cooking;
		}

		public void setMassAfterCooking(float massAfterCooking) {
			this.massAfterCooking = massAfterCooking;
		}

		public float getMassAfterCooking() {
			return massAfterCooking;
		}

		public void setMassBeforeCooking(float massBeforeCooking) {
			this.massBeforeCooking = massBeforeCooking;
		}

		public float getMassBeforeCooking() {
			return massBeforeCooking;
		}

		/********************************************************
		 * 	calcule le ratio de poids avant/apr�s cuisson
		 * car il faut prendre en compte l'eau qui s�vapore
		 * en effet 100g de pain grill� c'est plus calorique que 100g de pain
		 * car dans 100g de pain il y a une partie du poids qui est li� � l'eau
		 * donc a donc moins d'eau et plus de f�culents dans le pain grill�.  	
		 * 	@return (float) - ratio 
		 */
		
		public float rateCooking(){
		if (cooking == true || massAfterCooking != 0){
			return massBeforeCooking/massAfterCooking;
			}
		else return 1;
		}
		
		
		/********************************************************
		 * r�cup�rer les proteines
		 * @return (float) - quantit� de prot�ines 
		 */
		public float proteins(){
			float result=0f;
			
			if (this.getComponentList() != null){
							
				for (int i = 0;i<=this.getComponentList().size();i++){
					
				//	result += this.getComponentList().get(i).proteins();
				}
			}
			return result;
		}
		
		
		
		/********************************************************
		 * r�cup�rer les lipides
		 * @return (float) - quantit� de lipides 
		 */
		public float lipids(){
			float result=0f;
			
			if (this.getComponentList() != null){
							
				for (int i = 0;i<=this.getComponentList().size();i++){
					
				//	result += this.getComponentList().get(i).lipids();
				}
			}
			return result;
		}	
		
		
		/********************************************************
		 * r�cup�rer les glucides
		 * @return (float) - quantit� glucides
		 */
		public float glucids(){
			float result=0f;
			
			if (this.getComponentList() != null){
							
				for (int i = 0;i<=this.getComponentList().size();i++){
					
				//	result += this.getComponentList().get(i).glucids();
				}
			}
			return result;
		}
		
		/********************************************************
		 * r�cup�rer les calories
		 * @return (float) - quantit� de calories 
		 */
		public float calories(){
			float result=0f;
			
			if (this.getComponentList() != null){
							
				for (int i = 0;i<=this.getComponentList().size();i++){
					
				//	result += this.getComponentList().get(i).calories();
				}
			}
			return result;
		}
					
		
		
		
		
}
