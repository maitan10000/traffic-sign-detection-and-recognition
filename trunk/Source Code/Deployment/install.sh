#!/bin/bash

sudo apt-get update 
sudo apt-get upgrade 


#install opencv
echo "Installing OpenCV ========================================================================================="
sudo apt-get -y install build-essential libgtk2.0-dev libjpeg-dev libtiff4-dev libjasper-dev libopenexr-dev cmake python-dev python-numpy python-tk libtbb-dev libeigen2-dev yasm libfaac-dev libopencore-amrnb-dev libopencore-amrwb-dev libtheora-dev libvorbis-dev libxvidcore-dev libx264-dev libqt4-dev libqt4-opengl-dev sphinx-common texlive-latex-extra libv4l-dev libdc1394-22-dev libavcodec-dev libavformat-dev libswscale-dev
wget -O opencv-2.4.8.zip "http://downloads.sourceforge.net/project/opencvlibrary/opencv-unix/2.4.8/opencv-2.4.8.zip?r=http%3A%2F%2Fopencv.org%2F&ts=1397535325&use_mirror=ncu"
sudo apt-get install -y unzip
unzip -o opencv-2.4.8.zip
#build opencv
cd opencv-2.4.8
mkdir build 
cd build
cmake -D WITH_TBB=ON -D BUILD_NEW_PYTHON_SUPPORT=ON -D WITH_V4L=ON -D INSTALL_C_EXAMPLES=ON -D INSTALL_PYTHON_EXAMPLES=ON -D BUILD_EXAMPLES=ON -D WITH_QT=ON -D WITH_OPENGL=ON .. 
sudo make install

sudo echo "/usr/local/lib" >> /etc/ld.so.conf.d/opencv.conf
sudo ldconfig 
sudo echo "PKG_CONFIG_PATH=$PKG_CONFIG_PATH:/usr/local/lib/pkgconfig
export PKG_CONFIG_PATH">> /etc/bash.bashrc

cd ..
cd ..


#install jdk
echo "Installing JDK ========================================================================================="
sudo apt-get -y install default-jdk
echo "export JAVA_HOME=/usr/lib/jvm/default-java" >> ~/.bashrc
#exit

#down tomcat
echo "Installing Tomcat 7.0.53 ========================================================================================="
wget "http://mirrors.digipower.vn/apache/tomcat/tomcat-7/v7.0.53/bin/apache-tomcat-7.0.53.tar.gz"
#extract tomcat
tar xzf apache-tomcat-7.0.53.tar.gz
#enable run tomcat
sudo chmod +x apache-tomcat-7.0.53/bin/*.sh
#copy tomcat setting
sudo cp -f server.xml apache-tomcat-7.0.53/conf
sudo cp -f web.xml apache-tomcat-7.0.53/conf

#install JsonCpp
echo "Installing JsonCpp ========================================================================================="
mkdir JsonCpp
cd JsonCpp
apt-get install scons
wget -O jsoncpp-src-0.5.0.tar.gz "http://downloads.sourceforge.net/project/jsoncpp/jsoncpp/0.5.0/jsoncpp-src-0.5.0.tar.gz?r=http%3A%2F%2Fsourceforge.net%2Fprojects%2Fjsoncpp%2F%3Fsource%3Dpdlp&ts=1397534929&use_mirror=nchc"
tar -xzf jsoncpp-src-0.5.0.tar.gz
cd jsoncpp-src-0.5.0
scons platform=linux-gcc

#copy jsoncpp
sudo cp libs/linux-gcc-*/libjson_linux-gcc-*_libmt.so /usr/lib
sudo mkdir /usr/include/jsoncpp
sudo cp include/json/* /usr/include/jsoncpp

cd ..
cd ..

# set execute mod to TSRT
sudo chmod +x TSRT/TSRT
sudo chmod +x TSRT/Data/SVM_Multiclass/svm_multiclass_classify
sudo chmod +x TSRT/Data/SVM_Multiclass/svm_multiclass_learn

#install mysql
echo "Installing MySQL ========================================================================================="
sudo apt-get -y install mysql-server

echo "Done"