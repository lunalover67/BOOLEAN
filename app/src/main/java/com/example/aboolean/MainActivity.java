package com.example.aboolean;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    DisplayItem box, flip_btn, reset_btn;
    boolean flipped, is_flipping = false;
    Random random = new Random();

    final Handler handler = new Handler();
    final Runnable flicker = new Runnable() {

        boolean light = true;
        long end_time = 0;

        @Override
        public void run() {

            if (end_time == 0) {
                end_time =  System.currentTimeMillis() + 1000 + random.nextInt(3000);
                light = true;
                box.buffText();

                handler.postDelayed(this, 0);

            }
            else if (System.currentTimeMillis() >= end_time) {
                end_time = 0;
                is_flipping = false;
                flipped = true;
                reset_btn.setBtnColours(DisplayItem.COLOURS.LIGHT);

            }
            else {

                if (light) {
                    box.setContentColours(DisplayItem.COLOURS.DARK);
                    box.setContentText("0");

                } else {
                    box.setContentColours(DisplayItem.COLOURS.LIGHT);
                    box.setContentText("1");
                }

                light = !light;

                handler.postDelayed(this, 150);

            }
        }
    };

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

        Log.d("flipped", String.valueOf(flipped));
        Log.d("is_flipping", String.valueOf(is_flipping));

        if (flipped || is_flipping) {
            return;
        }

        Log.d("condition", "PASSED");

        is_flipping = true;

        flip_btn.setBtnColours(DisplayItem.COLOURS.DARK);
        reset_btn.setBtnColours(DisplayItem.COLOURS.DARK);


        handler.post(flicker);
    }

    public void reset(View view) {

        if (is_flipping || !flipped) {
            return;
        }

        flipped = false;
        reset_btn.setBtnColours(DisplayItem.COLOURS.DARK);
        flip_btn.setBtnColours(DisplayItem.COLOURS.LIGHT);
        box.resetBox();

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
    LinearLayout border; // not rly needed
    LinearLayout background;
    Button button;

    enum COLOURS {
        LIGHT,
        DARK
    }

    public DisplayItem(TextView textView, LinearLayout linearLayoutBorder, LinearLayout linearLayoutBackground) {
        text = textView;
        border = linearLayoutBorder;
        background = linearLayoutBackground;
    }

    public DisplayItem(LinearLayout linearLayoutBorder, Button button) {
        this.button = button;
        border = linearLayoutBorder;
    }

    public void setBtnColours(COLOURS colour) {

        if (button == null) {
            return;
        }

        int primary = 0;
        int secondary = 0;

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

        button.setBackgroundColor(primary);
        button.setTextColor(secondary);
    }

    public void setContentColours(COLOURS colour) {

        if (background == null) {
            return;
        }

        int primary = 0;
        int secondary = 0;

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

        background.setBackgroundColor(primary);
        text.setTextColor(secondary);

    }

    public void setContentText(String string) {
        text.setText(string);
    }

    public void buffText() {
        text.setText("");
        text.setTextSize(122); //sp
    }

    public void resetBox() {
        text.setTextSize(32);
        text.setText("COIN");
        setContentColours(COLOURS.LIGHT);
    }

}