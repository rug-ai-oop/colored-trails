package Model.ToMPlayer;

public abstract class Player implements Cloneable {

	boolean confidenceLocked = false;


	// The set of chips is represented as an index, the score resulting from the set of chips
	// is represented as an entry in the utilityFunction array
	Setting setting;
	int chips;
	int[] utilityFunction;
	double[][] beliefsOfferType;
	int[][] countBeliefsOfferType;
	int[][] countTotalOfferType;
	double[] beliefOffer;
	double[][] beliefOfferSaved;
	int saveCount = 0;

	double learningSpeed = 0.1;


	/**
	 * Gives the agent's counter-offer for a specific offer
	 * @param offer the offer make by Player p from the perspective of this agent.
	 *              That is, if accepted, the agent gets offer
	 * @return the counter-offer to Player p from the perspective of Player p.
	 *              That is, if accepted, Player p gets the returned value.
	 */
	abstract public int makeOffer(int offer);
	abstract public int selectOffer(int offer);

	/**
	 * Stores the current beliefs for later retrieval. Used for prediction using a "fictitious play"-like structure.
	 */
	public void saveBeliefs() {
		beliefOfferSaved[saveCount] = beliefOffer.clone();
		saveCount++;
	}

	/**
	 * Restores previously stored beliefs
	 */
	public void restoreBeliefs() {
		saveCount--;
		beliefOffer = beliefOfferSaved[saveCount].clone();
	}

	public Player(Setting setting, int nrColors, int nrChipsPerPlayer) {
		this.setting = setting;
		setupBeliefs(nrColors, nrChipsPerPlayer);
	}

	public void makeBeliefs54() {
		countBeliefsOfferType = new int[][]{{5, 5, 5, 5, 5, 5, 5, 5, 5},{84, 542, 373, 5, 5, 5, 5, 5, 5},{65, 908, 552, 149, 5, 5, 5, 5, 5},
				{59, 57, 336, 86, 19, 5, 5, 5, 5},{77, 53, 46, 35, 8, 5, 5, 5, 5},{5, 5, 5, 5, 5, 5, 5, 5, 5},
				{5, 5, 5, 5, 5, 5, 5, 5, 5},{5, 5, 5, 5, 5, 5, 5, 5, 5},{5, 5, 5, 5, 5, 5, 5, 5, 5}};
		countTotalOfferType   = new int[][]{{286, 53, 59, 69, 90, 5, 5, 5, 5},{84, 1018, 1035, 295, 66, 5, 5, 5, 5},{65, 1406, 1007, 410, 126, 5, 5, 5, 5},
				{59, 57, 517, 161, 55, 5, 5, 5, 5},{77, 53, 46, 48, 12, 5, 5, 5, 5},{5, 5, 5, 5, 5, 5, 5, 5, 5},
				{5, 5, 5, 5, 5, 5, 5, 5, 5},{5, 5, 5, 5, 5, 5, 5, 5, 5},{5, 5, 5, 5, 5, 5, 5, 5, 5}};
	}
	/**
	 * Sets up belief structure for types of beliefs
	 * @param nrColors
	 * @param nrChipsPerPlayer
	 */
	public void setupBeliefs(int nrColors, int nrChipsPerPlayer) {
		int i,j;
		countBeliefsOfferType = new int[nrChipsPerPlayer*2+1][nrChipsPerPlayer*2+1];
		countTotalOfferType = new int[nrChipsPerPlayer*2+1][nrChipsPerPlayer*2+1];
		// Initialize beliefs to 5 positive encounters to make sure the agent's experience doesn't crash immediately to disbelief
		for (i = 0; i < countBeliefsOfferType.length; ++i) {
			for (j = 0; j < countBeliefsOfferType[0].length; ++j) {
				countBeliefsOfferType[i][j] = 5;
				countTotalOfferType[i][j] = 5;
			}
		}
//		init();
	}


	/**
	 * Initializes beliefs for a new negotiations. Note that only beliefs about colors are reset,
	 * while beliefs about offer types are kept.
	 */
	public void init(int chipsSelf, int chipsOther, int[] utilityFunction) {
		this.chips = chipsSelf;
		this.utilityFunction = utilityFunction.clone();
		int i;
		beliefOffer = new double[utilityFunction.length];
		beliefOfferSaved = new double[5][beliefOffer.length];
		for (i = 0; i < utilityFunction.length; ++i) {
			beliefOffer[i] = getBeliefOfferType(i);
		}
	}

	private double getBeliefOfferType(int offer) {
		int[] diff = Chips.getDifference(chips, offer, setting.binMax);
		int pos = Chips.getPositiveAmount(diff);
		int neg = Chips.getNegativeAmount(diff);
		double retVal = 0.0;
		retVal = ((double)countBeliefsOfferType[pos][neg])/countTotalOfferType[pos][neg];
		return retVal;
	}

	/**
	 * Returns the currently owned set of chips as an index.
	 * @return the currently owned set of chips as an index
	 */
	public int getChips() {
		return chips;
	}

	/**
	 * Sets the chipset to a new value, for example as a result of negotiation
	 * @param newValue new index for the set of chips
	 */
	public void setChips(int newValue) {
		chips = newValue;
	}

	/**
	 * Returns the subjective probability that the partner would accept a given offer
	 * @param offer offer to make to the partner
	 * @return subjective probability that the offer is accepted
	 */
	protected double getBelief(int offer) {
		return beliefOffer[offer];
	}

	protected void updateBeliefsOfferRejected(int offer) {
		decreaseOfferTypeBelief(offer);
		decreaseColorBelief(offer);
	}

	public void updateBeliefsOfferAccepted(int offer) {
		increaseOfferTypeBelief(offer);
//		decreaseColorBelief(offer);
	}

	/**
	 * Updates beliefs based on receiving an offer
	 * @param offer offer that is received
	 */
	protected void updateBeliefsOfferReceived(int offer) {
		increaseOfferTypeBelief(offer);
		increaseColorBelief(offer);
	}

	private void increaseColorBelief(int newOwnChips) {
		int i,j;
		int[] newOwnBins = Chips.getBins(newOwnChips, setting.binMax);
		int[] curOffer;
		for (i = 0; i < beliefOffer.length; ++i) {
			curOffer = Chips.getBins(i, setting.binMax);
			// curOffer represents what this agents wants to *keep*, not the part for the other Player
			for (j = 0; j < curOffer.length; ++j) {
				if (curOffer[j] > newOwnBins[j]) {
					// curOffer demands more chips of color j than the
					// offer newOwnChips of the trading partner.
					// It's less likely to be accepted
					beliefOffer[i] = (1-learningSpeed)*beliefOffer[i];
				}
			}
		}
	}
	private void decreaseColorBelief(int newOwnChips) {
		int i,j;
		int[] newOwnBins = Chips.getBins(newOwnChips, setting.binMax);
		int[] curOffer;
		for (i = 0; i < beliefOffer.length; ++i) {
			curOffer = Chips.getBins(i, setting.binMax);
			// curOffer represents what this agents wants to *keep*, not the part for the other Player
			for (j = 0; j < curOffer.length; ++j) {
				if (curOffer[j] >= newOwnBins[j]) {
					// curOffer demands at least as much chips of color j
					// as the offer newOwnChips of the trading partner.
					// It's less to be rejected as well
					beliefOffer[i] = (1-learningSpeed)*beliefOffer[i];
				}
			}
		}
	}

	protected void revokeRejection(int newOwnChips) {
		// This process removes a previously observed/assumed rejection
		int pos, neg;
		int[] diff = Chips.getDifference(chips, newOwnChips, setting.binMax);
		pos = Chips.getPositiveAmount(diff);
		neg = Chips.getNegativeAmount(diff);
		countTotalOfferType[pos][neg]--;
	}

	private void increaseOfferTypeBelief(int newOwnChips) {
		int pos, neg;
		int[] diff = Chips.getDifference(chips, newOwnChips, setting.binMax);
		pos = Chips.getPositiveAmount(diff);
		neg = Chips.getNegativeAmount(diff);
		countBeliefsOfferType[pos][neg]++;
		countTotalOfferType[pos][neg]++;
	}

	private void decreaseOfferTypeBelief(int newOwnChips) {
		int pos, neg;
		int[] diff = Chips.getDifference(chips, newOwnChips, setting.binMax);
		pos = Chips.getPositiveAmount(diff);
		neg = Chips.getNegativeAmount(diff);
		countTotalOfferType[pos][neg]++;
	}

	/**
	 * Returns the expected value of an offer (from the perspective of this player)
	 * @param offer the chips that this player wants to *keep*
	 */
	protected double getExpectedValue(int offer) {
		return getBelief(offer)*(utilityFunction[offer]-utilityFunction[chips]+1)-1;
	}

	protected double getValue(int offerToSelf) {
		return getExpectedValue(offerToSelf);
	}

	public String toString() {
		int i;
		int[] bins = Chips.getBins(chips, setting.binMax);
		String retVal =  "";
		for (i = 0; i < bins.length; ++i) {
			retVal += bins[i]+"\t";
		}
		return retVal + utilityFunction[chips];

	}

	public Player clone() throws CloneNotSupportedException {
		Player clone = (Player)super.clone();
		clone.utilityFunction = utilityFunction;
		if (beliefsOfferType != null) clone.beliefsOfferType = beliefsOfferType.clone();
		if (countBeliefsOfferType != null) clone.countBeliefsOfferType = countBeliefsOfferType.clone();
		if (countTotalOfferType != null) clone.countTotalOfferType = countTotalOfferType.clone();
		if (beliefOffer != null) clone.beliefOffer = beliefOffer.clone();
		return clone;
	}

	public void setLearningSpeed(double newLearningSpeed) {
		learningSpeed = newLearningSpeed;
	}

	public double getLearningSpeed() {
		return learningSpeed;
	}

	public void processOfferAccepted(int offerMade) {
		revokeRejection(offerMade);
		updateBeliefsOfferAccepted(offerMade);
	}

	protected void receiveOffer(int offer) {
		updateBeliefsOfferReceived(offer);
	}

	protected void sendOffer(int offer) {
		updateBeliefsOfferRejected(offer);
	}

	abstract public void setOrder(int newOrder);

	abstract protected void informLocation(int locationOther, int locationSelf);

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}


	public int[] getUtilityFunction() {
		return utilityFunction;
	}

	public void setUtilityFunction(int[] utilityFunction) {
		this.utilityFunction = utilityFunction;
	}
}
