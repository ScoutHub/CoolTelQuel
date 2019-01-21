package fr.eseo.model;


// 
/**
 * The Class Case.
 */
public class Case {

	/** The coordinate X. */
	private int coordinateX;
	
	/** The coordinate Y. */
	private int coordinateY;
	
	/** The case type. */
	private CaseType caseType;

	/** The Constant DEFAULT_CASE_TYPE. */
	public static final CaseType DEFAULT_CASE_TYPE = CaseType.earth;
	
	/**
	 * Instantiates a new case.
	 */
	public Case(){
		this.coordinateX = 0;
		this.coordinateY = 0;
		this.caseType = DEFAULT_CASE_TYPE;
	}
	
	/**
	 * Instantiates a new case.
	 *
	 * @param coordinateX the coordinate X
	 * @param coordinateY the coordinate Y
	 * @param caseType the case type
	 */
	public Case(int coordinateX, int coordinateY, CaseType caseType){
		this.coordinateX = coordinateX;
		this.coordinateY = coordinateY;
		this.caseType = caseType;
	}
	
	/**
	 * Gets the coordinate X.
	 *
	 * @return the coordinate X
	 */
	public int getCoordinateX(){
		return coordinateX;
	}
	
	/**
	 * Sets the coordinate X.
	 *
	 * @param coordinateX the new coordinate X
	 */
	public void setCoordinateX(int coordinateX){
		this.coordinateX = coordinateX;
	}

	/**
	 * Gets the coordinate Y.
	 *
	 * @return the coordinate Y
	 */
	public int getCoordinateY(){
		return coordinateY;
	}
	
	/**
	 * Sets the coordinate Y.
	 *
	 * @param coordinateY the new coordinate Y
	 */
	public void setCoordinateY(int coordinateY){
		this.coordinateY = coordinateY;
	}
	
	/**
	 * Gets the case type.
	 *
	 * @return the case type
	 */
	public CaseType getCaseType(){
		return caseType;
	}
	
	/**
	 * Sets the case type.
	 *
	 * @param caseType the new case type
	 */
	public void setCaseType(CaseType caseType){
		this.caseType = caseType;
	}	
}
