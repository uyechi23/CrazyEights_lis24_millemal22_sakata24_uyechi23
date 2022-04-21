package C8.CrazyEights.C8InfoMessage;

import android.util.Log;

import androidx.annotation.NonNull;

import C8.Cards.Card;
import C8.Cards.Deck;
import C8.GameFramework.infoMessage.GameState;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Objects;
import java.util.Random;

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
 * @version 20 April 2022
 */
public class C8GameState extends GameState implements Serializable {
    /* Instance variables */
    private int numPlayers; // number of players
    private int playerIndex; // ID number of the player whose turn it is
    private Hashtable<Integer, Deck> playerHands; // all players hands
    private Deck drawPile; // cards to be drawn from
    private Deck discardPile; // cards that were discarded
    private String currentSuit; // top card current suit
    private String currentFace; // top card current face
    private boolean hasDeclaredSuit; // false if 8 has been played without a declared suit

    /**
     * CrazyEightsGameState constructor
     *
     * Initializes the instance variables for start of game
     */
    public C8GameState(int numPlayers) {
        // set number of players
        this.numPlayers = numPlayers;

        // set the hasDeclaredSuit variable
        this.hasDeclaredSuit = true;

        // randomly choose a first turn
        Random rand = new Random();
        this.playerIndex = rand.nextInt(this.numPlayers);

        // create a hashtable of player hands
        // given an array of players, iterate through each and make an entry in the hashtable
        this.playerHands = new Hashtable<>();
        for (int i = 0; i < this.numPlayers; i++) {
            this.playerHands.put(i, new Deck());
        }

        // create the draw pile as a new deck, add 52 cards, and shuffle
        this.drawPile = new Deck();
        this.drawPile.add52();
        this.drawPile.shuffle();

        // create an empty discard pile and add a card to it
        this.discardPile = new Deck();
        Card topCard = this.drawPile.removeTopCard();
        while (topCard.getValue() == 8) {
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
        if (playerHands.size() <= 4) {
            perPlayer = 7;
        } else {
            perPlayer = 5;
        }
        for (int player : playerHands.keySet()) {
            for (int i = 0; i < perPlayer; i++) {
                if (this.drawPile != null && this.playerHands.get(player) != null) {
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
     * @param randSeed - a seed to change the shuffling pattern of the deck
     */
    public C8GameState(int numPlayers, int randSeed) {
        // set number of players
        this.numPlayers = numPlayers;

        // set the hasDeclaredSuit variable
        this.hasDeclaredSuit = true;

        // randomly choose a first turn based on seed
        Random rand = new Random(randSeed);
        this.playerIndex = rand.nextInt(this.numPlayers);

        // create a hashtable of player hands
        // given an array of players, iterate through each and make an entry in the hashtable
        this.playerHands = new Hashtable<>();
        for (int i = 0; i < this.numPlayers; i++) {
            this.playerHands.put(i, new Deck());
        }

        // create the draw pile as a new deck, add 52 cards, and shuffle
        this.drawPile = new Deck();
        this.drawPile.add52();
        this.drawPile.shuffleSeed(randSeed);

        // create an empty discard pile and add a card to it
        this.discardPile = new Deck();
        Card topCard = this.drawPile.removeTopCard();
        while (topCard.getValue() == 8) {
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
        if (playerHands.size() <= 4) {
            perPlayer = 7;
        } else {
            perPlayer = 5;
        }
        for (int player : playerHands.keySet()) {
            for (int i = 0; i < perPlayer; i++) {
                if (this.drawPile != null) {
                    this.playerHands.get(player).add(this.drawPile.removeTopCard());
                }
            }
        }
    }

    /**
     * Copy Constructor
     *
     * Makes an uncensored copy, NOT for players
     *
     * @param origState - the original CrazyEightsGameState
     */
    public C8GameState(C8GameState origState) {
        // copy number of players in this game state
        this.setNumPlayers(origState.getNumPlayers());

        // set the hasDeclaredSuit variable
        this.setHasDeclaredSuit(origState.getHasDeclaredSuit());

        // copies the name of the current player
        this.setPlayerIndex(origState.getPlayerIndex());

        // copies player hands
        this.setPlayerHands(origState.getPlayerHands());
        // copies the draw pile and turns it all face down
        this.setDrawPile(origState.getDrawPile());
        // copies the discard pile
        this.setDiscardPile(origState.getDiscardPile());
        this.turnDiscardPileFaceDown();
        // sets the currentSuit and currentFace to the top card
        this.setFace(origState.getDiscardPile().peekTopCard().getFace());
        this.setSuit(origState.getDiscardPile().peekTopCard().getSuit());
    }

    /**
     * Copy Constructor
     *
     * Makes a censored copy for players
     *
     * @param origState - the original CrazyEightsGameState
     * @param playerPerspective - the player for which to censor data
     */
    public C8GameState(C8GameState origState, int playerPerspective) {
        // copy number of players in this game state
        // deep not needed
        this.setNumPlayers(origState.getNumPlayers());

        // set the hasDeclaredSuit variable
        // deep not needed
        this.setHasDeclaredSuit(origState.getHasDeclaredSuit());

        // copies the name of the current player
        // deep not needed
        this.setPlayerIndex(origState.getPlayerIndex());

        // copies player hands
        // deep needed
        Hashtable<Integer, Deck> newHands = new Hashtable<>();
        // makes a deep copy of each hand and puts it into the new player hand copy
        for (Integer key : origState.getPlayerHands().keySet()) {
            Deck newHand = new Deck(origState.getPlayerHands().get(key));
            newHands.put(key, newHand);
        }
        this.setPlayerHands(newHands);

        // copies the draw pile and turns it all face down
        // deep needed
        Deck newDraw = new Deck(origState.getDrawPile());
        newDraw.turnFaceDown();
        this.setDrawPile(newDraw);

        // copies the discard pile
        // deep needed
        Deck newDiscard = new Deck(origState.getDiscardPile());
        this.setDiscardPile(newDiscard);

        // sets the currentSuit and currentFace to the original
        this.setFace(origState.getCurrentFace());
        this.setSuit(origState.getCurrentSuit());

//        // copies the name of players dependant on the type of player it is
//        if (p instanceof ProxyPlayer) {
//            ProxyPlayer proxyPlayer = (ProxyPlayer) p;
//            // TODO: find a way to get a proxy player's name
//            return;
//        } else if (p instanceof GameComputerPlayer) {
//            C8ComputerPlayer computerPlayer = (C8ComputerPlayer) p;
//            playerName = computerPlayer.getName();
//        } else {
//            C8HumanPlayer humanPlayer = (C8HumanPlayer) p;
//            playerName = humanPlayer.getName();
//        }
//
        // censor all but GamePlayer p
        this.turnHandsOverExcept(playerPerspective);
    }

    /**
     * movePlay(index, currPlayer)
     *
     * @param index      - the index of the card to play
     * @return boolean - true if move made
     */
    public boolean movePlay(int index) {
        // get the card played
        Card play = this.getPlayerHands().get(this.getPlayerIndex()).getCards().get(index);
        // check for valid card
        if(!checkValid(play)) return false;
        this.playCard(index); // play the card
        if(this.checkToChangeSuit()) { // check if the suit needs to be changed
            return true; // return here because we dont want to move to next player
        }
        this.nextPlayer(); // move to next player
        return true;
    }

    /**
     * checkValid(index, currPlayer)
     *
     * @param check      - the card to check if valid play
     * @return boolean - true if valid move
     */
    public boolean checkValid(Card check) {
        if(this.getCurrentSuit().equals(check.getSuit())
                || this.getCurrentFace().equals(check.getFace())
                || check.getFace().equals("Eight")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * turnDiscardPileFaceDown()
     *
     * Nullifies the cards in the deck
     * Turns them face-down so data of card is unknown
     */
    public void turnDiscardPileFaceDown() {
        if (!this.discardPile.isEmpty()) {
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
    public void turnHandsOverExcept(int noFlipPlayer) {
        // for each key (player), turn their hands face-down unless it's the player
        for (int key : this.playerHands.keySet()) {
            if (!(key == noFlipPlayer) && this.playerHands.get(key) != null) {
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
        s += "It is Player " + this.playerIndex + "'s turn!\n\n";

        // prints hand of all players
        s += "All player hands:\n\n";
        for (int player : this.playerHands.keySet()) {
            s += "Player " + player + "'s hand: \n"
                    + this.getPlayerHands().get(player).toString() + "\n";
        }

        // prints played card (now is top of the deck)
        s += "Top Card of Discard Pile: " + this.getDiscardPile().peekTopCard().toString() + "\n\n";

        s += "Actual suit (if declared 8): " + this.getCurrentSuit() + "\n\n";

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
        if (drawPile.isEmpty()) {
            // if it is, return false without doing anything
            return false;
        } else {
            playerHands.get(this.playerIndex).add(this.drawPile.removeTopCard());
            return true;
        }
    }

    /**
     * playCard()
     *
     * Plays a specific, indexed card from the current player's hand
     *
     * @param index - the index of the Card to play based on the current player's hand
     * @return boolean - true it's a valid call
     */
    public boolean playCard(int index) {
        // check if the player's hand is empty
        if (playerHands.get(this.playerIndex) == null) return false;

        // check if the index given to the method is valid
        if (index < 0 || index >= Objects.requireNonNull(playerHands.get(this.playerIndex)).size())
            return false;

        // remove the specified card in the player's hand and add it to the top of the discard pile
        discardPile.add(Objects.requireNonNull(playerHands.get(this.playerIndex)).removeSpecific(index));
        setSuit(this.discardPile.peekTopCard().getSuit());
        setFace(this.discardPile.peekTopCard().getFace());

        // return true for valid move
        return true;
    }

    /**
     * setSuitDueToEight()
     *
     * Sets the current suit if the card played was an eight
     *
     * @param newSuit - the new suit declared by the player
     * @return boolean - false if the top card is not an eight
     */
    public boolean setSuitDueToEight(String newSuit) {
        // if the top card is not equal to eight, then do nothing and return false
        if (!discardPile.peekTopCard().face.equals("Eight")) return false;

        // if the top card is an eight, set the suit to the new suit
        this.setSuit(newSuit);

        // set hasDeclaredSuit boolean to true
        this.setHasDeclaredSuit(true);

        // next player
        this.nextPlayer();
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
        if (this.getDiscardPile().peekTopCard().getFace().equals("Eight")) {
            this.setHasDeclaredSuit(false);
            return true;
        } else {
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
    public boolean nextPlayer() {
        // increment the player index and set the playerTurn variable to be the next player
        this.playerIndex = (this.playerIndex + 1) % (this.playerHands.size());
        return true;
    }

    /**
     * skipTurn()
     *
     * skips player's turn if there are no valid moves in their hand
     * and if the draw deck is empty
     *
     * @return boolean
     */
    public boolean skipTurn() {
        // retrieve the hand of the current player
        Deck currDeck = this.playerHands.get(this.getPlayerIndex());

        // mock-up the top card of the discard pile (in case last suit was an 8)
        Card currCard = new Card(this.currentFace, this.currentSuit);

        // counter for the number of valid cards in player's hand
        int validCards = 0;

        // for every card in the current player's hands, check if it is valid.
        // if the card is valid, increment validCards and set skip to false
        if(currCard.getFace().equals("Eight")){
            for(Card c : currDeck.getCards()){
                if(c.matchSuit(currCard) || c.getFace().equals("Eight")){
                    validCards++;
                }
            }
        }else{
            for(Card c : currDeck.getCards()) {
                if(c.isValid(currCard)) {
                    validCards++;
                }
            }
        }
        // if there are no valid cards, skip player's turn then return skip
        if (validCards == 0) {
            //skip turn
            this.nextPlayer();
            return true;
        }else{
            return false;
        }
    }

    /**
     * checkIfValid()
     *
     * returns true if the player's hand has a valid card
     *
     * @return boolean - checks if any of the cards in current player's hands are valid
     */
    public boolean checkIfValid(int playerNum){
        // retrieve the hand of the player indexed
        Deck currDeck = this.playerHands.get(playerNum);

        // for every card in the current player's hands, check if it's an 8.
        // if the card is valid, return true
        // if no cards are valid, return false
        for (Card c : currDeck.cards) {
            if(c.getFace().equals("Eight")){
                return true;
            }
            if(c.getFace().equals(this.getCurrentFace()) || c.getSuit().equals(this.getCurrentSuit())){
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
        for(int p : this.getPlayerHands().keySet()){
            // if a player's hand is empty
            if(this.getPlayerHands().get(p).isEmpty()) {
                return "Player " + p + " wins! ";
            }
        }
        return null;
    }

    /**
     * calculates the scores of the players
     *
     * @return array of scores
     */
    public int[] sendScore(){
        int potScore = 0;
        int[] playerScores = new int[this.numPlayers];
        int handValue = 0;
        int winner = 12;

        // finds which player won
        for(int p : this.getPlayerHands().keySet()) {
            // if a player's hand is empty
            if (this.getPlayerHands().get(p).isEmpty()) {
                winner = p;
            }
        }

            // Finds the total value of all cards left in play
        for (int i = 0; i < this.numPlayers; i++) {

            // loops through each player's hand
            for (Card c : this.getPlayerHands().get(i).cards) {

                if (c == null) break;

                if (c.getFace() == "King" || c.getFace() == "Queen" ||
                        c.getFace() == "Jack" || c.getFace() == "Ace") {
                        potScore += 10;
                }
                else if (c.getFace() == "Eight") potScore += 50;
                else {
                    potScore += c.getValue();
                }
            }
        }

        //calculates an individual player's score and prints it
        for (int j = 0; j < this.numPlayers; j++) {
            handValue = 0;
            if (j == winner) {
                playerScores[j] = potScore;
            }
            else {
                for (Card card : this.getPlayerHands().get(j).cards) {

                    if (card == null) break;

                    if (card.getFace() == "King" || card.getFace() == "Queen" ||
                            card.getFace() == "Jack" || card.getFace() == "Ace") {
                        handValue += 10;
                    }
                    else if (card.getFace() == "Eight") handValue += 50;
                    else {
                        handValue += card.getValue();
                    }
                }
                playerScores[j] = potScore - handValue;
            }


        }
        return playerScores;
    }

    /**
     * Setter methods:
     */
    public void setNumPlayers(int num){
        this.numPlayers = num;
    }

    public void setPlayerIndex(int index) {
        this.playerIndex = index;
    }

    public void setPlayerHands(Hashtable<Integer, Deck> table) {
        this.playerHands = table;
    }

    public void setDrawPile(Deck deck) {
        this.drawPile = deck;
    }

    public void setDiscardPile(Deck deck) {
        this.discardPile = deck;
    }

    public void setSuit(String suit) {
        this.currentSuit = suit;
    }

    public void setFace(String face) {
        this.currentFace = face;
    }

    public void setHasDeclaredSuit(boolean declared) {
        this.hasDeclaredSuit = declared;
    }

    /**
     * Getter methods:
     */
    public int getNumPlayers(){
        return this.numPlayers;
    }

    public int getPlayerIndex() {
        return this.playerIndex;
    }

    public Deck getDrawPile() {
        return new Deck(this.drawPile);
    }

    public Deck getDiscardPile() {
        return new Deck(this.discardPile);
    }

    public String getCurrentFace() {
        return this.currentFace;
    }

    public String getCurrentSuit() {
        return this.currentSuit;
    }

    public Hashtable<Integer, Deck> getPlayerHands() {
        return this.playerHands;
    }

    public boolean getHasDeclaredSuit() {
        return this.hasDeclaredSuit;
    }

    /*
     * TODO: Other methods to implement (possibly):
     * - startGame
     * - exitGame
     * - restartGame
     */

}