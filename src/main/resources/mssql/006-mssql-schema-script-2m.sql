USE [master]
GO

/****** Object:  Database [apiary_2m]    Script Date: 07/18/2013 17:26:43 ******/
IF  EXISTS (SELECT name FROM sys.databases WHERE name = N'apiary_2m')
DROP DATABASE [apiary_2m]
GO

USE [master]
GO

/****** Object:  Database [apiary_2m]    Script Date: 07/18/2013 17:26:43 ******/
CREATE DATABASE [apiary_2m]
GO


USE [apiary_2m]
GO

/****** Object:  Table [dbo].[Person]    Script Date: 07/18/2013 17:31:54 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Person]') AND type in (N'U'))
DROP TABLE [dbo].[Person]
GO

USE [apiary_2m]
GO

/****** Object:  Table [dbo].[Person]    Script Date: 07/18/2013 17:31:54 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[Person](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[name] [varchar](255) NOT NULL,
	[level] [int] NOT NULL,
 CONSTRAINT [PK_Person] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

USE [apiary_2m]
GO

/****** Object:  Index [IX_Person]    Script Date: 07/18/2013 17:39:45 ******/
IF  EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID(N'[dbo].[Person]') AND name = N'IX_Person')
DROP INDEX [IX_Person] ON [dbo].[Person] WITH ( ONLINE = OFF )
GO

USE [apiary_2m]
GO

/****** Object:  Index [IX_Person]    Script Date: 07/18/2013 17:39:45 ******/
CREATE NONCLUSTERED INDEX [IX_Person] ON [dbo].[Person] 
(
	[name] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO

USE [apiary_2m]
GO

/****** Object:  Index [IX_Person_1]    Script Date: 07/18/2013 17:40:13 ******/
IF  EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID(N'[dbo].[Person]') AND name = N'IX_Person_1')
DROP INDEX [IX_Person_1] ON [dbo].[Person] WITH ( ONLINE = OFF )
GO

USE [apiary_2m]
GO

/****** Object:  Index [IX_Person_1]    Script Date: 07/18/2013 17:40:13 ******/
CREATE NONCLUSTERED INDEX [IX_Person_1] ON [dbo].[Person] 
(
	[level] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO


USE [apiary_2m]
GO

IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Person_Direct_Reportee_Person]') AND parent_object_id = OBJECT_ID(N'[dbo].[Person_Direct_Reportee]'))
ALTER TABLE [dbo].[Person_Direct_Reportee] DROP CONSTRAINT [FK_Person_Direct_Reportee_Person]
GO

IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Person_Direct_Reportee_Person1]') AND parent_object_id = OBJECT_ID(N'[dbo].[Person_Direct_Reportee]'))
ALTER TABLE [dbo].[Person_Direct_Reportee] DROP CONSTRAINT [FK_Person_Direct_Reportee_Person1]
GO

USE [apiary_2m]
GO

/****** Object:  Table [dbo].[Person_Direct_Reportee]    Script Date: 07/18/2013 17:40:59 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Person_Direct_Reportee]') AND type in (N'U'))
DROP TABLE [dbo].[Person_Direct_Reportee]
GO

USE [apiary_2m]
GO

/****** Object:  Table [dbo].[Person_Direct_Reportee]    Script Date: 07/18/2013 17:40:59 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Person_Direct_Reportee](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[person_id] [bigint] NOT NULL,
	[directly_manages] [bigint] NOT NULL,
 CONSTRAINT [PK_Person_Direct_Reportee] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[Person_Direct_Reportee]  WITH CHECK ADD  CONSTRAINT [FK_Person_Direct_Reportee_Person] FOREIGN KEY([person_id])
REFERENCES [dbo].[Person] ([id])
GO

ALTER TABLE [dbo].[Person_Direct_Reportee] CHECK CONSTRAINT [FK_Person_Direct_Reportee_Person]
GO

ALTER TABLE [dbo].[Person_Direct_Reportee]  WITH CHECK ADD  CONSTRAINT [FK_Person_Direct_Reportee_Person1] FOREIGN KEY([directly_manages])
REFERENCES [dbo].[Person] ([id])
GO

ALTER TABLE [dbo].[Person_Direct_Reportee] CHECK CONSTRAINT [FK_Person_Direct_Reportee_Person1]
GO

USE [apiary_2m]
GO

/****** Object:  Index [IX_Person_Direct_Reportee]    Script Date: 07/18/2013 17:42:45 ******/
IF  EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID(N'[dbo].[Person_Direct_Reportee]') AND name = N'IX_Person_Direct_Reportee')
DROP INDEX [IX_Person_Direct_Reportee] ON [dbo].[Person_Direct_Reportee] WITH ( ONLINE = OFF )
GO

USE [apiary_2m]
GO

/****** Object:  Index [IX_Person_Direct_Reportee]    Script Date: 07/18/2013 17:42:45 ******/
CREATE NONCLUSTERED INDEX [IX_Person_Direct_Reportee] ON [dbo].[Person_Direct_Reportee] 
(
	[person_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO

USE [apiary_2m]
GO

/****** Object:  Index [IX_Person_Direct_Reportee_1]    Script Date: 07/18/2013 17:43:23 ******/
IF  EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID(N'[dbo].[Person_Direct_Reportee]') AND name = N'IX_Person_Direct_Reportee_1')
DROP INDEX [IX_Person_Direct_Reportee_1] ON [dbo].[Person_Direct_Reportee] WITH ( ONLINE = OFF )
GO

USE [apiary_2m]
GO

/****** Object:  Index [IX_Person_Direct_Reportee_1]    Script Date: 07/18/2013 17:43:23 ******/
CREATE NONCLUSTERED INDEX [IX_Person_Direct_Reportee_1] ON [dbo].[Person_Direct_Reportee] 
(
	[directly_manages] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO








