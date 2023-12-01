CREATE PROCEDURE `GetUserVoted` (in p_userid int )
BEGIN
SELECT voteitems.idvote, voteitems.voteName FROM voteitems WHERE voteitems.idvote IN ( SELECT voterecord.idVote FROM voterecord WHERE voterecord.idVoter = 2);
END
