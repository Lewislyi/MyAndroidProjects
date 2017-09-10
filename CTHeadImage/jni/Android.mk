LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := CTHeadImage
LOCAL_SRC_FILES := CTheadJNI.cpp CTheadModel.cpp
LOCAL_LDLIBS:=  -landroid -llog
#LOCAL_C_INCLUDES:= ${ANDROID_NDK_HOME}/sources/cxx-stl/stlport/stlport/stl/ 
LOCAL_C_INCLUDES += ${ANDROID_NDK_HOME}/sources/cxx-stl/stlport/stlport/
#LOCAL_C_INCLUDES:= ${ANDROID_NDK_HOME}/sources/cxx-stl/gnu-libstdc++/4.9/include/ext
#sLOCAL_CFLAGS:='-D_STLP_USE_NEWALLOC'
#LOCAL_CPPFLAGS += -fexceptions
#LOCAL_SHARED_LIBRARY:= ${ANDROID_NDK_HOME}/sources/cxx-stl/stlport/stlport/libs/armeabi/libstlport_shared.so
#APP_STL := gnustl_static
include $(BUILD_SHARED_LIBRARY)
