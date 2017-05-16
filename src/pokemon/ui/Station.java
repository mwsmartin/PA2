package pokemon.ui;

public class Station  {
	private boolean isVisited;
	private int numberOfball;
	private int [] location = new int[2];
	
	/*
	 * class constructor
	 */
	public Station(int x, int y, int ball){
		isVisited = false;
		location[0] =x;
		location[1] =y;
		numberOfball =ball;
		
	}
	public int[] getlocation(){
		return location;
	}
	public int getball(){
		return numberOfball;
	}
	public void setlocation(int x, int y){
		location[0]=x;
		location[1]=y;
	}
	
}
