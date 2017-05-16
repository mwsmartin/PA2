package pokemon.ui;

public class Map {
	/*
	 * Map of Input txt would be turn to this class Map
	 */
    
    public char[][] xy; 
    private int[] startlocation = new int[2];
    private int[] Endlocation = new int[2];
    private int[] dimension = new int[2];
    
    /*
     * class constructor
     */
    public Map(int i, int j){
    	xy= new char[i][j];
    	dimension[0]=i;
    	dimension[1]=j;
    	
    }
    
    /*
     * return dimenson of map
     */
    public int[] getdimension(){
    	return this.dimension;
    }
    
    /*
     * assign element to a certain location of map
     */
    
    public void setmap(int i, int j, char c){
    	xy[i][j]=c;
    }
    
    /*
     * return element of a certain location of map
     */
    public char getmap(int i,int j){
    	return xy[i][j];
    }
    
    /*
     * set startlocation of player
     */
    public void setstartlocation(int i , int j){
    	startlocation[0]=i;
    	startlocation[1]=j;
    }
    
    /*
     * set destination 
     */
    public void setEndlocation(int i , int j){
    	Endlocation[0]=i;
    	Endlocation[1]=j;
    }
    
    /*
     * get location of  destination
     */
    
    public int[] getEndlocation(){
    	return Endlocation;
    	
    }
    
    /*
     * get play's start location
     */
    public int[] getstartlocation(){
    	return startlocation;
    }
   /*
    * set element of map in location[i][j]
    */
    public void setxy(int i , int j, char c){
    	xy[i][j] =c;
    }
    
    /*
     * get element of map in location[i][j]
     */
    public char getxy(int i , int j){
    	return xy[i][j];
    }
  
}
