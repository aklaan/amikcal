package com.rdupuis.amikcal.Users;

import java.util.Date;

public class UserObj {

	private String name;
	private Date birthday;
	private int experience;
	private int nbStars;
	private int level;
	private int language;
	private String idGoogle; 
	
	////////////////////////////////////////////////////////////
	//Getters
	///////////////////////////////////////////////////////////
	public String getName(){
		return name;
	}

	public Date getBirthday(){
		return birthday;
	}
	
	public int getExp(){
		return experience;
	}
	
	public int getNbstars(){
		return nbStars;
	}
	
	public int getLevel(){
		return level;
	}
     
	public int getLanguage() {
		return language;
	}
	/////////////////////////////////////////////////////////////
	//Setters
	////////////////////////////////////////////////////////////
		public void setName(String value){
		try {
			name= value;
			}
		catch (Exception e) {System.out.println(e.getMessage());		
		}
	}
	
		public void setExp(int value){
		try {
			experience = value;
			}
		catch (Exception e) {System.out.println(e.getMessage());		
		}
	}
	
		public void setNbstars(int value){
			try {
				nbStars = value;
				}
			catch (Exception e) {System.out.println(e.getMessage());		
			}
		}
	
		public void setLevel(int value){
			try {
				level = value;
				}
			catch (Exception e) {System.out.println(e.getMessage());		
			}
		}
		public void setLanguage(int language) {
			this.language = language;
		}
	
	public void setIdGoogle(String idGoogle) {
			this.idGoogle = idGoogle;
		}

		public String getIdGoogle() {
			return idGoogle;
		}

	//Constructeur
	public UserObj(){
		this.setName("anonymous");
		this.setExp(0);
	}
	
	
	///////////////////////////////////////////////////////////////////
	// load() : Charger le profil : 1 seul profile possible 
	// 
	//////////////////////////////////////////////////////////////////
	public void load(){
		
	}
	
	
	///////////////////////////////////////////////////////////////////
	// save() : sauver le profil 
	// 
	//////////////////////////////////////////////////////////////////
	public void save(){
		
	}

	

	
	
}
