MERGE INTO Guilds AS target
USING (VALUES ({0}, -1)) AS source (GuildID, test)
ON target.GuildID = source.GuildID
WHEN MATCHED THEN
    UPDATE SET target.test = source.test
WHEN NOT MATCHED THEN
    INSERT (GuildID, test) VALUES (source.GuildID, source.test);