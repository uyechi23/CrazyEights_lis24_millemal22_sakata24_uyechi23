package C8.CrazyEights.C8ActionMessage;

import C8.GameFramework.players.GamePlayer;

public class C8SelectSuitAction extends C8MoveAction{
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public C8SelectSuitAction(GamePlayer player) {
        // initialize with the super class constructor
        super(player);
    }

    /**
     * @return this move represents a select suit move
     */
    public boolean isSelectSuit() { return true; }
}

