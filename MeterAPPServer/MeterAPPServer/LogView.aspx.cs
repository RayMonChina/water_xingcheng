using FluentData;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.IO;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using TestAndroid.BLL;
using TestAndroid.Models.Entity;
using TestAndroid.Models.Request;
using TestAndroid.Models.Response;

namespace MeterAPPServer
{
    public partial class LogView : System.Web.UI.Page
    {
        public DataTable LogDt;
        private long MinSize = 300;
        private long MaxSize = 800;

        private string loginid = "0003";
        private string NoteNo = "0";

        private string FileCreateTime = "";
        private int ordernumber = 0;
        private string waterUserNO = "";
        private string waterMeterNo = "";
        private string waterUserName = "";
        private int waterMeterLastNumber = 0;
        //private string 

        private DirectoryInfo d = new DirectoryInfo(@"D:\P-自来水\Log\");

        AndroidOperateService AService = new AndroidOperateService();
        protected void Page_Load(object sender, EventArgs e)
        {
            CreateLogDt();
            BindLoginUser();

        }

        public static IDbContext WDbContext()
        {
            string strConn = ConfigurationManager.AppSettings["cbConnstring"];
            return new DbContext().ConnectionString(strConn, new SqlServerProvider());
        }

        private void BindNoteNo()
        {

        }

        private void BindLoginUser()
        {
            BindNoteNo();
        }

        protected void Btn_Search_Click(object sender, EventArgs e)
        {
            //
            Search_Files();
        }

        private void Search_Files()
        {

            GetAll(d);
            LB_Num.Text = LogDt.Rows.Count.ToString();



            DataView dv = LogDt.DefaultView;
            dv.Sort = "OrderNumber asc";



            GV_LOG.DataSource = dv.ToTable();
            GV_LOG.DataBind();

        }

        #region 创建临时表
        private void CreateLogDt()
        {
            //{"Latitude":"0.0","Longitude":"0.0","OVERDUEMONEY":0.0,"avePrice":2.2,"chargeID":"201605070003SF000381","chargeState":"3",
            //"checkDateTime":"2016-05-07 12:39:28","checkState":"1","extraCharge1":0.0,"extraCharge2":0.0,"extraChargePrice1":0.0,
            // "extraChargePrice2":0.0,"extraTotalCharge":0.0,"readMeterRecordDate":"2016-05-07 12:39:21","readMeterRecordId":"20160430004179",
            //"totalCharge":662.2,"totalNumber":301,"waterMeterEndNumber":375,"waterTotalCharge":662.2,"operType":"5"}
            LogDt = new DataTable();

            LogDt.Columns.Add("readMeterRecordId");
            LogDt.Columns.Add("UserNo");
            LogDt.Columns.Add("UserFName");
            LogDt.Columns.Add("LastMonthValue");
            LogDt.Columns.Add("CurrentMonthValue");
            LogDt.Columns.Add("UpdateTime");
            LogDt.Columns.Add("NoteNo");//表本号
            LogDt.Columns.Add("StealNo");
            LogDt.Columns.Add("ChaoBiaoTag");
            LogDt.Columns.Add("chargeID");
            LogDt.Columns.Add("ChaoBiaoDate");
            LogDt.Columns.Add("loginId");
            LogDt.Columns.Add("USERNAME");
            LogDt.Columns.Add("checkState");
            LogDt.Columns.Add("checkDateTime");
            LogDt.Columns.Add("OrderNumber");

            //LogDt.Columns.Add("Latitude");
            //LogDt.Columns.Add("Longitude");
            //LogDt.Columns.Add("OVERDUEMONEY");
            //LogDt.Columns.Add("avePrice");
            //LogDt.Columns.Add("chargeID");
            //LogDt.Columns.Add("chargeState");
            //LogDt.Columns.Add("checkDateTime");
            //LogDt.Columns.Add("checkState");
            //LogDt.Columns.Add("extraCharge1");
            //LogDt.Columns.Add("extraCharge2");
            //LogDt.Columns.Add("extraChargePrice1");
            //LogDt.Columns.Add("extraChargePrice2");
            //LogDt.Columns.Add("extraTotalCharge");
            //LogDt.Columns.Add("readMeterRecordDate");
            //LogDt.Columns.Add("readMeterRecordId");
            //LogDt.Columns.Add("totalCharge");
            //LogDt.Columns.Add("totalNumber");
            //LogDt.Columns.Add("waterMeterEndNumber");
            //LogDt.Columns.Add("waterTotalCharge");
            //LogDt.Columns.Add("operType");

        }
        #endregion

        #region 遍历文件
        void GetAll(DirectoryInfo dir)
        {
            FileInfo[] allFile = dir.GetFiles();
            foreach (FileInfo fi in allFile)
            {
                long len = fi.Length;
                if (len > MinSize && len < MaxSize)
                {
                    FileCreateTime = fi.LastWriteTime.ToString();
                    StreamReader sr = fi.OpenText();
                    string RecordInfo = sr.ReadLine();
                    sr.Close();

                    //写入临时表
                    //InsertLogDt(RecordInfo);

                    //写入表readMeterRecordLog
                    InsertreadMeterRecordLog(RecordInfo);
                }
                else
                {
                    fi.Delete();
                }
            }

            DirectoryInfo[] allDir = dir.GetDirectories();
            foreach (DirectoryInfo d in allDir)
            {
                GetAll(d);
            }
        }

        private void InsertreadMeterRecordLog(string RecordInfo)
        {
            var ur = JsonTool.Deserialize<WUploadUserReq>(RecordInfo);
            if (string.IsNullOrEmpty(ur.readMeterRecordId))
            {
                return;
            }

            WSingleUserItemReq req = new WSingleUserItemReq();
            req.readMeterRecordId = ur.readMeterRecordId;

            WUserItemRes wr = AService.GetSingleUserItemRes(req);
            if (wr.userItems.Count < 1)
            {
                return;
            }
            WUserItem wi = wr.userItems[0];

            if (ur.chargeState == wi.ChaoBiaoTag && ur.checkState == wi.checkState && ur.chargeID == wi.chargeID && ur.waterMeterEndNumber == wi.CurrentMonthValue)
            {
                return;
            }
            ordernumber = wi.OrderNumber;
            waterUserNO = wi.UserNo;
            waterMeterNo=wi.StealNo;
            waterUserName=wi.UserFName;
            waterMeterLastNumber = wi.LastMonthValue;
            loginid = wi.loginId;
            UpdataMeterData(ur);
        }

        private void UpdataMeterData(WUploadUserReq req)
        {
            using (var context = WDbContext())
            {
                context.Insert("readMeterRecordLog")
                  .Column("readMeterRecordId", req.readMeterRecordId)
                  .Column("waterMeterEndNumber", req.waterMeterEndNumber)
                  .Column("totalNumber", req.totalNumber)
                  .Column("avePrice", req.avePrice)
                  .Column("waterTotalCharge", req.waterTotalCharge)
                  .Column("extraChargePrice1", req.extraChargePrice1)
                  .Column("extraCharge1", req.extraCharge1)
                  .Column("extraChargePrice2", req.extraChargePrice2)
                  .Column("extraCharge2", req.extraCharge2)
                  .Column("extraTotalCharge", req.extraTotalCharge)
                  .Column("totalCharge", req.totalCharge)
                  .Column("OVERDUEMONEY", req.OVERDUEMONEY)
                  .Column("chargeID", req.chargeID)
                  .Column("chargeState", req.chargeState)
                  .Column("checkState", req.checkState)
                  .Column("checker", req.checker)
                  .Column("checkDateTime", FileCreateTime)
                   .Column("loginid", loginid)
                  .Column("Longitude", req.Longitude)
                  .Column("Latitude", req.Latitude)
                  .Column("waterPhone", req.Phone)
                  .Column("Memo1", req.Memo1)
                  .Column("ordernumber", ordernumber)
                  .Column("waterUserNO", waterUserNO)
                  .Column("waterMeterNo", waterMeterNo)
                  .Column("waterUserName", waterUserName)
                  .Column("waterMeterLastNumber", waterMeterLastNumber)
                  .Execute();
            }
        }

        private void InsertLogDt(string sr)
        {
            var ur = JsonTool.Deserialize<WUploadUserReq>(sr);
            if (string.IsNullOrEmpty(ur.readMeterRecordId))
            {
                return;
            }
            WSingleUserItemReq req = new WSingleUserItemReq();
            req.readMeterRecordId = ur.readMeterRecordId;

            WUserItemRes wr = AService.GetSingleUserItemRes(req);
            if (wr.userItems.Count < 1)
            {
                return;
            }
            WUserItem wi = wr.userItems[0];
            try
            {
                if (wi.loginId == loginid)
                {
                    if (ur.chargeState == wi.ChaoBiaoTag && ur.checkState == wi.checkState && ur.chargeID == wi.chargeID && ur.waterMeterEndNumber == wi.CurrentMonthValue)
                    {
                        return;
                    }

                    DataRow dr = LogDt.NewRow();
                    dr[0] = ur.readMeterRecordId;
                    dr[1] = wi.UserNo;
                    dr[2] = wi.UserFName;
                    dr[3] = wi.LastMonthValue;
                    dr[4] = ur.waterMeterEndNumber;
                    dr[5] = FileCreateTime;
                    dr[6] = wi.NoteNo;
                    dr[7] = wi.StealNo;
                    dr[8] = ur.chargeState;
                    dr[9] = ur.chargeID;
                    dr[10] = ur.readMeterRecordDate;
                    dr[11] = wi.loginId;
                    dr[12] = wi.USERNAME;
                    dr[13] = ur.checkState;
                    dr[14] = ur.checkDateTime;
                    dr[15] = wi.OrderNumber;

                    LogDt.Rows.Add(dr);
                }
            }
            catch (Exception)
            {


            }
        }



        #endregion
    }
}