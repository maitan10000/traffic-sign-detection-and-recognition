#include <cstdlib>
#include "opencv2/objdetect/objdetect.hpp"
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/imgproc/imgproc.hpp"

#include <opencv2/core/core.hpp>
#include <opencv2/features2d/features2d.hpp>
#include <opencv2/nonfree/features2d.hpp>
#include <opencv2/calib3d/calib3d.hpp> 

#include <iostream>
#include <fstream>
#include <stdio.h>
#include <dirent.h>
#include <math.h>

#include "helper.h"
#include "Utils.h"
#include "svm_common.h"

using namespace std;
using namespace cv;

int main(int argc, char** argv) {

    char* workingDir = "/home/everything/Desktop/Sample/svm_multiclass_linux32/";
    char* modelFilePath = "model";
    char* inputFilePath = "input.txt";
    char* outputFilePath = "output.txt";
    char* labelFilePath = "label.txt";
    string id = classifier(workingDir, inputFilePath, modelFilePath, outputFilePath, labelFilePath);
    cout << "ID: " << id;
    
    return 0;
}

