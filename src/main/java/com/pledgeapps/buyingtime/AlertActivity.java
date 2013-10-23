package com.pledgeapps.buyingtime;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;



import java.text.SimpleDateFormat;
import java.util.Date;


public class AlertActivity extends Activity {


    TextView currentTime;
    Button snoozeButton;
    Button dismissButton;
    Handler refreshHandler;
    String previousDisplayTime = "";
    SimpleDateFormat formatter = new SimpleDateFormat("h:mma");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);


        setContentView(R.layout.activity_alert);

        currentTime = (TextView) findViewById(R.id.currentTime);
        snoozeButton = (Button) findViewById(R.id.snoozeButton);
        dismissButton = (Button) findViewById(R.id.dismissButton);

        snoozeButton.setOnClickListener( new View.OnClickListener() {public void onClick(View view) {snooze();}} );
        dismissButton.setOnClickListener( new View.OnClickListener() {public void onClick(View view) {dismiss();}} );


        refreshHandler= new Handler();
        refreshHandler.postDelayed(refreshRunnable, 1000);
        updateScreen(true);



        // Register to get the alarm killed intent.
        //registerReceiver(mReceiver, new IntentFilter(Alarms.ALARM_KILLED));


    }


    private Runnable refreshRunnable = new Runnable() {
        public void run() {
            try {
                updateScreen(false);
            } catch (Exception e) {}
            refreshHandler.postDelayed(this, 1000);
        }
    };


    public void updateScreen(boolean forceRefresh)
    {
        String displayTime = formatter.format(new Date()).toLowerCase().replace("m", "");
        if (!displayTime.equals(previousDisplayTime))
        {
            currentTime.setText(displayTime);
            previousDisplayTime=displayTime;
        }
    }


    @Override
    public void onPause()
    {
        new Thread(new Runnable() {
            public void run() {
                snooze();
            }
        }).start();
        super.onPause();
    }



    private void snooze()
    {

    }

    private void dismiss()
    {

    }


}