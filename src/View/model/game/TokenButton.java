package View.model.game;

import Model.Token;

import javax.swing.*;

public class TokenButton extends JButton {
    private Token token;
    public TokenButton(Token token) {
        super();
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}
