package C8.CrazyEights.C8Players;

import C8.Cards.Deck;
import C8.CrazyEights.C8InfoMessage.C8GameState;
import C8.GameFramework.infoMessage.GameInfo;
import C8.GameFramework.infoMessage.GameState;
import C8.GameFramework.players.GameComputerPlayer;

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
     * instance variables
     */
    private C8GameState state = null;

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
        // Makes sure the info message is a Game State before sending an action or if its null
        if(!(info instanceof GameState) || info == null) {
            return;
        }

        // typecast state into our C8GameState object
        this.state = (C8GameState) info;

        // retrieve my deck
        Deck currDeck = state.getPlayerHands().get(this.playerNum);

        if (state.getPlayerIndex() == this.playerNum){
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
        else{
            state.nextPlayer();
        }

    }

    public String getName() {
        return null;
    }
}
