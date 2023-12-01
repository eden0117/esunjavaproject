CREATE DEFINER=`root`@`localhost` PROCEDURE `GetAllVote`()
BEGIN
select Name,voteName,voteStatus from voterecord inner join voterlist on voterecord.idVoter = voterlist.idVoter inner join voteitems on voterecord.idVote = voteitems.idVote;
END