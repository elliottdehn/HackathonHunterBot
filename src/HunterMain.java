

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;


import HelperMethods.Core;
import Structures.Node;
import Structures.TrapSpot;
import Structures.Activities.handleASingleTrap;
import Structures.Activities.setUpTrap;
//standing = 9380
//failed = 9385
//success = 933
//dropped = 10008
//item = 10009
@ScriptManifest(author = "Elliott", info = "hunter bot", name = "Hunter", version = 1.0, logo = "")
public class HunterMain extends Script{
	
	public ArrayList<Node> mainRoutine = new ArrayList<Node>();
	public ArrayList<Node> initRoutine = new ArrayList<Node>();
	
	public TrapSpot trap1;
	public TrapSpot trap2;
	public TrapSpot trap3;
	public TrapSpot trap4;
	public TrapSpot trap5;
	
	public ArrayList<TrapSpot> allTraps = new ArrayList<TrapSpot>();	
	public LinkedList<TrapSpot> priority = new LinkedList<TrapSpot>();
	public LinkedList<TrapSpot> secondary = new LinkedList<TrapSpot>();
	
	public int readyID = 9380;
	public int failedID = 9385;
	public int successID = 9383;
	public int droppedID = 10008;
	public int inventoryID = 10008;
	
	
	
	@Override
	public void onStart(){
		Position playerPos = this.myPlayer().getPosition();
		
		Position place1 = new Position(playerPos.getX() - 1, playerPos.getY()-1, playerPos.getZ());
		Position place2 = new Position(playerPos.getX() + 1, playerPos.getY()+1, playerPos.getZ());
		Position place3 = new Position(playerPos.getX()-1, playerPos.getY() + 1, playerPos.getZ());
		Position place4 = new Position(playerPos.getX()+1, playerPos.getY() - 1, playerPos.getZ());
		
		trap1 = new TrapSpot(place1, readyID, successID, failedID, droppedID, inventoryID);
		trap2 = new TrapSpot(place2, readyID, successID, failedID, droppedID, inventoryID);
		trap3 = new TrapSpot(place3, readyID, successID, failedID, droppedID, inventoryID);
		trap4 = new TrapSpot(place4, readyID, successID, failedID, droppedID, inventoryID);
		
		allTraps.add(trap1);
		allTraps.add(trap2);
		allTraps.add(trap3);
		allTraps.add(trap4);
		
		Node setTrap1 = new setUpTrap(this, trap1);
		Node setTrap2 = new setUpTrap(this, trap2);
		Node setTrap3 = new setUpTrap(this, trap3);
		Node setTrap4 = new setUpTrap(this, trap4);
		
		Collections.addAll(initRoutine, setTrap1, setTrap2, setTrap3, setTrap4);
		
		for(Node x : initRoutine){
			if(x.validate()){
				x.execute();
			}
		}
		
		
	}
	@Override
	public int onLoop() throws InterruptedException {
		//checks the status of every trap and makes a list of traps to handle and in what order to handle them
		for(TrapSpot trap : allTraps){
			log("Set: " + trap.isSet(this));
			if(trap.isGround(this) && !priority.contains(trap)){
				//state changed to ground since we last checked
				if(secondary.contains(trap)){
					secondary.remove(trap);
				}
				priority.add(trap);
			} else if (!trap.isSet(this) && !secondary.contains(trap)){
				secondary.add(trap);
			}
		}
		
		Node handleASingleTrap = new handleASingleTrap(this, priority, secondary);
		if(handleASingleTrap.validate()){
			handleASingleTrap.execute();
		
		}
		return 100;
	}

}
