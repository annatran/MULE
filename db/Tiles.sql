USE [MuleStateServer]
GO

/****** Object:  Table [dbo].[Tiles]    Script Date: 11/14/2013 06:59:46 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[Tiles](
	[tileX] [int] NULL,
	[tileY] [int] NULL,
	[terrain] [varchar](max) NULL,
	[propertyOwnerId] [int] NULL,
	[propertyMuleType] [varchar](max) NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

