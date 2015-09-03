package com.rdupuis.amikcal.recipe;

public class RecipeObj {

	
//		private List<UserActivityComponent> ComponentList;
//		private boolean cooking; // cuisson
//		private float massBeforeCooking; 
//		private float massAfterCooking;
//		
//		
//		public void setComponentList(List<UserActivityComponent> componentList) {
//			ComponentList = componentList;
//		}
//
//		public List<UserActivityComponent> getComponentList() {
//			return ComponentList;
//		}
//
//		public void setCooking(boolean cooking) {
//			this.cooking = cooking;
//		}
//
//		public boolean isCooking() {
//			return cooking;
//		}
//
//		public void setMassAfterCooking(float massAfterCooking) {
//			this.massAfterCooking = massAfterCooking;
//		}
//
//		public float getMassAfterCooking() {
//			return massAfterCooking;
//		}
//
//		public void setMassBeforeCooking(float massBeforeCooking) {
//			this.massBeforeCooking = massBeforeCooking;
//		}
//
//		public float getMassBeforeCooking() {
//			return massBeforeCooking;
//		}
//
//		/********************************************************
//		 * 	calcule le ratio de poids avant/après cuisson
//		 * car il faut prendre en compte l'eau qui s'évapore
//		 * en effet 100g de pain grillé c'est plus calorique que 100g de pain
//		 * car dans 100g de pain il y a une partie du poids qui est lié à l'eau
//		 * donc a donc moins d'eau et plus de féculents dans le pain grillé.
//		 * 	@return (float) - ratio 
//		 */
//		
//		public float rateCooking(){
//		if (cooking == true || massAfterCooking != 0){
//			return massBeforeCooking/massAfterCooking;
//			}
//		else return 1;
//		}
//		
//		
//		/********************************************************
//		 * récupérer les proteines
//		 * @return (float) - quantité de protéines
//		 */
//		public float proteins(){
//			float result=0f;
//			
//			if (this.getComponentList() != null){
//							
//				for (int i = 0;i<=this.getComponentList().size();i++){
//					
//				//	result += this.getComponentList().get(i).proteins();
//				}
//			}
//			return result;
//		}
//		
//		
//		
//		/********************************************************
//		 * récupérer les lipides
//		 * @return (float) - quantité de lipides
//		 */
//		public float lipids(){
//			float result=0f;
//			
//			if (this.getComponentList() != null){
//							
//				for (int i = 0;i<=this.getComponentList().size();i++){
//					
//				//	result += this.getComponentList().get(i).lipids();
//				}
//			}
//			return result;
//		}	
//		
//		
//		/********************************************************
//		 * récupérer les glucides
//		 * @return (float) - quantité glucides
//		 */
//		public float glucids(){
//			float result=0f;
//			
//			if (this.getComponentList() != null){
//							
//				for (int i = 0;i<=this.getComponentList().size();i++){
//					
//				//	result += this.getComponentList().get(i).glucids();
//				}
//			}
//			return result;
//		}
//		
//		/********************************************************
//		 * récupérer les calories
//		 * @return (float) - quantité de calories
//		 */
//		public float calories(){
//			float result=0f;
//			
//			if (this.getComponentList() != null){
//							
//				for (int i = 0;i<=this.getComponentList().size();i++){
//					
//				//	result += this.getComponentList().get(i).calories();
//				}
//			}
//			return result;
//		}
//					
//		
//		
//		
		
}
