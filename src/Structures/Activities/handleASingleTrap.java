package Structures.Activities;

import java.util.ArrayList;
import java.util.LinkedList;

import org.osbot.rs07.script.Script;

import Structures.Node;
import Structures.TrapSpot;

public class handleASingleTrap extends Node{
	private LinkedList<TrapSpot> priority;
	private LinkedList<TrapSpot> secondary;
	
	public handleASingleTrap(Script script, LinkedList<TrapSpot> fallenTraps, LinkedList<TrapSpot> otherTraps) {
		super(script);	
		priority = fallenTraps;
		secondary = otherTraps;
	}

	@Override
	public void execute() {
		if(priority.size() != 0){
			handleGround(priority.removeFirst());
		} else {
			TrapSpot front = secondary.removeFirst();
			if(front.isBad()){
				handleBad(front);
			} else if (front.isGood()){
				handleGood(front);
			} else if (front.isFlowered()){
				handleFlower(front);
			}
		}
	}



	@Override
	public boolean validate() {
		//if no traps need to be handled, do nothing
		return priority.size() == 0 && secondary.size() == 0;
	}
	
	private void handleGood(TrapSpot trap){
		
	}
	private void handleBad(TrapSpot trap){
		
	}
	private void handleGround(TrapSpot trap){
		//pick up the trap
		//set up trap node execute
	}
	private void handleFlower(TrapSpot front) {
		
	}
	
	
}
