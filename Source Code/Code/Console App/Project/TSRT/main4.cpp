#include "opencv2/objdetect/objdetect.hpp"
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/imgproc/imgproc.hpp"

#include <opencv2/core/core.hpp>
//#include <opencv2/highgui/highgui.hpp>
#include <opencv2/features2d/features2d.hpp>
#include <opencv2/nonfree/features2d.hpp>
#include <opencv2/calib3d/calib3d.hpp> 

#include <libgen.h>
#include <iostream>
#include <stdio.h>
#include <dirent.h>
#include <algorithm>    // std::sort
#include <math.h>
#include <jsoncpp/writer.h>
#include <jsoncpp/value.h>
#include <jsoncpp/reader.h>
#include <bits/errno.h>

#include "configure.h"
#include "helper.h"
#include "trafficsignUtils.h"
#include <time.h>
//#include "constant.h"
#include "base64.h"

using namespace std;
using namespace cv;


/** Functions **/
void printError(char* errorMessage);
void printResult(vector<ResultInfo> listResult);
void arg_parse(int argc, const char* argv[]);
void menu(const char* appName);
void printConfigure();
void detectAndRecognize(const char* imageFile, CascadeClassifier detector, char* DataImageFolder);
void saveDetectedLocate(Mat image, vector<Rect> listLocate, const char* saveDic, const char* bName);
vector<ResultInfo> recognize(Mat imageInput, vector<Rect> listResult);
vector<Rect> parseListLocate(const char* inputText);


/** Global variable */
static Configure configure;
char* configureFile = "Data/configure.inf";
double timeExec = 0;
clock_t tStart = NULL;

int main(int argc, const char* argv[]) {
    char* errorMessage = "";

//    configure.TRAIN_FOLDER = "Data/Train Image/";
//    configure.CASCADE_TYPE1 = "Data/type1.xml";
//    configure.CASCADE_TYPE2 = "Data/type2.xml";
//    configure.SVM_FOLDER = "Data/SVM_Multiclass/";
//    configure.saveToFile(configureFile);
    //load configure
    if (configure.readFromFile(configureFile) == false) {
        errorMessage = "Cannot read configure file";
        printError(errorMessage);
        return 0;
    }

    //parse arguments
    arg_parse(argc, argv);

    return 0;
}



//Print error
//

void printError(char* errorMessage) {
    Json::Value root;
    Json::Value error(errorMessage);
    root.append(error);
    cout << root;
}

// Arguments Processing
//

void arg_parse(int argc, const char* argv[]) {
    if (argc == 1) {
        menu(argv[0]);
        return;
    }
    char* errorMessage = "";
    int type = 1;
    const char* outDirectory = "";
    bool saveDetect = false;
    bool parseLocate = false;
    vector<Rect> listLocate;

    for (int i = 1; i < argc; i++) {
        if (!strcmp(argv[i], "-h") || !strcmp(argv[i], "--help")) {
            menu(argv[0]);
            return;
        } else if (!strcmp(argv[i], "-p") || !strcmp(argv[i], "--print_configure")) {
            printConfigure();
            return;
        } else if (!strcmp(argv[i], "-t") || !strcmp(argv[i], "--type")) {
            type = atoi(argv[++i]);
        } else if (!strcmp(argv[i], "-s") || !strcmp(argv[i], "--save_detect")) {
            outDirectory = argv[++i];
            saveDetect = true;
        } else if (!strcmp(argv[i], "-d") || !strcmp(argv[i], "--detect_list")) {
            parseLocate = true;
            listLocate = parseListLocate(argv[++i]);
        } else if (!strcmp(argv[i], "-l") || !strcmp(argv[i], "--learn")) {
            float tradeNum  = 1000;
            if(i < argc - 1)
            {
                tradeNum = atof(argv[++i]);
            }
            int totalSampleTrained = SVMTrain(configure.SVM_FOLDER, configure.TRAIN_FOLDER, tradeNum, false);
            return;
        } else {
            tStart = clock();
            const char* fileImage = argv[i];
            const char* bName = basename(strdup(fileImage));
            //load image
            Mat image = imread(fileImage, CV_LOAD_IMAGE_COLOR);
            if (!image.data) {
                errorMessage = "Cannot load main image";
                printError(errorMessage);
                return;
            }

            if (type == 1) {
                CascadeClassifier detectorType1;
                if (!detectorType1.load(configure.CASCADE_TYPE1)) {
                    errorMessage = "Cannot load cascase type1";
                    printError(errorMessage);
                    return;
                }

                CascadeClassifier detectorType2;
                if (!detectorType2.load(configure.CASCADE_TYPE2)) {
                    errorMessage = "Cannot load cascase type2";
                    printError(errorMessage);
                    return;
                }

                if (parseLocate == false) {
                    listLocate = detectTrafficSign1(image, detectorType1);
                    vector<Rect> tmpListLocate = detectTrafficSign1(image, detectorType2);
                    for (int i = 0; i < tmpListLocate.size(); i++) {
                        listLocate.push_back(tmpListLocate.at(i));
                    }
                }
                vector<ResultInfo> listResultInfo = recognize(image, listLocate);
                printResult(listResultInfo);

                //save detect location
                if (saveDetect == true) {
                    saveDetectedLocate(image, listLocate, outDirectory, bName);
                }
            } else {
                errorMessage = "Type input not valid";
                printError(errorMessage);
                return;
            }
        }
    }
}

// Menu 
//

void menu(const char* appName) {
    cout << appName << " - Traffic sign detect and recognize" << endl;
    cout << "Command Usage: TSRT";
    cout << " [option]... [image path]" << endl;

    cout << "  Options" << endl;
    cout << "    -p <print_configure>" << endl;
    cout << "        Determine output the configure content" << endl;

    cout << "    -t <type>" << endl;
    cout << "        Determine the type use to detect. The default is 1" << endl;
    cout << "        1: Warning traffic sign" << endl;
    cout << "        2: Prohibitory traffic sign" << endl;

    cout << "    -s <save_detect>" << endl;
    cout << "        Save detect image. Set directory to save detect. Default is current directory" << endl;

    cout << "    -d <detect_list>" << endl;
    cout << "        Recognize with detect locate input instead using build in detect function:" << endl;
    cout << "         Detect list input is base64 of JSON string, format:" << endl;
    cout << "         [{\"locate\":{\"height\":134,\"width\":134,\"x\":473,\"y\":123}} {another locate ...} ]" << endl;

    cout << "    -l <learn>" << endl;
    cout << "        Learn and create new model file to SVM recognize" << endl;

    cout << "  Supported Image Types" << endl;
    cout << "      bmp|jpeg|jpg|png" << endl;

}

// Print Configure
//

void printConfigure() {
    printf("Cascade type1: %s\n", configure.CASCADE_TYPE1);
    printf("Cascade type2: %s\n", configure.CASCADE_TYPE2);
    printf("Train warning: %s\n", configure.TRAIN_FOLDER);
    printf("SVM Folder: %s\n", configure.SVM_FOLDER);
}


// Print result
//

void printResult(vector<ResultInfo> listResult) {
    Json::Value root;
    if (listResult.size() > 0) {
        Json::Value listRe;
        for (int i = 0; i < listResult.size(); i++) {
            Json::Value re;
            re["ID"] = listResult.at(i).ID;
            re["Rate"] = listResult.at(i).Rate;
            Json::Value rect;
            rect["x"] = listResult.at(i).Locate.x;
            rect["y"] = listResult.at(i).Locate.y;
            rect["width"] = listResult.at(i).Locate.width;
            rect["height"] = listResult.at(i).Locate.height;
            re["Locate"] = rect;
            listRe.append(re);
        }
        root["Result"] = listRe;
    } else {
        root["Result"] = Json::Value(Json::arrayValue);
    }

    if (tStart != NULL) {
        timeExec = (double) (clock() - tStart) / CLOCKS_PER_SEC;
        root["Time"] = timeExec;
    }
    cout << root;
}
// Detect and recognize
//

/*
void detectAndRecognize(Mat imageInput, CascadeClassifier detector, char* DataImageFolder) {
    clock_t tStart = clock();
    char* errorMessage = "";

    
    //load data image to compare
    vector<string> listDataImageFile = getAllFile(DataImageFolder);
    vector<Mat> listDataImage;
    vector<char*> listID;

    //init data
    for (int i = 0; i < listDataImageFile.size(); i++) {
        listID.push_back(&(listDataImageFile.at(i))[0]);
        char file[1000];
        strcpy(file, DataImageFolder);
        strcat(file, listDataImageFile.at(i).c_str());
        Mat tmpImage = imread(file, CV_LOAD_IMAGE_COLOR);
        if (!tmpImage.data) {
            //printf("Cannot load image");
            errorMessage = "Cannot load data image: ";
            char errorTmp[1500];
            strcpy(errorTmp, errorMessage);
            strcat(errorTmp, file);
            printError(errorTmp);
            return;
        } else {
            //resize data image
            Size s(constants::COMPARE_WIDTH, constants::COMPARE_HEIGHT);
            resize(tmpImage, tmpImage, s, 0, 0, CV_INTER_AREA);
            listDataImage.push_back(tmpImage);
        }
    }
    if (listDataImage.size() == 0) {
        errorMessage = "Data image not found or not have image";
        printError(errorMessage);
        return;
    }
    //printf("Size: %d", listDataImage.size());
     

    //read image
    Mat image = imageInput.clone();
    Mat tmpImage = image.clone();
    vector<Rect> listResult = detectTrafficSign1(image, detector);
    //         destroyAllWindows();
    //                detectAndDisplay(tmpImage, warningCascade);
    //                waitKey(0);
    Json::Value root;
    if (listResult.size() > 0) {
        //show result
        if (constants::SHOW_RESULT) {
            destroyAllWindows();
            detectAndDisplay(tmpImage, detector);
            waitKey(1 * 1000);
        }
        //vector<ResultInfo> tmpresult = findObjectRecognize(image, listResult, listDataImage, listID);
        vector<ResultInfo> tmpresult = SVMRecognize(image, listResult, configure.SVM_FOLDER);
        Json::Value listRe;
        for (int i = 0; i < tmpresult.size(); i++) {
            Json::Value re;
            re["ID"] = tmpresult.at(i).ID;
            re["Rate"] = tmpresult.at(i).Rate;
            Json::Value rect;
            rect["x"] = tmpresult.at(i).Locate.x;
            rect["y"] = tmpresult.at(i).Locate.y;
            rect["width"] = tmpresult.at(i).Locate.width;
            rect["height"] = tmpresult.at(i).Locate.height;
            re["Locate"] = rect;
            listRe.append(re);
        }
        root["Result"] = listRe;
    } else {
        root["Result"] = Json::Value(Json::arrayValue);
    }

    double timeExec = (double) (clock() - tStart) / CLOCKS_PER_SEC;
    root["Time"] = timeExec;
    cout << root;
}

 */

//Save detected locate to folder
//

void saveDetectedLocate(Mat image, vector<Rect> listLocate, const char* saveDic, const char* bName) {
    for (size_t i = 0; i < listLocate.size(); i++) {
        Mat tmpImage = image(listLocate.at(i));

        char tmpName[1000];
        strcpy(tmpName, saveDic);
        strcat(tmpName, strdup(bName));
        strcat(tmpName, "_out_");
        char num[12];
        snprintf(num, 12, "%d", i);
        strcat(tmpName, num);
        strcat(tmpName, ".jpg");

        imwrite(&tmpName[0], tmpImage);
    }
}

// Recognize 
//

vector<ResultInfo> recognize(Mat imageInput, vector<Rect> listResult) {
    return SVMRecognize(imageInput, listResult, configure.SVM_FOLDER);
}

// Parse list locate
//

vector<Rect> parseListLocate(const char* inputText) {
    vector<Rect> listResult;
    string listLocateString = base64_decode(inputText);
    Json::Value root; // will contains the root value after parsing.
    Json::Reader reader;

    bool parsingSuccessful = reader.parse(listLocateString, root);
    if (!parsingSuccessful) {
        //return false;
        return listResult;
    }

    for (int i = 0; i < root.size(); i++) {
        Json::Value locateJSON = root[i]["locate"];
        Rect locate;
        locate.height = locateJSON["height"].asInt();
        locate.width = locateJSON["width"].asInt();
        locate.y = locateJSON["y"].asInt();
        locate.x = locateJSON["x"].asInt();
        listResult.push_back(locate);
    }
    return listResult;
}