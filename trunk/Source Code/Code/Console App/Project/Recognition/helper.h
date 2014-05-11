/* 
 * File:   helper.h
 * Author: everything
 *
 * Created on February 23, 2014, 7:46 PM
 */

#ifndef HELPER_H
#define	HELPER_H



#endif	/* HELPER_H */
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

//#include <algorithm>    // std::sort
//#include <math.h>

using namespace std;
using namespace cv;

string convertInt(int number);
bool wayToSort(int i, int j);
vector<string> getAllFile(char* root);
