CREATE SCHEMA `ylazy` DEFAULT CHARACTER SET utf8 ;

ALTER TABLE `ylazy`.`lazy_task` 
CHANGE COLUMN `result_json` `result_json` LONGTEXT NULL DEFAULT NULL ,
CHANGE COLUMN `url` `url` LONGTEXT NULL DEFAULT NULL ;

update lazy_task t1, lazy_task t2 set t1.result_json = t2.result_json where t1.id in(1,2) and t2.id=3;

