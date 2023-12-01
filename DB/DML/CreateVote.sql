CREATE DEFINER=`root`@`localhost` PROCEDURE `CreateVote`(in voteName varchar(45))
BEGIN
INSERT INTO voteitems (voteName, voteCount) VALUES (voteName, 0);
END