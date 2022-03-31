package C8.CrazyEights;

import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

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
 * @version 28 March 2022
 */
public class GameBoardController implements View.OnTouchListener,
        View.OnClickListener, View.OnDragListener, SeekBar.OnSeekBarChangeListener {

    private int progress;
    private int maxprogress;
    private int displayIndex;


    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onProgressChanged( SeekBar seekBar, int i, boolean b) {
        /**
         * should change the three cards it's showing to the next
         * five cards in the array, or >5 cards in the array
         *
         * should it be centered always, or should it just fill the three slots in order?
         * [][][] , []_[] , _[]_  -OR-  [][][] , [][]_ , ___
         */
        //maxprogress = (int) Math.ceil(/*players hand length / 5*/);
        progress = seekBar.getProgress();
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
