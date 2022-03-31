package C8.CrazyEights;

import android.graphics.Color;

import C8.CrazyEights.C8InfoMessage.C8GameState;
import C8.CrazyEights.C8Players.C8ComputerPlayer;
import C8.CrazyEights.C8Players.C8HumanPlayer;
import C8.GameFramework.GameMainActivity;
import C8.GameFramework.LocalGame;
import C8.GameFramework.gameConfiguration.GameConfig;
import C8.GameFramework.gameConfiguration.GamePlayerType;
import C8.GameFramework.infoMessage.GameState;
import C8.GameFramework.players.GamePlayer;

import java.util.ArrayList;

/**
 * C8MainActivity
 *
 * Creates and adds players for Crazy Eights game
 *
 * @author Selena Li
 * @author Maliyah Miller
 * @author Jake Uyechi
 * @author Tyler Sakata
 *
 * @version 29 March 2022
 */
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
            // TODO: retrieve the number of players from the config menu to construct GameState
            gameState = new C8GameState(4);
        }

        return new C8LocalGame((C8GameState) gameState);
    }

}
