package Model.ToMPlayer;

import java.util.ArrayList;



public class PlayerToM extends Player {
	private static final long serialVersionUID = 1L;
	static int MODE_ONE_LOCATION = 0;
	static int MODE_ALL_LOCATION = 1;
	// All locations is better, but slower

	double tmpSelectOfferValue;
	double[] locationBeliefs;
	double[][] savedLocationBeliefs;
	PlayerToM partnerModel;
	PlayerToM selfModel;
	double confidence;
	public double dummyConfidence;
	int order;
	int currentLocation;

	boolean simulateBeliefChange = true;
	int mode = MODE_ALL_LOCATION;
	boolean startHigh = true;



	public PlayerToM(int order, Setting setting, int nrColors, int nrChipsPerPlayer) {
		super(setting, nrColors, nrChipsPerPlayer);
		this.order = order;
		locationBeliefs = new double[Setting.NUMBER_OF_TYPES];
		savedLocationBeliefs = new double[5][Setting.NUMBER_OF_TYPES];
		if (order > 0) {
			selfModel = new PlayerToM(order-1, setting, nrColors, nrChipsPerPlayer);
			partnerModel = new PlayerToM(order-1, setting, nrColors, nrChipsPerPlayer);
			partnerModel.confidenceLocked = true;
		} else {
			selfModel = null;
			partnerModel = null;
		}
	}

	public void setSetting(Setting newSetting) {
		this.setting = newSetting;
		if (order > 0) {
			selfModel.setSetting(newSetting);
			partnerModel.setSetting(newSetting);
		}
	}

	/**
	 * Initializes agent and partner models
	 */
	public void init(int chipsSelf, int chipsOther, int[] utilityFunction) {
		int i;
		super.init(chipsSelf, chipsOther, utilityFunction);
		if (order > 0) {
			selfModel.init(chipsSelf, chipsOther, utilityFunction);
			for (i = 0; i < locationBeliefs.length; ++i) {
				locationBeliefs[i] = 1.0/locationBeliefs.length;
			}
			partnerModel.init(chipsOther, chipsSelf, utilityFunction);
			currentLocation = (int)(Math.random() * Setting.NUMBER_OF_TYPES);
			confidence = (confidenceLocked || startHigh? 1.0 : 0.0);
		}
	}

	/**
	 * Get agent's belief that partner has a certain location. If multiple agent models are held,
	 * belief is scaled according to theory of mind confidences.
	 * @param location location for which to give belief
	 */
	protected double getLocationBelief(int location) {
		if (confidenceLocked) {
			return locationBeliefs[location];
		}
		if (order > 0) {
			return confidence*locationBeliefs[location] + (1-confidence)*selfModel.getLocationBelief(location);
		}
		return 1.0/Setting.NUMBER_OF_TYPES; // ToM0: no idea about location
	}

	/**
	 * External source informs the agent on the locations of BOTH players
	 */
	protected void informLocation(int locationOther, int locationSelf) {
		int i;
		if (order > 0) {
			for (i = 0; i < locationBeliefs.length; ++i) {
				locationBeliefs[i] = 0;
			}
			locationBeliefs[locationOther] = 1.0;
			selfModel.informLocation(locationOther, locationSelf);
			partnerModel.informLocation(locationSelf, locationOther);
		}
	}

	/**
	 * Sets location to most likely location
	 */
	protected void setLikelyLocation() {
		int i;
		ArrayList<Integer> list = new ArrayList<Integer>();
		double curValue, tmpValue;
		curValue = getLocationBelief(0);
		list.add(0);
		for (i = 1; i < Setting.NUMBER_OF_TYPES; ++i) {
			tmpValue = getLocationBelief(i);
			if (tmpValue > curValue) {
				list.clear();
				list.add(i);
				curValue = tmpValue;
			} else if (tmpValue == curValue) {
				list.add(i);
			}
		}
		currentLocation = list.get((int) (Math.random() * list.size()));
	}

	/**
	 * Get value of making an offer, given the current location of the partner
	 * @param offerToSelf
	 * @param offerToOther
	 * @return
	 */
	protected double getLocationValue(int offerToSelf, int offerToOther) {
		int response = partnerModel.selectOffer(offerToOther);
		double curValue = 0.0;
		if (response == offerToOther){
			// Partner accepts
			curValue += locationBeliefs[currentLocation]*(utilityFunction[offerToSelf] - utilityFunction[chips] - 1);
		} else if (response != partnerModel.chips) {
			// Partner rejects, but makes a counteroffer
			response = setting.flipOffer(response);
			curValue += locationBeliefs[currentLocation]*(Math.max(0, utilityFunction[response] - utilityFunction[chips] - 1) - 1);
		} // If neither case is satisfied, partner terminates negotiations, resulting in value 0
		return curValue;
	}

	/**
	 * Push back the beliefs for (nested) hypothetical beliefs
	 */
	public void saveBeliefs() {
		super.saveBeliefs();
		if (order > 0) {
			savedLocationBeliefs[saveCount-1] = locationBeliefs.clone();
			partnerModel.saveBeliefs();
			selfModel.saveBeliefs();
		}
	}

	/**
	 * Restore previous pushed back beliefs
	 */
	public void restoreBeliefs() {
		super.restoreBeliefs();
		if (order > 0) {
			locationBeliefs = savedLocationBeliefs[saveCount].clone();
			partnerModel.restoreBeliefs();
			selfModel.restoreBeliefs();
		}
	}

	/**
	 *
	 * @param offerToSelf
	 * @return
	 */
	protected double getValue(int offerToSelf) {
		int l, offerToOther;
		double curValue = 0.0;
		if (order == 0) {
			return getExpectedValue(offerToSelf);
		}
		if (confidence > 0 || confidenceLocked) {
			offerToOther = setting.flipOffer(offerToSelf);
			if (simulateBeliefChange) {
				// To reduce calculation, discounted ToM0 value is calculated independent of location
				partnerModel.saveBeliefs();
				partnerModel.receiveOffer(offerToOther);
			}
			if (mode == MODE_ONE_LOCATION) {
				// The agent acts as if he fully believes that his partner is at a specific location
				curValue += getLocationValue(offerToSelf, offerToOther);
			} else {
				// The agent takes into account that his partner could be at different locations
				for (l = 0; l < Setting.NUMBER_OF_TYPES; ++l) {
					partnerModel.utilityFunction = setting.getUtilityFunction(l);
					currentLocation = l;
					if (locationBeliefs[l] > 0.0) {
						curValue += locationBeliefs[l]*getLocationValue(offerToSelf, offerToOther);
					}
				}
			}
			if (simulateBeliefChange) {
				partnerModel.restoreBeliefs();
			}
		}
		if (confidence >= 1 || confidenceLocked) {
			return curValue;
		}
		return curValue*confidence + (1 - confidence)*selfModel.getValue(offerToSelf);
	}

	public void setLearningSpeed(double newLearningSpeed) {
		super.setLearningSpeed(newLearningSpeed);
		if (order > 0) {
			selfModel.setLearningSpeed(newLearningSpeed);
			partnerModel.setLearningSpeed(newLearningSpeed);
		}
	}

	public int makeOffer(int offer) 	{
		int curOffer;
		if (offer < 0) {
			// The game has not yet started, no offer to receive
			curOffer = selectOffer(chips);
		} else {
			receiveOffer(offer);
			curOffer = selectOffer(offer);
		}
		sendOffer(curOffer);
		dummyConfidence += confidence;
		return setting.flipOffer(curOffer);
	}

	public int selectOffer(int offer) {
		int i, bestOffer;
		double curValue;
		tmpSelectOfferValue = 0.0;
		bestOffer = chips;
		for (i = 0; i < utilityFunction.length; ++i) {
			curValue = getValue(i);
			if (curValue > tmpSelectOfferValue) {
				tmpSelectOfferValue = curValue;
				bestOffer = i;
			}
		}
		if (utilityFunction[offer] - utilityFunction[chips] >= tmpSelectOfferValue || utilityFunction[offer] >= utilityFunction[bestOffer]) {
			bestOffer = offer;
			tmpSelectOfferValue = utilityFunction[offer] - utilityFunction[chips];
		}
		if (tmpSelectOfferValue < 0 || utilityFunction[bestOffer] < utilityFunction[chips]) {
			bestOffer = chips;
			tmpSelectOfferValue = 0;
		}
		return bestOffer;
	}

	protected void receiveOffer(int offer) {
		int inverseOffer;
		super.receiveOffer(offer);
		if (order > 0) {
			updateLocationBeliefs(offer);
			inverseOffer = setting.flipOffer(offer);
			// Update partner model for the fact that they sent the offer that was just received
			partnerModel.sendOffer(inverseOffer);
			selfModel.receiveOffer(offer);
		}
	}

	protected void sendOffer(int offer) {
		int inverseOffer;
		super.sendOffer(offer);
		if (order > 0) {
			inverseOffer = setting.flipOffer(offer);
			// Update partner model for receiving the offer that was made
			partnerModel.receiveOffer(inverseOffer);
			selfModel.sendOffer(offer);
		}
	}

	// Terribly inefficient
	protected void updateLocationBeliefs(int offerReceived) {
		int l, partnerAlternative,offerPartnerChips;
		double sumAll, maxExpVal, curExpVal, accuracyRating;

		offerPartnerChips = setting.flipOffer(offerReceived);
		sumAll = 0.0;
		accuracyRating = 0.0;
		for (l = 0; l < locationBeliefs.length; ++l) {
			partnerModel.utilityFunction = setting.getUtilityFunction(l);
			// Since partnerModels are confidenceLocked, this need not be applied to lower ToM levels
			partnerAlternative = partnerModel.selectOffer(0);
			// Agent's guess for partner's best option given location l
			curExpVal = partnerModel.getValue(offerPartnerChips);
			// Agent's guess for partner's value of offerReceived

			if (partnerModel.utilityFunction[offerPartnerChips] > partnerModel.utilityFunction[partnerModel.chips] || curExpVal < 0) {
				// Given location l, offerReceived gives the partner a higher score than the initial situation and withdrawing
				maxExpVal = partnerModel.getValue(partnerAlternative);
				if (maxExpVal > -1) {
					locationBeliefs[l] *= Math.max(0.0,Math.min(1.0,(curExpVal+1)/(maxExpVal+1)));
					accuracyRating += locationBeliefs[l];
//						orderLocationBeliefs[i][l] = (1-getLearningSpeed())*orderLocationBeliefs[i][l] + getLearningSpeed()*curOrderPredictionValue;
					if (maxExpVal+1==0) {
						System.out.print(offerPartnerChips+" "+curExpVal+" "+ partnerAlternative+" "+partnerModel.chips+"  "+maxExpVal+"  ");
					}
				} else {
					// The model is incorrect. The maximum expected value is -1, which means that there is no offer that is expected
					// to be accepted. However, this also makes the current offer have value -1, 100% of the maximum value.
				}
			} else {
				// Making the offer "offerReceived" is not rational, the score would decrease. This is considered to be impossible.
				locationBeliefs[l] = 0;
			}
			sumAll += locationBeliefs[l];
		}
		if (sumAll > 0) {
			sumAll = 1.0/sumAll;
			for (l = 0; l < locationBeliefs.length; ++l) {
				locationBeliefs[l] *= sumAll;
			}
		}
		if (!confidenceLocked) {
			confidence = Math.min(1.0,Math.max(0.0,(1-getLearningSpeed())*confidence + getLearningSpeed()*accuracyRating));
			if (order > 1) {
				selfModel.updateLocationBeliefs(offerReceived);
			}
		}
		if (mode == MODE_ONE_LOCATION) {
			setLikelyLocation();
		}
	}

	public void setOrder(int newOrder) {
		if (order > 0) {
			confidence = (newOrder == order?1.0:0.0);
			selfModel.setOrder(newOrder);
		}
	}

	public void restrictOrder(int maxOrder) {
		if (order > maxOrder) {
			confidence = 0.0;
			selfModel.setOrder(maxOrder);

		}
	}

	public String getConfidence() {
		if (order > 0) {
			return confidence + " " + selfModel.getConfidence();
		}
		return "";
	}

	public String getAccuracies(int offerReceived) {
		if (order > 0) {
			int l, partnerAlternative,offerPartnerChips;
			double maxExpVal, curExpVal, accuracyRating;

			offerPartnerChips = setting.flipOffer(offerReceived);
			accuracyRating = 0.0;
			for (l = 0; l < locationBeliefs.length; ++l) {
				partnerAlternative = partnerModel.selectOffer(0);
				partnerModel.utilityFunction = setting.getUtilityFunction(l);
				curExpVal = partnerModel.getValue(offerPartnerChips);
				if (partnerModel.utilityFunction[offerPartnerChips] > partnerModel.utilityFunction[partnerModel.chips]) {
					maxExpVal = partnerModel.getValue(partnerAlternative);
					if (maxExpVal > -1) {
						accuracyRating += locationBeliefs[l]*(curExpVal+1)/(maxExpVal+1);
						if (maxExpVal+1==0) {
							System.out.print(offerPartnerChips+" "+curExpVal+" "+ partnerAlternative+" "+partnerModel.chips+"  "+maxExpVal+"  ");
						}
					} else {
						// The model is incorrect. The maximum expected value is -1, which means that there is no offer that is expected
						// to be accepted. However, this also makes the current offer have value -1, 100% of the maximum value.
					}
				}
			}
			return accuracyRating+" "+selfModel.getAccuracies(offerReceived);
		}
		return "";
	}

	public void setConfidence(double[] newConfidence) {
		if (order > 0) {
			confidence = newConfidence[order];
			selfModel.setConfidence(newConfidence);
		}
	}



}
