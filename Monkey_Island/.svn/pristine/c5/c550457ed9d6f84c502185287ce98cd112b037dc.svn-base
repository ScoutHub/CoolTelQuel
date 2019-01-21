package fr.eseo.model;


/**
 * The Class Item.
 */
public abstract class Item extends Entity{
	
	
	/** The visibility. */
	private boolean visibility;
	
	/**
	 * Instantiates a new object.
	 */
	public Item(){
		super();
		this.visibility = false;
	}
	
	/**
	 * Instantiates a new object.
	 *
	 * @param visibility the visibility
	 */
	public Item(boolean visibility){
		super();
		this.visibility = visibility;
	}
	
	/**
	 * Instantiates a new item.
	 *
	 * @param x the coordinate x of the item
	 * @param y the coordinate y of the item
	 * @param visibility the visibility
	 */
	public Item(int x, int y, boolean visibility){
		super(x, y);
		this.visibility = visibility;
	}
	
	
	/**
	 * Gets the visibility.
	 *
	 * @return the visibility
	 */
	public boolean getVisibility(){
		return this.visibility;
	}
	
	/**
	 * Sets the visibility.
	 *
	 * @param visibility the new visibility
	 */
	public void setVisibility(boolean visibility){
		this.visibility = visibility;
		this.setChanged();
		this.notifyObservers(this.visibility);
	}
	

}
