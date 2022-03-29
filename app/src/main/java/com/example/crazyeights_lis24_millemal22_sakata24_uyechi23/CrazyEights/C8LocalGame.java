package com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights;

import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights.C8InfoMessage.C8GameState;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.GameFramework.LocalGame;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.GameFramework.actionMessage.GameAction;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.GameFramework.players.GamePlayer;

/**
 * CrazyEightsLocalGame
 *
 * The Game class that handles the game operations. It sends GameStates to players with properly
 * censored information. It handles the main GameState and takes actions from players to do so.
 *
 * @author Selena Li
 * @author Maliyah Miller
 * @author Jake Uyechi
 * @author Tyler Sakata
 *
 * @version 24 February 2022
 */
public class C8LocalGame extends LocalGame {

    // game state
    C8GameState state;

    public C8LocalGame(C8GameState initState) {

        this.state = initState;

    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        // disregard method if the state is null
        if(state == null){ return; }

        // make a copy of the game state and nullify any
        // data the player shouldn't have access to
        C8GameState newState = new C8GameState(state);

        newState.turnDiscardPileFaceDown();

    }

    @Override
    protected boolean canMove(int playerIdx) {
        return false;
    }

    @Override
    protected String checkIfGameOver() {
        return this.state.checkGameOver();
    }

    @Override
    protected boolean makeMove(GameAction action) {
        return false;
    }
}
