package com.example.aboolean;


// ==================================================
// == [IMPORTS] =====================================
// ==================================================


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.activity.EdgeToEdge;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.Button;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import java.util.Random;


// ==================================================
// == [MAIN_ACTIVITY] ===============================
// ==================================================


public class MainActivity extends AppCompatActivity {


    // --------------------------------------------------
    // -- [CLASS VARIABLES] -----------------------------
    // --------------------------------------------------


    // UI items on the screen.
    DisplayItem coin_box, flip_btn, reset_btn;

    // Tracker variables.
    boolean flipped, is_flipping = false;

    // Random.. for.. well.. random stuff!
    final Random random = new Random();

    // (Flickering)
    // The guy who puts tasks into the message queue (for main thread to do).
    final Handler handler = new Handler();

    // (Flickering)
    // Pretty much the class that contains the logic for flickering.
    final Runnable flicker = new Runnable() {

        // -- [VARIABLES] -----------------------------------
        // -- (Note: init only with "new Runnable") ---------

        // Is the coin box light?
        boolean light = true;

        // When to end the flickering.
        // Note: value of 0 acts as a flag.
        long end_time = 0;

        // -- [METHODS] -------------------------------------
        // -- (Note: is run when flicker.run() is called) ---

        @Override
        public void run() {

            // If this is the "first time" running.

            if (end_time == 0) {

                // Run for 1-4 seconds (lower limit 1s, + anywhere between 0 and 3s).
                end_time =  System.currentTimeMillis() + 1000 + random.nextInt(3001);

                // Make sure the variable light is light (as from original it is light).
                light = true;

                // Just make the text bigger and prep for 1/0.
                coin_box.buffText();

                // Re-run this function (which will then go to the "else" case).
                handler.postDelayed(this, 0);

            }

            // If time is up.

            else if (System.currentTimeMillis() >= end_time) {

                // Reset flag.
                end_time = 0;

                // Update global flags.
                is_flipping = false;
                flipped = true;

                // Visually make the reset button accessible.
                reset_btn.setBtnColours(DisplayItem.COLOURS.LIGHT);

            }

            // If flickering.

            else {

                // If light, make dark.
                if (light) {
                    coin_box.setContentColours(DisplayItem.COLOURS.DARK);
                    coin_box.setContentText("0");

                }

                // If dark, make light.
                else {
                    coin_box.setContentColours(DisplayItem.COLOURS.LIGHT);
                    coin_box.setContentText("1");
                }

                // Update colour state.
                light = !light;

                // Re-run (with delay for "visual appeal").
                handler.postDelayed(this, 200);

            }
        }
    };


    // --------------------------------------------------
    // -- [ON_CREATE] + [CLEAR_SYSTEM_UI] ---------------
    // --------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Graphics Setup.

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        clearSystemUI();
        setContentView(R.layout.activity_main);

        // UI Linking.

        coin_box = new DisplayItem(findViewById(R.id.display_text),
                              findViewById(R.id.display_box_background));

        flip_btn = new DisplayItem(findViewById(R.id.flip_btn));

        reset_btn = new DisplayItem(findViewById(R.id.reset_btn));

    }

    // Re-used this method from older projects.
    private void clearSystemUI() {

        // [CLARITY] Gets rid of the top status bar.
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        // [CLARITY] Gets rid of the bottom navigation bar.
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

    }


    // --------------------------------------------------
    // -- [APPLICATION LOGIC] ---------------------------
    // --------------------------------------------------

    // "Flips" the coin; runs the flicker animation.
    public void flip(View view) {

        // Makes sure only flips when its in its default state.
        if (flipped || is_flipping) {
            return;
        }

        // Update flag.
        is_flipping = true;

        // "Disable" the flip button.
        flip_btn.setBtnColours(DisplayItem.COLOURS.DARK);

        // Prep and run the flicker animation.
        handler.post(flicker);

    }

    // Resets the coin
    public void reset(View view) {

        // Makes sure only flips when its in its flipped state.
        if (is_flipping || !flipped) {
            return;
        }

        // Update flag.
        flipped = false;

        // Visually enable the flip button, disable flip button.
        reset_btn.setBtnColours(DisplayItem.COLOURS.DARK);
        flip_btn.setBtnColours(DisplayItem.COLOURS.LIGHT);

        // Reset the coin box.
        coin_box.resetCoin();

    }
}


// ==================================================
// == [DISPLAY_ITEM] CLASS (for organization) =======
// ==================================================


class DisplayItem {


    // -- [VARIABLES] -----------------------------------
    // -- (Note: different behaviour depending on view) -


    // If linear layout.
    LinearLayout coin_background;
    TextView coin_text; // todo RECONFIGURE TO USE COIN

    // If button.
    Button button;

    // Just for organization.
    enum COLOURS {
        LIGHT,
        DARK
    }


    // --------------------------------------------------
    // -- [CONSTRUCTORS] --------------------------------
    // --------------------------------------------------


    // Constructor for if linear layout (coin).
    public DisplayItem(TextView textView, LinearLayout linearLayout) {
        coin_text = textView;
        coin_background = linearLayout;
    }

    // Constructor for if button.
    public DisplayItem(Button button) {
        this.button = button;
    }


    // --------------------------------------------------
    // -- [BUTTON METHODS] ------------------------------
    // --------------------------------------------------


    // Sets the colour scheme of a button (DARK or LIGHT).
    public void setBtnColours(COLOURS colour) {

        // Ensures that this is being acted on a BUTTON.
        if (button == null) {
            return;
        }

        // Define colour variables.
        int primary = 0;
        int secondary = 0;

        // Sets the colour variables based on the colour input.
        switch (colour) {

            case LIGHT:
                primary = Color.parseColor("#FFFFFF");
                secondary = Color.parseColor("#222222");
                break;

            case DARK:
                primary = Color.parseColor("#222222");
                secondary = Color.parseColor("#FFFFFF");
                break;

        }

        // Applies said colours.
        button.setBackgroundColor(primary);
        button.setTextColor(secondary);

    }


    // --------------------------------------------------
    // -- [COIN METHODS] --------------------------------
    // --------------------------------------------------


    // Sets the colour scheme of the coin (DARK or LIGHT).
    public void setContentColours(COLOURS colour) {

        // Ensures that this is being acted on the coin (LinearLayout).
        if (coin_background == null) {
            return;
        }

        // Define colour variables.
        int primary = 0;
        int secondary = 0;

        // Sets the colour variables based on the colour input.
        switch (colour) {

            case LIGHT:
                primary = Color.parseColor("#FFFFFF");
                secondary = Color.parseColor("#222222");
                break;

            case DARK:
                primary = Color.parseColor("#222222");
                secondary = Color.parseColor("#FFFFFF");
                break;

        }

        // Apply said colours.
        coin_background.setBackgroundColor(primary);
        coin_text.setTextColor(secondary);

    }

    // Changes text in coin box to param.
    public void setContentText(String string) {
        coin_text.setText(string);
    }

    // Makes the text in content coin box bigger (so that 1/0 looks good).
    public void buffText() {
        coin_text.setText(""); // Will show COIN super big for a moment if not done.
        coin_text.setTextSize(122); // In sp.
    }

    // Resets the coin's appearance.
    public void resetCoin() {
        coin_text.setTextSize(32);
        coin_text.setText("COIN");
        setContentColours(COLOURS.LIGHT);
    }

}