DROP TABLE IF EXISTS Guilds;
DROP TABLE IF EXISTS Categories;
DROP TABLE IF EXISTS Channels;
DROP TABLE IF EXISTS Threads;
CREATE TABLE Guilds (GuildID BIGINT PRIMARY KEY, Name NVARCHAR(250));
CREATE TABLE Categories (CategoryID BIGINT PRIMARY KEY, ParentID BIGINT, Name NVARCHAR(250));
CREATE TABLE Channels (ChannelID BIGINT PRIMARY KEY, ParentID BIGINT, Name NVARCHAR(250));
CREATE TABLE Threads (ThreadID BIGINT PRIMARY KEY, ParentID BIGINT, Name NVARCHAR(250), OriginalCreatorID BIGINT);
insert into Guilds (GuildID, Name) values (1, 'Test Guild');
insert into Categories (CategoryID, ParentID, Name) values (2, 1, 'Test Category');
insert into Channels (ChannelID, ParentID, Name) values (3, 2, 'Test Channel');
insert into Threads (ThreadID, ParentID, Name, OriginalCreatorID) values (4, 3, 'Test Thread', 1);

