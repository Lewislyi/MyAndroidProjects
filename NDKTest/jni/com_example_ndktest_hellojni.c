#include <jni.h>
#include <string.h>

JNIEXPORT jstring JNICALL Java_com_example_ndktest_hellojni_NDKTestFromJNI(JNIEnv *env, jobject jobj)
{
	return (*env)->NewStringUTF(env, "Hello JNI");
}
