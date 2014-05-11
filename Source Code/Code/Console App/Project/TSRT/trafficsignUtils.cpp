#include "trafficsignUtils.h"
#include "configure.h"


//Detect and display to screen
//

void detectAndDisplay(Mat frame, CascadeClassifier detector) {
    //detect
    vector<Rect> object = detectTrafficSign1(frame, detector);
    for (size_t i = 0; i < object.size(); i++) {
        rectangle(frame, object[i], Scalar(255, 0, 255), 4, 8, 0);
    }

    //-- Show result    
    if (constants::SHOW_RESULT == true) {
        Size s(frame.size().width / 2, frame.size().height / 2);
        resize(frame, frame, s, 0, 0, CV_INTER_AREA);
        imshow("show1", frame);
        moveWindow("show1", 0, 0);
        //waitKey(0);
        //destroyWindow("show1");
    }
}

//show the image with detect location
//

void debug(Mat imageInput, vector<Rect> listLocate) {
    Mat image = imageInput.clone();
    //Draw locate
    for (size_t i = 0; i < listLocate.size(); i++) {
        rectangle(image, listLocate[i], Scalar(255, 0, 255), 4, 8, 0);
    }

    //-- Show result    
    if (constants::SHOW_RESULT == true) {
        Size s(image.size().width / 2, image.size().height / 2);
        resize(image, image, s, 0, 0, CV_INTER_AREA);
        imshow("show1", image);
        moveWindow("show1", 0, 0);
        //waitKey(0);
        //destroyWindow("show1");
    }
}


//Detect traffic sign
//return collect rect of detect area

vector<Rect> detectTrafficSign1(Mat imageInput, CascadeClassifier detector) {
    vector<Rect> object;
    int minSize = imageInput.size().width/20;
    if(minSize > imageInput.size().height)
    {
        minSize = imageInput.size().height;
    }
    int maxSize = imageInput.size().width/2;
    if(maxSize > imageInput.size().height)
    {
        maxSize = imageInput.size().height;
    }
    //Mat frame_gray;
    //convert to gray image
    //cvtColor(imageInput, frame_gray, CV_BGR2GRAY);
    //equalizeHist(frame_gray, frame_gray);
    //detect traffic sign
    //    Size minSize = new Size();
    //    minSize.height = 5;
    //    minSize.width = 5;
    //
    //    Size maxSize = new Size();
    //    maxSize.height = 150;
    //    maxSize.width = 150;

    detector.detectMultiScale(imageInput, object, 1.1, 1, 0 | 2, Size(minSize, minSize), Size(maxSize, maxSize));
    return object;
}

//Detect traffic sign
//return collect image of detect area

vector<Mat> detectTrafficSign2(Mat imageInput, CascadeClassifier detector) {
    vector<Mat> result;
    //get detect area in rectangle
    vector<Rect> object = detectTrafficSign1(imageInput, detector);

    //copy detect area to vector image result
    for (size_t i = 0; i < object.size(); i++) {
        Mat tmp = imageInput(object.at(i));
        result.push_back(tmp);
    }
    return result;
}

// Recognize traffic sign using find object algorithm
//

vector<ResultInfo> findObjectRecognize(Mat imageInput, vector<Rect> locates, vector<Mat> listDataImage, vector<char*> listID) {
    vector<ResultInfo> listResult;

    for (size_t i = 0; i < locates.size(); i++) {
        //extract image of detect area
        Mat workingMat = imageInput(locates.at(i));
        //resize image to support recognize more accurate
        Size s(constants::COMPARE_WIDTH, constants::COMPARE_HEIGHT);
        resize(workingMat, workingMat, s, 0, 0, CV_INTER_AREA);

        //begin recognize
        vector<int> listValue;
        int method = mInliers; //total matches
        bool quiet = true;

        for (int i = 0; i < listDataImage.size(); i++) {
            //Copy as grayscale
            cv::Mat objectImg;
            cv::Mat sceneImg;

            //change to gray image
            cvtColor(workingMat, objectImg, CV_BGR2GRAY);
            cvtColor(listDataImage.at(i), sceneImg, CV_BGR2GRAY);

            //recognize
            int value = 0;
            if (!objectImg.empty() && !sceneImg.empty()) {
                std::vector<cv::KeyPoint> objectKeypoints;
                std::vector<cv::KeyPoint> sceneKeypoints;
                cv::Mat objectDescriptors;
                cv::Mat sceneDescriptors;

                //Extract keypoints
                cv::SIFT sift;
                sift.detect(objectImg, objectKeypoints);
                sift.detect(sceneImg, sceneKeypoints);

                //Extract descriptors
                sift.compute(objectImg, objectKeypoints, objectDescriptors);
                sift.compute(sceneImg, sceneKeypoints, sceneDescriptors);

                ////////////////////////////
                // NEAREST NEIGHBOR MATCHING USING FLANN LIBRARY (included in OpenCV)
                ////////////////////////////

                cv::Mat results;
                cv::Mat dists;
                std::vector<std::vector<cv::DMatch> > matches;
                int k = 2; // find the 2 nearest neighbors

                // Create Flann KDTree index
                cv::flann::Index flannIndex(sceneDescriptors, cv::flann::KDTreeIndexParams(), cvflann::FLANN_DIST_EUCLIDEAN);
                results = cv::Mat(objectDescriptors.rows, k, CV_32SC1); // Results index
                dists = cv::Mat(objectDescriptors.rows, k, CV_32FC1); // Distance results are CV_32FC1

                // search (nearest neighbor)
                flannIndex.knnSearch(objectDescriptors, results, dists, k, cv::flann::SearchParams());

                ////////////////////////////
                // PROCESS NEAREST NEIGHBOR RESULTS
                ////////////////////////////

                // Find correspondences by NNDR (Nearest Neighbor Distance Ratio)
                float nndrRatio = 0.6;
                std::vector<cv::Point2f> mpts_1, mpts_2; // Used for homography
                std::vector<int> indexes_1, indexes_2; // Used for homography
                std::vector<uchar> outlier_mask; // Used for homography
                // Check if this descriptor matches with those of the objects

                for (int i = 0; i < objectDescriptors.rows; ++i) {
                    // Apply NNDR
                    if (dists.at<float>(i, 0) <= nndrRatio * dists.at<float>(i, 1)) {
                        mpts_1.push_back(objectKeypoints.at(i).pt);
                        indexes_1.push_back(i);

                        mpts_2.push_back(sceneKeypoints.at(results.at<int>(i, 0)).pt);
                        indexes_2.push_back(results.at<int>(i, 0));
                    }
                }

                if (method == mInliers) {
                    // FIND HOMOGRAPHY
                    unsigned int minInliers = 15;
                    if (mpts_1.size() >= minInliers) {
                        cv::Mat H = findHomography(mpts_1,
                                mpts_2,
                                cv::RANSAC,
                                1.0,
                                outlier_mask);
                        int inliers = 0, outliers = 0;
                        for (unsigned int k = 0; k < mpts_1.size(); ++k) {
                            if (outlier_mask.at(k)) {
                                ++inliers;
                            } else {
                                ++outliers;
                            }
                        }
                        if (!quiet)
                            printf("Total=%d Inliers=%d Outliers=%d\n", (int) mpts_1.size(), inliers, outliers);
                        value = (inliers * 100) / (inliers + outliers);
                    }
                } else {
                    value = mpts_1.size();
                }
            }//end if

            if (!quiet)
                printf("Similarity = %d\n", value);
            listValue.push_back(value);
        }//end for

        //find sort by highest recognize rate
        vector<int> tmpList = listValue;
        sort(tmpList.begin(), tmpList.end(), wayToSort);

        vector<int> listPos;
        for (int i = 0; i < tmpList.size(); i++) {
            for (int j = 0; j < listValue.size(); j++) {
                if (tmpList.at(i) > 0 && listValue.at(j) == tmpList.at(i)) {
                    bool added = std::find(listPos.begin(), listPos.end(), j) == listPos.end();
                    if (added) {
                        listPos.push_back(j);
                        break;
                    }
                }
            }
        }

        //show 5 highest result
        if (constants::SHOW_RESULT) {
            int x = 600;
            int y = 0;
            int count = 0;
            for (int i = 0; i < 5; i++) {
                //-- Show what you got
                if (tmpList.at(i) > 0) {
                    String name = convertInt(i) + " - " + convertInt(tmpList.at(i));
                    //printf("%s",name);
                    imshow(name, listDataImage.at(listPos.at(i)));
                    moveWindow(name, x, y);
                    y = y + 150;
                    count++;
                }
            }

            if (count > 0) {
                imshow("Show", workingMat);
                waitKey(5 * 1000);
                //destroyAllWindows();
                destroyWindow("Show");
                for (int i = 0; i < 5; i++) {
                    //-- Show what you got
                    if (tmpList.at(i) > 0) {
                        String name = convertInt(i) + " - " + convertInt(tmpList.at(i));
                        //printf("%s",name);
                        destroyWindow(name);
                    }
                }
            }
        }
        //add to result
        ResultInfo re;
        re.Locate = locates.at(i);
        if (tmpList.at(0) > 0)//found match
        {
            re.ID = listID.at(listPos.at(0)); //highest value
            re.Rate = tmpList.at(0);
        } else {
            re.ID = "";
        }
        listResult.push_back(re);
    }//end for
    return listResult;
}

// Recognize traffic sign using SVM multiclass
//

vector<ResultInfo> SVMRecognize(Mat imageInput, vector<Rect> locates, char* SVMDir) {
    char* workingDir = SVMDir;
    char* modelFilePath = "model.txt";
    char* labelFilePath = "label.txt";

    stringstream ss;
    ss << rand();
    
    char inputFilePath[100];
    strcpy(inputFilePath, ss.str().c_str());
    strcat(inputFilePath, "input.txt");
    char outputFilePath[100];
    strcpy(outputFilePath, ss.str().c_str());
    strcat(outputFilePath, "output.txt");

    vector<ResultInfo> listResult;
    for (size_t i = 0; i < locates.size(); i++) {
        //extract image of detect area
        Mat workingMat = imageInput(locates.at(i));

        //extract feature to file
        char tmpPath[1000];
        strcpy(tmpPath, workingDir);
        strcat(tmpPath, inputFilePath);
        remove(tmpPath);
        appendFeatureToFile2(workingMat, tmpPath, 1);

        //classifier using SVM multiclass
        string idR;
        float rateR;
        SVMClassifier(workingDir, inputFilePath, modelFilePath, outputFilePath, labelFilePath, idR, rateR);

        //add to result
        ResultInfo re;
        re.Locate = locates.at(i);
        if (rateR >= 40) {
            re.ID = strdup(idR.c_str());
            re.Rate = ceil(rateR);
        } else {
            re.ID = "";
            re.Rate = 0;
        }
        listResult.push_back(re);
    }//end for locates.size
    return listResult;
}

// Train SVM data to recognize
//

int SVMTrain(char* SVMDir, char* TrainImageDir, float tradeOfNum, bool deleteFeatureFile) {
    char* workingDir = SVMDir;
    char* modelFilePath = "model.txt";
    char* labelFilePath = "label.txt";

    SVMLearn(workingDir, modelFilePath, labelFilePath, TrainImageDir, tradeOfNum, deleteFeatureFile);
}

