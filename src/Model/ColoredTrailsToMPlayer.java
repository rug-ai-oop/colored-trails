package Model;

import Model.ToMPlayer.Chips;
import Model.ToMPlayer.Player;
import Model.ToMPlayer.PlayerToM;
import Model.ToMPlayer.Setting;

import java.io.Serializable;
import java.util.ArrayList;

public class ColoredTrailsToMPlayer extends ColoredTrailsPlayer implements Serializable {
	Player proxyPlayer;
	Setting proxySetting = new Setting();
	int receivedOffer;

	public ColoredTrailsToMPlayer(String name, int order) {
		super(name);
		proxyPlayer = new PlayerToM(order, proxySetting, Setting.NUMBER_OF_COLORS, Setting.TOKENS_PER_PLAYER);
	}


	@Override
	public void setGrid(Grid grid) {
		super.setGrid(grid);
	}

	public void init() {
		proxySetting.loadGrid(grid);
		int playerID = 0;
		proxyPlayer.init(proxySetting.getChipSet(playerID), proxySetting.getChipSet(1 - playerID), proxySetting.getUtilityFunction((new int[]{0,1,1,2,3,4,4,4,4,5,5,5,5,5,5,6,6,6,6,7,8,9,10,10,11,12})[grid.getGoalIndex(this)]));
		receivedOffer = proxySetting.getChipSet(playerID);
	}


	@Override
	public Patch revealGoal() {
		// TODO: Implement revealing goal
		return null;
	}

	@Override
	public void listenToGoal(Patch goal) {
		// TODO: Implement listening to goal

	}

	@Override
	public ArrayList<ArrayList<Token>> makeOffer() {
		int offer = proxyPlayer.makeOffer(receivedOffer);
		int[] chipset = Chips.getBins(offer, proxySetting.getBinMax());
		ArrayList<Token> tokensInPlay = grid.getAllTokensInPlay();
		ArrayList<ArrayList<Token>> newOffer = new ArrayList<ArrayList<Token>>();
		newOffer.add(new ArrayList<Token>());
		newOffer.add(new ArrayList<Token>());
		for (Token token : tokensInPlay) {
			if (chipset[Chips.getTokenColor(token)] > 0) {
				chipset[Chips.getTokenColor(token)]--;
				newOffer.get(0).add(token);
			} else {
				newOffer.get(1).add(token);
			}
		}
		return newOffer;
	}

	@Override
	public void receiveOffer(ArrayList<ArrayList<Token>> offer) {
		int[] bins = new int[Setting.NUMBER_OF_COLORS];
		for (Token token : offer.get(0)) {
			bins[Chips.getTokenColor(token)]++;
		}
		receivedOffer = Chips.convert(bins, proxySetting.getBinMax());
	}


	public static void main(String[] args) {
		Grid grid = new Grid();
		ColoredTrailsToMPlayer[] players = new ColoredTrailsToMPlayer[2];
		players[0] = new ColoredTrailsToMPlayer("Alpha", 0);
		players[1] = new ColoredTrailsToMPlayer("Beta", 0);
		grid.addPlayer(players[0]);
		grid.addPlayer(players[1]);
		grid.setUp(0);
		players[0].init();
		players[1].init();
		try {
			grid.start(false);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}
}
