MERGE INTO Categories AS target
USING (VALUES ({0}, -1)) AS source (CategoryID, test)
ON target.CategoryID = source.CategoryID
WHEN MATCHED THEN
    UPDATE SET target.test = source.test
WHEN NOT MATCHED THEN
    INSERT (CategoryID, test) VALUES (source.CategoryID, source.test);