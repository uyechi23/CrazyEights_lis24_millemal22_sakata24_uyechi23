package C8.CrazyEights;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.R;

import C8.Cards.Card;
import C8.Cards.Deck;
import C8.CrazyEights.C8InfoMessage.C8GameState;
import C8.GameFramework.animation.AnimationSurface;

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
 * @version 20 April 2022
 */
public class GameBoard extends AnimationSurface {

    // Game state info
    C8GameState state = null;

    // string array of player names
    String[] playerNames = null;

    // players hand index
    int handIndex = 0;

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

    // slot dimensions (suit select (insert card suit here))
    RectF slot1, slot2, slot3, slot4, drawSlot, discardSlot, ssS, ssD, ssC, ssH;

    // text size
    float fontSize = 30.0f;

    // paints
    Paint slotPaint = new Paint();
    Paint textPaint = new Paint();
    Paint largeTextPaint = new Paint();
    Paint playerPaint = new Paint();
    Paint dimmerPaint = new Paint();
    Paint currPlayerPaint = new Paint();

    // cards to draw during select suit
    public final static Card spades8 = new Card("S8");
    public final static Card diamonds8 = new Card("D8");
    public final static Card clubs8 = new Card("C8");
    public final static Card hearts8 = new Card("H8");
    private TextView playerTextView;

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

        // initialize instance variables; cannot declare them above
        // since the getWidth() and getHeight() methods do not work
        // until the canvas is initialized
        initBoard();

        // draw slots
//        canvas.drawRect(slot1, slotPaint);
//        canvas.drawRect(slot2, slotPaint);
//        canvas.drawRect(slot3, slotPaint);
//        canvas.drawRect(slot4, slotPaint);
        if(this.state.getPlayerIndex() == 0){
            canvas.drawRect(slot1, currPlayerPaint);
        }else if(this.state.getPlayerIndex() == 1){
            canvas.drawRect(slot2, currPlayerPaint);
        }else if(this.state.getPlayerIndex() == 2){
            canvas.drawRect(slot3, currPlayerPaint);
        }else if(this.state.getPlayerIndex() == 3){
            canvas.drawRect(slot4, currPlayerPaint);
        }

        // draw player names
        canvas.drawText(this.playerNames[0] + ": Cards Left (" +
                this.state.getPlayerHands().get(0).size() + ")", slot1.centerX(),
                slot1.bottom - (int) (0.5 * fontSize), textPaint);
        canvas.drawText(this.playerNames[1] + ": Cards Left (" +
                        this.state.getPlayerHands().get(1).size() + ")", slot2.centerX(),
                slot2.bottom - (int) (0.5 * fontSize), textPaint);
        canvas.drawText(this.playerNames[2] + ": Cards Left (" +
                        this.state.getPlayerHands().get(2).size() + ")", slot3.centerX(),
                slot3.bottom - (int) (0.5 * fontSize), textPaint);
        canvas.drawText(this.playerNames[3] + ": Cards Left (" +
                        this.state.getPlayerHands().get(3).size() + ")", slot4.centerX(),
                slot4.bottom - (int) (0.5 * fontSize), textPaint);
        canvas.drawText("Draw", drawSlot.centerX(),
                drawSlot.bottom-(int)(0.5*fontSize), textPaint);
        canvas.drawText("Discard", discardSlot.centerX(),
                discardSlot.bottom-(int)(0.5*fontSize), textPaint);
        canvas.drawText("Player " + this.playerNames[state.getPlayerIndex()] + "'s turn", 10, 30, playerPaint);



        // draw player hands
        drawPlayerHand(canvas, slot1, state.getPlayerHands().get(0), handIndex);
        drawPlayerHand(canvas, slot2, state.getPlayerHands().get(1), 0);
        drawPlayerHand(canvas, slot3, state.getPlayerHands().get(2), 0);
        drawPlayerHand(canvas, slot4, state.getPlayerHands().get(3), 0);
        makeDrawPile(canvas, drawSlot, state.getDrawPile());
        drawDiscardPile(canvas, discardSlot, state.getDiscardPile());

        canvas.drawText("" + this.state.getDrawPile().size(), drawSlot.centerX(),
                drawSlot.centerY()-(int)(0.5*fontSize), textPaint);

        if(!state.getHasDeclaredSuit() && this.state.getPlayerIndex() == 0) {
            canvas.drawRect(this.getLeft(), 0, this.getRight(), this.getBottom(), dimmerPaint);
            canvas.drawText("Select a suit!", boardWidth/2, boardHeight/4, largeTextPaint);
            drawCard(canvas, ssS, spades8);
            drawCard(canvas, ssD, diamonds8);
            drawCard(canvas, ssC, clubs8);
            drawCard(canvas, ssH, hearts8);
        }

        if(!this.state.getDiscardPile().peekTopCard().getSuit().equals(this.state.getCurrentSuit())){
            Deck temp = new Deck();
            switch (this.state.getCurrentSuit()) {
                case "Spades":
                    temp.add(spades8);
                    break;
                case "Diamonds":
                    temp.add(diamonds8);
                    break;
                case "Clubs":
                    temp.add(clubs8);
                    break;
                case "Hearts":
                    temp.add(hearts8);
                    break;
            }
            drawDiscardPile(canvas, discardSlot, temp);
        }
    }

    /**
     * Updates the C8GameState object with a new array of player names
     * NOTE: state must be updated using this method since GameState constructor doesn't use array
     *
     * @param state - the gamestate to update
     * @param playerNames - the string array of player names
     */
    public void updateMode(C8GameState state, String[] playerNames, int handIndex){
        this.state = state;
        this.playerNames = playerNames;
        this.handIndex = handIndex;
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
                (float) (boardWidth/2), (float) (2 * (boardHeight/3)));

        drawSlot = new RectF((float) (boardWidth/2), (float) (boardHeight/3),
                (float) ((boardWidth/3) * 2), (float) (2 * (boardHeight/3)));

        ssS = new RectF((float) boardWidth/9, (float) (boardHeight/3),
                (float) 2 * (boardWidth/9), (float) (2 * (boardHeight/3)));

        ssD = new RectF((float) (boardWidth/3), (float) (boardHeight/3),
                (float) 4 * (boardWidth/9), (float) (2 * (boardHeight/3)));

        ssC = new RectF((float) 5 * (boardWidth/9), (float) (boardHeight/3),
                (float) 2 * (boardWidth/3), (float) (2 * (boardHeight/3)));

        ssH = new RectF((float) 7 * (boardWidth/9), (float) (boardHeight/3),
                (float) 8 * (boardWidth/9), (float) (2 * (boardHeight/3)));

        // slot paint
        slotPaint.setColor(Color.BLACK);

        // text paint
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(fontSize);
        textPaint.setTextAlign(Paint.Align.CENTER);

        // large text paint
        largeTextPaint.setColor(Color.WHITE);
        largeTextPaint.setTextSize(fontSize * 3);
        largeTextPaint.setTextAlign(Paint.Align.CENTER);

        // player paint
        playerPaint.setColor(Color.BLACK);
        playerPaint.setTextSize(35);
        playerPaint.setTextAlign(Paint.Align.LEFT);

        // current player slot paint
        currPlayerPaint.setColor(Color.YELLOW);

        // dimmer paint
        dimmerPaint.setARGB(160, 0, 0, 0);
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
    private void drawPlayerHand(Canvas g, RectF slot, Deck playerDeck, int handIndex) {
        // the card dimensions
        float cardSizeY = (slot.bottom - slot.top) * 0.8f;
        float cardSizeX = cardSizeY * 0.7f;
        // delta locations to draw cards nicely within the slots
        float delta = (slot.right - slot.left - cardSizeX)/(playerDeck.size() - 1);
        float guiDelta = (float)((slot.right - slot.left - cardSizeX) / 2);
        // check the deck is not empty yet
        if(playerDeck.size() > 0) {
            // non-gui hand
            if (playerDeck.getCards().get(0) == null) {
                if(playerDeck.size() == 1){
                    drawCard(g, new RectF(slot.left, slot.top, slot.left + cardSizeX,
                                    slot.top + cardSizeY),
                            playerDeck.getCards().get(0));
                }
                // loop through from top to back, drawing each card offset a little
                for (int i = 0; i < playerDeck.size(); i++) {
                    // determine the position of this card's top/left corner
                    float left = (float) (slot.left + (delta * i));
                    // draw a card into the appropriate rectangle (other player hand cards should be null)
                    drawCard(g, new RectF(left, slot.top, left + cardSizeX,
                                    slot.top + cardSizeY),
                            playerDeck.getCards().get(i));
                }
            }
            // gui hand
            else {
                if (playerDeck.size() >= 3) {
                    for (int i = 0; i < 3; i++) {
                        float left = (float) (slot.left + (guiDelta * i));
                        drawCard(g, new RectF(left, slot.top, left + cardSizeX,
                                        slot.top + cardSizeY),
                                playerDeck.getCards().get(handIndex + i));
                    }
                } else if (playerDeck.size() == 2 || playerDeck.size() == 1) {
                    for (int i = 0; i < playerDeck.size(); i++) {
                        float left = (float) (slot.left + (guiDelta * i));
                        drawCard(g, new RectF(left, slot.top, left + cardSizeX,
                                        slot.top + cardSizeY),
                                playerDeck.getCards().get(handIndex + i));
                    }
                }
            }
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

        // card dimensions
        float cardSizeY = (slot.bottom - slot.top) * 0.85f;
        float cardSizeX = cardSizeY * 0.7f;
        float left = slot.centerX() - (cardSizeX / 2.0f);

        // draw singular card to denote draw pile
        drawCard(g, new RectF(left, slot.top, left + cardSizeX,
                slot.top + cardSizeY), null);
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

        // card dimensions
        float cardSizeY = (slot.bottom - slot.top) * 0.85f;
        float cardSizeX = cardSizeY * 0.7f;
        float left = slot.centerX() - (cardSizeX / 2.0f);

        // draw singular top card to denote discard pile
        drawCard(g, new RectF(left, slot.top, left + cardSizeX,
                slot.top + cardSizeY), pile.peekTopCard());
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
    public RectF getSsS() {
        return ssS;
    }
    public RectF getSsD() {
        return ssD;
    }
    public RectF getSsC() {
        return ssC;
    }
    public RectF getSsH() {
        return ssH;
    }

    public void setPlayerTextView(TextView text) {
        this.playerTextView = text;
    }


}