LOCAL_PATH := $(call my-dir)

# static library info
LOCAL_MODULE := libYourLibrary
LOCAL_SRC_FILES := ../libs/lib/libopus.a
LOCAL_EXPORT_C_INCLUDES := ../lib/include
include $(PREBUILT_STATIC_LIBRARY)

# wrapper info
include $(CLEAR_VARS)
LOCAL_C_INCLUDES += ../libs/opus/include
LOCAL_MODULE    := OpusDecoder.c
LOCAL_SRC_FILES := com_opusandroiddecoder_OpusDecoder.c

LOCAL_STATIC_LIBRARIES := opus
include $(BUILD_SHARED_LIBRARY)