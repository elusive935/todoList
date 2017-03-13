USE test;
DROP TABLE IF EXISTS `all_tasks`;
CREATE TABLE IF NOT EXISTS `all_tasks` (`idTask` INT NOT NULL AUTO_INCREMENT , `text` VARCHAR(255), `status` INT, PRIMARY KEY (`idTask`));
INSERT INTO `all_tasks` (text, status) VALUES ('TodoTask01', 0), ('TodoTask02', 0), ('TodoTask03', 1), ('TodoTask04', 1), ('TodoTask05', 0), ('TodoTask06', 0), ('TodoTask07', 0), ('TodoTask08', 1), ('TodoTask09', 0), ('TodoTask10', 1), ('TodoTask11', 0), ('TodoTask12', 0), ('TodoTask13', 0), ('TodoTask14', 0), ('TodoTask15', 1), ('TodoTask16', 1), ('TodoTask17', 0), ('TodoTask18', 0), ('TodoTask19', 0), ('TodoTask20', 1), ('TodoTask21', 1), ('TodoTask22', 0), ('TodoTask23', 0), ('TodoTask24', 0), ('TodoTask25', 1), ('TodoTask26', 0), ('TodoTask27', 1);

