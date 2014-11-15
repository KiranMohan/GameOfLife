import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class GameOfLife {

	private final int boardSize; // just so that we can keep a check on the system resources

	public GameOfLife(int boardSize) {
		super();
		this.boardSize = boardSize;
	}

	/**
	 * This function generates the cells for next time tick
	 * It keeps a map of only live cells. Note the map syntax Map<row, Map<col, Cell>>
	 * <ol>
	 * <li> It marks the next state of live cells one by one. While doing this we also 
	 * keep a track of the neighboring dead cells.
	 * <li> It marks the next state the dead cells identified in previous step. If the 
	 * next state is alive, the dead cells are moved to live cells map. It then discard
	 * the dead cell map
	 * <li> It parses the live cells map again and remove all cell with next state marked
	 * as dead.
	 * </ol>
	 * @param liveCells
	 */
	public void processNextBoard(Map<Integer, Map<Integer, Cell>> liveCells) {

		Map<Integer, Map<Integer, Cell>> deadCells = new HashMap<Integer, Map<Integer, Cell>>();

		// mark next state of liveCells
		for (Map<Integer, Cell> map : liveCells.values()) {
			for (Cell c : map.values()) {
				markNextStateForLiveCell(c, liveCells, deadCells);
			}
		}

		// mark next state of deadCells
		for (Map<Integer, Cell> map : deadCells.values()) {
			for (Cell c : map.values()) {
				markNextStateForDeadCell(c, liveCells, deadCells);
			}
		}
		
		// all possible live cells in are added to liveCells map
		// we no longer need deadCells map
		deadCells = null;

		// set liveCells		
		Iterator<Map<Integer, Cell>> itr1 = liveCells.values().iterator();
		while (itr1.hasNext()) {
			// iterate the map and find each cell
			Map<Integer, Cell> map = itr1.next();
			Iterator<Entry<Integer, Cell>> itr2 = map.entrySet().iterator();
			while (itr2.hasNext()) {
				Cell c = itr2.next().getValue();
				if (!c.isNextState()) { // cell is gonna be dead
					itr2.remove();	    // remove it
				}
			}

			if (map.size() == 0) { // empty the map at this row if there are no live cells in the row
				itr1.remove();
			}
		}
	}

	private void markNextStateForLiveCell(Cell c, Map<Integer, Map<Integer, Cell>> liveCells,
	        Map<Integer, Map<Integer, Cell>> deadCells) {

		Integer x = c.getX();
		Integer y = c.getY();

		// find number of live neighboring cells
		int countLive = 0;
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				if (i == x && j == y)
					continue;

				if (liveCells.get(i) != null) {
					if (liveCells.get(i).get(j) != null) {
						countLive++;
					} else {
						// a dead cell in the neighborhood
						addDeadCell(i, j, deadCells);
					}
				} else {
					// a dead cell in the neighborhood
					addDeadCell(i, j, deadCells);
				}
			}
		}

		// mark
		if (countLive < 2 || countLive > 3) {
			c.setNextState(false);
		} else {
			c.setNextState(true);
		}

	}

	private void markNextStateForDeadCell(Cell c, Map<Integer, Map<Integer, Cell>> liveCells,
	        Map<Integer, Map<Integer, Cell>> deadCells) {
		Integer x = c.getX();
		Integer y = c.getY();

		// find count of neighboring live cells
		int countLive = 0;
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				
				if (i == x && j == y) //the current cell itself, skip it.
					continue;

				if (liveCells.get(i) != null && liveCells.get(i).get(j) != null) {
					countLive++;
				}
			}
		}

		// add the "going to be alive" dead cells to liveCells map
		if (countLive == 3) {
			c.setNextState(true);
			if (liveCells.get(c.getX()) == null) {
				liveCells.put(c.getX(), new HashMap<Integer, Cell>());
			}
			liveCells.get(c.getX()).put(c.getY(), c);
		}
	}

	private void addDeadCell(int x, int y, Map<Integer, Map<Integer, Cell>> deadCells) {
		if (x < 0 || x >= boardSize || y < 0 || y >= boardSize) {
			return; // out of board border, return
		}

		if (deadCells.get(x) == null) {
			deadCells.put(x, new HashMap<Integer, Cell>());
		}
		deadCells.get(x).put(y, new Cell(x, y, false));
	}

	public void show(Map<Integer, Map<Integer, Cell>> liveCells) {
		// showGraphical() requires the library
		// http://algs4.cs.princeton.edu/code/stdlib.jar
		showGraphical(liveCells);
		//		showText(liveCells);
	}

	private void showText(Map<Integer, Map<Integer, Cell>> liveCells) {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (liveCells.get(i) != null) {
					if (liveCells.get(i).get(j) != null) {
						System.out.print(1 + " ");
					} else {
						System.out.print(". ");
					}
				} else {
					System.out.print(". ");
				}

			}
			System.out.println();
		}
		System.out.println("\n\n");
	}

	private void showGraphical(Map<Integer, Map<Integer, Cell>> liveCells) {
		StdDraw.setXscale(0, boardSize);
		StdDraw.setYscale(0, boardSize);
		StdDraw.clear();
		
		// mark all cell positions
//		StdDraw.setPenRadius();
//		StdDraw.setPenColor(StdDraw.BLACK);
//		for (int i = 0; i < boardSize; i++) {
//			for (int j = 0; j < boardSize; j++) {
//				StdDraw.point(i, j);
//			}
//		}

		// show the live cells
		StdDraw.setPenRadius(0.01);
		StdDraw.setPenColor(StdDraw.RED);
		for (Map<Integer, Cell> map : liveCells.values()) {
			for (Cell c : map.values()) {
				StdDraw.point(c.getX(), c.getY());
			}
		}
		StdDraw.show(1000);
	}

	public static void main(String[] args) {

		final int BOARD_SIZE = 50; // square board 
		final int SEED_COUNT = 100; // number of elements initially alive
		final int INITIAL_LIVE_AREA = 50; // area were initial set of live cells reside
		Random random = new Random();

		// create a set of live cells at the center of the board
		Map<Integer, Map<Integer, Cell>> liveCells = new HashMap<Integer, Map<Integer, Cell>>();
		int numberOfCells = 0;
		while (numberOfCells < SEED_COUNT) {
			int x = random.nextInt(INITIAL_LIVE_AREA) + (BOARD_SIZE - INITIAL_LIVE_AREA) / 2;
			int y = random.nextInt(INITIAL_LIVE_AREA) + (BOARD_SIZE - INITIAL_LIVE_AREA) / 2;
			if (liveCells.get(x) == null) {
				liveCells.put(x, new HashMap<Integer, Cell>());
			}
			if (liveCells.get(x).put(y, new Cell(x, y, true)) == null){
				++numberOfCells;
			}
		}

		GameOfLife g = new GameOfLife(BOARD_SIZE);

		//show the seed
		g.show(liveCells);

		// lets play
		// for (int i = 0; i < 10; ++i) {
		while (true) {
			g.processNextBoard(liveCells);
			g.show(liveCells);
			if (liveCells.size() == 0) {
				System.out.println("Extinction!!!");
				break;
			}
		}

	}

}
