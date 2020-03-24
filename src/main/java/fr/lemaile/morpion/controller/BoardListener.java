package fr.lemaile.morpion.controller;

public interface BoardListener {
    void updateBox(int boxNumber);

    void newMatch();
}
