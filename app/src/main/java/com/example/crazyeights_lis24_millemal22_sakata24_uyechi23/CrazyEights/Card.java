package com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.R;

/**
 * Card
 *
 * A Card object that has values and faces. Includes compare methods and
 * a copy constructor. Also includes the ability to draw itself which will be
 * implemented in the future.
 *
 * @author Selena Li
 * @author Maliyah Miller
 * @author Jake Uyechi
 * @author Tyler Sakata
 *
 * @version 15 March 2022
 */
public class Card {

    // instance variables of card
    // suit - the suit of the card
    // face - the face value of the card
    // value - the point value of the card (used to calculate post-game scores)
    public String suit;
    public String face;
    public int value;

    // array that contains the android resource indices for the 52 card
    // images
    private int[][] resIdx = {
            {
                    R.drawable.card_clubs_a, R.drawable.card_clubs_2, R.drawable.card_clubs_3,
                    R.drawable.card_clubs_4, R.drawable.card_clubs_5, R.drawable.card_clubs_6,
                    R.drawable.card_clubs_7, R.drawable.card_clubs_8, R.drawable.card_clubs_9,
                    R.drawable.card_clubs_0, R.drawable.card_clubs_j, R.drawable.card_clubs_q,
                    R.drawable.card_clubs_k,
            },
            {
                    R.drawable.card_diamonds_a, R.drawable.card_diamonds_2, R.drawable.card_diamonds_3,
                    R.drawable.card_diamonds_4, R.drawable.card_diamonds_5, R.drawable.card_diamonds_6,
                    R.drawable.card_diamonds_7, R.drawable.card_diamonds_8, R.drawable.card_diamonds_9,
                    R.drawable.card_diamonds_0, R.drawable.card_diamonds_j, R.drawable.card_diamonds_q,
                    R.drawable.card_diamonds_k,
            },
            {
                    R.drawable.card_hearts_a, R.drawable.card_hearts_2, R.drawable.card_hearts_3,
                    R.drawable.card_hearts_4, R.drawable.card_hearts_5, R.drawable.card_hearts_6,
                    R.drawable.card_hearts_7, R.drawable.card_hearts_8, R.drawable.card_hearts_9,
                    R.drawable.card_hearts_0, R.drawable.card_hearts_j, R.drawable.card_hearts_q,
                    R.drawable.card_hearts_k,
            },
            {
                    R.drawable.card_spades_a, R.drawable.card_spades_2, R.drawable.card_spades_3,
                    R.drawable.card_spades_4, R.drawable.card_spades_5, R.drawable.card_spades_6,
                    R.drawable.card_spades_7, R.drawable.card_spades_8, R.drawable.card_spades_9,
                    R.drawable.card_spades_0, R.drawable.card_spades_j, R.drawable.card_spades_q,
                    R.drawable.card_spades_k,
            },
    };

    // the array of card images
    private Bitmap[][] cardImages = null;


    /**
     * Default Card constructor using two chars as a String
     *
     * @param chars - expected to be a 2-character String, the first
     *              character representing the suit and the second
     *              character representing the face value
     */
    public Card(String chars){

        // check that chars are valid
        if(chars == null || chars.length() != 2) return;

        // this should be a String with two characters
        String[] toks = chars.split("");

        // first character is the suit
        switch(toks[0]){
            case "S": this.suit = "Spades"; break;
            case "H": this.suit = "Hearts"; break;
            case "D": this.suit = "Diamonds"; break;
            case "C": this.suit = "Clubs"; break;
            default: return;
        }

        // second character is the face
        switch(toks[1]){
            case "A": this.face = "Ace"; break;
            case "K": this.face = "King"; break;
            case "Q": this.face = "Queen"; break;
            case "J": this.face = "Jack"; break;
            case "0": this.face = "Ten"; break;
            case "9": this.face = "Nine"; break;
            case "8": this.face = "Eight"; break;
            case "7": this.face = "Seven"; break;
            case "6": this.face = "Six"; break;
            case "5": this.face = "Five"; break;
            case "4": this.face = "Four"; break;
            case "3": this.face = "Three"; break;
            case "2": this.face = "Two"; break;
            default: return;
        }

        // calculate value based on face
        setValue(this.face);
    }

    /**
     * Default Card constructor using face and suit parameters
     *
     * @param face - the face of the card as a String
     * @param suit - the suit of the card as a String
     */
    public Card(String face, String suit) {
        // assign cards
        this.suit = suit;
        this.face = face;

        // calculate card value (an integer representing the face value of the card)
        setValue(face);
    }

    /**
     * Deep copy constructor fot a Card
     *
     * @param orig - the Card to copy
     */
    public Card(Card orig) {
        this.face = orig.getFace();
        this.suit = orig.getSuit();
        this.resIdx = orig.getResIdx();
        this.cardImages = orig.getCardImages();
        setValue(this.face);
    }

    /**
     * Gets the value of the card
     *
     * @return - the card's value (an int)
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Gets the face of the card
     *
     * @return - the card's face value (a String)
     */
    public String getFace() {
        return this.face;
    }

    /**
     * Gets the suit of the card
     *
     * @return - the card's suit (a String)
     */
    public String getSuit() {
        return this.suit;
    }

    /**
     * Gets the resource array for card resource IDs
     *
     * @return - a 2D array containing resource IDs
     */
    public int[][] getResIdx() {
        if(this.resIdx == null) return null;
        return this.resIdx.clone();
    }

    /**
     * Gets the resource array for card Bitmap images
     *
     * @return - a 2D array containing Bitmaps
     */
    public Bitmap[][] getCardImages() {
        if(this.cardImages == null) return null;
        return this.cardImages.clone();
    }

    /**
     * Sets the value of the card based on it's face value
     *
     * @param face - the face of the card as a String
     */
    public void setValue(String face){
        switch (face) {
            case "Ace": this.value = 1; break;
            case "King": // same as queen
            case "Queen": // same as jack
            case "Jack": // same as 10
            case "Ten": this.value = 10; break;
            case "Nine": this.value = 9; break;
            case "Eight": this.value = 8; break;
            case "Seven": this.value = 7; break;
            case "Six": this.value = 6; break;
            case "Five": this.value = 5; break;
            case "Four": this.value = 4; break;
            case "Three": this.value = 3; break;
            case "Two": this.value = 2; break;
            default: this.value = 0; break;
        }
    }

    /**
     * Compare the suit
     * Usage: card1.matchSuit(card2);
     *
     * @param compare - the Card object to compare this object to
     *
     * @return - a boolean; true if the Cards match suits, false otherwise
     */
    public boolean matchSuit(Card compare) {
        return (compare.suit.equals(this.suit));
    }

    /**
     * Compare the face
     * Usage: card1.matchFace(card2);
     *
     * @param compare - the Card objet to compare this object to
     *
     * @return - a boolean; true if the Cards match faces, false otherwise
     */
    public boolean matchFace(Card compare) {
        return (compare.face.equals(this.face));
    }

    /**
     * Compares two cards to see if it's a valid play
     * Usage: discardCard.isValid(handCard);
     *
     * @param compare - the card in the player's hand
     *               to be played on the discard pile
     *
     * @return - true if parameter compare is a valid card
     */
    public boolean isValid(Card compare){
        // if card is an 8, only check if suit matches
        // if card is anything else, check if either suit or face matches
        if(compare.getFace().equals("Eight")){
            return matchSuit(compare);
        }else{
            return matchSuit(compare) || matchFace(compare);
        }
    }

    /**
     * toString()
     *
     * @return - a String representing this Card
     */
    @NonNull
    public String toString() { return "" + this.face + " of " + this.suit; }

    /*
    External Citation:
        Date:       21 February 2022
        Problem:    Needed to draw the card on a SurfaceView instead of using ImageView
        Resource:   GameFramework Card.java class includes the methods below
        Solution:   Used code from the Card.java class in the SlapJack GameFramework example
     */

    /**
     * Draws the card on a Graphics object.  The card is drawn as a
     * white card with a black border.  If the card's rank is numerih, the
     * appropriate number of spots is drawn.  Otherwise the appropriate
     * picture (e.g., of a queen) is included in the card's drawing.
     *
     * @param g - the graphics object on which to draw
     * @param where - a rectangle that tells where the card should be drawn
     */
    public void drawOn(Canvas g, RectF where) {
        // create the paint object
        Paint p = new Paint();
        p.setColor(Color.BLACK);

        // get the bitmap for the card
        int firstIndex = -1;
        switch(this.getSuit()){
            case "Clubs": firstIndex = 0; break;
            case "Diamonds": firstIndex = 1; break;
            case "Hearts": firstIndex = 2; break;
            case "Spades": firstIndex = 3; break;
        }

        int secondIndex = -1;
        switch(this.getFace()){
            case "Ace": secondIndex = 0; break;
            case "Two": secondIndex = 1; break;
            case "Three": secondIndex = 2; break;
            case "Four": secondIndex = 3; break;
            case "Five": secondIndex = 4; break;
            case "Six": secondIndex = 5; break;
            case "Seven": secondIndex = 6; break;
            case "Eight": secondIndex = 7; break;
            case "Nine": secondIndex = 8; break;
            case "Ten": secondIndex = 9; break;
            case "Jack": secondIndex = 10; break;
            case "Queen": secondIndex = 11; break;
            case "King": secondIndex = 12; break;
        }

        // I think if this is here, we can cycle through each card in the deck, mark it
        // as valid or invalid, then the drawing class will gray it out if it's invalid.
        boolean validPlay = true;

        Bitmap bitmap = cardImages[firstIndex][secondIndex];

        // create the source rectangle
        Rect r = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());

        // draw the bitmap into the target rectangle
        g.drawBitmap(bitmap, r, where, p);
    }

    /**
     * initializes the card images
     *
     * @param activity - the current activity
     */
    public void initImages(Activity activity) {
        // if it's already initialized, then ignore
        if (this.cardImages != null) return;

        // create the outer array
        this.cardImages = new Bitmap[resIdx.length][];

        // loop through the resource-index array, creating a
        // "parallel" array with the images themselves
        for (int i = 0; i < resIdx.length; i++) {
            // create an inner array
            this.cardImages[i] = new Bitmap[this.resIdx[i].length];
            for (int j = 0; j < resIdx[i].length; j++) {
                // create the bitmap from the corresponding image
                // resource, and set the corresponding array element
                this.cardImages[i][j] =
                        BitmapFactory.decodeResource(
                                activity.getResources(),
                                this.resIdx[i][j]);
            }
        }
    }
}
