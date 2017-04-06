//
// Created by Fadejimi Adegbulugbe on 05/04/2017.
//

#include <jni.h>
/* Header for class OpusDecoder */

#ifndef _Included_com_opusandroiddecoder_OpusDecoder
#define _Included_com_opusandroiddecoder_OpusDecoder
#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jboolean JNICALL Java_com_opusandroiddecoder_OpusDecoder_nativeInitDecoder
        (JNIEnv *, jobject, jint, jint, jint);

JNIEXPORT jint JNICALL Java_com_opusandroiddecoder_OpusDecoder_nativeDecodeBytes
        (JNIEnv *, jobject, jbyteArray, jshortArray);

JNIEXPORT jboolean JNICALL Java_com_opusandroiddecoder_OpusDecoder_nativeReleaseDecoder
        (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
