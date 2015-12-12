package Structures;

import java.util.List;

import org.osbot.rs07.api.Inventory;
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

	public Position position;
	
	public final int itemID;
	public final int failedID;
	public final int groundID;
	public final int successID;
	public final int setID;
	
	
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
			core.waitTime(400);
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
		List<RS2Object> objects = script.getObjects().get(position.getX(), position.getY());
		switch (this.checkTrap(script)){
			case FAIL:
				for(RS2Object x : objects){
					if(x.getId() == this.failedID){
						while(x.exists()){
							x.interact("Dismantle");
							core.waitTime(300);
						}
						break;
					}
				}
				break;
			case SUCCESS:
				for(RS2Object x : objects){
					if(x.getId() == this.successID){
						while(x.exists()){
							x.interact("Check");
							core.waitTime(300);
						}
						break;
					}
				}
				break;
			case GROUND:
				for(RS2Object x : objects){
					if(x.getId() == this.groundID){
						while(x.exists()){
							x.interact("Lay");
							core.waitTime(300);
						}
						break;
					}
				}
				break;
			default:
				//do fuck all
				break;
		}
	}
	
	private State checkTrap(Script script){
		List<RS2Object> objects = script.getObjects().get(position.getX(), position.getY());
		State currentState = State.UNKNOWN;
		if(!objects.isEmpty()){
			for(RS2Object obj : objects){
				if(obj.getId() == this.setID){
					currentState = State.SET;
					break;
				} else if (obj.getId() == this.failedID){
					currentState = State.FAIL;
					break;
				} else if (obj.getId() == this.groundID){
					currentState = State.GROUND;
					break;
				} else if (obj.getId() == this.successID){
					currentState = State.SUCCESS;
					break;
				} else {
					currentState = State.UNKNOWN;
				}
			}
		}
		//TODO: flowered state
		script.log("State: " + currentState);
		return currentState;
	}
	
	public boolean isSet(Script script){
		State currentState = this.checkTrap(script);
		return (currentState == State.SET) ? true : false;
	}
	public boolean isGround(Script script){
		State currentState = this.checkTrap(script);
		return (currentState == State.GROUND) ? true : false;
	}
	public boolean isFlowered(Script script){
		State currentState = this.checkTrap(script);
		return (currentState == State.FLOWERED) ? true : false;
	}
	public boolean isGood(Script script){
		State currentState = this.checkTrap(script);
		return (currentState == State.SUCCESS) ? true : false;
	}
	public boolean isBad(Script script){
		State currentState = this.checkTrap(script);
		return (currentState == State.FAIL) ? true : false;
	}
	public boolean isUnknown(Script script){
		State currentState = this.checkTrap(script);
		return (currentState == State.UNKNOWN) ? true : false;
	}
	
	@Override
	public boolean equals(Object trap){
		if(this == trap){
			return true;
		}
		if(trap == null){
			return false;
		}
		if(!(trap instanceof TrapSpot)){
			return false;
		}
		
		return false;
		
	}
	
}
