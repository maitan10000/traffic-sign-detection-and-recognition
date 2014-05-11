///* 
// * File:   main.cpp
// * Author: everything
// *
// * Created on March 8, 2014, 10:06 PM
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
//    // and feed SVM with them:
//    // SVMLight::SVMTrainer svm("features.txt");
//    char* DataImageFolder = "/home/everything/Desktop/Data/Train Image/";
//    vector<string> listTrainFolder = getAllFile(DataImageFolder);
//
//    for (int j = 0; j < listTrainFolder.size(); j++) {
//        HOGDescriptor hog;
//        hog.winSize = Size(64, 64);
//        SVMLight::SVMTrainer svm("features.txt");
//        size_t posCount = 0, negCount = 0;
//
//        char DataPosImageFolder[1000];
//        strcpy(DataPosImageFolder, DataImageFolder);
//        strcat(DataPosImageFolder, listTrainFolder.at(j).c_str());
//        strcat(DataPosImageFolder, "/");
//
//        //char* DataPosImageFolder = "/home/everything/Desktop/Data/Train Image/203a/";
//        vector<string> listPosImage = getAllFile(DataPosImageFolder);
//        for (int i = 0; i < listPosImage.size(); i++) {
//            char posImagePath[1000];
//            strcpy(posImagePath, DataPosImageFolder);
//            strcat(posImagePath, listPosImage.at(i).c_str());
//
//            Mat img = imread(posImagePath, CV_LOAD_IMAGE_GRAYSCALE);
//            if (!img.data)
//                break;
//            Size s(64, 64);
//            resize(img, img, s, 0, 0, CV_INTER_AREA);
//
//            // obtain feature vector:
//            vector<float> featureVector;
//            hog.compute(img, featureVector, Size(8, 8), Size(0, 0));
//
//            // write feature vector to file that will be used for training:
//            svm.writeFeatureVectorToFile(featureVector, true); // true = positive sample
//            posCount++;
//            img.release();
//        }
//
//        //train negative image
//        for (int k = 0; k < listTrainFolder.size() - 1; k++) {
//            int negPosition = k + j + 1;
//            if (negPosition >= listTrainFolder.size()) {
//                negPosition = negPosition - listTrainFolder.size();
//            }
//
//            char DataNegImageFolder[1000];
//            strcpy(DataNegImageFolder, DataImageFolder);
//            strcat(DataNegImageFolder, listTrainFolder.at(negPosition).c_str());
//            strcat(DataNegImageFolder, "/");
//            //char* DataNegImageFolder = "/home/everything/Desktop/Data/Train Image/203b/";
//            vector<string> listNagImage = getAllFile(DataNegImageFolder);
//            for (int i = 0; i < listNagImage.size(); i++) {
//                vector<float> featureVector;
//                char negImagePath[1000];
//                strcpy(negImagePath, DataNegImageFolder);
//                strcat(negImagePath, listNagImage.at(i).c_str());
//                
//                Mat img = imread(negImagePath, CV_LOAD_IMAGE_GRAYSCALE);
//                if (!img.data)
//                    break;
//                Size s(64, 64);
//                resize(img, img, s, 0, 0, CV_INTER_AREA);
//
//                hog.compute(img, featureVector, Size(8, 8), Size(0, 0));
//                svm.writeFeatureVectorToFile(featureVector, false);
//                negCount++;
//                img.release();
//            }//end for i
//        }//end for k
//
//        std::cout << "\nfinished writing features: "
//                << posCount << " positive and "
//                << negCount << " negative samples used";
//        string classifyFile = "classifier/" + listTrainFolder.at(j) + ".txt";
//        std::string modelName(classifyFile);
//        svm.trainAndSaveModel(modelName);
//        std::cout << "SVM saved to " << modelName;
//    }//end for j
//    return 0;
//}
//
