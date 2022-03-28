package com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.Test;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.R;
import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.Test.C8TestController;

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
public class C8TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ------------C8 Game State Test------------
        setContentView(R.layout.c8gamestatetest);

        // access the button from the c8gamestatetest file and assign an onClickListener
        Button runTest = findViewById(R.id.RunTestButton);
        C8TestController listener = new C8TestController(findViewById(R.id.EditText));

        // attach on click listener to runTest button
        runTest.setOnClickListener(listener);

    }

}