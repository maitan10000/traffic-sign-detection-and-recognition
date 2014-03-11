CREATE DATABASE `trafficdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
CREATE TABLE `account` (
  `userID` int(11) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL,
  `createDate` varchar(45) DEFAULT NULL,
  `isActive` bit(1) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `category` (
  `categoryID` int(11) NOT NULL,
  `categoryName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`categoryID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `trafficinformation` (
  `trafficID` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `image` varchar(45) DEFAULT NULL,
  `categoryID` int(11) NOT NULL,
  `information` varchar(255) NOT NULL,
  `penaltyfee` varchar(45) DEFAULT NULL,
  `creator` int(11) NOT NULL,
  `createDate` varchar(45) NOT NULL,
  `modifyDate` varchar(45) DEFAULT NULL,
  `isActive` bit(1) DEFAULT NULL,
  PRIMARY KEY (`trafficID`),
  KEY `categoryID_idx` (`categoryID`),
  KEY `creator_idx` (`creator`),
  CONSTRAINT `categoryID` FOREIGN KEY (`categoryID`) REFERENCES `category` (`categoryID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `creator` FOREIGN KEY (`creator`) REFERENCES `account` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `trainimage` (
  `trafficID` int(11) DEFAULT NULL,
  `imageID` int(11) NOT NULL AUTO_INCREMENT,
  `imageName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`imageID`),
  KEY `trafficID_idx` (`trafficID`),
  CONSTRAINT `trafficID` FOREIGN KEY (`trafficID`) REFERENCES `trafficinformation` (`trafficID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `report` (
  `reportID` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `creator` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `userID` int(11) DEFAULT NULL,
  `createDate` varchar(45) DEFAULT NULL,
  `isActive` bit(1) DEFAULT NULL,
  PRIMARY KEY (`reportID`),
  KEY `userID_idx` (`userID`),
  CONSTRAINT `userID` FOREIGN KEY (`userID`) REFERENCES `account` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `favorite` (
  `favoriteID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `trafficID` int(11) NOT NULL,
  `createDate` varchar(45) DEFAULT NULL,
  `modifyDate` varchar(45) DEFAULT NULL,
  `isActive` bit(1) DEFAULT NULL,
  PRIMARY KEY (`favoriteID`),
  KEY `userIDfk_idx` (`userID`),
  KEY `trafficIDfk_idx` (`trafficID`),
  CONSTRAINT `userIDfk` FOREIGN KEY (`userID`) REFERENCES `account` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `trafficIDfk` FOREIGN KEY (`trafficID`) REFERENCES `trafficinformation` (`trafficID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `result` (
  `resultID` int(11) NOT NULL,
  `uploadedImage` varchar(45) DEFAULT NULL,
  `listTraffic` varchar(45) DEFAULT NULL,
  `userID` int(11) DEFAULT NULL,
  `createDate` varchar(45) DEFAULT NULL,
  `isActive` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`resultID`),
  KEY `useID_idx` (`userID`),
  CONSTRAINT `useID` FOREIGN KEY (`userID`) REFERENCES `account` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



