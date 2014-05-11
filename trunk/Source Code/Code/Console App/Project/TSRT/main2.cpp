//#include "opencv2/objdetect/objdetect.hpp"
//#include "opencv2/highgui/highgui.hpp"
//#include "opencv2/imgproc/imgproc.hpp"
//
//#include <opencv2/core/core.hpp>
////#include <opencv2/highgui/highgui.hpp>
//#include <opencv2/features2d/features2d.hpp>
//#include <opencv2/nonfree/features2d.hpp>
//#include <opencv2/calib3d/calib3d.hpp> 
//
//#include <iostream>
//#include <stdio.h>
//#include <dirent.h>
//#include <algorithm>    // std::sort
//#include <math.h>
//#include <jsoncpp/writer.h>
//
//#include "configure.h"
//#include "helper.h"
//#include "trafficsignUtil.h"
//
//using namespace std;
//using namespace cv;
//
//
//
///** Global variable */
//CascadeClassifier warningCascade;
//static Configure configure;
//
//int main(int argc, const char** argv) {
//
//    char* configureFile = "Data/configure.inf";
//    //configure.FOLDER_WARNING = "/home/everything/Desktop/Data Image/";
//    //configure.CASCADE_WARNING = "/home/everything/Desktop/cascade/train300.xml";
//    //configure.saveToFile(configureFile);
//    configure.readFromFile(configureFile);
//    //printf("%s\n", configure.FOLDER_WARNING);
//    //printf("%s\n", configure.CASCADE_WARNING);
//
//    //load Data image to compare
//    char* DataImageFolder =  configure.FOLDER_WARNING;//"/home/everything/Desktop/Data Image/";
//    vector<string> listDataImageFile = getAllFile(DataImageFolder);
//    vector<Mat> listDataImage;
//    vector<char*> listID;
//
//    //init data
//    for (int i = 0; i < listDataImageFile.size(); i++) {
//        listID.push_back(&(listDataImageFile.at(i))[0]);
//        char file[1000];
//        strcpy(file, DataImageFolder);
//        strcat(file, listDataImageFile.at(i).c_str());
//        Mat tmpImage = imread(file, CV_LOAD_IMAGE_COLOR);
//        if (!tmpImage.data) {
//            printf("Cannot load image");
//        } else {
//            //resize data image
//            Size s(60, 60);
//            resize(tmpImage, tmpImage, s, 0, 0, CV_INTER_AREA);
//            listDataImage.push_back(tmpImage);
//        }
//    }
//
//    printf("Size: %d", listDataImage.size());
//    //    for (int i = 0; i < listDataImage.size(); i++) {
//    //        //imshow("show", listDataImage.at(i));
//    //        //waitKey(0);
//    //        //destroyWindow("show");
//    //    }
//
//    //get image compare
//    char* folder = "/home/everything/Desktop/Data/Upload/";
//    vector<string> listFile = getAllFile(folder);
//
//    if (!warningCascade.load("/home/everything/Desktop/cascade/train300.xml")) {
//        printf("Cannot load!");
//    } else {
//        //printf("Load success!");
//        int i = 0;
//        for (i = 0; i < listFile.size(); i++) {
//            //printf("%s\n", listFile.at(i).c_str());
//            char file[1000];
//            strcpy(file, folder);
//            strcat(file, listFile.at(i).c_str());
//
//            Mat image = imread(file, CV_LOAD_IMAGE_COLOR);
//            if (!image.data) {
//                printf("Cannot load image");
//            } else {
//                Mat tmpImage;
//                image.copyTo(tmpImage);
//                vector<Rect> listResult = detectTrafficSign1(image, warningCascade);
//                //printf("Found: %d\n", listResult.size());
//
//                if (listResult.size() > 0) {
//                    destroyAllWindows();
//                    detectAndDisplay(tmpImage, warningCascade);
//                    waitKey(1 * 1000);
//                    vector<ResultInfo> tmpresult = findObjectRecognize(image, listResult, listDataImage, listID);
//                    Json::Value root;
//                    for(int i = 0; i < tmpresult.size(); i++)
//                    {
//                        Json::Value re;
//                        re["ID"] = tmpresult.at(i).ID;
//                        Json::Value rect;
//                        rect["x"] = tmpresult.at(i).Locate.x;
//                        rect["y"] = tmpresult.at(i).Locate.y;
//                        rect["width"] = tmpresult.at(i).Locate.width;
//                        rect["height"] = tmpresult.at(i).Locate.height;
//                        re["Locate"] = rect;
//                        root.append(re);
//                    }
//                    cout << root;
//                }
//
//            }
//        }
//    }
//    //getchar();
//    return 0;
//}
//
