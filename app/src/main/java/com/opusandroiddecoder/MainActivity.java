package com.opusandroiddecoder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button playButton;
    private Play play;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = new Play(this);

        playButton = (Button) findViewById(R.id.play_btn);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( play.isPlaying )
                {
                    stopPlaying();
                }
                else
                {

                    startPlaying();
                }
            }
        });
    }


    /**
     *	Starts the thread to play a recorded file.
     */
    private void startPlaying()
    {

        new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    play.playFile( "ms.opus" );
                    Log.d( TAG, "Play thread end" );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } ).start();


    }

    /**
     *	Stops the playback thread.
     */
    private void stopPlaying()
    {
        this.play.stopPlaying();

    }
}
