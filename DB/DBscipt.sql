USE [master]
GO
/****** Object:  Database [DB_SWP391]    Script Date: 2/6/2025 10:22:29 PM ******/
CREATE DATABASE [DB_SWP391]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'DB_SWP391', FILENAME = N'C:\FPT\Ki 5\SWP391\Database\DB_SWP391.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'DB_SWP391_log', FILENAME = N'C:\FPT\Ki 5\SWP391\Database\DB_SWP391_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [DB_SWP391] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [DB_SWP391].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [DB_SWP391] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [DB_SWP391] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [DB_SWP391] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [DB_SWP391] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [DB_SWP391] SET ARITHABORT OFF 
GO
ALTER DATABASE [DB_SWP391] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [DB_SWP391] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [DB_SWP391] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [DB_SWP391] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [DB_SWP391] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [DB_SWP391] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [DB_SWP391] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [DB_SWP391] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [DB_SWP391] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [DB_SWP391] SET  ENABLE_BROKER 
GO
ALTER DATABASE [DB_SWP391] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [DB_SWP391] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [DB_SWP391] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [DB_SWP391] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [DB_SWP391] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [DB_SWP391] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [DB_SWP391] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [DB_SWP391] SET RECOVERY FULL 
GO
ALTER DATABASE [DB_SWP391] SET  MULTI_USER 
GO
ALTER DATABASE [DB_SWP391] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [DB_SWP391] SET DB_CHAINING OFF 
GO
ALTER DATABASE [DB_SWP391] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [DB_SWP391] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [DB_SWP391] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [DB_SWP391] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'DB_SWP391', N'ON'
GO
ALTER DATABASE [DB_SWP391] SET QUERY_STORE = ON
GO
ALTER DATABASE [DB_SWP391] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [DB_SWP391]
GO
/****** Object:  Table [dbo].[Conversation]    Script Date: 2/6/2025 10:22:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Conversation](
	[conversationID] [int] IDENTITY(1,1) NOT NULL,
	[MentorID] [int] NULL,
	[MenteeID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[conversationID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[CV]    Script Date: 2/6/2025 10:22:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CV](
	[CvID] [int] IDENTITY(1,1) NOT NULL,
	[ProfessionIntroduction] [nvarchar](255) NULL,
	[ServiceDescription] [nvarchar](255) NULL,
	[CashPerSlot] [int] NOT NULL,
	[rejectReason] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[CvID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Follow]    Script Date: 2/6/2025 10:22:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Follow](
	[FollowID] [int] IDENTITY(1,1) NOT NULL,
	[MentorID] [int] NULL,
	[MenteeID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[FollowID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[FollowRequest]    Script Date: 2/6/2025 10:22:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[FollowRequest](
	[RequestID] [int] IDENTITY(1,1) NOT NULL,
	[RequestTime] [date] NULL,
	[DeadLineTime] [date] NULL,
	[Subject] [nvarchar](255) NULL,
	[Content] [nvarchar](255) NULL,
	[Status] [nvarchar](20) NULL,
	[MentorID] [int] NULL,
	[SenderID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[RequestID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Mentee]    Script Date: 2/6/2025 10:22:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Mentee](
	[UserID] [int] NULL,
	[MenteeStatus] [nvarchar](10) NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Mentor]    Script Date: 2/6/2025 10:22:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Mentor](
	[UserID] [int] NULL,
	[Description] [nvarchar](255) NULL,
	[CvID] [int] NULL,
	[Achivement] [nvarchar](255) NULL,
	[MentorStatus] [nvarchar](10) NOT NULL,
	[Skill] [nvarchar](255) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[MentorSkills]    Script Date: 2/6/2025 10:22:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[MentorSkills](
	[SkillID] [int] NULL,
	[MentorID] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Message]    Script Date: 2/6/2025 10:22:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Message](
	[MessageID] [int] IDENTITY(1,1) NOT NULL,
	[conversationID] [int] NULL,
	[SenderID] [int] NULL,
	[sentAt] [datetime] NULL,
	[msgContent] [ntext] NULL,
PRIMARY KEY CLUSTERED 
(
	[MessageID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Payment]    Script Date: 2/6/2025 10:22:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Payment](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Status] [nvarchar](255) NULL,
	[Balance] [int] NULL,
	[UserID] [int] NULL,
	[ReceiverID] [int] NULL,
	[Time] [datetime] NULL,
	[RequestID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Promotion]    Script Date: 2/6/2025 10:22:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Promotion](
	[PromotionId] [int] IDENTITY(1,1) NOT NULL,
	[Code] [nvarchar](50) NOT NULL,
	[DiscountPercentage] [decimal](5, 2) NOT NULL,
	[StartDate] [date] NOT NULL,
	[EndDate] [date] NOT NULL,
	[UserId] [int] NULL,
	[SkillId] [int] NULL,
	[CreatedAt] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[PromotionId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Rating]    Script Date: 2/6/2025 10:22:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Rating](
	[MentorID] [int] NULL,
	[MenteeID] [int] NULL,
	[noStar] [int] NULL,
	[ratingComment] [ntext] NULL,
	[rateTime] [datetime] NOT NULL,
	[SlotID] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[RejectRequest]    Script Date: 2/6/2025 10:22:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RejectRequest](
	[RequestID] [int] NULL,
	[Reason] [nvarchar](255) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Report]    Script Date: 2/6/2025 10:22:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Report](
	[ReportContent] [text] NULL,
	[reportTime] [datetime] NULL,
	[UserID] [int] NULL,
	[Status] [nchar](10) NULL,
	[ReportID] [int] IDENTITY(1,1) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ReportID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Request]    Script Date: 2/6/2025 10:22:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Request](
	[RequestID] [int] IDENTITY(1,1) NOT NULL,
	[SenderID] [int] NULL,
	[UserID] [int] NULL,
	[RequestReason] [nvarchar](255) NULL,
	[RequestStatus] [nvarchar](255) NULL,
	[RequestTime] [datetime] NOT NULL,
	[DeadlineTime] [datetime] NOT NULL,
	[RequestSubject] [nvarchar](255) NULL,
	[SkillID] [int] NULL,
	[rejectReason] [nvarchar](max) NULL,
	[RequestType] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[RequestID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[RequestSlot]    Script Date: 2/6/2025 10:22:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RequestSlot](
	[RequestID] [int] NULL,
	[SlotID] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Role]    Script Date: 2/6/2025 10:22:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Role](
	[RoleID] [int] IDENTITY(1,1) NOT NULL,
	[roleName] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[RoleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Schedule]    Script Date: 2/6/2025 10:22:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Schedule](
	[MentorID] [int] NULL,
	[Year] [int] NULL,
	[Week] [int] NULL,
	[ScheduleID] [int] IDENTITY(1,1) NOT NULL,
	[Status] [varchar](20) NULL,
	[rejectReason] [varchar](255) NULL,
 CONSTRAINT [PK__Schedule__9C8A5B69F7CD8C26] PRIMARY KEY CLUSTERED 
(
	[ScheduleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Skills]    Script Date: 2/6/2025 10:22:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Skills](
	[SkillID] [int] IDENTITY(1,1) NOT NULL,
	[SkillName] [nvarchar](255) NULL,
	[enable] [bit] NOT NULL,
	[Description] [ntext] NOT NULL,
	[image] [nvarchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[SkillID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Slot]    Script Date: 2/6/2025 10:22:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Slot](
	[SlotID] [int] IDENTITY(1,1) NOT NULL,
	[Time] [float] NULL,
	[startAt] [datetime] NULL,
	[Link] [nchar](50) NULL,
	[ScheduleID] [int] NULL,
	[SkillID] [int] NULL,
	[MenteeID] [int] NULL,
	[Status] [nchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[SlotID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Transaction]    Script Date: 2/6/2025 10:22:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Transaction](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [int] NULL,
	[Balance] [int] NULL,
	[Type] [nvarchar](1) NULL,
	[Content] [nvarchar](255) NULL,
	[Time] [date] NULL,
	[Status] [nvarchar](20) NULL,
	[TransCode] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User]    Script Date: 2/6/2025 10:22:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[UserID] [int] IDENTITY(1,1) NOT NULL,
	[sex] [bit] NULL,
	[activeStatus] [bit] NULL,
	[username] [varchar](255) NULL,
	[password] [varchar](255) NULL,
	[dob] [date] NULL,
	[email] [varchar](255) NULL,
	[phoneNumber] [varchar](10) NULL,
	[wallet] [int] NULL,
	[address] [nvarchar](255) NULL,
	[RoleID] [int] NULL,
	[isValidate] [bit] NULL,
	[Avatar] [nvarchar](255) NULL,
	[fullname] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[UserID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE TABLE [dbo].[tokenForgetPassword](
    [id] [int] IDENTITY(1,1) NOT NULL,         -- Khóa chính, tự động tăng
    [token] VARCHAR(255) NOT NULL,					-- Chuỗi token đặt lại mật khẩu
    [expiryTime] datetime NOT NULL,              -- Thời gian hết hạn của token
    [isUsed] [bit] NOT NULL,                       -- Trạng thái sử dụng của token (0: chưa sử dụng, 1: đã sử dụng)
    [userId] [int] NOT NULL,                       -- Khóa ngoại tham chiếu đến UserID của bảng User
PRIMARY KEY CLUSTERED 
(
    [id] ASC
) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, 
ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
CONSTRAINT FK_User_Token FOREIGN KEY ([userId]) REFERENCES [dbo].[User] ([UserID]) 
    ON DELETE CASCADE ON UPDATE CASCADE
) ON [PRIMARY];
/****** Object:  Table [dbo].[UserBank]    Script Date: 2/6/2025 10:22:29 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserBank](
	[UserID] [int] NULL,
	[BankName] [nvarchar](255) NULL,
	[BankType] [nvarchar](100) NULL,
	[BankNo] [nvarchar](100) NULL
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Promotion] ON 

INSERT [dbo].[Promotion] ([PromotionId], [Code], [DiscountPercentage], [StartDate], [EndDate], [UserId], [SkillId], [CreatedAt]) VALUES (1, N'WELCOME10', CAST(10.00 AS Decimal(5, 2)), CAST(N'2025-01-01' AS Date), CAST(N'2025-02-01' AS Date), NULL, NULL, CAST(N'2025-02-06T22:18:08.533' AS DateTime))
SET IDENTITY_INSERT [dbo].[Promotion] OFF
GO
SET IDENTITY_INSERT [dbo].[Role] ON 
select * from [User]
INSERT [dbo].[Role] ([RoleID], [roleName]) VALUES (1, N'Admin')
INSERT [dbo].[Role] ([RoleID], [roleName]) VALUES (2, N'Manager')
INSERT [dbo].[Role] ([RoleID], [roleName]) VALUES (3, N'Mentor')
INSERT [dbo].[Role] ([RoleID], [roleName]) VALUES (4, N'Mentee')
SET IDENTITY_INSERT [dbo].[Role] OFF
GO
SET IDENTITY_INSERT [dbo].[User] ON 

INSERT [dbo].[User] ([UserID], [sex], [activeStatus], [username], [password], [dob], [email], [phoneNumber], [wallet], [address], [RoleID], [isValidate], [Avatar], [fullname]) VALUES (1, 1, 1, N'user01', N'password123', CAST(N'1995-06-15' AS Date), N'user01@example.com', N'0123456789', 1000, N'Hanoi, Vietnam', 4, 1, N'avatar1.jpg', N'User One')
INSERT [dbo].[User] ([UserID], [sex], [activeStatus], [username], [password], [dob], [email], [phoneNumber], [wallet], [address], [RoleID], [isValidate], [Avatar], [fullname]) VALUES (2, 1, 1, N'manager01', N'password123', CAST(N'1990-02-20' AS Date), N'manager01@example.com', N'0987654321', 2000, N'Hanoi, Vietnam', 2, 1, N'avatar2.jpg', N'Staff One')
INSERT [dbo].[User] ([UserID], [sex], [activeStatus], [username], [password], [dob], [email], [phoneNumber], [wallet], [address], [RoleID], [isValidate], [Avatar], [fullname]) VALUES (3, 0, 1, N'admin01', N'password123', CAST(N'1985-08-10' AS Date), N'admin01@example.com', N'0912345678', 5000, N'HCMC, Vietnam', 1, 1, N'avatar3.jpg', N'Admin One')
INSERT [dbo].[User] ([UserID], [sex], [activeStatus], [username], [password], [dob], [email], [phoneNumber], [wallet], [address], [RoleID], [isValidate], [Avatar], [fullname]) VALUES (4, 1, 1, N'mentor01', N'password123', CAST(N'1988-12-05' AS Date), N'mentor01@example.com', N'0901234567', 3000, N'Danang, Vietnam', 3, 1, N'avatar4.jpg', N'Manager One')
SET IDENTITY_INSERT [dbo].[User] OFF
GO
ALTER TABLE [dbo].[CV] ADD  CONSTRAINT [DF_CV_CashPerSlot]  DEFAULT ((50000)) FOR [CashPerSlot]
GO
ALTER TABLE [dbo].[FollowRequest] ADD  DEFAULT (getdate()) FOR [RequestTime]
GO
ALTER TABLE [dbo].[FollowRequest] ADD  DEFAULT (dateadd(day,(7),getdate())) FOR [DeadLineTime]
GO
ALTER TABLE [dbo].[Mentee] ADD  CONSTRAINT [DF_Mentee_MenteeStatus]  DEFAULT (N'Active') FOR [MenteeStatus]
GO
ALTER TABLE [dbo].[Mentor] ADD  CONSTRAINT [DF_Mentor_MentorStatus]  DEFAULT (N'Active') FOR [MentorStatus]
GO
ALTER TABLE [dbo].[Payment] ADD  CONSTRAINT [DF_Payment_Time]  DEFAULT (getdate()) FOR [Time]
GO
ALTER TABLE [dbo].[Promotion] ADD  DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[Rating] ADD  CONSTRAINT [DF_Rating_rateTime]  DEFAULT (getdate()) FOR [rateTime]
GO
ALTER TABLE [dbo].[Report] ADD  CONSTRAINT [DF_Report_reportTime]  DEFAULT (getdate()) FOR [reportTime]
GO
ALTER TABLE [dbo].[Report] ADD  CONSTRAINT [DF_Report_Status]  DEFAULT (N'Pending') FOR [Status]
GO
ALTER TABLE [dbo].[Request] ADD  DEFAULT (getdate()) FOR [RequestTime]
GO
ALTER TABLE [dbo].[Skills] ADD  DEFAULT ((1)) FOR [enable]
GO
ALTER TABLE [dbo].[Slot] ADD  CONSTRAINT [DF_Slot_Status]  DEFAULT (N'Not Confirm') FOR [Status]
GO
ALTER TABLE [dbo].[Transaction] ADD  DEFAULT (getdate()) FOR [Time]
GO
ALTER TABLE [dbo].[User] ADD  DEFAULT ((0)) FOR [isValidate]
GO
ALTER TABLE [dbo].[Conversation]  WITH CHECK ADD FOREIGN KEY([MenteeID])
REFERENCES [dbo].[User] ([UserID])
GO
ALTER TABLE [dbo].[Conversation]  WITH CHECK ADD FOREIGN KEY([MentorID])
REFERENCES [dbo].[User] ([UserID])
GO
ALTER TABLE [dbo].[Follow]  WITH CHECK ADD FOREIGN KEY([MenteeID])
REFERENCES [dbo].[User] ([UserID])
GO
ALTER TABLE [dbo].[Follow]  WITH CHECK ADD FOREIGN KEY([MentorID])
REFERENCES [dbo].[User] ([UserID])
GO
ALTER TABLE [dbo].[FollowRequest]  WITH CHECK ADD FOREIGN KEY([MentorID])
REFERENCES [dbo].[User] ([UserID])
GO
ALTER TABLE [dbo].[FollowRequest]  WITH CHECK ADD FOREIGN KEY([SenderID])
REFERENCES [dbo].[User] ([UserID])
GO
ALTER TABLE [dbo].[Mentee]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[User] ([UserID])
GO
ALTER TABLE [dbo].[Mentor]  WITH CHECK ADD FOREIGN KEY([CvID])
REFERENCES [dbo].[CV] ([CvID])
GO
ALTER TABLE [dbo].[Mentor]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[User] ([UserID])
GO
ALTER TABLE [dbo].[MentorSkills]  WITH CHECK ADD FOREIGN KEY([MentorID])
REFERENCES [dbo].[User] ([UserID])
GO
ALTER TABLE [dbo].[MentorSkills]  WITH CHECK ADD FOREIGN KEY([SkillID])
REFERENCES [dbo].[Skills] ([SkillID])
GO
ALTER TABLE [dbo].[Message]  WITH CHECK ADD FOREIGN KEY([conversationID])
REFERENCES [dbo].[Conversation] ([conversationID])
GO
ALTER TABLE [dbo].[Message]  WITH CHECK ADD FOREIGN KEY([SenderID])
REFERENCES [dbo].[User] ([UserID])
GO
ALTER TABLE [dbo].[Payment]  WITH CHECK ADD FOREIGN KEY([ReceiverID])
REFERENCES [dbo].[User] ([UserID])
GO
ALTER TABLE [dbo].[Payment]  WITH CHECK ADD FOREIGN KEY([RequestID])
REFERENCES [dbo].[Request] ([RequestID])
GO
ALTER TABLE [dbo].[Payment]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[User] ([UserID])
GO
ALTER TABLE [dbo].[Promotion]  WITH CHECK ADD  CONSTRAINT [FK_Promotion_Skill] FOREIGN KEY([SkillId])
REFERENCES [dbo].[Skills] ([SkillID])
GO
ALTER TABLE [dbo].[Promotion] CHECK CONSTRAINT [FK_Promotion_Skill]
GO
ALTER TABLE [dbo].[Promotion]  WITH CHECK ADD  CONSTRAINT [FK_Promotion_User] FOREIGN KEY([UserId])
REFERENCES [dbo].[User] ([UserID])
GO
ALTER TABLE [dbo].[Promotion] CHECK CONSTRAINT [FK_Promotion_User]
GO
ALTER TABLE [dbo].[Rating]  WITH CHECK ADD FOREIGN KEY([MenteeID])
REFERENCES [dbo].[User] ([UserID])
GO
ALTER TABLE [dbo].[Rating]  WITH CHECK ADD FOREIGN KEY([MentorID])
REFERENCES [dbo].[User] ([UserID])
GO
ALTER TABLE [dbo].[Rating]  WITH CHECK ADD FOREIGN KEY([SlotID])
REFERENCES [dbo].[Request] ([RequestID])
GO
ALTER TABLE [dbo].[Rating]  WITH CHECK ADD  CONSTRAINT [FK_Rating_Slot] FOREIGN KEY([SlotID])
REFERENCES [dbo].[Slot] ([SlotID])
GO
ALTER TABLE [dbo].[Rating] CHECK CONSTRAINT [FK_Rating_Slot]
GO
ALTER TABLE [dbo].[RejectRequest]  WITH CHECK ADD FOREIGN KEY([RequestID])
REFERENCES [dbo].[Request] ([RequestID])
GO
ALTER TABLE [dbo].[Report]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[User] ([UserID])
GO
ALTER TABLE [dbo].[Request]  WITH CHECK ADD FOREIGN KEY([SenderID])
REFERENCES [dbo].[User] ([UserID])
GO
ALTER TABLE [dbo].[Request]  WITH CHECK ADD FOREIGN KEY([SkillID])
REFERENCES [dbo].[Skills] ([SkillID])
GO
ALTER TABLE [dbo].[Request]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[User] ([UserID])
GO
ALTER TABLE [dbo].[RequestSlot]  WITH CHECK ADD FOREIGN KEY([RequestID])
REFERENCES [dbo].[Request] ([RequestID])
GO
ALTER TABLE [dbo].[RequestSlot]  WITH CHECK ADD FOREIGN KEY([SlotID])
REFERENCES [dbo].[Slot] ([SlotID])
GO
ALTER TABLE [dbo].[Schedule]  WITH CHECK ADD FOREIGN KEY([MentorID])
REFERENCES [dbo].[User] ([UserID])
GO
ALTER TABLE [dbo].[Slot]  WITH CHECK ADD FOREIGN KEY([MenteeID])
REFERENCES [dbo].[User] ([UserID])
GO
ALTER TABLE [dbo].[Slot]  WITH CHECK ADD  CONSTRAINT [FK__Slot__ScheduleID__797309D9] FOREIGN KEY([ScheduleID])
REFERENCES [dbo].[Schedule] ([ScheduleID])
GO
ALTER TABLE [dbo].[Slot] CHECK CONSTRAINT [FK__Slot__ScheduleID__797309D9]
GO
ALTER TABLE [dbo].[Slot]  WITH CHECK ADD FOREIGN KEY([SkillID])
REFERENCES [dbo].[Skills] ([SkillID])
GO
ALTER TABLE [dbo].[Transaction]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[User] ([UserID])
GO
ALTER TABLE [dbo].[User]  WITH CHECK ADD FOREIGN KEY([RoleID])
REFERENCES [dbo].[Role] ([RoleID])
GO
ALTER TABLE [dbo].[UserBank]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[User] ([UserID])
GO
USE [master]
GO
ALTER DATABASE [DB_SWP391] SET  READ_WRITE 
GO

INSERT INTO [dbo].[Skills] ([SkillName], [enable], [Description], [image])
VALUES 
(N'Tiếng Anh Tiểu Học', 1, N'Mô tả về Tiếng Anh Tiểu Học', N'images/english_primary.jpg'),
(N'Tiếng Anh THCS', 1, N'Mô tả về Tiếng Anh THCS', N'images/english_junior_high.jpg'),
(N'Tiếng Anh THPT', 1, N'Mô tả về Tiếng Anh THPT', N'images/english_senior_high.jpg'),
(N'IELTS', 1, N'Mô tả về IELTS', N'images/ielts.jpg'),
(N'TOIEC', 1, N'Mô tả về TOIEC', N'images/toiec.jpg');

CREATE TABLE [dbo].[BecomeMentor] (
    MentorID INT PRIMARY KEY,  -- Đây sẽ là khóa ngoại, trỏ tới UserID trong bảng User
    fullname NVARCHAR(255) NOT NULL,          -- Tên đầy đủ của mentor
    phoneNumber NVARCHAR(20) NOT NULL,        -- Số điện thoại
    email NVARCHAR(255) NOT NULL,             -- Email
    address NVARCHAR(255) NOT NULL,           -- Địa chỉ
    sex bit NOT NULL,             -- Giới tính
    dob DATE NOT NULL,                        -- Ngày sinh
    Skills NVARCHAR(MAX),                    -- Lưu các SkillID của Mentor dưới dạng chuỗi, ví dụ "1, 2, 3"
    ProfessionIntroduction NVARCHAR(MAX) NOT NULL, -- Giới thiệu nghề nghiệp
    ServiceDescription NVARCHAR(MAX) NOT NULL, -- Mô tả dịch vụ
    CashPerSlot INT NOT NULL,                 -- Số tiền mỗi buổi học
    FOREIGN KEY (MentorID) REFERENCES [dbo].[User](UserID) -- MentorID liên kết với UserID
);

-- Thêm 5 người dùng vào bảng User với Role là Mentor (RoleID = 3)
SET IDENTITY_INSERT [dbo].[User] ON;

INSERT INTO [dbo].[User] ([UserID], [sex], [activeStatus], [username], [password], [dob], [email], [phoneNumber], [wallet], [address], [RoleID], [isValidate], [Avatar], [fullname]) 
VALUES 
(6, 1, 1, N'mentor02', N'$2a$10$GqUFuQKfr/UFNrSFf1YMCu9TttGLdvEwRteJIQIp7Pvd4DCisu/5C', '1984-07-15', N'mentor02@example.com', N'0911111111', 4000, N'Hanoi, Vietnam', 3, 1, N'avatar5.jpg', N'Mentor Two'),
(7, 0, 1, N'mentor03', N'$2a$10$GqUFuQKfr/UFNrSFf1YMCu9TttGLdvEwRteJIQIp7Pvd4DCisu/5C', '1992-03-22', N'mentor03@example.com', N'0922222222', 4500, N'HCMC, Vietnam', 3, 1, N'avatar6.jpg', N'Mentor Three'),
(8, 1, 1, N'mentor04', N'$2a$10$GqUFuQKfr/UFNrSFf1YMCu9TttGLdvEwRteJIQIp7Pvd4DCisu/5C', '1989-09-10', N'mentor04@example.com', N'0933333333', 5000, N'Danang, Vietnam', 3, 1, N'avatar7.jpg', N'Mentor Four'),
(9, 0, 1, N'mentor05', N'$2a$10$GqUFuQKfr/UFNrSFf1YMCu9TttGLdvEwRteJIQIp7Pvd4DCisu/5C', '1995-01-05', N'mentor05@example.com', N'0944444444', 4200, N'Hue, Vietnam', 3, 1, N'avatar8.jpg', N'Mentor Five'),
(10, 1, 1, N'mentor06', N'$2a$10$GqUFuQKfr/UFNrSFf1YMCu9TttGLdvEwRteJIQIp7Pvd4DCisu/5C', '1987-12-30', N'mentor06@example.com', N'0955555555', 4700, N'Haiphong, Vietnam', 3, 1, N'avatar9.jpg', N'Mentor Six');

SET IDENTITY_INSERT [dbo].[User] OFF;
GO

-- Thêm 5 mentor vào bảng Mentor
INSERT INTO [dbo].[Mentor] ([UserID], [Description], [CvID], [Achivement], [MentorStatus], [Skill]) 
VALUES 
(4, N'Chuyên gia luyện thi IELTS', NULL, N'IELTS 9.0', N'Active', N'IELTS, TOEIC'),
(6, N'Chuyên gia luyện thi IELTS', NULL, N'IELTS 8.5', N'Active', N'IELTS, TOEIC'),
(7, N'Giáo viên tiếng Anh phổ thông', NULL, N'10 năm kinh nghiệm giảng dạy', N'Active', N'Tiếng Anh Phổ thông'),
(8, N'Trợ giảng tiếng Anh THCS', NULL, N'Giảng dạy tại trung tâm XYZ', N'Active', N'Tiếng Anh Trung học'),
(9, N'Chuyên gia luyện phát âm', NULL, N'Khóa học phát âm cho người mất gốc', N'Active', N'Tiếng Anh Tiểu học, TOEIC'),
(10, N'Giáo viên dạy giao tiếp tiếng Anh', NULL, N'Chuyên dạy học viên người lớn', N'Active', N'IELTS, Tiếng Anh Phổ thông');

-- Thêm kỹ năng cho các Mentor vào bảng MentorSkills
INSERT INTO [dbo].[MentorSkills] ([MentorID], [SkillID]) 
VALUES 
(4, 4), -- Mentor01 có kỹ năng IELTS
(4, 5), -- Mentor01 có kỹ năng TOEIC
(6, 4), -- Mentor02 có kỹ năng IELTS
(6, 5), -- Mentor02 có kỹ năng TOEIC
(7, 3), -- Mentor03 có kỹ năng Tiếng Anh Phổ thông
(8, 2), -- Mentor04 có kỹ năng Tiếng Anh Trung học
(9, 1), -- Mentor05 có kỹ năng Tiếng Anh Tiểu học
(9, 5), -- Mentor05 có kỹ năng TOEIC
(10, 4), -- Mentor06 có kỹ năng IELTS
(10, 3); -- Mentor06 có kỹ năng Tiếng Anh Phổ thông

SET IDENTITY_INSERT [dbo].[CV] ON;

INSERT INTO [dbo].[CV] ([CvID], [ProfessionIntroduction], [ServiceDescription], [CashPerSlot], [rejectReason]) 
VALUES 
(1, N'Giảng viên IELTS chuyên nghiệp', N'Khóa học luyện thi IELTS từ 5.0 lên 8.0', 300000, NULL),
(2, N'Giáo viên tiếng Anh phổ thông', N'Hướng dẫn học sinh cấp 3 nâng cao trình độ tiếng Anh', 250000, NULL),
(3, N'Trợ giảng tiếng Anh trung học', N'Hỗ trợ học sinh cấp 2 phát triển kỹ năng nghe nói', 200000, NULL),
(4, N'Chuyên gia luyện phát âm', N'Khóa học phát âm chuẩn Anh - Mỹ', 270000, NULL),
(5, N'Giáo viên dạy giao tiếp tiếng Anh', N'Khóa học giao tiếp cấp tốc cho người mất gốc', 280000, NULL);
(6, N'Giáo viên dạy giao tiếp tiếng Anh IELTS', N'Luyện thi TOIEC', 500000, NULL);
SET IDENTITY_INSERT [dbo].[CV] OFF;
GO

INSERT INTO Conversation (MentorID, MenteeID) VALUES (6, 5);
INSERT INTO Conversation (MentorID, MenteeID) VALUES (7, 5);
INSERT INTO Conversation (MentorID, MenteeID) VALUES (8, 5);
INSERT INTO Conversation (MentorID, MenteeID) VALUES (9, 5);
INSERT INTO Conversation (MentorID, MenteeID) VALUES (10, 5);

INSERT INTO Message (conversationID, SenderID, sentAt, msgContent)
VALUES
((SELECT conversationID FROM Conversation WHERE MentorID = 6 AND MenteeID = 5), 5, GETDATE(), N'Chào anh, em muốn học IELTS.'),
((SELECT conversationID FROM Conversation WHERE MentorID = 6 AND MenteeID = 5), 6, GETDATE(), N'Chào em, anh sẽ giúp em luyện IELTS!'),
((SELECT conversationID FROM Conversation WHERE MentorID = 6 AND MenteeID = 5), 5, GETDATE(), N'Anh có lịch rảnh vào cuối tuần không ạ?'),
((SELECT conversationID FROM Conversation WHERE MentorID = 6 AND MenteeID = 5), 6, GETDATE(), N'Có nhé! Em muốn học lúc nào?');

GO



-- Cập nhật bảng Mentor để liên kết với CV
UPDATE [dbo].[Mentor] SET CvID = 1 WHERE UserID = 6;
UPDATE [dbo].[Mentor] SET CvID = 2 WHERE UserID = 7;
UPDATE [dbo].[Mentor] SET CvID = 3 WHERE UserID = 8;
UPDATE [dbo].[Mentor] SET CvID = 4 WHERE UserID = 9;
UPDATE [dbo].[Mentor] SET CvID = 5 WHERE UserID = 10;
UPDATE [dbo].[Mentor] SET CvID = 6 WHERE UserID = 4;
-- Chỉ cho phép một cuộc trò chuyện duy nhất giữa một Mentor và một Mentee
ALTER TABLE [dbo].[Conversation]
ADD CONSTRAINT UQ_Mentor_Mentee UNIQUE ([MentorID], [MenteeID]);
GO
USE [master]
GO
ALTER DATABASE [DB_SWP391] SET  READ_WRITE 
GO

ALTER TABLE Slot ALTER COLUMN Time varchar(10) NOT NULL;ALTER TABLE Slot ALTER COLUMN Time varchar(10) NOT NULL;