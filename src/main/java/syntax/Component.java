/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package syntax;

/**
 *
 * @author diazj
 */
public class Component {
    
    String lex;
    int token;
    int symbolPosition;
    int line;

    public Component(String lex, int token, int symbolPosition, int line) {
        this.lex = lex;
        this.token = token;
        this.symbolPosition = symbolPosition;
        this.line = line;
    }

    public String getLex() {
        return lex;
    }

    public void setLex(String lex) {
        this.lex = lex;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public int getSymbolPosition() {
        return symbolPosition;
    }

    public void setSymbolPosition(int symbolPosition) {
        this.symbolPosition = symbolPosition;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return  lex + ", " + token + "," + symbolPosition + ", " + line;
    }
}
