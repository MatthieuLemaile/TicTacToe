package fr.lemaile.morpion.model;

public class Box {

    public Box(Symbol symbol) {
        this.symbol = symbol;
    }

    private Symbol symbol;

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }
}
