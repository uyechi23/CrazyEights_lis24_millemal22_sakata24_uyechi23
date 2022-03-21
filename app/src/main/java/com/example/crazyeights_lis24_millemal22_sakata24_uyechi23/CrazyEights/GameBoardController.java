package com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights;

import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

public class GameBoardController implements View.OnTouchListener, View.OnClickListener, View.OnDragListener{
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
}
