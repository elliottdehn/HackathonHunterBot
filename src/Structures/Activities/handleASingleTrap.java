package Structures.Activities;

import java.util.LinkedList;

import org.osbot.rs07.script.Script;

import HelperMethods.Core;
import Structures.Node;
import Structures.TrapSpot;

public class handleASingleTrap extends Node{
	private LinkedList<TrapSpot> priority;
	private LinkedList<TrapSpot> secondary;
	
	public Core core = new Core();
	
	public handleASingleTrap(Script script, LinkedList<TrapSpot> fallenTraps, LinkedList<TrapSpot> otherTraps) {
		super(script);	
		priority = fallenTraps;
		secondary = otherTraps;
	}

	@Override
	public void execute() {
		if(priority.size() != 0){
			TrapSpot firstFallen = priority.removeFirst();
			firstFallen.grabTrap(this.script);
			firstFallen.setTrap(this.script);
			
		} else {
			TrapSpot front = secondary.removeFirst();
			if(!front.isUnknown(this.script)){
				front.grabTrap(this.script);
				front.setTrap(this.script);
			}
		}
		
	}



	@Override
	public boolean validate() {
		//if no traps need to be handled, do nothing
		return priority.size() != 0 || secondary.size() != 0;
	}
	
	
	
}
