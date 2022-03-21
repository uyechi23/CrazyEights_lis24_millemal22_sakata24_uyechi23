package com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.GameFramework.infoMessage.GameState;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.GameFramework.players.GameComputerPlayer;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.GameFramework.players.GamePlayer;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.GameFramework.players.ProxyPlayer;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

/**
 * CrazyEightsGameState
 *
 * Creates the decks, each player's hand, and verifies functionality of each game action
 *
 * @author Selena Li
 * @author Maliyah Miller
 * @author Jake Uyechi
 * @author Tyler Sakata
 *
 * @version 15 March 2022
 */
public class CrazyEightsGameState extends GameState {
    /* Instance variables */
    private String playerTurn; // name of player whose turn it is
    private String[] playerNames; // names of the players
    private int playerIndex; // ID number of the player whose turn it is
    private Hashtable<String, Deck> playerHands; // all players hands
    private Deck drawPile; // cards to be drawn from
    private Deck discardPile; // cards that were discarded
    private String currentSuit; // top card current suit
    private String currentFace; // top card current face
    private boolean hasDeclaredSuit; // false if 8 has been played without a declared suit

    /**
     * CrazyEightsGameState constructor
     *
     * Initializes the instance variables for start of game
     *
     * @param players - a String array of player names
     */
    public CrazyEightsGameState(String[] players) {
        // take the input array as the playerNames
        this.playerNames = players;

        // set the hasDeclaredSuit variable
        this.hasDeclaredSuit = true;

        // randomly choose a first turn
        Random rand = new Random();
        this.playerIndex = rand.nextInt(players.length);
        this.playerTurn = players[this.playerIndex];

        // create a hashtable of player hands
        // given an array of players, iterate through each and make an entry in the hashtable
        this.playerHands = new Hashtable<>();
        for(String player : players){
            this.playerHands.put(player, new Deck());
        }

        // create the draw pile as a new deck, add 52 cards, and shuffle
        this.drawPile = new Deck();
        this.drawPile.add52();
        this.drawPile.shuffle();

        // create an empty discard pile and add a card to it
        this.discardPile = new Deck();
        Card topCard = this.drawPile.removeTopCard();
        while(topCard.getValue() == 8){
            this.drawPile.add(topCard);
            this.drawPile.shuffle();
            topCard = this.drawPile.removeTopCard();
        }
        this.discardPile.add(topCard);

        // set current suit and face
        currentSuit = this.discardPile.peekTopCard().getSuit();
        currentFace = this.discardPile.peekTopCard().getFace();

        // distribute cards to each player
        int perPlayer;
        if(players.length <= 4){
            perPlayer = 7;
        }else{
            perPlayer = 5;
        }
        for(String player : players){
            for(int i = 0; i < perPlayer; i++) {
                if(this.drawPile != null && this.playerHands.get(player) != null) {
                    this.playerHands.get(player).add(this.drawPile.removeTopCard());
                }
            }
        }
    }

    /**
     * CrazyEightsGameState constructor
     *
     * Initializes the instance variables for start of game
     *
     * @param players - a String array of player names
     * @param randSeed - a seed to change the shuffling pattern of the deck
     */
    public CrazyEightsGameState(String[] players, int randSeed) {
        // take the input array as the playerNames
        this.playerNames = players;

        // set the hasDeclaredSuit variable
        this.hasDeclaredSuit = true;

        // randomly choose a first turn based on seed
        Random rand = new Random(randSeed);
        this.playerIndex = rand.nextInt(players.length);
        this.playerTurn = players[this.playerIndex];

        // create a hashtable of player hands
        // given an array of players, iterate through each and make an entry in the hashtable
        this.playerHands = new Hashtable<>();
        for(String player : players){
            this.playerHands.put(player, new Deck());
        }

        // create the draw pile as a new deck, add 52 cards, and shuffle
        this.drawPile = new Deck();
        this.drawPile.add52();
        this.drawPile.shuffleSeed(randSeed);

        // create an empty discard pile and add a card to it
        this.discardPile = new Deck();
        Card topCard = this.drawPile.removeTopCard();
        while(topCard.getValue() == 8){
            this.drawPile.add(topCard);
            this.drawPile.shuffleSeed(randSeed);
            topCard = this.drawPile.removeTopCard();
        }
        this.discardPile.add(topCard);

        // set current suit and face
        currentSuit = this.discardPile.peekTopCard().getSuit();
        currentFace = this.discardPile.peekTopCard().getFace();

        // distribute cards to each player
        int perPlayer = 0;
        if(players.length <= 4){
            perPlayer = 7;
        }else{
            perPlayer = 5;
        }
        for(String player : players){
            for(int i = 0; i < perPlayer; i++) {
                if(this.drawPile != null) {
                    this.playerHands.get(player).add(this.drawPile.removeTopCard());
                }
            }
        }
    }

    /**
     * Copy Constructor
     *
     * Makes a censored copy for players
     *
     * @param origState - the original CrazyEightsGameState
     * @param p - the GamePlayer this object will be sent to
     */
    public CrazyEightsGameState(CrazyEightsGameState origState, GamePlayer p) {
        // take the input array as the playerNames
        this.setPlayerNames(origState.getPlayerNames());

        // set the hasDeclaredSuit variable
        this.setHasDeclaredSuit(origState.getHasDeclaredSuit());

        String playerName; // TODO: set playerName to be the player name of the GamePlayer
        // copies the name of the current player
        this.setPlayerTurn(origState.getPlayerTurn());

        // copies player hands
        this.setPlayerHands(origState.getPlayerHands());
        // copies the draw pile and turns it all face down
        this.setDrawPile(origState.getDrawPile());
        this.drawPile.turnFaceDown();
        // copies the discard pile
        this.setDiscardPile(origState.getDiscardPile());
        this.turnDiscardPileFaceDown();
        // sets the currentSuit and currentFace to the top card
        this.setFace(origState.getDiscardPile().peekTopCard().getFace());
        this.setSuit(origState.getDiscardPile().peekTopCard().getSuit());

        // copies the name of players dependant on the type of player it is
        if(p instanceof ProxyPlayer) {
            ProxyPlayer proxyPlayer = (ProxyPlayer) p;
            // TODO: find a way to get a proxy player's name
            return;
        }
        else if(p instanceof GameComputerPlayer) {
            CrazyEightsComputerPlayer computerPlayer = (CrazyEightsComputerPlayer) p;
            playerName = computerPlayer.getName();
        }
        else {
            CrazyEightsHumanPlayer humanPlayer = (CrazyEightsHumanPlayer) p;
            playerName = humanPlayer.getName();
        }

        // censor all but GamePlayer p
        this.turnHandsOverExcept(playerName);
    }

    /**
     * Setter methods:
     */
    public void setPlayerTurn(String turn){ this.playerTurn = turn; }
    public void setPlayerNames(String[] playerNames){ this.playerNames = playerNames; }
    public void setPlayerIndex(int index){ this.playerIndex = index; }
    public void setPlayerHands(Hashtable<String,Deck> table){ this.playerHands = table; }
    public void setDrawPile(Deck deck){ this.drawPile = deck; }
    public void setDiscardPile(Deck deck){ this.discardPile = deck; }
    public void setSuit(String suit){ this.currentSuit = suit; }
    public void setFace(String face){ this.currentFace = face; }
    public void setHasDeclaredSuit(boolean declared){ this.hasDeclaredSuit = declared; }

    /**
     * Getter methods:
     */
    public String getPlayerTurn() { return this.playerTurn; }
    public String[] getPlayerNames() {
        String[] ret = new String[this.playerNames.length];
        System.arraycopy(this.playerNames, 0, ret, 0, this.playerNames.length);
        return ret;
    }
    public int getPlayerIndex() { return this.playerIndex; }
    public Hashtable<String, Deck> getPlayerHands() {
        Hashtable<String, Deck> ret = new Hashtable<String, Deck>(this.playerNames.length);
        for(String player : this.playerNames){
            ret.put(player, new Deck(this.playerHands.get(player)));
        }
        return ret;
    }
    public Deck getDrawPile() { return new Deck(this.drawPile); }
    public Deck getDiscardPile() { return new Deck(this.discardPile); }
    public String getCurrentFace() { return this.currentFace; }
    public String getCurrentSuit() { return this.currentSuit; }
    public boolean getHasDeclaredSuit() { return this.hasDeclaredSuit; }

    /**
     * movePlay(index, currPlayer)
     *
     * @param index - the index of the card to play
     * @param currPlayer - the name of the player making the move
     *
     * @return boolean - true if valid move
     *
     */
    public boolean movePlay(int index, String currPlayer){
        if(!currPlayer.equals(this.getPlayerTurn())) return false;
        this.playCard(index); // play the card
        this.checkToChangeSuit(); // check if the suit needs to be changed
        this.nextPlayer(); // move to next player
        return true;
    }

    /**
     * moveDraw(currPlayer)
     *
     * @param currPlayer - the name of the player making the move
     *
     * @return boolean - true if valid move
     */
    public boolean moveDraw(String currPlayer){
        if(!currPlayer.equals(this.getPlayerTurn())) return false;
        boolean canMove = false; // have a boolean if the player can move
        // while the player can't move and there are cards in the draw pile
        while(!canMove && this.getDrawPile().size() > 0) {
            this.drawCard();
            canMove = this.checkIfValid();
        }
        if(canMove) {
            this.playLastCard(); // if the player can move, play the last card, else pass
            this.checkToChangeSuit(); // check if the suit needs to be changed
        }
        this.nextPlayer(); // move to next player
        return true;
    }

    /**
     * turnDiscardPileFaceDown()
     *
     * Nullifies the cards in the deck
     * Turns them face-down so data of card is unknown
     */
    public void turnDiscardPileFaceDown() {
        if(!this.discardPile.isEmpty()) {
            // remove the top card in the deck
            // c will be null if the deck is empty
            Card c = this.discardPile.removeTopCard();

            // turn all of the cards in the deck face-down
            // if there are no cards this method call does nothing
            this.discardPile.turnFaceDown();

            // if the draw pile was not empty at the start
            // (i.e., Card c is not null),
            // add the top card back to the top of the deck
            this.discardPile.add(c);
        }
    }

    /**
     * turnHandOverExcept()
     *
     * Nullifies all player's hands except one
     *
     * @param noFlipPlayer
     */
    public void turnHandsOverExcept(String noFlipPlayer) {
        // for each key (player), turn their hands face-down unless it's the player
        for(String key : this.playerNames){
            if(!key.equals(noFlipPlayer) && this.playerHands.get(key) != null){
                Objects.requireNonNull(this.playerHands.get(key)).turnFaceDown();
            }
        }
    }

    /**
     * toString()
     *
     * Describes state of game as a string
     *
     * @return String - a String representing the state of the game
     */
    @NonNull
    @Override
    public String toString() {
        String s = "";

        // prints who's turn it is
        s += "It is " + this.playerTurn + "'s turn!\n\n";

        // prints hand of all players
        s += "All player hands:\n\n";
        for(String player : this.playerNames){
            s += "Player " + player + "'s hand: \n"
                    + this.getPlayerHands().get(player).toString() + "\n";
        }

        // prints played card (now is top of the deck)
        s += "Top Card of Discard Pile: " + this.getDiscardPile().peekTopCard().toString() + "\n\n";

        // prints the cards in the draw pile
        s += "Cards in Draw Pile: \n" + getDrawPile().toString() + "\n";

        // prints selected suit after 8 card is played
        if (this.discardPile.peekTopCard().face.equals("Eight")) {
            s += "Most recent card was an eight\nNew suit is: " + currentSuit + "\n";
        }

        // add extra newline
        s += "\n";

        return s;
    }

    /**
     * drawCard()
     *
     * Draws a card from the deck to the current player's hand
     *
     * @return boolean - true if a card can be drawn
     */
    public boolean drawCard() {
        // checks if the draw pile is empty
        if(drawPile.isEmpty()){
            // if it is, return false without doing anything
            return false;
        }else{
            Objects.requireNonNull(playerHands.get(playerTurn)).add(this.drawPile.removeTopCard());
            return true;
        }
    }

    /**
     * playCard()
     *
     * Plays a specific, indexed card from the current player's hand
     *
     * @param index - the index of the Card to play based on the current player's hand
     *
     * @return boolean - true it's a valid call
     */
    public boolean playCard(int index) {
        // check if the player's hand is empty
        if(playerHands.get(playerTurn) == null) return false;

        // check if the index given to the method is valid
        if(index < 0 || index >= Objects.requireNonNull(playerHands.get(playerTurn)).size()) return false;

        // remove the specified card in the player's hand and add it to the top of the discard pile
        discardPile.add(Objects.requireNonNull(playerHands.get(playerTurn)).removeSpecific(index));
        setSuit(this.discardPile.peekTopCard().getSuit());
        setFace(this.discardPile.peekTopCard().getFace());

        // set the hasDeclaredSuit boolean to false if top card is 8
        setHasDeclaredSuit(!(this.getDiscardPile().peekTopCard().getFace().equals("Eight")));

        // return true for valid move
        return true;
    }

    /**
     * playLastCard()
     *
     * Plays the most recently obtained card
     *
     * @return boolean - true if it's a valid call
     */
    public boolean playLastCard() {
        // check if the player's hand is empty
        if(playerHands.get(playerTurn) == null) return false;

        // play the card, calculating index in this method
        playCard(this.getPlayerHands().get(this.playerTurn).size() - 1);

        // set the hasDeclaredSuit boolean to false if top card is 8
        setHasDeclaredSuit(!(this.getCurrentFace().equals("Eight")));

        // return true - valid action
        return true;
    }

    /**
     * setSuitDueToEight()
     *
     * Sets the current suit if the card played was an eight
     *
     * @param newSuit - the new suit declared by the player
     *
     * @return boolean - false if the top card is not an eight
     */
    public boolean setSuitDueToEight(String newSuit) {
        // if the top card is not equal to eight, then do nothing and return false
        if(!discardPile.peekTopCard().face.equals("Eight")) return false;

        // if the top card is an eight, set the suit to the new suit
        this.setSuit(newSuit);

        // set hasDeclaredSuit boolean to true
        this.setHasDeclaredSuit(true);
        return true;
    }

    /**
     * checkToChangeSuit()
     *
     * Checks if the suit needs to be changed and change it based on the most frequent suit in hand
     *
     * @return boolean - true if new suit needs to be declared
     */
    public boolean checkToChangeSuit() {
        if(!this.getHasDeclaredSuit()){
            Deck currHand = this.getPlayerHands().get(this.getPlayerTurn());
            this.setSuitDueToEight(currHand.findMostSuits());
            return true;
        }else{
            return false;
        }
    }

    /**
     * nextPlayer()
     *
     * changes to the next player
     *
     * @return boolean - always true
     */
    public boolean nextPlayer(){
        // increment the player index and set the playerTurn variable to be the next player
        this.playerIndex = (this.playerIndex + 1)%(this.playerNames.length);
        this.playerTurn = this.playerNames[this.playerIndex];
        return true;
    }

    /**
     * checkIfValid()
     *
     * returns true if the player's hand has a valid card
     *
     * @return boolean - checks if any of the cards in current player's hands are valid
     */
    public boolean checkIfValid(){
        // retrieve the hand of the current player
        Deck currDeck = this.playerHands.get(this.playerTurn);

        // mock-up the top card of the discard pile (in case last suit was an 8)
        Card currCard = new Card(this.currentFace, this.currentSuit);

        // for every card in the current player's hands, check if it's an 8.
        // if the card is valid, return true
        // if no cards are valid, return false
        for (Card c : currDeck.cards) {
            if(c.isValid(currCard)){
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the game is over
     *
     * @return String - name of the winner, null if no winner
     */
    public String checkGameOver(){
        // loop through all players
        for(String p : this.getPlayerNames()){
            // if a player's hand is empty
            if(this.getPlayerHands().get(p).isEmpty()) return p;
        }
        return null;
    }

    /*
     * TODO: Other methods to implement (possibly):
     * - startGame
     * - exitGame
     * - restartGame
     * - undo
     * - pass
     */

}