package C8.CrazyEights.C8ActionMessage;

import C8.GameFramework.actionMessage.GameAction;
import C8.GameFramework.players.GamePlayer;

/**
 * C8MoveAction
 *
 * Action class for when the player makes an action
 *
 * @author Selena Li
 * @author Maliyah Miller
 * @author Jake Uyechi
 * @author Tyler Sakata
 *
 * @version 8 April 2022
 */
public abstract class C8MoveAction extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public C8MoveAction(GamePlayer player) {
        // invoke super class constructor
        super(player);
    }

    /**
     * @return whether this move plays a card
     */
    public boolean isPlay() { return false; }

    /**
     * @return whether this move draws a card
     */
    public boolean isDraw() {
        return false;
    }

    /**
     *
     * @return whether this move skips a turn
     */
    public boolean isSkipped() { return false; }
}
