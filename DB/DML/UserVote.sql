CREATE DEFINER=`root`@`localhost` PROCEDURE `UserVote`(in voteid int,in voterid int)
BEGIN
INSERT INTO voterecord (idVote, idVoter) VALUES (voteid, voterid);
UPDATE voteitems SET voteCount = voteCount + 1 WHERE (idvote = voteid);
END