package com.opusandroiddecoder;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Fadejimi on 07/04/2017.
 */

public class Play {

    private static String TAG = Play.class.getSimpleName();

    private Context context;
    // status flags
    public boolean isPlaying = false;
    public boolean isStopped = false;
    public boolean shouldStopPlaying = false;


    // auto config
    private boolean	isOpusEncoded = true;
    private int	frequency = 16000;
    private int	channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    private int	numberOfChannels = 1;	// Mono
    private int	audioEncoding = AudioFormat.ENCODING_PCM_16BIT; // raw encoding
    private int frameSize = 960;	// The frame_size must an opus frame size for the encoder's sampling rate.
    private final int audioRecBufferSize = 2 * AudioRecord.getMinBufferSize( frequency, channelConfiguration, audioEncoding ); // Buffer for audio output.

    /**
     * Plays a given file.
     *
     * @param file The file to play
     */

    public Play(Context context) {
        this.context = context;
    }
    public void playFile(String fileName) throws IOException
    {
        this.shouldStopPlaying = false;
        this.isPlaying = true;

        AssetManager assets = context.getAssets();
        InputStream inputStream = assets.open(fileName);

        AudioTrack atrack = new AudioTrack( AudioManager.STREAM_MUSIC, frequency, channelConfiguration, audioEncoding, audioRecBufferSize, AudioTrack.MODE_STREAM );
        atrack.play();

        int bytesRead = 0;
        if (this.isOpusEncoded)
        {
            short[] buffer = new short[audioRecBufferSize];

            OpusDecoder decoder = new OpusDecoder( inputStream, frequency, numberOfChannels, frameSize);

            while ( !shouldStopPlaying && bytesRead < inputStream.available() )
            {
                try
                {
                    // Read from the InputStream
                    bytesRead += decoder.read( buffer );
                    atrack.write( buffer, 0, buffer.length );
                    atrack.flush();
                }
                catch ( IOException e )
                {
                    Log.e( TAG, e.getLocalizedMessage(),e );
                    break;
                }
            }
        }
        else //no encoding
        {
            byte[] directBuffer = new byte[audioRecBufferSize];
            while ( !shouldStopPlaying && bytesRead < inputStream.available() )
            {
                try
                {
                    // Read from the InputStream
                    bytesRead += inputStream.read( directBuffer );
                    atrack.write( directBuffer, 0, directBuffer.length );
                    atrack.flush();
                }
                catch ( IOException e )
                {
                    Log.e( TAG, e.getLocalizedMessage(),e );
                    break;
                }
            }
        }
        atrack.stop();

        this.isPlaying = false;
    }

    /**
     * Sets the flag to stop the playing thread.
     */
    public void stopPlaying()
    {
        this.shouldStopPlaying = true;
    }

}
