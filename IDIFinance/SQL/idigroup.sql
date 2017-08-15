CREATE TABLE `idigroup`.`BALANCE_SHEET` ( `ASSETS_ID` INT NOT NULL AUTO_INCREMENT , `ASSETS_NAME` VARCHAR(255) NOT NULL , RULE VARCHAR(255) NULL ,`ASSETS_CODE` VARCHAR(10) NOT NULL , `NOTE` VARCHAR(255) NULL , `START_VALUE` DOUBLE NULL, `END_VALUE` DOUBLE NULL , `CHANGED_RATIO` DOUBLE NULL , `ASSETS_PERIOD` TIMESTAMP NOT NULL , `DESCRIPTION` VARCHAR(255) NULL, PRIMARY KEY (`ASSETS_ID`) ) ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;