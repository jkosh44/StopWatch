package hu.ait.android.stopwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private LinearLayout layoutContainer;
    private TextView tvTime;
    private Timer timer;
    private int counter = 0;
    private String time;


    private class MyCounterTask extends TimerTask {
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    time = getString(R.string.txt_ellapsed_time, counter++);
                    tvTime.setText(time);
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutContainer = (LinearLayout) findViewById(R.id.layoutContainer);
        tvTime = (TextView) findViewById(R.id.tvTime);


    }

    public void onClick(View v) {
        if (v.getId() == R.id.btnMark) {

            //save the note
            final View noteRow = getLayoutInflater().inflate(R.layout.note_row, null);

            TextView tvNote = (TextView) noteRow.findViewById(R.id.tvNote);
            tvNote.setText(time);
            layoutContainer.addView(noteRow, 0);
            final Animation appearAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.mark_anim);
            noteRow.startAnimation(appearAnimation);


        } else if (v.getId() == R.id.btnStop) {
            layoutContainer.removeAllViews();
            if (timer != null) {
                timer.cancel();
            }
            counter = 0;
            tvTime.setVisibility(TextView.GONE);
        } else if (v.getId() == R.id.btnStart) {
            timer = new Timer();
            tvTime.setVisibility(TextView.VISIBLE);
            timer.schedule(new MyCounterTask(), 0, 1000);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();


    }

}
