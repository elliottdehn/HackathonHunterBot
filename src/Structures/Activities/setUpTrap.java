package Structures.Activities;

import org.osbot.rs07.script.Script;

import Structures.Node;
import Structures.TrapSpot;

public class setUpTrap extends Node{
	public TrapSpot trap;
	
	public setUpTrap(Script script, TrapSpot trapObject){
		super(script);
		trap = trapObject;
	}
	@Override
	public void execute() {
		trap.setTrap(this.script);		
	}

	@Override
	public boolean validate() {

		return script.inventory.contains("Box trap") && !script.myPlayer().isAnimating(); //TODO
	}

}
