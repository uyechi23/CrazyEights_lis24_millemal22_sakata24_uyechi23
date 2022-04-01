package C8.CrazyEights.C8Players;

import C8.Cards.Card;
import C8.Cards.Deck;
import C8.CrazyEights.C8ActionMessage.C8DrawAction;
import C8.CrazyEights.C8ActionMessage.C8PlayAction;
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
        if(!(info instanceof GameState) || info == null) return;

        // typecast state into our C8GameState object
        this.state = (C8GameState) info;

        // if it's not the current player's turn, return
        if(this.state.getPlayerIndex() != this.playerNum) return;

        if (this.state.checkGameOver() != null){
            return;
        }

        // retrieve my deck
        Deck currDeck = state.getPlayerHands().get(this.playerNum);

        // find if the player's hand contains valid cards
        //boolean canMove = this.state.checkIfValid(this.playerNum);

        // if player can move...
        int i = 0;
        //if(canMove){
            // ...play the first valid card in hand
            for(Card c : currDeck.getCards()){
                //if(c.isValid(this.state.getDiscardPile().peekTopCard())){
                    this.sleep(1);
                    C8PlayAction play = new C8PlayAction(this, i);
                    this.game.sendAction(play);
                    break;
                }
                i++;
            //}
            return;
//        }else{
//            if (this.state.getDrawPile().isEmpty()){
//                this.state.skipTurn();
//                return;
//            }
//            // ...draw a card
//            this.sleep(1);
//            C8DrawAction draw = new C8DrawAction(this);
//            this.game.sendAction(draw);
//            this.state.nextPlayer();
//            return;
//        }

    }

    public String getName() {
        return null;
    }
}
