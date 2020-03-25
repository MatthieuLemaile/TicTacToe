package fr.lemaile.morpion.controller;

import fr.lemaile.morpion.model.Box;
import fr.lemaile.morpion.model.Player;
import fr.lemaile.morpion.model.Symbol;
import fr.lemaile.morpion.ui.BoardUi;
import fr.lemaile.morpion.ui.UiFactory;
import org.mockito.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class BoardTest {

    @Captor
    ArgumentCaptor<Player> currentPlayerCaptor;
    @Mock
    private UiFactory uiFactory;
    @Mock
    private BoardUi boardUi;
    @Captor
    private ArgumentCaptor<List<Box>> boxesCaptor;
    @Captor
    private ArgumentCaptor<Player> playerOneCaptor;
    @Captor
    private ArgumentCaptor<Player> playerTwoCaptor;
    private Board board;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(uiFactory.createBoard(boxesCaptor.capture(), playerOneCaptor.capture(), playerTwoCaptor.capture())).thenReturn(boardUi);
        board = new Board(uiFactory);
    }

    @Test
    public void constructor_check() {
        Assert.assertEquals(Symbol.CROSS, playerOneCaptor.getValue().getSymbol());
        Assert.assertEquals(Symbol.NOUGHT, playerTwoCaptor.getValue().getSymbol());
        Assert.assertEquals(9, boxesCaptor.getValue().size());
        boxesCaptor.getValue().forEach(b -> Assert.assertEquals(Symbol.EMPTY, b.getSymbol()));
        Mockito.verify(uiFactory, Mockito.times(1)).createBoard(Mockito.anyList(), Mockito.any(Player.class), Mockito.any(Player.class));
        Mockito.verify(boardUi, Mockito.times(1)).updateBoard(boxesCaptor.getValue(), playerOneCaptor.getValue());
    }

    @Test
    void new_match_should_reset() {
        boxesCaptor.getValue().get(1).setSymbol(Symbol.CROSS);
        board.newMatch();
        boxesCaptor.getValue().forEach(b -> Assert.assertEquals(Symbol.EMPTY, b.getSymbol()));
        Mockito.verify(boardUi, Mockito.times(2)).updateBoard(boxesCaptor.getValue(), playerOneCaptor.getValue());
    }

    @Test
    void update_box_should_pat() {
        // O X O X O X X O X
        Mockito.doNothing().when(boardUi).updateBoard(Mockito.anyList(), currentPlayerCaptor.capture());
        board.updateBox(1);
        board.updateBox(0);
        board.updateBox(3);
        board.updateBox(2);
        board.updateBox(5);
        board.updateBox(4);
        board.updateBox(6);
        board.updateBox(7);
        board.updateBox(8);

        Mockito.verify(boardUi, Mockito.times(10)).updateBoard(Mockito.eq(boxesCaptor.getValue()), Mockito.any(Player.class));
        List<Player> players = currentPlayerCaptor.getAllValues();
        Assert.assertEquals(9, players.size());
        Assert.assertEquals(9, boxesCaptor.getValue().size());
        //The first to play after the first time played is the second player
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(0));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(1));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(2));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(3));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(4));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(5));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(6));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(7));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(8));

        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(0).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(1).getSymbol());
        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(2).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(3).getSymbol());
        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(4).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(5).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(6).getSymbol());
        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(7).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(8).getSymbol());

        Mockito.verify(boardUi, Mockito.times(1)).pat();
        Mockito.verify(boardUi, Mockito.never()).won(Mockito.any(Player.class));
    }

    @Test
    void update_box_should_win_first_row() {
        // X X X O O . . . .
        Mockito.doNothing().when(boardUi).updateBoard(Mockito.anyList(), currentPlayerCaptor.capture());
        board.updateBox(0);
        board.updateBox(3);
        board.updateBox(1);
        board.updateBox(4);
        board.updateBox(2);

        Mockito.verify(boardUi, Mockito.times(6)).updateBoard(Mockito.eq(boxesCaptor.getValue()), Mockito.any(Player.class));
        List<Player> players = currentPlayerCaptor.getAllValues();
        Assert.assertEquals(5, players.size());
        Assert.assertEquals(9, boxesCaptor.getValue().size());
        //The first to play after the first time played is the second player
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(0));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(1));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(2));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(3));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(4));

        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(0).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(1).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(2).getSymbol());
        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(3).getSymbol());
        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(4).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(5).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(6).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(7).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(8).getSymbol());

        Mockito.verify(boardUi, Mockito.never()).pat();
        Mockito.verify(boardUi, Mockito.times(1)).won(playerOneCaptor.getValue());
    }

    @Test
    void update_box_should_win_second_row() {
        // O O . X X X . . .
        Mockito.doNothing().when(boardUi).updateBoard(Mockito.anyList(), currentPlayerCaptor.capture());
        board.updateBox(3);
        board.updateBox(0);
        board.updateBox(4);
        board.updateBox(1);
        board.updateBox(5);

        Mockito.verify(boardUi, Mockito.times(6)).updateBoard(Mockito.eq(boxesCaptor.getValue()), Mockito.any(Player.class));
        List<Player> players = currentPlayerCaptor.getAllValues();
        Assert.assertEquals(5, players.size());
        Assert.assertEquals(9, boxesCaptor.getValue().size());
        //The first to play after the first time played is the second player
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(0));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(1));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(2));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(3));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(4));

        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(0).getSymbol());
        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(1).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(2).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(3).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(4).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(5).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(6).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(7).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(8).getSymbol());

        Mockito.verify(boardUi, Mockito.never()).pat();
        Mockito.verify(boardUi, Mockito.times(1)).won(playerOneCaptor.getValue());
    }

    @Test
    void update_box_should_win_third_row() {
        // O O . . . . X X X
        Mockito.doNothing().when(boardUi).updateBoard(Mockito.anyList(), currentPlayerCaptor.capture());
        board.updateBox(6);
        board.updateBox(0);
        board.updateBox(7);
        board.updateBox(1);
        board.updateBox(8);

        Mockito.verify(boardUi, Mockito.times(6)).updateBoard(Mockito.eq(boxesCaptor.getValue()), Mockito.any(Player.class));
        List<Player> players = currentPlayerCaptor.getAllValues();
        Assert.assertEquals(5, players.size());
        Assert.assertEquals(9, boxesCaptor.getValue().size());
        //The first to play after the first time played is the second player
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(0));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(1));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(2));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(3));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(4));

        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(0).getSymbol());
        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(1).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(2).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(3).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(4).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(5).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(6).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(7).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(8).getSymbol());

        Mockito.verify(boardUi, Mockito.never()).pat();
        Mockito.verify(boardUi, Mockito.times(1)).won(playerOneCaptor.getValue());
    }

    @Test
    void update_box_should_win_first_column() {
        // X O O X . . X . .
        Mockito.doNothing().when(boardUi).updateBoard(Mockito.anyList(), currentPlayerCaptor.capture());
        board.updateBox(0);
        board.updateBox(1);
        board.updateBox(3);
        board.updateBox(2);
        board.updateBox(6);

        Mockito.verify(boardUi, Mockito.times(6)).updateBoard(Mockito.eq(boxesCaptor.getValue()), Mockito.any(Player.class));
        List<Player> players = currentPlayerCaptor.getAllValues();
        Assert.assertEquals(5, players.size());
        Assert.assertEquals(9, boxesCaptor.getValue().size());
        //The first to play after the first time played is the second player
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(0));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(1));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(2));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(3));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(4));

        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(0).getSymbol());
        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(1).getSymbol());
        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(2).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(3).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(4).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(5).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(6).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(7).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(8).getSymbol());

        Mockito.verify(boardUi, Mockito.never()).pat();
        Mockito.verify(boardUi, Mockito.times(1)).won(playerOneCaptor.getValue());
    }

    @Test
    void update_box_should_win_second_column() {
        // O X O . X . . X .
        Mockito.doNothing().when(boardUi).updateBoard(Mockito.anyList(), currentPlayerCaptor.capture());
        board.updateBox(1);
        board.updateBox(0);
        board.updateBox(4);
        board.updateBox(2);
        board.updateBox(7);

        Mockito.verify(boardUi, Mockito.times(6)).updateBoard(Mockito.eq(boxesCaptor.getValue()), Mockito.any(Player.class));
        List<Player> players = currentPlayerCaptor.getAllValues();
        Assert.assertEquals(5, players.size());
        Assert.assertEquals(9, boxesCaptor.getValue().size());
        //The first to play after the first time played is the second player
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(0));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(1));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(2));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(3));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(4));

        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(0).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(1).getSymbol());
        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(2).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(3).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(4).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(5).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(6).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(7).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(8).getSymbol());

        Mockito.verify(boardUi, Mockito.never()).pat();
        Mockito.verify(boardUi, Mockito.times(1)).won(playerOneCaptor.getValue());
    }

    @Test
    void update_box_should_win_third_column() {
        // O O X . . X . . X
        Mockito.doNothing().when(boardUi).updateBoard(Mockito.anyList(), currentPlayerCaptor.capture());
        board.updateBox(2);
        board.updateBox(0);
        board.updateBox(5);
        board.updateBox(1);
        board.updateBox(8);

        Mockito.verify(boardUi, Mockito.times(6)).updateBoard(Mockito.eq(boxesCaptor.getValue()), Mockito.any(Player.class));
        List<Player> players = currentPlayerCaptor.getAllValues();
        Assert.assertEquals(5, players.size());
        Assert.assertEquals(9, boxesCaptor.getValue().size());
        //The first to play after the first time played is the second player
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(0));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(1));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(2));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(3));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(4));

        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(0).getSymbol());
        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(1).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(2).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(3).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(4).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(5).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(6).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(7).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(8).getSymbol());

        Mockito.verify(boardUi, Mockito.never()).pat();
        Mockito.verify(boardUi, Mockito.times(1)).won(playerOneCaptor.getValue());
    }

    @Test
    void update_box_should_win_diagonal() {
        // O X X X O X . . O
        Mockito.doNothing().when(boardUi).updateBoard(Mockito.anyList(), currentPlayerCaptor.capture());
        board.updateBox(1);
        board.updateBox(0);
        board.updateBox(2);
        board.updateBox(4);
        board.updateBox(3);
        board.updateBox(8);
        board.updateBox(5);

        Mockito.verify(boardUi, Mockito.times(8)).updateBoard(Mockito.eq(boxesCaptor.getValue()), Mockito.any(Player.class));
        List<Player> players = currentPlayerCaptor.getAllValues();
        Assert.assertEquals(7, players.size());
        Assert.assertEquals(9, boxesCaptor.getValue().size());
        //The first to play after the first time played is the second player
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(0));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(1));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(2));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(3));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(4));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(5));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(6));

        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(0).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(1).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(2).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(3).getSymbol());
        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(4).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(5).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(6).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(7).getSymbol());
        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(8).getSymbol());

        Mockito.verify(boardUi, Mockito.never()).pat();
        Mockito.verify(boardUi, Mockito.times(1)).won(playerTwoCaptor.getValue());
    }

    @Test
    void update_box_should_win_antidiag() {
        // X X O X O X O . .
        Mockito.doNothing().when(boardUi).updateBoard(Mockito.anyList(), currentPlayerCaptor.capture());
        board.updateBox(0);
        board.updateBox(2);
        board.updateBox(1);
        board.updateBox(4);
        board.updateBox(3);
        board.updateBox(6);
        board.updateBox(5);

        Mockito.verify(boardUi, Mockito.times(8)).updateBoard(Mockito.eq(boxesCaptor.getValue()), Mockito.any(Player.class));
        List<Player> players = currentPlayerCaptor.getAllValues();
        Assert.assertEquals(7, players.size());
        Assert.assertEquals(9, boxesCaptor.getValue().size());
        //The first to play after the first time played is the second player
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(0));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(1));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(2));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(3));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(4));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(5));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(6));

        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(0).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(1).getSymbol());
        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(2).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(3).getSymbol());
        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(4).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(5).getSymbol());
        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(6).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(7).getSymbol());
        Assert.assertEquals(Symbol.EMPTY, boxesCaptor.getValue().get(8).getSymbol());

        Mockito.verify(boardUi, Mockito.never()).pat();
        Mockito.verify(boardUi, Mockito.times(1)).won(playerTwoCaptor.getValue());
    }

    @Test
    void update_box_should_win_last_move() {
        // O O X X O X O X X
        Mockito.doNothing().when(boardUi).updateBoard(Mockito.anyList(), currentPlayerCaptor.capture());
        board.updateBox(2);
        board.updateBox(0);
        board.updateBox(5);
        board.updateBox(1);
        board.updateBox(3);
        board.updateBox(4);
        board.updateBox(7);
        board.updateBox(6);
        board.updateBox(8);

        Mockito.verify(boardUi, Mockito.times(10)).updateBoard(Mockito.eq(boxesCaptor.getValue()), Mockito.any(Player.class));
        List<Player> players = currentPlayerCaptor.getAllValues();
        Assert.assertEquals(9, players.size());
        Assert.assertEquals(9, boxesCaptor.getValue().size());
        //The first to play after the first time played is the second player
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(0));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(1));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(2));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(3));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(4));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(5));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(6));
        Assert.assertEquals(playerOneCaptor.getValue(), players.get(7));
        Assert.assertEquals(playerTwoCaptor.getValue(), players.get(8));

        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(0).getSymbol());
        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(1).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(2).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(3).getSymbol());
        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(4).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(5).getSymbol());
        Assert.assertEquals(Symbol.NOUGHT, boxesCaptor.getValue().get(6).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(7).getSymbol());
        Assert.assertEquals(Symbol.CROSS, boxesCaptor.getValue().get(8).getSymbol());

        Mockito.verify(boardUi, Mockito.never()).pat();
        Mockito.verify(boardUi, Mockito.times(1)).won(playerOneCaptor.getValue());
    }
}