
#include <jsoncpp/value.h>
#include <jsoncpp/writer.h>
#include <jsoncpp/reader.h>

#include <iostream>
#include <stdio.h>
#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <cerrno>
#include <string.h>

#include "configure.h"
using namespace std;

//Save configure to file
//

void Configure::saveToFile(char* filePath) {
    Json::Value configureObj;
    configureObj["TRAIN FOLDER"] = TRAIN_FOLDER;
    configureObj["CASCADE TYPE1"] = CASCADE_TYPE1;
    configureObj["CASCADE TYPE2"] = CASCADE_TYPE2;
    configureObj["SVM FOLDER"] = SVM_FOLDER;
    //printf(filePath);
    Json::StyledWriter writer;
    string outputConfig = writer.write(configureObj);
    //cout << outputConfig;
    ofstream out(filePath);
    out << outputConfig;
}

//Read configure from file
//

bool Configure::readFromFile(char* filePath) {
    string inputConfig = "";
    std::ifstream in(filePath, std::ios::in | std::ios::binary);
    if (in) {
        std::ostringstream contents;
        contents << in.rdbuf();
        in.close();
        inputConfig = contents.str();
    }

    //cout << inputConfig;

    Json::Value configureObj; // will contains the root value after parsing.
    Json::Reader reader;

    bool parsingSuccessful = reader.parse(inputConfig, configureObj);
    if (!parsingSuccessful) {
        // report to the user the failure and their locations in the document.
        //std::cout << "Failed to parse configuration\n"
        //        << reader.getFormatedErrorMessages();
        return false;
    }

    string tmpString = configureObj["TRAIN FOLDER"].asString();
    this->TRAIN_FOLDER = new char[tmpString.length() + 1];
    strcpy(this->TRAIN_FOLDER, tmpString.c_str());

    tmpString = configureObj["CASCADE TYPE1"].asString();
    this->CASCADE_TYPE1 = new char[tmpString.length() + 1];
    strcpy(this->CASCADE_TYPE1, tmpString.c_str());

    tmpString = configureObj["CASCADE TYPE2"].asString();
    this->CASCADE_TYPE2 = new char[tmpString.length() + 1];
    strcpy(this->CASCADE_TYPE2, tmpString.c_str());

    tmpString = configureObj["SVM FOLDER"].asString();
    this->SVM_FOLDER = new char[tmpString.length() + 1];
    strcpy(this->SVM_FOLDER, tmpString.c_str());

    return true;
}

