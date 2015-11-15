package Structures;

import org.osbot.rs07.api.Inventory;
import org.osbot.rs07.api.LocalWalker;
import org.osbot.rs07.api.Objects;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.script.Script;

import HelperMethods.Core;

public class TrapSpot {
	
	public Core core = new Core();
	
	public enum State {
		FAIL, SUCCESS, GROUND, SET, UNKNOWN, FLOWERED
	}
	
	public Position position;
	
	private int itemID;
	private int failedID;
	private int groundID;
	private int successID;
	private int setID;
	
	
	public TrapSpot(Position pos, int readyID, int trappedID, int failID, int droppedID, int invID){
		position = pos;
		
		itemID = invID;
		successID = trappedID;
		failedID = failID;
		groundID = droppedID;
		setID = readyID;
		
	}
	
	public void setTrap(Script script){
		LocalWalker walker = script.getLocalWalker();
			
		walker.walk(position);


			
		Inventory inv = script.getInventory();
		Item trap = inv.getItem(itemID);
		
		trap.interact("Lay");
		while(!script.myPlayer().isAnimating()){
			core.waitTime(10);
		}
		while(script.myPlayer().isAnimating()){
			core.waitTime(100);
		}
		
		script.log("Attempted to lay");
		
		while(script.myPlayer().isAnimating()){
			core.waitTime(100);
		}
	}
	
	public void setTrapPosition(int x, int y, int z){
		Position newPos = new Position(x, y, z);
		position = newPos;
	}

	public void grabTrap(Script script){
		//if the trap failed, use that object id
		//if the trap trapped, pick up that object id
		//if the drop dropped, pick up item id at location
		switch (this.checkTrap()){
			Objects objects = script.getObjects();
			List<RS2Object> atLocation = objects.get(xPos, yPos);
			case FAIL:
				break;
			case SUCCESS:
				break;
			case GROUND:
				break;
		}
	}
	
	public State checkTrap(){
		//check the position, use logic with returned id
		State trapState = State.SET;
		return trapState;
	}
	
}
