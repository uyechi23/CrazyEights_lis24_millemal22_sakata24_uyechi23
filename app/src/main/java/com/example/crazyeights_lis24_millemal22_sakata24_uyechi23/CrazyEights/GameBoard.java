package com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.Cards.Card;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.Cards.Deck;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights.C8InfoMessage.C8GameState;
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
 * @version 29 March 2022
 */
public class GameBoard extends AnimationSurface {

    // Game state info
    C8GameState state = null;

    // string array of player names
    String[] playerNames = null;

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
    RectF slot1, slot2, slot3, slot4, drawSlot, discardSlot;

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


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // if state == null, do nothing
        if(this.state == null) return;

//        GameBoardController cont = new GameBoardController(this.gameBoard, this.state);
//
//        gameBoard.setOnTouchListener(cont);


        // initialize instance variables; cannot declare them above
        // since the getWidth() and getHeight() methods do not work
        // until the canvas is initialized
        initBoard();

        // draw slots
//        canvas.drawRect(slot1, slotPaint);
//        canvas.drawRect(slot2, slotPaint);
//        canvas.drawRect(slot3, slotPaint);
//        canvas.drawRect(slot4, slotPaint);

        // draw player names
        canvas.drawText(this.playerNames[0], slot1.centerX(),
                slot1.bottom - (int) (0.5 * fontSize), textPaint);
        canvas.drawText(this.playerNames[1], slot2.centerX(),
                slot2.bottom - (int) (0.5 * fontSize), textPaint);
        canvas.drawText(this.playerNames[2], slot3.centerX(),
                slot3.bottom - (int) (0.5 * fontSize), textPaint);
        canvas.drawText(this.playerNames[3], slot4.centerX(),
                slot4.bottom - (int) (0.5 * fontSize), textPaint);
        canvas.drawText("Draw", drawSlot.centerX(),
                drawSlot.bottom-(int)(0.5*fontSize), textPaint);
        canvas.drawText("Discard", discardSlot.centerX(),
                discardSlot.bottom-(int)(0.5*fontSize), textPaint);

        // draw player hands
        drawPlayerHand(canvas, slot1, state.getPlayerHands().get(0));
        drawPlayerHand(canvas, slot2, state.getPlayerHands().get(1));
        drawPlayerHand(canvas, slot3, state.getPlayerHands().get(2));
        drawPlayerHand(canvas, slot4, state.getPlayerHands().get(3));
        makeDrawPile(canvas, drawSlot, state.getDrawPile());
        drawDiscardPile(canvas, discardSlot, state.getDiscardPile());

       //drawCard(canvas, slot1, state.getDiscardPile().peekTopCard()); //this did not work at all
    }

    /**
     * Updates the C8GameState object with a new array of player names
     * NOTE: state must be updated using this method since GameState constructor doesn't use array
     *
     * @param state - the gamestate to update
     * @param playerNames - the string array of player names
     */
    public void updateMode(C8GameState state, String[] playerNames){
        this.state = state;
        this.playerNames = playerNames;
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

        discardSlot = new RectF((float) (boardWidth/3), (float) (boardHeight/3),
                (float) (((boardWidth/2))), (float) (2 * (boardHeight/3)));

        drawSlot = new RectF((float) (boardWidth/2), (float) (boardHeight/3),
                (float) ((boardWidth/3) * 2), (float) (2 * (boardHeight/3)));


        // slot paint
        slotPaint.setColor(Color.BLACK);

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
        // the card bitmap dimensions
        float cardSizeX = 140.0f;
        float cardSizeY = 190.0f;
        // scale factor to fit nicely into the slot (-0.3 for a little smaller than the slot)
        float scaleFactor = ((slot.bottom-slot.top)/cardSizeY) - 0.4f;
        //
        float delta = (float)(cardSizeX/2.0);
        // loop through from top to back, drawing each card offset a little
        for (int i = 0; i < playerDeck.size(); i++) {
            // determine the position of this card's top/left corner
            float left = (float) (slot.left + (delta*i));
            // draw a card into the appropriate rectangle (other player hand cards should be null)
            drawCard(g, scaledBy(new RectF(left + (delta/2), slot.top, left + cardSizeX + (delta/2),
                            slot.top+190.0f), scaleFactor),
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
     * draws the back of a card, that when tapped will act as a draw pile
     * won't appear if the draw pile is empty
     *
     * @param g
     *      the canvas object
     * @param slot
     *      a rectangle defining the location to draw the card
     * @param pile
     *      checks if the deck is empty and if not,
     *      it draws the back of a card
     */
    private static void makeDrawPile(Canvas g, RectF slot, Deck pile){
        if (pile.isEmpty()){
            return;
        }

        float cardSizeX = 140.0f;
        float cardSizeY = 190.0f;
        // scale factor to fit nicely into the slot (-0.3 for a little smaller than the slot)
        float scaleFactor = ((slot.bottom-slot.top)/cardSizeY) - 0.4f;
        //
        float delta = (float)(cardSizeX/2.0);
        float left = slot.left;

        drawCard(g, scaledBy(new RectF(left+delta, slot.top, left + cardSizeX + delta,
                slot.top+190.0f), scaleFactor), null);
    }

    /**
     * draws the top card of the discard pile
     * puts an error message in the log if the discard
     * pile is empty which shouldn't happen
     *
     * @param g
     * @param slot
     * @param pile
     */
    private static void drawDiscardPile(Canvas g, RectF slot, Deck pile){
        if (pile.isEmpty()){
            Log.d("error", "Discard Pile null");
        }
        float cardSizeX = 140.0f;
        float cardSizeY = 190.0f;
        // scale factor to fit nicely into the slot (-0.3 for a little smaller than the slot)
        float scaleFactor = ((slot.bottom-slot.top)/cardSizeY) - 0.4f;
        //
        float delta = (float)(cardSizeX/2.0);
        float left = slot.left;

        drawCard(g, scaledBy(new RectF(left+delta, slot.top, left + cardSizeX + delta,
                        slot.top+190.0f), scaleFactor),
                pile.peekTopCard());
    }

    /**
     * scales a rectangle, moving all edges with respect to its left corner
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

        // scale each side; move back so that top left is in original location
        left = left + midX;
        right = right*factor*1.5f + midX;
        top = top + midY;
        bottom = bottom*factor*1.5f + midY;

        // create/return the new rectangle
        return new RectF(left, top, right, bottom);
    }

    public RectF getDrawSlot(){ return drawSlot; }
    public RectF getSlot1(){ return slot1; }

}