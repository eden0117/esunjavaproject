CREATE DEFINER=`root`@`localhost` PROCEDURE `UserLogin`(in p_username  varchar(45),in p_userpassword varchar(45))
BEGIN
 DECLARE v_id INT;
DECLARE v_username VARCHAR(45);
DECLARE v_userpermission INT;

SELECT idVoter, Name, permission INTO v_id, v_username, v_userpermission
FROM voterlist
INNER JOIN password ON voterlist.idVoter = password.idPassword
WHERE voterlist.Name = p_username 
AND password.password = p_userpassword ;

UPDATE voterlist SET LoginStatus = 1 WHERE idVoter = v_id;

SELECT v_id AS id, v_username AS username, v_userpermission AS userpermission;
END