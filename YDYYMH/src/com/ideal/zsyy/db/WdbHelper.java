package com.ideal.zsyy.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WdbHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 5;
	private static String DATABASE_NAME = "w_db";
	public WdbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	public WdbHelper(Context context,String dbName)
	{
		super(context, DATABASE_NAME+"_"+dbName, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		this.ResetTable(db);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		this.ResetTable(db);
	}
	
	public void ResetTableQianFeiHistory(SQLiteDatabase db)
	{
		String strSql="Drop Table IF EXISTS TB_QianFeiHistory ";
		db.execSQL(strSql);	
		
		strSql="Create Table IF NOT EXISTS TB_QianFeiHistory(Qid INTEGER Primary key AUTOINCREMENT," +
				" waterUserId varchar(20), waterUserNO VARCHAR(20),waterUserName  VARCHAR(20)," +
				" waterPhone varchar(50), waterUserAddress varchar(100),waterMeterLastNumber int,"+
				" waterMeterEndNumber int,totalNumber int,totalCharge Numeric(8,3),"+
				" readMeterRecordYear int,readMeterRecordMonth int,meterReadingNO  varchar(50),"+
				" meterReadingID varchar(50),ordernumber int,WATERUSERQQYE Numeric(10,3),"+
				" WATERUSERJSYE Numeric(10,3),INFORMPRINTSIGN int)";
		db.execSQL(strSql);
	}
	
	public void ResetTable(SQLiteDatabase db)
	{
		String strSql="drop table IF EXISTS  TB_UserInfo";
		db.execSQL(strSql);
		strSql="drop table IF EXISTS TB_PicInfo";
		db.execSQL(strSql);
		strSql="drop table IF EXISTS TB_FaultReport";
		db.execSQL(strSql);
		strSql="drop table IF EXISTS TB_CustomAdvice";
		db.execSQL(strSql);
		strSql="drop table IF EXISTS TB_LPoint";
		db.execSQL(strSql);
		strSql="drop table IF EXISTS TB_BiaoBenInfo";
		db.execSQL(strSql);
		strSql="drop table IF EXISTS TB_ChaoBiaoInfo";
		db.execSQL(strSql);
		strSql="Drop Table IF EXISTS TB_UnitPrice";
		db.execSQL(strSql);
		strSql="Drop Table IF EXISTS TB_WaterCharge ";
		db.execSQL(strSql);
		strSql="Drop Table IF EXISTS TB_QianFeiHistory ";
		db.execSQL(strSql);
		strSql="Drop Table IF EXISTS TB_QianFei ";
		db.execSQL(strSql);
		
		strSql=" CREATE TABLE IF NOT EXISTS TB_UserInfo ([UserID] INTEGER PRIMARY KEY AUTOINCREMENT," +
				"[readMeterRecordId] NVARCHAR(50), [StealNo] varchar(50), [UserNo] NVARCHAR(50), " +
				"[BId] INTEGER, [NoteNo] varchar(50),  [UserFName] varchar(100), [Phone] varchar(50), " +
				"[Address] varchar(300), [LastMonthValue] Numeric(10,3), [LastMonthWater] Numeric(10,3), " +
				"[LastMonthFee] Numeric(10,3), [LastChaoBiaoDate] nvarchar(30), [CurrentMonthValue] Numeric(10,3), " +
				"[CurrMonthWNum] Numeric(12,3), [CurrMonthFee] Numberic(12,3), [ShouFei] Numeric(10,3), " +
				"[ShouFeiDate] varchar(50), [PriceType] varchar(20), [PriceTypeName] VARCHAR(50), " +
				"[StealID] nvarchar(50), [ChaoBiaoTag] TINYINT, [ChaoBiaoDate] varchar(50), [IsChaoBiao] TINYINT," +
				"[OrChaoBiaoTag] TINYINT,[OrMemoTag] TINYINT,[IsPrint] TINYINT, [OrPhoneTag] TINYINT,[OrGpsTag] TINYINT,"+
				"[PreMoney] Numeric(10,3), [OweMoney] Numeric(10,3), " +
				"[OrOweFee] Numeric(12,3), [OrPreMoney] Numeric(12,3), [Latitude] Real, [Longitude] Real, " +
				"[alreadyUpload] TINYINT, [IsReverse] INT NOT NULL, [StepPrice] NVARCHAR(100), [ExtraPrice] NVARCHAR(100), " +
				"[IsSummaryMeter] INT, [WaterMeterParentID] varchar(50), [OrderNumber] int, [WaterFixValue] NUMBER(12, 3), " +
				"[NFCTag] NVARCHAR(100), [PianNo] NVARCHAR(30), [AreaNo] NVARCHAR(30), [DuanNo] NVARCHAR(30), [avePrice] REAL," +
				" [extraChargePrice1] REAL, [extraCharge1] REAL, [extraChargePrice2] REAL, [extraCharge2] REAL, [extraTotalCharge] REAL, " +
				"[TotalCharge] REAL, [OVERDUEMONEY] REAL, [ReadMeterRecordYear] INT, [ReadMeterRecordMonth] INT, " +
				"[WaterMeterPositionName] nvarchar(50), [checkState] nvarchar(20), [checkDateTime] NVARCHAR(20), [checker] NVARCHAR(20), " +
				"[chargeID] NVARCHAR(50),waterUserchargeType varchar(10),waterUserTypeId varchar(50),Memo1 varchar(300));";
		db.execSQL(strSql);
		
		strSql=" CREATE TABLE IF NOT EXISTS TB_WaterCharge ([CHARGEID] nvarchar(50), " +
				" [TOTALNUMBERCHARGE] NUMBER(12, 3), [EXTRACHARGECHARGE1] numeric(9,2), " +
				" [EXTRACHARGECHARGE2] numeric(9,2), [WATERTOTALCHARGE] numeric(9,2)," +
				" [TOTALCHARGE] numeric(9,2), [OVERDUEMONEY] numeric(9,2), [CHARGETYPEID] int, " +
				" [CHARGEClASS] nvarchar(50), [CHARGEBCYS] numeric(9,2), [CHARGEBCSS] numeric(9,2), " +
				" [CHARGEYSQQYE] numeric(9,2), [CHARGEYSBCSZ] numeric(9,2), [CHARGEYSJSYE] numeric(9,2), " +
				" [CHARGEWORKERID] nvarcar(50), [CHARGEWORKERNAME] nvarchar(50), [CHARGEDATETIME] nvarchar(20), " +
				" [RECEIPTPRINTCOUNT] int, [RECEIPTNO] nvarchar(50), [MEMO] nvarchar(200),[alreadyUpload] INT);";
		db.execSQL(strSql);
		
		strSql="  CREATE TABLE IF NOT EXISTS TB_PicInfo(" +
				" PicId INTEGER primary key autoincrement,BId INTEGER,StealNo varchar(30),UserNo varchar(50),NoteNo varchar(50),PicName varchar(100),PicPath varchar(100)," +
				" PicType int,AddDate varchar(50),AddUserId varchar(50),AddUser varchar(100),Longitude Real, Latitude Real,alreadyUpload TINYINT);";
		db.execSQL(strSql);
		strSql="  CREATE TABLE IF NOT EXISTS TB_FaultReport(" +
				" PicId INTEGER primary key AUTOINCREMENT,BId INTEGER,StealNo varchar(30),UserNo varchar(50),NoteNo varchar(50)," +
				" FaultDescript varchar(300)," +
				" AddDate varchar(50),AddUserID varchar(50),AddUser varchar(100),alreadyUpload TINYINT);";
		db.execSQL(strSql);
		strSql="  CREATE TABLE IF NOT EXISTS TB_CustomAdvice(" +
				" AdviceID INTEGER Primary Key AUTOINCREMENT,BId INTEGER," +
				" UserNo varchar(50),NoteNo varchar(50),Advice varchar(300),AddUserId varchar(50),AddUser varchar(50)," +
				" AddDate varchar(50),alreadyUpload TINYINT);";
		db.execSQL(strSql);
		strSql="  CREATE TABLE IF NOT EXISTS TB_LPoint(" +
				" PId INTEGER Primary Key Autoincrement,Latitude real," +
				" Longitude real,UserId varchar(50),UserName varchar(50)," +
				" AddDate varchar(50),alreadyUpload TINYINT);";
		db.execSQL(strSql);
		strSql="  CREATE TABLE IF NOT EXISTS TB_BiaoBenInfo(" +
				" BId INTEGER primary key autoincrement," +
				" NoteNo varchar(50),PianNo nvarchar(30),AreaNo nvarchar(30)," +
				" DuanNo nvarchar(30), CBMonth int,CBYear int," +
				" CBUser varchar(50),CBUserID varchar(50),CustomerCount int);";
		db.execSQL(strSql);
		strSql="  CREATE TABLE IF NOT EXISTS TB_ChaoBiaoInfo(" +
				" CId INTEGER primary key autoincrement,UserNo varchar(50)," +
				" ChaoBiaoTag TINYINT, PreMoney Numeric(12,3)," +
				" OweMoney Numeric(12,3),CurrentMonthValue Numeric(12,3),ChaoBiaoDate varchar(50)," +
				" ShouFei Numeric(12,3),ShouFeiDate varchar(50),Latitude Real,Longitude Real,alreadyUpload TINYINT );";
		db.execSQL(strSql);
		strSql="Create Table IF NOT EXISTS TB_UnitPrice(Unid INTEGER Primary key AUTOINCREMENT," +
				" PriceType varchar(20), PriceTypeName VARCHAR(50),SIndex int,WFrom Numeric(10,3)," +
				" WTo Numeric(10,3),WPrice Numeric(8,3))";
		db.execSQL(strSql);
		//waterUserId,waterUserNO,waterUserName,waterPhone,waterUserAddress,waterMeterLastNumber,
		//waterMeterEndNumber,totalNumber,totalCharge,
		//readMeterRecordYear,readMeterRecordMonth,meterReadingNO,
		//meterReadingID,ordernumber,WATERUSERQQYE,
		//WATERUSERJSYE,INFORMPRINTSIGN FROM V_YSDETAIL_BYWATERMETER
		strSql="Create Table IF NOT EXISTS TB_QianFeiHistory(Qid INTEGER Primary key AUTOINCREMENT," +
				" waterUserId varchar(20), waterUserNO VARCHAR(20),waterUserName  VARCHAR(20)," +
				" waterPhone varchar(50), waterUserAddress varchar(100),waterMeterLastNumber int,"+
				" waterMeterEndNumber int,totalNumber int,totalCharge Numeric(8,3),"+
				" readMeterRecordYear int,readMeterRecordMonth int,meterReadingNO  varchar(50),"+
				" meterReadingID varchar(50),ordernumber int,WATERUSERQQYE Numeric(10,3),"+
				" WATERUSERJSYE Numeric(10,3),INFORMPRINTSIGN int)";
		db.execSQL(strSql);
		strSql="Create Table IF NOT EXISTS TB_QianFei(Qid INTEGER Primary key AUTOINCREMENT," +
				" waterUserId varchar(20), waterUserNO VARCHAR(20),waterUserName  VARCHAR(20)," +
				" waterPhone varchar(50), waterUserAddress varchar(100),meterReadingNO  varchar(50),"+
				" meterReadingID varchar(50),prestore Numeric(8,3),TOTALFEE Numeric(8,3),TOTALNUMBER int,ordernumber int)";
		db.execSQL(strSql);
	}

}
