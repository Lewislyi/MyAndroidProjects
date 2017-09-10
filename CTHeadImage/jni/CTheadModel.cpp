#include "CTheadModel.h"

#include <unistd.h>
#include <stdlib.h>
#include <stddef.h>
#include <stdio.h>
#include <string.h>
#include <hash_map>
#define TAG "CTheadModel" // ������Զ����LOG�ı�ʶ
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG,__VA_ARGS__) // ����LOGI����

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
		for(int j = 0; j < 256; j++){
			for(int z = 0; z < 256; z++){
				if(m_cthead[i][j][z] > m_Max)
					m_Max = m_cthead[i][j][z];
				if(m_cthead[i][j][z] < m_Min)
					m_Min = m_cthead[i][j][z];
			}
		}
	}
//Convert to 4 bits color
	for(int i = 0; i < 113; i++){
		for(int j = 0; j < 256; j++){
			for(int z = 0; z < 256; z++){
				short tmpdat = (short)(255.0f*((float)m_cthead[i][j][z]-(float)m_Min)/((float)(m_Max-m_Min)));
				m_ctcolor[i][j][z] = (tmpdat << 16 & 0x00FF0000) |
				        (tmpdat << 8 & 0x0000FF00 ) |
				        (tmpdat & 0x000000FF ) |
				         0xFF000000;
			}
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
			if(z < sizeof(color)){
//				tmp = (short)(255.0f*((float)m_cthead[i][j][slide]-(float)m_Min)/((float)(m_Max-m_Min)));
//				color[z] = (tmp << 16 & 0x00FF0000) |
//				        (tmp << 8 & 0x0000FF00 ) |
//				        (tmp & 0x000000FF ) |
//				         0xFF000000;
				color[z] = m_ctcolor[i][j][slide];
				z++;
			}
		}
//ת��512*512����ͼ��
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

void CTheadModel::GetSideHighLight(int arrSideImg[], int size){
	int z=0;
	short tmp, datmax;
	int color[113*256];
	for(int i = 0; i < 113; i++){
		for(int j = 0; j < 256; j++){
			int k = 0;
			datmax = m_cthead[i][j][k];
			while(k < 256){
				if(m_cthead[i][j][k] > datmax){
					datmax = m_cthead[i][j][k];
					tmp = k;
				}
				k++;
			}
			if(z < sizeof(color)){
				tmp = (short)(255.0f*((float)datmax-(float)m_Min)/((float)(m_Max-m_Min)));
				color[z] = (tmp << 16 & 0x00FF0000) |
				        (tmp << 8 & 0x0000FF00 ) |
				        (tmp & 0x000000FF ) |
				         0xFF000000;
				//color[z] = m_ctcolor[i][j][tmp];
				z++;
			}
		}
	}
//ת��512*512����ͼ��
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
//				tmp = (short)(255.0f*((float)m_cthead[slide][i][j]-(float)m_Min)/((float)(m_Max-m_Min)));
//				color[z] = (tmp << 16 & 0x00FF0000) |
//				        (tmp << 8 & 0x0000FF00 ) |
//				        (tmp & 0x000000FF ) |
//				         0xFF000000;
				color[z] = m_ctcolor[slide][i][j];
				z++;
			}
		}
//ת��512*512����ͼ��
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

void CTheadModel::GetTopHighLight(int arrTopImg[], int size){
	int z=0;
	short datmax;
	int tmp;
	int color[256*256];
	for(int i = 0; i < 256; i++){
		for(int j = 0; j < 256; j++){
			int k = 0;
			datmax = m_cthead[k][i][j];
			while(k < 113){
				if(m_cthead[k][i][j] > datmax){
					datmax = m_cthead[k][i][j];
					tmp = k;
				}
				k++;
			}
			if(z < sizeof(color)){
				tmp = (short)(255.0f*((float)datmax-(float)m_Min)/((float)(m_Max-m_Min)));
				color[z] = (tmp << 16 & 0x00FF0000) |
				        (tmp << 8 & 0x0000FF00 ) |
				        (tmp & 0x000000FF ) |
				         0xFF000000;
				//LOGI("dataset Top HighLight: %d %d\n",tmp, size);
				//color[z] = m_ctcolor[tmp][i][j];
				z++;
			}
		}
	}
//ת��512*512����ͼ��
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
			if(z < sizeof(color)){
//				tmp = (short)(255.0f*((float)m_cthead[i][slide][j]-(float)m_Min)/((float)(m_Max-m_Min)));
//				color[z] = (tmp << 16 & 0x00FF0000) |
//				        (tmp << 8 & 0x0000FF00 ) |
//				        (tmp & 0x000000FF ) |
//				         0xFF000000;
				color[z] = m_ctcolor[i][slide][j];
				z++;
			}
		}
//ת��512*512����ͼ��
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

void CTheadModel::GetFrontHighLight(int arrFrontImg[], int size){
	int z=0;
	short tmp = 0;
	int color[256*113];
	short datmax;
	for(int i = 0; i < 113; i++){
		for(int j = 0; j < 256; j++){
			int k = 0;
			datmax = m_cthead[i][k][j];
			while(k < 256){
				if(m_cthead[i][k][j] > datmax){
					datmax = m_cthead[i][k][j];
					tmp = k;
				}
				k++;
			}
			if(z < sizeof(color)){
				tmp = (short)(255.0f*((float)datmax-(float)m_Min)/((float)(m_Max-m_Min)));
				color[z] = (tmp << 16 & 0x00FF0000) |
						(tmp << 8 & 0x0000FF00 ) |
						(tmp & 0x000000FF ) |
						 0xFF000000;
				//color[z] = m_ctcolor[i][tmp][j];
				z++;
			}
		}
	}
//ת��512*512����ͼ��
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

void CTheadModel::GetHistogram(int colorData[], int nSize){
	std::hash_map<int, int> corMap;
	std::hash_map<int, float> percentMap;
	float percent;
	int nMin = 10000;
	int nMax = 0;
	//��ͳ�����ص�
	for(int i = 0; i < nSize; i++){
		if(colorData[i] < nMin)
			nMin = colorData[i];
		if(colorData[i] > nMax)
			nMax = colorData[i];
		corMap[colorData[i]]++;
	}
	for(std::hash_map<int, int>::iterator iter = corMap.begin(); iter != corMap.end(); ++iter){
		int m_key = iter->first;
		percent = (float)corMap[m_key] / (float)nSize;
		for(std::hash_map<int, int>::iterator iter2 = corMap.begin(); iter2 != corMap.end(); ++iter2)
		{
			int k_key = iter2->first;
			if(k_key < m_key)
				percent = percent + ((float)corMap[k_key] / (float)nSize);
		}
		percentMap[m_key] = percent;
	}

	for(int i = 0; i < nSize; i++){
		//���û��۸���ӳ�䵽��min-max���Ҷȼ���
		short processPixcel = (short)(percentMap[colorData[i]] * 255.0f);
					//�ٽ����ص�ץ����4�ֽ�����
		colorData[i] = (processPixcel << 16 & 0x00FF0000) |
					        (processPixcel << 8 & 0x0000FF00 ) |
					        (processPixcel & 0x000000FF ) |
					         0xFF000000;
		//colorData[i] = (percentMap[colorData[i]] * (nMax - nMin));
	}
	return;
}

