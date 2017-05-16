package pokemon.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application implements java.io.Serializable {
	static Object obj=new Object();
	static Object obj2=new Object();
	private final Lock lock = new ReentrantLock();
	private final Condition condition = lock.newCondition();
	
	List<Pokemon> List_Poke = new ArrayList<Pokemon>();
	List<Station> List_Stat = new ArrayList<Station>();
	List<PokeShow> PSList = new ArrayList<PokeShow>();
	 List<Thread> ThreadPList = new ArrayList<Thread>();
	 List<StatShow> SSList = new ArrayList<StatShow>();
	 List<Thread> ThreadSList = new ArrayList<Thread>();
	private static final long serialVersionUID = 1L;
	BorderPane border = new BorderPane();
	Stage stage;
	
	int pa =0;
	int row;
	int col;
	//int st=0;
	int[] freelocation = new int[2];
	Map map;
	int NumOfPokemon = 0;//total number of pokermon
	int NumOfStation = 0;//total number of station
	File inputFile ;
	String wall = new File("icons/tree.png").toURI().toString();
	 String exit = new File("icons/exit.png").toURI().toString();
	 String station = new File("icons/ball_ani.gif").toURI().toString();
	 private static final String playerR = new File("icons/right.png").toURI().toString();
	 private static final String playerL = new File("icons/left.png").toURI().toString();
	 private static final String playerU = new File("icons/back.png").toURI().toString();
	 private static final String playerD = new File("icons/front.png").toURI().toString();
	 boolean goUp, goDown, goRight, goLeft;
	 protected boolean stop = false;
	 Label score = new Label("0");
	 Label numball = new Label("0");
	// Label space = new Label("    ");
	 Label numPoke = new Label("0");
	 Label showText = new Label(" ");
	 GridPane grid ;//hold the map
	 Player player; 
	 int testx; int testy; 
	 private ImageView avatar=null;
		// current position of the avatar
		int currentPosx ;
		int currentPosy ;
	 int AP =0;
	 VBox vbox = new VBox();
	public void initialize(File inputFile) throws Exception{
		
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		String line = br.readLine();
		row = Integer.parseInt(line.split(" ")[0]);
		col = Integer.parseInt(line.split(" ")[1]);
		map=new Map(row,col);
		for (int i = 0; i < row; i++) {
			line = br.readLine();
			char [] temp = line.toCharArray();
			for(int j = 0; j < col; j++){
				map.setxy(i,j,temp[j]);	
				
				if(temp[j]=='b'||temp[j]=='B'){
					
					map.setstartlocation(i,j);
		
				}
				if(temp[j]=='d'||temp[j]=='D'){
					map.setEndlocation(i, j);
				}
				if(temp[j]=='P'||temp[j]=='p'){
					NumOfPokemon++;
				}
				if(temp[j]=='S'||temp[j]=='s'){
					NumOfStation++;
				}
			
			} }
		  for(int n = 0; n < NumOfPokemon; n++){
		    	line = br.readLine();
		    	setPoke(line,NumOfPokemon);
		    	
		    }
		   
		   for(int n = 0; n < NumOfStation; n++){
		    	line = br.readLine();
		    	setStation(line,NumOfStation);
		    	
		    }
		
	
	
	}
	public void setStation(String input, int NumofS){
		String[] test = input.split(" ");
		String test1=test[0].substring(1,test[0].length()-2);
		String[] location = test1.split(",");
		int x = Integer.parseInt(location[0]);
		int y = Integer.parseInt(location[1]);
		int z = Integer.parseInt(test[1]);
	
		Station P = new Station(x,y,z);
		List_Stat.add(P);
		
	}
	public void setPoke(String input , int NumofP){
		
		String[] test = input.split(" ");
		String test1=test[0].substring(1,test[0].length()-2);
		String[] location = test1.split(",");
		int x = Integer.parseInt(location[0]);
		int y = Integer.parseInt(location[1]);
		String name = test[1].substring(0,test[1].length()-1);
		
		String type = test[2].substring(0,test[2].length()-1);
		
		int combatpower = Integer.parseInt(test[3].substring(0,test[3].length()-1));
	
		int reqball = Integer.parseInt(test[4]);
		
		
		Pokemon P = new Pokemon(x,y,name,type,combatpower,reqball);	
		List_Poke.add(P);		
	}
	
	public static void main(String[] args) throws Exception{
	
		
		launch(Game.class,args);
	}
	
	
  
	private GridPane addMaze(){
       
		synchronized(obj){
		grid.setHgap(0);
		grid.setVgap(0);
		
		
		for(int r=0; r<row; r++){
			for(int c=0; c<col; c++){
				ImageView image=null;
	        if(map.getmap(r,c)=='D')
	        	image= new ImageView(exit);
			if(map.getmap(r,c)=='#')
				image= new ImageView(wall);
			/*if(map.getmap(r,c)=='S')
				image= new ImageView(station);	*/	
			    
            if(image!=null)  
            { setimage(image);
			grid.add(image,c,r);}

			}}
				
		return grid;}
	}
	public void setimage(ImageView input){
		input.setFitHeight(35);
		input.setFitWidth(35);
	}
	private void moveAvatarBy(int dx, int dy) {
		synchronized(obj){
		stop=true;		
		int x=currentPosx+dx ;
		int y =currentPosy+dy;
		if(0<=x&&x<row&&0<=y&&y<col){
			
			if(map.getxy(x,y)!='#')
				{this.map.setmap(currentPosx, currentPosy, ' ');
				player.addstep();
				currentPosx=x;
				
			    currentPosy=y;
			  this.map.setstartlocation(x,y);
			  
			   }
		}
	    
		grid.add(avatar, currentPosy, currentPosx); AP=1;
		
		 if(map.getmap(currentPosx, currentPosy)=='S')
		 {   for(Station S : List_Stat){
			 if(currentPosx==S.getlocation()[0]&&currentPosy==S.getlocation()[1]){
			 player.addball(S.getball());
			 break;}}}
			int s=player.cal();
		
				showText = new Label(" ");
		
			score= new Label(Integer.toString(s));
			numPoke = new Label(Integer.toString(player.NumofPoke));
			numball = new Label(Integer.toString(player.numball()));
			border.getChildren().remove(vbox);
			border.setRight(addhbox());
			
		}
		
	
	}
	public void removeavatar(){
		synchronized(obj){
		grid.getChildren().remove(avatar); 
			AP=0;}
	}
	private void checkAP(){
		synchronized(obj){
		if(AP==0){int temp = 0;
		for(Node node : grid.getChildren()){
			if(node==avatar)
				temp=1;
		}
			if(temp==0){AP=1;
		grid.add(avatar,currentPosy,currentPosx);}}}
	}

  class PokeShow implements Runnable{
	  Pokemon poke;
	  ImageView image;
	  int wakeup=0;
      boolean start=false;
      int stop = 0;
      int sleep =0;
	public PokeShow(Pokemon poke){
	  this.poke=poke;
	  this.image = new ImageView(this.poke.getimage());
	  setimage(image);	
	  synchronized(obj){grid.add(image, this.poke.getlocation()[1],this.poke.getlocation()[0]);}
	
	
	}
	
	@Override
	public void run(){
	
		try {
			
			Thread.yield();
			Thread.sleep(2001);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		 
		while(stop==0){
			
			
		
			 int test =0;
			 synchronized(this){
				 test=sleep;
			 }
			    start=!start;
			   if(test==0){
				try {
					int t = ThreadLocalRandom.current().nextInt(1,2);	
  					Thread.yield();
  					Thread.sleep(t*1000);
  					//randInt();
  				} catch (InterruptedException e) {
  					e.printStackTrace();
  				}}
				synchronized(this){
					if(test==1){
						try {
							int t = ThreadLocalRandom.current().nextInt(2,4);	
		  					Thread.yield();
		  					Thread.sleep(t*1000);
		  					//randInt();
		  				} catch (InterruptedException e) {
		  					e.printStackTrace();
		  				}
						sleep=0;
						wakeup=1;
					}
				
				}
				
		
		
		}
	
	}
	}
  class StatShow implements Runnable{
	  Station stat;
	  ImageView image = new ImageView(station);
	  boolean show ;
	  int start=0;
	  
	  public StatShow(Station S){
		  this.stat=S;
		  setimage(image);	
		 
		  synchronized(obj){grid.add(this.image, this.stat.getlocation()[1], this.stat.getlocation()[0]);	
			 show = true;
			}
	  }
	@Override
	public void run() {
		
		while(true){
			
			synchronized(this.stat){if(show==false){
				try {
  						
  					int t = ThreadLocalRandom.current().nextInt(5,10);
  					Thread.yield();
  					Thread.sleep(t*1000);
  					//randInt();
  				} catch (InterruptedException e) {
  					e.printStackTrace();
  				}
					show=true;
  					start=1;
  				    
			}}
			
			
		}
	}
	  
  }
  public int[] getfreelocation(){
	  int[] result = new int[2] ;
	
	  boolean T=false;
	  int c=0;
	  int r=0;
	  while(!T){
		   c=ThreadLocalRandom.current().nextInt(0, col);
		  r=ThreadLocalRandom.current().nextInt(0, row );
		  if(map.getxy(r, c)==' ')
		    T=true;
		
	  }
	 
   
		  result[0]=r;
		  result[1]=c;
	  
	  return result;
  }
  
  public void StatUI(StatShow SS){
	  if(pa==0){
	  synchronized(obj) {if(map.getstartlocation()[0]==SS.stat.getlocation()[0]&&map.getstartlocation()[1]==SS.stat.getlocation()[1]&&SS.show==true){
		  synchronized(SS){
			 // player.addball(SS.stat.getball());
			  grid.getChildren().remove(SS.image);
				SS.stat.setlocation(0, 0);
				SS.show=false;
			
				}	}
		  
		  
	  }
	    
		 if(SS.start==1){
		  synchronized(SS){
			  SS.start=0;}
		
		  synchronized(obj){			  
			  freelocation = getfreelocation();
			  grid.add(SS.image, freelocation[1], freelocation[0]);
			  SS.stat.setlocation(freelocation[0], freelocation[1]);
			  map.setxy(freelocation[0], freelocation[1],'S');
			  
		  }
	  
  }}
	  
	  
  }
	
  public void PokeUI(PokeShow PS){
	   if(pa==0){
	   if(map.getstartlocation()[0]==PS.poke.getlocation()[0]&&map.getstartlocation()[1]==PS.poke.getlocation()[1]&&PS.stop==0&&PS.sleep==0){
		   synchronized(obj){
			     PS.poke.setlocation(0, 0);
				 grid.getChildren().remove(PS.image);
				 }
		  if(PS.poke.tryCatch(player.numball())){
			  
			synchronized(showText){  showText= new Label("Pokemon Caught!!");
			  //st=1;
			  }
			  showText.setTextFill(Color.GREEN);
			  border.getChildren().remove(vbox);
			  
			
			  synchronized(obj){
			player.addPoke(PS.poke);
			  player.addball(-PS.poke.getreqball());
			  int s=player.cal();		
				score= new Label(Integer.toString(s));
				numPoke = new Label(Integer.toString(player.NumofPoke));
				numball = new Label(Integer.toString(player.numball()));
				border.getChildren().remove(vbox);
				border.setRight(addhbox());		  
			  }
			  synchronized(PS){
				  PS.stop=1;	PS.sleep=1;  }
			
		  }
		  else{	  
			  synchronized(showText){  showText= new Label(" Not enough pokemon ball");
			  //st=1;
			  }
			  showText.setTextFill(Color.RED);
			  border.getChildren().remove(vbox);
				border.setRight(addhbox());
			  synchronized(PS){
				  PS.sleep=1;
			  }
			  
		  }
	  }
	 
	   
	  
	  else if(PS.start==true){
		 
		if(PS.sleep==0){  if(PS.wakeup==1){
			synchronized(PS){  PS.wakeup=0;}
		    	  synchronized(obj){			  
					  freelocation = getfreelocation();
					  grid.add(PS.image, freelocation[1], freelocation[0]);
					  PS.poke.setlocation(freelocation[0], freelocation[1]);
					  map.setxy(freelocation[0], freelocation[1],'S');		
					
				  }
		    	 
		     }
		     else{
			 PS.start=!PS.start;
			  synchronized(obj){
			  Random rand = new Random();
			  int randomNum = rand.nextInt((4 - 1) + 1) + 1;//1= up, 2= down, 3= left, 4= right
			  int move=0;     
			  boolean testvalidmove=false;
			  while(!testvalidmove){
				  
				  if(move==4)
					  break;
				  if(randomNum==1){
					  testvalidmove=checkvalidmove(PS.poke.getlocation()[0]-1,PS.poke.getlocation()[1]);
					  if(testvalidmove){
						  testx=PS.poke.getlocation()[0]-1;testy=PS.poke.getlocation()[1];
					  }
				  }
				  if(randomNum==2){
					  testvalidmove=checkvalidmove(PS.poke.getlocation()[0]+1,PS.poke.getlocation()[1]);
					  if(testvalidmove){
						  testx=PS.poke.getlocation()[0]+1;testy=PS.poke.getlocation()[1];
					  }
				  }
				  if(randomNum==3){
					  testvalidmove=checkvalidmove(PS.poke.getlocation()[0],PS.poke.getlocation()[1]-1);
					  if(testvalidmove){
						  testx=PS.poke.getlocation()[0];testy=PS.poke.getlocation()[1]-1;
					  }
				  }
				  if(randomNum==4){
					  testvalidmove=checkvalidmove(PS.poke.getlocation()[0],PS.poke.getlocation()[1]+1);
					  if(testvalidmove){
						  testx=PS.poke.getlocation()[0];testy=PS.poke.getlocation()[1]+1;
					  }
				  }
				  
				  else if(randomNum==4)
					  randomNum=1;
				  else {randomNum++;}
				  move++;
				  
			  }
			  if(move!=4){
	  
			  map.setmap(PS.poke.getlocation()[0], PS.poke.getlocation()[1],' ');
			  PS.poke.setlocation(testx, testy);
			  grid.getChildren().remove(PS.image);
			  map.setmap(PS.poke.getlocation()[0], PS.poke.getlocation()[1],'P');
			 
		  grid.add(PS.image, PS.poke.getlocation()[1], PS.poke.getlocation()[0]);}
			  }}}}
	  

		  		  
		  
	  }}

  
 public boolean checkvalidmove(int x , int y){
	  boolean result = false;
	  if(map.getstartlocation()[0]==x&&map.getstartlocation()[1]==y)
		  result = false;
	  
	  else if( 0<=x&&x<row&&0<=y&&y<col)
	 {   
				 if(map.getmap(x, y)!='#'&&map.getmap(x, y)!='S'&&map.getmap(x, y)!='D'&&map.getmap(x, y)!='P'){
					 
					 result = true;
					
				 }
					 
		 
	 }
	 
	  return result;
  }
 public VBox addhbox(){
	 VBox vbox = new VBox(10);
	 vbox.setPadding(new Insets(0, 20, 10, 20)); 
	 HBox hbox2 = new HBox(10);
	 HBox hbox3 = new HBox(10);
	 HBox hbox4 = new HBox(0);
	 HBox hbox5 = new HBox(10);
	 hbox2.getChildren().addAll(new Label("# of Pokemon caught:"),numPoke);
	 HBox hbox = new HBox(10);
	 hbox.getChildren().addAll(new Label("Current Score: "),score);
	 hbox3.getChildren().addAll(new Label("# of Pokeball own : "),numball);
	 hbox5.getChildren().addAll(showText);
	 Button resume = new Button("Resume");
	 Button Pause = new Button("Pause");
	 resume.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				synchronized(obj){pa=0;}
			}
		});
	 Pause.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			synchronized(obj){
			pa=1;}
			}
		});
	 hbox4.getChildren().addAll(resume,Pause);
	 vbox.getChildren().addAll(hbox,hbox2,hbox3,hbox4,hbox5);
	 return vbox;
	  
  }
	

   
	@Override
	public void start(Stage stage) throws Exception {
	
		
		grid = new GridPane();
		player = new Player();
	    inputFile=new File("./sampleIn.txt");
	    this.initialize(inputFile);
		this.stage=stage;
		this.avatar=new ImageView(playerR);
		setimage(avatar);
		currentPosx=this.map.getstartlocation()[0];//row
		currentPosy=this.map.getstartlocation()[1];//col
		
		border.setCenter(addMaze());
		vbox=addhbox();
		border.setRight(vbox);
		synchronized(obj){
		grid.add(avatar,currentPosy,currentPosx);
		AP=1;}
	    
		
		
		for(Pokemon P : List_Poke){
			PokeShow PS = new PokeShow(P);
			Thread T= new Thread(PS);
			//T.start();
			PSList.add(PS);
			ThreadPList.add(T);
		}
		for(Station S : List_Stat){
			StatShow SS = new StatShow(S);
			Thread T = new Thread(SS);/////////////////////////
			SSList.add(SS);
			ThreadSList.add(T);
		}
		for(Thread T : ThreadSList){
			T.setDaemon(true);
			T.start();
		}
		for(Thread T : ThreadPList){
			T.setDaemon(true);
			T.start();
		}
	
         Scene scene = new Scene(border);
         scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
 			@Override
 			public void handle(KeyEvent event) {if(pa==0){
 				switch (event.getCode()) {
 				case UP:
 					goUp = true;
 					
 					removeavatar();
 					
 					avatar=new ImageView(playerU);
 					setimage(avatar);
 					
 					break;
 				case DOWN:
 					goDown = true;
 					
 					removeavatar();
 					avatar=new ImageView(playerD);		
 					setimage(avatar);
 					
 					break;
 				case LEFT:
 					goLeft = true;
 					removeavatar();				
 					avatar=new ImageView(playerL);
 					setimage(avatar);
 					
 					break;
 				case RIGHT:
 					goRight = true;
 					removeavatar();
 					avatar=new ImageView(playerR);
 					setimage(avatar);
 					
 					break;
 				default:
 					break;
 				}
 			}}
 		});
         scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
 			@Override
 			public void handle(KeyEvent event) {
 				switch (event.getCode()) {
 				case UP:
 					goUp = false;
 					break;
 				case DOWN:
 					goDown = false;
 					break;
 				case LEFT:
 					goLeft = false;
 					break;
 				case RIGHT:
 					goRight = false;
 					break;
 				default:
 					break;
 				}
 				
 			
 				checkAP();
 				
 				stop = false;
 			}
 		});
		stage.setScene(scene);
		 stage.show();
			AnimationTimer timer = new AnimationTimer() {
				@Override
				public void handle(long now) {
					if(map.getEndlocation()[0]==currentPosx&&map.getEndlocation()[1]==currentPosy){
						synchronized(obj){pa=1;}
						synchronized(showText){  showText= new Label("End Game!");
						  }
						  showText.setTextFill(Color.GREEN);
						  border.getChildren().remove(vbox);
							border.setRight(addhbox());
						
					}
					
					
					
					for(PokeShow PS: PSList){
						
						PokeUI( PS);
					}
					for(StatShow SS : SSList){
						StatUI(SS);
					}
					if (stop){
						
						checkAP();
					return;}

				int dx = 0, dy = 0;

				if (goUp) {
					
					dx =-1;
				} else if (goDown) {
					dx =1;
				} else if (goRight) {
					dy = 1;
				} else if (goLeft) {
					dy = -1;
				} else {
					// no key was pressed return
					return;
				}
			    
				moveAvatarBy(dx, dy);
			}
				
				 	
				
			};
			// start the timer
			timer.start();
		

		
	}
	
	@Override
	public void stop()
	{ synchronized(obj){ for(PokeShow PS: PSList){
		
			
			grid.getChildren().remove(PS.image);
		
		
	}}
	   synchronized(obj){ for(StatShow SS : SSList){
	    
	    		
	    		grid.getChildren().remove(SS.image);}}
	   
	  
	    synchronized(obj){  grid.getChildren().clear();}
	    grid = new GridPane();
	    PSList.clear();
	    SSList.clear();
		ThreadPList.clear();
		ThreadSList.clear();
		
		
	    Platform.exit();
	    
	}
	

}
