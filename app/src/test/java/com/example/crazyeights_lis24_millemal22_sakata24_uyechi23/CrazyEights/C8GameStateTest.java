package com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights;

import static org.junit.Assert.*;

import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.Cards.Card;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.Cards.Deck;

import org.junit.Test;

public class C8GameStateTest {

    @Test
    public void drawCard() {
        String[] players = {"Maliyah", "Tyler", "Selena", "Jake"};
        C8GameState state = new C8GameState(players, 1);
        Card topOfDrawPile = state.getDrawPile().peekTopCard();
        boolean isValid = state.drawCard();
        Deck currPlayerHand = state.getPlayerHands().get(state.getPlayerTurn());
        Card justDrawn = currPlayerHand.peekTopCard();
        assertEquals(topOfDrawPile.getFace(), justDrawn.getFace());
        assertEquals(topOfDrawPile.getSuit(), justDrawn.getSuit());
        assertTrue(isValid);
    }

    @Test
    public void playCard() {
        String[] players = {"Maliyah", "Tyler", "Selena", "Jake"};
        C8GameState state = new C8GameState(players, 1);
        Card toBePlayed = new Card("Seven", "Hearts");
        boolean isValid = state.playCard(1); // play the 7 of Hearts
        assertEquals(state.getCurrentFace(), toBePlayed.getFace());
        assertEquals(state.getCurrentSuit(), toBePlayed.getSuit());
        assertTrue(isValid);
    }

    @Test
    public void nextPlayer() {
        String[] players = {"Maliyah", "Tyler", "Selena", "Jake"};
        C8GameState state = new C8GameState(players);
        String currentPlayer = state.getPlayerTurn();
        boolean isValid = state.nextPlayer();
        String nextPlayer = state.getPlayerTurn();
        int currIndex = 0;
        for(int i = 0; i < state.getPlayerNames().length; i++){
            if(players[i].equals(currentPlayer)) currIndex = i;
        }
        int nextIndex = (currIndex + 1) % players.length;
        assertEquals(nextPlayer, players[nextIndex]);
        assertTrue(isValid);
    }
}