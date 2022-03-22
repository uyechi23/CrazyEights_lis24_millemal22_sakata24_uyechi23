package com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
    Rect slot1;
    Rect slot2;
    Rect slot3;
    Rect slot4;

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
        slot1 = new Rect((int) (boardWidth/3), (int) (2 * (boardHeight/3)),
                (int) ((boardWidth/3) * 2), (int) boardHeight);
        slot2 = new Rect(0, (int) (boardHeight/3),
                (int) (boardWidth/3), (int) (2 * (boardHeight/3)));
        slot3 = new Rect((int) (boardWidth/3), 0,
                (int) ((boardWidth/3) * 2), (int) (boardHeight/3));
        slot4 = new Rect((int) ((boardWidth/3) * 2), (int) (boardHeight/3),
                (int) boardWidth, (int) (2 * (boardHeight/3)));


        // slot paint
        slotPaint.setColor(Color.GREEN);

        // text paint
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(fontSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }
}