#include "CTheadModel.h"

#include <unistd.h>
#include <stdlib.h>
#include <stddef.h>
#include <stdio.h>
#include <string.h>

#define TAG "CTheadModel" // 这个是自定义的LOG的标识
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__) // 定义LOGI类型

CTheadModel *CTheadModel::instance = NULL;
CTheadModel::CTheadModel(){
	memset(m_cthead, 0, DATA_SIZE);
	m_Max = 0;
	m_Min = 0;
}

CTheadModel::~CTheadModel(){
}

CTheadModel *CTheadModel::GetInstance(){
	if(instance == NULL){
		instance = new CTheadModel();
	}
	return instance;
}

void CTheadModel::LoadCTImageData(AAsset *asset){
	char *buf = (char *)AAsset_getBuffer(asset);
	int length = AAsset_getLength(asset);
	LOGI("array size: %d\n",sizeof(m_cthead));
	if(length <= sizeof(m_cthead))
		memcpy(m_cthead, buf, length);

	for(int i = 0; i < 113; i++){
		for(int j = 0; j < 256; j++)
			for(int z = 0; z < 256; z++){
				if(m_cthead[i][j][z] > m_Max)
					m_Max = m_cthead[i][j][z];
				if(m_cthead[i][j][z] < m_Min)
					m_Min = m_cthead[i][j][z];
			}
	}
	LOGI("dataset Max: %d, Min: %d\n",m_Max, m_Min);
	return;
}

void CTheadModel::GetSideImage(int slide, int arrSideImg[], int size){
	int z=0;
	short tmp = 0;
	int color[113*256];
	for(int i = 0; i < 113; i++)
		for(int j = 0; j < 256; j++){
			if(z < size){
				tmp = (short)(255.0f*((float)m_cthead[i][j][slide]-(float)m_Min)/((float)(m_Max-m_Min)));
				color[z] = (tmp << 16 & 0x00FF0000) |
				        (tmp << 8 & 0x0000FF00 ) |
				        (tmp & 0x000000FF ) |
				         0xFF000000;
				z++;
			}
		}
//转成512*512像素图像
		for(int i = 0; i < 512; i++){
			for(int j = 0; j < 512; j++){
				int oldI = (int)(((float)i/511) * (113 - 1));
				int oldJ = (int)(((float)j/511) * (256 -1));
				int oldPos = (256 * oldI) + oldJ;
				int newPos = (i*512) + j;
				if(newPos < size){
					arrSideImg[newPos] = color[oldPos];
				}
			}
		}
}

void CTheadModel::GetTopImage(int slide, int arrTopImg[], int size){
	int z=0;
	short tmp = 0;
	int color[256*256];
	for(int i = 0; i < 256; i++)
		for(int j = 0; j < 256; j++){
			if(z < sizeof(color)){
				tmp = (short)(255.0f*((float)m_cthead[slide][i][j]-(float)m_Min)/((float)(m_Max-m_Min)));
				color[z] = (tmp << 16 & 0x00FF0000) |
				        (tmp << 8 & 0x0000FF00 ) |
				        (tmp & 0x000000FF ) |
				         0xFF000000;
				z++;
			}
		}
//转成512*512像素图像
	for(int i = 0; i < 512; i++){
		for(int j = 0; j < 512; j++){
			int oldI = (int)(((float)i/511) * (256 - 1));
			int oldJ = (int)(((float)j/511) * (256 -1));
			int oldPos = (256 * oldI) + oldJ;
			int newPos = (i*512) + j;
			if(newPos < size){
				arrTopImg[newPos] = color[oldPos];
			}
		}
	}
}

void CTheadModel::GetFrontImage(int slide, int arrFrontImg[], int size){
	int z=0;
	short tmp = 0;
	int color[256*113];
	for(int i = 0; i < 113; i++)
		for(int j = 0; j < 256; j++){
			if(z < size){
				tmp = (short)(255.0f*((float)m_cthead[i][slide][j]-(float)m_Min)/((float)(m_Max-m_Min)));
				color[z] = (tmp << 16 & 0x00FF0000) |
				        (tmp << 8 & 0x0000FF00 ) |
				        (tmp & 0x000000FF ) |
				         0xFF000000;
				z++;
			}
		}
//转成512*512像素图像
		for(int i = 0; i < 512; i++){
			for(int j = 0; j < 512; j++){
				int oldI = (int)(((float)i/511) * (113 - 1));
				int oldJ = (int)(((float)j/511) * (256 -1));
				int oldPos = (256 * oldI) + oldJ;
				int newPos = (i*512) + j;
				if(newPos < size){
					arrFrontImg[newPos] = color[oldPos];
				}
			}
		}
}

char *CTheadModel::GetHistogram(int width, int height){
	return NULL;
}

