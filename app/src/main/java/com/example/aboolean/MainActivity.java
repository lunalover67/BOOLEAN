package com.example.aboolean;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    DisplayItem box, flip_btn, reset_btn;
    boolean flipped, is_flipping = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Graphics Setup.

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        clearSystemUI();
        setContentView(R.layout.activity_main);

        // UI Linking

        box = new DisplayItem(findViewById(R.id.display_text),
                findViewById(R.id.display_box_border),
                findViewById(R.id.display_box_background));

        flip_btn = new DisplayItem(findViewById(R.id.flip_border),
                findViewById(R.id.flip_btn));

        reset_btn = new DisplayItem(findViewById(R.id.reset_border),
                findViewById(R.id.reset_btn));
    }

    public void flip(View view) {

        if (flipped || is_flipping) {
            return;
        }

        // disable btns (both)

        // run flip animation (1-3s) :

        while (true) {


        }

        // show result

        // untoggle the reset

    }

    public void reset(View view) {

        if (!flipped) {
            return;
        }

        //
    }

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

}

class DisplayItem {

    TextView text;
    LinearLayout border;
    LinearLayout background;
    Button button;

    public DisplayItem(TextView textView, LinearLayout linearLayoutBorder, LinearLayout linearLayoutBackground) {
        text = textView;
        border = linearLayoutBorder;
        background = linearLayoutBackground;
    }

    public DisplayItem(LinearLayout linearLayoutBorder, Button button) {
        this.button = button;
        border = linearLayoutBorder;
    }

}