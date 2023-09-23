MERGE INTO Threads AS target
USING (VALUES ({0}, {1}, {2})) AS source (ThreadID, OriginalCreatorID, ReopenUserID)
ON target.ThreadID = source.ThreadID
WHEN MATCHED THEN
    UPDATE SET target.OriginalCreatorID = source.OriginalCreatorID,
               target.ReopenUserID = source.ReopenUserID
WHEN NOT MATCHED THEN
    INSERT (ThreadID, OriginalCreatorID, ReopenUserID) VALUES (source.ThreadID, source.OriginalCreatorID, source.ReopenUserID);