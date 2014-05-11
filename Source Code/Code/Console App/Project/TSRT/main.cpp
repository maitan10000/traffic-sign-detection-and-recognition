//#include "opencv2/objdetect/objdetect.hpp"
//#include "opencv2/highgui/highgui.hpp"
//#include "opencv2/imgproc/imgproc.hpp"
//
//#include <iostream>
//#include <stdio.h>
//#include <dirent.h>
//
//using namespace std;
//using namespace cv;
//
///** Function Headers */
//void detectAndDisplay(Mat frame);
//vector<string> getAllFile(char* root);
//
///** Global variable */
//CascadeClassifier testcascade;
//
//int main(int argc, const char** argv) {
//
//    char* folder = "/home/everything/Desktop/Test Image/";
//    vector<string> listFile = getAllFile(folder);
//    
//    if (!testcascade.load("/home/everything/Desktop/cascade/train300.xml")) {
//        printf("Cannot load!");
//    } else {
//        printf("Load success!");
//        int i = 0;
//        for (i = 0; i < listFile.size(); i++) {
//            printf("%s\n", listFile.at(i).c_str());
//            
//            char file[1000];                        
//            strcpy(file, folder);            
//            strcat(file, listFile.at(i).c_str());
//
//            //Mat image = imread("C:/opencv/build/x86/vc10/bin/nguyhiem/11.jpg", CV_LOAD_IMAGE_COLOR);
//            //Mat image = imread("/home/everything/Desktop/Test Image/IMG_20140110_093128.jpg", CV_LOAD_IMAGE_COLOR);
//            Mat image = imread(file, CV_LOAD_IMAGE_COLOR);
//            if (!image.data) {
//                printf("Cannot load image");
//            } else {
//                detectAndDisplay(image);
//            }
//        }
//
//    }
//    //getchar();
//    return 0;
//}
//
///** @function detectAndDisplay */
//void detectAndDisplay(Mat frame) {
//    std::vector<Rect> object;
//    Mat frame_gray;
//
//    cvtColor(frame, frame_gray, CV_BGR2GRAY);
//    equalizeHist(frame_gray, frame_gray);
//
//    //-- Detect faces
//    testcascade.detectMultiScale(frame_gray, object, 1.1, 1, 0 | CV_HAAR_SCALE_IMAGE, Size(5, 5));
//
//    for (size_t i = 0; i < object.size(); i++) {
//        //Point center( object[i].x + object[i].width*0.5, object[i].y + object[i].height*0.5 );
//        //ellipse( frame, center, Size( object[i].width*0.5, object[i].height*0.5), 0, 0, 360, Scalar( 255, 0, 255 ), 4, 8, 0 );
//        rectangle(frame, object[i], Scalar(255, 0, 255), 4, 8, 0);
//    }
//    //-- Show what you got
//    imshow("show", frame);
//    waitKey(0);
//    destroyWindow("show");
//}
//
//vector<string> getAllFile(char* root) {
//    vector<string> result;
//    DIR *dir;
//    struct dirent *ent;
//    if ((dir = opendir(root)) != NULL) {
//        /* print all the files and directories within directory */
//        while ((ent = readdir(dir)) != NULL) {
//            //printf("%s\n", ent->d_name);
//            if(strlen(ent->d_name)>2)
//            {
//                result.push_back(ent->d_name);
//            }
//        }
//        closedir(dir);
//    } else {
//        /* could not open directory */
//        perror("");
//        //return NULL;
//    }
//    return result;
//}