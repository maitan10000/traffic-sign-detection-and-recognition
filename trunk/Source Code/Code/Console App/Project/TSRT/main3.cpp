//#include <iostream>
//#include <string>
////#include <jsoncpp/json.h>
//#include <jsoncpp/reader.h>
//
//using namespace std;
//string jsonArray = "{ \"myArray\": [  \"Element1\", \"Element2\", \"Element3\"] }";
//
//int main() {
//    Json::Value root; // will contains the root value after parsing.
//    Json::Reader reader;
//    Json::Value JArray;
//    cout << "JSON Array " << jsonArray << endl;
//    bool parsingSuccessful = reader.parse(jsonArray, root);
//    if (!parsingSuccessful) {
//        std::cout << "Failed to parse configuration\n" << reader.getFormatedErrorMessages();
//    }
//    JArray = root["myArray"];
//    for (unsigned int i = 0; i < JArray.size(); i++) {
//        cout << "JSON array values: " << JArray[i].asString() << endl;
//    }
//    return 0;
//}
