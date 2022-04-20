package C8.CrazyEights;

import android.graphics.Color;

import C8.CrazyEights.C8InfoMessage.C8GameState;
import C8.CrazyEights.C8Players.C8ComputerPlayer;
import C8.CrazyEights.C8Players.C8HumanPlayer;
import C8.CrazyEights.C8Players.C8SmartComputerPlayer;
import C8.GameFramework.GameMainActivity;
import C8.GameFramework.LocalGame;
import C8.GameFramework.gameConfiguration.GameConfig;
import C8.GameFramework.gameConfiguration.GamePlayerType;
import C8.GameFramework.infoMessage.GameState;
import C8.GameFramework.players.GamePlayer;
import C8.GameFramework.players.ProxyPlayer;

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
 * @extraEnhancements Various sounds are implemented throughout the game
 *                    Current player's turn is displayed with highlighted boxes and in top left corner
 *
 * @version 20 April 2022
 */
public class C8MainActivity extends GameMainActivity {
    private static final int PORT_NUMBER = 67828;

    @Override
    public GameConfig createDefaultConfig() {

        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        playerTypes.add(new GamePlayerType("human player (green)") {
            public GamePlayer createPlayer(String name) {
                return new C8HumanPlayer(name, 0xFF98FB98);
            }
        });
        playerTypes.add(new GamePlayerType("human player (yellow)") {
            public GamePlayer createPlayer(String name) {
                return new C8HumanPlayer(name, 0xFFFFFFA7);
            }
        });
        playerTypes.add(new GamePlayerType("computer player (normal)") {
            public GamePlayer createPlayer(String name) {
                return new C8ComputerPlayer(name);
            }
        });
        playerTypes.add(new GamePlayerType("computer player (smart)"){
            public GamePlayer createPlayer(String name) {
                return new C8SmartComputerPlayer(name);
            }
        });
        playerTypes.add(new GamePlayerType("proxy player (human)"){
            public GamePlayer createPlayer(String name) {
                return new ProxyPlayer(PORT_NUMBER);
            }
        });

        // Create a game configuration class for C8
        GameConfig defaultConfig = new GameConfig(playerTypes, 2, 4, "Crazy Eights", PORT_NUMBER);

        // Add the default players
        defaultConfig.addPlayer("Human", 0);
        defaultConfig.addPlayer("Computer1", 2);

        //done!
        return defaultConfig;
    }

    @Override
    public LocalGame createLocalGame(GameState gameState) {
        if(gameState == null){
            // TODO: retrieve the number of players from the config menu to construct GameState
            gameState = new C8GameState(this.getConfig().getNumPlayers());
        }

        return new C8LocalGame((C8GameState) gameState);
    }

}
