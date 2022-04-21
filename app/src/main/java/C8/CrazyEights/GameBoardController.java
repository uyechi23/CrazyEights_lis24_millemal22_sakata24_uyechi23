package C8.CrazyEights;

import android.app.Activity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.R;

import C8.CrazyEights.C8InfoMessage.C8GameState;
import C8.CrazyEights.C8Players.C8HumanPlayer;
import C8.GameFramework.actionMessage.GameAction;
import C8.GameFramework.utilities.MessageBox;

/**
 * GameBoardController
 *
 * Implements listeners and seekbars
 *
 * @author Selena Li
 * @author Maliyah Miller
 * @author Jake Uyechi
 * @author Tyler Sakata
 *
 * @version 14 April 2022
 */
public class GameBoardController implements View.OnTouchListener,
        View.OnClickListener, View.OnDragListener, SeekBar.OnSeekBarChangeListener {

    // a reference to the player object this controller listens on
    private C8HumanPlayer player;

    private int progress;
    private int maxProgress;

    public GameBoardController(C8HumanPlayer hp){
        this.player = hp;
        this.progress = 0;
        this.maxProgress = 2;
    }

    /**
     * performs an action with the button/view that was clicked
     *
     * @param view the view that was clicked
     */
    @Override
    public void onClick(View view) {
        // check which button was clicked
        // menu button was clicked
        if(view == player.getActivity().findViewById(R.id.menu_button)){
            C8MenuDialog.displayMenu((C8MainActivity) player.getActivity());
        }
        // skip is clicked
        else if(view == player.getActivity().findViewById(R.id.skipButton)) {
            this.player.isClicked();
        }
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    /**
     * Changes the seekbar based on where it is touched. Also tells the board to display cards
     * accordingly
     *
     * @param seekBar the seekbar that was touched
     * @param i the progress of the seekbar
     * @param b if the change was a player or not
     */
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        // set the seekbar accordingly
        progress = seekBar.getProgress();
        this.player.updateSeekBar();
        // display the players hand accordingly
        this.player.setPlayerHandIndex(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //useless
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //more unnecessary than these comments
    }

}
