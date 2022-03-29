package com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights.C8ActionMessage;

import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.GameFramework.actionMessage.GameAction;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.GameFramework.players.GamePlayer;

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
    public boolean isDraw() { return false; }
}
