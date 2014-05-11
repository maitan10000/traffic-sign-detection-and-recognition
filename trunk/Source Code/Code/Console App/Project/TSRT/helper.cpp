#include "helper.h"


//Get all file in folder
//return list of file in foler

vector<string> getAllFile(char* root) {
    vector<string> result;
    DIR *dir;
    struct dirent *ent;
    if ((dir = opendir(root)) != NULL) {
        /* print all the files and directories within directory */
        while ((ent = readdir(dir)) != NULL) {
            //printf("%s\n", ent->d_name);
            if (strlen(ent->d_name) > 2) {
                result.push_back(ent->d_name);
            }
        }
        closedir(dir);
    } else {
        /* could not open directory */
        perror("");
        //return NULL;
    }
    return result;
}

//Convert integer to string
//

string convertInt(int number) {
    stringstream ss; //create a stringstream
    ss << number; //add number to the stream
    return ss.str(); //return a string with the contents of the stream
}

//Compare 2 integers, use to sort vector
//

bool wayToSort(int i, int j) {
    return i > j;
};

