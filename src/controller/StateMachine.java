package controller;

public  class StateMachine {
	public static final int IDLE = 0, CREATE = 1, RESIZE = 2, MOVE = 3, DELETE = 4;
	
	private static int currentState = IDLE;
	
	public static int getState() {
		return currentState;
	}
	
	public static void  setState(int state) {
		
		currentState = state;
	}
}
