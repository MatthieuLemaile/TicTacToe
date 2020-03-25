package fr.lemaile.morpion.model;

import java.util.Objects;

public class Player {

    public Player() {
    }

    public Player(Player player) {
        this();
        this.symbol = player.getSymbol();
    }

    private Symbol symbol;

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return symbol == player.symbol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
}
