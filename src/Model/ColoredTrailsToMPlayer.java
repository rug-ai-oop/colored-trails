package Model;

import Model.ToMPlayer.Chips;
import Model.ToMPlayer.Player;
import Model.ToMPlayer.PlayerToM;
import Model.ToMPlayer.Setting;

import java.util.ArrayList;

public class ColoredTrailsToMPlayer extends ColoredTrailsPlayer {
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
		proxySetting.loadGrid(grid);
		// TODO: I need some way of getting my goal
		int playerID = 0;
		proxyPlayer.init(proxySetting.getChipSet(playerID), proxySetting.getChipSet(1 - playerID), new int[]{1,2}/*How do I get the utility function? */);
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
}
