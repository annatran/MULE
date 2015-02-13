USE [MuleStateServer]
GO

/****** Object:  Table [dbo].[Resources]    Script Date: 11/14/2013 06:59:42 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[Resources](
	[resourceName] [varchar](max) NULL,
	[amount] [int] NULL,
	[playerId] [int] NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

