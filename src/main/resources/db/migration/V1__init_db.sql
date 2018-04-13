USE `pet_hotel` ;

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` BIGINT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(250) NOT NULL,
  `active` TINYINT(1) NOT NULL DEFAULT 1,
  `user_type` ENUM('owner', 'sitter', 'both', 'admin') NOT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `pet`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `pet` (
  `pet_id` BIGINT NOT NULL AUTO_INCREMENT,
  `pet_type` ENUM('cat', 'dog', 'other') NOT NULL DEFAULT 'other',
  `name` VARCHAR(45) NOT NULL,
  `age` INT NOT NULL,
  `passport` BIGINT NOT NULL,
  `user_user_id` BIGINT NOT NULL,
  PRIMARY KEY (`pet_id`),
  CONSTRAINT `fk_pet_user1`
    FOREIGN KEY (`user_user_id`)
    REFERENCES `user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `passport_UNIQUE` ON `pet` (`passport` ASC);

CREATE INDEX `fk_pet_user1_idx` ON `pet` (`user_user_id` ASC);


-- -----------------------------------------------------
-- Table `user_credentials`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_credentials` (
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `user_user_id` BIGINT NOT NULL,
  PRIMARY KEY (`user_user_id`),
  CONSTRAINT `fk_user_credentials_user`
    FOREIGN KEY (`user_user_id`)
    REFERENCES `user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE UNIQUE INDEX `login_UNIQUE` ON `user_credentials` (`login` ASC);

CREATE UNIQUE INDEX `email_UNIQUE` ON `user_credentials` (`email` ASC);


-- -----------------------------------------------------
-- Table `request`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `request` (
  `request_id` BIGINT NOT NULL AUTO_INCREMENT,
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `status` ENUM('new', 'solved', 'anulled') NOT NULL DEFAULT 'new',
  `user_user_id` BIGINT NOT NULL,
  `pet_pet_id` BIGINT NOT NULL,
  PRIMARY KEY (`request_id`),
  CONSTRAINT `fk_request_user1`
    FOREIGN KEY (`user_user_id`)
    REFERENCES `user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_request_pet1`
    FOREIGN KEY (`pet_pet_id`)
    REFERENCES `pet` (`pet_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_request_user1_idx` ON `request` (`user_user_id` ASC);

CREATE INDEX `fk_request_pet1_idx` ON `request` (`pet_pet_id` ASC);


-- -----------------------------------------------------
-- Table `response`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `response` (
  `response_id` BIGINT NOT NULL AUTO_INCREMENT,
  `status` ENUM('proposed', 'assign', 'reject') NOT NULL DEFAULT 'proposed',
  `details` VARCHAR(250) NULL,
  `request_request_id` BIGINT NOT NULL,
  `user_user_id` BIGINT NOT NULL,
  PRIMARY KEY (`response_id`),
  CONSTRAINT `fk_response_request1`
    FOREIGN KEY (`request_request_id`)
    REFERENCES `request` (`request_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_response_user1`
    FOREIGN KEY (`user_user_id`)
    REFERENCES `user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_response_request1_idx` ON `response` (`request_request_id` ASC);

CREATE INDEX `fk_response_user1_idx` ON `response` (`user_user_id` ASC);