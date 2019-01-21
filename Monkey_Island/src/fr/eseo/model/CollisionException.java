package fr.eseo.model;

// 
/**
 * The Class CollisionException.
 */
public class CollisionException extends Exception{
	
	/**
	 * attribut static.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * variable static pour la sortie de l'île.
	 */
	public static final int COLLISION_EXIT_ISLAND = 0;
	
	/**
	 * variable static pour la collision avec une case mer.
	 */

	public static final int COLLISION_SEA = 1;
	
	/**
	 * variable static pour la collision avec un autre pirate.
	 */
	public static final int COLLISION_PIRATE = 2;
	
	
	/**
	 * variable static pour la collision avec une case mer.
	 */
	public static final int COLLISION_MONKEY = 3;
	
	/**
	 * variable static pour la collision avec un rhum.
	 */
	public static final int COLLISION_RHUM = 4;
	
	/**
	 * variable static pour la collision avec un tr�sor.
	 */
	public static final int COLLISION_TREASURE = 5;

	
	/**
	 * attribut de cause de collision.
	 */
	private int cause;
	
	/**
	 * Instantiates a new collision exception.
	 *
	 * @param message de l'exception.
	 * @param cause de l'exception.
	 */
    public CollisionException(String message, int cause){
			super(message);
			this.cause = cause;
	}
    
    /**
     * Retourne la cause de l'exception.
     *
     * @return l'exception.
     */
	public int getExceptionCause(){
		return this.cause;
	}

}
