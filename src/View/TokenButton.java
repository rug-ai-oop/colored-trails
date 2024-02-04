package View;

import Model.Token;

import javax.swing.*;

public class TokenButton extends JButton {
    private Token token;
    public TokenButton(Token token) {
        super();
        this.token = token;
        //this.setBackground(token.getColor());
    }
}
