/* 
 * File:   base64.h
 * Author: everything
 *
 * Created on March 14, 2014, 6:48 PM
 */

#ifndef _BASE64_H_
#define _BASE64_H_
#include <string>
std::string base64_encode(unsigned char const* , unsigned int len);
std::string base64_decode(std::string const& s);
#endif 