CREATE DEFINER=`root`@`localhost` PROCEDURE `CreateUser`(IN username VARCHAR(45), IN userpassword VARCHAR(45), OUT result INT)
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM voterlist
        INNER JOIN `password` ON voterlist.idVoter = `password`.idpassword  
        WHERE voterlist.Name = username AND `password`.`password` = userpassword
    ) THEN
        INSERT INTO voterlist (`Name`) VALUES (username);
        INSERT INTO `mydb`.`password` (`password`) VALUES (userpassword);
        SET result = 1;  -- 表示創建成功
    ELSE
        SET result = 0;  -- 表示用戶已存在
    END IF;
    select result;
END