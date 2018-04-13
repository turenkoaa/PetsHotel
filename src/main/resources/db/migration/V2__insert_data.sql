USE `pet_hotel`;

-- -----------------------------------------------------
-- Data for table `user`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `user` (`user_id`, `first_name`, `last_name`, `address`, `active`, `user_type`) VALUES (1, 'Морозова', 'Виолетта', 'Дыбенко', 1, '\'owner\'');
INSERT INTO `user` (`user_id`, `first_name`, `last_name`, `address`, `active`, `user_type`) VALUES (2, 'Иванова', 'Мария', 'Проспект Просвящения', 1, '\'owner\'');
INSERT INTO `user` (`user_id`, `first_name`, `last_name`, `address`, `active`, `user_type`) VALUES (3, 'Михайлов', 'Виктор', 'Проспект Большевиков', 1, '\'owner\'');
INSERT INTO `user` (`user_id`, `first_name`, `last_name`, `address`, `active`, `user_type`) VALUES (4, 'Смирнова', 'Валерия', 'Ладожская', 1, '\'sitter\'');
INSERT INTO `user` (`user_id`, `first_name`, `last_name`, `address`, `active`, `user_type`) VALUES (5, 'Кузнецов', 'Сергей', 'Дыбенко', 1, '\'both\'');
INSERT INTO `user` (`user_id`, `first_name`, `last_name`, `address`, `active`, `user_type`) VALUES (6, 'Торопов', 'Иван', 'Удельная', 0, '\'sitter\'');
INSERT INTO `user` (`user_id`, `first_name`, `last_name`, `address`, `active`, `user_type`) VALUES (7, 'админ', 'админ', '-', 1, '\'admin\'');

COMMIT;


-- -----------------------------------------------------
-- Data for table `pet`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `pet` (`pet_id`, `pet_type`, `name`, `age`, `passport`, `user_user_id`) VALUES (1, '\'cat\'', 'мурзик', 2, 1231, 1);
INSERT INTO `pet` (`pet_id`, `pet_type`, `name`, `age`, `passport`, `user_user_id`) VALUES (2, '\'dog\'', 'дина', 1, 1232, 1);
INSERT INTO `pet` (`pet_id`, `pet_type`, `name`, `age`, `passport`, `user_user_id`) VALUES (3, '\'other\'', 'аксель', 6, 1234, 2);
INSERT INTO `pet` (`pet_id`, `pet_type`, `name`, `age`, `passport`, `user_user_id`) VALUES (4, '\'other\'', 'леся', 9, 1235, 5);

COMMIT;


-- -----------------------------------------------------
-- Data for table `request`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `request` (`request_id`, `start_date`, `end_date`, `status`, `user_user_id`, `pet_pet_id`) VALUES (1, ' \'12-05-2018\'', ' \'22-05-2018\'', '', 1, 1);
INSERT INTO `request` (`request_id`, `start_date`, `end_date`, `status`, `user_user_id`, `pet_pet_id`) VALUES (2, ' \'1-06-2018\'', ' \'5-06-2018\'', '', 2, 3);
INSERT INTO `request` (`request_id`, `start_date`, `end_date`, `status`, `user_user_id`, `pet_pet_id`) VALUES (3, ' \'1-06-2016\'', ' \'1-07-2016\'', '\'anulled\'', 5, 4);

COMMIT;


-- -----------------------------------------------------
-- Data for table `response`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `response` (`response_id`, `status`, `details`, `request_request_id`, `user_user_id`) VALUES (1, '', '', 1, 4);
INSERT INTO `response` (`response_id`, `status`, `details`, `request_request_id`, `user_user_id`) VALUES (2, DEFAULT, NULL, 2, 5);
INSERT INTO `response` (`response_id`, `status`, `details`, `request_request_id`, `user_user_id`) VALUES (3, '\'reject\'', NULL, 3, 6);

COMMIT;
