package fr.eseo.command;

/**
 * The class Command Control of the design pattern Command.
 */
public class CommandControl {
	
	private Command command;
	
	/**
	 * Instantiates a new Command Control
	 */
	public CommandControl(){
		
	}
	
	/**
	 * Set the command.
	 * @param command the command
	 */
	public void setCommand(Command command){
    	this.command = command;
	}
	
	/** 
	 * To execute the action of the command asked.
	 */
	public void commandAction(){
		command.execute();
	}
}
