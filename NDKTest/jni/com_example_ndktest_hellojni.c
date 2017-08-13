#include <jni.h>
#include <string.h>
#include <android/log.h>
#include <android/asset_manager_jni.h>

JNIEXPORT jstring JNICALL Java_com_example_ndktest_hellojni_NDKTestFromJNI(JNIEnv *env, jobject jobj)
{
	return (*env)->NewStringUTF(env, "Hello JNI");
}

#define TAG "hellojni" // ������Զ����LOG�ı�ʶ
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // ����LOGI����

void Java_com_example_ndktest_hellojni_readFromAssets(JNIEnv* env,jclass tis
,jobject assetManager,jstring filename)
{
   LOGI("ReadAssets");
   AAssetManager* mgr = AAssetManager_fromJava(env, assetManager);
   if(mgr==NULL)
   {
      LOGI(" %s","AAssetManager==NULL");
      return ;
   }

    /*��ȡ�ļ�������*/
   jboolean iscopy;
   const char *mfile = (*env)->GetStringUTFChars(env, filename, &iscopy);
   AAsset* asset = AAssetManager_open(mgr, mfile,AASSET_MODE_UNKNOWN);
   (*env)->ReleaseStringUTFChars(env, filename, mfile);
   if(asset==NULL)
   {
      LOGI(" %s","asset==NULL");
      return ;
   }
   /*��ȡ�ļ���С*/
   off_t bufferSize = AAsset_getLength(asset);
   LOGI("file size: %d\n",bufferSize);
//   char *buffer=(char *)malloc(bufferSize+1);
////   buffer[bufferSize]=0;
////   int numBytesRead = AAsset_read(asset, buffer, bufferSize);
////   LOGI(": %s",buffer);
////   free(buffer);
    /*�ر��ļ�*/
   AAsset_close(asset);
}
