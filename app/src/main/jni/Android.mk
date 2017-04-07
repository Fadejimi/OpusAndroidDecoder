LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := libopus
LOCAL_SRC_FILES := lib/libopus.a
LOCAL_EXPORT_LDLIBS := -llog
include $(PREBUILT_STATIC_LIBRARY)

# wrapper info
include $(CLEAR_VARS)
LOCAL_C_INCLUDES := include/opus
LOCAL_MODULE    := OpusDecoder
LOCAL_SRC_FILES := com_opusandroiddecoder_OpusDecoder.c

LOCAL_LDLIBS := -lz
LOCAL_STATIC_LIBRARIES := libopus
include $(BUILD_SHARED_LIBRARY)

