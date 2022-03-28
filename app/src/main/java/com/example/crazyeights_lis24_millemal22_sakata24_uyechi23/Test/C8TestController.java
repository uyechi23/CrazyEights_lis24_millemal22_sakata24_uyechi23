package com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.Test;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights.C8GameState;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights.C8HumanPlayer;

public class C8TestController implements View.OnClickListener{

    private final TextView info;

    public C8TestController(TextView textview){
        this.info = textview;
    }

    /*
    External Citation:
        Date:       14 March 2022
        Problem:    Wanted to make TextView scrollable
        Resource:   https://stackoverflow.com/questions/1748977/making-textview-scrollable-on-android
        Solution:   Added android:scrollbars = "vertical" in xml file and currText.setMovementMethod(new ScrollingMovementMethod());
     */

    @Override
    public void onClick(View view) {
        // clear the current TextView (from previous test)
        info.setMovementMethod(new ScrollingMovementMethod());
        info.setText("");

        // new instance of the Game State is created (firstInstance)
        // default constructor requires an array of player names
        String[] playerNames = new String[4];
        playerNames[0] = "Tyler";
        playerNames[1] = "Maliyah";
        playerNames[2] = "Selena";
        playerNames[3] = "Jake";
        C8HumanPlayer player1 = new C8HumanPlayer(playerNames[0], 0xFF000000);
        C8GameState firstInstance =
                new C8GameState(playerNames, 1);
        C8GameState firstCopy =
                new C8GameState(firstInstance, player1);


        // implementation methods:
        /*
         * PLAY CARD: GameState.playCard(index);
         *      The index of the card to play should be printed out in Logcat
         *
         * DRAW CARD: GameState.drawCard();
         *      This will find the current player and add a card to their hand
         *      In the actual game, the player will tap the deck multiple times
         *      until they have a card they can play. To test GameState, use a
         *      while loop to imitate this. Use with below code snippet:
         *
         *  boolean canMove = false; // have a boolean if the player can move
         *      // while the player can't move and there are cards in the draw pile
         *      while(!canMove && firstInstance.getDrawPile().size() > 0) {
         *          firstInstance.drawCard();
         *          canMove = firstInstance.checkIfValid();
         *      }
         *      if(canMove) firstInstance.playLastCard(); // play the last card if possible
         *
         * CHECK IF THE SUIT NEEDS TO BE CHANGED: GameState.checkToChangeSuit();
         *      This detects if the most recent card was an 8, and the suit needs
         *      to be changed. This decision is done automatically (based on the
         *      most common suit in the player's hand).
         *
         * NEXT PLAYER: GameState.nextPlayer();
         *      Moves to next player
         *
         *
         */

        // indicate game start
        info.append("GAME START: Starting game...\n\n");

        // print out current game state
        info.append("INITIAL GAME STATE: \n");
        info.append(firstInstance.toString() + "\n");

        // SEED 1: Selena starts, plays 7 of Hearts
        info.append("---------------------Turn 1:---------------------\n");
        info.append(firstInstance.getPlayerTurn() + " played a 7 of Hearts\n");
        info.append("-------------------------------------------------\n\n");
        firstInstance.movePlay(1, firstInstance.getPlayerTurn());
        info.append(firstInstance.toString());

        // SEED 1: Jake goes next, draws card(s)
        info.append("---------------------Turn 2:---------------------\n");
        info.append(firstInstance.getPlayerTurn() + " draws cards.\n");
        info.append(firstInstance.getPlayerTurn() + " played an Ace of Hearts\n");
        info.append("-------------------------------------------------\n\n");
        firstInstance.moveDraw(firstInstance.getPlayerTurn());
        info.append(firstInstance.toString());

        // SEED 1: Tyler goes next, plays Ace of Spades
        info.append("---------------------Turn 3:---------------------\n");
        info.append(firstInstance.getPlayerTurn() + " played an Ace of Spades\n");
        info.append("-------------------------------------------------\n\n");
        firstInstance.movePlay(2, firstInstance.getPlayerTurn());
        info.append(firstInstance.toString());

        // SEED 1: Maliyah goes next, plays 8 of Hearts
        // New Suit: Diamonds
        info.append("---------------------Turn 4:---------------------\n");
        info.append(firstInstance.getPlayerTurn() + " played an 8 of Hearts\n");
        info.append("Declared suit: Diamonds\n");
        info.append("-------------------------------------------------\n\n");
        firstInstance.movePlay(2, firstInstance.getPlayerTurn());
        info.append(firstInstance.toString());

        // making second instance same seed, same names.
        C8GameState secondInstance =
                new C8GameState(playerNames, 1);
        // making a copy. should be same as first copy
        C8GameState secondCopy =
                new C8GameState(secondInstance, player1);

        // print the two copies
        info.append("-------------------------------------------------\n");
        info.append("COPY CONSTRUCTOR TESTS\n");
        info.append("-------------------------------------------------\n\n");
        info.append("First Copy: \n");
        info.append(firstCopy.toString() + "\n");
        info.append("-------------------------------------------------\n\n");
        info.append("Second Copy: \n");
        info.append(secondCopy.toString() + "\n");
        // check if the two copies are identical
        if(firstCopy.toString().equals(secondCopy.toString())) {
            info.append("First copy is identical to second.\n");
        } else {
            info.append("First copy is not identical to second.\n");
        }
    }
}
