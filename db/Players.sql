USE [MuleStateServer]
GO

/****** Object:  Table [dbo].[Players]    Script Date: 11/14/2013 06:59:36 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[Players](
	[playerId] [int] NOT NULL,
	[playerName] [varchar](max) NULL,
	[playerColor] [int] NULL,
	[race] [varchar](max) NULL,
	[score] [int] NULL,
	[time] [int] NULL,
	[muleType] [varchar](max) NULL,
 CONSTRAINT [PK_Players] PRIMARY KEY CLUSTERED 
(
	[playerId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

