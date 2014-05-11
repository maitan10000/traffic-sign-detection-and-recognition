/* 
 * File:   trafficsignUtil.h
 * Author: everything
 *
 * Created on February 23, 2014, 8:00 PM
 */

#ifndef TRAFFICSIGNUTIL_H
#define	TRAFFICSIGNUTIL_H



#endif	/* TRAFFICSIGNUTIL_H */
#include "opencv2/objdetect/objdetect.hpp"
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/imgproc/imgproc.hpp"

#include <opencv2/core/core.hpp>
//#include <opencv2/highgui/highgui.hpp>
#include <opencv2/features2d/features2d.hpp>
#include <opencv2/nonfree/features2d.hpp>
#include <opencv2/calib3d/calib3d.hpp> 

#include <iostream>
#include <stdio.h>
#include <dirent.h>
#include <algorithm>
#include <math.h>

//#include "configure.h"
#include "helper.h"
#include "constant.h"
#include "utils.h"
#include <stdlib.h>

using namespace std;
using namespace cv;

enum {
    mTotal, mInliers
};

class ResultInfo
{
    public:
        char* ID;
        Rect Locate;
        int Rate;
};

/** Function Headers */
void detectAndDisplay(Mat frame, CascadeClassifier detector);
vector<Rect> detectTrafficSign1(Mat imageInput, CascadeClassifier detector);
vector<Mat> detectTrafficSign2(Mat imageInput, CascadeClassifier detector);
vector<ResultInfo> findObjectRecognize(Mat imageInput, vector<Rect> locates, vector<Mat> listDataImage, vector<char*> listID);
vector<ResultInfo> SVMRecognize(Mat imageInput, vector<Rect> locates, char* SVMDir);
int SVMTrain(char* SVMDir, char* TrainImageDir, float tradeOfNum, bool deleteFeatureFile);