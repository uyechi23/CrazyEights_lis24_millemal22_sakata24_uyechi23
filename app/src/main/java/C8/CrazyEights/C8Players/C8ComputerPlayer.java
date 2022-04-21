package C8.CrazyEights.C8Players;

import android.util.Log;

import C8.Cards.Card;
import C8.Cards.Deck;
import C8.CrazyEights.C8ActionMessage.C8DrawAction;
import C8.CrazyEights.C8ActionMessage.C8PlayAction;
import C8.CrazyEights.C8ActionMessage.C8SelectSuitAction;
import C8.CrazyEights.C8ActionMessage.C8SkipAction;
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
 * @version 21 April 2022
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
        if(!(info instanceof GameState) || info == null) return;

        // typecast state into our C8GameState object
        this.state = (C8GameState) info;

        // if it's not the current player's turn, return
        if(this.state.getPlayerIndex() != this.playerNum) return;

        // retrieve my deck
        Deck currDeck = state.getPlayerHands().get(this.playerNum);

        // find if the player's hand contains valid cards
        boolean canMove = this.state.checkIfValid(this.playerNum);

        // if the player has just played an eight
        if(!this.state.getHasDeclaredSuit()) {
            this.game.sendAction(new C8SelectSuitAction
                    (this, this.state.getDiscardPile().peekTopCard().getSuit()));
        }
        // if player can move...
        else if(canMove){
            int i = 0;
            // ...play the first valid card in hand
            for(Card c : currDeck.getCards()){
                if(this.state.getCurrentSuit().equals(c.getSuit())
                        || this.state.getCurrentFace().equals(c.getFace())
                        || c.getFace().equals("Eight")) {
                    sleep(1.5);
                    Log.d("Found", "Played card" + c.getFace() +" of " + c.getSuit());
                    this.game.sendAction(new C8PlayAction(this, i));
                    break;
                }
                i++;
            }
        }else{
            if(!this.state.getDrawPile().isEmpty()) {
                // ...draw a card
                C8DrawAction draw = new C8DrawAction(this);
                sleep(0.5);
                this.game.sendAction(draw);
            }else{
                C8SkipAction skip = new C8SkipAction(this);
                sleep(0.5);
                this.game.sendAction(skip);
            }
        }

    }

    public String getName() {
        return null;
    }
}
