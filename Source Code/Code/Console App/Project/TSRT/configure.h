/* 
 * File:   configure.h
 * Author: everything
 *
 * Created on February 23, 2014, 5:51 PM
 */

#ifndef CONFIGURE_H
#define	CONFIGURE_H



#endif	/* CONFIGURE_H */

class Configure {    
   public:
    char* SVM_FOLDER;
    char* TRAIN_FOLDER;    
    char* CASCADE_TYPE1;
    char* CASCADE_TYPE2;
    bool SHOW_RESULT;
    void saveToFile(char*);
    bool readFromFile(char*);
};
