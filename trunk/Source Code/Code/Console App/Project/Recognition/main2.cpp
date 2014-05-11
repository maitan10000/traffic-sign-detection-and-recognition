///* 
// * File:   main2.cpp
// * Author: everything
// *
// * Created on March 10, 2014, 10:06 AM
// */
//
//#include <cstdlib>
//#include "opencv2/objdetect/objdetect.hpp"
//#include "opencv2/highgui/highgui.hpp"
//#include "opencv2/imgproc/imgproc.hpp"
//
//#include <opencv2/core/core.hpp>
//#include <opencv2/features2d/features2d.hpp>
//#include <opencv2/nonfree/features2d.hpp>
//#include <opencv2/calib3d/calib3d.hpp> 
//
//#include <iostream>
//#include <stdio.h>
//#include <dirent.h>
//#include <math.h>
//
//#include "helper.h"
//#include "SvmLightLib.h"
//
//using namespace std;
//using namespace cv;
//
///*
// * 
// */
//int main(int argc, char** argv) {
//
//    HOGDescriptor hog;
//    hog.winSize = Size(64, 64);
//    string classifier = "classifier/203a.txt";
//    SVMLight::SVMClassifier c(classifier);
//    vector<float> descriptorVector = c.getDescriptorVector();
//    hog.setSVMDetector(descriptorVector);
//
//    char* testImage = "/home/everything/Desktop/nguyhiem/7.jpg";
//    Mat img = imread(testImage, CV_LOAD_IMAGE_GRAYSCALE);
//    if (!img.data)
//        return 0;
//    Size s(64, 64);
//    resize(img, img, s, 0, 0, CV_INTER_AREA);
//
//    vector<Rect> found;
//    Size padding(Size(0, 0));
//    Size winStride(Size(8, 8));
//    hog.detectMultiScale(img, found, 0.0, winStride, padding, 1.01, 0.1);
//    int count = found.size();
//    cout << "Found: " + count;
//
//    return 0;
//}
