CREATE DEFINER=`root`@`localhost` PROCEDURE `CreateUser`(in username varchar(45),in userpassword varchar(45))
BEGIN
INSERT INTO voterlist (`Name`) VALUES (username);
INSERT INTO `mydb`.`password` (`password`) VALUES (userpassword);
END