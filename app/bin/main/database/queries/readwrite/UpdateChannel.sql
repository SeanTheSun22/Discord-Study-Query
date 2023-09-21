MERGE INTO Channels AS target
USING (VALUES ({0}, -1)) AS source (ChannelID, test)
ON target.ChannelID = source.ChannelID
WHEN MATCHED THEN
    UPDATE SET target.test = source.test
WHEN NOT MATCHED THEN
    INSERT (ChannelID, test) VALUES (source.ChannelID, source.test);