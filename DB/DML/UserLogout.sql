CREATE DEFINER=`root`@`localhost` PROCEDURE `UserLogout`(in p_userid int)
BEGIN
declare v_userid int;
select idVoter into v_userid from voterlist where idVoter = p_userid ;
update voterlist set LoginStatus = 0  where idVoter = v_userid;
END