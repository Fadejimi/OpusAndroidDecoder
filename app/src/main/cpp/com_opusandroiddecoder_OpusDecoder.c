//
// Created by Fadejimi Adegbulugbe on 05/04/2017.
//

#include "com_opusandroiddecoder_OpusDecoder.h"
#include <string.h>
#include <android/log.h>
#include "libopus.a"
#include "stdio.h"
#include "include/opus/opus_types.h"
#include "include/opus/opus_defines.h" #include "include/opus/opus.h"

//Fields
char logMsg[255];
OpusDecoder *dec;

//Config
opus_int32 SAMPLING_RATE;
int CHANNELS;
int APPLICATION_TYPE = OPUS_APPLICATION_VOIP;
int FRAME_SIZE;
//--

/*
 * Class:     com_opusandroiddecoder_OpusDecoder
 * Method:    nativeInitDecoder
 * Signature: (I;I;I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_opusandroiddecoder_OpusDecoder_nativeInitDecoder (JNIEnv *env, jobject obj, jint samplingRate, jint numberOfChannels, jint frameSize )
{
    FRAME_SIZE = frameSize;
    SAMPLING_RATE = samplingRate;
    CHANNELS = numberOfChannels;

    int size;
    int error;

    size = opus_decoder_get_size(CHANNELS);
    dec = malloc(size);
    error = opus_decoder_init(dec, SAMPLING_RATE, CHANNELS);

    sprintf(logMsg, "Initialized Decoder with ErrorCode: %d", error);
    __android_log_write(ANDROID_LOG_DEBUG, "Native Code:", logMsg);

    return error;
}


void main(){}