LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := libopus
LOCAL_SRC_FILES := lib/libopus.a
include $(PREBUILD_STATIC_LIBRARY)

# wrapper info
include $(CLEAR_VARS)
LOCAL_MODULE    := OpusDecoder.c
LOCAL_SRC_FILES := com_opusandroiddecoder_OpusDecoder.c
LOCAL_C_INCLUDES := $(LOCAL_PATH)/include/opus

LOCAL_LDLIBS   = -lz -lm
LOCAL_CFLAGS   = -Wall -pedantic -std=c99 -g
include $(BUILD_SHARED_LIBRARY)