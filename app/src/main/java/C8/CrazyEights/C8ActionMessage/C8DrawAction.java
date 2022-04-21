package C8.CrazyEights.C8ActionMessage;

import C8.GameFramework.players.GamePlayer;

/**
 * C8DrawAction
 *
 * Action class for drawing a card
 *
 * @author Selena Li
 * @author Maliyah Miller
 * @author Jake Uyechi
 * @author Tyler Sakata
 *
 * @version 20 April 2022
 */
public class C8DrawAction extends C8MoveAction{
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public C8DrawAction(GamePlayer player) {
        // initialize with the super class constructor
        super(player);
    }

    /**
     * @return this move represents a draw move
     */
    public boolean isDraw() {
        return true;
    }
}
