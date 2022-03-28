package com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.Cards;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Deck
 *
 * A Deck object simply contains an ArrayList of cards and has methods to shuffle
 * and turn over cards (set to null objects), with other methods to help with the
 * manipulation of the cards within the "cards" ArrayList.
 *
 * @author Selena Li
 * @author Maliyah Miller
 * @author Jake Uyechi
 * @author Tyler Sakata
 *
 * @version 15 March 2022
 */
public class Deck implements Serializable {

    // initialize an ArrayList with a capacity of 52
    // the last element in this ArrayList is the top of the deck
    public final ArrayList<Card> cards = new ArrayList<>();

    /**
     * Default constructor; creates a blank Deck to hold cards
     */
    public Deck() {
        ArrayList<Card> cards = new ArrayList<>();
    }

    /**
     * Constructor using an ArrayList of Cards
     *
     * @param cards - the ArrayList of Cards to add to the Deck
     */
    public Deck(ArrayList<Card> cards){
        this.setCards(cards);
    }


    /**
     * Deep copy constructor
     *
     * @param orig - the original Deck to copy
     */
    public Deck(Deck orig) {
        for (Card c : orig.cards) {
            if(c == null){
                this.cards.add(null);
            }else{
                Card copy = new Card(c);
                this.cards.add(copy);
            }
        }
    }

    /**
     * Get the ArrayList of Cards from this Deck
     *
     * @return - ArrayList of Cards
     */
    public ArrayList<Card> getCards() {
        ArrayList<Card> ret = new ArrayList<Card>(this.size());
        for(Card c : this.cards){
            Card copy = new Card(c);
            ret.add(copy);
        }
        return ret;
    }

    /**
     * Set the ArrayList of Cards for this Deck
     *
     * @param orig - the ArrayList of Cards to set
     */
    public void setCards(ArrayList<Card> orig) {
        for(Card c : orig){
            if(c == null){
                this.cards.add(null);
            }else{
                Card copy = new Card(c);
                this.cards.add(copy);
            }
        }
    }

    /**
     * Add a standard, 52-card deck of playing cards
     */
    public void add52(){
        // suits
        for(char s : "SHDC".toCharArray()){
            // faces
            for(char f : "AKQJ098765432".toCharArray()){
                // construct a new card and add it to the deck
                Card newCard = new Card("" + s + f);
                cards.add(newCard);
            }
        }
    }

    /**
     * Shuffle this Deck
     */
    public void shuffle() {
        synchronized(this.cards){
            Collections.shuffle(this.cards);
        }
    }

    /**
     * Shuffle this Deck based on a seed
     * @param seed - the seed to set the Random class
     */
    public void shuffleSeed(int seed){
        synchronized(this.cards){
            Collections.shuffle(this.cards, new Random(seed));
        }
    }

    /**
     * Add a card to the top of the deck (last index)
     *
     * @param c - the Card to add
     */
    public void add(Card c){ synchronized(this.cards){ this.cards.add(c); } }

    /**
     * Get the size of the Deck
     *
     * @return - the amount of cards in this Deck
     */
    public int size(){ return this.cards.size(); }

    /**
     * Check if the Deck is empty
     *
     * @return - true if the Deck is empty, false otherwise
     */
    public boolean isEmpty(){ return this.size() <= 0; }

    /**
     * Remove a card from the top of the Deck, and return it
     *
     * @return - the removed Card
     */
    public Card removeTopCard(){
        synchronized (this.cards) {
            if (this.cards.isEmpty()) return null; // return null if it's empty
            return this.cards.remove(this.cards.size() - 1); // return the top card
        }
    }

    /**
     * Remove a card from the bottom of the Deck, and return it
     *
     * @return - the removed Card
     */
    public Card removeBottomCard(){
        synchronized (this.cards){
            if(this.cards.isEmpty()) return null; // return null if it's empty
            return this.cards.remove(0); // return the bottom card
        }
    }

    /**
     * Remove a specific card from the Deck
     *
     * @param index - the index of the Card to remove
     *
     * @return - the removed Card
     */
    public Card removeSpecific(int index){
        synchronized (this.cards){
            if(this.cards.isEmpty()) return null; // return null if it's empty
            return this.cards.remove(index); // return the indexed card
        }
    }

    /**
     * Remove all Cards from this Deck
     */
    public void emptyDeck(){
        synchronized (this.cards){
            this.setCards(new ArrayList<>()); // set the cards to a blank ArrayList
        }
    }

    /**
     * Peek at the Card on the top of the deck
     *
     * @return - a copy of the Card on the top of the Deck
     */
    public Card peekTopCard(){
        synchronized (this.cards) {
            if (this.cards.isEmpty()) return null; // return null if it's empty
            return new Card(this.cards.get(this.cards.size() - 1)); // return a copy of the top Card
        }
    }

    /**
     * Finds the most common suit in a Deck
     *
     * @return - a String of the most common suit
     */
    public String findMostSuits(){
        // spades - 0
        // hearts - 1
        // diamonds - 2
        // clubs - 3
        String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
        int[] counts = {0, 0, 0, 0};

        // categorize cards into suits and keep counts, iterating through all cards in deck
        for(Card c : this.cards){
            switch (c.getSuit()) {
                case "Spades": counts[0] = counts[0] + 1; break;
                case "Hearts": counts[1] = counts[1] + 1; break;
                case "Diamonds": counts[2] = counts[2] + 1; break;
                case "Clubs": counts[3] = counts[3] + 1; break;
            }
        }

        // find the max by looping through int array
        String ret = "Spades";
        int max = counts[0];
        for(int i = 0; i < counts.length; i++){
            if(counts[i] > max){
                ret = suits[i];
                max = counts[i];
            }
        }

        return ret;
    }

    /**
     * turn all cards in the deck face-down this will be used when making
     * copies of the GameState to send to players, since players will not
     * need to know what cards are in the deck. Does not modify the size
     * of the deck, but prevents players from seeing what's in it
     */
    public void turnFaceDown(){
        synchronized(this.cards) {
            for (int i = 0; i < cards.size(); i++) {
                cards.set(i, null);
            }
        }
    }

    /**
     * Append another Deck to this one
     *
     * @param d - the Deck to add
     */
    public void addDeck(Deck d){ this.cards.addAll(d.cards); }

    /**
     * toString()
     *
     * @return - a String representing this Deck
     */
    @NonNull
    public String toString(){

        // indicate which card is on the "top";
        // only matters for discard and draw piles
        String ret = "";

        // iterate backwards, since the top of the deck is treated as the last index
        // if the card is null (i.e., face-down or in another player's hand),
        // print out (hidden card) instead.
        synchronized (this.cards){
            for(int i = this.cards.size() - 1; i >= 0; i--){
                if(this.cards.get(i) != null){
                    ret += "[" + i + "] - " + this.cards.get(i).toString() + "\n";
                }else{
                    ret += "[" + i + "] - " + "(hidden card)\n";
                }
            }
        }

        // return string
        return ret;
    }
}
