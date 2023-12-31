-- MySQL Script generated by MySQL Workbench
-- Thu Nov 30 17:03:19 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`voteitems`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`voteitems` ;

CREATE TABLE IF NOT EXISTS `mydb`.`voteitems` (
  `idvote` INT NOT NULL AUTO_INCREMENT,
  `voteName` VARCHAR(45) NULL,
  `voteCount` INT NULL,
  `voteStatus` INT NOT NULL DEFAULT 1,
  PRIMARY KEY (`idvote`),
  UNIQUE INDEX `idvote_UNIQUE` (`idvote` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`voterlist`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`voterlist` ;

CREATE TABLE IF NOT EXISTS `mydb`.`voterlist` (
  `idVoter` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NULL,
  `LoginStatus` INT NOT NULL DEFAULT 0,
  `permission` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`idVoter`),
  UNIQUE INDEX `idVoter_UNIQUE` (`idVoter` ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`password`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`password` ;

CREATE TABLE IF NOT EXISTS `mydb`.`password` (
  `idPassword` INT NOT NULL AUTO_INCREMENT,
  `password` VARCHAR(45) NULL,
  PRIMARY KEY (`idPassword`),
  UNIQUE INDEX `idVoter_UNIQUE` (`idPassword` ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `mydb`.`voterecord`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`voterecord` ;

CREATE TABLE IF NOT EXISTS `mydb`.`voterecord` (
  `recordid` INT NOT NULL AUTO_INCREMENT,
  `idVote` INT NULL,
  `idVoter` INT NULL,
  `status` INT NOT NULL DEFAULT 1,
  PRIMARY KEY (`recordid`),
  UNIQUE INDEX `record_UNIQUE` (`recordid` ASC) VISIBLE,
  INDEX `idVote_idx` (`idVote` ASC) VISIBLE,
  INDEX `idVoter_idx` (`idVoter` ASC) VISIBLE,
  CONSTRAINT `idVote`
    FOREIGN KEY (`idVote`)
    REFERENCES `mydb`.`voteitems` (`idvote`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `idVoter`
    FOREIGN KEY (`idVoter`)
    REFERENCES `mydb`.`voterlist` (`idVoter`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
