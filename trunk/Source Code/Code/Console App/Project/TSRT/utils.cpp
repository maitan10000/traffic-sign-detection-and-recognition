#include "utils.h"
#include "trafficsignUtils.h"

vector<float> extractFeature(const char* imagePath) {
    vector<float> featureVector;
    //read and resize image
    Size s(64, 64);
    Mat img = imread(imagePath, CV_LOAD_IMAGE_GRAYSCALE);
    if (!img.data)
        return featureVector;
    resize(img, img, s, 0, 0, CV_INTER_AREA);

    //extract feature using HOGDescriptor    
    HOGDescriptor hog;
    hog.winSize = s;
    hog.compute(img, featureVector, Size(8, 8), Size(0, 0));
    return featureVector;
}

void appendLineToFile(const char* filePath, string line) {
    std::ofstream myfile;
    myfile.open(filePath, ios::out | ios::app); //append to file
    myfile << line << "\n";
    myfile.close();
}

void appendFeatureToFile1(const char* imagePath, const char* fileSavePath, int label) {
    vector<float> features = extractFeature(imagePath);
    if (features.size() > 0) {
        //create SVM multiclass line format
        stringstream line;
        line << label;
        for (int i = 0; i < features.size(); i++) {
            line << " " << (i + 1) << ":" << features.at(i);
        }
        line << " #";
        appendLineToFile(fileSavePath, line.str());
    }
}

void appendFeatureToFile2(Mat image, const char* fileSavePath, int label) {
    vector<float> featureVector;
    //read and resize image
    Size s(64, 64);
    resize(image, image, s, 0, 0, CV_INTER_AREA);

    //extract feature using HOGDescriptor    
    HOGDescriptor hog;
    hog.winSize = s;
    hog.compute(image, featureVector, Size(8, 8), Size(0, 0));
    if (featureVector.size() > 0) {
        //create SVM multiclass line format
        stringstream line;
        line << label;
        for (int i = 0; i < featureVector.size(); i++) {
            line << " " << (i + 1) << ":" << featureVector.at(i);
        }
        line << " #";
        appendLineToFile(fileSavePath, line.str());
    }

}

string exec(char* cmd) {
    FILE* pipe = popen(cmd, "r");
    if (!pipe) return "ERROR";
    char buffer[128];
    string result = "";
    while (!feof(pipe)) {
        if (fgets(buffer, 128, pipe) != NULL)
            result += buffer;
    }
    pclose(pipe);
    return result;
}

vector<string> explode(const string& str, const char& ch) {
    string next;
    vector<string> result;

    // For each character in the string
    for (string::const_iterator it = str.begin(); it != str.end(); it++) {
        // If we've hit the terminal character
        if (*it == ch) {
            // If we have some characters accumulated
            if (!next.empty()) {
                // Add them to the result vector
                result.push_back(next);
                next.clear();
            }
        } else {
            // Accumulate the next character into the sequence
            next += *it;
        }
    }
    if (!next.empty())
        result.push_back(next);
    return result;
}

void SVMClassifier(char* workingDir, char* inputFile, char* modelFile, char* outputFile, char* labelFile, string &lableResult, float &rateResult) {
    char* app = "svm_multiclass_classify";
    char command[1000];
    strcpy(command, workingDir);
    strcat(command, app);
    strcat(command, " ");
    strcat(command, workingDir);
    strcat(command, inputFile);
    strcat(command, " ");
    strcat(command, workingDir);
    strcat(command, modelFile);
    strcat(command, " ");
    strcat(command, workingDir);
    strcat(command, outputFile);

    char inputFileFullPath[1000];
    strcpy(inputFileFullPath, workingDir);
    strcat(inputFileFullPath, inputFile);

    char outputFileFullPath[1000];
    strcpy(outputFileFullPath, workingDir);
    strcat(outputFileFullPath, outputFile);

    string outPut = exec(command);
    //cout << outPut;

    //parse result from outputFile;
    char tmpPath[1000];
    strcpy(tmpPath, workingDir);
    strcat(tmpPath, outputFile);

    string line;
    ifstream myfile(tmpPath);
    if (myfile.is_open()) {
        getline(myfile, line); //read first line
        myfile.close();
    }
    //cout << line;

    int maxPos = 0;
    float Max = 0;
    vector<string> listString = explode(line, ' ');
    for (int i = 1; i < listString.size(); i++) {
        float tmpFloat = atof(listString.at(i).c_str());
        if (tmpFloat > Max) {
            Max = tmpFloat;
            maxPos = i;
        }
    }

    //cout << Max << " Pos: " << maxPos;

    //match label  
    string lable = "";
    strcpy(tmpPath, workingDir);
    strcat(tmpPath, labelFile);

    ifstream labelStream(tmpPath);
    if (labelStream.is_open()) {
        while (getline(labelStream, line)) {
            vector<string> listLabel = explode(line, ':');
            int tmpPos = atoi(listLabel.at(0).c_str());
            if (tmpPos == maxPos) {
                lable = listLabel.at(1);
                break;
            }
        }
        labelStream.close();
    }

    lableResult = lable;
    rateResult = Max;
    remove(inputFileFullPath);
    remove(outputFileFullPath);
}

int SVMLearn(char* workingDir, char* modelFile, char* labelFile, char* trainImageDir, float tradeOffNum, bool deleteFeatureFile) {
    char* app = "svm_multiclass_learn";
    char modeFilePath[1000];
    strcpy(modeFilePath, workingDir);
    strcat(modeFilePath, modelFile);

    char labelFilePath[1000];
    strcpy(labelFilePath, workingDir);
    strcat(labelFilePath, labelFile);

    char featureFilePath[1000];
    strcpy(featureFilePath, workingDir);
    strcat(featureFilePath, "saveFeatures.txt");

    int label = 1;
    //remove file
    remove(modeFilePath);
    remove(labelFilePath);
    remove(featureFilePath);

    //get feature and save to file
    vector<string> listTrainFolder = getAllFile(trainImageDir);

    int count = 0;
    for (int j = 0; j < listTrainFolder.size(); j++) {
        //save label
        stringstream labelLine;
        labelLine << label << ":" << listTrainFolder.at(j);
        appendLineToFile(labelFilePath, labelLine.str());

        char DataPosImageFolder[1000];
        strcpy(DataPosImageFolder, trainImageDir);
        strcat(DataPosImageFolder, listTrainFolder.at(j).c_str());
        strcat(DataPosImageFolder, "/");

        vector<string> listPosImage = getAllFile(DataPosImageFolder);
        for (int i = 0; i < listPosImage.size(); i++) {
            char posImagePath[1000];
            strcpy(posImagePath, DataPosImageFolder);
            strcat(posImagePath, listPosImage.at(i).c_str());
            appendFeatureToFile1(posImagePath, featureFilePath, label);
            count++;
        }
        label++;
    }//end for j

    //cout << "Read feature of " << count << " images";

    //create model from feature file
    char command[1000];
    strcpy(command, workingDir);
    strcat(command, app);
    strcat(command, " -c ");
    char tempChar[8];
    sprintf(tempChar, "%.0f", tradeOffNum);
    strcat(command, tempChar);
    strcat(command, " ");
    strcat(command, featureFilePath);
    strcat(command, " ");
    strcat(command, workingDir);
    strcat(command, modelFile);
    string outPut = exec(command);
    //cout << outPut;

    if (deleteFeatureFile == true) {
        remove(featureFilePath);
    }
    return count;
}