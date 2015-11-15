package Structures;

import org.osbot.rs07.script.Script;

public abstract class Node {
	public Script script;
	
    public Node(Script script) {

        this.script = script;

    }
    
	abstract public void execute();
	abstract public boolean validate();
}
