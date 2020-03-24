package fr.lemaile.morpion.ui;

import fr.lemaile.morpion.model.Box;
import fr.lemaile.morpion.model.Player;

import java.util.List;

public interface BoardUi {
    void updateBoard(List<Box> boxes, Player playingPlayer);

    void won(Player wonPlayer);

    void pat();
}
