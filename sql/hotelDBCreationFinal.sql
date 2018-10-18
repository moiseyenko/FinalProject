-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema hotel
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema hotel
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hotel` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `hotel` ;

-- -----------------------------------------------------
-- Table `hotel`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel`.`account` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `login` VARCHAR(25) NULL DEFAULT NULL COMMENT 'login',
  `password` VARCHAR(150) NULL DEFAULT NULL COMMENT 'password',
  `email` VARCHAR(46) NULL DEFAULT NULL COMMENT 'email',
  `admin` BIT(1) NOT NULL DEFAULT b'0' COMMENT 'admin= 1; user = 0',
  `removed` BIT(1) NOT NULL DEFAULT b'0' COMMENT 'removed=1, active=0',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
COMMENT = 'account';


-- -----------------------------------------------------
-- Table `hotel`.`bankaccount`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel`.`bankaccount` (
  `account_id` INT(10) UNSIGNED NOT NULL COMMENT 'account id',
  `amount` DECIMAL(10,2) NULL DEFAULT '0.00' COMMENT 'amount of bank account',
  PRIMARY KEY (`account_id`),
  CONSTRAINT `fk_bankaccount_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `hotel`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
COMMENT = 'bank account';


-- -----------------------------------------------------
-- Table `hotel`.`class`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel`.`class` (
  `id` VARCHAR(25) NOT NULL COMMENT 'room class id',
  `removed` BIT(1) NOT NULL DEFAULT b'0' COMMENT 'removed=1, active=0',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
COMMENT = 'room class';


-- -----------------------------------------------------
-- Table `hotel`.`nationality`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel`.`nationality` (
  `id` CHAR(2) NOT NULL COMMENT 'nationality id',
  `country` VARCHAR(80) NULL DEFAULT NULL COMMENT 'country name',
  `removed` BIT(1) NOT NULL DEFAULT b'0' COMMENT 'removed=1, active=0',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `country_UNIQUE` (`country` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
COMMENT = 'nationality';


-- -----------------------------------------------------
-- Table `hotel`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel`.`client` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'client id',
  `first_name` VARCHAR(45) NULL DEFAULT NULL COMMENT 'first name',
  `last_name` VARCHAR(45) NULL DEFAULT NULL COMMENT 'last name',
  `passport` VARCHAR(15) NULL DEFAULT NULL COMMENT 'passport code',
  `nationality_id` CHAR(2) NOT NULL COMMENT 'nationality id',
  `blacklist` BIT(1) NOT NULL DEFAULT b'0' COMMENT 'in black list=1, not =0',
  PRIMARY KEY (`id`),
  INDEX `fk_client_nationality1_idx` (`nationality_id` ASC),
  CONSTRAINT `fk_client_nationality1`
    FOREIGN KEY (`nationality_id`)
    REFERENCES `hotel`.`nationality` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
COMMENT = 'client';


-- -----------------------------------------------------
-- Table `hotel`.`room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel`.`room` (
  `number` SMALLINT(1) UNSIGNED NOT NULL COMMENT 'room number',
  `capacity` SMALLINT(1) UNSIGNED NOT NULL COMMENT 'room capacity',
  `class_id` VARCHAR(25) NOT NULL COMMENT 'room class',
  `price` DECIMAL(10,2) NOT NULL COMMENT 'room price',
  `removed` BIT(1) NOT NULL DEFAULT b'0' COMMENT 'removed=1, acitve=0',
  PRIMARY KEY (`number`),
  INDEX `fk_room_class1_idx` (`class_id` ASC),
  CONSTRAINT `fk_room_class1`
    FOREIGN KEY (`class_id`)
    REFERENCES `hotel`.`class` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
COMMENT = 'room';


-- -----------------------------------------------------
-- Table `hotel`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hotel`.`order` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'order id',
  `client_id` INT(10) UNSIGNED NOT NULL COMMENT 'client id',
  `account_id` INT(10) UNSIGNED NOT NULL COMMENT 'account id',
  `room_number` SMALLINT(1) UNSIGNED NOT NULL COMMENT 'room number',
  `from` DATE NULL DEFAULT NULL COMMENT 'сheck in date',
  `to` DATE NULL DEFAULT NULL COMMENT 'date of eviction',
  `cost` DECIMAL(10,2) NOT NULL COMMENT 'order cost',
  `removed` BIT(1) NOT NULL DEFAULT b'0' COMMENT 'removed=1, active=0',
  PRIMARY KEY (`id`),
  INDEX `fk_order_client1_idx` (`client_id` ASC),
  INDEX `fk_order_account1_idx` (`account_id` ASC),
  INDEX `fk_order_room1_idx` (`room_number` ASC),
  CONSTRAINT `fk_order_client1`
    FOREIGN KEY (`client_id`)
    REFERENCES `hotel`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `hotel`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_room1`
    FOREIGN KEY (`room_number`)
    REFERENCES `hotel`.`room` (`number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
COMMENT = 'order';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
