LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := CTHeadImage
LOCAL_SRC_FILES := CTheadJNI.cpp CTheadModel.cpp
LOCAL_LDLIBS:= -landroid -llog

include $(BUILD_SHARED_LIBRARY)
