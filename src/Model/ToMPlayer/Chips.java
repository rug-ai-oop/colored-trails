package Model.ToMPlayer;

import Model.Color;
import Model.Patch;
import Model.Token;

import java.util.ArrayList;

public class Chips {

	public static int[] getChipset(ArrayList<Token> tokenList) {
		int[] chipset = new int[Setting.NUMBER_OF_COLORS];
		for (int i = 0; i < tokenList.size(); ++i) {
			chipset[Color.getColorPriority(tokenList.get(i).getColor()) - 1]++;
		}
		return chipset;
	}

	public static int getTokenColor(Token t) {
		return Color.getColorPriority(t.getColor()) - 1;
	}

	public static int getPatchColor(Patch p) {
		return Color.getColorPriority(p.getColor()) - 1;
	}

	/**
	 * Returns the sum of the positive elements
	 */
	public static int getPositiveAmount(int[] differences) {
		int acc = 0, i;
		for (i = 0; i < differences.length; ++i) {
			if (differences[i] > 0) {
				acc += differences[i];
			}
		}
		return acc;
	}

	/**
	 * Returns the sum of the negative elements
	 */
	public static int getNegativeAmount(int[] differences) {
		int acc = 0, i;
		for (i = 0; i < differences.length; ++i) {
			if (differences[i] < 0) {
				acc -= differences[i];
			}
		}
		return acc;
	}


	/**
	 * Returns the bin differences of int offers
	 * @param index1 first offer
	 * @param index2 second offer
	 * @param binMax maximum amount per bin
	 * @return differences per bin
	 */
	public static int[] getDifference(int index1, int index2, int[] binMax) {
		int[] bins1 = getBins(index1,binMax);
		int[] bins2 = getBins(index2,binMax);
		int i;
		for (i = 0; i < bins1.length; ++i) {
			bins1[i] -= bins2[i];
		}
		return bins1;
	}

	/**
	 * Gets the complementary index of the given offer. That is, input + output = binMax
	 * @param index offer
	 * @param binMax maximum amount per bin
	 * @return complementary offer
	 */
	public static int invert(int index, int[] binMax) {
		int[] bins = getBins(index,binMax);
		int i;
		for (i = 0; i < bins.length; ++i) {
			bins[i] = binMax[i] - bins[i];
		}
		return convert(bins,binMax);

	}

	/**
	 * Converts an offer specified per bin to an offer as an index
	 * @param bins offer as bins
	 * @param binMax maximum amount per bin
	 * @return offer as index
	 */
	public static int convert(int[] bins, int[] binMax) {
		int outCode = bins[bins.length-1];
		int i;
		for (i = bins.length-2; i >= 0; --i) {
			outCode = outCode*(binMax[i]+1) + bins[i];
		}
		return outCode;
	}

	/**
	 * Gets the total number of tokens in a given offer
	 * @param index offer
	 * @param binMax maximum amount per bin
	 * @return number of tokens across all bins
	 */
	public static int getNrTokens(int index, int[] binMax) {
		int i;
		int nrTokens = 0;
		for (i = 0; i < binMax.length; ++i) {
			nrTokens += index % (binMax[i]+1);
			index /= (binMax[i]+1);
		}
		return nrTokens;
	}

	/**
	 * Converts an offer as index to an offer as bins
	 * @param index offer as index
	 * @param binMax maximum amount per bin
	 * @return offer as bins
	 */
	public static int[] getBins(int index, int[] binMax) {
		int[] bins = new int[binMax.length];
		int i;
		for (i = 0; i < bins.length; ++i) {
			bins[i] = index % (binMax[i]+1);
			index /= (binMax[i]+1);
		}
		return bins;
	}
}
