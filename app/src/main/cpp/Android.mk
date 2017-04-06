LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := OpusDecoder

LOCAL_SRC_FILES := com_opusandroiddecoder_OpusDecoder.c

LOCAL_LDLIBS := -L$(SYSROOT)/usr/lib -llog
LOCAL_STATIC_LIBRARIES := opus
include $(BUILD_SHARED_LIBRARY)

$(call import-module,opus)