-- DROP DATABASE `trafficdb`;
CREATE DATABASE `trafficdb` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `trafficdb`;

CREATE TABLE `account` (
    `userID` varchar(30) NOT NULL,
    `password` varchar(255) NOT NULL,
    `email` varchar(30) NOT NULL,
    `name` nvarchar(30) NOT NULL,
    `role` nvarchar(10) NOT NULL,
    `createDate` datetime DEFAULT NULL,
    `isActive` bit(1) DEFAULT NULL,
    PRIMARY KEY (`userID`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `category` (
    `categoryID` int NOT NULL AUTO_INCREMENT,
    `categoryName` nvarchar(30) DEFAULT NULL,
    PRIMARY KEY (`categoryID`)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `trafficinformation` (
    `trafficID` varchar(11) NOT NULL,
    `name` nvarchar(255) NOT NULL,
    `image` nvarchar(255) DEFAULT NULL,
    `categoryID` int NOT NULL,
    `information` nvarchar(4000) NOT NULL,
    `penaltyfee` nvarchar(255) DEFAULT NULL,
    `creator` varchar(30) NOT NULL,
    `createDate` datetime NOT NULL,
    `modifyDate` datetime DEFAULT NULL,
    `isActive` bit(1) DEFAULT NULL,
    PRIMARY KEY (`trafficID`),
    KEY `categoryID_idx` (`categoryID`),
    KEY `creator_idx` (`creator`),
    CONSTRAINT `categoryID` FOREIGN KEY (`categoryID`)
        REFERENCES `category` (`categoryID`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `creator` FOREIGN KEY (`creator`)
        REFERENCES `account` (`userID`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `trainimage` (
    `trafficID` varchar(11) DEFAULT NULL,
    `imageID` varchar(255) NOT NULL,
    `imageName` nvarchar(255) DEFAULT NULL,
    PRIMARY KEY (`imageID`),
    KEY `trafficID_idx` (`trafficID`),
    CONSTRAINT `trafficID` FOREIGN KEY (`trafficID`)
        REFERENCES `trafficinformation` (`trafficID`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `report` (
    `reportID` int(11) NOT NULL AUTO_INCREMENT,
    `referenceID` varchar(11) NOT NULL, 
    `content` nvarchar(4000) NOT NULL,
    `creator` varchar(30) NOT NULL,
    `type` int(11) NOT NULL, 	
    `createDate` datetime DEFAULT NULL,
    `isRead` bit(1) DEFAULT NULL,
    `isActive` bit(1) DEFAULT NULL,
    PRIMARY KEY (`reportID`),
    KEY `creatorfk_idx` (`creator`),
    CONSTRAINT `creatorfk` FOREIGN KEY (`creator`)
        REFERENCES `account` (`userID`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `favorite` (
    `creator` varchar(30) NOT NULL,
    `trafficID` varchar(11) NOT NULL,
    `createDate` datetime DEFAULT NULL,
    `modifyDate` datetime DEFAULT NULL,
    `isActive` bit(1) DEFAULT NULL,
    PRIMARY KEY (`creator` , `trafficID`),
    KEY `creatorfk1_idx` (`creator`),
    KEY `trafficIDfk_idx` (`trafficID`),
    CONSTRAINT `creatorfk1` FOREIGN KEY (`creator`)
        REFERENCES `account` (`userID`)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT `trafficIDfk` FOREIGN KEY (`trafficID`)
        REFERENCES `trafficinformation` (`trafficID`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `result` (
    `resultID` int(11) NOT NULL AUTO_INCREMENT,
    `uploadedImage` nvarchar(255) DEFAULT NULL,
    `listTraffic` nvarchar(4000) DEFAULT NULL,
    `creator` varchar(30) DEFAULT NULL,
    `createDate` datetime DEFAULT NULL,
    `isActive` bit(1) DEFAULT NULL,
    PRIMARY KEY (`resultID`),
    KEY `creatorfk2_idx` (`creator`),
    CONSTRAINT `creatorfk2` FOREIGN KEY (`creator`)
        REFERENCES `account` (`userID`)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `account` VALUES ('guest','e1adc3949ba59abbe56e057f2f883e','guest@gmail.com','Guest','user','2014-01-01 00:00:00',1);
INSERT INTO `account` VALUES ('user1','e1adc3949ba59abbe56e057f2f883e','user1@gmail.com','User1','user','2014-01-01 00:00:00',1);
INSERT INTO `account` VALUES ('staff1','e1adc3949ba59abbe56e057f2f883e','staff1@gmail.com','Staff1','staff','2014-01-01 00:00:00',1);
INSERT INTO `account` VALUES ('admin1','e1adc3949ba59abbe56e057f2f883e','admin1@gmail.com','Admin1','admin','2014-01-01 00:00:00',1);

INSERT INTO `account` VALUES ('user2','e1adc3949ba59abbe56e057f2f883e','user2@gmail.com','User2','user','2014-01-01 00:00:00',1);
INSERT INTO `account` VALUES ('staff2','e1adc3949ba59abbe56e057f2f883e','staff2@gmail.com','Staff2','staff','2014-01-01 00:00:00',1);
INSERT INTO `account` VALUES ('admin2','e1adc3949ba59abbe56e057f2f883e','admin2@gmail.com','Admin2','admin','2014-01-01 00:00:00',1);

INSERT INTO `category` VALUES (1,'Biển báo cấm'),(2,'Biển báo nguy hiểm'),(3,'Biển hiệu lệnh'),(4,'Biển chỉ dẫn'),(5,'Biển phụ');
