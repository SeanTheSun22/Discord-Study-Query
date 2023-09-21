MERGE INTO Channels AS target
USING (VALUES ({0}, {1}, {2})) AS source (ChannelID, PingRoleID, ModeratorRoleID)
ON target.ChannelID = source.ChannelID
WHEN MATCHED THEN
    UPDATE SET 
        target.PingRoleID = source.PingRoleID,
        target.ModeratorRoleID = source.ModeratorRoleID
WHEN NOT MATCHED THEN
    INSERT (ChannelID, PingRoleID, ModeratorRoleID) 
    VALUES (source.ChannelID, source.PingRoleID, source.ModeratorRoleID);