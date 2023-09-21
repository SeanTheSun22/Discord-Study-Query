MERGE INTO Threads AS target
USING (VALUES ({0}, {1})) AS source (ThreadID, OriginalCreatorID)
ON target.ThreadID = source.ThreadID
WHEN MATCHED THEN
    UPDATE SET target.OriginalCreatorID = source.OriginalCreatorID
WHEN NOT MATCHED THEN
    INSERT (ThreadID, OriginalCreatorID) VALUES (source.ThreadID, source.OriginalCreatorID);