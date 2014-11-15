
public class Cell {
	private final Integer x;
	private final Integer y;	
	private boolean nextState;

	public Cell(Integer x, Integer y, boolean alive) {
	    super();
	    this.x = x;
	    this.y = y;
    }

	/**
	 * @return the nextState
	 */
	public boolean isNextState() {
		return nextState;
	}

	/**
	 * @param nextState the nextState to set
	 */
	public void setNextState(boolean nextState) {
		this.nextState = nextState;
	}

	/**
	 * @return the x
	 */
	public Integer getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public Integer getY() {
		return y;
	}
	
}
