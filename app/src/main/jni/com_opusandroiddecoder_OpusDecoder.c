//
// Created by Fadejimi Adegbulugbe on 05/04/2017.
//

#include "com_opusandroiddecoder_OpusDecoder.h"
#include "string.h"
#include "android/log.h"
#include "stdio.h"
#include "include/opus/opus.h"

//Fields
char logMsg[255];
OpusDecoder *dec;

//Config
opus_int32 SAMPLING_RATE;
int CHANNELS;
//int APPLICATION_TYPE = OPUS_APPLICATION_VOIP;
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

/*
 * Class:     com_opusandroiddecoder_OpusDecoder
 * Method:    nativeDecodeBytes
 * Signature: ([B;[S)I
 */
JNIEXPORT jint JNICALL Java_com_opusandroiddecoder_nativeDecodeBytes (JNIEnv *env, jobject obj, jbyteArray in, jshortArray out)
{
    __android_log_write(ANDROID_LOG_DEBUG, "Native Code:", "Opus decoding");

    sprintf(logMsg, "FrameSize: %d - SamplingRate: %d - Channels: %d", FRAME_SIZE, SAMPLING_RATE, CHANNELS);
    __android_log_write(ANDROID_LOG_DEBUG, "Native Code:", logMsg);

    jint inputArraySize = (*env)->GetArrayLength(env, in);
    jint outputArraySize = (*env)->GetArrayLength(env, out);

    sprintf(logMsg, "Length of Input Array: %d", inputArraySize);
    __android_log_write(ANDROID_LOG_DEBUG, "Native Code:", logMsg);
    sprintf(logMsg, "Length of Output Array: %d", outputArraySize);
    __android_log_write(ANDROID_LOG_DEBUG, "Native Code:", logMsg);

    jbyte* encodedData = (*env)->GetByteArrayElements(env, in, 0);
    opus_int16 *data = (opus_int16*)calloc((size_t)outputArraySize,sizeof(opus_int16));
    int decodedDataArraySize = opus_decode(dec, (const unsigned char *) encodedData, inputArraySize, data, FRAME_SIZE, 0);

    sprintf(logMsg, "Length of Decoded Data: %d", decodedDataArraySize);
    __android_log_write(ANDROID_LOG_DEBUG, "Native Code:", logMsg);

    if (decodedDataArraySize >=0)
    {
        if (decodedDataArraySize <= outputArraySize)
        {
            (*env)->SetShortArrayRegion(env,out,0,decodedDataArraySize,data);
        }
        else
        {
            sprintf(logMsg, "Output array of size: %d to small for storing encoded data.", outputArraySize);
            __android_log_write(ANDROID_LOG_DEBUG, "Native Code:", logMsg);

            return -1;
        }
    }

    (*env)->ReleaseByteArrayElements(env,in,encodedData,JNI_ABORT);

    return decodedDataArraySize;
}

/*
 * Class:     com_opusandroiddecoder_OpusDecoder
 * Method:    nativeReleaseDecoder
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_opusandroiddecoder_OpusDecoder_nativeReleaseDecoder (JNIEnv *env, jobject obj)
{
    return 1;
}
