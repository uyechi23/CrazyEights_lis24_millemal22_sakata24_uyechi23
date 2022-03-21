package com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights;

import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.DateTimePatternGenerator;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.widget.LinearLayout;

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
 * @version 24 February 2022
 */
public class GameBoard extends SurfaceView {

    //Gameboard dimensions
    float boardWidth = 2000.0f; //Need to get from xml file maybe start the game with a popup window then                                                        4
    float boardHeight = 853.0f; //the surface view can be created, and onDraw can be invalidated then the getters might work

    /**
     * slot dimensions
     *
     * slot1: current player
     * slot2: left of current player
     * slot3: above current player
     * slot4: right of current player
     *
     * slot_[0]: Start X
     * slot_[1]: Start Y
     * slot_[2]: End X
     * slot_[3]: End Y
     */

    //slot 1 dimensions (current player)
    float[] slot1 = {(boardWidth/3), (2 * (boardHeight/3)),
            ((boardWidth/3) * 2), boardHeight};
    float[] slot2 = {0.0f, (boardHeight/3),
            (boardWidth/3), (2 * (boardHeight/3))};
    float[] slot3 = {(boardWidth/3), 0.0f,
            ((boardWidth/3) * 2), (boardHeight/3)};
    float[] slot4 = {((boardWidth/3) * 2), (boardHeight/3),
            boardWidth, (2 * (boardHeight/3))};

    //Current player Card Dimensions
    float playerCardHeight = (slot1[3]-slot1[1])/2;
    float playerCardWidth = playerCardHeight/2;

    //Other Player Card Dimensions
    float otherCardHeight = (slot1[3]-slot1[1])/3;
    float otherCardWidth = otherCardHeight/2;

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

        //slot paint
        Paint slotPaint = new Paint();
        slotPaint.setColor(Color.GREEN);

      //  drawCurrentPlayerCard(canvas);
        float startY = slot1[3] - ((slot1[3]-slot1[1]) / 2);
        float endX = slot1[0] + playerCardWidth;
        canvas.drawRect(slot1[0], slot1[1], slot1[2], slot1[3], slotPaint);
        canvas.drawRect(slot2[0], slot2[1], slot2[2], slot2[3], slotPaint);
        canvas.drawRect(slot3[0], slot3[1], slot3[2], slot3[3], slotPaint);
        canvas.drawRect(slot4[0], slot4[1], slot4[2], slot4[3], slotPaint);
        //canvas.drawRect(slot1StartX, startY, endX, boardEndY, cardFrontColor);                                              //300,100
        //canvas.drawRect(slot1EndX/3, startY, (slot1EndX/3)+playerCardWidth, boardEndY, cardFrontColor);
        //canvas.drawRect(2*(slot1EndX/3), startY, (2*(slot1EndX/3))+playerCardWidth, boardEndY, cardFrontColor);
        String player1 = "Player 1";
        String player2 = "Player 2";
        String player3 = "Player 3";
        String player4 = "Player 4";
        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(30.0f);
        canvas.drawText(player1, slot1[0]+275, slot1[3], textPaint);
        canvas.drawText(player2, slot2[0]+275, slot2[3], textPaint);
        canvas.drawText(player3, slot3[0]+275, slot3[3], textPaint);
        canvas.drawText(player4, slot4[0]+275, slot4[3], textPaint);

    }
}