package com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceView;

import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.GameFramework.animation.AnimationSurface;

/**
 * GameBoard
 *
 * A GameBoard object is an extension of SurfaceView class that allows the game to
 * be drawn on the screen.
 *
 * @author Selena Li
 * @author Maliyah Miller
 * @author Jake Uyechi
 * @author Tyler Sakata
 *
 * @version 21 March 2022
 */
public class GameBoard extends AnimationSurface {

    // Game state info
    C8GameState state;

    //Game board dimensions
    float boardWidth; //Need to get from xml file maybe start the game with a popup window then                                                        4
    float boardHeight; //the surface view can be created, and onDraw can be invalidated then the getters might work

    /**
     * slot dimensions
     *
     * slot1: current player
     * slot2: left of current player
     * slot3: above current player
     * slot4: right of current player
     */

    // slot dimensions
    RectF slot1;
    RectF slot2;
    RectF slot3;
    RectF slot4;

    // text size
    float fontSize = 30.0f;

    // paints
    Paint slotPaint = new Paint();
    Paint textPaint = new Paint();

    public GameBoard(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public GameBoard(Context context, AttributeSet attrs){
        super(context, attrs);
        setWillNotDraw(false);
    }

    /**
     * Draws the outline of the cards of whoever's turn it is
     * Needs the amount of cards as a parameter so it can allot
     * the spacing using the slot width
     *
     * How many cards per row, or do we want them stacked on top of each other?
     * cards per row- may need to resize the cards at some point
     * stacked- will have to make it so the one they tap is fully visible (might cover other cards)
     */
    public void drawCurrentPlayerCard(Canvas canvas /* , float amount of cards in hand*/){


    }

    /**
     * draws the back of the other players' cards
     * uses the amount of cards for spacing
     * will need to receive the slot number somehow since the players will be rotating slots
     * maybe have the slot number in the Player object so when the turn is changed it changes
     * the slot number, and then the slot number can be referenced here
     */
    public void drawOtherPlayerCards(Canvas canvas /* , float amount of cards in hand , player *so i can get the slot number* */){


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // initialize instance variables; cannot declare them above
        // since the getWidth() and getHeight() methods do not work
        // until the canvas is initialized
        initBoard();

        // draw slots
        canvas.drawRect(slot1, slotPaint);
        canvas.drawRect(slot2, slotPaint);
        canvas.drawRect(slot3, slotPaint);
        canvas.drawRect(slot4, slotPaint);

        // draw player names
        canvas.drawText("Player 1", slot1.centerX(),
                slot1.bottom - (int) (0.5 * fontSize), textPaint);
        canvas.drawText("Player 2", slot2.centerX(),
                slot2.bottom - (int) (0.5 * fontSize), textPaint);
        canvas.drawText("Player 3", slot3.centerX(),
                slot3.bottom - (int) (0.5 * fontSize), textPaint);
        canvas.drawText("Player 4", slot4.centerX(),
                slot4.bottom - (int) (0.5 * fontSize), textPaint);
        if(state != null) {
            drawPlayerHand(canvas, slot1, state.getPlayerHands().get(state.getPlayerNames()[0]));
        }
    }

    public void initBoard(){
        //Game board dimensions
        this.boardWidth = getWidth();
        this.boardHeight = getHeight();

        /*
         * slot dimensions
         *
         * slot1: current player
         * slot2: left of current player
         * slot3: above current player
         * slot4: right of current player
         */
        slot1 = new RectF((float) (boardWidth/3), (float) (2 * (boardHeight/3)),
                (float) ((boardWidth/3) * 2), (float) boardHeight);
        slot2 = new RectF(0, (float) (boardHeight/3),
                (float) (boardWidth/3), (float) (2 * (boardHeight/3)));
        slot3 = new RectF((float) (boardWidth/3), 0,
                (float) ((boardWidth/3) * 2), (float) (boardHeight/3));
        slot4 = new RectF((float) ((boardWidth/3) * 2), (float) (boardHeight/3),
                (float) boardWidth, (float) (2 * (boardHeight/3)));


        // slot paint
        slotPaint.setColor(Color.GREEN);

        // text paint
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(fontSize);
        textPaint.setTextAlign(Paint.Align.CENTER);

    }

    /**
     * draws a sequence of cards, each offset a bit from the previous one, so that all can be
     * seen
     *
     * @param g
     * 		the canvas to draw on
     * @param slot
     * 		the rectangle that defines the slot to draw all cards in
     * @param playerDeck
     * 		the hand of the player to draw
     */
    private void drawPlayerHand(Canvas g, RectF slot, Deck playerDeck) {
        float cardSize = 140.0f;
        // loop through from back to front, drawing a card in each location
        for (int i = playerDeck.size()-1; i >= 0; i--) {
            // determine the position of this card's top/left corner
            float left = (float) ((slot.centerX() - playerDeck.size()*(cardSize/2.0)) + (cardSize/2.0));
            float top = slot.top;
            // draw a card into the appropriate rectangle (other player hand cards should be null)
            drawCard(g,
                    new RectF(left, top, left + slot.width(), top + slot.height()),
                    playerDeck.getCards().get(i));
        }
    }

    /**
     * draws a card on the canvas; if the card is null, draw a card-back
     *
     * @param g
     * 		the canvas object
     * @param rect
     * 		a rectangle defining the location to draw the card
     * @param c
     * 		the card to draw; if null, a card-back is drawn
     */
    private static void drawCard(Canvas g, RectF rect, Card c) {
        if (c == null) {
            // draw card back
            Card.drawBack(g, rect, "B0");
        }
        else {
            // just draw the card
            c.drawOn(g, rect);
        }
    }

    /**
     * scales a rectangle, moving all edges with respect to its center
     *
     * @param rect
     * 		the original rectangle
     * @param factor
     * 		the scaling factor
     * @return
     * 		the scaled rectangle
     */
    private static RectF scaledBy(RectF rect, float factor) {
        // compute the edge locations of the original rectangle, but with
        // the middle of the rectangle moved to the origin
        float midX = (rect.left+rect.right)/2;
        float midY = (rect.top+rect.bottom)/2;
        float left = rect.left-midX;
        float right = rect.right-midX;
        float top = rect.top-midY;
        float bottom = rect.bottom-midY;

        // scale each side; move back so that center is in original location
        left = left*factor + midX;
        right = right*factor + midX;
        top = top*factor + midY;
        bottom = bottom*factor + midY;

        // create/return the new rectangle
        return new RectF(left, top, right, bottom);
    }
}