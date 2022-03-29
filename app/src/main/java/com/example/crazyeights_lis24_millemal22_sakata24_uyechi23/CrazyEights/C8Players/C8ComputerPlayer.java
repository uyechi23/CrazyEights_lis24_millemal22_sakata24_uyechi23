package com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights.C8Players;

import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.Cards.Deck;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights.C8InfoMessage.C8GameState;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.GameFramework.infoMessage.GameInfo;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.GameFramework.infoMessage.GameState;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.GameFramework.players.GameComputerPlayer;

/**
 * CrazyEightsComputerPlayer
 *
 *  A player object that extends from GameComputerPlayer. It operates similar to its parent.
 *  (for now)
 *
 * @author Selena Li
 * @author Maliyah Miller
 * @author Jake Uyechi
 * @author Tyler Sakata
 *
 * @version 29 March 2022
 */
public class C8ComputerPlayer extends GameComputerPlayer {
    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public C8ComputerPlayer(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        // Makes sure the info message is a Game State before sending an action
        if(!(info instanceof GameState)) {
            return;
        }

        // typecast state into a C8GameState object
        C8GameState state = (C8GameState) info;

        // retrieve the deck
        Deck currDeck = state.getPlayerHands().get(this.playerNum);

        // Checks if its the players turn
        if(state.getPlayerIndex() == this.playerNum){
            // Check all cards in hand to see if any are playable
            for(int i = 0; i < currDeck.size(); i++) {
                // If suit matches play card
                if(state.getCurrentSuit().equals(currDeck.getCards().get(i).suit)) {
                    state.movePlay(i, state.getPlayerIndex());
                    return;
                }
                // If number matches play card
                if(state.getCurrentFace().equals(currDeck.getCards().get(i).face)) {
                    state.movePlay(i, state.getPlayerIndex());
                    return;
                }
            }
            // None of your cards are playable keep drawing until you get a playable one
            state.moveDraw(state.getPlayerIndex());
        }

    }

    public String getName() {
        return null;
    }
}
