///* 
// * File:   main3.cpp Create DB to classifier
// * Author: everything
// *
// * Created on March 12, 2014, 10:06 AM
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
//#include "Utils.h"
//#include "svm_struct_api_types.h"
//
//using namespace std;
//using namespace cv;
//
///*
// * 
// */
//int main(int argc, char** argv) {
//
//    const char* labelSavePath = "/home/everything/Desktop/label.txt";
//    const char* fileSavePath = "/home/everything/Desktop/saveFeatures.txt";
//    //const char* imagePath = "/home/everything/Desktop/sample_picture_2014-03-11_18-19-18.jpg";
//    int label = 1;
//    //remove file
//    remove(labelSavePath);
//    remove(fileSavePath);
//    //writeFeatureToFile(imagePath, fileSavePath, label);
//    
//    
//    char* DataImageFolder = "/home/everything/Desktop/Export/Data/Train Image/";
//    vector<string> listTrainFolder = getAllFile(DataImageFolder);
//
//    int count = 0;
//    for (int j = 0; j < listTrainFolder.size(); j++) {   
//        //save label
//         stringstream labelLine;
//         labelLine << label << ":" << listTrainFolder.at(j);
//         appendLineToFile(labelSavePath, labelLine.str());
//        
//        char DataPosImageFolder[1000];
//        strcpy(DataPosImageFolder, DataImageFolder);
//        strcat(DataPosImageFolder, listTrainFolder.at(j).c_str());
//        strcat(DataPosImageFolder, "/");
//
//        vector<string> listPosImage = getAllFile(DataPosImageFolder);
//        for (int i = 0; i < listPosImage.size(); i++) {
//            char posImagePath[1000];
//            strcpy(posImagePath, DataPosImageFolder);
//            strcat(posImagePath, listPosImage.at(i).c_str());
//            appendFeatureToFile(posImagePath, fileSavePath, label);
//            count++;
//        }
//        label++;               
//    }//end for j
//    
//    cout << "Read feature of " << count << " images";
//    return 0;
//}
