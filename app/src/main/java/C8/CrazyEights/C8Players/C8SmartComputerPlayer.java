package C8.CrazyEights.C8Players;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Hashtable;

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
 *  ALGORITHM:
 *
 * @author Selena Li
 * @author Maliyah Miller
 * @author Jake Uyechi
 * @author Tyler Sakata
 *
 * @version 29 March 2022
 */
public class C8SmartComputerPlayer extends GameComputerPlayer {
    /**
     * instance variables
     */
    private C8GameState state = null;

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public C8SmartComputerPlayer(String name) {
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

        // retrieve the cards that have already been played
        Deck playedCards = state.getDiscardPile();

        // find if the player's hand contains valid cards
        boolean canMove = this.state.checkIfValid(this.playerNum);

        // if player can move...
        if(canMove){
            // get the data of the played faces (and corresponding frequency)
            Hashtable<String, Integer> faceData = getPlayedFaces(playedCards);
            Hashtable<String, Integer> suitData = getPlayedSuits(playedCards);

            // get the top card of the discard pile
            Card top = this.state.getDiscardPile().peekTopCard();

            // check if there's any matching suits
            boolean faceMatch = false;
            boolean suitMatch = false;
            boolean hasEight = false;
            for(Card c : currDeck.getCards()){
                if(top.matchSuit(c)) suitMatch = true;
                if(top.matchFace(c)) faceMatch = true;
                if(c.getFace().equals("Eight")) hasEight = true;
            }

            // make logical plays based on the information gathered
            if(suitMatch){
                // if there is a suit match, keep the suit the same
                // play the face card that has the highest frequency in the discard pile

            }else if(faceMatch){
                // if there is no suit match, but there is a face match
                // play the suit that has the highest frequency in the discard pile

            }else if(hasEight){
                // if there is not face or suit match, but there is an eight
                // play the eight and declare the suit with the most suits in hand

            }
        }else{
            // ...draw a card
            C8DrawAction draw = new C8DrawAction(this);
            sleep(0.5);
            this.game.sendAction(draw);
        }

    }

    public String getName() {
        return null;
    }

    /**
     * Get a Hashtable of the face values that have been played and the counts of each
     * @param cards - the cards in the discard pile
     * @return - the Hashtable of data
     */
    public Hashtable<String, Integer> getPlayedFaces(Deck cards){
        // create a return array list
        Hashtable<String, Integer> played = new Hashtable<>();

        // if deck is null, return
        if(cards == null) return null;

        // loop through all the cards in the played pile
        for(Card c : cards.getCards()){
            int count = 0;
            if(played.contains(c.getFace())) count = played.get(c.getFace());
            played.put(c.getFace(), count + 1);
        }

        // return
        return played;
    }

    /**
     * Get a Hastable of the suits that have been played and the counts of each
     * @param cards - the cards in the discard pile
     * @return - the Hashtable of data
     */
    public Hashtable<String, Integer> getPlayedSuits(Deck cards){
        // create a return array list
        Hashtable<String, Integer> played = new Hashtable<>();

        // if deck is null, return
        if(cards == null) return null;

        // loop through all the cards in the played pile
        for(Card c : cards.getCards()){
            int count = 0;
            if(played.contains(c.getSuit())) count = played.get(c.getSuit());
            played.put(c.getSuit(), count + 1);
        }

        // return
        return played;
    }
}
