package com.opusandroiddecoder;

/**
 * Created by Fadejimi on 07/04/2017.
 */

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.util.Log;


public class OpusDecoder extends FilterInputStream {

    private static String	TAG	= "OpusDecoder";

    //Native methods
    private native boolean nativeInitDecoder( int samplingRate, int numberOfChannels, int frameSize );
    private native int nativeDecodeBytes( byte[] in , short[] out);
    private native boolean nativeReleaseDecoder();

    //Fields
    private InputStream	in;

    static
    {
        try
        {
            System.loadLibrary( "OpusDecoder" );
        }
        catch ( Exception e )
        {
            Log.e( TAG, "Could not load Systemlibrary 'OpusDecoder'" );
        }
    }

    /**
     * Default constructor with 8khz sampling rate, mono channel and 20ms frames
     *
     * @param out InputStream to write to
     */
    protected OpusDecoder( InputStream in )
    {
        this(in, 16000, 1, 960);
    }

    public OpusDecoder( InputStream in, int frequency, int numberOfChannels, int frameSize )
    {
        super( in );
        this.in = in;

        this.nativeInitDecoder(frequency, numberOfChannels, frameSize);
    }
    @Override
    public int read() throws IOException
    {
        throw new UnsupportedOperationException( "Method not implemented in inheriting class" );
    }

    @Override
    public int read( byte[] buffer ) throws IOException
    {
        throw new UnsupportedOperationException( "Method not implemented in inheriting class for byte[]. Please use short[]." );
    }

    public int read( short[] buffer ) throws IOException
    {
        Log.d( TAG, "Buffer Size: " + buffer.length );

        byte[] encodedBuffer = new byte[buffer.length / 5];

        int bytesRead = this.in.read( encodedBuffer );
        Log.d( TAG, bytesRead + " bytes read from input stream" );
        if ( bytesRead >= 0 )
        {
            int bytesEncoded = nativeDecodeBytes( encodedBuffer , buffer);
            Log.d( TAG, bytesEncoded + " bytes encoded" );
        }

        return bytesRead;
    }

    @Override
    public int read( byte[] buffer, int offset, int count ) throws IOException
    {
        throw new UnsupportedOperationException( "Method not implemented in inheriting class" );
    }

    @Override
    public void close() throws IOException
    {
        this.in.close();
        this.nativeReleaseDecoder();
    }


}
