package fr.lemaile.morpion.ui;

import fr.lemaile.morpion.model.Box;
import fr.lemaile.morpion.model.Player;

import java.util.List;

public interface UiFactory {
    BoardUi createBoard(List<Box> boxes, Player playerOne, Player playerTwo);
}
