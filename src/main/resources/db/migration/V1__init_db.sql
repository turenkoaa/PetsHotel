USE `pet_hotel` ;

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` BIGINT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `address` VARCHAR(250) NOT NULL,
  `active` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `email_UNIQUE` ON `user` (`email` ASC);

-- -----------------------------------------------------
-- Table `review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `review` (
  `review_id` BIGINT NOT NULL AUTO_INCREMENT,
  `like` TINYINT(1) NOT NULL DEFAULT 1,
  `comment` VARCHAR(300) NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`review_id`),
  CONSTRAINT `fk_review_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `fk_review_user1_idx` ON `review` (`user_id` ASC);
-- -----------------------------------------------------
-- Table `pet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pet` (
  `pet_id` BIGINT NOT NULL AUTO_INCREMENT,
  `pet_type` ENUM('CAT', 'DOG', 'OTHER') NOT NULL DEFAULT 'OTHER',
  `name` VARCHAR(45) NOT NULL,
  `age` INT NOT NULL,
  `passport` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`pet_id`),
  CONSTRAINT `fk_pet_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `passport_UNIQUE` ON `pet` (`passport` ASC);

CREATE INDEX `fk_pet_user1_idx` ON `pet` (`user_id` ASC);


-- -----------------------------------------------------
-- Table `user_credentials`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_credentials` (
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_user_credentials_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `login_UNIQUE` ON `user_credentials` (`login` ASC);


-- -----------------------------------------------------
-- Table `request`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `request` (
  `request_id` BIGINT NOT NULL AUTO_INCREMENT,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `status` ENUM('NEW', 'SOLVED', 'ANULLED') NOT NULL DEFAULT 'NEW',
  `user_id` BIGINT NOT NULL,
  `cost` INT NOT NULL,
  `paid` TINYINT(1) NOT NULL DEFAULT 0,
  `pet_id` BIGINT NOT NULL,
  PRIMARY KEY (`request_id`),
  CONSTRAINT `fk_request_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_request_pet1`
    FOREIGN KEY (`pet_id`)
    REFERENCES `pet` (`pet_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE INDEX `fk_request_user1_idx` ON `request` (`user_id` ASC);

CREATE INDEX `fk_request_pet1_idx` ON `request` (`pet_id` ASC);

-- -----------------------------------------------------
-- Table `response`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `response` (
  `response_id` BIGINT NOT NULL AUTO_INCREMENT,
  `status` ENUM('PROPOSED', 'ACCEPTED', 'REJECTED') NOT NULL DEFAULT 'PROPOSED',
  `details` VARCHAR(250) NULL,
  `request_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `cost` INT NULL,
  PRIMARY KEY (`response_id`),
  CONSTRAINT `fk_response_request1`
    FOREIGN KEY (`request_id`)
    REFERENCES `request` (`request_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_response_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_response_request1_idx` ON `response` (`request_id` ASC);

CREATE INDEX `fk_response_user1_idx` ON `response` (`user_id` ASC);