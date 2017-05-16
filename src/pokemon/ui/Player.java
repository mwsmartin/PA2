package pokemon.ui;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private int myball ;
	private int step;
	int TypeofPoke;
	int NumofPoke;
	int maxcombat;
	public int mark;
	public List<Pokemon> List_Poke = new ArrayList<Pokemon>();
    
	public void addstep(){
		step++;
	}
	public void addball(int x){
		myball+=x;
	}
	public int numball(){
		return myball;
	}
	public void addPoke(Pokemon P){
		List_Poke.add(P);
	}
	 public int cal(){
		 int m;
		 int numbp ;
		 if(List_Poke==null)
			 numbp =0;
		 else numbp = List_Poke.size();
		
		 int combat =0;
		 int numbt = 0;
		 List<String> numType = new ArrayList<String>();
		 if(List_Poke!=null)
		 for(Pokemon p : List_Poke){
			 if(p.getcombat()>combat)
				 combat=p.getcombat();
			 String type=p.gettype();
			 int temp =0 ;
			 for(String S : numType){
				 if(S.equals(type))
					 temp=1;
				 
			 }
			 if(temp==0)
				 {numbt++;
				 
				 numType.add(type);}
		 }
		 
		 maxcombat=combat;
		 NumofPoke=numbp;
		 TypeofPoke=numbt;
		
		 
		 m=myball+5*numbp+10*numbt+combat-step;
		 return m;
	 }

}
