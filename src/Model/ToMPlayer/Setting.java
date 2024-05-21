package Model.ToMPlayer;

import Model.Color;
import Model.Grid;
import Model.Patch;
import Model.Token;

import java.io.Serializable;
import java.util.ArrayList;


public class Setting implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int BOARD_SIZE = 5;
	public static final int NUMBER_OF_COLORS = 5;
	public static final int TOKENS_PER_PLAYER = 5;
	public static final int NUMBER_OF_TYPES = 12;

	int[] binMax;
	// Total numbers of chips for each color
	int[][] utilityFunctions;
	// Utility functions (utility as a function of offer) for each possible goal location
	int[] chipSets = new int[2];
	// Initial set of chips for both players
	int[][] board;
	// Colors of the tiles on the board. Not needed for calculation, but may be useful for visualization.
	int[] flipArray;
	// Flips the offer to the perspective of the other player
	int[] utilityMaxima;
	// Maximum possible utility for each possible goal location
	public int[] locations = new int[2];

	static final int SCORE_GOAL = 500;
	static final int SCORE_STEP = 100;
	static final int SCORE_SURPLUS = 50;

	/**
	 * Returns the given offer from the perspective of the other agent
	 * @param offer offer from the perspective of agent i
	 * @return offer from the perspective of agent j
	 */
	public int flipOffer(int offer) {
		if (offer < 0) offer = 0;
		return flipArray[offer];
	}

	public int[][] getBoard() {
		return board;
	}

	/**
	 * Generates a random square board setting according to the specified parameters
	 * @param boardSize size of the board in number of tiles
	 * @param tokenDiversity number of different colors of tiles and chips
	 * @param tokensPerPlayer number of tokens per player
	 * @return initial set of chips for agent 0
	 */
	public int generateSetting(int boardSize, int tokenDiversity, int tokensPerPlayer) {
		board = new int[boardSize][boardSize];
		int[] ci, cj;
		ci = new int[tokenDiversity];
		cj = new int[tokenDiversity];
		int i;
		initBoard(tokenDiversity);

		for (i = 0; i < tokensPerPlayer; ++i) {
			ci[(int)(Math.random()*tokenDiversity)]++;
			cj[(int)(Math.random()*tokenDiversity)]++;
		}
		calculateSetting(ci, cj);
		return chipSets[0];
	}

	public int loadGrid(Grid grid) {
		board = new int[BOARD_SIZE][BOARD_SIZE];
		ArrayList<Patch> patches = grid.getPatches();
		for (int i = 0; i < patches.size(); ++i) {
			board[i/5][i%5] = Chips.getPatchColor(patches.get(i));
		}
		int[] cj = Chips.getChipset(grid.getAllTokensInPlay());
		int[] ci = Chips.getChipset(grid.getTokens(grid.getCurrentPlayer()));
		for (int i = 0; i < ci.length; ++i) {
			cj[i] -= ci[i];
		}
		calculateSetting(ci, cj);
		return chipSets[0];
	}

	protected void calculateSetting(int[] ci, int[] cj) {
		int[][][] locationScoreMatrix;
		int i,j,k,pos;
		binMax = new int[ci.length];
		k = 1;
		for (i = 0; i < ci.length; ++i) {
			k *= (ci[i]+cj[i]+1);
			binMax[i] = ci[i]+cj[i];
		}
		chipSets[0] = Chips.convert(ci, binMax);
		chipSets[1] = Chips.convert(cj, binMax);
		int boardSize = board.length;
		locationScoreMatrix = new int[boardSize][boardSize][k];
		calculateLocationScoreMatrix(locationScoreMatrix);
		pos = 0;
		utilityFunctions = new int[(boardSize-1)*(boardSize+1)/2][k];
		utilityMaxima = new int[utilityFunctions.length];
		for (i = 0; i < boardSize; ++i) {
			for (j = 0; j < boardSize; ++j) {
				if (Math.abs(2*i-boardSize+1)+Math.abs(2*j-boardSize+1) >= boardSize) {
					utilityMaxima[pos] = 0;
					getUtilityFunction(utilityFunctions[pos], i, j);
					for (k = 0; k < utilityFunctions[pos].length; ++k) {
//						utilityFunctions[pos][k] = locationScoreMatrix[i][j][k];
						if (utilityFunctions[pos][k] > utilityMaxima[pos]) {
							utilityMaxima[pos] = utilityFunctions[pos][k];
						}
					}
					pos++;
				}
			}
		}
		flipArray = new int[utilityFunctions[0].length];
		for (k = 0; k < flipArray.length; ++k) {
			flipArray[k] = Chips.invert(k, binMax);
		}
	}

	/**
	 * Randomly colors every tile on the board
	 * @param tokenDiversity number of different colors
	 */
	private void initBoard(int tokenDiversity) {
		int i,j;
		for (i = 0; i < board.length; ++i) {
			for (j = 0; j < board[i].length; ++j) {
				board[i][j] = (int)(Math.random()*tokenDiversity);
			}
		}
	}

	private void calculateLocationScoreMatrix(int[][][] locationScoreMatrix) {
		int i,j,k,n;
		int midX = locationScoreMatrix.length/2;
		int midY = locationScoreMatrix[0].length/2;
		// Initialize
		for (k = 0; k < locationScoreMatrix[0][0].length; ++k) {
			n = Chips.getNrTokens(k,binMax);
			for (i = 0; i < locationScoreMatrix.length; ++i) {
				for (j = 0; j < locationScoreMatrix[0].length; ++j) {
					locationScoreMatrix[i][j][k] = n*SCORE_SURPLUS - SCORE_STEP*(Math.abs(i-midX) + Math.abs(j-midY));
					if (i == midX && j == midY) {
						locationScoreMatrix[i][j][k] += SCORE_GOAL;
					}
				}
			}
		}
		// Spread out possible paths from the target location
		for (i = 0; i <= midX; ++i) {
			checkLocation(locationScoreMatrix,midX-i,midY);
			checkLocation(locationScoreMatrix,midX+i,midY);
			for (j = 1; j <= midY; ++j) {
				checkLocation(locationScoreMatrix,midX-i,midY-j);
				checkLocation(locationScoreMatrix,midX-i,midY+j);
				checkLocation(locationScoreMatrix,midX+i,midY-j);
				checkLocation(locationScoreMatrix,midX+i,midY+j);
			}
		}
	}

	private void checkLocation(int[][][] locationScoreMatrix, int x, int y) {
		int k,k2;
		int[] bins;
		for (k = 0; k < locationScoreMatrix[0][0].length; ++k) {
			// For each offer
			bins = Chips.getBins(k,binMax);
			if (bins[board[x][y]] < binMax[board[x][y]]) {
				// If there are chips of the color of the current square not in this calculation
				bins[board[x][y]]++;
				k2 = Chips.convert(bins,binMax);
				if (x > 0 && locationScoreMatrix[x-1][y][k2] < locationScoreMatrix[x][y][k]) {
					// The score can be improved by taking an alternate path
					locationScoreMatrix[x-1][y][k2] = locationScoreMatrix[x][y][k];
				}
				if (y > 0 && locationScoreMatrix[x][y-1][k2] < locationScoreMatrix[x][y][k]) {
					locationScoreMatrix[x][y-1][k2] = locationScoreMatrix[x][y][k];
				}
				if (x < locationScoreMatrix.length-1 && locationScoreMatrix[x+1][y][k2] < locationScoreMatrix[x][y][k]) {
					locationScoreMatrix[x+1][y][k2] = locationScoreMatrix[x][y][k];
				}
				if (y < locationScoreMatrix[0].length-1 && locationScoreMatrix[x][y+1][k2] < locationScoreMatrix[x][y][k]) {
					locationScoreMatrix[x][y+1][k2] = locationScoreMatrix[x][y][k];
				}
			}
		}
	}

	public String toString() {
		String outVal = "";
		final int[] convertor = new int[]{0,1,3,4,5,9,15,19,20,21,23,24};
		int i,j;
		for (i = 0; i < board.length; ++i) {
			outVal += board[i][0];
			for (j = 1; j < board.length; ++j) {
				if (i==2 && j==2) {
					outVal += "\t["+board[i][j]+"]";
				} else {
					outVal += "\t"+board[i][j];
				}
				if (i*5+j==convertor[locations[0]]) {
					outVal += "*";
				}
				if (i*5+j==convertor[locations[1]]) {
					outVal += "#";
				}
			}
			outVal += "\n";
		}
		int [] bins = Chips.getBins(chipSets[0], binMax);
		outVal += "P1 ("+locations[0]+"):";
		for (i = 0; i < bins.length; ++i) {
			outVal += "\t"+bins[i];
		}
		bins = Chips.getBins(chipSets[1], binMax);
		outVal += "\nP2 ("+locations[1]+"):";
		for (i = 0; i < bins.length; ++i) {
			outVal += "\t"+bins[i];
		}
		System.out.println(utilityFunctions.length+" "+utilityFunctions[0].length);
		return outVal;
	}

	/**
	 * Returns the number of possible goal locations
	 * @return number of possible goal locations
	 */
	public int getNumberOfTypes() {
		return utilityFunctions.length;
	}

	/**
	 * Returns the utility table (offer -> utility) for goal location i
	 * @param i goal location
	 * @return utility table (offer -> utility) for goal location i
	 */
	public int[] getUtilityFunction(int i) {
		return utilityFunctions[i];
	}

	public void setUtilityFunctions(int[][] utilityFunctions) {
		this.utilityFunctions = utilityFunctions;
	}

	/**
	 * Returns the initial set of chips for agent i
	 * @param i agent index
	 * @return initial set of chips for agent i
	 */
	public int getChipSet(int i) {
		return chipSets[i];
	}

	public void setChipSets(int[] chipSets) {
		this.chipSets = chipSets;
	}

	public int[] getBinMax() {
		return binMax;
	}

	public void setBinMax(int[] binMax) {
		this.binMax = binMax;
	}

	/**
	 * Calculates the utility function for the given goal location
	 * @param utilityFunction array to place the utility function (output)
	 * @param x row coordinate
	 * @param y column coordinate
	 */
	public void getUtilityFunction(int[] utilityFunction, int x, int y) {
		int i,j,k,n;
		boolean doContinue = true;
		int[][][] scoreMatrix = new int[board.length][board[0].length][utilityFunction.length];
		for (k = 0; k < utilityFunction.length; ++k) {
			n = Chips.getNrTokens(k, binMax);
			for (i = 0; i < scoreMatrix.length; ++i) {
				for (j = 0; j < scoreMatrix[0].length; ++j) {
					scoreMatrix[i][j][k] = n*SCORE_SURPLUS - SCORE_STEP*(Math.abs(x-i) + Math.abs(y-j));
					if (i == x && j == y) {
						scoreMatrix[i][j][k] += SCORE_GOAL;
					}
				}
			}
		}
		while (doContinue) {
			doContinue = false;
			for (i = 0; i < scoreMatrix.length; ++i) {
				for (j = 0; j < scoreMatrix[0].length; ++j) {
					doContinue = doContinue || calculateLocation(scoreMatrix, i, j);
				}
			}
		}
		for (k = 0; k < utilityFunction.length; ++k) {
			utilityFunction[k] = scoreMatrix[board.length/2][board[0].length/2][k];
		}
	}

	private boolean calculateLocation(int[][][] scoreMatrix, int x, int y) {
		int k,k2;
		int[] bins;
		boolean hasChanged = false;
		for (k = 0; k < scoreMatrix[0][0].length; ++k) {
			// For each offer
			bins = Chips.getBins(k,binMax);
			if (bins[board[x][y]] < binMax[board[x][y]]) {
				// If it is possible to have used a chip to get to this location...
				bins[board[x][y]]++;
				k2 = Chips.convert(bins,binMax);
				if (x > 0 && scoreMatrix[x-1][y][k2] < scoreMatrix[x][y][k]) {
					// If the current score at (x-1,y) can be improved by handing over a chip to reach (x,y)...
//					System.out.println(x+" "+y+" "+scoreMatrix[x-1][y][k2]+"="+scoreMatrix[x][y][k]);
					scoreMatrix[x-1][y][k2] = scoreMatrix[x][y][k];
					hasChanged = true;
				}
				if (y > 0 && scoreMatrix[x][y-1][k2] < scoreMatrix[x][y][k]) {
					scoreMatrix[x][y-1][k2] = scoreMatrix[x][y][k];
					hasChanged = true;
				}
				if (x < scoreMatrix.length-1 && scoreMatrix[x+1][y][k2] < scoreMatrix[x][y][k]) {
					scoreMatrix[x+1][y][k2] = scoreMatrix[x][y][k];
					hasChanged = true;
				}
				if (y < scoreMatrix[0].length-1 && scoreMatrix[x][y+1][k2] < scoreMatrix[x][y][k]) {
					scoreMatrix[x][y+1][k2] = scoreMatrix[x][y][k];
					hasChanged = true;
				}
			}
		}
		return hasChanged;
	}


}
