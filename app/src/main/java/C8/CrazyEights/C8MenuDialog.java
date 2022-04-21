package C8.CrazyEights;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.example.crazyeights_lis24_millemal22_sakata24_uyechi23.R;

import C8.GameFramework.utilities.MessageBox;

/**
 * C8MenuDialog
 *
 * The dialog class that is used for the menu button exclusively. Inherits from the message
 * box class in the GameFramework
 *
 * @author Selena Li
 * @author Maliyah Miller
 * @author Jake Uyechi
 * @author Tyler Sakata
 *
 * @version 20 April 2022
 */
public class C8MenuDialog extends MessageBox {

    /**
     * displays a dialog menu when the menu button is clicked
     * (used the MessageBox class for reference as well as the dialog android documentation)
     *
     * @param activity the main activity to alter
     */
    public static void displayMenu(C8MainActivity activity) {
        // Generates an alert dialog box
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Menu:");
        // Sets the two options
        builder.setItems((new String[]{"Quit", "Toggle Sound"}),
                // define an on click listener solely for use in here
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0) {
                    // taken from GameMainActivity. Another popup to confirm if quit is pressed
                    MessageBox.popUpChoice("Do you really want to quit?",
                            "Quit", "Continue playing",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface di, int val) {
                                    // if the user says that he wants to quit, exit the
                                    // application
                                    System.exit(0);
                                }
                            },
                            null,
                            activity);
                } else {
                    // toggle the sound
                    activity.toggleSoundEnabled();
                }
            }
        });
        // display the dialog box
        AlertDialog alert = builder.create();
        alert.show();
    }
}

