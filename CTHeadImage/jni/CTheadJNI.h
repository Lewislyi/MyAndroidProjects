/*
 * CTHeadImage.h
 *
 *  Created on: Aug 15, 2017
 *      Author: lewis
 */
#ifndef CTHEADIMAGE_H_
#define CTHEADIMAGE_H_
#include <jni.h>
#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL Java_com_example_ctheadimage_CTheadJNI_readFromAssets(JNIEnv* env,jclass tis, jobject assetManager,jstring filename);

JNIEXPORT void JNICALL Java_com_example_ctheadimage_CTheadJNI_readSideData(JNIEnv *env, jobject obj, jint slide, jintArray arrSide, jint size);

JNIEXPORT void JNICALL Java_com_example_ctheadimage_CTheadJNI_readFrontData(JNIEnv *env, jobject obj, jint slide, jintArray arrFront, jint size);

JNIEXPORT void JNICALL Java_com_example_ctheadimage_CTheadJNI_readTopData(JNIEnv *env, jobject obj, jint slide, jintArray arrTop, jint size);

JNIEXPORT void JNICALL Java_com_example_ctheadimage_CTheadJNI_readSideHL(JNIEnv *env, jobject obj, jintArray arrSideHL, jint size);

JNIEXPORT void JNICALL Java_com_example_ctheadimage_CTheadJNI_readTopHL(JNIEnv *env, jobject obj, jintArray arrTopHL, jint size);

JNIEXPORT void JNICALL Java_com_example_ctheadimage_CTheadJNI_readFrontHL(JNIEnv *env, jobject obj, jintArray arrFrontHL, jint size);

JNIEXPORT void JNICALL Java_com_example_ctheadimage_CTheadJNI_readHistogram(JNIEnv *env, jobject obj, jintArray arrCTData, jint size);
#ifdef __cplusplus
}
#endif

#endif /* CTHEADIMAGE_H_ */
