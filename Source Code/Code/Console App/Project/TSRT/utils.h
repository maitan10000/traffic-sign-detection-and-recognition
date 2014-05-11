/* 
 * File:   Utils.h
 * Author: everything
 *
 * Created on March 12, 2014, 3:46 PM
 */

#ifndef UTILS_H
#define	UTILS_H



#endif	/* UTILS_H */

#include "opencv2/objdetect/objdetect.hpp"
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/imgproc/imgproc.hpp"

#include <opencv2/core/core.hpp>
#include <opencv2/features2d/features2d.hpp>
#include <opencv2/nonfree/features2d.hpp>
#include <opencv2/calib3d/calib3d.hpp> 

#include <iostream>
#include <stdio.h>
#include <dirent.h>
#include <math.h>
#include <iostream>
#include <fstream>
using namespace std;
using namespace cv;

vector<float> extractFeature(const char* imagePath);
void appendLineToFile(const char* filePath, string line);
void appendFeatureToFile1(const char* imagePath, const char* fileSavePath, int label);
void appendFeatureToFile2(Mat image, const char* fileSavePath, int label);
string exec(char* cmd);
vector<string> explode(const string& str, const char& ch);
void SVMClassifier(char* workingDir, char* inputFile, char* modelFile, char* outputFile, char* labelFile, string &lableResult, float &rateResult);
int SVMLearn(char* workingDir, char* modelFile, char* labelFile, char* trainImageDir, float tradeOffNum, bool deleteFeatureFile);