package Structures;

import java.util.List;

import org.osbot.rs07.api.Inventory;
import org.osbot.rs07.api.LocalWalker;
import org.osbot.rs07.api.Objects;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.input.mouse.MainScreenTileDestination;
import org.osbot.rs07.script.Script;

import HelperMethods.Core;

public class TrapSpot {
	
	public Core core = new Core();
	
	public enum State {
		FAIL, SUCCESS, GROUND, SET, UNKNOWN, FLOWERED
	}

	private State currentState;
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
		MainScreenTileDestination mainScreenSpot = new MainScreenTileDestination(script.getBot(), position);
		script.getMouse().click(mainScreenSpot);

		while(!script.myPlayer().getPosition().equals(position) || script.myPlayer().isAnimating()){
			core.waitTime(300);
			script.getMouse().click(mainScreenSpot);
		}
			
		Inventory inv = script.getInventory();
		Item trap = inv.getItem(itemID);
		while(script.myPlayer().isAnimating()){
			core.waitTime(100);
		}
		trap.interact("Lay");
		while(!script.myPlayer().isAnimating()){
			core.waitTime(10);
		}
		while(script.myPlayer().isAnimating()){
			core.waitTime(100);
		}
		
		script.log("Attempted to lay");

	}
	
	public void setTrapPosition(int x, int y, int z){
		Position newPos = new Position(x, y, z);
		position = newPos;
	}

	public void grabTrap(Script script){
		Objects objects = script.getObjects();
		List<RS2Object> atLocation = objects.get(position.getX(), position.getY());
		switch (this.checkTrap()){
			case FAIL:
				RS2Object failedTrap;
				for(int i = 0; i < atLocation.size(); i++){
					if(atLocation.get(i).getId() == failedID){
						failedTrap = atLocation.get(i);
						failedTrap.interact("Dismantle");
						while(!script.myPlayer().isAnimating()){
							core.waitTime(10);
						}
						while(script.myPlayer().isAnimating()){
							core.waitTime(100);
						}
						break;
					}
				}
				break;
			case SUCCESS:
				RS2Object successTrap;
				for(int i = 0; i < atLocation.size(); i++){
					if(atLocation.get(i).getId() == successID){
						successTrap = atLocation.get(i);
						successTrap.interact("Check");
						while(!script.myPlayer().isAnimating()){
							core.waitTime(10);
						}
						while(script.myPlayer().isAnimating()){
							core.waitTime(100);
						}
						break;
					}
				}
				break;
			case GROUND:
				RS2Object groundTrap;
				for(int i = 0; i < atLocation.size(); i++){
					if(atLocation.get(i).getId() == groundID){
						groundTrap = atLocation.get(i);
						groundTrap.interact("Take");
						while(!script.myPlayer().isAnimating()){
							core.waitTime(10);
						}
						while(script.myPlayer().isAnimating()){
							core.waitTime(100);
						}
						break;
					}
				}
				break;
		}
	}
	
	private State checkTrap(){
		//insert logic where we set the state
		currentState = State.SET;
		return currentState;
	}
	
	public boolean isSet(){
		this.checkTrap();
		return (currentState == State.SET) ? true : false;
	}
	public boolean isGround(){
		this.checkTrap();
		return (currentState == State.GROUND) ? true : false;
	}
	public boolean isFlowered(){
		this.checkTrap();
		return (currentState == State.FLOWERED) ? true : false;
	}
	public boolean isGood(){
		this.checkTrap();
		return (currentState == State.SUCCESS) ? true : false;
	}
	public boolean isBad(){
		this.checkTrap();
		return (currentState == State.FAIL) ? true : false;
	}
	
	public boolean equals(TrapSpot trap){
		if(this.position.equals(trap.position)){
			return true;
		}
		
		return false;
		
	}
	
}
