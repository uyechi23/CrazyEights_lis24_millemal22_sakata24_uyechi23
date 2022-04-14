package C8.CrazyEights.C8ActionMessage;

import C8.GameFramework.players.GamePlayer;

public class C8SelectSuitAction extends C8MoveAction{
    /* instance var */
    String suitSelected;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public C8SelectSuitAction(GamePlayer player, String initSuit) {
        // initialize with the super class constructor
        super(player);
        this.suitSelected = initSuit;
    }

    /**
     * @return this move represents a select suit move
     */
    public boolean isSelectSuit() { return true; }
    /**
     * @return this moves selected suit
     */
    public String getSuitSelected() { return this.suitSelected; }
}

