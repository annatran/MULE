USE [MuleStateServer]
GO

/****** Object:  Table [dbo].[GameController]    Script Date: 11/14/2013 06:59:23 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[GameController](
	[difficulty] [int] NULL,
	[currentPlayerRemainingTime] [int] NULL,
	[currentPlayerInitialTime] [int] NULL,
	[currentPlayerId] [int] NULL,
	[currentRound] [int] NULL
) ON [PRIMARY]

GO

