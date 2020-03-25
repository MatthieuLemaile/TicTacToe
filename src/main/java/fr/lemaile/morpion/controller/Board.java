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
    private int moveCount;
    private boolean won;

    public Board(UiFactory uiFactory) {
        playerOne = new Player();
        playerOne.setSymbol(Symbol.CROSS);
        playerTwo = new Player();
        playerTwo.setSymbol(Symbol.NOUGHT);
        currentPlayer = playerOne;
        boxes = new ArrayList<>();
        moveCount = 0;
        won = false;
        IntStream.range(0, 9).forEach(i -> boxes.add(new Box(Symbol.EMPTY)));
        boardUi = uiFactory.createBoard(boxes, playerOne, playerTwo);
        boardUi.updateBoard(boxes, currentPlayer);
    }

    @Override
    public void updateBox(int boxNumber) {
        moveCount++;
        Player playingPlayer = new Player(currentPlayer);
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
                won = checkWinFirstRow(lastMoveSymbol) ||
                        checkWinFirstColumn(lastMoveSymbol) ||
                        checkWinDiagonal(lastMoveSymbol);
                break;
            case 1:
                won = checkWinFirstRow(lastMoveSymbol) ||
                        checkWinSecondColumn(lastMoveSymbol);
                break;
            case 2:
                won = checkWinFirstRow(lastMoveSymbol) ||
                        checkWinThirdColumn(lastMoveSymbol) ||
                        checkWinAntiDiagonal(lastMoveSymbol);
                break;
            case 3:
                won = checkWinSecondRow(lastMoveSymbol) ||
                        checkWinFirstColumn(lastMoveSymbol);
                break;
            case 4:
                won = checkWinSecondRow(lastMoveSymbol) ||
                        checkWinSecondColumn(lastMoveSymbol) ||
                        checkWinDiagonal(lastMoveSymbol) ||
                        checkWinAntiDiagonal(lastMoveSymbol);
                break;
            case 5:
                won = checkWinSecondRow(lastMoveSymbol) ||
                        checkWinThirdColumn(lastMoveSymbol);
                break;
            case 6:
                won = checkWinThirdRow(lastMoveSymbol) ||
                        checkWinFirstColumn(lastMoveSymbol) ||
                        checkWinAntiDiagonal(lastMoveSymbol);
                break;
            case 7:
                won = checkWinThirdRow(lastMoveSymbol) ||
                        checkWinSecondColumn(lastMoveSymbol);
                break;
            case 8:
                won = checkWinThirdRow(lastMoveSymbol) ||
                        checkWinThirdColumn(lastMoveSymbol) ||
                        checkWinDiagonal(lastMoveSymbol);
                break;
            default:
        }
        //TODO Refacto this to check 9 move before switching, and so ending function faster, avoid unnecessary calculus.
        if (won) {
            boardUi.won(evaluatedPlayer);
        } else {
            if (moveCount == 9) {
                boardUi.pat();
            }
        }
    }

    private boolean checkWinFirstRow(Symbol lastMoveSymbol) {
        return boxes.get(0).getSymbol().equals(lastMoveSymbol) &&
                boxes.get(1).getSymbol().equals(lastMoveSymbol) &&
                boxes.get(2).getSymbol().equals(lastMoveSymbol);
    }

    private boolean checkWinSecondRow(Symbol lastMoveSymbol) {
        return boxes.get(3).getSymbol().equals(lastMoveSymbol) &&
                boxes.get(4).getSymbol().equals(lastMoveSymbol) &&
                boxes.get(5).getSymbol().equals(lastMoveSymbol);
    }

    private boolean checkWinThirdRow(Symbol lastMoveSymbol) {
        return boxes.get(6).getSymbol().equals(lastMoveSymbol) &&
                boxes.get(7).getSymbol().equals(lastMoveSymbol) &&
                boxes.get(8).getSymbol().equals(lastMoveSymbol);
    }

    private boolean checkWinFirstColumn(Symbol lastMoveSymbol) {
        return boxes.get(0).getSymbol().equals(lastMoveSymbol) &&
                boxes.get(3).getSymbol().equals(lastMoveSymbol) &&
                boxes.get(6).getSymbol().equals(lastMoveSymbol);
    }

    private boolean checkWinSecondColumn(Symbol lastMoveSymbol) {
        return boxes.get(1).getSymbol().equals(lastMoveSymbol) &&
                boxes.get(4).getSymbol().equals(lastMoveSymbol) &&
                boxes.get(7).getSymbol().equals(lastMoveSymbol);
    }

    private boolean checkWinThirdColumn(Symbol lastMoveSymbol) {
        return boxes.get(2).getSymbol().equals(lastMoveSymbol) &&
                boxes.get(5).getSymbol().equals(lastMoveSymbol) &&
                boxes.get(8).getSymbol().equals(lastMoveSymbol);
    }

    private boolean checkWinDiagonal(Symbol lastMoveSymbol) {
        return boxes.get(0).getSymbol().equals(lastMoveSymbol) &&
                boxes.get(4).getSymbol().equals(lastMoveSymbol) &&
                boxes.get(8).getSymbol().equals(lastMoveSymbol);
    }

    private boolean checkWinAntiDiagonal(Symbol lastMoveSymbol) {
        return boxes.get(2).getSymbol().equals(lastMoveSymbol) &&
                boxes.get(4).getSymbol().equals(lastMoveSymbol) &&
                boxes.get(6).getSymbol().equals(lastMoveSymbol);
    }

    @Override
    public void newMatch() {
        boxes.forEach(b -> b.setSymbol(Symbol.EMPTY));
        moveCount = 0;
        won = false;
        currentPlayer = playerOne;
        boardUi.updateBoard(boxes, currentPlayer);
    }
}
