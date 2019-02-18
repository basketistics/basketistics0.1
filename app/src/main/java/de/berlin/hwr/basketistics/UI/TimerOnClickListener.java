package de.berlin.hwr.basketistics.UI;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

public class TimerOnClickListener implements View.OnClickListener {
    private static final String TAG = "TimerOnClickListener";
    CountDownTimer timer;
    public TimerOnClickListener(CountDownTimer timer)
    {
        this.timer = timer;
    }


    public void pauseTimer(CountDownTimer timer)
    {
        Log.i(TAG, "Timer Paused");
        timer.cancel();
    }


    @Override
    public void onClick(View v) {
        Log.i(TAG, "Timer stopped.");
        pauseTimer(timer);
    }
}
