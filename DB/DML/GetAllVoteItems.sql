CREATE DEFINER=`root`@`localhost` PROCEDURE `GetAllVoteItems`()
BEGIN
select * from voteitems;
END