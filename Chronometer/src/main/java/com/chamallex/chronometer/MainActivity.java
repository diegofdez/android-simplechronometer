package com.chamallex.chronometer;

import android.os.Bundle;
import android.app.Activity;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class MainActivity extends Activity {

    ChronoStatus status;
    Long pauseTime = 0L;
    private TextView chronoLabel;
    private Button startButton;
    private Button stopButton;
    private Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = ChronoStatus.STOPPED;
        chronoLabel = (TextView) findViewById(R.id.chronoLabel);
        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        chronometer = (Chronometer) findViewById(R.id.chronometer);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.equals(ChronoStatus.STOPPED))
                {
                    chronoLabel.setText("Running");
                    startButton.setText("Pause");
                    Log.v("Stop2Run", "Start chrono run");
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    status = ChronoStatus.RUNNING;
                }
                else if (status.equals(ChronoStatus.RUNNING))
                {
                    chronoLabel.setText("Paused");
                    startButton.setText("Run");
                    Log.v("Run2Pause", "Pause chrono");
                    chronometer.stop();
                    pauseTime = SystemClock.elapsedRealtime();
                    status = ChronoStatus.PAUSED;
                }
                else if (status.equals(ChronoStatus.PAUSED))
                {
                    chronoLabel.setText("Running");
                    startButton.setText("Pause");
                    Log.v("Pause2Run", "Restart chrono run");
                    chronometer.setBase(
                            SystemClock.elapsedRealtime() - (pauseTime - chronometer.getBase()));
                    chronometer.start();
                    status = ChronoStatus.RUNNING;
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronoLabel.setText("Stopped");
                startButton.setText("Start");
                Log.v("Stop", "Stop chrono");
                chronometer.stop();
                status = ChronoStatus.STOPPED;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
