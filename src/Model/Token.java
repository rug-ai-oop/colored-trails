package Model;
import java.util.ArrayList;
import java.util.Random;

public class Token {
    private Color color;
    public Token(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<Token> initializeTokens(int tokenNr){
        ArrayList<Token> tokens = new ArrayList<>();
        for(int i = 0;i<tokenNr; i++){
            int pick = new Random().nextInt(Color.values().length);
            tokens.add(new Token(Color.values()[pick]));
        }
        return tokens;
    }
}
