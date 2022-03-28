package com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights;

import android.graphics.Color;
import android.os.Bundle;

import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.GameFramework.GameMainActivity;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.GameFramework.LocalGame;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.GameFramework.gameConfiguration.GameConfig;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.GameFramework.gameConfiguration.GamePlayerType;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.GameFramework.infoMessage.GameState;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.GameFramework.players.GamePlayer;

import java.util.ArrayList;

public class C8MainActivity extends GameMainActivity {
    private static final int PORT_NUMBER = 6969;

    @Override
    public GameConfig createDefaultConfig() {

        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        playerTypes.add(new GamePlayerType("human player (green)") {
            public GamePlayer createPlayer(String name) {
                return new C8HumanPlayer(name, Color.GREEN);
            }
        });
        playerTypes.add(new GamePlayerType("human player (yellow)") {
            public GamePlayer createPlayer(String name) {
                return new C8HumanPlayer(name, Color.YELLOW);
            }
        });
        playerTypes.add(new GamePlayerType("computer player (normal)") {
            public GamePlayer createPlayer(String name) {
                return new C8ComputerPlayer(name);
            }
        });

        // Create a game configuration class for SlapJack
        GameConfig defaultConfig = new GameConfig(playerTypes, 4, 4, "Crazy Eights", PORT_NUMBER);

        // Add the default players
        defaultConfig.addPlayer("Human", 0);
        defaultConfig.addPlayer("Computer1", 2);
        defaultConfig.addPlayer("Computer2", 2);
        defaultConfig.addPlayer("Computer3", 2);

        // Set the initial information for the remote player
        defaultConfig.setRemoteData("Guest", "", 1);

        //done!
        return defaultConfig;
    }

    @Override
    public LocalGame createLocalGame(GameState gameState) {
        if(gameState == null){
            // TODO: create a new game state to send if the input argument is null
            // TODO: retrieve player names from the config screen to use C8GameState default constructor
        }

        return new C8LocalGame((C8GameState) gameState);
    }

}
