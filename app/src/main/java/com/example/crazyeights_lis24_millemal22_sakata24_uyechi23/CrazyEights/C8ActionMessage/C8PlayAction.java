package com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights.C8ActionMessage;

import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.GameFramework.players.GamePlayer;

public class C8PlayAction extends C8MoveAction{
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public C8PlayAction(GamePlayer player) {
        // initialize with the super class constructor
        super(player);
    }

    /**
     * @return this move represents a play move
     */
    public boolean isPlay() { return true; }
}
