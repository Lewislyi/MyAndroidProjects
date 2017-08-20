#include "CTheadJNI.h"
#include "CTheadModel.h"
#include <jni.h>
#include <stddef.h>
#include <android/log.h>
#include <android/asset_manager_jni.h>

#define TAG "CThead" // 这个是自定义的LOG的标识
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__) // 定义LOGI类型

JNIEXPORT void JNICALL Java_com_example_ctheadimage_CTheadJNI_readFromAssets(JNIEnv *env, jclass tis ,jobject assetManager,jstring filename)
{
   LOGI("ReadAssets");
   AAssetManager* mgr = AAssetManager_fromJava(env, assetManager);
   if(mgr==NULL)
   {
	  LOGI(" %s","AAssetManager==NULL");
	  return ;
   }
	/*获取文件名并打开*/
   jboolean iscopy;
   const char *mfile = env->GetStringUTFChars(filename, &iscopy);
   AAsset* asset = AAssetManager_open(mgr, mfile,AASSET_MODE_UNKNOWN);
   env->ReleaseStringUTFChars(filename, mfile);
   if(asset==NULL)
   {
	  LOGI(" %s","asset==NULL");
	  return ;
   }
   /*获取文件大小*/
   off_t bufferSize = AAsset_getLength(asset);
   LOGI("file size: %d\n",bufferSize);

   CTheadModel::GetInstance()->LoadCTImageData(asset);
	/*关闭文件*/
   AAsset_close(asset);
}

JNIEXPORT void JNICALL Java_com_example_ctheadimage_CTheadJNI_readSideData(JNIEnv *env, jobject obj, jint slide, jintArray arrSide, jint size)
{
	jboolean iscopy;
	int *arrData = env->GetIntArrayElements(arrSide, &iscopy);
	CTheadModel::GetInstance()->GetSideImage(slide, arrData, size);
}

JNIEXPORT void JNICALL Java_com_example_ctheadimage_CTheadJNI_readTopData(JNIEnv *env, jobject obj, jint slide, jintArray arrTop, jint size)
{
	jboolean iscopy;
	int *arrData = env->GetIntArrayElements(arrTop, &iscopy);
	CTheadModel::GetInstance()->GetTopImage(slide, arrData, size);
}

JNIEXPORT void JNICALL Java_com_example_ctheadimage_CTheadJNI_readFrontData(JNIEnv *env, jobject obj, jint slide, jintArray arrFront, jint size)
{
	jboolean iscopy;
	int *arrData = env->GetIntArrayElements(arrFront, &iscopy);
	CTheadModel::GetInstance()->GetFrontImage(slide, arrData, size);
}
