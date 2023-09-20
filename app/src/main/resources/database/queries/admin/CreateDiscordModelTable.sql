DROP TABLE IF EXISTS DiscordModel;
CREATE TABLE DiscordModel (DiscordID BIGINT PRIMARY KEY, ParentID BIGINT, Name NVARCHAR(250));
DROP TABLE IF EXISTS DiscordModelChildren;
CREATE TABLE DiscordModelChildren (ParentID BIGINT, ChildID BIGINT, PRIMARY KEY (ParentID, ChildID));