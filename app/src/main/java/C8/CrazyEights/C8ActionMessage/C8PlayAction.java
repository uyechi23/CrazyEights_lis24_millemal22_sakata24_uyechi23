package C8.CrazyEights.C8ActionMessage;

import C8.GameFramework.players.GamePlayer;

/**
 * C8PlayAction
 *
 * Action class for playing a card
 *
 * @author Selena Li
 * @author Maliyah Miller
 * @author Jake Uyechi
 * @author Tyler Sakata
 *
 * @version 29 March 2022
 */
public class C8PlayAction extends C8MoveAction{

    private int index;

    /**
     * constructor for GameAction
     *
     * @param player - the player who created the action
     * @param index - the index of card
     */
    public C8PlayAction(GamePlayer player, int index) {
        // initialize with the super class constructor
        super(player);
        this.index = index;
    }

    /**
     * getIndex()
     *
     * @return int
     */
    public int getIndex() {
        return index;
    }

    /**
     * isPlay()
     *
     * Represents a play move
     *
     * @return boolean
     */
    public boolean isPlay() { return true; }
}
