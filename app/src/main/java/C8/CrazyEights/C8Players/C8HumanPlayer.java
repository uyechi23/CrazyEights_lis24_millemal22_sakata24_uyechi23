package C8.CrazyEights.C8Players;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import C8.Cards.Card;
import C8.CrazyEights.C8ActionMessage.C8DrawAction;
import C8.CrazyEights.C8ActionMessage.C8PlayAction;
import C8.CrazyEights.C8InfoMessage.C8GameState;
import C8.CrazyEights.GameBoard;
import C8.CrazyEights.GameBoardController;
import C8.GameFramework.Game;
import C8.GameFramework.GameMainActivity;
import C8.GameFramework.animation.Animator;
import C8.GameFramework.infoMessage.GameInfo;
import C8.GameFramework.infoMessage.IllegalMoveInfo;
import C8.GameFramework.infoMessage.NotYourTurnInfo;
import C8.GameFramework.players.GameHumanPlayer;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.R;

import java.util.Objects;

/**
 * CrazyEightsHumanPlayer
 *
 * A player object that extends from GameHumanPlayer. It operates similar to its parent.
 * (for now)
 *
 * @author Selena Li
 * @author Maliyah Miller
 * @author Jake Uyechi
 * @author Tyler Sakata
 *
 * @version 29 March 2022
 */
public class C8HumanPlayer extends GameHumanPlayer implements Animator {
    /**
     * instance variables
     */
    protected C8GameState state; // player state w/ censored info
    private Activity currActivity; // the activity of the current player
    private GameBoard gameBoard; // animation surface of the player GUI
    private SeekBar handProgress; // SeekBar representing section of player's hand
    private int backgroundColor; // background color of GUI
    private boolean stateUpdated; // if the state was updated recently

    /**
     * static instance variables
     */
    private static final int MAX_CARD_DISPLAY = 3; // the maximum number of cards displayed

    /**
     * constructor
     *
     * @param name the name of the player
     */
    public C8HumanPlayer(String name, int color) {
        super(name);
        this.backgroundColor = color;
    }

    @Override
    public View getTopView() {
        return currActivity.findViewById(R.id.top_gui);
    }

    @Override
    public void receiveInfo(GameInfo info) {

        if (info instanceof NotYourTurnInfo || info == null) {
            // if the move received is either an illegal move or it's not the current player' turn,
            // flash the screen red
            gameBoard.flash(0xFFFF0000, 50);
        } else if (info instanceof C8GameState) {
            // if it is a GameState object, typecast to a C8GameState object and save it
            this.state = (C8GameState) info;
            this.stateUpdated = true;
            // update the gamestate using .updateMode()
            this.gameBoard.updateMode(this.state, this.allPlayerNames);
            // call the onDraw() method by invalidating the View
            this.gameBoard.invalidate();
        }
        // any other types of GameInfo objects passed to here do nothing
    }

    @Override
    public void setAsGui(GameMainActivity activity) {
        // save the current activity
        this.currActivity = activity;

        // set the GUI to be the activity main GUI
        activity.setContentView(R.layout.activity_main);

        // retrieve the AnimationSurface and link this object to it as the animator
        this.gameBoard = (GameBoard) currActivity.findViewById(R.id.gameBoard);
        gameBoard.setAnimator(this);

        // retrieve the hand's SeekBar and make a controller
        GameBoardController gbc = new GameBoardController(this);
        this.handProgress = (SeekBar) currActivity.findViewById(R.id.seekBar3);
        this.handProgress.setOnSeekBarChangeListener(gbc);

        // initialize card images
        Card.initImages(activity);

        // update the GUI with the current GameState object
        if (state != null) receiveInfo(state);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int interval() {
        // 1/20 of a second; 50 ms
        return 50;
    }

    @Override
    public int backgroundColor() {
        // return background color
        return this.backgroundColor;
    }

    @Override
    public boolean doPause() {
        return false;
    }

    @Override
    public boolean doQuit() {
        return false;
    }

    @Override
    public void tick(Canvas canvas) {
        // if state was not received yet at start
        if (state == null) return;
        // if state was not updated
        if (!this.stateUpdated) return;

        // toggle that we updated the state GUI so we can re-toggle and draw for another state
        this.stateUpdated = false;

        // redraw the gameboard
        this.gameBoard.invalidate();
    }

    @Override
    public void onTouch(MotionEvent event) {
        // ignore everything except down-touch events
        if (event.getAction() != MotionEvent.ACTION_DOWN) return;

        // get the location of the touch on the surface
        int x = (int) event.getX();
        int y = (int) event.getY();

        // do something with coordinates
        SeekBar handProgress = this.currActivity.findViewById(R.id.seekBar3);
        int currProgress = handProgress.getProgress();
        if (gameBoard.getDrawSlot().contains(x, y)) {
            game.sendAction(new C8DrawAction(this));
        } else if (gameBoard.getSlot1().contains(x, y)) {

            int numCards = this.state.getPlayerHands().get(0).size();

            for (int i = 1; i < numCards + 1; i++) {

                float cardEndX = (gameBoard.getSlot1().left +
                        gameBoard.getSlot1().right) * ((i + numCards) / (3.f * numCards));
                float cardStartX = (((gameBoard.getSlot1().left +
                        gameBoard.getSlot1().right) * (((i - 1) + numCards) / (3.f * numCards))));

                if (x <= cardEndX && x >= cardStartX) {
                    game.sendAction(new C8PlayAction(this, (i-1)));
                }
            }

            //TODO: ignore the touch if its not on a valid position
            //TODO: send game action for drawing
            //TODO: check coords and find which card clicked on and send play action
        }

        /**
         * Updates the SeekBar to have appropriate progress intervals for the player's hand
         */
//    public void updateSeekBar(){
//        // get the SeekBar
//        SeekBar sb = currActivity.findViewById(R.id.seekBar3);
//
//        // get the number of cards in the player's hand
//        int numCards = state.getPlayerHands().get(this.playerNum).size();
//
//        // max number of cards to display
//        int maxDisplay = 3;
//        int extra = 0;
//        if(numCards%maxDisplay != 0) extra = 1;
//
//        // set the max progress
//        sb.setMax(numCards/maxDisplay + extra);
//    }

    }
}