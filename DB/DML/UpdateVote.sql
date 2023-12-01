CREATE PROCEDURE `UpdateVote` (in voteid int)
BEGIN
UPDATE voteitems SET voteStatus = 0 WHERE (idvote = voteid);
END
