package fr.lemaile.morpion.controller;

import fr.lemaile.morpion.model.Box;
import fr.lemaile.morpion.model.Player;
import fr.lemaile.morpion.model.Symbol;
import fr.lemaile.morpion.ui.BoardUi;
import fr.lemaile.morpion.ui.UiFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Board implements BoardListener {

    private final List<Box> boxes;
    private final Player playerOne;
    private final Player playerTwo;
    private final BoardUi boardUi;
    private Player currentPlayer;

    public Board(UiFactory uiFactory) {
        playerOne = new Player();
        playerOne.setSymbol(Symbol.CROSS);
        playerTwo = new Player();
        playerTwo.setSymbol(Symbol.NOUGHT);
        currentPlayer = playerOne;
        boxes = new ArrayList<>();
        IntStream.range(0, 9).forEach(i -> boxes.add(new Box(Symbol.EMPTY)));
        boardUi = uiFactory.createBoard(boxes, playerOne, playerTwo);
        boardUi.updateBoard(boxes, currentPlayer);
    }

    @Override
    public void updateBox(int boxNumber) {
        Player playingPlayer = currentPlayer;
        boxes.get(boxNumber).setSymbol(playingPlayer.getSymbol());
        if (currentPlayer.equals(playerOne)) {
            currentPlayer = playerTwo;
        } else {
            currentPlayer = playerOne;
        }
        boardUi.updateBoard(boxes, currentPlayer);
        checkWinCondition(boxNumber, playingPlayer);
    }

    private void checkWinCondition(int lastMoveBoxNumber, Player evaluatedPlayer) {
        Symbol lastMoveSymbol = evaluatedPlayer.getSymbol();
        switch (lastMoveBoxNumber) {
            case 0:
                if (boxes.get(1).getSymbol().equals(lastMoveSymbol) && boxes.get(2).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                if (boxes.get(3).getSymbol().equals(lastMoveSymbol) && boxes.get(6).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                if (boxes.get(4).getSymbol().equals(lastMoveSymbol) && boxes.get(8).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                break;
            case 1:
                if (boxes.get(0).getSymbol().equals(lastMoveSymbol) && boxes.get(2).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                if (boxes.get(4).getSymbol().equals(lastMoveSymbol) && boxes.get(7).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                break;
            case 2:
                if (boxes.get(0).getSymbol().equals(lastMoveSymbol) && boxes.get(1).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                if (boxes.get(5).getSymbol().equals(lastMoveSymbol) && boxes.get(8).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                if (boxes.get(4).getSymbol().equals(lastMoveSymbol) && boxes.get(6).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                break;
            case 3:
                if (boxes.get(4).getSymbol().equals(lastMoveSymbol) && boxes.get(5).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                if (boxes.get(0).getSymbol().equals(lastMoveSymbol) && boxes.get(6).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                break;
            case 4:
                if (boxes.get(3).getSymbol().equals(lastMoveSymbol) && boxes.get(5).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                if (boxes.get(1).getSymbol().equals(lastMoveSymbol) && boxes.get(7).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                if (boxes.get(0).getSymbol().equals(lastMoveSymbol) && boxes.get(8).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                if (boxes.get(2).getSymbol().equals(lastMoveSymbol) && boxes.get(6).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                break;
            case 5:
                if (boxes.get(3).getSymbol().equals(lastMoveSymbol) && boxes.get(4).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                if (boxes.get(2).getSymbol().equals(lastMoveSymbol) && boxes.get(8).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                break;
            case 6:
                if (boxes.get(7).getSymbol().equals(lastMoveSymbol) && boxes.get(8).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                if (boxes.get(0).getSymbol().equals(lastMoveSymbol) && boxes.get(3).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                if (boxes.get(2).getSymbol().equals(lastMoveSymbol) && boxes.get(4).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                break;
            case 7:
                if (boxes.get(6).getSymbol().equals(lastMoveSymbol) && boxes.get(8).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                if (boxes.get(1).getSymbol().equals(lastMoveSymbol) && boxes.get(4).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                break;
            case 8:
                if (boxes.get(6).getSymbol().equals(lastMoveSymbol) && boxes.get(7).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                if (boxes.get(2).getSymbol().equals(lastMoveSymbol) && boxes.get(5).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                if (boxes.get(0).getSymbol().equals(lastMoveSymbol) && boxes.get(4).getSymbol().equals(lastMoveSymbol)) {
                    boardUi.won(evaluatedPlayer);
                }
                break;
            default:

        }
    }

    @Override
    public void newMatch() {
        boxes.forEach(b -> b.setSymbol(Symbol.EMPTY));
        boardUi.updateBoard(boxes, currentPlayer);
        currentPlayer = playerOne;
    }
}
