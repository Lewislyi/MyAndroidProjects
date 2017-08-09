LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := NDKTest
LOCAL_SRC_FILES := com_example_ndktest_hellojni.c

MY_SWIG_PACKAGE:= com.apress.swig
MY_SWIG_INTERFACE:=Unix.i
MY_SWIG_TYPE:=c
NDK_PROJECT_PATH£º= ~/workspace/NDKTest/

include $(LOCAL_PATH)/my-swig-generate.mk
include $(BUILD_SHARED_LIBRARY)
