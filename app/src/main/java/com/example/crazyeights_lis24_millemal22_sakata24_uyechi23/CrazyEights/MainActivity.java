package com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.CrazyEights;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.R;

/**
 * MainActivity
 *
 * The MainActivity runs onCreate when the app is run and starts the game.
 *
 * This project is for SP22 CS301 - Object-Oriented Programming,
 * simulating a game of Crazy Eights.
 *
 * @author Selena Li
 * @author Maliyah Miller
 * @author Jake Uyechi
 * @author Tyler Sakata
 *
 * @version 15 March 2022
 */
public class MainActivity extends AppCompatActivity {

    /*
    External Citation:
        Date:       14 March 2022
        Problem:    Wanted to make TextView scrollable
        Resource:   https://stackoverflow.com/questions/1748977/making-textview-scrollable-on-android
        Solution:   Added android:scrollbars = "vertical" in xml file and currText.setMovementMethod(new ScrollingMovementMethod());
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c8gamestatetest);

        // access the button from the c8gamestatetest file and assign an onClickListener
        Button runTest = findViewById(R.id.RunTestButton);
        C8TestController listener = new C8TestController(findViewById(R.id.EditText));

        // attach on click listener to runTest button
        runTest.setOnClickListener(listener);
    }

}