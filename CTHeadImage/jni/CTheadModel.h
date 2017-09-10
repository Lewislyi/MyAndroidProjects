/*
 * CTheadModel.h
 *
 *  Created on: Aug 19, 2017
 *      Author: lewis
 */

#ifndef CTHEADMODEL_H_
#define CTHEADMODEL_H_
#define DATA_SIZE 113*256*256
#include <jni.h>
#include <android/log.h>
#include <android/asset_manager_jni.h>
class CTheadModel{
public:
	CTheadModel();
	~CTheadModel();
	void LoadCTImageData(AAsset *asset);
	void GetSideImage(int slide, int arrSideImg[], int size);
	void GetTopImage(int slide, int arrTopImg[], int size);
	void GetFrontImage(int slide, int arrFrontImg[], int size);
	void GetFrontHighLight(int arrFrontImg[], int size);
	void GetTopHighLight(int arrTopImg[], int size);
	void GetSideHighLight(int arrSideImg[], int size);
	void GetHistogram(int corData[], int nSize);
	static CTheadModel *GetInstance();
	short m_Max;
	short m_Min;
private:
	short m_cthead[113][256][256]={0};
	int m_ctcolor[113][256][256]={0};
	static CTheadModel *instance;
};




#endif /* CTHEADMODEL_H_ */
