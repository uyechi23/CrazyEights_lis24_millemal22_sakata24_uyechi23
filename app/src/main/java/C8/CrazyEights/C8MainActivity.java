package C8.CrazyEights;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.provider.MediaStore;

import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.R;

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
import java.util.Random;

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

    private MediaPlayer card_shuffle, DF_rick_roll, rick_roll,
            play_card, please_dont, whoosh, suit_change;

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
        initMusic();

        if(gameState == null){
            // TODO: retrieve the number of players from the config menu to construct GameState
            gameState = new C8GameState(this.getConfig().getNumPlayers());
        }

        /*
        External Citation
        Date:       19 April 2022
        Problem:    Needed to figure out how to add sound
        Resource:   https://www.geeksforgeeks.org/how-to-add-audio-files-to-android-app-in-android-studio/
        Solution:   Used the procedure in the link above to add a card shuffle sound on game start
         */

        // roll 1d20 for initiative
        Random rand = new Random();
        int troll = 1 + rand.nextInt(20);


        // decide starting sound based on roll
        if(troll == 1){
            // critical miss (1)
            this.DF_rick_roll.start();
        }else if(troll < 7){
            // rolled a miss (2-6)
            this.rick_roll.start();
        }else{
            // rolled a hit (>6)
            this.card_shuffle.start();
        } // nothing for critical hit sorry

        // return the new game state
        return new C8LocalGame((C8GameState) gameState, this);
    }

    public void initMusic(){
        // initialize music players
        this.card_shuffle = MediaPlayer.create(this, R.raw.card_shuffle);
        this.DF_rick_roll = MediaPlayer.create(this, R.raw.deepfryrickroll);
        this.rick_roll = MediaPlayer.create(this, R.raw.rickroll);
        this.play_card = MediaPlayer.create(this, R.raw.playcard);
        this.please_dont = MediaPlayer.create(this, R.raw.pleasedont);
        this.whoosh = MediaPlayer.create(this, R.raw.whoosh);
        this.suit_change = MediaPlayer.create(this, R.raw.suitchange);
    }

    public void playShuffle() { this.card_shuffle.start(); }
    public void playDFRR() { this.DF_rick_roll.start(); }
    public void playRR() { this.rick_roll.start(); }
    public void playCard() { this.play_card.start(); }
    public void playDont() { this.please_dont.start(); }
    public void playWhoosh() { this.whoosh.start(); }
    public void playChange() { this.suit_change.start(); }

}
