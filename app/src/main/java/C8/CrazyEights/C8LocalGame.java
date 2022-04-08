package C8.CrazyEights;

import android.util.Log;

import C8.Cards.Card;
import C8.CrazyEights.C8ActionMessage.C8DrawAction;
import C8.CrazyEights.C8ActionMessage.C8PlayAction;
import C8.CrazyEights.C8ActionMessage.C8SkipAction;
import C8.CrazyEights.C8InfoMessage.C8GameState;
import C8.GameFramework.LocalGame;
import C8.GameFramework.actionMessage.GameAction;
import C8.GameFramework.players.GamePlayer;

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
 * @version 29 March 2022
 */
public class C8LocalGame extends LocalGame {

    // game state
    C8GameState state;

    public C8LocalGame(C8GameState initState) {
        this.state = initState;
    }

    /**
     * Sends a censored state to a GamePlayer.
     *
     * @param p the player to notify
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        // disregard method if the state is null
        if(state == null){ return; }

        // make a copy of the game state and nullify any
        // data the player shouldn't have access to
        int playerID = getPlayerIdx(p);
        C8GameState newState = new C8GameState(this.state, playerID);

        // send info to the player
        p.sendInfo(newState);
    }

    /**
     * Checks if a current player can move. Called when an action is recieved.
     *
     * @param playerIdx the player's player-number (ID)
     * @return if the player can move
     */
    @Override
    protected boolean canMove(int playerIdx) {
        // if the move is from a player whose turn it is not return false
        if(playerIdx != this.state.getPlayerIndex()) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the game is over.
     *
     * @return string of name of who won. Null if no-one has won.
     */
    @Override
    protected String checkIfGameOver() {
        return this.state.checkGameOver();
    }

    /**
     * Makes a move for a player. Called when an action is received.
     *
     * @param action The move that the player has sent to the game
     * @return if the move was valid
     */
    @Override
    protected boolean makeMove(GameAction action) {
        Log.d("Test", state.toString());
        // check type of action
        if(action instanceof C8DrawAction) {
            // returns true if a move was made,
            // returns false if draw pile empty
            return state.moveDraw();
        }
        else if(action instanceof C8PlayAction) {
            // returns true if valid move was made,
            // false if card was not played
            return state.movePlay(((C8PlayAction) action).getIndex());
        }
        else if (action instanceof C8SkipAction) {
            // returns true if valid move was made,
            // false if turn can not be skipped
            for (Card c : this.state.getPlayerHands().get(0).cards) {
                if (c.isValid(new Card(this.state.getCurrentFace(), this.state.getCurrentSuit()))) {
                    return false;
                }
            }
            return state.skipTurn();

        }
        // action was not valid
        return false;
    }
}
