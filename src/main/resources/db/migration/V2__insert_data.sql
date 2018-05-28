USE `pet_hotel`;
SET NAMES utf8;

-- -----------------------------------------------------
-- Data for table `user`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `user` (`user_id`, `first_name`, `last_name`, `address`, `active`, `email`) VALUES (1, 'Морозова', 'Виолетта', 'Дыбенко', 1, 'morozova@smth.com');
INSERT INTO `user` (`user_id`, `first_name`, `last_name`, `address`, `active`, `email`) VALUES (2, 'Иванова', 'Мария', 'Проспект Просвящения', 1, 'ivanova@smth.com');
INSERT INTO `user` (`user_id`, `first_name`, `last_name`, `address`, `active`, `email`) VALUES (3, 'Михайлов', 'Виктор', 'Проспект Большевиков', 1, 'mihailov@smth.com');
INSERT INTO `user` (`user_id`, `first_name`, `last_name`, `address`, `active`, `email`) VALUES (4, 'Смирнова', 'Валерия', 'Ладожская', 1, 'smirnova@smth.com');
INSERT INTO `user` (`user_id`, `first_name`, `last_name`, `address`, `active`, `email`) VALUES (5, 'Кузнецов', 'Сергей', 'Дыбенко', 1, 'kuznetsov@smth.com');
INSERT INTO `user` (`user_id`, `first_name`, `last_name`, `address`, `active`, `email`) VALUES (6, 'Торопов', 'Иван', 'Удельная', 0, 'toropov@smth.com');
-- INSERT INTO `user` (`user_id`, `first_name`, `last_name`, `address`, `active`, `email`) VALUES (7, 'админ', 'админ', '-', 1, 'admin@smth.com');

COMMIT;


-- -----------------------------------------------------
-- Data for table `pet`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `pet` (`pet_id`, `pet_type`, `name`, `age`, `passport`, `user_id`) VALUES (1, 'CAT', 'мурзик', 2, 1231, 1);
INSERT INTO `pet` (`pet_id`, `pet_type`, `name`, `age`, `passport`, `user_id`) VALUES (2, 'DOG', 'дина', 1, 1232, 1);
INSERT INTO `pet` (`pet_id`, `pet_type`, `name`, `age`, `passport`, `user_id`) VALUES (3, 'OTHER', 'аксель', 6, 1234, 2);
INSERT INTO `pet` (`pet_id`, `pet_type`, `name`, `age`, `passport`, `user_id`) VALUES (4, 'OTHER', 'леся', 9, 1235, 5);

COMMIT;


-- -----------------------------------------------------
-- Data for table `request`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `request` (`request_id`, `start_date`, `end_date`, `status`, `user_id`, `cost`) VALUES (1, '2018-05-12', '2018-05-25', DEFAULT, 1, 300);
INSERT INTO `request` (`request_id`, `start_date`, `end_date`, `status`, `user_id`, `cost`) VALUES (2, '2018-06-1', '2018-06-5', DEFAULT, 2, 1200);
INSERT INTO `request` (`request_id`, `start_date`, `end_date`, `status`, `user_id`, `cost`) VALUES (3, '2016-06-1', '2016-07-1', 'ANULLED', 5, 2000);

COMMIT;

-- -----------------------------------------------------
-- Data for table `pet_request`
-- -----------------------------------------------------

START TRANSACTION;
INSERT INTO `pet_request` (`request_id`, `pet_id`) VALUES (1, 1);
INSERT INTO `pet_request` (`request_id`, `pet_id`) VALUES (2, 3);
INSERT INTO `pet_request` (`request_id`, `pet_id`) VALUES (3, 4);

COMMIT;

-- -----------------------------------------------------
-- Data for table `response`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `response` (`response_id`, `status`, `details`, `request_id`, `user_id`, `cost`) VALUES (1, DEFAULT, NULL, 1, 4, 400);
INSERT INTO `response` (`response_id`, `status`, `details`, `request_id`, `user_id`, `cost`) VALUES (2, DEFAULT, NULL, 2, 5, 1000);
INSERT INTO `response` (`response_id`, `status`, `details`, `request_id`, `user_id`, `cost`) VALUES (3, 'REJECTED', NULL, 3, 6, 1800);

COMMIT;
