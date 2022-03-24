package com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights;

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
 * @version 24 February 2022
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

        C8GameState state = (C8GameState) info;

        // Checks if its the players turn
        if(state.getPlayerTurn().equals(this.name)) {
            // Check all cards in hand to see if any are playable
            for(int i = 0; i < state.getPlayerHands().get(state.getPlayerTurn()).size(); i++) {
                // If suit matches play card
                if(state.getCurrentSuit().equals(state.getPlayerHands().get(state.getPlayerTurn()).getCards().get(i).suit)) {
                    state.movePlay(i, state.getPlayerTurn());
                    return;
                }
                // If number matches play card
                if(state.getCurrentSuit().equals(state.getPlayerHands().get(state.getPlayerTurn()).getCards().get(i).face)) {
                    state.movePlay(i, state.getPlayerTurn());
                    return;
                }
            }
            // None of your cards are playable keep drawing until you get a playable one
            state.moveDraw(state.getPlayerTurn());
        }

    }

    public String getName() {
        return null;
    }
}
