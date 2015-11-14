
public class TrapSpot {
	
	public enum State {
		FAIL, SUCCESS, GROUND, SET, UNKNOWN, FLOWERED
	}
	
	public int xPos;
	public int yPos;
	
	private int itemID;
	private int failedID;
	private int groundID;
	private int successID;
	private int setID;
	
	public TrapSpot(int x, int y, int readyID, int trappedID, int failID, int droppedID, int invID){
		xPos = x;
		yPos = y;
		itemID = invID;
		successID = trappedID;
		failedID = failID;
		groundID = droppedID;
		setID = readyID;
	}
	
	public void setTrap(){
		//set the trap at position xPos, yPos
		//use the itemID
	}
	
	public void setTrapPosition(int x, int y){
		xPos = x;
		yPos = y;		
	}

	public void grabTrap(){
		//if the trap failed, use that object id
		//if the trap trapped, pick up that object id
		//if the drop dropped, pick up item id at location
	}
	
	public State checkTrap(){
		//check the position, use logic with returned id
		State trapState = State.SET;
		return trapState;
	}
	
}
