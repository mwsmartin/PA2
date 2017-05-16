package pokemon.ui;

import java.io.File;

public class Pokemon  {
	private boolean catched;
	private int[] location ;
	private String name;
	private String type;
	private int combatpower;
	private int reqball;
	private String image;
	
	public Pokemon(int x, int y, String name, String type, int combatpower, int reqball){
		catched = false;	
		location = new int [2];
		this.location[0] =x;
		this.location[1] =y;
		this.name=name;
		this.type=type;
		this.combatpower=combatpower;
		this.reqball=reqball;
		int n = PokemonList.getIdOfFromName(name);
		this.image= new File("icons/"+n+".png").toURI().toString();
		
	}
	public String getimage(){
		return this.image;
	}
	 /*
	  * set catched
	  */
	 public void setcatch(boolean c){
		 catched=c;
		 
	 }
	
	/*
	 * return number of required ball
	 */
	public int getreqball(){
		return reqball;
	}
	
	/*
	 * return combat power
	 */
	public int getcombat(){
		return combatpower;
	}
	
	/*
	 * return type of pokemon
	 */
	public String gettype(){
		return type;
	}
	/*
	 * return name of pokemon
	 */
	
	public String getname(){
		return name;
	}
	
	/*
	 * return location of pokemon
	 */
	public int[] getlocation(){
		return location;
	}
	
	/*
	 * try to catch the pokemon, if catch successfully, return true
	 */
	
	public boolean tryCatch(int ball){
		if(this.catched==false){
		if(ball>=reqball){
			this.catched= true;
			return true;
		}
		
		else
			return false;}
		return false;
	}
	public void setlocation(int x, int y){
		location[0]=x;
		location[1]=y;
	}
	/*
	 * check if the pokemon has been catched
	 */
	public boolean isCatched(){
		return catched;
	}
}
