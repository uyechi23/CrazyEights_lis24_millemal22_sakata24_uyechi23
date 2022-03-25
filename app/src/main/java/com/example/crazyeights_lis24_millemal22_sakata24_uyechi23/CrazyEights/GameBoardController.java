package com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights;

import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

public class GameBoardController implements View.OnTouchListener, View.OnClickListener, View.OnDragListener, SeekBar.OnSeekBarChangeListener {
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
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        /**
         * should change the three cards it's showing to the next
         * three cards in the array, or >3 cards in the array
         *
         * should it be centered always, or should it just fill the three slots in order?
         * [][][] , []_[] , _[]_  -OR-  [][][] , [][]_ , ___
         */
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
