
create database dyncdb;
use dyncdb;

grant all privileges on `dbuser_%`.* to root@localhost identified by 'root' with grant option;
FLUSH PRIVILEGES;


CREATE TABLE `ISSUE` (
  `ISSUE_ID` INT(20) NOT NULL AUTO_INCREMENT UNIQUE,
  `USER_ID` VARCHAR(50) NOT NULL,
  `TYPE` VARCHAR(20),
  `SUBJECT` TEXT,
  `CONTENTS` TEXT,
  `DISPLAY` BOOLEAN,
  `RECOMMAND` INT(50),
  `REG_DATE` DATETIME,
  `UPLOAD` VARCHAR(50),
  PRIMARY KEY (`ISSUE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE `WINDOW` (
  `ISSUE_ID` INT(20) NOT NULL UNIQUE,
  `CODE_ID` INT(50) NOT NULL UNIQUE,
  `START_LINE` INT(20),
  `END_LINE` INT(20),
  `WINDOW_PATH` VARCHAR(200)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE `CODE` (
  `CODE_ID` INT(50) NOT NULL AUTO_INCREMENT UNIQUE,
  `CODE_REPOSITORY` INT(20),
  `CODE_SUBJECT` VARCHAR(255),
  `BASE_LANGUAGE` VARCHAR(100),
  `CODE_CONTENTS` TEXT,
  `REVISION` INT(50) NOT NULL,
  `USING` BOOLEAN,
  `REG_DATE` DATETIME,
  PRIMARY KEY (`CODE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE `TAG` (
  `USER_ID` INT(50) NOT NULL,
  `TAG_NAME` VARCHAR(200),
  `ISSUE_ID` INT(20) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE `USER` (
  `USER_ID` INT(50) NOT NULL AUTO_INCREMENT UNIQUE,
  `USER_NAVERHASH` TEXT,
  'USER_KAKAOHASH' TEXT,
  `USER_NAME` VARCHAR(10),
  `USER_DESCRIPTION` TEXT,
  `CODE_REPOSITORY` INT(20) NOT NULL UNIQUE,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE `CODE_REPOSITORY` (
  `CODE_REPOSITORY` INT(20) NOT NULL,
  `USER_ID` INT(50) NOT NULL,
  PRIMARY KEY (`CODE_REPOSITORY`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

ALTER TABLE `ISSUE` CHANGE `USER_ID` `USER_ID` INT(50);
ALTER TABLE `ISSUE` ADD FOREIGN KEY (`USER_ID`) REFERENCES `USER` (`USER_ID`);
ALTER TABLE `WINDOW`  ADD FOREIGN KEY (`ISSUE_ID`) REFERENCES `ISSUE` (`ISSUE_ID`);
ALTER TABLE `WINDOW`  ADD FOREIGN KEY (`CODE_ID`) REFERENCES `CODE` (`CODE_ID`);
ALTER TABLE `CODE`  ADD FOREIGN KEY (`CODE_REPOSITORY`) REFERENCES `CODE_REPOSITORY` (`CODE_REPOSITORY`);
ALTER TABLE `TAG`  ADD FOREIGN KEY (`USER_ID`) REFERENCES `USER` (`USER_ID`);
ALTER TABLE `CODE_REPOSITORY`  ADD FOREIGN KEY (`USER_ID`) REFERENCES `USER` (`USER_ID`);

INSERT INTO `USER` (`USER_ID`,`ISSUE_NAVERHASH`,`USER_NAME`,`USER_DESCRIPTION`,`CODE_REPOSITORY`) 
VALUES(1,'admin', 'admin', 'admin', 1);
